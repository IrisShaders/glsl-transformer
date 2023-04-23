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
import io.github.douira.glsl_transformer.parser.ExtendedParser;
}

options {
	tokenVocab = GLSLLexer;
	superClass = ExtendedParser;
}

//the root rule
translationUnit: versionStatement? externalDeclaration* EOF;

//allows for EXT_null_initializer
versionStatement:
	NR NR_VERSION version = (
		NR_GLSL_110
		| NR_GLSL_120
		| NR_GLSLES_100
		| NR_GLSL_130
		| NR_GLSL_140
		| NR_GLSL_150
		| NR_GLSL_330
		| NR_GLSLES_300
		| NR_GLSLES_310
		| NR_GLSLES_320
		| NR_GLSL_400
		| NR_GLSL_410
		| NR_GLSL_420
		| NR_GLSL_430
		| NR_GLSL_440
		| NR_GLSL_450
		| NR_GLSL_460
	) profile = (NR_CORE | NR_COMPATIBILITY | NR_ES)? NR_EOL;

externalDeclaration:
	functionDefinition
	| declaration
	| pragmaDirective
	| extensionDirective
	| customDirective
	| includeDirective
	| layoutDefaults
	| emptyDeclaration;

emptyDeclaration: SEMICOLON;

pragmaDirective:
	NR NR_PRAGMA stdGL = NR_STDGL? (
		type = NR_IDENTIFIER
		| type = (NR_PRAGMA_DEBUG | NR_PRAGMA_OPTIMIZE) NR_LPAREN state = (
			NR_ON
			| NR_OFF
		) NR_RPAREN
		| type = NR_PRAGMA_INVARIANT NR_LPAREN state = NR_ALL NR_RPAREN
	) NR_EOL;

extensionDirective:
	NR NR_EXTENSION extensionName = NR_IDENTIFIER (
		NR_COLON extensionBehavior = (
			NR_REQUIRE
			| NR_ENABLE
			| NR_WARN
			| NR_DISABLE
		)
	)? NR_EOL;

customDirective: NR NR_CUSTOM content = C_CONTENT? C_EOL;

includeDirective:
	NR NR_INCLUDE (
		NR_STRING_START content = S_CONTENT? S_STRING_END
		| angleStart = NR_STRING_START_ANGLE content = S_CONTENT_ANGLE? S_STRING_END_ANGLE
	) NR_EOL;

layoutDefaults:
	layoutQualifier layoutMode = (UNIFORM | IN | OUT | BUFFER) SEMICOLON;

functionDefinition: functionPrototype compoundStatement;

//Note: diverges from the spec by explicity adding a method call instead of handling it through postfixExpression in functionIdentifier
finiteExpression:
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
	) # literalExpression
	//only the grouping expression allows the sequence expression since the sequence expression
	//has the lowest precendence and not putting it in parentheses would simply create a
	//sequence expression around the whole expression
	| LPAREN value = expression RPAREN															# groupingExpression
	| left = finiteExpression LBRACKET right = expression RBRACKET	# arrayAccessExpression
	| operand = finiteExpression DOT_LENGTH_METHOD_CALL							# lengthAccessExpression
	//Note: diverges from the spec by not allowing a prefixExpression as an identifier
	//array-type function identfiers are handled by typeSpecifier
	| (IDENTIFIER | typeSpecifier) LPAREN (
		| VOID
		| parameters += finiteExpression (
			COMMA parameters += finiteExpression
		)*
	) RPAREN																							# functionCallExpression
	| operand = finiteExpression DOT member = IDENTIFIER	# memberAccessExpression
	| operand = finiteExpression op = (INC_OP | DEC_OP)		# postfixExpression
	| <assoc = right> op = (
		INC_OP
		| DEC_OP
		| PLUS_OP
		| MINUS_OP
		| LOGICAL_NOT_OP
		| BITWISE_NEG_OP
	) operand = finiteExpression																													# prefixExpression
	| left = finiteExpression op = (TIMES_OP | DIV_OP | MOD_OP) right = finiteExpression	#
		multiplicativeExpression
	| left = finiteExpression op = (PLUS_OP | MINUS_OP) right = finiteExpression						# additiveExpression
	| left = finiteExpression op = (LEFT_OP | RIGHT_OP) right = finiteExpression						# shiftExpression
	| left = finiteExpression op = (LT_OP | GT_OP | LE_OP | GE_OP) right = finiteExpression	#
		relationalExpression
	| left = finiteExpression op = (EQ_OP | NE_OP) right = finiteExpression	# equalityExpression
	| left = finiteExpression op = BITWISE_AND_OP right = finiteExpression	# bitwiseAndExpression
	| left = finiteExpression op = BITWISE_XOR_OP right = finiteExpression	# bitwiseExclusiveOrExpression
	| left = finiteExpression op = BITWISE_OR_OP right = finiteExpression		# bitwiseInclusiveOrExpression
	| left = finiteExpression op = LOGICAL_AND_OP right = finiteExpression	# logicalAndExpression
	| left = finiteExpression op = LOGICAL_XOR_OP right = finiteExpression	# logicalExclusiveOrExpression
	| left = finiteExpression op = LOGICAL_OR_OP right = finiteExpression		# logicalInclusiveOrExpression
	| <assoc = right> condition = finiteExpression QUERY_OP trueAlternative = finiteExpression COLON
		falseAlternative = finiteExpression # conditionalExpression
	| <assoc = right> left = finiteExpression op = (
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
	) right = finiteExpression # assignmentExpression;

