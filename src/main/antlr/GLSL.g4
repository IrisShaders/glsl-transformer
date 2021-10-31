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

parser grammar GLSL;

options {
	tokenVocab = GLSLLexer;
}

//the root rule
translation_unit:
	version_statement external_declaration_list;

version_statement:
	| VERSION INTCONSTANT EOL
	| VERSION INTCONSTANT IDENTIFIER EOL;

external_declaration_list: external_declaration+;

external_declaration:
	function_definition
	| declaration
	| pragma_statement
	| extension_statement
	| layout_defaults
	| SEMICOLON;

pragma_statement:
	PRAGMA_DEBUG_ON EOL
	| PRAGMA_DEBUG_OFF EOL
	| PRAGMA_OPTIMIZE_ON EOL
	| PRAGMA_OPTIMIZE_OFF EOL
	| PRAGMA_INVARIANT_ALL EOL;

extension_statement:
	EXTENSION IDENTIFIER COLON extension_state EOL;

extension_state: REQUIRE | ENABLE | WARN | DISABLE;

layout_defaults:
	layout_qualifier layout_modes SEMICOLON;

layout_modes: UNIFORM | IN | OUT | BUFFER;

function_definition:
	function_prototype compound_statement;

variable_identifier: IDENTIFIER;

primary_expression:
	variable_identifier
	| INTCONSTANT
	| UINTCONSTANT
	| FLOATCONSTANT
	| BOOLCONSTANT
	| DOUBLECONSTANT
	| LPAREN expression RPAREN;

postfix_expression:
	primary_expression
	| postfix_expression (
		LBRACKET expression RBRACKET
		| DOT IDENTIFIER
		| INC_OP
		| DEC_OP
	)
	| function_call;

function_call:
	function_identifier (
		VOID? LPAREN RPAREN
		| LPAREN function_call_parameter_list RPAREN
	);

function_call_parameter_list:
	assignment_expression (
		COMMA assignment_expression
	)*;

function_identifier:
	builtin_type_specifier_nonarray
	| variable_identifier;

unary_expression:
	postfix_expression
	| unary_operator unary_expression;

unary_operator:
	INC_OP
	| DEC_OP
	| PLUS_OP
	| MINUS_OP
	| NOT_OP
	| BNEG_OP;

//this weird nested structure is necessary to ensure correct operator precendence
multiplicative_expression:
	unary_expression (
		(TIMES_OP | DIV_OP | MOD_OP) unary_expression
	)*;

additive_expression:
	multiplicative_expression (
		(PLUS_OP | MINUS_OP) multiplicative_expression
	)*;

shift_expression:
	additive_expression (
		(LEFT_OP | RIGHT_OP) additive_expression
	)*;

relational_expression:
	shift_expression (
		(LT_OP | GT_OP | LE_OP | GE_OP) shift_expression
	)*;

equality_expression:
	relational_expression (
		(EQ_OP | NE_OP) relational_expression
	)*;

and_expression:
	equality_expression (
		BAND_OP equality_expression
	)*;

exclusive_or_expression:
	and_expression (BXOR_OP and_expression)*;

inclusive_or_expression:
	exclusive_or_expression (
		BOR_OP exclusive_or_expression
	)*;

logical_and_expression:
	inclusive_or_expression (
		AND_OP inclusive_or_expression
	)*;

logical_xor_expression:
	logical_and_expression (
		XOR_OP logical_and_expression
	)*;

logical_or_expression:
	logical_xor_expression (
		OR_OP logical_xor_expression
	)*;

conditional_expression:
	logical_or_expression (
		QUERY_OP expression COLON assignment_expression
	)*;

assignment_expression:
	conditional_expression
	| unary_expression assignment_operator assignment_expression;

assignment_operator:
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
	assignment_expression (
		COMMA assignment_expression
	)*;

constant_expression: conditional_expression;

declaration:
	function_prototype SEMICOLON
	| init_declarator_list SEMICOLON
	| PRECISION precision_qualifier type_specifier SEMICOLON
	| type_qualifier IDENTIFIER LBRACE struct_declaration_list LBRACE (
		IDENTIFIER array_specifier?
	)? SEMICOLON
	| type_qualifier (
		IDENTIFIER (COMMA IDENTIFIER)*
	)? SEMICOLON;

function_prototype:
	function_header LPAREN function_parameter_list RPAREN;

function_parameter_list:
	parameter_declaration (
		COMMA parameter_declaration
	)*;

function_header:
	fully_specified_type variable_identifier;

