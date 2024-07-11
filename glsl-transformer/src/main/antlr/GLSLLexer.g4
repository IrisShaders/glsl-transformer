lexer grammar GLSLLexer;

@header {
import io.github.douira.glsl_transformer.parser.VersionedGLSLLexer;
}

options {
	superClass = VersionedGLSLLexer;
}

channels {
	WHITESPACE,
	COMMENTS,
	PREPROCESSOR
}

//GLSL token utilities
fragment DECIMAL_DIGITS: '0' | ('1' .. '9' DIGIT*); //no leading 0
fragment OCTAL_DIGITS: '0' ('0' .. '7')+;
fragment HEX_DIGITS: '0x' (DIGIT | 'a' .. 'f' | 'A' .. 'F')+;
fragment DIGIT: '0' .. '9';
fragment FLOAT_DIGITS: ((DIGIT+ ('.' DIGIT*)?) | ('.' DIGIT+)) (
		('e' | 'E') ('+' | '-')? DIGIT*
	)?;
fragment IDENTIFIER_frag: ('a' .. 'z' | 'A' .. 'Z' | '_') (
		DIGIT
		| 'a' .. 'z'
		| 'A' .. 'Z'
		| '_'
	)*;
fragment WS_frag: [\t\r\u000C ]+;
fragment NEWLINE: '\r'? '\n';
fragment NO_NEWLINE: ~('\r' | '\n');
fragment LINE_CONTINUE_frag: '\\' NEWLINE;

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
CONST: 'const';
PRECISE: 'precise';
INVARIANT: 'invariant';
SMOOTH: 'smooth';
FLAT: 'flat';
CENTROID: 'centroid';
ATTRIBUTE: 'attribute';
VOLATILE: 'volatile';
VARYING: 'varying';
SHARED: 'shared';
LAYOUT: 'layout';
DOT_LENGTH_METHOD_CALL: '.length()';
NOPERSPECTIVE: 'noperspective' {isAfter(130)}?; //ignores ES mode
SAMPLE: 'sample' {isAfter(400)}?; //ignores ES mode
PATCH: 'patch' {isAfter(400)}?; //ignores ES mode
COHERENT: 'coherent' {isAfter(130)}?; //unclear, depends on extension
RESTRICT: 'restrict' {isAfter(130)}?; //unclear, depends on extension
READONLY: 'readonly' {isAfter(130)}?; //unclear, depends on extension
WRITEONLY: 'writeonly' {isAfter(130)}?; //unclear, depends on extension
SUBROUTINE: 'subroutine' {isAfter(400)}?; //ignores ES mode
DEVICECOHERENT:
	'devicecoherent' {isAfter(130)}?; //unclear, depends on extension
QUEUEFAMILYCOHERENT:
	'queuefamilycoherent' {isAfter(130)}?; //unclear, depends on extension
WORKGROUPCOHERENT:
	'workgroupcoherent' {isAfter(130)}?; //unclear, depends on extension
SUBGROUPCOHERENT:
	'subgroupcoherent' {isAfter(130)}?; //unclear, depends on extension
NONPRIVATE: 'nonprivate' {isAfter(130)}?; //unclear, depends on extension
RAY_PAYLOAD_EXT: 'rayPayloadEXT';
RAY_PAYLOAD_IN_EXT: 'rayPayloadInEXT';
HIT_ATTRIBUTE_EXT: 'hitAttributeEXT';
CALLABLE_DATA_EXT: 'callableDataEXT';
CALLABLE_DATA_IN_EXT: 'callableDataInEXT';
IGNORE_INTERSECTION_EXT: 'ignoreIntersectionEXT';
TERMINATE_RAY_EXT: 'terminateRayEXT';
ACCELERATION_STRUCTURE_EXT: 'accelerationStructureEXT';

ATOMIC_UINT: 'atomic_uint' {isAfter(420)}?; //ignores ES mode
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
DEMOTE: 'demote';

