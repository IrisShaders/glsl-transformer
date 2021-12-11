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
 * Unless required by aNRlicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

parser grammar GLSLParser;

@header {
import io.github.douira.glsl_transformer.generic.ExtendedContext;
}

options {
	tokenVocab = GLSLLexer;
	contextSuperClass = ExtendedContext;
}

//the root rule
translationUnit:
	versionStatement externalDeclaration* EOF;

//allows for EXT_null_initializer
versionStatement:
	(
		NR VERSION NR_INTCONSTANT NR_IDENTIFIER? NR_EOL
	)?;

externalDeclaration:
	functionDefinition
	| declaration
	| pragmaStatement
	| extensionStatement
	| layoutDefaults
	| SEMICOLON;

pragmaStatement:
	NR PRAGMA NR_STDGL? (
		(PRAGMA_DEBUG | PRAGMA_OPTIMIZE) NR_LPAREN (
			NR_ON
			| NR_OFF
		) RPAREN
		| PRAGMA_INVARIANT NR_LPAREN NR_ALL NR_RPAREN
		| NR_IDENTIFIER
	) NR_EOL;

extensionStatement:
	NR EXTENSION NR_IDENTIFIER (
		NR_COLON extensionState
	)? NR_EOL;

extensionState:
	NR_REQUIRE
	| NR_ENABLE
	| NR_WARN
	| NR_DISABLE;

layoutDefaults:
	layoutQualifier layoutModes SEMICOLON;

layoutModes: UNIFORM | IN | OUT | BUFFER;

functionDefinition:
	functionPrototype compoundStatement;

variableIdentifier: IDENTIFIER;

primaryExpression:
	variableIdentifier
	| INT16CONSTANT
	| UINT16CONSTANT
	| INT32CONSTANT
	| UINT32CONSTANT
	| INT64CONSTANT
	| UINT64CONSTANT
	| FLOAT16CONSTANT
	| FLOAT32CONSTANT
	| FLOAT64CONSTANT
	| BOOLCONSTANT
	| LPAREN expression RPAREN;

//Note: diverges from the spec by explicity adding a method call instead of handling it through postfixExpression in functionIdentifier
postfixExpression:
	primaryExpression
	| postfixExpression (
		LBRACKET expression RBRACKET
		| DOT IDENTIFIER
		| DOT methodCall
		| INC_OP
		| DEC_OP
	)
	| functionCall;

functionCall: functionIdentifier callParameterList;

methodCall: variableIdentifier callParameterList;

callParameterList:
	LPAREN (
		| VOID
		| assignmentExpression (
			COMMA assignmentExpression
		)*
	) RPAREN;

//Note: diverges from the spec by not allowing a prefixExpression as an identifier
//array-type function identfiers are handled by typeSpecifier
functionIdentifier:
	typeSpecifier
	| variableIdentifier;

unaryExpression:
	postfixExpression
	| unaryOperator unaryExpression;

unaryOperator:
	INC_OP
	| DEC_OP
	| PLUS_OP
	| MINUS_OP
	| NOT_OP
	| BNEG_OP;

//this weird nested structure is necessary to ensure correct operator precendence
multiplicativeExpression:
	unaryExpression (
		(TIMES_OP | DIV_OP | MOD_OP) unaryExpression
	)*;

additiveExpression:
	multiplicativeExpression (
		(PLUS_OP | MINUS_OP) multiplicativeExpression
	)*;

shiftExpression:
	additiveExpression (
		(LEFT_OP | RIGHT_OP) additiveExpression
	)*;

relationalExpression:
	shiftExpression (
		(LT_OP | GT_OP | LE_OP | GE_OP) shiftExpression
	)*;

equalityExpression:
	relationalExpression (
		(EQ_OP | NE_OP) relationalExpression
	)*;

andExpression:
	equalityExpression (BAND_OP equalityExpression)*;

exclusiveOrExpression:
	andExpression (BXOR_OP andExpression)*;

inclusiveOrExpression:
	exclusiveOrExpression (
		BOR_OP exclusiveOrExpression
	)*;

logicalAndExpression:
	inclusiveOrExpression (
		AND_OP inclusiveOrExpression
	)*;

logicalXorExpression:
	logicalAndExpression (
		XOR_OP logicalAndExpression
	)*;

logicalOrExpression:
	logicalXorExpression (
		OR_OP logicalXorExpression
	)*;

conditionalExpression:
	logicalOrExpression (
		QUERY_OP expression COLON assignmentExpression
	)*;

assignmentExpression:
	conditionalExpression
	| unaryExpression assignmentOperator assignmentExpression;

assignmentOperator:
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
	| OR_ASSIGN;