expression:
	items += finiteExpression (COMMA items += finiteExpression)*;

declaration:
	functionPrototype SEMICOLON # functionDeclaration
	| typeQualifier blockName = IDENTIFIER structBody (
		variableName = IDENTIFIER arraySpecifier?
	)? SEMICOLON # interfaceBlockDeclaration
	| typeQualifier (
		variableNames += IDENTIFIER (COMMA variableNames += IDENTIFIER)*
	)? SEMICOLON																						# variableDeclaration
	| PRECISION precisionQualifier typeSpecifier SEMICOLON	# precisionDeclaration
	| fullySpecifiedType (
		declarationMembers += declarationMember (
			COMMA declarationMembers += declarationMember
		)*
	)? SEMICOLON # typeAndInitDeclaration;

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
	| NONPRIVATE
	| RAY_PAYLOAD_EXT
	| RAY_PAYLOAD_IN_EXT
	| HIT_ATTRIBUTE_EXT
	| CALLABLE_DATA_EXT
	| CALLABLE_DATA_IN_EXT;

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
		IDENTIFIER
		| fixedTypeSpecifier
		| numericTypeSpecifier
		| structSpecifier
	) arraySpecifier?;

//needs duplicated rule parts like this or it becomes mutually left-recursive
//constant expressions
arraySpecifier: arraySpecifierSegment+;
arraySpecifierSegment: (LBRACKET expression? RBRACKET);

numericTypeSpecifier:
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
	| U8VEC2
	| U8VEC3
	| U8VEC4
	| INT16
	| I16VEC2
	| I16VEC3
	| I16VEC4
	| UINT16
	| U16VEC2
	| U16VEC3
	| U16VEC4
	| INT32
	| I32VEC2
	| I32VEC3
	| I32VEC4
	| UINT32
	| U32VEC2
	| U32VEC3
	| U32VEC4
	| INT64
	| I64VEC2
	| I64VEC3
	| I64VEC4
	| UINT64
	| U64VEC2
	| U64VEC3
	| U64VEC4;

fixedTypeSpecifier:
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
	| UIMAGE2DMSARRAY
	| ACCELERATION_STRUCTURE_EXT;

structSpecifier: STRUCT IDENTIFIER? structBody;

structBody: LBRACE structMember+ RBRACE;

structMember:
	fullySpecifiedType structDeclarators += structDeclarator (
		COMMA structDeclarators += structDeclarator
	)* SEMICOLON;

structDeclarator: IDENTIFIER arraySpecifier?;

initializer:
	finiteExpression
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
	CONTINUE SEMICOLON									# continueStatement
	| BREAK SEMICOLON										# breakStatement
	| RETURN expression? SEMICOLON			# returnStatement
	| DISCARD SEMICOLON									# discardStatement
	| IGNORE_INTERSECTION_EXT SEMICOLON	# ignoreIntersectionStatement
	| TERMINATE_RAY_EXT SEMICOLON				# terminateRayStatement;

demoteStatement: DEMOTE SEMICOLON;