fragment INTCONSTANT_frag: DECIMAL_DIGITS | OCTAL_DIGITS | HEX_DIGITS;
fragment FLOAT_SUFFIX: 'f' | 'F';
fragment HALF_SUFFIX: 'h' | 'H';
fragment DOUBLE_SUFFIX: 'l' | 'L';
fragment UNSIGNED_SUFFIX: 'u' | 'U';
fragment SHORT_SUFFIX: 's' | 'S';

UINT16CONSTANT: INTCONSTANT_frag UNSIGNED_SUFFIX SHORT_SUFFIX;
INT16CONSTANT: INTCONSTANT_frag SHORT_SUFFIX;

UINT32CONSTANT: INTCONSTANT_frag UNSIGNED_SUFFIX;
INT32CONSTANT: INTCONSTANT_frag;

UINT64CONSTANT: INTCONSTANT_frag UNSIGNED_SUFFIX DOUBLE_SUFFIX;
INT64CONSTANT: INTCONSTANT_frag DOUBLE_SUFFIX;

FLOAT16CONSTANT: FLOAT_DIGITS HALF_SUFFIX FLOAT_SUFFIX;
FLOAT32CONSTANT: FLOAT_DIGITS FLOAT_SUFFIX?;
FLOAT64CONSTANT: FLOAT_DIGITS DOUBLE_SUFFIX FLOAT_SUFFIX;
BOOLCONSTANT: 'true' | 'false';

BOOL: 'bool';
BVEC2: 'bvec2';
BVEC3: 'bvec3';
BVEC4: 'bvec4';

INT8: 'int8_t';
I8VEC2: 'i8vec2';
I8VEC3: 'i8vec3';
I8VEC4: 'i8vec4';
UINT8: 'uint8_t';
U8VEC2: 'u8vec2';
U8VEC3: 'u8vec3';
U8VEC4: 'u8vec4';

INT16: 'int16_t';
I16VEC2: 'i16vec2';
I16VEC3: 'i16vec3';
I16VEC4: 'i16vec4';
UINT16: 'uint16_t';
U16VEC2: 'u16vec2';
U16VEC3: 'u16vec3';
U16VEC4: 'u16vec4';

INT32: 'int32_t' | 'int';
I32VEC2: 'i32vec2' | 'ivec2';
I32VEC3: 'i32vec3' | 'ivec3';
I32VEC4: 'i32vec4' | 'ivec4';
UINT32: 'uint32_t' | 'uint';
U32VEC2: 'u32vec2' | 'uvec2';
U32VEC3: 'u32vec3' | 'uvec3';
U32VEC4: 'u32vec4' | 'uvec4';

INT64: 'int64_t';
I64VEC2: 'i64vec2';
I64VEC3: 'i64vec3';
I64VEC4: 'i64vec4';
UINT64: 'uint64_t';
U64VEC2: 'u64vec2';
U64VEC3: 'u64vec3';
U64VEC4: 'u64vec4';

FLOAT16: 'float16_t';
F16VEC2: 'f16vec2';
F16VEC3: 'f16vec3';
F16VEC4: 'f16vec4';
F16MAT2X2: 'f16mat2x2' | 'f16mat2';
F16MAT2X3: 'f16mat2x3';
F16MAT2X4: 'f16mat2x4';
F16MAT3X2: 'f16mat3x2';
F16MAT3X3: 'f16mat3x3' | 'f16mat3';
F16MAT3X4: 'f16mat3x4';
F16MAT4X2: 'f16mat4x2';
F16MAT4X3: 'f16mat4x3';
F16MAT4X4: 'f16mat4x4' | 'f16mat4';

