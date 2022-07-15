/* GLSL grammar that extends the graphicsfuzz grammar
but is specifically built for this project*/

/*
 * Copyright 2018 The GraphicsFuzz Project Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

parser grammar GLSLParser;

@header {
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.ExtendedParser;
}

options {
	tokenVocab = GLSLLexer;
	contextSuperClass = ExtendedContext;
	superClass = ExtendedParser;
}

//the root rule
translationUnit: versionStatement? externalDeclaration* EOF;

//allows for EXT_null_initializer
versionStatement:
	NR VERSION version = NR_INTCONSTANT profile = (
		NR_CORE
		| NR_COMPATIBILITY
		| NR_ES
	)? NR_EOL;

externalDeclaration:
	functionDefinition
	| declaration
	| pragmaStatement
	| extensionStatement
	| layoutDefaults
	| emptyDeclaration;

emptyDeclaration: SEMICOLON;

pragmaStatement:
	NR PRAGMA stdGL = NR_STDGL? (
		type = (PRAGMA_DEBUG | PRAGMA_OPTIMIZE) NR_LPAREN state = (
			NR_ON
			| NR_OFF
		) NR_RPAREN
		| type = PRAGMA_INVARIANT NR_LPAREN state = NR_ALL NR_RPAREN
		| type = NR_IDENTIFIER
	) NR_EOL;

extensionStatement:
	NR EXTENSION extensionName = NR_IDENTIFIER (
		NR_COLON extensionBehavior = (
			NR_REQUIRE
			| NR_ENABLE
			| NR_WARN
			| NR_DISABLE
		)
	)? NR_EOL;

layoutDefaults:
	layoutQualifier layoutMode = (UNIFORM | IN | OUT | BUFFER) SEMICOLON;

functionDefinition: functionPrototype compoundStatement;

//Note: diverges from the spec by explicity adding a method call instead of handling it through postfixExpression in functionIdentifier
expression:
	IDENTIFIER # referenceExpression
	| (
		INT16CONSTANT
		| UINT16CONSTANT
		| INT32CONSTANT
		| UINT32CONSTANT
		| INT64CONSTANT
		| UINT64CONSTANT
		| FLOAT16CONSTANT
		| FLOAT32CONSTANT
		| FLOAT64CONSTANT
		| BOOLCONSTANT
	)																													# literalExpression
	| LPAREN value = expression RPAREN												# groupingExpression
	| left = expression LBRACKET right = expression RBRACKET	# arrayAccessExpression
	| operand = expression DOT_LENGTH LPAREN RPAREN						# lengthAccessExpression
	//Note: diverges from the spec by not allowing a prefixExpression as an identifier
	//array-type function identfiers are handled by typeSpecifier
	| (typeSpecifier | IDENTIFIER) LPAREN (
		| VOID
		| parameters += expression (COMMA parameters += expression)*
	) RPAREN																				# functionCallExpression
	| operand = expression DOT member = IDENTIFIER	# memberAccessExpression
	| operand = expression op = (INC_OP | DEC_OP)		# postfixExpression
	| <assoc = right> op = (
		INC_OP
		| DEC_OP
		| PLUS_OP
		| MINUS_OP
		| BOOL_NOT_OP
		| BIT_NEG_OP
	) operand = expression																											# prefixExpression
	| left = expression op = (TIMES_OP | DIV_OP | MOD_OP) right = expression		# multiplicativeExpression
	| left = expression op = (PLUS_OP | MINUS_OP) right = expression						# additiveExpression
	| left = expression op = (LEFT_OP | RIGHT_OP) right = expression						# shiftExpression
	| left = expression op = (LT_OP | GT_OP | LE_OP | GE_OP) right = expression	# relationalExpression
	| left = expression op = (EQ_OP | NE_OP) right = expression									# equalityExpression
	| left = expression op = BIT_AND_OP right = expression											# bitwiseAndExpression
	| left = expression op = BIT_XOR_OP right = expression											# bitwiseExclusiveOrExpression
	| left = expression op = BIT_OR_OP right = expression												# bitwiseInclusiveOrExpression
	| left = expression op = BOOL_AND_OP right = expression											# logicalAndExpression
	| left = expression op = BOOL_XOR_OP right = expression											# logicalExclusiveOrExpression
	| left = expression op = BOOL_OR_OP right = expression											# logicalInclusiveOrExpression
	| <assoc = right> condition = expression QUERY_OP trueAlternative = expression COLON falseAlternative =
		expression # conditionalExpression
	| <assoc = right> left = expression op = (
		ASSIGN_OP
		| MUL_ASSIGN
		| DIV_ASSIGN
		| MOD_ASSIGN
		| ADD_ASSIGN
		| SUB_ASSIGN
		| LEFT_ASSIGN
		| RIGHT_ASSIGN
		| AND_ASSIGN
		| XOR_ASSIGN
		| OR_ASSIGN
	) right = expression													# assignmentExpression
	| left = expression COMMA right = expression	# sequenceExpression;

declaration:
	functionPrototype SEMICOLON # functionDeclaration
	| fullySpecifiedType (
		declarationMembers += declarationMember (
			COMMA declarationMembers += declarationMember
		)*
	)? SEMICOLON																						# typeAndInitDeclaration
	| PRECISION precisionQualifier typeSpecifier SEMICOLON	# precisionDeclaration
	| typeQualifier blockName = IDENTIFIER structBody (
		variableName = IDENTIFIER arraySpecifier?
	)? SEMICOLON # interfaceBlockDeclaration
	| typeQualifier (
		variableNames += IDENTIFIER (COMMA variableNames += IDENTIFIER)*
	)? SEMICOLON # variableDeclaration;

//allows for EXT_subgroup_uniform_control_flow
functionPrototype:
	attribute? fullySpecifiedType IDENTIFIER LPAREN functionParameterList RPAREN attribute?;

functionParameterList: (
		parameters += parameterDeclaration (
			COMMA parameters += parameterDeclaration
		)*
	)?;

parameterDeclaration:
	fullySpecifiedType (parameterName = IDENTIFIER arraySpecifier?)?;

//part of GL_EXT_control_flow_attributes
attribute:
	LBRACKET LBRACKET attributes += singleAttribute (
		COMMA attributes += singleAttribute
	)* RBRACKET RBRACKET;

singleAttribute:
	(prefix = IDENTIFIER COLON COLON)? name = IDENTIFIER (
		LPAREN content = expression RPAREN
	)?;

declarationMember: IDENTIFIER arraySpecifier? (ASSIGN_OP initializer)?;

fullySpecifiedType: typeQualifier? typeSpecifier;

//allows for KHR_memory_scope_semantics
storageQualifier:
	CONST
	| IN
	| OUT
	| INOUT
	| CENTROID
	| PATCH
	| SAMPLE
	| UNIFORM
	| VARYING
	| ATTRIBUTE
	| BUFFER
	| SHARED
	| RESTRICT
	| VOLATILE
	| COHERENT
	| READONLY
	| WRITEONLY
	| SUBROUTINE (
		//TYPE_NAME instead of IDENTIFIER in the spec
		LPAREN typeNames += IDENTIFIER (COMMA typeNames += IDENTIFIER)* RPAREN
	)?
	| DEVICECOHERENT
	| QUEUEFAMILYCOHERENT
	| WORKGROUPCOHERENT
	| SUBGROUPCOHERENT
	| NONPRIVATE;

layoutQualifier:
	LAYOUT LPAREN layoutQualifiers += layoutQualifierId (
		COMMA layoutQualifiers += layoutQualifierId
	)* RPAREN;

//constant expression
layoutQualifierId:
	IDENTIFIER (ASSIGN_OP expression)?	# namedLayoutQualifier
	| SHARED														# sharedLayoutQualifier;

precisionQualifier: HIGHP | MEDIUMP | LOWP;

interpolationQualifier: SMOOTH | FLAT | NOPERSPECTIVE;

invariantQualifier: INVARIANT;

preciseQualifier: PRECISE;

typeQualifier: (
		storageQualifier
		| layoutQualifier
		| precisionQualifier
		| interpolationQualifier
		| invariantQualifier
		| preciseQualifier
	)+;

//TYPE_NAME instead of IDENTIFIER in the spec
typeSpecifier: (
		builtinTypeSpecifierFixed
		| builtinTypeSpecifierParseable
		| structSpecifier
		| IDENTIFIER
	) arraySpecifier?;

//needs duplicated rule parts like this or it becomes mutually left-recursive
//constant expressions
arraySpecifier: arraySpecifierSegment+;
arraySpecifierSegment: (LBRACKET expression? RBRACKET);

builtinTypeSpecifierParseable:
	BOOL
	| BVEC2
	| BVEC3
	| BVEC4
	| FLOAT16
	| F16VEC2
	| F16VEC3
	| F16VEC4
	| F16MAT2X2
	| F16MAT2X3
	| F16MAT2X4
	| F16MAT3X2
	| F16MAT3X3
	| F16MAT3X4
	| F16MAT4X2
	| F16MAT4X3
	| F16MAT4X4
	| FLOAT32
	| F32VEC2
	| F32VEC3
	| F32VEC4
	| F32MAT2X2
	| F32MAT2X3
	| F32MAT2X4
	| F32MAT3X2
	| F32MAT3X3
	| F32MAT3X4
	| F32MAT4X2
	| F32MAT4X3
	| F32MAT4X4
	| FLOAT64
	| F64VEC2
	| F64VEC3
	| F64VEC4
	| F64MAT2X2
	| F64MAT2X3
	| F64MAT2X4
	| F64MAT3X2
	| F64MAT3X3
	| F64MAT3X4
	| F64MAT4X2
	| F64MAT4X3
	| F64MAT4X4
	| INT8
	| I8VEC2
	| I8VEC3
	| I8VEC4
	| UINT8
	| UI8VEC2
	| UI8VEC3
	| UI8VEC4
	| INT16
	| I16VEC2
	| I16VEC3
	| I16VEC4
	| UINT16
	| UI16VEC2
	| UI16VEC3
	| UI16VEC4
	| INT32
	| I32VEC2
	| I32VEC3
	| I32VEC4
	| UINT32
	| UI32VEC2
	| UI32VEC3
	| UI32VEC4
	| INT64
	| I64VEC2
	| I64VEC3
	| I64VEC4
	| UINT64
	| UI64VEC2
	| UI64VEC3
	| UI64VEC4;

builtinTypeSpecifierFixed:
	VOID
	| ATOMIC_UINT
	| SAMPLER2D
	| SAMPLER3D
	| SAMPLERCUBE
	| SAMPLER2DSHADOW
	| SAMPLERCUBESHADOW
	| SAMPLER2DARRAY
	| SAMPLER2DARRAYSHADOW
	| SAMPLERCUBEARRAY
	| SAMPLERCUBEARRAYSHADOW
	| ISAMPLER2D
	| ISAMPLER3D
	| ISAMPLERCUBE
	| ISAMPLER2DARRAY
	| ISAMPLERCUBEARRAY
	| USAMPLER2D
	| USAMPLER3D
	| USAMPLERCUBE
	| USAMPLER2DARRAY
	| USAMPLERCUBEARRAY
	| SAMPLER1D
	| SAMPLER1DSHADOW
	| SAMPLER1DARRAY
	| SAMPLER1DARRAYSHADOW
	| ISAMPLER1D
	| ISAMPLER1DARRAY
	| USAMPLER1D
	| USAMPLER1DARRAY
	| SAMPLER2DRECT
	| SAMPLER2DRECTSHADOW
	| ISAMPLER2DRECT
	| USAMPLER2DRECT
	| SAMPLERBUFFER
	| ISAMPLERBUFFER
	| USAMPLERBUFFER
	| SAMPLER2DMS
	| ISAMPLER2DMS
	| USAMPLER2DMS
	| SAMPLER2DMSARRAY
	| ISAMPLER2DMSARRAY
	| USAMPLER2DMSARRAY
	| IMAGE2D
	| IIMAGE2D
	| UIMAGE2D
	| IMAGE3D
	| IIMAGE3D
	| UIMAGE3D
	| IMAGECUBE
	| IIMAGECUBE
	| UIMAGECUBE
	| IMAGEBUFFER
	| IIMAGEBUFFER
	| UIMAGEBUFFER
	| IMAGE1D
	| IIMAGE1D
	| UIMAGE1D
	| IMAGE1DARRAY
	| IIMAGE1DARRAY
	| UIMAGE1DARRAY
	| IMAGE2DRECT
	| IIMAGE2DRECT
	| UIMAGE2DRECT
	| IMAGE2DARRAY
	| IIMAGE2DARRAY
	| UIMAGE2DARRAY
	| IMAGECUBEARRAY
	| IIMAGECUBEARRAY
	| UIMAGECUBEARRAY
	| IMAGE2DMS
	| IIMAGE2DMS
	| UIMAGE2DMS
	| IMAGE2DMSARRAY
	| IIMAGE2DMSARRAY
	| UIMAGE2DMSARRAY;

structSpecifier: STRUCT IDENTIFIER? structBody;

structBody: LBRACE structMember+ RBRACE;

structMember:
	fullySpecifiedType structDeclarators += structDeclarator (
		COMMA structDeclarators += structDeclarator
	)* SEMICOLON;

structDeclarator: IDENTIFIER arraySpecifier?;

initializer:
	expression
	| LBRACE (
		initializers += initializer (COMMA initializers += initializer)* COMMA?
	)? RBRACE;

statement:
	compoundStatement
	| declarationStatement
	| expressionStatement
	| emptyStatement
	| selectionStatement
	| switchStatement
	| caseLabel
	| forStatement
	| whileStatement
	| doWhileStatement
	| jumpStatement
	| demoteStatement;

compoundStatement: LBRACE statement* RBRACE;

declarationStatement: declaration;

expressionStatement: expression SEMICOLON;

emptyStatement: SEMICOLON;

selectionStatement:
	attribute? IF LPAREN condition = expression RPAREN ifTrue = statement (
		ELSE ifFalse = statement
	)?;

iterationCondition:
	fullySpecifiedType name = IDENTIFIER ASSIGN_OP initializer;

switchStatement:
	attribute? SWITCH LPAREN condition = expression RPAREN compoundStatement;

caseLabel:
	CASE expression COLON	# valuedCaseLabel
	| DEFAULT COLON				# defaultCaseLabel;

whileStatement:
	attribute? WHILE LPAREN (
		condition = expression
		| initCondition = iterationCondition
	) RPAREN loopBody = statement;

doWhileStatement:
	attribute? DO loopBody = statement WHILE LPAREN condition = expression RPAREN SEMICOLON;

forStatement:
	attribute? FOR LPAREN (
		emptyStatement
		| expressionStatement
		| declarationStatement
	) (condition = expression | initCondition = iterationCondition)? SEMICOLON incrementer = expression? RPAREN
		loopBody = statement;

jumpStatement:
	CONTINUE SEMICOLON							# continueStatement
	| BREAK SEMICOLON								# breakStatement
	| RETURN expression? SEMICOLON	# returnStatement
	| DISCARD SEMICOLON							# discardStatement;

demoteStatement: DEMOTE SEMICOLON;
