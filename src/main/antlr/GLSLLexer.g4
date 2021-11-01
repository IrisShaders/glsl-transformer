lexer grammar GLSLLexer;

channels {
	WHITESPACE,
	COMMENTS,
	PREPROCESSOR
}

//GLSL tokens
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
// ROW_MAJOR: 'row_major';
// PACKED: 'packed';
fragment FLOAT_DIGITS: (
		(DIGIT+ ('.' DIGIT*)?)
		| ('.' DIGIT+)
	) (('e' | 'E') ('+' | '-')? DIGIT*)?;
FLOATCONSTANT: FLOAT_DIGITS ('f' | 'F')?;
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

//GLSL token utilities
fragment DECIMAL_DIGITS: '0' | ('1' ..'9' DIGIT*);
fragment OCTAL_DIGITS: '0' '0' ..'7'+;
fragment HEX_DIGITS:
	'0x' (DIGIT | 'a' ..'f' | 'A' ..'F')+;
fragment DIGIT: '0' ..'9';

//hidden comment and whitespace tokens
COMMENT: (
		'//' ~('\n' | '\r')* '\r'? '\n'
		| '/*' (.)*? '*/'
	) -> channel(HIDDEN);
WS: [\t\r\u000C ]+ -> channel(WHITESPACE);
EOL: '\n' -> channel(WHITESPACE);

//utility preprocessor tokens
fragment NR: '#'; //number sign
fragment WSS: [ \t]+; //"white space some"
fragment WSM: [ \t]*; //"white space maybe"
fragment PREFIX_NR: WSM NR WSM;
fragment PRAGMA_PREFIX: PREFIX_NR 'pragma' WSS;
fragment PRAGMA_SUFFIX_ON:
	WSM '(' WSM 'on' WSM ')';
fragment PRAGMA_SUFFIX_OFF:
	WSM '(' WSM 'off' WSM ')';
fragment PRAGMA_SUFFIX_ALL:
	WSM '(' WSM 'all' WSM ')';

//preprocessor-related tokens that are not hidden
PRAGMA_DEBUG_ON:
	PRAGMA_PREFIX 'debug' PRAGMA_SUFFIX_ON;
PRAGMA_DEBUG_OFF:
	PRAGMA_PREFIX 'debug' PRAGMA_SUFFIX_OFF;
PRAGMA_OPTIMIZE_ON:
	PRAGMA_PREFIX 'optimize' PRAGMA_SUFFIX_ON;
PRAGMA_OPTIMIZE_OFF:
	PRAGMA_PREFIX 'optimize' PRAGMA_SUFFIX_OFF;
PRAGMA_INVARIANT_ALL:
	PRAGMA_PREFIX 'invariant' PRAGMA_SUFFIX_ALL;

EXTENSION: '#extension';
VERSION: '#version';
REQUIRE: 'require';
ENABLE: 'enable';
WARN: 'warn';
DISABLE: 'disable';

// //preprocessor tokens that are hidden
// PP_START:
// 	(
// 		PP_DEFINE
// 		| PP_UNDEF
// 		| PP_IF
// 		| PP_IFDEF
// 		| PP_IFNDEF
// 		| PP_ELSE
// 		| PP_ELIF
// 		| PP_ENDIF
// 		| PP_ERROR
// 		| PP_LINE
// 	) -> channel(PREPROCESSOR), pushMode(Preprocessor);

// PP_DEFINE:
// 	PREFIX_NR 'define' -> channel(PREPROCESSOR);
// PP_UNDEF:
// 	PREFIX_NR 'define' -> channel(PREPROCESSOR);
// PP_IF: PREFIX_NR 'if' -> channel(PREPROCESSOR);
// PP_IFDEF:
// 	PREFIX_NR 'ifdef' -> channel(PREPROCESSOR);
// PP_IFNDEF:
// 	PREFIX_NR 'ifndef' -> channel(PREPROCESSOR);
// PP_ELSE:
// 	PREFIX_NR 'else' -> channel(PREPROCESSOR);
// PP_ELIF:
// 	PREFIX_NR 'elif' -> channel(PREPROCESSOR);
// PP_ENDIF:
// 	PREFIX_NR 'endif' -> channel(PREPROCESSOR);
// PP_ERROR:
// 	PREFIX_NR 'error' -> channel(PREPROCESSOR);
// PP_LINE:
// 	PREFIX_NR 'line' -> channel(PREPROCESSOR);

// PP_EMPTY_DIRECTIVE:
// 	PREFIX_NR WSM EOL -> channel(PREPROCESSOR);

// //gobble the preprocessor content only if started a preprocessor directive
// mode Preprocessor;
// PP_EOL: EOL -> channel(PREPROCESSOR), popMode;
// PP_CONTENT: ~('\n')+ -> channel(PREPROCESSOR);