FLOAT32: 'float32_t' | 'float';
F32VEC2: 'f32vec2' | 'vec2';
F32VEC3: 'f32vec3' | 'vec3';
F32VEC4: 'f32vec4' | 'vec4';
F32MAT2X2: 'f32mat2x2' | 'f32mat2' | 'mat2' | 'mat2x2';
F32MAT2X3: 'f32mat2x3' | 'mat2x3';
F32MAT2X4: 'f32mat2x4' | 'mat2x4';
F32MAT3X2: 'f32mat3x2' | 'mat3x2';
F32MAT3X3: 'f32mat3x3' | 'f32mat3' | 'mat3' | 'mat3x3';
F32MAT3X4: 'f32mat3x4' | 'mat3x4';
F32MAT4X2: 'f32mat4x2' | 'mat4x2';
F32MAT4X3: 'f32mat4x3' | 'mat4x3';
F32MAT4X4: 'f32mat4x4' | 'f32mat4' | 'mat4' | 'mat4x4';

FLOAT64: 'float64_t' | 'double';
F64VEC2: 'f64vec2' | 'dvec2';
F64VEC3: 'f64vec3' | 'dvec3';
F64VEC4: 'f64vec4' | 'dvec4';
F64MAT2X2: 'f64mat2x2' | 'f64mat2' | 'dmat2' | 'dmat2x2';
F64MAT2X3: 'f64mat2x3' | 'dmat2x3';
F64MAT2X4: 'f64mat2x4' | 'dmat2x4';
F64MAT3X2: 'f64mat3x2' | 'dmat3x2';
F64MAT3X3: 'f64mat3x3' | 'f64mat3' | 'dmat3' | 'dmat3x3';
F64MAT3X4: 'f64mat3x4' | 'dmat3x4';
F64MAT4X2: 'f64mat4x2' | 'dmat4x2';
F64MAT4X3: 'f64mat4x3' | 'dmat4x3';
F64MAT4X4: 'f64mat4x4' | 'f64mat4' | 'dmat4' | 'dmat4x4';

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
SAMPLER1DSHADOW: 'sampler1DShadow';
SAMPLER2DSHADOW: 'sampler2DShadow';
SAMPLER2DRECTSHADOW: 'sampler2DRectShadow';
SAMPLER1DARRAY: 'sampler1DArray';
SAMPLER2DARRAY: 'sampler2DArray';
SAMPLER1DARRAYSHADOW:
	'sampler1DArrayShadow' {isAfter(130)}?; //ignores ES mode
SAMPLER2DARRAYSHADOW: 'sampler2DArrayShadow';
ISAMPLER1D: 'isampler1D' {isAfter(130)}?; //ignores ES mode
ISAMPLER2D: 'isampler2D';
ISAMPLER2DRECT: 'isampler2DRect' {isAfter(140)}?; //ignores ES mode
ISAMPLER3D: 'isampler3D';
ISAMPLER1DARRAY: 'isampler1DArray' {isAfter(130)}?; //ignores ES mode
ISAMPLER2DARRAY: 'isampler2DArray';
USAMPLER1D: 'usampler1D' {isAfter(130)}?; //ignores ES mode
USAMPLER2D: 'usampler2D';
USAMPLER2DRECT: 'usampler2DRect' {isAfter(140)}?; //ignores ES mode
USAMPLER3D: 'usampler3D';
USAMPLER1DARRAY: 'usampler1DArray' {isAfter(130)}?; //ignores ES mode
USAMPLER2DARRAY: 'usampler2DArray';
SAMPLER2DMS: 'sampler2DMS' {isAfter(150)}?; //ignores ES mode;
ISAMPLER2DMS: 'isampler2DMS' {isAfter(150)}?; //ignores ES mode;
USAMPLER2DMS: 'usampler2DMS' {isAfter(150)}?; //ignores ES mode;
SAMPLER2DMSARRAY: 'sampler2DMSArray' {isAfter(150)}?; //ignores ES mode;
ISAMPLER2DMSARRAY:
	'isampler2DMSArray' {isAfter(150)}?; //ignores ES mode;