expression:
	assignmentExpression (
		COMMA assignmentExpression
	)*;

constantExpression: conditionalExpression;

declaration:
	functionPrototype SEMICOLON
	| initDeclaratorList SEMICOLON
	| PRECISION precisionQualifier typeSpecifier SEMICOLON
	| typeQualifier IDENTIFIER LBRACE structDeclarationList RBRACE (
		IDENTIFIER arraySpecifier?
	)? SEMICOLON
	| typeQualifier (IDENTIFIER (COMMA IDENTIFIER)*)? SEMICOLON;

//allows for EXT_subgroup_uniform_control_flow
functionPrototype:
	attribute? functionHeader LPAREN functionParameterList RPAREN attribute?;

functionParameterList:
	(
		parameterDeclaration (
			COMMA parameterDeclaration
		)*
	)?;

functionHeader: fullySpecifiedType IDENTIFIER;

parameterDeclarator:
	typeSpecifier IDENTIFIER
	| typeSpecifier IDENTIFIER arraySpecifier;

parameterDeclaration:
	typeQualifier? parameterDeclarator
	| fullySpecifiedType;

//part of GL_EXT_control_flow_attributes
attribute:
	LBRACKET LBRACKET singleAttribute (
		COMMA singleAttribute
	)* RBRACKET RBRACKET;

singleAttribute:
	(IDENTIFIER COLON COLON)? IDENTIFIER (
		LPAREN constantExpression RPAREN
	)?;

initDeclaratorList:
	fullySpecifiedType declarationMemberList;

declarationMemberList:
	declarationMember? (COMMA declarationMember)*;

declarationMember:
	IDENTIFIER arraySpecifier? (
		ASSIGN_OP initializer
	)?;

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
	| COHERENT
	| VOLATILE
	| RESTRICT
	| READONLY
	| WRITEONLY
	| SUBROUTINE (LPAREN typeNameList RPAREN)?
	| DEVICECOHERENT
	| QUEUEFAMILYCOHERENT
	| WORKGROUPCOHERENT
	| SUBGROUPCOHERENT
	| NONPRIVATE;

layoutQualifier:
	LAYOUT LPAREN layoutQualifierId (
		COMMA layoutQualifierId
	)* RPAREN;

layoutQualifierId:
	IDENTIFIER (ASSIGN_OP constantExpression)?
	| SHARED;

precisionQualifier: HIGHP | MEDIUMP | LOWP;

interpolationQualifier:
	SMOOTH
	| FLAT
	| NOPERSPECTIVE;

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
typeNameList: IDENTIFIER (COMMA IDENTIFIER)*;

typeSpecifier:
	typeSpecifierNonarray arraySpecifier?;

//needs duplicated rule parts like this or it becomes mutually left-recursive
arraySpecifier:
	arraySpecifier LBRACKET constantExpression? RBRACKET
	| LBRACKET constantExpression? RBRACKET;

//TYPE_NAME instead of IDENTIFIER in the spec
typeSpecifierNonarray:
	builtinTypeSpecifierFixed
	| builtinTypeSpecifierParseable
	| structSpecifier
	| IDENTIFIER;

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

structSpecifier:
	STRUCT IDENTIFIER? LBRACE structDeclarationList RBRACE;

structDeclarationList: structDeclaration+;

structDeclaration:
	fullySpecifiedType structDeclaratorList SEMICOLON;

structDeclaratorList:
	structDeclarator (COMMA structDeclarator)*;

structDeclarator: IDENTIFIER arraySpecifier?;

initializer:
	assignmentExpression
	| LBRACE (
		initializer (COMMA initializer)* COMMA?
	)? RBRACE;

statement: compoundStatement | simpleStatement;

simpleStatement:
	declarationStatement
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
	attribute? IF LPAREN expression RPAREN statement (
		ELSE statement
	)?;

condition:
	expression
	| fullySpecifiedType IDENTIFIER ASSIGN_OP initializer;

switchStatement:
	attribute? SWITCH LPAREN expression RPAREN LBRACE statement* RBRACE;

caseLabel: (CASE expression | DEFAULT) COLON;

whileStatement:
	attribute? WHILE LPAREN condition RPAREN statement;

doWhileStatement:
	attribute? DO statement WHILE LPAREN expression RPAREN SEMICOLON;

forStatement:
	attribute? FOR LPAREN (
		emptyStatement
		| expressionStatement
		| declarationStatement
	) condition? SEMICOLON expression? RPAREN statement;

jumpStatement: (
		CONTINUE
		| BREAK
		| RETURN expression?
		| DISCARD //fragment shader only
	) SEMICOLON;

demoteStatement: DEMOTE SEMICOLON;
