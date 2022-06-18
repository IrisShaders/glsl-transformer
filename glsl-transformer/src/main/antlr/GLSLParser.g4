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
		| NR_COMPATABILITY
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
	NR PRAGMA NR_STDGL? (
		type = (PRAGMA_DEBUG | PRAGMA_OPTIMIZE) NR_LPAREN state = (
			NR_ON
			| NR_OFF
		) RPAREN
		| type = PRAGMA_INVARIANT NR_LPAREN state = NR_ALL NR_RPAREN
		| type = NR_IDENTIFIER
	) NR_EOL;

extensionStatement:
	NR EXTENSION extensionName = NR_IDENTIFIER (
		NR_COLON extensionState = (
			NR_REQUIRE
			| NR_ENABLE
			| NR_WARN
			| NR_DISABLE
		)
	)? NR_EOL;

layoutDefaults:
	layoutQualifier layoutMode = (UNIFORM | IN | OUT | BUFFER) SEMICOLON;

functionDefinition: functionPrototype compoundStatement;

variableIdentifier: IDENTIFIER;

functionCall: functionIdentifier callParameterList;

methodCall: variableIdentifier callParameterList;

//assignment expressions
callParameterList:
	LPAREN ( | VOID | expression (COMMA expression)*) RPAREN;

//Note: diverges from the spec by not allowing a prefixExpression as an identifier
//array-type function identfiers are handled by typeSpecifier
functionIdentifier: typeSpecifier | variableIdentifier;

//Note: diverges from the spec by explicity adding a method call instead of handling it through postfixExpression in functionIdentifier
expression:
	variableIdentifier # referenceExpression
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
	| operand = expression DOT methodCall											# methodCallExpression
	| functionCall																						# functionCallExpression
	| operand = expression DOT IDENTIFIER											# memberAccessExpression
	| operand = expression op = (INC_OP | DEC_OP)							# postfixExpression
	| <assoc = right> op = (
		INC_OP
		| DEC_OP
		| PLUS_OP
		| MINUS_OP
		| NOT_OP
		| BNEG_OP
	) operand = expression																											# prefixExpression
	| left = expression op = (TIMES_OP | DIV_OP | MOD_OP) right = expression		# multiplicativeExpression
	| left = expression op = (PLUS_OP | MINUS_OP) right = expression						# additiveExpression
	| left = expression op = (LEFT_OP | RIGHT_OP) right = expression						# shiftExpression
	| left = expression op = (LT_OP | GT_OP | LE_OP | GE_OP) right = expression	# relationalExpression
	| left = expression op = (EQ_OP | NE_OP) right = expression									# equalityExpression
	| left = expression op = BAND_OP right = expression													# bitwiseAndExpression
	| left = expression op = BXOR_OP right = expression													# bitwiseExclusiveOrExpression
	| left = expression op = BOR_OP right = expression													# bitwiseInclusiveOrExpression
	| left = expression op = AND_OP right = expression													# logicalAndExpression
	| left = expression op = XOR_OP right = expression													# logicalExclusiveOrExpression
	| left = expression op = OR_OP right = expression														# logicalInclusiveOrExpression
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
	functionPrototype SEMICOLON															# functionDeclaration
	| initDeclaratorList SEMICOLON													# typeAndInitDeclaration
	| PRECISION precisionQualifier typeSpecifier SEMICOLON	# precisionDeclaration
	| typeQualifier blockName = IDENTIFIER structBody (
		variableName = IDENTIFIER arraySpecifier?
	)? SEMICOLON # interfaceBlockDeclaration
	| typeQualifier (
		variableNames += IDENTIFIER (COMMA variableNames += IDENTIFIER)*
	)? SEMICOLON # variableDeclaration;

//allows for EXT_subgroup_uniform_control_flow
functionPrototype:
	attribute? functionHeader LPAREN functionParameterList RPAREN attribute?;

functionParameterList: (
		parameterDeclaration (COMMA parameterDeclaration)*
	)?;

functionHeader: fullySpecifiedType IDENTIFIER;

parameterDeclarator:
	typeSpecifier parameterName = IDENTIFIER arraySpecifier?;

parameterDeclaration:
	typeQualifier? parameterDeclarator
	| fullySpecifiedType;

//part of GL_EXT_control_flow_attributes
attribute:
	LBRACKET LBRACKET attributes += singleAttribute (
		COMMA attributes += singleAttribute
	)* RBRACKET RBRACKET;

//constant expression
singleAttribute:
	(IDENTIFIER COLON COLON)? IDENTIFIER (LPAREN expression RPAREN)?;