USAMPLER2DMSARRAY:
	'usampler2DMSArray' {isAfter(150)}?; //ignores ES mode;
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

SAMPLERCUBESHADOW: 'samplerCubeShadow';
SAMPLERCUBEARRAYSHADOW: 'samplerCubeArrayShadow';
SAMPLERCUBE: 'samplerCube';
ISAMPLERCUBE: 'isamplerCube';
USAMPLERCUBE: 'usamplerCube';
SAMPLERBUFFER: 'samplerBuffer' {isAfter(130)}?; //ignores ES mode
ISAMPLERBUFFER: 'isamplerBuffer' {isAfter(140)}?; //ignores ES mode
USAMPLERBUFFER: 'usamplerBuffer' {isAfter(140)}?; //ignores ES mode
SAMPLERCUBEARRAY: 'samplerCubeArray';
ISAMPLERCUBEARRAY: 'isamplerCubeArray';
USAMPLERCUBEARRAY: 'usamplerCubeArray';
IMAGECUBE: 'imageCube';
UIMAGECUBE: 'uimageCube';
IIMAGECUBE: 'iimageCube';
IMAGEBUFFER: 'imageBuffer';
IIMAGEBUFFER: 'iimageBuffer';
UIMAGEBUFFER: 'uimageBuffer';
IMAGECUBEARRAY: 'imageCubeArray';
IIMAGECUBEARRAY: 'iimageCubeArray';
UIMAGECUBEARRAY: 'uimageCubeArray';

INC_OP: '++';
DEC_OP: '--';
VOID: 'void';
LEFT_OP: '<<';
RIGHT_OP: '>>';
LE_OP: '<=';
GE_OP: '>=';
EQ_OP: '==';
NE_OP: '!=';
LOGICAL_AND_OP: '&&';
LOGICAL_XOR_OP: '^^';
LOGICAL_OR_OP: '||';
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
LOGICAL_NOT_OP: '!';
BITWISE_NEG_OP: '~';
TIMES_OP: '*';
DIV_OP: '/';
MOD_OP: '%';
LT_OP: '<';
GT_OP: '>';
BITWISE_AND_OP: '&';
BITWISE_OR_OP: '|';
BITWISE_XOR_OP: '^';
QUERY_OP: '?';
ASSIGN_OP: '=';

//actual preprocessor parsing mode (is not parsed, hidden into a channel)
fragment NR_PP_PREFIX: '#' [\t ]*;
PP_ENTER_MODE:
	NR_PP_PREFIX (
		'define'
		| 'undef'
		| 'if'
		| 'ifdef'
		| 'ifndef'
		| 'else'
		| 'elif'
		| 'endif'
		| 'error'
	) -> channel(PREPROCESSOR), pushMode(Preprocessor);
PP_EMPTY:
	NR_PP_PREFIX (WS_frag | LINE_CONTINUE_frag)* NEWLINE -> channel(PREPROCESSOR);

//preprocessor-related tokens
NR_LINE:
	'#line' WS_frag DIGIT+ (WS_frag DIGIT+)? (NEWLINE | WS_frag)* NEWLINE -> channel(PREPROCESSOR);
NR: '#' -> pushMode(NR_Mode);
IDENTIFIER: IDENTIFIER_frag;

STRING_START: '"' {enableStrings}? -> pushMode(StringLiteral);

fragment LINE_COMMENT_frag: '//' NO_NEWLINE*;

//performance testing suggests that using the .*? here is fine, alternatives were slower
fragment BLOCK_COMMENT_frag: '/*' .*? '*/';

//hidden comment and whitespace tokens
LINE_CONTINUE: LINE_CONTINUE_frag -> channel(WHITESPACE);
LINE_COMMENT: LINE_COMMENT_frag NEWLINE -> channel(COMMENTS);
BLOCK_COMMENT: BLOCK_COMMENT_frag -> channel(COMMENTS);
WS: WS_frag -> channel(WHITESPACE);
EOL: NEWLINE -> channel(WHITESPACE);

