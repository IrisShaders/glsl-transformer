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

grammar GLSL;

//the root rule
translation_unit:
	version_statement external_declaration_list;

version_statement:
	| VERSION INTCONSTANT EOL
	| VERSION INTCONSTANT IDENTIFIER EOL;

pragma_statement:
	PRAGMA_DEBUG_ON EOL
	| PRAGMA_DEBUG_OFF EOL
	| PRAGMA_OPTIMIZE_ON EOL
	| PRAGMA_OPTIMIZE_OFF EOL
	| PRAGMA_INVARIANT_ALL EOL;

extension_statement:
	EXTENSION IDENTIFIER COLON IDENTIFIER EOL;

external_declaration_list: external_declaration+;

external_declaration:
	function_definition
	| declaration
	| pragma_statement
	| layout_defaults
	| SEMICOLON;

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
	non_constant_expression (
		COMMA non_constant_expression
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

//this weird nested structure is necessary to ensure correct parenthesis usage
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
	non_constant_expression (
		COMMA non_constant_expression
	)*;

non_constant_expression: assignment_expression;

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
	IDENTIFIER
	| IDENTIFIER ASSIGN_OP constant_expression
	| SHARED;

layout_defaults:
	layout_qualifier (UNIFORM | IN | OUT | BUFFER) SEMICOLON;

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
	non_constant_expression
	| LBRACE initializer_list RBRACE
	| LBRACE initializer_list COMMA RBRACE;

initializer_list: initializer (COMMA initializer)*;

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
		| RETURN
		| RETURN expression
		| DISCARD //fragment shader only
	) SEMICOLON;

PRAGMA_DEBUG_ON:
	[ \t]* '#' [ \t]* 'pragma' [ \t]+ 'debug' [ \t]* '(' [ \t]* 'on' [ \t]* ')';
PRAGMA_DEBUG_OFF:
	[ \t]* '#' [ \t]* 'pragma' [ \t]+ 'debug' [ \t]* '(' [ \t]* 'off' [ \t]* ')'
		;
PRAGMA_OPTIMIZE_ON:
	[ \t]* '#' [ \t]* 'pragma' [ \t]+ 'optimize' [ \t]* '(' [ \t]* 'on' [ \t]*
		')';
PRAGMA_OPTIMIZE_OFF:
	[ \t]* '#' [ \t]* 'pragma' [ \t]+ 'optimize' [ \t]* '(' [ \t]* 'off' [ \t]*
		')';
PRAGMA_INVARIANT_ALL:
	[ \t]* '#' [ \t]* 'pragma' [ \t]+ 'invariant' [ \t]* '(' [ \t]* 'all' [ \t]*
		')';
EXTENSION: [ \t]* '#' [ \t]* 'extension';
COLON: ':';
UNIFORM: 'uniform';
BUFFER: 'buffer';
IN: 'in';
OUT: 'out';
INOUT: 'inout';
HIGHP: 'highp';
MEDIUMP: 'mediump';
LOWP: 'lowp';
PRECISION: 'precision';
VERSION: [ \t]* '#' [ \t]* 'version';
INTCONSTANT:
	DECIMAL_DIGITS
	| OCTAL_DIGITS
	| HEX_DIGITS;
CONST: 'const';
PRECISE: 'precise';
INVARIANT: 'invariant';
SMOOTH: 'smooth';
FLAT: 'flat';
NOPERSPECTIVE: 'noperspective';
CENTROID: 'centroid';
SAMPLE: 'sample';
PATCH: 'patch';
ATTRIBUTE: 'attribute';
COHERENT: 'coherent';
VOLATILE: 'volatile';
RESTRICT: 'restrict';
VARYING: 'varying';
READONLY: 'readonly';
WRITEONLY: 'writeonly';
SHARED: 'shared';
SUBROUTINE: 'subroutine';
LAYOUT: 'layout';
UINTCONSTANT: (
		DECIMAL_DIGITS
		| OCTAL_DIGITS
		| HEX_DIGITS
	) 'u';
ROW_MAJOR: 'row_major';
PACKED: 'packed';
fragment FLOAT_DIGITS: (
		(DIGIT+ ('.' DIGIT*)?)
		| ('.' DIGIT+)
	) (('e' | 'E') ('+' | '-')? DIGIT*)?;