initDeclaratorList:
	fullySpecifiedType (
		declarationMembers += declarationMember (
			COMMA declarationMembers += declarationMember
		)*
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
	| SUBROUTINE (LPAREN typeNameList RPAREN)?
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
typeNameList: names += IDENTIFIER (COMMA names += IDENTIFIER)*;

typeSpecifier: typeSpecifierNonarray arraySpecifier?;

//needs duplicated rule parts like this or it becomes mutually left-recursive
//constant expressions
arraySpecifier: arraySpecifierSegment+;
arraySpecifierSegment: (LBRACKET expression? RBRACKET);

//TYPE_NAME instead of IDENTIFIER in the spec
typeSpecifierNonarray:
	builtinTypeSpecifierFixed				# builtinType
	| builtinTypeSpecifierParseable	# builtinType
	| structSpecifier								# structSpecifierType
	| IDENTIFIER										# referencedType;

builtinTypeSpecifierParseable:
	BOOL				# booleanType
	| BVEC2			# booleanVectorType
	| BVEC3			# booleanVectorType
	| BVEC4			# booleanVectorType
	| FLOAT16		# floatType
	| F16VEC2		# floatVectorType
	| F16VEC3		# floatVectorType
	| F16VEC4		# floatVectorType
	| F16MAT2X2	# floatMatrixType
	| F16MAT2X3	# floatMatrixType
	| F16MAT2X4	# floatMatrixType
	| F16MAT3X2	# floatMatrixType
	| F16MAT3X3	# floatMatrixType
	| F16MAT3X4	# floatMatrixType
	| F16MAT4X2	# floatMatrixType
	| F16MAT4X3	# floatMatrixType
	| F16MAT4X4	# floatMatrixType
	| FLOAT32		# floatType
	| F32VEC2		# floatVectorType
	| F32VEC3		# floatVectorType
	| F32VEC4		# floatVectorType
	| F32MAT2X2	# floatMatrixType
	| F32MAT2X3	# floatMatrixType
	| F32MAT2X4	# floatMatrixType
	| F32MAT3X2	# floatMatrixType
	| F32MAT3X3	# floatMatrixType
	| F32MAT3X4	# floatMatrixType
	| F32MAT4X2	# floatMatrixType
	| F32MAT4X3	# floatMatrixType
	| F32MAT4X4	# floatMatrixType
	| FLOAT64		# floatType
	| F64VEC2		# floatVectorType
	| F64VEC3		# floatVectorType
	| F64VEC4		# floatVectorType
	| F64MAT2X2	# floatMatrixType
	| F64MAT2X3	# floatMatrixType
	| F64MAT2X4	# floatMatrixType
	| F64MAT3X2	# floatMatrixType
	| F64MAT3X3	# floatMatrixType
	| F64MAT3X4	# floatMatrixType
	| F64MAT4X2	# floatMatrixType
	| F64MAT4X3	# floatMatrixType
	| F64MAT4X4	# floatMatrixType
	| INT8			# integerType
	| I8VEC2		# integerVectorType
	| I8VEC3		# integerVectorType
	| I8VEC4		# integerVectorType
	| UINT8			# integerType
	| UI8VEC2		# integerVectorType
	| UI8VEC3		# integerVectorType
	| UI8VEC4		# integerVectorType
	| INT16			# integerType
	| I16VEC2		# integerVectorType
	| I16VEC3		# integerVectorType
	| I16VEC4		# integerVectorType
	| UINT16		# integerType
	| UI16VEC2	# integerVectorType
	| UI16VEC3	# integerVectorType
	| UI16VEC4	# integerVectorType
	| INT32			# integerType
	| I32VEC2		# integerVectorType
	| I32VEC3		# integerVectorType
	| I32VEC4		# integerVectorType
	| UINT32		# integerType
	| UI32VEC2	# integerVectorType
	| UI32VEC3	# integerVectorType
	| UI32VEC4	# integerVectorType
	| INT64			# integerType
	| I64VEC2		# integerVectorType
	| I64VEC3		# integerVectorType
	| I64VEC4		# integerVectorType
	| UINT64		# integerType
	| UI64VEC2	# integerVectorType
	| UI64VEC3	# integerVectorType
	| UI64VEC4	# integerVectorType;

builtinTypeSpecifierFixed:
	VOID											# voidType
	| ATOMIC_UINT							# atomicUnitType
	| SAMPLER2D								# samplerType
	| SAMPLER3D								# samplerType
	| SAMPLERCUBE							# samplerType
	| SAMPLER2DSHADOW					# samplerType
	| SAMPLERCUBESHADOW				# samplerType
	| SAMPLER2DARRAY					# samplerType
	| SAMPLER2DARRAYSHADOW		# samplerType
	| SAMPLERCUBEARRAY				# samplerType
	| SAMPLERCUBEARRAYSHADOW	# samplerType
	| ISAMPLER2D							# samplerType
	| ISAMPLER3D							# samplerType
	| ISAMPLERCUBE						# samplerType
	| ISAMPLER2DARRAY					# samplerType
	| ISAMPLERCUBEARRAY				# samplerType
	| USAMPLER2D							# samplerType
	| USAMPLER3D							# samplerType
	| USAMPLERCUBE						# samplerType
	| USAMPLER2DARRAY					# samplerType
	| USAMPLERCUBEARRAY				# samplerType
	| SAMPLER1D								# samplerType
	| SAMPLER1DSHADOW					# samplerType
	| SAMPLER1DARRAY					# samplerType
	| SAMPLER1DARRAYSHADOW		# samplerType
	| ISAMPLER1D							# samplerType
	| ISAMPLER1DARRAY					# samplerType
	| USAMPLER1D							# samplerType
	| USAMPLER1DARRAY					# samplerType
	| SAMPLER2DRECT						# samplerType
	| SAMPLER2DRECTSHADOW			# samplerType
	| ISAMPLER2DRECT					# samplerType
	| USAMPLER2DRECT					# samplerType
	| SAMPLERBUFFER						# samplerType
	| ISAMPLERBUFFER					# samplerType
	| USAMPLERBUFFER					# samplerType
	| SAMPLER2DMS							# samplerType
	| ISAMPLER2DMS						# samplerType
	| USAMPLER2DMS						# samplerType
	| SAMPLER2DMSARRAY				# samplerType
	| ISAMPLER2DMSARRAY				# samplerType
	| USAMPLER2DMSARRAY				# samplerType
	| IMAGE2D									# imageType
	| IIMAGE2D								# imageType
	| UIMAGE2D								# imageType
	| IMAGE3D									# imageType
	| IIMAGE3D								# imageType
	| UIMAGE3D								# imageType
	| IMAGECUBE								# imageType
	| IIMAGECUBE							# imageType
	| UIMAGECUBE							# imageType
	| IMAGEBUFFER							# imageType
	| IIMAGEBUFFER						# imageType
	| UIMAGEBUFFER						# imageType
	| IMAGE1D									# imageType
	| IIMAGE1D								# imageType
	| UIMAGE1D								# imageType
	| IMAGE1DARRAY						# imageType
	| IIMAGE1DARRAY						# imageType
	| UIMAGE1DARRAY						# imageType
	| IMAGE2DRECT							# imageType
	| IIMAGE2DRECT						# imageType
	| UIMAGE2DRECT						# imageType
	| IMAGE2DARRAY						# imageType
	| IIMAGE2DARRAY						# imageType
	| UIMAGE2DARRAY						# imageType
	| IMAGECUBEARRAY					# imageType
	| IIMAGECUBEARRAY					# imageType
	| UIMAGECUBEARRAY					# imageType
	| IMAGE2DMS								# imageType
	| IIMAGE2DMS							# imageType
	| UIMAGE2DMS							# imageType
	| IMAGE2DMSARRAY					# imageType
	| IIMAGE2DMSARRAY					# imageType
	| UIMAGE2DMSARRAY					# imageType;

structSpecifier: STRUCT IDENTIFIER? structBody;

structBody: LBRACE structMember+ RBRACE;

structMember:
	fullySpecifiedType structDeclarators += structDeclarator (
		COMMA structDeclarators += structDeclarator
	)* SEMICOLON;

structDeclarator: IDENTIFIER arraySpecifier?;

//assignment epxression
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
	attribute? IF LPAREN expression RPAREN ifTrue = statement (
		ELSE ifFalse = statement
	)?;

iterationCondition:
	expression
	| fullySpecifiedType IDENTIFIER ASSIGN_OP initializer;

switchStatement:
	attribute? SWITCH LPAREN expression RPAREN compoundStatement;

caseLabel:
	CASE expression COLON	# valuedCaseLabel
	| DEFAULT COLON				# defaultCaseLabel;

whileStatement:
	attribute? WHILE LPAREN iterationCondition RPAREN loopBody = statement;

doWhileStatement:
	attribute? DO loopBody = statement WHILE LPAREN expression RPAREN SEMICOLON;

forStatement:
	attribute? FOR LPAREN (
		emptyStatement
		| expressionStatement
		| declarationStatement
	) iterationCondition? SEMICOLON expression? RPAREN loopBody = statement;

jumpStatement:
	CONTINUE SEMICOLON							# continueStatement
	| BREAK SEMICOLON								# breakStatement
	| RETURN expression? SEMICOLON	# returnStatement
	| DISCARD SEMICOLON							# discardStatement;

demoteStatement: DEMOTE SEMICOLON;