parameter_declarator:
	type_specifier IDENTIFIER
	| type_specifier IDENTIFIER array_specifier;

parameter_declaration:
	type_qualifier? parameter_declarator
	| fully_specified_type;

//TODO: is this correct? According to the spec it is but something like "int, foo;" doesn't make any sense.
//if not, then declaration_member should be put into an optional block together with the comma list
init_declarator_list:
	fully_specified_type declaration_member? (
		COMMA declaration_member
	)*;

declaration_member:
	IDENTIFIER array_specifier? (
		ASSIGN_OP initializer
	)?;

fully_specified_type:
	type_qualifier? type_specifier;

storage_qualifier:
	CONST
	| IN
	| OUT
	| INOUT
	| CENTROID
	| PATCH
	| SAMPLE
	| UNIFORM
	| BUFFER
	| SHARED
	| COHERENT
	| VOLATILE
	| RESTRICT
	| READONLY
	| WRITEONLY
	| SUBROUTINE (LPAREN type_name_list RPAREN)?;

layout_qualifier:
	LAYOUT LPAREN layout_qualifier_id (
		COMMA layout_qualifier_id
	)* RPAREN;

layout_qualifier_id:
	IDENTIFIER (ASSIGN_OP constant_expression)?
	| SHARED;

precision_qualifier: HIGHP | MEDIUMP | LOWP;

interpolation_qualifier:
	SMOOTH
	| FLAT
	| NOPERSPECTIVE;

invariant_qualifier: INVARIANT;

precise_qualifier: PRECISE;

type_qualifier: (
		storage_qualifier
		| layout_qualifier
		| precision_qualifier
		| interpolation_qualifier
		| invariant_qualifier
		| precise_qualifier
	)+;

//TYPE_NAME instead of IDENTIFIER in the spec
type_name_list: IDENTIFIER (COMMA IDENTIFIER)*;

type_specifier:
	type_specifier_nonarray array_specifier?;

//needs duplicated rule parts like this or it becomes mutually left-recursive
array_specifier:
	array_specifier LBRACKET constant_expression? RBRACKET
	| LBRACKET constant_expression? RBRACKET;

//TYPE_NAME instead of IDENTIFIER in the spec
type_specifier_nonarray:
	builtin_type_specifier_nonarray
	| struct_specifier
	| IDENTIFIER;

builtin_type_specifier_nonarray:
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
	| MAT2X2 //mat2, mat3, mat4 handled as the same token
	| MAT2X3
	| MAT2X4
	| MAT3X2
	| MAT3X3
	| MAT3X4
	| MAT4X2
	| MAT4X3
	| MAT4X4
	| DMAT2X2 //dmat2, dmat3, dmat4 handled as the same token
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

struct_specifier:
	STRUCT IDENTIFIER? LBRACE struct_declaration_list RBRACE;

struct_declaration_list: struct_declaration+;

struct_declaration:
	fully_specified_type struct_declarator_list SEMICOLON;

struct_declarator_list:
	struct_declarator (COMMA struct_declarator)*;

struct_declarator: IDENTIFIER array_specifier?;

initializer:
	assignment_expression
	| LBRACE initializer (COMMA initializer)* COMMA? RBRACE;

statement: compound_statement | simple_statement;

simple_statement:
	declaration_statement
	| expression_statement
	| empty_statement
	| selection_statement
	| switch_statement
	| case_label
	| for_statement
	| while_statement
	| do_while_statement
	| jump_statement;

compound_statement: LBRACE statement_list* RBRACE;

statement_list: statement+;

declaration_statement: declaration;

expression_statement: expression SEMICOLON;

empty_statement: SEMICOLON;

selection_statement:
	IF LPAREN expression RPAREN statement (
		ELSE statement
	)?;

condition:
	expression
	| fully_specified_type IDENTIFIER ASSIGN_OP initializer;

switch_statement:
	SWITCH LPAREN expression RPAREN LBRACE statement_list? RBRACE;

case_label: (CASE expression | DEFAULT) COLON;

while_statement:
	WHILE LPAREN condition RPAREN statement;

do_while_statement:
	DO statement WHILE LPAREN expression RPAREN SEMICOLON;

for_statement:
	FOR LPAREN (
		empty_statement
		| expression_statement
		| declaration_statement
	) condition? SEMICOLON expression? RPAREN statement;

jump_statement: (
		CONTINUE
		| BREAK
		| RETURN expression?
		| DISCARD //fragment shader only
	) SEMICOLON;