FLOATCONSTANT: FLOAT_DIGITS 'f'?;
BOOLCONSTANT: 'true' | 'false';
DOUBLECONSTANT: FLOAT_DIGITS ('LF' | 'lf');
INC_OP: '++';
DEC_OP: '--';
VOID: 'void';
LEFT_OP: '<<';
RIGHT_OP: '>>';
LE_OP: '<=';
GE_OP: '>=';
EQ_OP: '==';
NE_OP: '!=';
AND_OP: '&&';
XOR_OP: '^^';
OR_OP: '||';
MUL_ASSIGN: '*=';
DIV_ASSIGN: '/=';
MOD_ASSIGN: '%=';
ADD_ASSIGN: '+=';
SUB_ASSIGN: '-=';
LEFT_ASSIGN: '<<=';
RIGHT_ASSIGN: '>>=';
AND_ASSIGN: '&=';
XOR_ASSIGN: '^=';
OR_ASSIGN: '|=';
FLOAT: 'float';
DOUBLE: 'double';
INT: 'int';
UINT: 'uint';
BOOL: 'bool';
SAMPLERCUBE: 'samplerCube';
SAMPLERCUBESHADOW: 'samplerCubeShadow';
SAMPLERBUFFER: 'samplerBuffer';
SAMPLERCUBEARRAY: 'samplerCubeArray';
SAMPLERCUBEARRAYSHADOW: 'samplerCubeArrayShadow';
ISAMPLERCUBE: 'isamplerCube';
ISAMPLERBUFFER: 'isamplerBuffer';
ISAMPLERCUBEARRAY: 'isamplerCubeArray';
USAMPLERCUBE: 'usamplerCube';
USAMPLERBUFFER: 'usamplerBuffer';
USAMPLERCUBEARRAY: 'usamplerCubeArray';
IMAGECUBE: 'imageCube';
IMAGEBUFFER: 'imageBuffer';
IMAGECUBEARRAY: 'imageCubeArray';
IIMAGECUBE: 'iimageCube';
IIMAGEBUFFER: 'iimageBuffer';
IIMAGECUBEARRAY: 'iimageCubeArray';
UIMAGECUBE: 'uimageCube';
UIMAGEBUFFER: 'uimageBuffer';
UIMAGECUBEARRAY: 'uimageCubeArray';
ATOMIC_UINT: 'atomic_uint';
STRUCT: 'struct';
IF: 'if';
ELSE: 'else';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
WHILE: 'while';
DO: 'do';
FOR: 'for';
CONTINUE: 'continue';
BREAK: 'break';
RETURN: 'return';
DISCARD: 'discard';
VEC2: 'vec2';
VEC3: 'vec3';
VEC4: 'vec4';
DVEC2: 'dvec2';
DVEC3: 'dvec3';
DVEC4: 'dvec4';
BVEC2: 'bvec2';
BVEC3: 'bvec3';
BVEC4: 'bvec4';
IVEC2: 'ivec2';
IVEC3: 'ivec3';
IVEC4: 'ivec4';
UVEC2: 'uvec2';
UVEC3: 'uvec3';
UVEC4: 'uvec4';
MAT2X2: 'mat2' | 'mat2x2';
MAT2X3: 'mat2x3';
MAT2X4: 'mat2x4';
MAT3X2: 'mat3x2';
MAT3X3: 'mat3' | 'mat3x3';
MAT3X4: 'mat3x4';
MAT4X2: 'mat4x2';
MAT4X3: 'mat4x3';
MAT4X4: 'mat4' | 'mat4x4';
DMAT2X2: 'dmat2' | 'dmat2x2';
DMAT2X3: 'dmat2x3';
DMAT2X4: 'dmat2x4';
DMAT3X2: 'dmat3x2';
DMAT3X3: 'dmat3' | 'dmat3x3';
DMAT3X4: 'dmat3x4';
DMAT4X2: 'dmat4x2';
DMAT4X3: 'dmat4x3';
DMAT4X4: 'dmat4' | 'dmat4x4';
IMAGE1D: 'image1D';
IMAGE2D: 'image2D';
IMAGE3D: 'image3D';
UIMAGE1D: 'uimage1D';
UIMAGE2D: 'uimage2D';
UIMAGE3D: 'uimage3D';
IIMAGE1D: 'iimage1D';
IIMAGE2D: 'iimage2D';
IIMAGE3D: 'iimage3D';
SAMPLER1D: 'sampler1D';
SAMPLER2D: 'sampler2D';
SAMPLER3D: 'sampler3D';
SAMPLER2DRECT: 'sampler2DRect';
SAMPLEREXTERNALOES: 'samplerExternalOES';
SAMPLER1DSHADOW: 'sampler1DShadow';
SAMPLER2DSHADOW: 'sampler2DShadow';
SAMPLER2DRECTSHADOW: 'sampler2DRectShadow';
SAMPLER1DARRAY: 'sampler1DArray';
SAMPLER2DARRAY: 'sampler2DArray';
SAMPLER1DARRAYSHADOW: 'sampler1DArrayShadow';
SAMPLER2DARRAYSHADOW: 'sampler2DArrayShadow';
ISAMPLER1D: 'isampler1D';
ISAMPLER2D: 'isampler2D';
ISAMPLER2DRECT: 'isampler2DRect';
ISAMPLER3D: 'isampler3D';
ISAMPLER1DARRAY: 'isampler1DArray';
ISAMPLER2DARRAY: 'isampler2DArray';
USAMPLER1D: 'usampler1D';
USAMPLER2D: 'usampler2D';
USAMPLER2DRECT: 'usampler2DRect';
USAMPLER3D: 'usampler3D';
USAMPLER1DARRAY: 'usampler1DArray';
USAMPLER2DARRAY: 'usampler2DArray';
SAMPLER2DMS: 'sampler2DMS';
ISAMPLER2DMS: 'isampler2DMS';
USAMPLER2DMS: 'usampler2DMS';
SAMPLER2DMSARRAY: 'sampler2DMSArray';
ISAMPLER2DMSARRAY: 'isampler2DMSArray';
USAMPLER2DMSARRAY: 'usampler2DMSArray';
IMAGE2DRECT: 'image2DRect';
IMAGE1DARRAY: 'image1DArray';
IMAGE2DARRAY: 'image2DArray';
IMAGE2DMS: 'image2DMS';
IMAGE2DMSARRAY: 'image2DMSArray';
IIMAGE2DRECT: 'iimage2DRect';
IIMAGE1DARRAY: 'iimage1DArray';
IIMAGE2DARRAY: 'iimage2DArray';
IIMAGE2DMS: 'iimage2DMS';
IIMAGE2DMSARRAY: 'iimage2DMSArray';
UIMAGE2DRECT: 'uimage2DRect';
UIMAGE1DARRAY: 'uimage1DArray';
UIMAGE2DARRAY: 'uimage2DArray';
UIMAGE2DMS: 'uimage2DMS';
UIMAGE2DMSARRAY: 'uimage2DMSArray';

LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
SEMICOLON: ';';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';

DOT: '.';
PLUS_OP: '+';
MINUS_OP: '-';
NOT_OP: '!';
BNEG_OP: '~';
TIMES_OP: '*';
DIV_OP: '/';
MOD_OP: '%';
LT_OP: '<';
GT_OP: '>';
BAND_OP: '&';
BOR_OP: '|';
BXOR_OP: '^';
QUERY_OP: '?';
ASSIGN_OP: '=';

IDENTIFIER: ('a' ..'z' | 'A' ..'Z' | '_') (
		DIGIT
		| 'a' ..'z'
		| 'A' ..'Z'
		| '_'
	)*;

fragment DECIMAL_DIGITS: '0' | ('1' ..'9' DIGIT*);
fragment OCTAL_DIGITS: '0' '0' ..'7'+;
fragment HEX_DIGITS:
	'0x' (DIGIT | 'a' ..'f' | 'A' ..'F')+;
fragment DIGIT: '0' ..'9';

COMMENT: (
		'//' ~('\n' | '\r')* '\r'? '\n'
		| '/*' (.)*? '*/'
	) -> channel(HIDDEN);

WS: [\t\r\u000C ]+ -> channel(HIDDEN);

EOL: '\n';