//nr-sign parsing mode
mode NR_Mode;
NR_EXTENSION: 'extension';
NR_VERSION: 'version';
NR_CUSTOM:
	'custom' {enableCustomDirective}? -> pushMode(CustomDirective);
NR_INCLUDE: 'include' {enableIncludeDirective}?;
NR_PRAGMA: 'pragma';
NR_PRAGMA_DEBUG: 'debug';
NR_PRAGMA_OPTIMIZE: 'optimize';
NR_PRAGMA_INVARIANT: 'invariant';
NR_ON: 'on';
NR_OFF: 'off';
NR_ALL: 'all';
NR_REQUIRE: 'require';
NR_ENABLE: 'enable';
NR_WARN: 'warn';
NR_DISABLE: 'disable';
NR_COLON: ':';
NR_LPAREN: '(';
NR_RPAREN: ')';
NR_STDGL: 'STDGL';
NR_CORE: 'core';
NR_COMPATIBILITY: 'compatibility';
NR_ES: 'es';
NR_GLSL_110: '110';
NR_GLSL_120: '120';
NR_GLSLES_100: '100';
NR_GLSL_130: '130';
NR_GLSL_140: '140';
NR_GLSL_150: '150';
NR_GLSL_330: '330';
NR_GLSLES_300: '300';
NR_GLSLES_310: '310';
NR_GLSLES_320: '320';
NR_GLSL_400: '400';
NR_GLSL_410: '410';
NR_GLSL_420: '420';
NR_GLSL_430: '430';
NR_GLSL_440: '440';
NR_GLSL_450: '450';
NR_GLSL_460: '460';

NR_STRING_START: '"' {enableIncludeDirective}? -> pushMode(String);
NR_STRING_START_ANGLE:
	'<' {enableIncludeDirective}? -> pushMode(StringAngle);
NR_INTCONSTANT: INTCONSTANT_frag;
NR_IDENTIFIER: IDENTIFIER_frag;
NR_LINE_CONTINUE: LINE_CONTINUE_frag -> channel(WHITESPACE);
NR_LINE_COMMENT: LINE_COMMENT_frag -> channel(COMMENTS);
NR_BLOCK_COMMENT: BLOCK_COMMENT_frag -> channel(COMMENTS);
NR_EOL: NEWLINE -> popMode;
NR_WS: WS_frag -> channel(WHITESPACE);

mode String;
NR_S_CONTENT: ~["\r\n]+;
NR_S_STRING_END: '"' -> popMode;

mode StringLiteral;
SL_CONTENT: ~["]+;
SL_STRING_END: '"' -> popMode;

mode StringAngle;
NR_SA_CONTENT: ~[>\r\n]+;
NR_SA_STRING_END: '>' -> popMode;

mode CustomDirective;
C_LINE_COMMENT: LINE_COMMENT_frag -> channel(COMMENTS);
C_BLOCK_COMMENT: BLOCK_COMMENT_frag -> channel(COMMENTS);
C_EOL: NEWLINE -> popMode, popMode;
C_WS: WS_frag -> channel(WHITESPACE);
C_CONTENT: ~[\n\t\r\u000C ] NO_NEWLINE*;

//gobble the preprocessor content only if started a preprocessor directive
mode Preprocessor;
PP_LINE_CONTINUE: LINE_CONTINUE_frag -> channel(WHITESPACE);
PP_LINE_COMMENT: LINE_COMMENT_frag -> channel(COMMENTS);
PP_BLOCK_COMMENT: BLOCK_COMMENT_frag -> channel(COMMENTS);
PP_EOL: NEWLINE -> channel(PREPROCESSOR), popMode;
PP_CONTENT:
	NO_NEWLINE* ~('\r' | '\n' | '\\') -> channel(PREPROCESSOR);
