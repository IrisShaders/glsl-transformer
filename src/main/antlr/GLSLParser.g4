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

options {
	tokenVocab = GLSLLexer;
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
	NR PRAGMA (
		(PRAGMA_DEBUG | PRAGMA_OPTIMIZE) (ON | OFF)
		| PRAGMA_INVARIANT ALL
	) NR_EOL;

extensionStatement:
	NR EXTENSION NR_IDENTIFIER NR_COLON extensionState NR_EOL;

extensionState: REQUIRE | ENABLE | WARN | DISABLE;

layoutDefaults:
	layoutQualifier layoutModes SEMICOLON;

layoutModes: UNIFORM | IN | OUT | BUFFER;

functionDefinition:
	functionPrototype compoundStatement;

variableIdentifier: IDENTIFIER;

primaryExpression:
	variableIdentifier
	| INTCONSTANT
	| UINTCONSTANT
	| FLOATCONSTANT
	| BOOLCONSTANT
	| DOUBLECONSTANT
	| LPAREN expression RPAREN;

postfixExpression:
	primaryExpression
	| postfixExpression (
		LBRACKET expression RBRACKET
		| DOT IDENTIFIER
		| INC_OP
		| DEC_OP
	)
	| functionCall;

functionCall:
	functionIdentifier (
		LPAREN (
			| VOID
			| assignmentExpression (
				COMMA assignmentExpression
			)*) RPAREN
	);

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

functionHeader:
	fullySpecifiedType variableIdentifier;

parameterDeclarator:
	typeSpecifier IDENTIFIER
	| typeSpecifier IDENTIFIER arraySpecifier;

parameterDeclaration:
	typeQualifier? parameterDeclarator
	| fullySpecifiedType;

//part of EXT_subgroup_uniform_control_flow
attribute:
	LBRACKET LBRACKET singleAttribute (
		COMMA singleAttribute
	)* RBRACKET RBRACKET;

singleAttribute:
	IDENTIFIER (LPAREN constantExpression RPAREN)?;

initDeclaratorList:
	fullySpecifiedType declarationMember? (
		COMMA declarationMember
	)*;

declarationMember:
	IDENTIFIER arraySpecifier? (
		ASSIGN_OP initializer
	)?;

fullySpecifiedType: typeQualifier? typeSpecifier;

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
	| SUBROUTINE (LPAREN typeNameList RPAREN)?;

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
	builtinTypeSpecifierNonarray
	| structSpecifier
	| IDENTIFIER;

builtinTypeSpecifierNonarray:
	VOID
	| FLOAT
	| DOUBLE
	| INT
	| UINT
	| BOOL
	| VEC2
	| VEC3
	| VEC4
	| DVEC2
	| DVEC3
	| DVEC4
	| BVEC2
	| BVEC3
	| BVEC4
	| IVEC2
	| IVEC3
	| IVEC4
	| UVEC2
	| UVEC3
	| UVEC4
	| MAT2X2 //mat2, mat3, mat4 handled in the lexer
	| MAT2X3
	| MAT2X4
	| MAT3X2
	| MAT3X3
	| MAT3X4
	| MAT4X2
	| MAT4X3
	| MAT4X4
	| DMAT2X2 //dmat2, dmat3, dmat4 handled in the lexer
	| DMAT2X3
	| DMAT2X4
	| DMAT3X2
	| DMAT3X3
	| DMAT3X4
	| DMAT4X2
	| DMAT4X3
	| DMAT4X4
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
	IF LPAREN expression RPAREN statement (
		ELSE statement
	)?;

condition:
	expression
	| fullySpecifiedType IDENTIFIER ASSIGN_OP initializer;

switchStatement:
	SWITCH LPAREN expression RPAREN LBRACE statement* RBRACE;

caseLabel: (CASE expression | DEFAULT) COLON;

whileStatement:
	WHILE LPAREN condition RPAREN statement;

doWhileStatement:
	DO statement WHILE LPAREN expression RPAREN SEMICOLON;

forStatement:
	FOR LPAREN (
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
