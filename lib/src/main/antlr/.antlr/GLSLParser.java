// Generated from /Users/christopher/Documents/dev/glsl-transformer/src/main/antlr/GLSLParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GLSLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COLON=1, UNIFORM=2, BUFFER=3, IN=4, OUT=5, INOUT=6, HIGHP=7, MEDIUMP=8, 
		LOWP=9, PRECISION=10, CONST=11, PRECISE=12, INVARIANT=13, SMOOTH=14, FLAT=15, 
		NOPERSPECTIVE=16, CENTROID=17, SAMPLE=18, PATCH=19, ATTRIBUTE=20, COHERENT=21, 
		VOLATILE=22, RESTRICT=23, VARYING=24, READONLY=25, WRITEONLY=26, SHARED=27, 
		SUBROUTINE=28, DEVICECOHERENT=29, QUEUEFAMILYCOHERENT=30, WORKGROUPCOHERENT=31, 
		SUBGROUPCOHERENT=32, NONPRIVATE=33, LAYOUT=34, ATOMIC_UINT=35, STRUCT=36, 
		IF=37, ELSE=38, SWITCH=39, CASE=40, DEFAULT=41, WHILE=42, DO=43, FOR=44, 
		CONTINUE=45, BREAK=46, RETURN=47, DISCARD=48, DEMOTE=49, UINT16CONSTANT=50, 
		INT16CONSTANT=51, UINT32CONSTANT=52, INT32CONSTANT=53, UINT64CONSTANT=54, 
		INT64CONSTANT=55, FLOAT16CONSTANT=56, FLOAT32CONSTANT=57, FLOAT64CONSTANT=58, 
		BOOLCONSTANT=59, BOOL=60, BVEC2=61, BVEC3=62, BVEC4=63, FLOAT16=64, F16VEC2=65, 
		F16VEC3=66, F16VEC4=67, F16MAT2X2=68, F16MAT2X3=69, F16MAT2X4=70, F16MAT3X2=71, 
		F16MAT3X3=72, F16MAT3X4=73, F16MAT4X2=74, F16MAT4X3=75, F16MAT4X4=76, 
		FLOAT32=77, F32VEC2=78, F32VEC3=79, F32VEC4=80, F32MAT2X2=81, F32MAT2X3=82, 
		F32MAT2X4=83, F32MAT3X2=84, F32MAT3X3=85, F32MAT3X4=86, F32MAT4X2=87, 
		F32MAT4X3=88, F32MAT4X4=89, FLOAT64=90, F64VEC2=91, F64VEC3=92, F64VEC4=93, 
		F64MAT2X2=94, F64MAT2X3=95, F64MAT2X4=96, F64MAT3X2=97, F64MAT3X3=98, 
		F64MAT3X4=99, F64MAT4X2=100, F64MAT4X3=101, F64MAT4X4=102, INT8=103, I8VEC2=104, 
		I8VEC3=105, I8VEC4=106, UINT8=107, UI8VEC2=108, UI8VEC3=109, UI8VEC4=110, 
		INT16=111, I16VEC2=112, I16VEC3=113, I16VEC4=114, UINT16=115, UI16VEC2=116, 
		UI16VEC3=117, UI16VEC4=118, INT32=119, I32VEC2=120, I32VEC3=121, I32VEC4=122, 
		UINT32=123, UI32VEC2=124, UI32VEC3=125, UI32VEC4=126, INT64=127, I64VEC2=128, 
		I64VEC3=129, I64VEC4=130, UINT64=131, UI64VEC2=132, UI64VEC3=133, UI64VEC4=134, 
		IMAGE1D=135, IMAGE2D=136, IMAGE3D=137, UIMAGE1D=138, UIMAGE2D=139, UIMAGE3D=140, 
		IIMAGE1D=141, IIMAGE2D=142, IIMAGE3D=143, SAMPLER1D=144, SAMPLER2D=145, 
		SAMPLER3D=146, SAMPLER2DRECT=147, SAMPLEREXTERNALOES=148, SAMPLER1DSHADOW=149, 
		SAMPLER2DSHADOW=150, SAMPLER2DRECTSHADOW=151, SAMPLER1DARRAY=152, SAMPLER2DARRAY=153, 
		SAMPLER1DARRAYSHADOW=154, SAMPLER2DARRAYSHADOW=155, ISAMPLER1D=156, ISAMPLER2D=157, 
		ISAMPLER2DRECT=158, ISAMPLER3D=159, ISAMPLER1DARRAY=160, ISAMPLER2DARRAY=161, 
		USAMPLER1D=162, USAMPLER2D=163, USAMPLER2DRECT=164, USAMPLER3D=165, USAMPLER1DARRAY=166, 
		USAMPLER2DARRAY=167, SAMPLER2DMS=168, ISAMPLER2DMS=169, USAMPLER2DMS=170, 
		SAMPLER2DMSARRAY=171, ISAMPLER2DMSARRAY=172, USAMPLER2DMSARRAY=173, IMAGE2DRECT=174, 
		IMAGE1DARRAY=175, IMAGE2DARRAY=176, IMAGE2DMS=177, IMAGE2DMSARRAY=178, 
		IIMAGE2DRECT=179, IIMAGE1DARRAY=180, IIMAGE2DARRAY=181, IIMAGE2DMS=182, 
		IIMAGE2DMSARRAY=183, UIMAGE2DRECT=184, UIMAGE1DARRAY=185, UIMAGE2DARRAY=186, 
		UIMAGE2DMS=187, UIMAGE2DMSARRAY=188, SAMPLERCUBE=189, SAMPLERCUBESHADOW=190, 
		SAMPLERBUFFER=191, SAMPLERCUBEARRAY=192, SAMPLERCUBEARRAYSHADOW=193, ISAMPLERCUBE=194, 
		ISAMPLERBUFFER=195, ISAMPLERCUBEARRAY=196, USAMPLERCUBE=197, USAMPLERBUFFER=198, 
		USAMPLERCUBEARRAY=199, IMAGECUBE=200, IMAGEBUFFER=201, IMAGECUBEARRAY=202, 
		IIMAGECUBE=203, IIMAGEBUFFER=204, IIMAGECUBEARRAY=205, UIMAGECUBE=206, 
		UIMAGEBUFFER=207, UIMAGECUBEARRAY=208, INC_OP=209, DEC_OP=210, VOID=211, 
		LEFT_OP=212, RIGHT_OP=213, LE_OP=214, GE_OP=215, EQ_OP=216, NE_OP=217, 
		AND_OP=218, XOR_OP=219, OR_OP=220, MUL_ASSIGN=221, DIV_ASSIGN=222, MOD_ASSIGN=223, 
		ADD_ASSIGN=224, SUB_ASSIGN=225, LEFT_ASSIGN=226, RIGHT_ASSIGN=227, AND_ASSIGN=228, 
		XOR_ASSIGN=229, OR_ASSIGN=230, LPAREN=231, RPAREN=232, LBRACE=233, RBRACE=234, 
		SEMICOLON=235, LBRACKET=236, RBRACKET=237, COMMA=238, DOT=239, PLUS_OP=240, 
		MINUS_OP=241, NOT_OP=242, BNEG_OP=243, TIMES_OP=244, DIV_OP=245, MOD_OP=246, 
		LT_OP=247, GT_OP=248, BAND_OP=249, BOR_OP=250, BXOR_OP=251, QUERY_OP=252, 
		ASSIGN_OP=253, PP_ENTER_MODE=254, PP_EMPTY=255, NR=256, IDENTIFIER=257, 
		LINE_CONTINUE=258, COMMENT=259, WS=260, EOL=261, EXTENSION=262, VERSION=263, 
		PRAGMA=264, PRAGMA_DEBUG=265, PRAGMA_OPTIMIZE=266, PRAGMA_INVARIANT=267, 
		NR_ON=268, NR_OFF=269, NR_ALL=270, NR_REQUIRE=271, NR_ENABLE=272, NR_WARN=273, 
		NR_DISABLE=274, NR_COLON=275, NR_LPAREN=276, NR_RPAREN=277, NR_STDGL=278, 
		NR_INTCONSTANT=279, NR_IDENTIFIER=280, NR_WS=281, NR_LINE_CONTINUE=282, 
		NR_EOL=283, PP_LINE_CONTINUE=284, PP_EOL=285, PP_CONTENT=286;
	public static final int
		RULE_translationUnit = 0, RULE_versionStatement = 1, RULE_externalDeclaration = 2, 
		RULE_pragmaStatement = 3, RULE_extensionStatement = 4, RULE_extensionState = 5, 
		RULE_layoutDefaults = 6, RULE_layoutModes = 7, RULE_functionDefinition = 8, 
		RULE_variableIdentifier = 9, RULE_primaryExpression = 10, RULE_postfixExpression = 11, 
		RULE_functionCall = 12, RULE_methodCall = 13, RULE_callParameterList = 14, 
		RULE_functionIdentifier = 15, RULE_unaryExpression = 16, RULE_unaryOperator = 17, 
		RULE_multiplicativeExpression = 18, RULE_additiveExpression = 19, RULE_shiftExpression = 20, 
		RULE_relationalExpression = 21, RULE_equalityExpression = 22, RULE_andExpression = 23, 
		RULE_exclusiveOrExpression = 24, RULE_inclusiveOrExpression = 25, RULE_logicalAndExpression = 26, 
		RULE_logicalXorExpression = 27, RULE_logicalOrExpression = 28, RULE_conditionalExpression = 29, 
		RULE_assignmentExpression = 30, RULE_assignmentOperator = 31, RULE_expression = 32, 
		RULE_constantExpression = 33, RULE_declaration = 34, RULE_functionPrototype = 35, 
		RULE_functionParameterList = 36, RULE_functionHeader = 37, RULE_parameterDeclarator = 38, 
		RULE_parameterDeclaration = 39, RULE_attribute = 40, RULE_singleAttribute = 41, 
		RULE_initDeclaratorList = 42, RULE_declarationMemberList = 43, RULE_declarationMember = 44, 
		RULE_fullySpecifiedType = 45, RULE_storageQualifier = 46, RULE_layoutQualifier = 47, 
		RULE_layoutQualifierId = 48, RULE_precisionQualifier = 49, RULE_interpolationQualifier = 50, 
		RULE_invariantQualifier = 51, RULE_preciseQualifier = 52, RULE_typeQualifier = 53, 
		RULE_typeNameList = 54, RULE_typeSpecifier = 55, RULE_arraySpecifier = 56, 
		RULE_typeSpecifierNonarray = 57, RULE_builtinTypeSpecifierParseable = 58, 
		RULE_builtinTypeSpecifierFixed = 59, RULE_structSpecifier = 60, RULE_structDeclarationList = 61, 
		RULE_structDeclaration = 62, RULE_structDeclaratorList = 63, RULE_structDeclarator = 64, 
		RULE_initializer = 65, RULE_statement = 66, RULE_simpleStatement = 67, 
		RULE_compoundStatement = 68, RULE_declarationStatement = 69, RULE_expressionStatement = 70, 
		RULE_emptyStatement = 71, RULE_selectionStatement = 72, RULE_condition = 73, 
		RULE_switchStatement = 74, RULE_caseLabel = 75, RULE_whileStatement = 76, 
		RULE_doWhileStatement = 77, RULE_forStatement = 78, RULE_jumpStatement = 79, 
		RULE_demoteStatement = 80;
	private static String[] makeRuleNames() {
		return new String[] {
			"translationUnit", "versionStatement", "externalDeclaration", "pragmaStatement", 
			"extensionStatement", "extensionState", "layoutDefaults", "layoutModes", 
			"functionDefinition", "variableIdentifier", "primaryExpression", "postfixExpression", 
			"functionCall", "methodCall", "callParameterList", "functionIdentifier", 
			"unaryExpression", "unaryOperator", "multiplicativeExpression", "additiveExpression", 
			"shiftExpression", "relationalExpression", "equalityExpression", "andExpression", 
			"exclusiveOrExpression", "inclusiveOrExpression", "logicalAndExpression", 
			"logicalXorExpression", "logicalOrExpression", "conditionalExpression", 
			"assignmentExpression", "assignmentOperator", "expression", "constantExpression", 
			"declaration", "functionPrototype", "functionParameterList", "functionHeader", 
			"parameterDeclarator", "parameterDeclaration", "attribute", "singleAttribute", 
			"initDeclaratorList", "declarationMemberList", "declarationMember", "fullySpecifiedType", 
			"storageQualifier", "layoutQualifier", "layoutQualifierId", "precisionQualifier", 
			"interpolationQualifier", "invariantQualifier", "preciseQualifier", "typeQualifier", 
			"typeNameList", "typeSpecifier", "arraySpecifier", "typeSpecifierNonarray", 
			"builtinTypeSpecifierParseable", "builtinTypeSpecifierFixed", "structSpecifier", 
			"structDeclarationList", "structDeclaration", "structDeclaratorList", 
			"structDeclarator", "initializer", "statement", "simpleStatement", "compoundStatement", 
			"declarationStatement", "expressionStatement", "emptyStatement", "selectionStatement", 
			"condition", "switchStatement", "caseLabel", "whileStatement", "doWhileStatement", 
			"forStatement", "jumpStatement", "demoteStatement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'uniform'", "'buffer'", "'in'", "'out'", "'inout'", "'highp'", 
			"'mediump'", "'lowp'", "'precision'", "'const'", "'precise'", null, "'smooth'", 
			"'flat'", "'noperspective'", "'centroid'", "'sample'", "'patch'", "'attribute'", 
			"'coherent'", "'volatile'", "'restrict'", "'varying'", "'readonly'", 
			"'writeonly'", "'shared'", "'subroutine'", "'devicecoherent'", "'queuefamilycoherent'", 
			"'workgroupcoherent'", "'subgroupcoherent'", "'nonprivate'", "'layout'", 
			"'atomic_uint'", "'struct'", "'if'", "'else'", "'switch'", "'case'", 
			"'default'", "'while'", "'do'", "'for'", "'continue'", "'break'", "'return'", 
			"'discard'", "'demote'", null, null, null, null, null, null, null, null, 
			null, null, "'bool'", "'bvec2'", "'bvec3'", "'bvec4'", "'float16_t'", 
			"'f16vec2'", "'f16vec3'", "'f16vec4'", null, "'f16mat2x3'", "'f16mat2x4'", 
			"'f16mat3x2'", null, "'f16mat3x4'", "'f16mat4x2'", "'f16mat4x3'", null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "'int8_t'", "'i8vec2'", "'i8vec3'", "'i8vec4'", "'uint8_t'", 
			"'ui8vec2'", "'ui8vec3'", "'ui8vec4'", "'int16_t'", "'i16vec2'", "'i16vec3'", 
			"'i16vec4'", "'uint16_t'", "'ui16vec2'", "'ui16vec3'", "'ui16vec4'", 
			null, null, null, null, null, null, null, null, "'int64_t'", "'i64vec2'", 
			"'i64vec3'", "'i64vec4'", "'uint64_t'", "'ui64vec2'", "'ui64vec3'", "'ui64vec4'", 
			"'image1D'", "'image2D'", "'image3D'", "'uimage1D'", "'uimage2D'", "'uimage3D'", 
			"'iimage1D'", "'iimage2D'", "'iimage3D'", "'sampler1D'", "'sampler2D'", 
			"'sampler3D'", "'sampler2DRect'", "'samplerExternalOES'", "'sampler1DShadow'", 
			"'sampler2DShadow'", "'sampler2DRectShadow'", "'sampler1DArray'", "'sampler2DArray'", 
			"'sampler1DArrayShadow'", "'sampler2DArrayShadow'", "'isampler1D'", "'isampler2D'", 
			"'isampler2DRect'", "'isampler3D'", "'isampler1DArray'", "'isampler2DArray'", 
			"'usampler1D'", "'usampler2D'", "'usampler2DRect'", "'usampler3D'", "'usampler1DArray'", 
			"'usampler2DArray'", "'sampler2DMS'", "'isampler2DMS'", "'usampler2DMS'", 
			"'sampler2DMSArray'", "'isampler2DMSArray'", "'usampler2DMSArray'", "'image2DRect'", 
			"'image1DArray'", "'image2DArray'", "'image2DMS'", "'image2DMSArray'", 
			"'iimage2DRect'", "'iimage1DArray'", "'iimage2DArray'", "'iimage2DMS'", 
			"'iimage2DMSArray'", "'uimage2DRect'", "'uimage1DArray'", "'uimage2DArray'", 
			"'uimage2DMS'", "'uimage2DMSArray'", "'samplerCube'", "'samplerCubeShadow'", 
			"'samplerBuffer'", "'samplerCubeArray'", "'samplerCubeArrayShadow'", 
			"'isamplerCube'", "'isamplerBuffer'", "'isamplerCubeArray'", "'usamplerCube'", 
			"'usamplerBuffer'", "'usamplerCubeArray'", "'imageCube'", "'imageBuffer'", 
			"'imageCubeArray'", "'iimageCube'", "'iimageBuffer'", "'iimageCubeArray'", 
			"'uimageCube'", "'uimageBuffer'", "'uimageCubeArray'", "'++'", "'--'", 
			"'void'", "'<<'", "'>>'", "'<='", "'>='", "'=='", "'!='", "'&&'", "'^^'", 
			"'||'", "'*='", "'/='", "'%='", "'+='", "'-='", "'<<='", "'>>='", "'&='", 
			"'^='", "'|='", null, null, "'{'", "'}'", "';'", "'['", "']'", "','", 
			"'.'", "'+'", "'-'", "'!'", "'~'", "'*'", "'/'", "'%'", "'<'", "'>'", 
			"'&'", "'|'", "'^'", "'?'", "'='", null, null, "'#'", null, null, null, 
			null, null, "'extension'", "'version'", "'pragma'", "'debug'", "'optimize'", 
			null, "'on'", "'off'", "'all'", "'require'", "'enable'", "'warn'", "'disable'", 
			null, null, null, "'STDGL'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COLON", "UNIFORM", "BUFFER", "IN", "OUT", "INOUT", "HIGHP", "MEDIUMP", 
			"LOWP", "PRECISION", "CONST", "PRECISE", "INVARIANT", "SMOOTH", "FLAT", 
			"NOPERSPECTIVE", "CENTROID", "SAMPLE", "PATCH", "ATTRIBUTE", "COHERENT", 
			"VOLATILE", "RESTRICT", "VARYING", "READONLY", "WRITEONLY", "SHARED", 
			"SUBROUTINE", "DEVICECOHERENT", "QUEUEFAMILYCOHERENT", "WORKGROUPCOHERENT", 
			"SUBGROUPCOHERENT", "NONPRIVATE", "LAYOUT", "ATOMIC_UINT", "STRUCT", 
			"IF", "ELSE", "SWITCH", "CASE", "DEFAULT", "WHILE", "DO", "FOR", "CONTINUE", 
			"BREAK", "RETURN", "DISCARD", "DEMOTE", "UINT16CONSTANT", "INT16CONSTANT", 
			"UINT32CONSTANT", "INT32CONSTANT", "UINT64CONSTANT", "INT64CONSTANT", 
			"FLOAT16CONSTANT", "FLOAT32CONSTANT", "FLOAT64CONSTANT", "BOOLCONSTANT", 
			"BOOL", "BVEC2", "BVEC3", "BVEC4", "FLOAT16", "F16VEC2", "F16VEC3", "F16VEC4", 
			"F16MAT2X2", "F16MAT2X3", "F16MAT2X4", "F16MAT3X2", "F16MAT3X3", "F16MAT3X4", 
			"F16MAT4X2", "F16MAT4X3", "F16MAT4X4", "FLOAT32", "F32VEC2", "F32VEC3", 
			"F32VEC4", "F32MAT2X2", "F32MAT2X3", "F32MAT2X4", "F32MAT3X2", "F32MAT3X3", 
			"F32MAT3X4", "F32MAT4X2", "F32MAT4X3", "F32MAT4X4", "FLOAT64", "F64VEC2", 
			"F64VEC3", "F64VEC4", "F64MAT2X2", "F64MAT2X3", "F64MAT2X4", "F64MAT3X2", 
			"F64MAT3X3", "F64MAT3X4", "F64MAT4X2", "F64MAT4X3", "F64MAT4X4", "INT8", 
			"I8VEC2", "I8VEC3", "I8VEC4", "UINT8", "UI8VEC2", "UI8VEC3", "UI8VEC4", 
			"INT16", "I16VEC2", "I16VEC3", "I16VEC4", "UINT16", "UI16VEC2", "UI16VEC3", 
			"UI16VEC4", "INT32", "I32VEC2", "I32VEC3", "I32VEC4", "UINT32", "UI32VEC2", 
			"UI32VEC3", "UI32VEC4", "INT64", "I64VEC2", "I64VEC3", "I64VEC4", "UINT64", 
			"UI64VEC2", "UI64VEC3", "UI64VEC4", "IMAGE1D", "IMAGE2D", "IMAGE3D", 
			"UIMAGE1D", "UIMAGE2D", "UIMAGE3D", "IIMAGE1D", "IIMAGE2D", "IIMAGE3D", 
			"SAMPLER1D", "SAMPLER2D", "SAMPLER3D", "SAMPLER2DRECT", "SAMPLEREXTERNALOES", 
			"SAMPLER1DSHADOW", "SAMPLER2DSHADOW", "SAMPLER2DRECTSHADOW", "SAMPLER1DARRAY", 
			"SAMPLER2DARRAY", "SAMPLER1DARRAYSHADOW", "SAMPLER2DARRAYSHADOW", "ISAMPLER1D", 
			"ISAMPLER2D", "ISAMPLER2DRECT", "ISAMPLER3D", "ISAMPLER1DARRAY", "ISAMPLER2DARRAY", 
			"USAMPLER1D", "USAMPLER2D", "USAMPLER2DRECT", "USAMPLER3D", "USAMPLER1DARRAY", 
			"USAMPLER2DARRAY", "SAMPLER2DMS", "ISAMPLER2DMS", "USAMPLER2DMS", "SAMPLER2DMSARRAY", 
			"ISAMPLER2DMSARRAY", "USAMPLER2DMSARRAY", "IMAGE2DRECT", "IMAGE1DARRAY", 
			"IMAGE2DARRAY", "IMAGE2DMS", "IMAGE2DMSARRAY", "IIMAGE2DRECT", "IIMAGE1DARRAY", 
			"IIMAGE2DARRAY", "IIMAGE2DMS", "IIMAGE2DMSARRAY", "UIMAGE2DRECT", "UIMAGE1DARRAY", 
			"UIMAGE2DARRAY", "UIMAGE2DMS", "UIMAGE2DMSARRAY", "SAMPLERCUBE", "SAMPLERCUBESHADOW", 
			"SAMPLERBUFFER", "SAMPLERCUBEARRAY", "SAMPLERCUBEARRAYSHADOW", "ISAMPLERCUBE", 
			"ISAMPLERBUFFER", "ISAMPLERCUBEARRAY", "USAMPLERCUBE", "USAMPLERBUFFER", 
			"USAMPLERCUBEARRAY", "IMAGECUBE", "IMAGEBUFFER", "IMAGECUBEARRAY", "IIMAGECUBE", 
			"IIMAGEBUFFER", "IIMAGECUBEARRAY", "UIMAGECUBE", "UIMAGEBUFFER", "UIMAGECUBEARRAY", 
			"INC_OP", "DEC_OP", "VOID", "LEFT_OP", "RIGHT_OP", "LE_OP", "GE_OP", 
			"EQ_OP", "NE_OP", "AND_OP", "XOR_OP", "OR_OP", "MUL_ASSIGN", "DIV_ASSIGN", 
			"MOD_ASSIGN", "ADD_ASSIGN", "SUB_ASSIGN", "LEFT_ASSIGN", "RIGHT_ASSIGN", 
			"AND_ASSIGN", "XOR_ASSIGN", "OR_ASSIGN", "LPAREN", "RPAREN", "LBRACE", 
			"RBRACE", "SEMICOLON", "LBRACKET", "RBRACKET", "COMMA", "DOT", "PLUS_OP", 
			"MINUS_OP", "NOT_OP", "BNEG_OP", "TIMES_OP", "DIV_OP", "MOD_OP", "LT_OP", 
			"GT_OP", "BAND_OP", "BOR_OP", "BXOR_OP", "QUERY_OP", "ASSIGN_OP", "PP_ENTER_MODE", 
			"PP_EMPTY", "NR", "IDENTIFIER", "LINE_CONTINUE", "COMMENT", "WS", "EOL", 
			"EXTENSION", "VERSION", "PRAGMA", "PRAGMA_DEBUG", "PRAGMA_OPTIMIZE", 
			"PRAGMA_INVARIANT", "NR_ON", "NR_OFF", "NR_ALL", "NR_REQUIRE", "NR_ENABLE", 
			"NR_WARN", "NR_DISABLE", "NR_COLON", "NR_LPAREN", "NR_RPAREN", "NR_STDGL", 
			"NR_INTCONSTANT", "NR_IDENTIFIER", "NR_WS", "NR_LINE_CONTINUE", "NR_EOL", 
			"PP_LINE_CONTINUE", "PP_EOL", "PP_CONTENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GLSLParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GLSLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TranslationUnitContext extends ParserRuleContext {
		public VersionStatementContext versionStatement() {
			return getRuleContext(VersionStatementContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GLSLParser.EOF, 0); }
		public List<ExternalDeclarationContext> externalDeclaration() {
			return getRuleContexts(ExternalDeclarationContext.class);
		}
		public ExternalDeclarationContext externalDeclaration(int i) {
			return getRuleContext(ExternalDeclarationContext.class,i);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_translationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			versionStatement();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (PRECISION - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (VOID - 194)) | (1L << (SEMICOLON - 194)) | (1L << (LBRACKET - 194)) | (1L << (NR - 194)) | (1L << (IDENTIFIER - 194)))) != 0)) {
				{
				{
				setState(163);
				externalDeclaration();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(169);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionStatementContext extends ParserRuleContext {
		public TerminalNode NR() { return getToken(GLSLParser.NR, 0); }
		public TerminalNode VERSION() { return getToken(GLSLParser.VERSION, 0); }
		public TerminalNode NR_INTCONSTANT() { return getToken(GLSLParser.NR_INTCONSTANT, 0); }
		public TerminalNode NR_EOL() { return getToken(GLSLParser.NR_EOL, 0); }
		public TerminalNode NR_IDENTIFIER() { return getToken(GLSLParser.NR_IDENTIFIER, 0); }
		public VersionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionStatement; }
	}

	public final VersionStatementContext versionStatement() throws RecognitionException {
		VersionStatementContext _localctx = new VersionStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_versionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(171);
				match(NR);
				setState(172);
				match(VERSION);
				setState(173);
				match(NR_INTCONSTANT);
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NR_IDENTIFIER) {
					{
					setState(174);
					match(NR_IDENTIFIER);
					}
				}

				setState(177);
				match(NR_EOL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExternalDeclarationContext extends ParserRuleContext {
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public PragmaStatementContext pragmaStatement() {
			return getRuleContext(PragmaStatementContext.class,0);
		}
		public ExtensionStatementContext extensionStatement() {
			return getRuleContext(ExtensionStatementContext.class,0);
		}
		public LayoutDefaultsContext layoutDefaults() {
			return getRuleContext(LayoutDefaultsContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public ExternalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_externalDeclaration; }
	}

	public final ExternalDeclarationContext externalDeclaration() throws RecognitionException {
		ExternalDeclarationContext _localctx = new ExternalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_externalDeclaration);
		try {
			setState(186);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				functionDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				pragmaStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(183);
				extensionStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(184);
				layoutDefaults();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(185);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PragmaStatementContext extends ParserRuleContext {
		public TerminalNode NR() { return getToken(GLSLParser.NR, 0); }
		public TerminalNode PRAGMA() { return getToken(GLSLParser.PRAGMA, 0); }
		public TerminalNode NR_EOL() { return getToken(GLSLParser.NR_EOL, 0); }
		public TerminalNode NR_LPAREN() { return getToken(GLSLParser.NR_LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public TerminalNode PRAGMA_INVARIANT() { return getToken(GLSLParser.PRAGMA_INVARIANT, 0); }
		public TerminalNode NR_ALL() { return getToken(GLSLParser.NR_ALL, 0); }
		public TerminalNode NR_RPAREN() { return getToken(GLSLParser.NR_RPAREN, 0); }
		public TerminalNode NR_IDENTIFIER() { return getToken(GLSLParser.NR_IDENTIFIER, 0); }
		public TerminalNode NR_STDGL() { return getToken(GLSLParser.NR_STDGL, 0); }
		public TerminalNode PRAGMA_DEBUG() { return getToken(GLSLParser.PRAGMA_DEBUG, 0); }
		public TerminalNode PRAGMA_OPTIMIZE() { return getToken(GLSLParser.PRAGMA_OPTIMIZE, 0); }
		public TerminalNode NR_ON() { return getToken(GLSLParser.NR_ON, 0); }
		public TerminalNode NR_OFF() { return getToken(GLSLParser.NR_OFF, 0); }
		public PragmaStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragmaStatement; }
	}

	public final PragmaStatementContext pragmaStatement() throws RecognitionException {
		PragmaStatementContext _localctx = new PragmaStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_pragmaStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(NR);
			setState(189);
			match(PRAGMA);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NR_STDGL) {
				{
				setState(190);
				match(NR_STDGL);
				}
			}

			setState(202);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRAGMA_DEBUG:
			case PRAGMA_OPTIMIZE:
				{
				setState(193);
				_la = _input.LA(1);
				if ( !(_la==PRAGMA_DEBUG || _la==PRAGMA_OPTIMIZE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(194);
				match(NR_LPAREN);
				setState(195);
				_la = _input.LA(1);
				if ( !(_la==NR_ON || _la==NR_OFF) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(196);
				match(RPAREN);
				}
				break;
			case PRAGMA_INVARIANT:
				{
				setState(197);
				match(PRAGMA_INVARIANT);
				setState(198);
				match(NR_LPAREN);
				setState(199);
				match(NR_ALL);
				setState(200);
				match(NR_RPAREN);
				}
				break;
			case NR_IDENTIFIER:
				{
				setState(201);
				match(NR_IDENTIFIER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(204);
			match(NR_EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExtensionStatementContext extends ParserRuleContext {
		public TerminalNode NR() { return getToken(GLSLParser.NR, 0); }
		public TerminalNode EXTENSION() { return getToken(GLSLParser.EXTENSION, 0); }
		public TerminalNode NR_IDENTIFIER() { return getToken(GLSLParser.NR_IDENTIFIER, 0); }
		public TerminalNode NR_EOL() { return getToken(GLSLParser.NR_EOL, 0); }
		public TerminalNode NR_COLON() { return getToken(GLSLParser.NR_COLON, 0); }
		public ExtensionStateContext extensionState() {
			return getRuleContext(ExtensionStateContext.class,0);
		}
		public ExtensionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extensionStatement; }
	}

	public final ExtensionStatementContext extensionStatement() throws RecognitionException {
		ExtensionStatementContext _localctx = new ExtensionStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_extensionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(NR);
			setState(207);
			match(EXTENSION);
			setState(208);
			match(NR_IDENTIFIER);
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NR_COLON) {
				{
				setState(209);
				match(NR_COLON);
				setState(210);
				extensionState();
				}
			}

			setState(213);
			match(NR_EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExtensionStateContext extends ParserRuleContext {
		public TerminalNode NR_REQUIRE() { return getToken(GLSLParser.NR_REQUIRE, 0); }
		public TerminalNode NR_ENABLE() { return getToken(GLSLParser.NR_ENABLE, 0); }
		public TerminalNode NR_WARN() { return getToken(GLSLParser.NR_WARN, 0); }
		public TerminalNode NR_DISABLE() { return getToken(GLSLParser.NR_DISABLE, 0); }
		public ExtensionStateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extensionState; }
	}

	public final ExtensionStateContext extensionState() throws RecognitionException {
		ExtensionStateContext _localctx = new ExtensionStateContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_extensionState);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_la = _input.LA(1);
			if ( !(((((_la - 271)) & ~0x3f) == 0 && ((1L << (_la - 271)) & ((1L << (NR_REQUIRE - 271)) | (1L << (NR_ENABLE - 271)) | (1L << (NR_WARN - 271)) | (1L << (NR_DISABLE - 271)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LayoutDefaultsContext extends ParserRuleContext {
		public LayoutQualifierContext layoutQualifier() {
			return getRuleContext(LayoutQualifierContext.class,0);
		}
		public LayoutModesContext layoutModes() {
			return getRuleContext(LayoutModesContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public LayoutDefaultsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layoutDefaults; }
	}

	public final LayoutDefaultsContext layoutDefaults() throws RecognitionException {
		LayoutDefaultsContext _localctx = new LayoutDefaultsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_layoutDefaults);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			layoutQualifier();
			setState(218);
			layoutModes();
			setState(219);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LayoutModesContext extends ParserRuleContext {
		public TerminalNode UNIFORM() { return getToken(GLSLParser.UNIFORM, 0); }
		public TerminalNode IN() { return getToken(GLSLParser.IN, 0); }
		public TerminalNode OUT() { return getToken(GLSLParser.OUT, 0); }
		public TerminalNode BUFFER() { return getToken(GLSLParser.BUFFER, 0); }
		public LayoutModesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layoutModes; }
	}

	public final LayoutModesContext layoutModes() throws RecognitionException {
		LayoutModesContext _localctx = new LayoutModesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_layoutModes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public FunctionPrototypeContext functionPrototype() {
			return getRuleContext(FunctionPrototypeContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			functionPrototype();
			setState(224);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableIdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public VariableIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableIdentifier; }
	}

	public final VariableIdentifierContext variableIdentifier() throws RecognitionException {
		VariableIdentifierContext _localctx = new VariableIdentifierContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_variableIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public TerminalNode INT16CONSTANT() { return getToken(GLSLParser.INT16CONSTANT, 0); }
		public TerminalNode UINT16CONSTANT() { return getToken(GLSLParser.UINT16CONSTANT, 0); }
		public TerminalNode INT32CONSTANT() { return getToken(GLSLParser.INT32CONSTANT, 0); }
		public TerminalNode UINT32CONSTANT() { return getToken(GLSLParser.UINT32CONSTANT, 0); }
		public TerminalNode INT64CONSTANT() { return getToken(GLSLParser.INT64CONSTANT, 0); }
		public TerminalNode UINT64CONSTANT() { return getToken(GLSLParser.UINT64CONSTANT, 0); }
		public TerminalNode FLOAT16CONSTANT() { return getToken(GLSLParser.FLOAT16CONSTANT, 0); }
		public TerminalNode FLOAT32CONSTANT() { return getToken(GLSLParser.FLOAT32CONSTANT, 0); }
		public TerminalNode FLOAT64CONSTANT() { return getToken(GLSLParser.FLOAT64CONSTANT, 0); }
		public TerminalNode BOOLCONSTANT() { return getToken(GLSLParser.BOOLCONSTANT, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_primaryExpression);
		try {
			setState(243);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				variableIdentifier();
				}
				break;
			case INT16CONSTANT:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				match(INT16CONSTANT);
				}
				break;
			case UINT16CONSTANT:
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				match(UINT16CONSTANT);
				}
				break;
			case INT32CONSTANT:
				enterOuterAlt(_localctx, 4);
				{
				setState(231);
				match(INT32CONSTANT);
				}
				break;
			case UINT32CONSTANT:
				enterOuterAlt(_localctx, 5);
				{
				setState(232);
				match(UINT32CONSTANT);
				}
				break;
			case INT64CONSTANT:
				enterOuterAlt(_localctx, 6);
				{
				setState(233);
				match(INT64CONSTANT);
				}
				break;
			case UINT64CONSTANT:
				enterOuterAlt(_localctx, 7);
				{
				setState(234);
				match(UINT64CONSTANT);
				}
				break;
			case FLOAT16CONSTANT:
				enterOuterAlt(_localctx, 8);
				{
				setState(235);
				match(FLOAT16CONSTANT);
				}
				break;
			case FLOAT32CONSTANT:
				enterOuterAlt(_localctx, 9);
				{
				setState(236);
				match(FLOAT32CONSTANT);
				}
				break;
			case FLOAT64CONSTANT:
				enterOuterAlt(_localctx, 10);
				{
				setState(237);
				match(FLOAT64CONSTANT);
				}
				break;
			case BOOLCONSTANT:
				enterOuterAlt(_localctx, 11);
				{
				setState(238);
				match(BOOLCONSTANT);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 12);
				{
				setState(239);
				match(LPAREN);
				setState(240);
				expression();
				setState(241);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(GLSLParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(GLSLParser.RBRACKET, 0); }
		public TerminalNode DOT() { return getToken(GLSLParser.DOT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public TerminalNode INC_OP() { return getToken(GLSLParser.INC_OP, 0); }
		public TerminalNode DEC_OP() { return getToken(GLSLParser.DEC_OP, 0); }
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		return postfixExpression(0);
	}

	private PostfixExpressionContext postfixExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, _parentState);
		PostfixExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_postfixExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(246);
				primaryExpression();
				}
				break;
			case 2:
				{
				setState(247);
				functionCall();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(265);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PostfixExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
					setState(250);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(261);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(251);
						match(LBRACKET);
						setState(252);
						expression();
						setState(253);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						setState(255);
						match(DOT);
						setState(256);
						match(IDENTIFIER);
						}
						break;
					case 3:
						{
						setState(257);
						match(DOT);
						setState(258);
						methodCall();
						}
						break;
					case 4:
						{
						setState(259);
						match(INC_OP);
						}
						break;
					case 5:
						{
						setState(260);
						match(DEC_OP);
						}
						break;
					}
					}
					} 
				}
				setState(267);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public FunctionIdentifierContext functionIdentifier() {
			return getRuleContext(FunctionIdentifierContext.class,0);
		}
		public CallParameterListContext callParameterList() {
			return getRuleContext(CallParameterListContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_functionCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			functionIdentifier();
			setState(269);
			callParameterList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallContext extends ParserRuleContext {
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public CallParameterListContext callParameterList() {
			return getRuleContext(CallParameterListContext.class,0);
		}
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_methodCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			variableIdentifier();
			setState(272);
			callParameterList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallParameterListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public TerminalNode VOID() { return getToken(GLSLParser.VOID, 0); }
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public CallParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callParameterList; }
	}

	public final CallParameterListContext callParameterList() throws RecognitionException {
		CallParameterListContext _localctx = new CallParameterListContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_callParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(LPAREN);
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				}
				break;
			case 2:
				{
				setState(276);
				match(VOID);
				}
				break;
			case 3:
				{
				setState(277);
				assignmentExpression();
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(278);
					match(COMMA);
					setState(279);
					assignmentExpression();
					}
					}
					setState(284);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			setState(287);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionIdentifierContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public FunctionIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionIdentifier; }
	}

	public final FunctionIdentifierContext functionIdentifier() throws RecognitionException {
		FunctionIdentifierContext _localctx = new FunctionIdentifierContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_functionIdentifier);
		try {
			setState(291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(289);
				typeSpecifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				variableIdentifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_unaryExpression);
		try {
			setState(297);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATOMIC_UINT:
			case STRUCT:
			case UINT16CONSTANT:
			case INT16CONSTANT:
			case UINT32CONSTANT:
			case INT32CONSTANT:
			case UINT64CONSTANT:
			case INT64CONSTANT:
			case FLOAT16CONSTANT:
			case FLOAT32CONSTANT:
			case FLOAT64CONSTANT:
			case BOOLCONSTANT:
			case BOOL:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case FLOAT16:
			case F16VEC2:
			case F16VEC3:
			case F16VEC4:
			case F16MAT2X2:
			case F16MAT2X3:
			case F16MAT2X4:
			case F16MAT3X2:
			case F16MAT3X3:
			case F16MAT3X4:
			case F16MAT4X2:
			case F16MAT4X3:
			case F16MAT4X4:
			case FLOAT32:
			case F32VEC2:
			case F32VEC3:
			case F32VEC4:
			case F32MAT2X2:
			case F32MAT2X3:
			case F32MAT2X4:
			case F32MAT3X2:
			case F32MAT3X3:
			case F32MAT3X4:
			case F32MAT4X2:
			case F32MAT4X3:
			case F32MAT4X4:
			case FLOAT64:
			case F64VEC2:
			case F64VEC3:
			case F64VEC4:
			case F64MAT2X2:
			case F64MAT2X3:
			case F64MAT2X4:
			case F64MAT3X2:
			case F64MAT3X3:
			case F64MAT3X4:
			case F64MAT4X2:
			case F64MAT4X3:
			case F64MAT4X4:
			case INT8:
			case I8VEC2:
			case I8VEC3:
			case I8VEC4:
			case UINT8:
			case UI8VEC2:
			case UI8VEC3:
			case UI8VEC4:
			case INT16:
			case I16VEC2:
			case I16VEC3:
			case I16VEC4:
			case UINT16:
			case UI16VEC2:
			case UI16VEC3:
			case UI16VEC4:
			case INT32:
			case I32VEC2:
			case I32VEC3:
			case I32VEC4:
			case UINT32:
			case UI32VEC2:
			case UI32VEC3:
			case UI32VEC4:
			case INT64:
			case I64VEC2:
			case I64VEC3:
			case I64VEC4:
			case UINT64:
			case UI64VEC2:
			case UI64VEC3:
			case UI64VEC4:
			case IMAGE1D:
			case IMAGE2D:
			case IMAGE3D:
			case UIMAGE1D:
			case UIMAGE2D:
			case UIMAGE3D:
			case IIMAGE1D:
			case IIMAGE2D:
			case IIMAGE3D:
			case SAMPLER1D:
			case SAMPLER2D:
			case SAMPLER3D:
			case SAMPLER2DRECT:
			case SAMPLER1DSHADOW:
			case SAMPLER2DSHADOW:
			case SAMPLER2DRECTSHADOW:
			case SAMPLER1DARRAY:
			case SAMPLER2DARRAY:
			case SAMPLER1DARRAYSHADOW:
			case SAMPLER2DARRAYSHADOW:
			case ISAMPLER1D:
			case ISAMPLER2D:
			case ISAMPLER2DRECT:
			case ISAMPLER3D:
			case ISAMPLER1DARRAY:
			case ISAMPLER2DARRAY:
			case USAMPLER1D:
			case USAMPLER2D:
			case USAMPLER2DRECT:
			case USAMPLER3D:
			case USAMPLER1DARRAY:
			case USAMPLER2DARRAY:
			case SAMPLER2DMS:
			case ISAMPLER2DMS:
			case USAMPLER2DMS:
			case SAMPLER2DMSARRAY:
			case ISAMPLER2DMSARRAY:
			case USAMPLER2DMSARRAY:
			case IMAGE2DRECT:
			case IMAGE1DARRAY:
			case IMAGE2DARRAY:
			case IMAGE2DMS:
			case IMAGE2DMSARRAY:
			case IIMAGE2DRECT:
			case IIMAGE1DARRAY:
			case IIMAGE2DARRAY:
			case IIMAGE2DMS:
			case IIMAGE2DMSARRAY:
			case UIMAGE2DRECT:
			case UIMAGE1DARRAY:
			case UIMAGE2DARRAY:
			case UIMAGE2DMS:
			case UIMAGE2DMSARRAY:
			case SAMPLERCUBE:
			case SAMPLERCUBESHADOW:
			case SAMPLERBUFFER:
			case SAMPLERCUBEARRAY:
			case SAMPLERCUBEARRAYSHADOW:
			case ISAMPLERCUBE:
			case ISAMPLERBUFFER:
			case ISAMPLERCUBEARRAY:
			case USAMPLERCUBE:
			case USAMPLERBUFFER:
			case USAMPLERCUBEARRAY:
			case IMAGECUBE:
			case IMAGEBUFFER:
			case IMAGECUBEARRAY:
			case IIMAGECUBE:
			case IIMAGEBUFFER:
			case IIMAGECUBEARRAY:
			case UIMAGECUBE:
			case UIMAGEBUFFER:
			case UIMAGECUBEARRAY:
			case VOID:
			case LPAREN:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(293);
				postfixExpression(0);
				}
				break;
			case INC_OP:
			case DEC_OP:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
				enterOuterAlt(_localctx, 2);
				{
				setState(294);
				unaryOperator();
				setState(295);
				unaryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperatorContext extends ParserRuleContext {
		public TerminalNode INC_OP() { return getToken(GLSLParser.INC_OP, 0); }
		public TerminalNode DEC_OP() { return getToken(GLSLParser.DEC_OP, 0); }
		public TerminalNode PLUS_OP() { return getToken(GLSLParser.PLUS_OP, 0); }
		public TerminalNode MINUS_OP() { return getToken(GLSLParser.MINUS_OP, 0); }
		public TerminalNode NOT_OP() { return getToken(GLSLParser.NOT_OP, 0); }
		public TerminalNode BNEG_OP() { return getToken(GLSLParser.BNEG_OP, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			_la = _input.LA(1);
			if ( !(((((_la - 209)) & ~0x3f) == 0 && ((1L << (_la - 209)) & ((1L << (INC_OP - 209)) | (1L << (DEC_OP - 209)) | (1L << (PLUS_OP - 209)) | (1L << (MINUS_OP - 209)) | (1L << (NOT_OP - 209)) | (1L << (BNEG_OP - 209)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public List<TerminalNode> TIMES_OP() { return getTokens(GLSLParser.TIMES_OP); }
		public TerminalNode TIMES_OP(int i) {
			return getToken(GLSLParser.TIMES_OP, i);
		}
		public List<TerminalNode> DIV_OP() { return getTokens(GLSLParser.DIV_OP); }
		public TerminalNode DIV_OP(int i) {
			return getToken(GLSLParser.DIV_OP, i);
		}
		public List<TerminalNode> MOD_OP() { return getTokens(GLSLParser.MOD_OP); }
		public TerminalNode MOD_OP(int i) {
			return getToken(GLSLParser.MOD_OP, i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_multiplicativeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			unaryExpression();
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 244)) & ~0x3f) == 0 && ((1L << (_la - 244)) & ((1L << (TIMES_OP - 244)) | (1L << (DIV_OP - 244)) | (1L << (MOD_OP - 244)))) != 0)) {
				{
				{
				setState(302);
				_la = _input.LA(1);
				if ( !(((((_la - 244)) & ~0x3f) == 0 && ((1L << (_la - 244)) & ((1L << (TIMES_OP - 244)) | (1L << (DIV_OP - 244)) | (1L << (MOD_OP - 244)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(303);
				unaryExpression();
				}
				}
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public List<MultiplicativeExpressionContext> multiplicativeExpression() {
			return getRuleContexts(MultiplicativeExpressionContext.class);
		}
		public MultiplicativeExpressionContext multiplicativeExpression(int i) {
			return getRuleContext(MultiplicativeExpressionContext.class,i);
		}
		public List<TerminalNode> PLUS_OP() { return getTokens(GLSLParser.PLUS_OP); }
		public TerminalNode PLUS_OP(int i) {
			return getToken(GLSLParser.PLUS_OP, i);
		}
		public List<TerminalNode> MINUS_OP() { return getTokens(GLSLParser.MINUS_OP); }
		public TerminalNode MINUS_OP(int i) {
			return getToken(GLSLParser.MINUS_OP, i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_additiveExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			multiplicativeExpression();
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS_OP || _la==MINUS_OP) {
				{
				{
				setState(310);
				_la = _input.LA(1);
				if ( !(_la==PLUS_OP || _la==MINUS_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(311);
				multiplicativeExpression();
				}
				}
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShiftExpressionContext extends ParserRuleContext {
		public List<AdditiveExpressionContext> additiveExpression() {
			return getRuleContexts(AdditiveExpressionContext.class);
		}
		public AdditiveExpressionContext additiveExpression(int i) {
			return getRuleContext(AdditiveExpressionContext.class,i);
		}
		public List<TerminalNode> LEFT_OP() { return getTokens(GLSLParser.LEFT_OP); }
		public TerminalNode LEFT_OP(int i) {
			return getToken(GLSLParser.LEFT_OP, i);
		}
		public List<TerminalNode> RIGHT_OP() { return getTokens(GLSLParser.RIGHT_OP); }
		public TerminalNode RIGHT_OP(int i) {
			return getToken(GLSLParser.RIGHT_OP, i);
		}
		public ShiftExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpression; }
	}

	public final ShiftExpressionContext shiftExpression() throws RecognitionException {
		ShiftExpressionContext _localctx = new ShiftExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_shiftExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			additiveExpression();
			setState(322);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT_OP || _la==RIGHT_OP) {
				{
				{
				setState(318);
				_la = _input.LA(1);
				if ( !(_la==LEFT_OP || _la==RIGHT_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(319);
				additiveExpression();
				}
				}
				setState(324);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public List<ShiftExpressionContext> shiftExpression() {
			return getRuleContexts(ShiftExpressionContext.class);
		}
		public ShiftExpressionContext shiftExpression(int i) {
			return getRuleContext(ShiftExpressionContext.class,i);
		}
		public List<TerminalNode> LT_OP() { return getTokens(GLSLParser.LT_OP); }
		public TerminalNode LT_OP(int i) {
			return getToken(GLSLParser.LT_OP, i);
		}
		public List<TerminalNode> GT_OP() { return getTokens(GLSLParser.GT_OP); }
		public TerminalNode GT_OP(int i) {
			return getToken(GLSLParser.GT_OP, i);
		}
		public List<TerminalNode> LE_OP() { return getTokens(GLSLParser.LE_OP); }
		public TerminalNode LE_OP(int i) {
			return getToken(GLSLParser.LE_OP, i);
		}
		public List<TerminalNode> GE_OP() { return getTokens(GLSLParser.GE_OP); }
		public TerminalNode GE_OP(int i) {
			return getToken(GLSLParser.GE_OP, i);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_relationalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			shiftExpression();
			setState(330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (LE_OP - 214)) | (1L << (GE_OP - 214)) | (1L << (LT_OP - 214)) | (1L << (GT_OP - 214)))) != 0)) {
				{
				{
				setState(326);
				_la = _input.LA(1);
				if ( !(((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (LE_OP - 214)) | (1L << (GE_OP - 214)) | (1L << (LT_OP - 214)) | (1L << (GT_OP - 214)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(327);
				shiftExpression();
				}
				}
				setState(332);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public List<RelationalExpressionContext> relationalExpression() {
			return getRuleContexts(RelationalExpressionContext.class);
		}
		public RelationalExpressionContext relationalExpression(int i) {
			return getRuleContext(RelationalExpressionContext.class,i);
		}
		public List<TerminalNode> EQ_OP() { return getTokens(GLSLParser.EQ_OP); }
		public TerminalNode EQ_OP(int i) {
			return getToken(GLSLParser.EQ_OP, i);
		}
		public List<TerminalNode> NE_OP() { return getTokens(GLSLParser.NE_OP); }
		public TerminalNode NE_OP(int i) {
			return getToken(GLSLParser.NE_OP, i);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_equalityExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			relationalExpression();
			setState(338);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQ_OP || _la==NE_OP) {
				{
				{
				setState(334);
				_la = _input.LA(1);
				if ( !(_la==EQ_OP || _la==NE_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(335);
				relationalExpression();
				}
				}
				setState(340);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public List<EqualityExpressionContext> equalityExpression() {
			return getRuleContexts(EqualityExpressionContext.class);
		}
		public EqualityExpressionContext equalityExpression(int i) {
			return getRuleContext(EqualityExpressionContext.class,i);
		}
		public List<TerminalNode> BAND_OP() { return getTokens(GLSLParser.BAND_OP); }
		public TerminalNode BAND_OP(int i) {
			return getToken(GLSLParser.BAND_OP, i);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_andExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			equalityExpression();
			setState(346);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BAND_OP) {
				{
				{
				setState(342);
				match(BAND_OP);
				setState(343);
				equalityExpression();
				}
				}
				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExclusiveOrExpressionContext extends ParserRuleContext {
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public List<TerminalNode> BXOR_OP() { return getTokens(GLSLParser.BXOR_OP); }
		public TerminalNode BXOR_OP(int i) {
			return getToken(GLSLParser.BXOR_OP, i);
		}
		public ExclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusiveOrExpression; }
	}

	public final ExclusiveOrExpressionContext exclusiveOrExpression() throws RecognitionException {
		ExclusiveOrExpressionContext _localctx = new ExclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_exclusiveOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			andExpression();
			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BXOR_OP) {
				{
				{
				setState(350);
				match(BXOR_OP);
				setState(351);
				andExpression();
				}
				}
				setState(356);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InclusiveOrExpressionContext extends ParserRuleContext {
		public List<ExclusiveOrExpressionContext> exclusiveOrExpression() {
			return getRuleContexts(ExclusiveOrExpressionContext.class);
		}
		public ExclusiveOrExpressionContext exclusiveOrExpression(int i) {
			return getRuleContext(ExclusiveOrExpressionContext.class,i);
		}
		public List<TerminalNode> BOR_OP() { return getTokens(GLSLParser.BOR_OP); }
		public TerminalNode BOR_OP(int i) {
			return getToken(GLSLParser.BOR_OP, i);
		}
		public InclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusiveOrExpression; }
	}

	public final InclusiveOrExpressionContext inclusiveOrExpression() throws RecognitionException {
		InclusiveOrExpressionContext _localctx = new InclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inclusiveOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			exclusiveOrExpression();
			setState(362);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BOR_OP) {
				{
				{
				setState(358);
				match(BOR_OP);
				setState(359);
				exclusiveOrExpression();
				}
				}
				setState(364);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public List<InclusiveOrExpressionContext> inclusiveOrExpression() {
			return getRuleContexts(InclusiveOrExpressionContext.class);
		}
		public InclusiveOrExpressionContext inclusiveOrExpression(int i) {
			return getRuleContext(InclusiveOrExpressionContext.class,i);
		}
		public List<TerminalNode> AND_OP() { return getTokens(GLSLParser.AND_OP); }
		public TerminalNode AND_OP(int i) {
			return getToken(GLSLParser.AND_OP, i);
		}
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_logicalAndExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			inclusiveOrExpression();
			setState(370);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_OP) {
				{
				{
				setState(366);
				match(AND_OP);
				setState(367);
				inclusiveOrExpression();
				}
				}
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalXorExpressionContext extends ParserRuleContext {
		public List<LogicalAndExpressionContext> logicalAndExpression() {
			return getRuleContexts(LogicalAndExpressionContext.class);
		}
		public LogicalAndExpressionContext logicalAndExpression(int i) {
			return getRuleContext(LogicalAndExpressionContext.class,i);
		}
		public List<TerminalNode> XOR_OP() { return getTokens(GLSLParser.XOR_OP); }
		public TerminalNode XOR_OP(int i) {
			return getToken(GLSLParser.XOR_OP, i);
		}
		public LogicalXorExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalXorExpression; }
	}

	public final LogicalXorExpressionContext logicalXorExpression() throws RecognitionException {
		LogicalXorExpressionContext _localctx = new LogicalXorExpressionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_logicalXorExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			logicalAndExpression();
			setState(378);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==XOR_OP) {
				{
				{
				setState(374);
				match(XOR_OP);
				setState(375);
				logicalAndExpression();
				}
				}
				setState(380);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public List<LogicalXorExpressionContext> logicalXorExpression() {
			return getRuleContexts(LogicalXorExpressionContext.class);
		}
		public LogicalXorExpressionContext logicalXorExpression(int i) {
			return getRuleContext(LogicalXorExpressionContext.class,i);
		}
		public List<TerminalNode> OR_OP() { return getTokens(GLSLParser.OR_OP); }
		public TerminalNode OR_OP(int i) {
			return getToken(GLSLParser.OR_OP, i);
		}
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_logicalOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			logicalXorExpression();
			setState(386);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OP) {
				{
				{
				setState(382);
				match(OR_OP);
				setState(383);
				logicalXorExpression();
				}
				}
				setState(388);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public List<TerminalNode> QUERY_OP() { return getTokens(GLSLParser.QUERY_OP); }
		public TerminalNode QUERY_OP(int i) {
			return getToken(GLSLParser.QUERY_OP, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(GLSLParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GLSLParser.COLON, i);
		}
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_conditionalExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			logicalOrExpression();
			setState(397);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(390);
					match(QUERY_OP);
					setState(391);
					expression();
					setState(392);
					match(COLON);
					setState(393);
					assignmentExpression();
					}
					} 
				}
				setState(399);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_assignmentExpression);
		try {
			setState(405);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(400);
				conditionalExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				unaryExpression();
				setState(402);
				assignmentOperator();
				setState(403);
				assignmentExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode ASSIGN_OP() { return getToken(GLSLParser.ASSIGN_OP, 0); }
		public TerminalNode MUL_ASSIGN() { return getToken(GLSLParser.MUL_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(GLSLParser.DIV_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(GLSLParser.MOD_ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(GLSLParser.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(GLSLParser.SUB_ASSIGN, 0); }
		public TerminalNode LEFT_ASSIGN() { return getToken(GLSLParser.LEFT_ASSIGN, 0); }
		public TerminalNode RIGHT_ASSIGN() { return getToken(GLSLParser.RIGHT_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(GLSLParser.AND_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(GLSLParser.XOR_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(GLSLParser.OR_ASSIGN, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(407);
			_la = _input.LA(1);
			if ( !(((((_la - 221)) & ~0x3f) == 0 && ((1L << (_la - 221)) & ((1L << (MUL_ASSIGN - 221)) | (1L << (DIV_ASSIGN - 221)) | (1L << (MOD_ASSIGN - 221)) | (1L << (ADD_ASSIGN - 221)) | (1L << (SUB_ASSIGN - 221)) | (1L << (LEFT_ASSIGN - 221)) | (1L << (RIGHT_ASSIGN - 221)) | (1L << (AND_ASSIGN - 221)) | (1L << (XOR_ASSIGN - 221)) | (1L << (OR_ASSIGN - 221)) | (1L << (ASSIGN_OP - 221)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			assignmentExpression();
			setState(414);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(410);
				match(COMMA);
				setState(411);
				assignmentExpression();
				}
				}
				setState(416);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantExpressionContext extends ParserRuleContext {
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public ConstantExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantExpression; }
	}

	public final ConstantExpressionContext constantExpression() throws RecognitionException {
		ConstantExpressionContext _localctx = new ConstantExpressionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_constantExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(417);
			conditionalExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public FunctionPrototypeContext functionPrototype() {
			return getRuleContext(FunctionPrototypeContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public InitDeclaratorListContext initDeclaratorList() {
			return getRuleContext(InitDeclaratorListContext.class,0);
		}
		public TerminalNode PRECISION() { return getToken(GLSLParser.PRECISION, 0); }
		public PrecisionQualifierContext precisionQualifier() {
			return getRuleContext(PrecisionQualifierContext.class,0);
		}
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TypeQualifierContext typeQualifier() {
			return getRuleContext(TypeQualifierContext.class,0);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSLParser.IDENTIFIER, i);
		}
		public TerminalNode LBRACE() { return getToken(GLSLParser.LBRACE, 0); }
		public StructDeclarationListContext structDeclarationList() {
			return getRuleContext(StructDeclarationListContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSLParser.RBRACE, 0); }
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_declaration);
		int _la;
		try {
			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(419);
				functionPrototype();
				setState(420);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(422);
				initDeclaratorList();
				setState(423);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(425);
				match(PRECISION);
				setState(426);
				precisionQualifier();
				setState(427);
				typeSpecifier();
				setState(428);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(430);
				typeQualifier();
				setState(431);
				match(IDENTIFIER);
				setState(432);
				match(LBRACE);
				setState(433);
				structDeclarationList();
				setState(434);
				match(RBRACE);
				setState(439);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(435);
					match(IDENTIFIER);
					setState(437);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==LBRACKET) {
						{
						setState(436);
						arraySpecifier(0);
						}
					}

					}
				}

				setState(441);
				match(SEMICOLON);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(443);
				typeQualifier();
				setState(452);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(444);
					match(IDENTIFIER);
					setState(449);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(445);
						match(COMMA);
						setState(446);
						match(IDENTIFIER);
						}
						}
						setState(451);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(454);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionPrototypeContext extends ParserRuleContext {
		public FunctionHeaderContext functionHeader() {
			return getRuleContext(FunctionHeaderContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public FunctionParameterListContext functionParameterList() {
			return getRuleContext(FunctionParameterListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public FunctionPrototypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionPrototype; }
	}

	public final FunctionPrototypeContext functionPrototype() throws RecognitionException {
		FunctionPrototypeContext _localctx = new FunctionPrototypeContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_functionPrototype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(459);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(458);
				attribute();
				}
			}

			setState(461);
			functionHeader();
			setState(462);
			match(LPAREN);
			setState(463);
			functionParameterList();
			setState(464);
			match(RPAREN);
			setState(466);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(465);
				attribute();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParameterListContext extends ParserRuleContext {
		public List<ParameterDeclarationContext> parameterDeclaration() {
			return getRuleContexts(ParameterDeclarationContext.class);
		}
		public ParameterDeclarationContext parameterDeclaration(int i) {
			return getRuleContext(ParameterDeclarationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public FunctionParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameterList; }
	}

	public final FunctionParameterListContext functionParameterList() throws RecognitionException {
		FunctionParameterListContext _localctx = new FunctionParameterListContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_functionParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(476);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (VOID - 194)) | (1L << (IDENTIFIER - 194)))) != 0)) {
				{
				setState(468);
				parameterDeclaration();
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(469);
					match(COMMA);
					setState(470);
					parameterDeclaration();
					}
					}
					setState(475);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionHeaderContext extends ParserRuleContext {
		public FullySpecifiedTypeContext fullySpecifiedType() {
			return getRuleContext(FullySpecifiedTypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public FunctionHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionHeader; }
	}

	public final FunctionHeaderContext functionHeader() throws RecognitionException {
		FunctionHeaderContext _localctx = new FunctionHeaderContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_functionHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			fullySpecifiedType();
			setState(479);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclaratorContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public ParameterDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclarator; }
	}

	public final ParameterDeclaratorContext parameterDeclarator() throws RecognitionException {
		ParameterDeclaratorContext _localctx = new ParameterDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_parameterDeclarator);
		try {
			setState(488);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(481);
				typeSpecifier();
				setState(482);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(484);
				typeSpecifier();
				setState(485);
				match(IDENTIFIER);
				setState(486);
				arraySpecifier(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationContext extends ParserRuleContext {
		public ParameterDeclaratorContext parameterDeclarator() {
			return getRuleContext(ParameterDeclaratorContext.class,0);
		}
		public TypeQualifierContext typeQualifier() {
			return getRuleContext(TypeQualifierContext.class,0);
		}
		public FullySpecifiedTypeContext fullySpecifiedType() {
			return getRuleContext(FullySpecifiedTypeContext.class,0);
		}
		public ParameterDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclaration; }
	}

	public final ParameterDeclarationContext parameterDeclaration() throws RecognitionException {
		ParameterDeclarationContext _localctx = new ParameterDeclarationContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_parameterDeclaration);
		int _la;
		try {
			setState(495);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(491);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << ATTRIBUTE) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << VARYING) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << DEVICECOHERENT) | (1L << QUEUEFAMILYCOHERENT) | (1L << WORKGROUPCOHERENT) | (1L << SUBGROUPCOHERENT) | (1L << NONPRIVATE) | (1L << LAYOUT))) != 0)) {
					{
					setState(490);
					typeQualifier();
					}
				}

				setState(493);
				parameterDeclarator();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(494);
				fullySpecifiedType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public List<TerminalNode> LBRACKET() { return getTokens(GLSLParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(GLSLParser.LBRACKET, i);
		}
		public List<SingleAttributeContext> singleAttribute() {
			return getRuleContexts(SingleAttributeContext.class);
		}
		public SingleAttributeContext singleAttribute(int i) {
			return getRuleContext(SingleAttributeContext.class,i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(GLSLParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(GLSLParser.RBRACKET, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(497);
			match(LBRACKET);
			setState(498);
			match(LBRACKET);
			setState(499);
			singleAttribute();
			setState(504);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(500);
				match(COMMA);
				setState(501);
				singleAttribute();
				}
				}
				setState(506);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(507);
			match(RBRACKET);
			setState(508);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleAttributeContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSLParser.IDENTIFIER, i);
		}
		public List<TerminalNode> COLON() { return getTokens(GLSLParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GLSLParser.COLON, i);
		}
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ConstantExpressionContext constantExpression() {
			return getRuleContext(ConstantExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public SingleAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleAttribute; }
	}

	public final SingleAttributeContext singleAttribute() throws RecognitionException {
		SingleAttributeContext _localctx = new SingleAttributeContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_singleAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(510);
				match(IDENTIFIER);
				setState(511);
				match(COLON);
				setState(512);
				match(COLON);
				}
				break;
			}
			setState(515);
			match(IDENTIFIER);
			setState(520);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(516);
				match(LPAREN);
				setState(517);
				constantExpression();
				setState(518);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitDeclaratorListContext extends ParserRuleContext {
		public FullySpecifiedTypeContext fullySpecifiedType() {
			return getRuleContext(FullySpecifiedTypeContext.class,0);
		}
		public DeclarationMemberListContext declarationMemberList() {
			return getRuleContext(DeclarationMemberListContext.class,0);
		}
		public InitDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initDeclaratorList; }
	}

	public final InitDeclaratorListContext initDeclaratorList() throws RecognitionException {
		InitDeclaratorListContext _localctx = new InitDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_initDeclaratorList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
			fullySpecifiedType();
			setState(523);
			declarationMemberList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationMemberListContext extends ParserRuleContext {
		public List<DeclarationMemberContext> declarationMember() {
			return getRuleContexts(DeclarationMemberContext.class);
		}
		public DeclarationMemberContext declarationMember(int i) {
			return getRuleContext(DeclarationMemberContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public DeclarationMemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationMemberList; }
	}

	public final DeclarationMemberListContext declarationMemberList() throws RecognitionException {
		DeclarationMemberListContext _localctx = new DeclarationMemberListContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_declarationMemberList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(526);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(525);
				declarationMember();
				}
			}

			setState(532);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(528);
				match(COMMA);
				setState(529);
				declarationMember();
				}
				}
				setState(534);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationMemberContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(GLSLParser.ASSIGN_OP, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public DeclarationMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationMember; }
	}

	public final DeclarationMemberContext declarationMember() throws RecognitionException {
		DeclarationMemberContext _localctx = new DeclarationMemberContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_declarationMember);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(535);
			match(IDENTIFIER);
			setState(537);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(536);
				arraySpecifier(0);
				}
			}

			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN_OP) {
				{
				setState(539);
				match(ASSIGN_OP);
				setState(540);
				initializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FullySpecifiedTypeContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TypeQualifierContext typeQualifier() {
			return getRuleContext(TypeQualifierContext.class,0);
		}
		public FullySpecifiedTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullySpecifiedType; }
	}

	public final FullySpecifiedTypeContext fullySpecifiedType() throws RecognitionException {
		FullySpecifiedTypeContext _localctx = new FullySpecifiedTypeContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_fullySpecifiedType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(544);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << ATTRIBUTE) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << VARYING) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << DEVICECOHERENT) | (1L << QUEUEFAMILYCOHERENT) | (1L << WORKGROUPCOHERENT) | (1L << SUBGROUPCOHERENT) | (1L << NONPRIVATE) | (1L << LAYOUT))) != 0)) {
				{
				setState(543);
				typeQualifier();
				}
			}

			setState(546);
			typeSpecifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StorageQualifierContext extends ParserRuleContext {
		public TerminalNode CONST() { return getToken(GLSLParser.CONST, 0); }
		public TerminalNode IN() { return getToken(GLSLParser.IN, 0); }
		public TerminalNode OUT() { return getToken(GLSLParser.OUT, 0); }
		public TerminalNode INOUT() { return getToken(GLSLParser.INOUT, 0); }
		public TerminalNode CENTROID() { return getToken(GLSLParser.CENTROID, 0); }
		public TerminalNode PATCH() { return getToken(GLSLParser.PATCH, 0); }
		public TerminalNode SAMPLE() { return getToken(GLSLParser.SAMPLE, 0); }
		public TerminalNode UNIFORM() { return getToken(GLSLParser.UNIFORM, 0); }
		public TerminalNode VARYING() { return getToken(GLSLParser.VARYING, 0); }
		public TerminalNode ATTRIBUTE() { return getToken(GLSLParser.ATTRIBUTE, 0); }
		public TerminalNode BUFFER() { return getToken(GLSLParser.BUFFER, 0); }
		public TerminalNode SHARED() { return getToken(GLSLParser.SHARED, 0); }
		public TerminalNode COHERENT() { return getToken(GLSLParser.COHERENT, 0); }
		public TerminalNode VOLATILE() { return getToken(GLSLParser.VOLATILE, 0); }
		public TerminalNode RESTRICT() { return getToken(GLSLParser.RESTRICT, 0); }
		public TerminalNode READONLY() { return getToken(GLSLParser.READONLY, 0); }
		public TerminalNode WRITEONLY() { return getToken(GLSLParser.WRITEONLY, 0); }
		public TerminalNode SUBROUTINE() { return getToken(GLSLParser.SUBROUTINE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public TypeNameListContext typeNameList() {
			return getRuleContext(TypeNameListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public TerminalNode DEVICECOHERENT() { return getToken(GLSLParser.DEVICECOHERENT, 0); }
		public TerminalNode QUEUEFAMILYCOHERENT() { return getToken(GLSLParser.QUEUEFAMILYCOHERENT, 0); }
		public TerminalNode WORKGROUPCOHERENT() { return getToken(GLSLParser.WORKGROUPCOHERENT, 0); }
		public TerminalNode SUBGROUPCOHERENT() { return getToken(GLSLParser.SUBGROUPCOHERENT, 0); }
		public TerminalNode NONPRIVATE() { return getToken(GLSLParser.NONPRIVATE, 0); }
		public StorageQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storageQualifier; }
	}

	public final StorageQualifierContext storageQualifier() throws RecognitionException {
		StorageQualifierContext _localctx = new StorageQualifierContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_storageQualifier);
		int _la;
		try {
			setState(577);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONST:
				enterOuterAlt(_localctx, 1);
				{
				setState(548);
				match(CONST);
				}
				break;
			case IN:
				enterOuterAlt(_localctx, 2);
				{
				setState(549);
				match(IN);
				}
				break;
			case OUT:
				enterOuterAlt(_localctx, 3);
				{
				setState(550);
				match(OUT);
				}
				break;
			case INOUT:
				enterOuterAlt(_localctx, 4);
				{
				setState(551);
				match(INOUT);
				}
				break;
			case CENTROID:
				enterOuterAlt(_localctx, 5);
				{
				setState(552);
				match(CENTROID);
				}
				break;
			case PATCH:
				enterOuterAlt(_localctx, 6);
				{
				setState(553);
				match(PATCH);
				}
				break;
			case SAMPLE:
				enterOuterAlt(_localctx, 7);
				{
				setState(554);
				match(SAMPLE);
				}
				break;
			case UNIFORM:
				enterOuterAlt(_localctx, 8);
				{
				setState(555);
				match(UNIFORM);
				}
				break;
			case VARYING:
				enterOuterAlt(_localctx, 9);
				{
				setState(556);
				match(VARYING);
				}
				break;
			case ATTRIBUTE:
				enterOuterAlt(_localctx, 10);
				{
				setState(557);
				match(ATTRIBUTE);
				}
				break;
			case BUFFER:
				enterOuterAlt(_localctx, 11);
				{
				setState(558);
				match(BUFFER);
				}
				break;
			case SHARED:
				enterOuterAlt(_localctx, 12);
				{
				setState(559);
				match(SHARED);
				}
				break;
			case COHERENT:
				enterOuterAlt(_localctx, 13);
				{
				setState(560);
				match(COHERENT);
				}
				break;
			case VOLATILE:
				enterOuterAlt(_localctx, 14);
				{
				setState(561);
				match(VOLATILE);
				}
				break;
			case RESTRICT:
				enterOuterAlt(_localctx, 15);
				{
				setState(562);
				match(RESTRICT);
				}
				break;
			case READONLY:
				enterOuterAlt(_localctx, 16);
				{
				setState(563);
				match(READONLY);
				}
				break;
			case WRITEONLY:
				enterOuterAlt(_localctx, 17);
				{
				setState(564);
				match(WRITEONLY);
				}
				break;
			case SUBROUTINE:
				enterOuterAlt(_localctx, 18);
				{
				setState(565);
				match(SUBROUTINE);
				setState(570);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(566);
					match(LPAREN);
					setState(567);
					typeNameList();
					setState(568);
					match(RPAREN);
					}
				}

				}
				break;
			case DEVICECOHERENT:
				enterOuterAlt(_localctx, 19);
				{
				setState(572);
				match(DEVICECOHERENT);
				}
				break;
			case QUEUEFAMILYCOHERENT:
				enterOuterAlt(_localctx, 20);
				{
				setState(573);
				match(QUEUEFAMILYCOHERENT);
				}
				break;
			case WORKGROUPCOHERENT:
				enterOuterAlt(_localctx, 21);
				{
				setState(574);
				match(WORKGROUPCOHERENT);
				}
				break;
			case SUBGROUPCOHERENT:
				enterOuterAlt(_localctx, 22);
				{
				setState(575);
				match(SUBGROUPCOHERENT);
				}
				break;
			case NONPRIVATE:
				enterOuterAlt(_localctx, 23);
				{
				setState(576);
				match(NONPRIVATE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LayoutQualifierContext extends ParserRuleContext {
		public TerminalNode LAYOUT() { return getToken(GLSLParser.LAYOUT, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public List<LayoutQualifierIdContext> layoutQualifierId() {
			return getRuleContexts(LayoutQualifierIdContext.class);
		}
		public LayoutQualifierIdContext layoutQualifierId(int i) {
			return getRuleContext(LayoutQualifierIdContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public LayoutQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layoutQualifier; }
	}

	public final LayoutQualifierContext layoutQualifier() throws RecognitionException {
		LayoutQualifierContext _localctx = new LayoutQualifierContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_layoutQualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(579);
			match(LAYOUT);
			setState(580);
			match(LPAREN);
			setState(581);
			layoutQualifierId();
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(582);
				match(COMMA);
				setState(583);
				layoutQualifierId();
				}
				}
				setState(588);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(589);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LayoutQualifierIdContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSLParser.ASSIGN_OP, 0); }
		public ConstantExpressionContext constantExpression() {
			return getRuleContext(ConstantExpressionContext.class,0);
		}
		public TerminalNode SHARED() { return getToken(GLSLParser.SHARED, 0); }
		public LayoutQualifierIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layoutQualifierId; }
	}

	public final LayoutQualifierIdContext layoutQualifierId() throws RecognitionException {
		LayoutQualifierIdContext _localctx = new LayoutQualifierIdContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_layoutQualifierId);
		int _la;
		try {
			setState(597);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(591);
				match(IDENTIFIER);
				setState(594);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN_OP) {
					{
					setState(592);
					match(ASSIGN_OP);
					setState(593);
					constantExpression();
					}
				}

				}
				break;
			case SHARED:
				enterOuterAlt(_localctx, 2);
				{
				setState(596);
				match(SHARED);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrecisionQualifierContext extends ParserRuleContext {
		public TerminalNode HIGHP() { return getToken(GLSLParser.HIGHP, 0); }
		public TerminalNode MEDIUMP() { return getToken(GLSLParser.MEDIUMP, 0); }
		public TerminalNode LOWP() { return getToken(GLSLParser.LOWP, 0); }
		public PrecisionQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_precisionQualifier; }
	}

	public final PrecisionQualifierContext precisionQualifier() throws RecognitionException {
		PrecisionQualifierContext _localctx = new PrecisionQualifierContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_precisionQualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(599);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterpolationQualifierContext extends ParserRuleContext {
		public TerminalNode SMOOTH() { return getToken(GLSLParser.SMOOTH, 0); }
		public TerminalNode FLAT() { return getToken(GLSLParser.FLAT, 0); }
		public TerminalNode NOPERSPECTIVE() { return getToken(GLSLParser.NOPERSPECTIVE, 0); }
		public InterpolationQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interpolationQualifier; }
	}

	public final InterpolationQualifierContext interpolationQualifier() throws RecognitionException {
		InterpolationQualifierContext _localctx = new InterpolationQualifierContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_interpolationQualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InvariantQualifierContext extends ParserRuleContext {
		public TerminalNode INVARIANT() { return getToken(GLSLParser.INVARIANT, 0); }
		public InvariantQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invariantQualifier; }
	}

	public final InvariantQualifierContext invariantQualifier() throws RecognitionException {
		InvariantQualifierContext _localctx = new InvariantQualifierContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_invariantQualifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(603);
			match(INVARIANT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PreciseQualifierContext extends ParserRuleContext {
		public TerminalNode PRECISE() { return getToken(GLSLParser.PRECISE, 0); }
		public PreciseQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preciseQualifier; }
	}

	public final PreciseQualifierContext preciseQualifier() throws RecognitionException {
		PreciseQualifierContext _localctx = new PreciseQualifierContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_preciseQualifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(605);
			match(PRECISE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeQualifierContext extends ParserRuleContext {
		public List<StorageQualifierContext> storageQualifier() {
			return getRuleContexts(StorageQualifierContext.class);
		}
		public StorageQualifierContext storageQualifier(int i) {
			return getRuleContext(StorageQualifierContext.class,i);
		}
		public List<LayoutQualifierContext> layoutQualifier() {
			return getRuleContexts(LayoutQualifierContext.class);
		}
		public LayoutQualifierContext layoutQualifier(int i) {
			return getRuleContext(LayoutQualifierContext.class,i);
		}
		public List<PrecisionQualifierContext> precisionQualifier() {
			return getRuleContexts(PrecisionQualifierContext.class);
		}
		public PrecisionQualifierContext precisionQualifier(int i) {
			return getRuleContext(PrecisionQualifierContext.class,i);
		}
		public List<InterpolationQualifierContext> interpolationQualifier() {
			return getRuleContexts(InterpolationQualifierContext.class);
		}
		public InterpolationQualifierContext interpolationQualifier(int i) {
			return getRuleContext(InterpolationQualifierContext.class,i);
		}
		public List<InvariantQualifierContext> invariantQualifier() {
			return getRuleContexts(InvariantQualifierContext.class);
		}
		public InvariantQualifierContext invariantQualifier(int i) {
			return getRuleContext(InvariantQualifierContext.class,i);
		}
		public List<PreciseQualifierContext> preciseQualifier() {
			return getRuleContexts(PreciseQualifierContext.class);
		}
		public PreciseQualifierContext preciseQualifier(int i) {
			return getRuleContext(PreciseQualifierContext.class,i);
		}
		public TypeQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeQualifier; }
	}

	public final TypeQualifierContext typeQualifier() throws RecognitionException {
		TypeQualifierContext _localctx = new TypeQualifierContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_typeQualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(613); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(613);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case UNIFORM:
				case BUFFER:
				case IN:
				case OUT:
				case INOUT:
				case CONST:
				case CENTROID:
				case SAMPLE:
				case PATCH:
				case ATTRIBUTE:
				case COHERENT:
				case VOLATILE:
				case RESTRICT:
				case VARYING:
				case READONLY:
				case WRITEONLY:
				case SHARED:
				case SUBROUTINE:
				case DEVICECOHERENT:
				case QUEUEFAMILYCOHERENT:
				case WORKGROUPCOHERENT:
				case SUBGROUPCOHERENT:
				case NONPRIVATE:
					{
					setState(607);
					storageQualifier();
					}
					break;
				case LAYOUT:
					{
					setState(608);
					layoutQualifier();
					}
					break;
				case HIGHP:
				case MEDIUMP:
				case LOWP:
					{
					setState(609);
					precisionQualifier();
					}
					break;
				case SMOOTH:
				case FLAT:
				case NOPERSPECTIVE:
					{
					setState(610);
					interpolationQualifier();
					}
					break;
				case INVARIANT:
					{
					setState(611);
					invariantQualifier();
					}
					break;
				case PRECISE:
					{
					setState(612);
					preciseQualifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(615); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << ATTRIBUTE) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << VARYING) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << DEVICECOHERENT) | (1L << QUEUEFAMILYCOHERENT) | (1L << WORKGROUPCOHERENT) | (1L << SUBGROUPCOHERENT) | (1L << NONPRIVATE) | (1L << LAYOUT))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameListContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSLParser.IDENTIFIER, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public TypeNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeNameList; }
	}

	public final TypeNameListContext typeNameList() throws RecognitionException {
		TypeNameListContext _localctx = new TypeNameListContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_typeNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			match(IDENTIFIER);
			setState(622);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(618);
				match(COMMA);
				setState(619);
				match(IDENTIFIER);
				}
				}
				setState(624);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSpecifierContext extends ParserRuleContext {
		public TypeSpecifierNonarrayContext typeSpecifierNonarray() {
			return getRuleContext(TypeSpecifierNonarrayContext.class,0);
		}
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public TypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpecifier; }
	}

	public final TypeSpecifierContext typeSpecifier() throws RecognitionException {
		TypeSpecifierContext _localctx = new TypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_typeSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(625);
			typeSpecifierNonarray();
			setState(627);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(626);
				arraySpecifier(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArraySpecifierContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(GLSLParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(GLSLParser.RBRACKET, 0); }
		public ConstantExpressionContext constantExpression() {
			return getRuleContext(ConstantExpressionContext.class,0);
		}
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public ArraySpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arraySpecifier; }
	}

	public final ArraySpecifierContext arraySpecifier() throws RecognitionException {
		return arraySpecifier(0);
	}

	private ArraySpecifierContext arraySpecifier(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArraySpecifierContext _localctx = new ArraySpecifierContext(_ctx, _parentState);
		ArraySpecifierContext _prevctx = _localctx;
		int _startState = 112;
		enterRecursionRule(_localctx, 112, RULE_arraySpecifier, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(630);
			match(LBRACKET);
			setState(632);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 35)) & ~0x3f) == 0 && ((1L << (_la - 35)) & ((1L << (ATOMIC_UINT - 35)) | (1L << (STRUCT - 35)) | (1L << (UINT16CONSTANT - 35)) | (1L << (INT16CONSTANT - 35)) | (1L << (UINT32CONSTANT - 35)) | (1L << (INT32CONSTANT - 35)) | (1L << (UINT64CONSTANT - 35)) | (1L << (INT64CONSTANT - 35)) | (1L << (FLOAT16CONSTANT - 35)) | (1L << (FLOAT32CONSTANT - 35)) | (1L << (FLOAT64CONSTANT - 35)) | (1L << (BOOLCONSTANT - 35)) | (1L << (BOOL - 35)) | (1L << (BVEC2 - 35)) | (1L << (BVEC3 - 35)) | (1L << (BVEC4 - 35)) | (1L << (FLOAT16 - 35)) | (1L << (F16VEC2 - 35)) | (1L << (F16VEC3 - 35)) | (1L << (F16VEC4 - 35)) | (1L << (F16MAT2X2 - 35)) | (1L << (F16MAT2X3 - 35)) | (1L << (F16MAT2X4 - 35)) | (1L << (F16MAT3X2 - 35)) | (1L << (F16MAT3X3 - 35)) | (1L << (F16MAT3X4 - 35)) | (1L << (F16MAT4X2 - 35)) | (1L << (F16MAT4X3 - 35)) | (1L << (F16MAT4X4 - 35)) | (1L << (FLOAT32 - 35)) | (1L << (F32VEC2 - 35)) | (1L << (F32VEC3 - 35)) | (1L << (F32VEC4 - 35)) | (1L << (F32MAT2X2 - 35)) | (1L << (F32MAT2X3 - 35)) | (1L << (F32MAT2X4 - 35)) | (1L << (F32MAT3X2 - 35)) | (1L << (F32MAT3X3 - 35)) | (1L << (F32MAT3X4 - 35)) | (1L << (F32MAT4X2 - 35)) | (1L << (F32MAT4X3 - 35)) | (1L << (F32MAT4X4 - 35)) | (1L << (FLOAT64 - 35)) | (1L << (F64VEC2 - 35)) | (1L << (F64VEC3 - 35)) | (1L << (F64VEC4 - 35)) | (1L << (F64MAT2X2 - 35)) | (1L << (F64MAT2X3 - 35)) | (1L << (F64MAT2X4 - 35)) | (1L << (F64MAT3X2 - 35)) | (1L << (F64MAT3X3 - 35)))) != 0) || ((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (F64MAT3X4 - 99)) | (1L << (F64MAT4X2 - 99)) | (1L << (F64MAT4X3 - 99)) | (1L << (F64MAT4X4 - 99)) | (1L << (INT8 - 99)) | (1L << (I8VEC2 - 99)) | (1L << (I8VEC3 - 99)) | (1L << (I8VEC4 - 99)) | (1L << (UINT8 - 99)) | (1L << (UI8VEC2 - 99)) | (1L << (UI8VEC3 - 99)) | (1L << (UI8VEC4 - 99)) | (1L << (INT16 - 99)) | (1L << (I16VEC2 - 99)) | (1L << (I16VEC3 - 99)) | (1L << (I16VEC4 - 99)) | (1L << (UINT16 - 99)) | (1L << (UI16VEC2 - 99)) | (1L << (UI16VEC3 - 99)) | (1L << (UI16VEC4 - 99)) | (1L << (INT32 - 99)) | (1L << (I32VEC2 - 99)) | (1L << (I32VEC3 - 99)) | (1L << (I32VEC4 - 99)) | (1L << (UINT32 - 99)) | (1L << (UI32VEC2 - 99)) | (1L << (UI32VEC3 - 99)) | (1L << (UI32VEC4 - 99)) | (1L << (INT64 - 99)) | (1L << (I64VEC2 - 99)) | (1L << (I64VEC3 - 99)) | (1L << (I64VEC4 - 99)) | (1L << (UINT64 - 99)) | (1L << (UI64VEC2 - 99)) | (1L << (UI64VEC3 - 99)) | (1L << (UI64VEC4 - 99)) | (1L << (IMAGE1D - 99)) | (1L << (IMAGE2D - 99)) | (1L << (IMAGE3D - 99)) | (1L << (UIMAGE1D - 99)) | (1L << (UIMAGE2D - 99)) | (1L << (UIMAGE3D - 99)) | (1L << (IIMAGE1D - 99)) | (1L << (IIMAGE2D - 99)) | (1L << (IIMAGE3D - 99)) | (1L << (SAMPLER1D - 99)) | (1L << (SAMPLER2D - 99)) | (1L << (SAMPLER3D - 99)) | (1L << (SAMPLER2DRECT - 99)) | (1L << (SAMPLER1DSHADOW - 99)) | (1L << (SAMPLER2DSHADOW - 99)) | (1L << (SAMPLER2DRECTSHADOW - 99)) | (1L << (SAMPLER1DARRAY - 99)) | (1L << (SAMPLER2DARRAY - 99)) | (1L << (SAMPLER1DARRAYSHADOW - 99)) | (1L << (SAMPLER2DARRAYSHADOW - 99)) | (1L << (ISAMPLER1D - 99)) | (1L << (ISAMPLER2D - 99)) | (1L << (ISAMPLER2DRECT - 99)) | (1L << (ISAMPLER3D - 99)) | (1L << (ISAMPLER1DARRAY - 99)) | (1L << (ISAMPLER2DARRAY - 99)) | (1L << (USAMPLER1D - 99)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (USAMPLER2D - 163)) | (1L << (USAMPLER2DRECT - 163)) | (1L << (USAMPLER3D - 163)) | (1L << (USAMPLER1DARRAY - 163)) | (1L << (USAMPLER2DARRAY - 163)) | (1L << (SAMPLER2DMS - 163)) | (1L << (ISAMPLER2DMS - 163)) | (1L << (USAMPLER2DMS - 163)) | (1L << (SAMPLER2DMSARRAY - 163)) | (1L << (ISAMPLER2DMSARRAY - 163)) | (1L << (USAMPLER2DMSARRAY - 163)) | (1L << (IMAGE2DRECT - 163)) | (1L << (IMAGE1DARRAY - 163)) | (1L << (IMAGE2DARRAY - 163)) | (1L << (IMAGE2DMS - 163)) | (1L << (IMAGE2DMSARRAY - 163)) | (1L << (IIMAGE2DRECT - 163)) | (1L << (IIMAGE1DARRAY - 163)) | (1L << (IIMAGE2DARRAY - 163)) | (1L << (IIMAGE2DMS - 163)) | (1L << (IIMAGE2DMSARRAY - 163)) | (1L << (UIMAGE2DRECT - 163)) | (1L << (UIMAGE1DARRAY - 163)) | (1L << (UIMAGE2DARRAY - 163)) | (1L << (UIMAGE2DMS - 163)) | (1L << (UIMAGE2DMSARRAY - 163)) | (1L << (SAMPLERCUBE - 163)) | (1L << (SAMPLERCUBESHADOW - 163)) | (1L << (SAMPLERBUFFER - 163)) | (1L << (SAMPLERCUBEARRAY - 163)) | (1L << (SAMPLERCUBEARRAYSHADOW - 163)) | (1L << (ISAMPLERCUBE - 163)) | (1L << (ISAMPLERBUFFER - 163)) | (1L << (ISAMPLERCUBEARRAY - 163)) | (1L << (USAMPLERCUBE - 163)) | (1L << (USAMPLERBUFFER - 163)) | (1L << (USAMPLERCUBEARRAY - 163)) | (1L << (IMAGECUBE - 163)) | (1L << (IMAGEBUFFER - 163)) | (1L << (IMAGECUBEARRAY - 163)) | (1L << (IIMAGECUBE - 163)) | (1L << (IIMAGEBUFFER - 163)) | (1L << (IIMAGECUBEARRAY - 163)) | (1L << (UIMAGECUBE - 163)) | (1L << (UIMAGEBUFFER - 163)) | (1L << (UIMAGECUBEARRAY - 163)) | (1L << (INC_OP - 163)) | (1L << (DEC_OP - 163)) | (1L << (VOID - 163)))) != 0) || ((((_la - 231)) & ~0x3f) == 0 && ((1L << (_la - 231)) & ((1L << (LPAREN - 231)) | (1L << (PLUS_OP - 231)) | (1L << (MINUS_OP - 231)) | (1L << (NOT_OP - 231)) | (1L << (BNEG_OP - 231)) | (1L << (IDENTIFIER - 231)))) != 0)) {
				{
				setState(631);
				constantExpression();
				}
			}

			setState(634);
			match(RBRACKET);
			}
			_ctx.stop = _input.LT(-1);
			setState(644);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArraySpecifierContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_arraySpecifier);
					setState(636);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(637);
					match(LBRACKET);
					setState(639);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 35)) & ~0x3f) == 0 && ((1L << (_la - 35)) & ((1L << (ATOMIC_UINT - 35)) | (1L << (STRUCT - 35)) | (1L << (UINT16CONSTANT - 35)) | (1L << (INT16CONSTANT - 35)) | (1L << (UINT32CONSTANT - 35)) | (1L << (INT32CONSTANT - 35)) | (1L << (UINT64CONSTANT - 35)) | (1L << (INT64CONSTANT - 35)) | (1L << (FLOAT16CONSTANT - 35)) | (1L << (FLOAT32CONSTANT - 35)) | (1L << (FLOAT64CONSTANT - 35)) | (1L << (BOOLCONSTANT - 35)) | (1L << (BOOL - 35)) | (1L << (BVEC2 - 35)) | (1L << (BVEC3 - 35)) | (1L << (BVEC4 - 35)) | (1L << (FLOAT16 - 35)) | (1L << (F16VEC2 - 35)) | (1L << (F16VEC3 - 35)) | (1L << (F16VEC4 - 35)) | (1L << (F16MAT2X2 - 35)) | (1L << (F16MAT2X3 - 35)) | (1L << (F16MAT2X4 - 35)) | (1L << (F16MAT3X2 - 35)) | (1L << (F16MAT3X3 - 35)) | (1L << (F16MAT3X4 - 35)) | (1L << (F16MAT4X2 - 35)) | (1L << (F16MAT4X3 - 35)) | (1L << (F16MAT4X4 - 35)) | (1L << (FLOAT32 - 35)) | (1L << (F32VEC2 - 35)) | (1L << (F32VEC3 - 35)) | (1L << (F32VEC4 - 35)) | (1L << (F32MAT2X2 - 35)) | (1L << (F32MAT2X3 - 35)) | (1L << (F32MAT2X4 - 35)) | (1L << (F32MAT3X2 - 35)) | (1L << (F32MAT3X3 - 35)) | (1L << (F32MAT3X4 - 35)) | (1L << (F32MAT4X2 - 35)) | (1L << (F32MAT4X3 - 35)) | (1L << (F32MAT4X4 - 35)) | (1L << (FLOAT64 - 35)) | (1L << (F64VEC2 - 35)) | (1L << (F64VEC3 - 35)) | (1L << (F64VEC4 - 35)) | (1L << (F64MAT2X2 - 35)) | (1L << (F64MAT2X3 - 35)) | (1L << (F64MAT2X4 - 35)) | (1L << (F64MAT3X2 - 35)) | (1L << (F64MAT3X3 - 35)))) != 0) || ((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (F64MAT3X4 - 99)) | (1L << (F64MAT4X2 - 99)) | (1L << (F64MAT4X3 - 99)) | (1L << (F64MAT4X4 - 99)) | (1L << (INT8 - 99)) | (1L << (I8VEC2 - 99)) | (1L << (I8VEC3 - 99)) | (1L << (I8VEC4 - 99)) | (1L << (UINT8 - 99)) | (1L << (UI8VEC2 - 99)) | (1L << (UI8VEC3 - 99)) | (1L << (UI8VEC4 - 99)) | (1L << (INT16 - 99)) | (1L << (I16VEC2 - 99)) | (1L << (I16VEC3 - 99)) | (1L << (I16VEC4 - 99)) | (1L << (UINT16 - 99)) | (1L << (UI16VEC2 - 99)) | (1L << (UI16VEC3 - 99)) | (1L << (UI16VEC4 - 99)) | (1L << (INT32 - 99)) | (1L << (I32VEC2 - 99)) | (1L << (I32VEC3 - 99)) | (1L << (I32VEC4 - 99)) | (1L << (UINT32 - 99)) | (1L << (UI32VEC2 - 99)) | (1L << (UI32VEC3 - 99)) | (1L << (UI32VEC4 - 99)) | (1L << (INT64 - 99)) | (1L << (I64VEC2 - 99)) | (1L << (I64VEC3 - 99)) | (1L << (I64VEC4 - 99)) | (1L << (UINT64 - 99)) | (1L << (UI64VEC2 - 99)) | (1L << (UI64VEC3 - 99)) | (1L << (UI64VEC4 - 99)) | (1L << (IMAGE1D - 99)) | (1L << (IMAGE2D - 99)) | (1L << (IMAGE3D - 99)) | (1L << (UIMAGE1D - 99)) | (1L << (UIMAGE2D - 99)) | (1L << (UIMAGE3D - 99)) | (1L << (IIMAGE1D - 99)) | (1L << (IIMAGE2D - 99)) | (1L << (IIMAGE3D - 99)) | (1L << (SAMPLER1D - 99)) | (1L << (SAMPLER2D - 99)) | (1L << (SAMPLER3D - 99)) | (1L << (SAMPLER2DRECT - 99)) | (1L << (SAMPLER1DSHADOW - 99)) | (1L << (SAMPLER2DSHADOW - 99)) | (1L << (SAMPLER2DRECTSHADOW - 99)) | (1L << (SAMPLER1DARRAY - 99)) | (1L << (SAMPLER2DARRAY - 99)) | (1L << (SAMPLER1DARRAYSHADOW - 99)) | (1L << (SAMPLER2DARRAYSHADOW - 99)) | (1L << (ISAMPLER1D - 99)) | (1L << (ISAMPLER2D - 99)) | (1L << (ISAMPLER2DRECT - 99)) | (1L << (ISAMPLER3D - 99)) | (1L << (ISAMPLER1DARRAY - 99)) | (1L << (ISAMPLER2DARRAY - 99)) | (1L << (USAMPLER1D - 99)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (USAMPLER2D - 163)) | (1L << (USAMPLER2DRECT - 163)) | (1L << (USAMPLER3D - 163)) | (1L << (USAMPLER1DARRAY - 163)) | (1L << (USAMPLER2DARRAY - 163)) | (1L << (SAMPLER2DMS - 163)) | (1L << (ISAMPLER2DMS - 163)) | (1L << (USAMPLER2DMS - 163)) | (1L << (SAMPLER2DMSARRAY - 163)) | (1L << (ISAMPLER2DMSARRAY - 163)) | (1L << (USAMPLER2DMSARRAY - 163)) | (1L << (IMAGE2DRECT - 163)) | (1L << (IMAGE1DARRAY - 163)) | (1L << (IMAGE2DARRAY - 163)) | (1L << (IMAGE2DMS - 163)) | (1L << (IMAGE2DMSARRAY - 163)) | (1L << (IIMAGE2DRECT - 163)) | (1L << (IIMAGE1DARRAY - 163)) | (1L << (IIMAGE2DARRAY - 163)) | (1L << (IIMAGE2DMS - 163)) | (1L << (IIMAGE2DMSARRAY - 163)) | (1L << (UIMAGE2DRECT - 163)) | (1L << (UIMAGE1DARRAY - 163)) | (1L << (UIMAGE2DARRAY - 163)) | (1L << (UIMAGE2DMS - 163)) | (1L << (UIMAGE2DMSARRAY - 163)) | (1L << (SAMPLERCUBE - 163)) | (1L << (SAMPLERCUBESHADOW - 163)) | (1L << (SAMPLERBUFFER - 163)) | (1L << (SAMPLERCUBEARRAY - 163)) | (1L << (SAMPLERCUBEARRAYSHADOW - 163)) | (1L << (ISAMPLERCUBE - 163)) | (1L << (ISAMPLERBUFFER - 163)) | (1L << (ISAMPLERCUBEARRAY - 163)) | (1L << (USAMPLERCUBE - 163)) | (1L << (USAMPLERBUFFER - 163)) | (1L << (USAMPLERCUBEARRAY - 163)) | (1L << (IMAGECUBE - 163)) | (1L << (IMAGEBUFFER - 163)) | (1L << (IMAGECUBEARRAY - 163)) | (1L << (IIMAGECUBE - 163)) | (1L << (IIMAGEBUFFER - 163)) | (1L << (IIMAGECUBEARRAY - 163)) | (1L << (UIMAGECUBE - 163)) | (1L << (UIMAGEBUFFER - 163)) | (1L << (UIMAGECUBEARRAY - 163)) | (1L << (INC_OP - 163)) | (1L << (DEC_OP - 163)) | (1L << (VOID - 163)))) != 0) || ((((_la - 231)) & ~0x3f) == 0 && ((1L << (_la - 231)) & ((1L << (LPAREN - 231)) | (1L << (PLUS_OP - 231)) | (1L << (MINUS_OP - 231)) | (1L << (NOT_OP - 231)) | (1L << (BNEG_OP - 231)) | (1L << (IDENTIFIER - 231)))) != 0)) {
						{
						setState(638);
						constantExpression();
						}
					}

					setState(641);
					match(RBRACKET);
					}
					} 
				}
				setState(646);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeSpecifierNonarrayContext extends ParserRuleContext {
		public BuiltinTypeSpecifierFixedContext builtinTypeSpecifierFixed() {
			return getRuleContext(BuiltinTypeSpecifierFixedContext.class,0);
		}
		public BuiltinTypeSpecifierParseableContext builtinTypeSpecifierParseable() {
			return getRuleContext(BuiltinTypeSpecifierParseableContext.class,0);
		}
		public StructSpecifierContext structSpecifier() {
			return getRuleContext(StructSpecifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public TypeSpecifierNonarrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpecifierNonarray; }
	}

	public final TypeSpecifierNonarrayContext typeSpecifierNonarray() throws RecognitionException {
		TypeSpecifierNonarrayContext _localctx = new TypeSpecifierNonarrayContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_typeSpecifierNonarray);
		try {
			setState(651);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATOMIC_UINT:
			case IMAGE1D:
			case IMAGE2D:
			case IMAGE3D:
			case UIMAGE1D:
			case UIMAGE2D:
			case UIMAGE3D:
			case IIMAGE1D:
			case IIMAGE2D:
			case IIMAGE3D:
			case SAMPLER1D:
			case SAMPLER2D:
			case SAMPLER3D:
			case SAMPLER2DRECT:
			case SAMPLER1DSHADOW:
			case SAMPLER2DSHADOW:
			case SAMPLER2DRECTSHADOW:
			case SAMPLER1DARRAY:
			case SAMPLER2DARRAY:
			case SAMPLER1DARRAYSHADOW:
			case SAMPLER2DARRAYSHADOW:
			case ISAMPLER1D:
			case ISAMPLER2D:
			case ISAMPLER2DRECT:
			case ISAMPLER3D:
			case ISAMPLER1DARRAY:
			case ISAMPLER2DARRAY:
			case USAMPLER1D:
			case USAMPLER2D:
			case USAMPLER2DRECT:
			case USAMPLER3D:
			case USAMPLER1DARRAY:
			case USAMPLER2DARRAY:
			case SAMPLER2DMS:
			case ISAMPLER2DMS:
			case USAMPLER2DMS:
			case SAMPLER2DMSARRAY:
			case ISAMPLER2DMSARRAY:
			case USAMPLER2DMSARRAY:
			case IMAGE2DRECT:
			case IMAGE1DARRAY:
			case IMAGE2DARRAY:
			case IMAGE2DMS:
			case IMAGE2DMSARRAY:
			case IIMAGE2DRECT:
			case IIMAGE1DARRAY:
			case IIMAGE2DARRAY:
			case IIMAGE2DMS:
			case IIMAGE2DMSARRAY:
			case UIMAGE2DRECT:
			case UIMAGE1DARRAY:
			case UIMAGE2DARRAY:
			case UIMAGE2DMS:
			case UIMAGE2DMSARRAY:
			case SAMPLERCUBE:
			case SAMPLERCUBESHADOW:
			case SAMPLERBUFFER:
			case SAMPLERCUBEARRAY:
			case SAMPLERCUBEARRAYSHADOW:
			case ISAMPLERCUBE:
			case ISAMPLERBUFFER:
			case ISAMPLERCUBEARRAY:
			case USAMPLERCUBE:
			case USAMPLERBUFFER:
			case USAMPLERCUBEARRAY:
			case IMAGECUBE:
			case IMAGEBUFFER:
			case IMAGECUBEARRAY:
			case IIMAGECUBE:
			case IIMAGEBUFFER:
			case IIMAGECUBEARRAY:
			case UIMAGECUBE:
			case UIMAGEBUFFER:
			case UIMAGECUBEARRAY:
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(647);
				builtinTypeSpecifierFixed();
				}
				break;
			case BOOL:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case FLOAT16:
			case F16VEC2:
			case F16VEC3:
			case F16VEC4:
			case F16MAT2X2:
			case F16MAT2X3:
			case F16MAT2X4:
			case F16MAT3X2:
			case F16MAT3X3:
			case F16MAT3X4:
			case F16MAT4X2:
			case F16MAT4X3:
			case F16MAT4X4:
			case FLOAT32:
			case F32VEC2:
			case F32VEC3:
			case F32VEC4:
			case F32MAT2X2:
			case F32MAT2X3:
			case F32MAT2X4:
			case F32MAT3X2:
			case F32MAT3X3:
			case F32MAT3X4:
			case F32MAT4X2:
			case F32MAT4X3:
			case F32MAT4X4:
			case FLOAT64:
			case F64VEC2:
			case F64VEC3:
			case F64VEC4:
			case F64MAT2X2:
			case F64MAT2X3:
			case F64MAT2X4:
			case F64MAT3X2:
			case F64MAT3X3:
			case F64MAT3X4:
			case F64MAT4X2:
			case F64MAT4X3:
			case F64MAT4X4:
			case INT8:
			case I8VEC2:
			case I8VEC3:
			case I8VEC4:
			case UINT8:
			case UI8VEC2:
			case UI8VEC3:
			case UI8VEC4:
			case INT16:
			case I16VEC2:
			case I16VEC3:
			case I16VEC4:
			case UINT16:
			case UI16VEC2:
			case UI16VEC3:
			case UI16VEC4:
			case INT32:
			case I32VEC2:
			case I32VEC3:
			case I32VEC4:
			case UINT32:
			case UI32VEC2:
			case UI32VEC3:
			case UI32VEC4:
			case INT64:
			case I64VEC2:
			case I64VEC3:
			case I64VEC4:
			case UINT64:
			case UI64VEC2:
			case UI64VEC3:
			case UI64VEC4:
				enterOuterAlt(_localctx, 2);
				{
				setState(648);
				builtinTypeSpecifierParseable();
				}
				break;
			case STRUCT:
				enterOuterAlt(_localctx, 3);
				{
				setState(649);
				structSpecifier();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(650);
				match(IDENTIFIER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuiltinTypeSpecifierParseableContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(GLSLParser.BOOL, 0); }
		public TerminalNode BVEC2() { return getToken(GLSLParser.BVEC2, 0); }
		public TerminalNode BVEC3() { return getToken(GLSLParser.BVEC3, 0); }
		public TerminalNode BVEC4() { return getToken(GLSLParser.BVEC4, 0); }
		public TerminalNode FLOAT16() { return getToken(GLSLParser.FLOAT16, 0); }
		public TerminalNode F16VEC2() { return getToken(GLSLParser.F16VEC2, 0); }
		public TerminalNode F16VEC3() { return getToken(GLSLParser.F16VEC3, 0); }
		public TerminalNode F16VEC4() { return getToken(GLSLParser.F16VEC4, 0); }
		public TerminalNode F16MAT2X2() { return getToken(GLSLParser.F16MAT2X2, 0); }
		public TerminalNode F16MAT2X3() { return getToken(GLSLParser.F16MAT2X3, 0); }
		public TerminalNode F16MAT2X4() { return getToken(GLSLParser.F16MAT2X4, 0); }
		public TerminalNode F16MAT3X2() { return getToken(GLSLParser.F16MAT3X2, 0); }
		public TerminalNode F16MAT3X3() { return getToken(GLSLParser.F16MAT3X3, 0); }
		public TerminalNode F16MAT3X4() { return getToken(GLSLParser.F16MAT3X4, 0); }
		public TerminalNode F16MAT4X2() { return getToken(GLSLParser.F16MAT4X2, 0); }
		public TerminalNode F16MAT4X3() { return getToken(GLSLParser.F16MAT4X3, 0); }
		public TerminalNode F16MAT4X4() { return getToken(GLSLParser.F16MAT4X4, 0); }
		public TerminalNode FLOAT32() { return getToken(GLSLParser.FLOAT32, 0); }
		public TerminalNode F32VEC2() { return getToken(GLSLParser.F32VEC2, 0); }
		public TerminalNode F32VEC3() { return getToken(GLSLParser.F32VEC3, 0); }
		public TerminalNode F32VEC4() { return getToken(GLSLParser.F32VEC4, 0); }
		public TerminalNode F32MAT2X2() { return getToken(GLSLParser.F32MAT2X2, 0); }
		public TerminalNode F32MAT2X3() { return getToken(GLSLParser.F32MAT2X3, 0); }
		public TerminalNode F32MAT2X4() { return getToken(GLSLParser.F32MAT2X4, 0); }
		public TerminalNode F32MAT3X2() { return getToken(GLSLParser.F32MAT3X2, 0); }
		public TerminalNode F32MAT3X3() { return getToken(GLSLParser.F32MAT3X3, 0); }
		public TerminalNode F32MAT3X4() { return getToken(GLSLParser.F32MAT3X4, 0); }
		public TerminalNode F32MAT4X2() { return getToken(GLSLParser.F32MAT4X2, 0); }
		public TerminalNode F32MAT4X3() { return getToken(GLSLParser.F32MAT4X3, 0); }
		public TerminalNode F32MAT4X4() { return getToken(GLSLParser.F32MAT4X4, 0); }
		public TerminalNode FLOAT64() { return getToken(GLSLParser.FLOAT64, 0); }
		public TerminalNode F64VEC2() { return getToken(GLSLParser.F64VEC2, 0); }
		public TerminalNode F64VEC3() { return getToken(GLSLParser.F64VEC3, 0); }
		public TerminalNode F64VEC4() { return getToken(GLSLParser.F64VEC4, 0); }
		public TerminalNode F64MAT2X2() { return getToken(GLSLParser.F64MAT2X2, 0); }
		public TerminalNode F64MAT2X3() { return getToken(GLSLParser.F64MAT2X3, 0); }
		public TerminalNode F64MAT2X4() { return getToken(GLSLParser.F64MAT2X4, 0); }
		public TerminalNode F64MAT3X2() { return getToken(GLSLParser.F64MAT3X2, 0); }
		public TerminalNode F64MAT3X3() { return getToken(GLSLParser.F64MAT3X3, 0); }
		public TerminalNode F64MAT3X4() { return getToken(GLSLParser.F64MAT3X4, 0); }
		public TerminalNode F64MAT4X2() { return getToken(GLSLParser.F64MAT4X2, 0); }
		public TerminalNode F64MAT4X3() { return getToken(GLSLParser.F64MAT4X3, 0); }
		public TerminalNode F64MAT4X4() { return getToken(GLSLParser.F64MAT4X4, 0); }
		public TerminalNode INT8() { return getToken(GLSLParser.INT8, 0); }
		public TerminalNode I8VEC2() { return getToken(GLSLParser.I8VEC2, 0); }
		public TerminalNode I8VEC3() { return getToken(GLSLParser.I8VEC3, 0); }
		public TerminalNode I8VEC4() { return getToken(GLSLParser.I8VEC4, 0); }
		public TerminalNode UINT8() { return getToken(GLSLParser.UINT8, 0); }
		public TerminalNode UI8VEC2() { return getToken(GLSLParser.UI8VEC2, 0); }
		public TerminalNode UI8VEC3() { return getToken(GLSLParser.UI8VEC3, 0); }
		public TerminalNode UI8VEC4() { return getToken(GLSLParser.UI8VEC4, 0); }
		public TerminalNode INT16() { return getToken(GLSLParser.INT16, 0); }
		public TerminalNode I16VEC2() { return getToken(GLSLParser.I16VEC2, 0); }
		public TerminalNode I16VEC3() { return getToken(GLSLParser.I16VEC3, 0); }
		public TerminalNode I16VEC4() { return getToken(GLSLParser.I16VEC4, 0); }
		public TerminalNode UINT16() { return getToken(GLSLParser.UINT16, 0); }
		public TerminalNode UI16VEC2() { return getToken(GLSLParser.UI16VEC2, 0); }
		public TerminalNode UI16VEC3() { return getToken(GLSLParser.UI16VEC3, 0); }
		public TerminalNode UI16VEC4() { return getToken(GLSLParser.UI16VEC4, 0); }
		public TerminalNode INT32() { return getToken(GLSLParser.INT32, 0); }
		public TerminalNode I32VEC2() { return getToken(GLSLParser.I32VEC2, 0); }
		public TerminalNode I32VEC3() { return getToken(GLSLParser.I32VEC3, 0); }
		public TerminalNode I32VEC4() { return getToken(GLSLParser.I32VEC4, 0); }
		public TerminalNode UINT32() { return getToken(GLSLParser.UINT32, 0); }
		public TerminalNode UI32VEC2() { return getToken(GLSLParser.UI32VEC2, 0); }
		public TerminalNode UI32VEC3() { return getToken(GLSLParser.UI32VEC3, 0); }
		public TerminalNode UI32VEC4() { return getToken(GLSLParser.UI32VEC4, 0); }
		public TerminalNode INT64() { return getToken(GLSLParser.INT64, 0); }
		public TerminalNode I64VEC2() { return getToken(GLSLParser.I64VEC2, 0); }
		public TerminalNode I64VEC3() { return getToken(GLSLParser.I64VEC3, 0); }
		public TerminalNode I64VEC4() { return getToken(GLSLParser.I64VEC4, 0); }
		public TerminalNode UINT64() { return getToken(GLSLParser.UINT64, 0); }
		public TerminalNode UI64VEC2() { return getToken(GLSLParser.UI64VEC2, 0); }
		public TerminalNode UI64VEC3() { return getToken(GLSLParser.UI64VEC3, 0); }
		public TerminalNode UI64VEC4() { return getToken(GLSLParser.UI64VEC4, 0); }
		public BuiltinTypeSpecifierParseableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtinTypeSpecifierParseable; }
	}

	public final BuiltinTypeSpecifierParseableContext builtinTypeSpecifierParseable() throws RecognitionException {
		BuiltinTypeSpecifierParseableContext _localctx = new BuiltinTypeSpecifierParseableContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_builtinTypeSpecifierParseable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(653);
			_la = _input.LA(1);
			if ( !(((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (BOOL - 60)) | (1L << (BVEC2 - 60)) | (1L << (BVEC3 - 60)) | (1L << (BVEC4 - 60)) | (1L << (FLOAT16 - 60)) | (1L << (F16VEC2 - 60)) | (1L << (F16VEC3 - 60)) | (1L << (F16VEC4 - 60)) | (1L << (F16MAT2X2 - 60)) | (1L << (F16MAT2X3 - 60)) | (1L << (F16MAT2X4 - 60)) | (1L << (F16MAT3X2 - 60)) | (1L << (F16MAT3X3 - 60)) | (1L << (F16MAT3X4 - 60)) | (1L << (F16MAT4X2 - 60)) | (1L << (F16MAT4X3 - 60)) | (1L << (F16MAT4X4 - 60)) | (1L << (FLOAT32 - 60)) | (1L << (F32VEC2 - 60)) | (1L << (F32VEC3 - 60)) | (1L << (F32VEC4 - 60)) | (1L << (F32MAT2X2 - 60)) | (1L << (F32MAT2X3 - 60)) | (1L << (F32MAT2X4 - 60)) | (1L << (F32MAT3X2 - 60)) | (1L << (F32MAT3X3 - 60)) | (1L << (F32MAT3X4 - 60)) | (1L << (F32MAT4X2 - 60)) | (1L << (F32MAT4X3 - 60)) | (1L << (F32MAT4X4 - 60)) | (1L << (FLOAT64 - 60)) | (1L << (F64VEC2 - 60)) | (1L << (F64VEC3 - 60)) | (1L << (F64VEC4 - 60)) | (1L << (F64MAT2X2 - 60)) | (1L << (F64MAT2X3 - 60)) | (1L << (F64MAT2X4 - 60)) | (1L << (F64MAT3X2 - 60)) | (1L << (F64MAT3X3 - 60)) | (1L << (F64MAT3X4 - 60)) | (1L << (F64MAT4X2 - 60)) | (1L << (F64MAT4X3 - 60)) | (1L << (F64MAT4X4 - 60)) | (1L << (INT8 - 60)) | (1L << (I8VEC2 - 60)) | (1L << (I8VEC3 - 60)) | (1L << (I8VEC4 - 60)) | (1L << (UINT8 - 60)) | (1L << (UI8VEC2 - 60)) | (1L << (UI8VEC3 - 60)) | (1L << (UI8VEC4 - 60)) | (1L << (INT16 - 60)) | (1L << (I16VEC2 - 60)) | (1L << (I16VEC3 - 60)) | (1L << (I16VEC4 - 60)) | (1L << (UINT16 - 60)) | (1L << (UI16VEC2 - 60)) | (1L << (UI16VEC3 - 60)) | (1L << (UI16VEC4 - 60)) | (1L << (INT32 - 60)) | (1L << (I32VEC2 - 60)) | (1L << (I32VEC3 - 60)) | (1L << (I32VEC4 - 60)) | (1L << (UINT32 - 60)))) != 0) || ((((_la - 124)) & ~0x3f) == 0 && ((1L << (_la - 124)) & ((1L << (UI32VEC2 - 124)) | (1L << (UI32VEC3 - 124)) | (1L << (UI32VEC4 - 124)) | (1L << (INT64 - 124)) | (1L << (I64VEC2 - 124)) | (1L << (I64VEC3 - 124)) | (1L << (I64VEC4 - 124)) | (1L << (UINT64 - 124)) | (1L << (UI64VEC2 - 124)) | (1L << (UI64VEC3 - 124)) | (1L << (UI64VEC4 - 124)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BuiltinTypeSpecifierFixedContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(GLSLParser.VOID, 0); }
		public TerminalNode ATOMIC_UINT() { return getToken(GLSLParser.ATOMIC_UINT, 0); }
		public TerminalNode SAMPLER2D() { return getToken(GLSLParser.SAMPLER2D, 0); }
		public TerminalNode SAMPLER3D() { return getToken(GLSLParser.SAMPLER3D, 0); }
		public TerminalNode SAMPLERCUBE() { return getToken(GLSLParser.SAMPLERCUBE, 0); }
		public TerminalNode SAMPLER2DSHADOW() { return getToken(GLSLParser.SAMPLER2DSHADOW, 0); }
		public TerminalNode SAMPLERCUBESHADOW() { return getToken(GLSLParser.SAMPLERCUBESHADOW, 0); }
		public TerminalNode SAMPLER2DARRAY() { return getToken(GLSLParser.SAMPLER2DARRAY, 0); }
		public TerminalNode SAMPLER2DARRAYSHADOW() { return getToken(GLSLParser.SAMPLER2DARRAYSHADOW, 0); }
		public TerminalNode SAMPLERCUBEARRAY() { return getToken(GLSLParser.SAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLERCUBEARRAYSHADOW() { return getToken(GLSLParser.SAMPLERCUBEARRAYSHADOW, 0); }
		public TerminalNode ISAMPLER2D() { return getToken(GLSLParser.ISAMPLER2D, 0); }
		public TerminalNode ISAMPLER3D() { return getToken(GLSLParser.ISAMPLER3D, 0); }
		public TerminalNode ISAMPLERCUBE() { return getToken(GLSLParser.ISAMPLERCUBE, 0); }
		public TerminalNode ISAMPLER2DARRAY() { return getToken(GLSLParser.ISAMPLER2DARRAY, 0); }
		public TerminalNode ISAMPLERCUBEARRAY() { return getToken(GLSLParser.ISAMPLERCUBEARRAY, 0); }
		public TerminalNode USAMPLER2D() { return getToken(GLSLParser.USAMPLER2D, 0); }
		public TerminalNode USAMPLER3D() { return getToken(GLSLParser.USAMPLER3D, 0); }
		public TerminalNode USAMPLERCUBE() { return getToken(GLSLParser.USAMPLERCUBE, 0); }
		public TerminalNode USAMPLER2DARRAY() { return getToken(GLSLParser.USAMPLER2DARRAY, 0); }
		public TerminalNode USAMPLERCUBEARRAY() { return getToken(GLSLParser.USAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLER1D() { return getToken(GLSLParser.SAMPLER1D, 0); }
		public TerminalNode SAMPLER1DSHADOW() { return getToken(GLSLParser.SAMPLER1DSHADOW, 0); }
		public TerminalNode SAMPLER1DARRAY() { return getToken(GLSLParser.SAMPLER1DARRAY, 0); }
		public TerminalNode SAMPLER1DARRAYSHADOW() { return getToken(GLSLParser.SAMPLER1DARRAYSHADOW, 0); }
		public TerminalNode ISAMPLER1D() { return getToken(GLSLParser.ISAMPLER1D, 0); }
		public TerminalNode ISAMPLER1DARRAY() { return getToken(GLSLParser.ISAMPLER1DARRAY, 0); }
		public TerminalNode USAMPLER1D() { return getToken(GLSLParser.USAMPLER1D, 0); }
		public TerminalNode USAMPLER1DARRAY() { return getToken(GLSLParser.USAMPLER1DARRAY, 0); }
		public TerminalNode SAMPLER2DRECT() { return getToken(GLSLParser.SAMPLER2DRECT, 0); }
		public TerminalNode SAMPLER2DRECTSHADOW() { return getToken(GLSLParser.SAMPLER2DRECTSHADOW, 0); }
		public TerminalNode ISAMPLER2DRECT() { return getToken(GLSLParser.ISAMPLER2DRECT, 0); }
		public TerminalNode USAMPLER2DRECT() { return getToken(GLSLParser.USAMPLER2DRECT, 0); }
		public TerminalNode SAMPLERBUFFER() { return getToken(GLSLParser.SAMPLERBUFFER, 0); }
		public TerminalNode ISAMPLERBUFFER() { return getToken(GLSLParser.ISAMPLERBUFFER, 0); }
		public TerminalNode USAMPLERBUFFER() { return getToken(GLSLParser.USAMPLERBUFFER, 0); }
		public TerminalNode SAMPLER2DMS() { return getToken(GLSLParser.SAMPLER2DMS, 0); }
		public TerminalNode ISAMPLER2DMS() { return getToken(GLSLParser.ISAMPLER2DMS, 0); }
		public TerminalNode USAMPLER2DMS() { return getToken(GLSLParser.USAMPLER2DMS, 0); }
		public TerminalNode SAMPLER2DMSARRAY() { return getToken(GLSLParser.SAMPLER2DMSARRAY, 0); }
		public TerminalNode ISAMPLER2DMSARRAY() { return getToken(GLSLParser.ISAMPLER2DMSARRAY, 0); }
		public TerminalNode USAMPLER2DMSARRAY() { return getToken(GLSLParser.USAMPLER2DMSARRAY, 0); }
		public TerminalNode IMAGE2D() { return getToken(GLSLParser.IMAGE2D, 0); }
		public TerminalNode IIMAGE2D() { return getToken(GLSLParser.IIMAGE2D, 0); }
		public TerminalNode UIMAGE2D() { return getToken(GLSLParser.UIMAGE2D, 0); }
		public TerminalNode IMAGE3D() { return getToken(GLSLParser.IMAGE3D, 0); }
		public TerminalNode IIMAGE3D() { return getToken(GLSLParser.IIMAGE3D, 0); }
		public TerminalNode UIMAGE3D() { return getToken(GLSLParser.UIMAGE3D, 0); }
		public TerminalNode IMAGECUBE() { return getToken(GLSLParser.IMAGECUBE, 0); }
		public TerminalNode IIMAGECUBE() { return getToken(GLSLParser.IIMAGECUBE, 0); }
		public TerminalNode UIMAGECUBE() { return getToken(GLSLParser.UIMAGECUBE, 0); }
		public TerminalNode IMAGEBUFFER() { return getToken(GLSLParser.IMAGEBUFFER, 0); }
		public TerminalNode IIMAGEBUFFER() { return getToken(GLSLParser.IIMAGEBUFFER, 0); }
		public TerminalNode UIMAGEBUFFER() { return getToken(GLSLParser.UIMAGEBUFFER, 0); }
		public TerminalNode IMAGE1D() { return getToken(GLSLParser.IMAGE1D, 0); }
		public TerminalNode IIMAGE1D() { return getToken(GLSLParser.IIMAGE1D, 0); }
		public TerminalNode UIMAGE1D() { return getToken(GLSLParser.UIMAGE1D, 0); }
		public TerminalNode IMAGE1DARRAY() { return getToken(GLSLParser.IMAGE1DARRAY, 0); }
		public TerminalNode IIMAGE1DARRAY() { return getToken(GLSLParser.IIMAGE1DARRAY, 0); }
		public TerminalNode UIMAGE1DARRAY() { return getToken(GLSLParser.UIMAGE1DARRAY, 0); }
		public TerminalNode IMAGE2DRECT() { return getToken(GLSLParser.IMAGE2DRECT, 0); }
		public TerminalNode IIMAGE2DRECT() { return getToken(GLSLParser.IIMAGE2DRECT, 0); }
		public TerminalNode UIMAGE2DRECT() { return getToken(GLSLParser.UIMAGE2DRECT, 0); }
		public TerminalNode IMAGE2DARRAY() { return getToken(GLSLParser.IMAGE2DARRAY, 0); }
		public TerminalNode IIMAGE2DARRAY() { return getToken(GLSLParser.IIMAGE2DARRAY, 0); }
		public TerminalNode UIMAGE2DARRAY() { return getToken(GLSLParser.UIMAGE2DARRAY, 0); }
		public TerminalNode IMAGECUBEARRAY() { return getToken(GLSLParser.IMAGECUBEARRAY, 0); }
		public TerminalNode IIMAGECUBEARRAY() { return getToken(GLSLParser.IIMAGECUBEARRAY, 0); }
		public TerminalNode UIMAGECUBEARRAY() { return getToken(GLSLParser.UIMAGECUBEARRAY, 0); }
		public TerminalNode IMAGE2DMS() { return getToken(GLSLParser.IMAGE2DMS, 0); }
		public TerminalNode IIMAGE2DMS() { return getToken(GLSLParser.IIMAGE2DMS, 0); }
		public TerminalNode UIMAGE2DMS() { return getToken(GLSLParser.UIMAGE2DMS, 0); }
		public TerminalNode IMAGE2DMSARRAY() { return getToken(GLSLParser.IMAGE2DMSARRAY, 0); }
		public TerminalNode IIMAGE2DMSARRAY() { return getToken(GLSLParser.IIMAGE2DMSARRAY, 0); }
		public TerminalNode UIMAGE2DMSARRAY() { return getToken(GLSLParser.UIMAGE2DMSARRAY, 0); }
		public BuiltinTypeSpecifierFixedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtinTypeSpecifierFixed; }
	}

	public final BuiltinTypeSpecifierFixedContext builtinTypeSpecifierFixed() throws RecognitionException {
		BuiltinTypeSpecifierFixedContext _localctx = new BuiltinTypeSpecifierFixedContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_builtinTypeSpecifierFixed);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(655);
			_la = _input.LA(1);
			if ( !(_la==ATOMIC_UINT || ((((_la - 135)) & ~0x3f) == 0 && ((1L << (_la - 135)) & ((1L << (IMAGE1D - 135)) | (1L << (IMAGE2D - 135)) | (1L << (IMAGE3D - 135)) | (1L << (UIMAGE1D - 135)) | (1L << (UIMAGE2D - 135)) | (1L << (UIMAGE3D - 135)) | (1L << (IIMAGE1D - 135)) | (1L << (IIMAGE2D - 135)) | (1L << (IIMAGE3D - 135)) | (1L << (SAMPLER1D - 135)) | (1L << (SAMPLER2D - 135)) | (1L << (SAMPLER3D - 135)) | (1L << (SAMPLER2DRECT - 135)) | (1L << (SAMPLER1DSHADOW - 135)) | (1L << (SAMPLER2DSHADOW - 135)) | (1L << (SAMPLER2DRECTSHADOW - 135)) | (1L << (SAMPLER1DARRAY - 135)) | (1L << (SAMPLER2DARRAY - 135)) | (1L << (SAMPLER1DARRAYSHADOW - 135)) | (1L << (SAMPLER2DARRAYSHADOW - 135)) | (1L << (ISAMPLER1D - 135)) | (1L << (ISAMPLER2D - 135)) | (1L << (ISAMPLER2DRECT - 135)) | (1L << (ISAMPLER3D - 135)) | (1L << (ISAMPLER1DARRAY - 135)) | (1L << (ISAMPLER2DARRAY - 135)) | (1L << (USAMPLER1D - 135)) | (1L << (USAMPLER2D - 135)) | (1L << (USAMPLER2DRECT - 135)) | (1L << (USAMPLER3D - 135)) | (1L << (USAMPLER1DARRAY - 135)) | (1L << (USAMPLER2DARRAY - 135)) | (1L << (SAMPLER2DMS - 135)) | (1L << (ISAMPLER2DMS - 135)) | (1L << (USAMPLER2DMS - 135)) | (1L << (SAMPLER2DMSARRAY - 135)) | (1L << (ISAMPLER2DMSARRAY - 135)) | (1L << (USAMPLER2DMSARRAY - 135)) | (1L << (IMAGE2DRECT - 135)) | (1L << (IMAGE1DARRAY - 135)) | (1L << (IMAGE2DARRAY - 135)) | (1L << (IMAGE2DMS - 135)) | (1L << (IMAGE2DMSARRAY - 135)) | (1L << (IIMAGE2DRECT - 135)) | (1L << (IIMAGE1DARRAY - 135)) | (1L << (IIMAGE2DARRAY - 135)) | (1L << (IIMAGE2DMS - 135)) | (1L << (IIMAGE2DMSARRAY - 135)) | (1L << (UIMAGE2DRECT - 135)) | (1L << (UIMAGE1DARRAY - 135)) | (1L << (UIMAGE2DARRAY - 135)) | (1L << (UIMAGE2DMS - 135)) | (1L << (UIMAGE2DMSARRAY - 135)) | (1L << (SAMPLERCUBE - 135)) | (1L << (SAMPLERCUBESHADOW - 135)) | (1L << (SAMPLERBUFFER - 135)) | (1L << (SAMPLERCUBEARRAY - 135)) | (1L << (SAMPLERCUBEARRAYSHADOW - 135)) | (1L << (ISAMPLERCUBE - 135)) | (1L << (ISAMPLERBUFFER - 135)) | (1L << (ISAMPLERCUBEARRAY - 135)) | (1L << (USAMPLERCUBE - 135)) | (1L << (USAMPLERBUFFER - 135)))) != 0) || ((((_la - 199)) & ~0x3f) == 0 && ((1L << (_la - 199)) & ((1L << (USAMPLERCUBEARRAY - 199)) | (1L << (IMAGECUBE - 199)) | (1L << (IMAGEBUFFER - 199)) | (1L << (IMAGECUBEARRAY - 199)) | (1L << (IIMAGECUBE - 199)) | (1L << (IIMAGEBUFFER - 199)) | (1L << (IIMAGECUBEARRAY - 199)) | (1L << (UIMAGECUBE - 199)) | (1L << (UIMAGEBUFFER - 199)) | (1L << (UIMAGECUBEARRAY - 199)) | (1L << (VOID - 199)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructSpecifierContext extends ParserRuleContext {
		public TerminalNode STRUCT() { return getToken(GLSLParser.STRUCT, 0); }
		public TerminalNode LBRACE() { return getToken(GLSLParser.LBRACE, 0); }
		public StructDeclarationListContext structDeclarationList() {
			return getRuleContext(StructDeclarationListContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSLParser.RBRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public StructSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structSpecifier; }
	}

	public final StructSpecifierContext structSpecifier() throws RecognitionException {
		StructSpecifierContext _localctx = new StructSpecifierContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_structSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(657);
			match(STRUCT);
			setState(659);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(658);
				match(IDENTIFIER);
				}
			}

			setState(661);
			match(LBRACE);
			setState(662);
			structDeclarationList();
			setState(663);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclarationListContext extends ParserRuleContext {
		public List<StructDeclarationContext> structDeclaration() {
			return getRuleContexts(StructDeclarationContext.class);
		}
		public StructDeclarationContext structDeclaration(int i) {
			return getRuleContext(StructDeclarationContext.class,i);
		}
		public StructDeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclarationList; }
	}

	public final StructDeclarationListContext structDeclarationList() throws RecognitionException {
		StructDeclarationListContext _localctx = new StructDeclarationListContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_structDeclarationList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(666); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(665);
				structDeclaration();
				}
				}
				setState(668); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (VOID - 194)) | (1L << (IDENTIFIER - 194)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclarationContext extends ParserRuleContext {
		public FullySpecifiedTypeContext fullySpecifiedType() {
			return getRuleContext(FullySpecifiedTypeContext.class,0);
		}
		public StructDeclaratorListContext structDeclaratorList() {
			return getRuleContext(StructDeclaratorListContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public StructDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclaration; }
	}

	public final StructDeclarationContext structDeclaration() throws RecognitionException {
		StructDeclarationContext _localctx = new StructDeclarationContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_structDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(670);
			fullySpecifiedType();
			setState(671);
			structDeclaratorList();
			setState(672);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclaratorListContext extends ParserRuleContext {
		public List<StructDeclaratorContext> structDeclarator() {
			return getRuleContexts(StructDeclaratorContext.class);
		}
		public StructDeclaratorContext structDeclarator(int i) {
			return getRuleContext(StructDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public StructDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclaratorList; }
	}

	public final StructDeclaratorListContext structDeclaratorList() throws RecognitionException {
		StructDeclaratorListContext _localctx = new StructDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_structDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			structDeclarator();
			setState(679);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(675);
				match(COMMA);
				setState(676);
				structDeclarator();
				}
				}
				setState(681);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDeclaratorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public ArraySpecifierContext arraySpecifier() {
			return getRuleContext(ArraySpecifierContext.class,0);
		}
		public StructDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclarator; }
	}

	public final StructDeclaratorContext structDeclarator() throws RecognitionException {
		StructDeclaratorContext _localctx = new StructDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_structDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(682);
			match(IDENTIFIER);
			setState(684);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(683);
				arraySpecifier(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerContext extends ParserRuleContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(GLSLParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSLParser.RBRACE, 0); }
		public List<InitializerContext> initializer() {
			return getRuleContexts(InitializerContext.class);
		}
		public InitializerContext initializer(int i) {
			return getRuleContext(InitializerContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLParser.COMMA, i);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_initializer);
		int _la;
		try {
			int _alt;
			setState(702);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATOMIC_UINT:
			case STRUCT:
			case UINT16CONSTANT:
			case INT16CONSTANT:
			case UINT32CONSTANT:
			case INT32CONSTANT:
			case UINT64CONSTANT:
			case INT64CONSTANT:
			case FLOAT16CONSTANT:
			case FLOAT32CONSTANT:
			case FLOAT64CONSTANT:
			case BOOLCONSTANT:
			case BOOL:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case FLOAT16:
			case F16VEC2:
			case F16VEC3:
			case F16VEC4:
			case F16MAT2X2:
			case F16MAT2X3:
			case F16MAT2X4:
			case F16MAT3X2:
			case F16MAT3X3:
			case F16MAT3X4:
			case F16MAT4X2:
			case F16MAT4X3:
			case F16MAT4X4:
			case FLOAT32:
			case F32VEC2:
			case F32VEC3:
			case F32VEC4:
			case F32MAT2X2:
			case F32MAT2X3:
			case F32MAT2X4:
			case F32MAT3X2:
			case F32MAT3X3:
			case F32MAT3X4:
			case F32MAT4X2:
			case F32MAT4X3:
			case F32MAT4X4:
			case FLOAT64:
			case F64VEC2:
			case F64VEC3:
			case F64VEC4:
			case F64MAT2X2:
			case F64MAT2X3:
			case F64MAT2X4:
			case F64MAT3X2:
			case F64MAT3X3:
			case F64MAT3X4:
			case F64MAT4X2:
			case F64MAT4X3:
			case F64MAT4X4:
			case INT8:
			case I8VEC2:
			case I8VEC3:
			case I8VEC4:
			case UINT8:
			case UI8VEC2:
			case UI8VEC3:
			case UI8VEC4:
			case INT16:
			case I16VEC2:
			case I16VEC3:
			case I16VEC4:
			case UINT16:
			case UI16VEC2:
			case UI16VEC3:
			case UI16VEC4:
			case INT32:
			case I32VEC2:
			case I32VEC3:
			case I32VEC4:
			case UINT32:
			case UI32VEC2:
			case UI32VEC3:
			case UI32VEC4:
			case INT64:
			case I64VEC2:
			case I64VEC3:
			case I64VEC4:
			case UINT64:
			case UI64VEC2:
			case UI64VEC3:
			case UI64VEC4:
			case IMAGE1D:
			case IMAGE2D:
			case IMAGE3D:
			case UIMAGE1D:
			case UIMAGE2D:
			case UIMAGE3D:
			case IIMAGE1D:
			case IIMAGE2D:
			case IIMAGE3D:
			case SAMPLER1D:
			case SAMPLER2D:
			case SAMPLER3D:
			case SAMPLER2DRECT:
			case SAMPLER1DSHADOW:
			case SAMPLER2DSHADOW:
			case SAMPLER2DRECTSHADOW:
			case SAMPLER1DARRAY:
			case SAMPLER2DARRAY:
			case SAMPLER1DARRAYSHADOW:
			case SAMPLER2DARRAYSHADOW:
			case ISAMPLER1D:
			case ISAMPLER2D:
			case ISAMPLER2DRECT:
			case ISAMPLER3D:
			case ISAMPLER1DARRAY:
			case ISAMPLER2DARRAY:
			case USAMPLER1D:
			case USAMPLER2D:
			case USAMPLER2DRECT:
			case USAMPLER3D:
			case USAMPLER1DARRAY:
			case USAMPLER2DARRAY:
			case SAMPLER2DMS:
			case ISAMPLER2DMS:
			case USAMPLER2DMS:
			case SAMPLER2DMSARRAY:
			case ISAMPLER2DMSARRAY:
			case USAMPLER2DMSARRAY:
			case IMAGE2DRECT:
			case IMAGE1DARRAY:
			case IMAGE2DARRAY:
			case IMAGE2DMS:
			case IMAGE2DMSARRAY:
			case IIMAGE2DRECT:
			case IIMAGE1DARRAY:
			case IIMAGE2DARRAY:
			case IIMAGE2DMS:
			case IIMAGE2DMSARRAY:
			case UIMAGE2DRECT:
			case UIMAGE1DARRAY:
			case UIMAGE2DARRAY:
			case UIMAGE2DMS:
			case UIMAGE2DMSARRAY:
			case SAMPLERCUBE:
			case SAMPLERCUBESHADOW:
			case SAMPLERBUFFER:
			case SAMPLERCUBEARRAY:
			case SAMPLERCUBEARRAYSHADOW:
			case ISAMPLERCUBE:
			case ISAMPLERBUFFER:
			case ISAMPLERCUBEARRAY:
			case USAMPLERCUBE:
			case USAMPLERBUFFER:
			case USAMPLERCUBEARRAY:
			case IMAGECUBE:
			case IMAGEBUFFER:
			case IMAGECUBEARRAY:
			case IIMAGECUBE:
			case IIMAGEBUFFER:
			case IIMAGECUBEARRAY:
			case UIMAGECUBE:
			case UIMAGEBUFFER:
			case UIMAGECUBEARRAY:
			case INC_OP:
			case DEC_OP:
			case VOID:
			case LPAREN:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(686);
				assignmentExpression();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(687);
				match(LBRACE);
				setState(699);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 35)) & ~0x3f) == 0 && ((1L << (_la - 35)) & ((1L << (ATOMIC_UINT - 35)) | (1L << (STRUCT - 35)) | (1L << (UINT16CONSTANT - 35)) | (1L << (INT16CONSTANT - 35)) | (1L << (UINT32CONSTANT - 35)) | (1L << (INT32CONSTANT - 35)) | (1L << (UINT64CONSTANT - 35)) | (1L << (INT64CONSTANT - 35)) | (1L << (FLOAT16CONSTANT - 35)) | (1L << (FLOAT32CONSTANT - 35)) | (1L << (FLOAT64CONSTANT - 35)) | (1L << (BOOLCONSTANT - 35)) | (1L << (BOOL - 35)) | (1L << (BVEC2 - 35)) | (1L << (BVEC3 - 35)) | (1L << (BVEC4 - 35)) | (1L << (FLOAT16 - 35)) | (1L << (F16VEC2 - 35)) | (1L << (F16VEC3 - 35)) | (1L << (F16VEC4 - 35)) | (1L << (F16MAT2X2 - 35)) | (1L << (F16MAT2X3 - 35)) | (1L << (F16MAT2X4 - 35)) | (1L << (F16MAT3X2 - 35)) | (1L << (F16MAT3X3 - 35)) | (1L << (F16MAT3X4 - 35)) | (1L << (F16MAT4X2 - 35)) | (1L << (F16MAT4X3 - 35)) | (1L << (F16MAT4X4 - 35)) | (1L << (FLOAT32 - 35)) | (1L << (F32VEC2 - 35)) | (1L << (F32VEC3 - 35)) | (1L << (F32VEC4 - 35)) | (1L << (F32MAT2X2 - 35)) | (1L << (F32MAT2X3 - 35)) | (1L << (F32MAT2X4 - 35)) | (1L << (F32MAT3X2 - 35)) | (1L << (F32MAT3X3 - 35)) | (1L << (F32MAT3X4 - 35)) | (1L << (F32MAT4X2 - 35)) | (1L << (F32MAT4X3 - 35)) | (1L << (F32MAT4X4 - 35)) | (1L << (FLOAT64 - 35)) | (1L << (F64VEC2 - 35)) | (1L << (F64VEC3 - 35)) | (1L << (F64VEC4 - 35)) | (1L << (F64MAT2X2 - 35)) | (1L << (F64MAT2X3 - 35)) | (1L << (F64MAT2X4 - 35)) | (1L << (F64MAT3X2 - 35)) | (1L << (F64MAT3X3 - 35)))) != 0) || ((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (F64MAT3X4 - 99)) | (1L << (F64MAT4X2 - 99)) | (1L << (F64MAT4X3 - 99)) | (1L << (F64MAT4X4 - 99)) | (1L << (INT8 - 99)) | (1L << (I8VEC2 - 99)) | (1L << (I8VEC3 - 99)) | (1L << (I8VEC4 - 99)) | (1L << (UINT8 - 99)) | (1L << (UI8VEC2 - 99)) | (1L << (UI8VEC3 - 99)) | (1L << (UI8VEC4 - 99)) | (1L << (INT16 - 99)) | (1L << (I16VEC2 - 99)) | (1L << (I16VEC3 - 99)) | (1L << (I16VEC4 - 99)) | (1L << (UINT16 - 99)) | (1L << (UI16VEC2 - 99)) | (1L << (UI16VEC3 - 99)) | (1L << (UI16VEC4 - 99)) | (1L << (INT32 - 99)) | (1L << (I32VEC2 - 99)) | (1L << (I32VEC3 - 99)) | (1L << (I32VEC4 - 99)) | (1L << (UINT32 - 99)) | (1L << (UI32VEC2 - 99)) | (1L << (UI32VEC3 - 99)) | (1L << (UI32VEC4 - 99)) | (1L << (INT64 - 99)) | (1L << (I64VEC2 - 99)) | (1L << (I64VEC3 - 99)) | (1L << (I64VEC4 - 99)) | (1L << (UINT64 - 99)) | (1L << (UI64VEC2 - 99)) | (1L << (UI64VEC3 - 99)) | (1L << (UI64VEC4 - 99)) | (1L << (IMAGE1D - 99)) | (1L << (IMAGE2D - 99)) | (1L << (IMAGE3D - 99)) | (1L << (UIMAGE1D - 99)) | (1L << (UIMAGE2D - 99)) | (1L << (UIMAGE3D - 99)) | (1L << (IIMAGE1D - 99)) | (1L << (IIMAGE2D - 99)) | (1L << (IIMAGE3D - 99)) | (1L << (SAMPLER1D - 99)) | (1L << (SAMPLER2D - 99)) | (1L << (SAMPLER3D - 99)) | (1L << (SAMPLER2DRECT - 99)) | (1L << (SAMPLER1DSHADOW - 99)) | (1L << (SAMPLER2DSHADOW - 99)) | (1L << (SAMPLER2DRECTSHADOW - 99)) | (1L << (SAMPLER1DARRAY - 99)) | (1L << (SAMPLER2DARRAY - 99)) | (1L << (SAMPLER1DARRAYSHADOW - 99)) | (1L << (SAMPLER2DARRAYSHADOW - 99)) | (1L << (ISAMPLER1D - 99)) | (1L << (ISAMPLER2D - 99)) | (1L << (ISAMPLER2DRECT - 99)) | (1L << (ISAMPLER3D - 99)) | (1L << (ISAMPLER1DARRAY - 99)) | (1L << (ISAMPLER2DARRAY - 99)) | (1L << (USAMPLER1D - 99)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (USAMPLER2D - 163)) | (1L << (USAMPLER2DRECT - 163)) | (1L << (USAMPLER3D - 163)) | (1L << (USAMPLER1DARRAY - 163)) | (1L << (USAMPLER2DARRAY - 163)) | (1L << (SAMPLER2DMS - 163)) | (1L << (ISAMPLER2DMS - 163)) | (1L << (USAMPLER2DMS - 163)) | (1L << (SAMPLER2DMSARRAY - 163)) | (1L << (ISAMPLER2DMSARRAY - 163)) | (1L << (USAMPLER2DMSARRAY - 163)) | (1L << (IMAGE2DRECT - 163)) | (1L << (IMAGE1DARRAY - 163)) | (1L << (IMAGE2DARRAY - 163)) | (1L << (IMAGE2DMS - 163)) | (1L << (IMAGE2DMSARRAY - 163)) | (1L << (IIMAGE2DRECT - 163)) | (1L << (IIMAGE1DARRAY - 163)) | (1L << (IIMAGE2DARRAY - 163)) | (1L << (IIMAGE2DMS - 163)) | (1L << (IIMAGE2DMSARRAY - 163)) | (1L << (UIMAGE2DRECT - 163)) | (1L << (UIMAGE1DARRAY - 163)) | (1L << (UIMAGE2DARRAY - 163)) | (1L << (UIMAGE2DMS - 163)) | (1L << (UIMAGE2DMSARRAY - 163)) | (1L << (SAMPLERCUBE - 163)) | (1L << (SAMPLERCUBESHADOW - 163)) | (1L << (SAMPLERBUFFER - 163)) | (1L << (SAMPLERCUBEARRAY - 163)) | (1L << (SAMPLERCUBEARRAYSHADOW - 163)) | (1L << (ISAMPLERCUBE - 163)) | (1L << (ISAMPLERBUFFER - 163)) | (1L << (ISAMPLERCUBEARRAY - 163)) | (1L << (USAMPLERCUBE - 163)) | (1L << (USAMPLERBUFFER - 163)) | (1L << (USAMPLERCUBEARRAY - 163)) | (1L << (IMAGECUBE - 163)) | (1L << (IMAGEBUFFER - 163)) | (1L << (IMAGECUBEARRAY - 163)) | (1L << (IIMAGECUBE - 163)) | (1L << (IIMAGEBUFFER - 163)) | (1L << (IIMAGECUBEARRAY - 163)) | (1L << (UIMAGECUBE - 163)) | (1L << (UIMAGEBUFFER - 163)) | (1L << (UIMAGECUBEARRAY - 163)) | (1L << (INC_OP - 163)) | (1L << (DEC_OP - 163)) | (1L << (VOID - 163)))) != 0) || ((((_la - 231)) & ~0x3f) == 0 && ((1L << (_la - 231)) & ((1L << (LPAREN - 231)) | (1L << (LBRACE - 231)) | (1L << (PLUS_OP - 231)) | (1L << (MINUS_OP - 231)) | (1L << (NOT_OP - 231)) | (1L << (BNEG_OP - 231)) | (1L << (IDENTIFIER - 231)))) != 0)) {
					{
					setState(688);
					initializer();
					setState(693);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(689);
							match(COMMA);
							setState(690);
							initializer();
							}
							} 
						}
						setState(695);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
					}
					setState(697);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(696);
						match(COMMA);
						}
					}

					}
				}

				setState(701);
				match(RBRACE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_statement);
		try {
			setState(706);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(704);
				compoundStatement();
				}
				break;
			case UNIFORM:
			case BUFFER:
			case IN:
			case OUT:
			case INOUT:
			case HIGHP:
			case MEDIUMP:
			case LOWP:
			case PRECISION:
			case CONST:
			case PRECISE:
			case INVARIANT:
			case SMOOTH:
			case FLAT:
			case NOPERSPECTIVE:
			case CENTROID:
			case SAMPLE:
			case PATCH:
			case ATTRIBUTE:
			case COHERENT:
			case VOLATILE:
			case RESTRICT:
			case VARYING:
			case READONLY:
			case WRITEONLY:
			case SHARED:
			case SUBROUTINE:
			case DEVICECOHERENT:
			case QUEUEFAMILYCOHERENT:
			case WORKGROUPCOHERENT:
			case SUBGROUPCOHERENT:
			case NONPRIVATE:
			case LAYOUT:
			case ATOMIC_UINT:
			case STRUCT:
			case IF:
			case SWITCH:
			case CASE:
			case DEFAULT:
			case WHILE:
			case DO:
			case FOR:
			case CONTINUE:
			case BREAK:
			case RETURN:
			case DISCARD:
			case DEMOTE:
			case UINT16CONSTANT:
			case INT16CONSTANT:
			case UINT32CONSTANT:
			case INT32CONSTANT:
			case UINT64CONSTANT:
			case INT64CONSTANT:
			case FLOAT16CONSTANT:
			case FLOAT32CONSTANT:
			case FLOAT64CONSTANT:
			case BOOLCONSTANT:
			case BOOL:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case FLOAT16:
			case F16VEC2:
			case F16VEC3:
			case F16VEC4:
			case F16MAT2X2:
			case F16MAT2X3:
			case F16MAT2X4:
			case F16MAT3X2:
			case F16MAT3X3:
			case F16MAT3X4:
			case F16MAT4X2:
			case F16MAT4X3:
			case F16MAT4X4:
			case FLOAT32:
			case F32VEC2:
			case F32VEC3:
			case F32VEC4:
			case F32MAT2X2:
			case F32MAT2X3:
			case F32MAT2X4:
			case F32MAT3X2:
			case F32MAT3X3:
			case F32MAT3X4:
			case F32MAT4X2:
			case F32MAT4X3:
			case F32MAT4X4:
			case FLOAT64:
			case F64VEC2:
			case F64VEC3:
			case F64VEC4:
			case F64MAT2X2:
			case F64MAT2X3:
			case F64MAT2X4:
			case F64MAT3X2:
			case F64MAT3X3:
			case F64MAT3X4:
			case F64MAT4X2:
			case F64MAT4X3:
			case F64MAT4X4:
			case INT8:
			case I8VEC2:
			case I8VEC3:
			case I8VEC4:
			case UINT8:
			case UI8VEC2:
			case UI8VEC3:
			case UI8VEC4:
			case INT16:
			case I16VEC2:
			case I16VEC3:
			case I16VEC4:
			case UINT16:
			case UI16VEC2:
			case UI16VEC3:
			case UI16VEC4:
			case INT32:
			case I32VEC2:
			case I32VEC3:
			case I32VEC4:
			case UINT32:
			case UI32VEC2:
			case UI32VEC3:
			case UI32VEC4:
			case INT64:
			case I64VEC2:
			case I64VEC3:
			case I64VEC4:
			case UINT64:
			case UI64VEC2:
			case UI64VEC3:
			case UI64VEC4:
			case IMAGE1D:
			case IMAGE2D:
			case IMAGE3D:
			case UIMAGE1D:
			case UIMAGE2D:
			case UIMAGE3D:
			case IIMAGE1D:
			case IIMAGE2D:
			case IIMAGE3D:
			case SAMPLER1D:
			case SAMPLER2D:
			case SAMPLER3D:
			case SAMPLER2DRECT:
			case SAMPLER1DSHADOW:
			case SAMPLER2DSHADOW:
			case SAMPLER2DRECTSHADOW:
			case SAMPLER1DARRAY:
			case SAMPLER2DARRAY:
			case SAMPLER1DARRAYSHADOW:
			case SAMPLER2DARRAYSHADOW:
			case ISAMPLER1D:
			case ISAMPLER2D:
			case ISAMPLER2DRECT:
			case ISAMPLER3D:
			case ISAMPLER1DARRAY:
			case ISAMPLER2DARRAY:
			case USAMPLER1D:
			case USAMPLER2D:
			case USAMPLER2DRECT:
			case USAMPLER3D:
			case USAMPLER1DARRAY:
			case USAMPLER2DARRAY:
			case SAMPLER2DMS:
			case ISAMPLER2DMS:
			case USAMPLER2DMS:
			case SAMPLER2DMSARRAY:
			case ISAMPLER2DMSARRAY:
			case USAMPLER2DMSARRAY:
			case IMAGE2DRECT:
			case IMAGE1DARRAY:
			case IMAGE2DARRAY:
			case IMAGE2DMS:
			case IMAGE2DMSARRAY:
			case IIMAGE2DRECT:
			case IIMAGE1DARRAY:
			case IIMAGE2DARRAY:
			case IIMAGE2DMS:
			case IIMAGE2DMSARRAY:
			case UIMAGE2DRECT:
			case UIMAGE1DARRAY:
			case UIMAGE2DARRAY:
			case UIMAGE2DMS:
			case UIMAGE2DMSARRAY:
			case SAMPLERCUBE:
			case SAMPLERCUBESHADOW:
			case SAMPLERBUFFER:
			case SAMPLERCUBEARRAY:
			case SAMPLERCUBEARRAYSHADOW:
			case ISAMPLERCUBE:
			case ISAMPLERBUFFER:
			case ISAMPLERCUBEARRAY:
			case USAMPLERCUBE:
			case USAMPLERBUFFER:
			case USAMPLERCUBEARRAY:
			case IMAGECUBE:
			case IMAGEBUFFER:
			case IMAGECUBEARRAY:
			case IIMAGECUBE:
			case IIMAGEBUFFER:
			case IIMAGECUBEARRAY:
			case UIMAGECUBE:
			case UIMAGEBUFFER:
			case UIMAGECUBEARRAY:
			case INC_OP:
			case DEC_OP:
			case VOID:
			case LPAREN:
			case SEMICOLON:
			case LBRACKET:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(705);
				simpleStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStatementContext extends ParserRuleContext {
		public DeclarationStatementContext declarationStatement() {
			return getRuleContext(DeclarationStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public SelectionStatementContext selectionStatement() {
			return getRuleContext(SelectionStatementContext.class,0);
		}
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public CaseLabelContext caseLabel() {
			return getRuleContext(CaseLabelContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public DemoteStatementContext demoteStatement() {
			return getRuleContext(DemoteStatementContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_simpleStatement);
		try {
			setState(719);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(708);
				declarationStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(709);
				expressionStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(710);
				emptyStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(711);
				selectionStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(712);
				switchStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(713);
				caseLabel();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(714);
				forStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(715);
				whileStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(716);
				doWhileStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(717);
				jumpStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(718);
				demoteStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GLSLParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSLParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(721);
			match(LBRACE);
			setState(725);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (PRECISION - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (IF - 2)) | (1L << (SWITCH - 2)) | (1L << (CASE - 2)) | (1L << (DEFAULT - 2)) | (1L << (WHILE - 2)) | (1L << (DO - 2)) | (1L << (FOR - 2)) | (1L << (CONTINUE - 2)) | (1L << (BREAK - 2)) | (1L << (RETURN - 2)) | (1L << (DISCARD - 2)) | (1L << (DEMOTE - 2)) | (1L << (UINT16CONSTANT - 2)) | (1L << (INT16CONSTANT - 2)) | (1L << (UINT32CONSTANT - 2)) | (1L << (INT32CONSTANT - 2)) | (1L << (UINT64CONSTANT - 2)) | (1L << (INT64CONSTANT - 2)) | (1L << (FLOAT16CONSTANT - 2)) | (1L << (FLOAT32CONSTANT - 2)) | (1L << (FLOAT64CONSTANT - 2)) | (1L << (BOOLCONSTANT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (INC_OP - 194)) | (1L << (DEC_OP - 194)) | (1L << (VOID - 194)) | (1L << (LPAREN - 194)) | (1L << (LBRACE - 194)) | (1L << (SEMICOLON - 194)) | (1L << (LBRACKET - 194)) | (1L << (PLUS_OP - 194)) | (1L << (MINUS_OP - 194)) | (1L << (NOT_OP - 194)) | (1L << (BNEG_OP - 194)) | (1L << (IDENTIFIER - 194)))) != 0)) {
				{
				{
				setState(722);
				statement();
				}
				}
				setState(727);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(728);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationStatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationStatement; }
	}

	public final DeclarationStatementContext declarationStatement() throws RecognitionException {
		DeclarationStatementContext _localctx = new DeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_declarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			declaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(732);
			expression();
			setState(733);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStatementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(735);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GLSLParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(GLSLParser.ELSE, 0); }
		public SelectionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionStatement; }
	}

	public final SelectionStatementContext selectionStatement() throws RecognitionException {
		SelectionStatementContext _localctx = new SelectionStatementContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_selectionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(738);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(737);
				attribute();
				}
			}

			setState(740);
			match(IF);
			setState(741);
			match(LPAREN);
			setState(742);
			expression();
			setState(743);
			match(RPAREN);
			setState(744);
			statement();
			setState(747);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(745);
				match(ELSE);
				setState(746);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FullySpecifiedTypeContext fullySpecifiedType() {
			return getRuleContext(FullySpecifiedTypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSLParser.ASSIGN_OP, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_condition);
		try {
			setState(755);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(749);
				expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(750);
				fullySpecifiedType();
				setState(751);
				match(IDENTIFIER);
				setState(752);
				match(ASSIGN_OP);
				setState(753);
				initializer();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(GLSLParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GLSLParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSLParser.RBRACE, 0); }
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(758);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(757);
				attribute();
				}
			}

			setState(760);
			match(SWITCH);
			setState(761);
			match(LPAREN);
			setState(762);
			expression();
			setState(763);
			match(RPAREN);
			setState(764);
			match(LBRACE);
			setState(768);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (PRECISION - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (IF - 2)) | (1L << (SWITCH - 2)) | (1L << (CASE - 2)) | (1L << (DEFAULT - 2)) | (1L << (WHILE - 2)) | (1L << (DO - 2)) | (1L << (FOR - 2)) | (1L << (CONTINUE - 2)) | (1L << (BREAK - 2)) | (1L << (RETURN - 2)) | (1L << (DISCARD - 2)) | (1L << (DEMOTE - 2)) | (1L << (UINT16CONSTANT - 2)) | (1L << (INT16CONSTANT - 2)) | (1L << (UINT32CONSTANT - 2)) | (1L << (INT32CONSTANT - 2)) | (1L << (UINT64CONSTANT - 2)) | (1L << (INT64CONSTANT - 2)) | (1L << (FLOAT16CONSTANT - 2)) | (1L << (FLOAT32CONSTANT - 2)) | (1L << (FLOAT64CONSTANT - 2)) | (1L << (BOOLCONSTANT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (INC_OP - 194)) | (1L << (DEC_OP - 194)) | (1L << (VOID - 194)) | (1L << (LPAREN - 194)) | (1L << (LBRACE - 194)) | (1L << (SEMICOLON - 194)) | (1L << (LBRACKET - 194)) | (1L << (PLUS_OP - 194)) | (1L << (MINUS_OP - 194)) | (1L << (NOT_OP - 194)) | (1L << (BNEG_OP - 194)) | (1L << (IDENTIFIER - 194)))) != 0)) {
				{
				{
				setState(765);
				statement();
				}
				}
				setState(770);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(771);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseLabelContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(GLSLParser.COLON, 0); }
		public TerminalNode CASE() { return getToken(GLSLParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(GLSLParser.DEFAULT, 0); }
		public CaseLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseLabel; }
	}

	public final CaseLabelContext caseLabel() throws RecognitionException {
		CaseLabelContext _localctx = new CaseLabelContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_caseLabel);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(776);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(773);
				match(CASE);
				setState(774);
				expression();
				}
				break;
			case DEFAULT:
				{
				setState(775);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(778);
			match(COLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GLSLParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_whileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(781);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(780);
				attribute();
				}
			}

			setState(783);
			match(WHILE);
			setState(784);
			match(LPAREN);
			setState(785);
			condition();
			setState(786);
			match(RPAREN);
			setState(787);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(GLSLParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(GLSLParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_doWhileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(789);
				attribute();
				}
			}

			setState(792);
			match(DO);
			setState(793);
			statement();
			setState(794);
			match(WHILE);
			setState(795);
			match(LPAREN);
			setState(796);
			expression();
			setState(797);
			match(RPAREN);
			setState(798);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GLSLParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLParser.LPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public TerminalNode RPAREN() { return getToken(GLSLParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public DeclarationStatementContext declarationStatement() {
			return getRuleContext(DeclarationStatementContext.class,0);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(801);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(800);
				attribute();
				}
			}

			setState(803);
			match(FOR);
			setState(804);
			match(LPAREN);
			setState(808);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				{
				setState(805);
				emptyStatement();
				}
				break;
			case 2:
				{
				setState(806);
				expressionStatement();
				}
				break;
			case 3:
				{
				setState(807);
				declarationStatement();
				}
				break;
			}
			setState(811);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (UNIFORM - 2)) | (1L << (BUFFER - 2)) | (1L << (IN - 2)) | (1L << (OUT - 2)) | (1L << (INOUT - 2)) | (1L << (HIGHP - 2)) | (1L << (MEDIUMP - 2)) | (1L << (LOWP - 2)) | (1L << (CONST - 2)) | (1L << (PRECISE - 2)) | (1L << (INVARIANT - 2)) | (1L << (SMOOTH - 2)) | (1L << (FLAT - 2)) | (1L << (NOPERSPECTIVE - 2)) | (1L << (CENTROID - 2)) | (1L << (SAMPLE - 2)) | (1L << (PATCH - 2)) | (1L << (ATTRIBUTE - 2)) | (1L << (COHERENT - 2)) | (1L << (VOLATILE - 2)) | (1L << (RESTRICT - 2)) | (1L << (VARYING - 2)) | (1L << (READONLY - 2)) | (1L << (WRITEONLY - 2)) | (1L << (SHARED - 2)) | (1L << (SUBROUTINE - 2)) | (1L << (DEVICECOHERENT - 2)) | (1L << (QUEUEFAMILYCOHERENT - 2)) | (1L << (WORKGROUPCOHERENT - 2)) | (1L << (SUBGROUPCOHERENT - 2)) | (1L << (NONPRIVATE - 2)) | (1L << (LAYOUT - 2)) | (1L << (ATOMIC_UINT - 2)) | (1L << (STRUCT - 2)) | (1L << (UINT16CONSTANT - 2)) | (1L << (INT16CONSTANT - 2)) | (1L << (UINT32CONSTANT - 2)) | (1L << (INT32CONSTANT - 2)) | (1L << (UINT64CONSTANT - 2)) | (1L << (INT64CONSTANT - 2)) | (1L << (FLOAT16CONSTANT - 2)) | (1L << (FLOAT32CONSTANT - 2)) | (1L << (FLOAT64CONSTANT - 2)) | (1L << (BOOLCONSTANT - 2)) | (1L << (BOOL - 2)) | (1L << (BVEC2 - 2)) | (1L << (BVEC3 - 2)) | (1L << (BVEC4 - 2)) | (1L << (FLOAT16 - 2)) | (1L << (F16VEC2 - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (F16VEC3 - 66)) | (1L << (F16VEC4 - 66)) | (1L << (F16MAT2X2 - 66)) | (1L << (F16MAT2X3 - 66)) | (1L << (F16MAT2X4 - 66)) | (1L << (F16MAT3X2 - 66)) | (1L << (F16MAT3X3 - 66)) | (1L << (F16MAT3X4 - 66)) | (1L << (F16MAT4X2 - 66)) | (1L << (F16MAT4X3 - 66)) | (1L << (F16MAT4X4 - 66)) | (1L << (FLOAT32 - 66)) | (1L << (F32VEC2 - 66)) | (1L << (F32VEC3 - 66)) | (1L << (F32VEC4 - 66)) | (1L << (F32MAT2X2 - 66)) | (1L << (F32MAT2X3 - 66)) | (1L << (F32MAT2X4 - 66)) | (1L << (F32MAT3X2 - 66)) | (1L << (F32MAT3X3 - 66)) | (1L << (F32MAT3X4 - 66)) | (1L << (F32MAT4X2 - 66)) | (1L << (F32MAT4X3 - 66)) | (1L << (F32MAT4X4 - 66)) | (1L << (FLOAT64 - 66)) | (1L << (F64VEC2 - 66)) | (1L << (F64VEC3 - 66)) | (1L << (F64VEC4 - 66)) | (1L << (F64MAT2X2 - 66)) | (1L << (F64MAT2X3 - 66)) | (1L << (F64MAT2X4 - 66)) | (1L << (F64MAT3X2 - 66)) | (1L << (F64MAT3X3 - 66)) | (1L << (F64MAT3X4 - 66)) | (1L << (F64MAT4X2 - 66)) | (1L << (F64MAT4X3 - 66)) | (1L << (F64MAT4X4 - 66)) | (1L << (INT8 - 66)) | (1L << (I8VEC2 - 66)) | (1L << (I8VEC3 - 66)) | (1L << (I8VEC4 - 66)) | (1L << (UINT8 - 66)) | (1L << (UI8VEC2 - 66)) | (1L << (UI8VEC3 - 66)) | (1L << (UI8VEC4 - 66)) | (1L << (INT16 - 66)) | (1L << (I16VEC2 - 66)) | (1L << (I16VEC3 - 66)) | (1L << (I16VEC4 - 66)) | (1L << (UINT16 - 66)) | (1L << (UI16VEC2 - 66)) | (1L << (UI16VEC3 - 66)) | (1L << (UI16VEC4 - 66)) | (1L << (INT32 - 66)) | (1L << (I32VEC2 - 66)) | (1L << (I32VEC3 - 66)) | (1L << (I32VEC4 - 66)) | (1L << (UINT32 - 66)) | (1L << (UI32VEC2 - 66)) | (1L << (UI32VEC3 - 66)) | (1L << (UI32VEC4 - 66)) | (1L << (INT64 - 66)) | (1L << (I64VEC2 - 66)) | (1L << (I64VEC3 - 66)))) != 0) || ((((_la - 130)) & ~0x3f) == 0 && ((1L << (_la - 130)) & ((1L << (I64VEC4 - 130)) | (1L << (UINT64 - 130)) | (1L << (UI64VEC2 - 130)) | (1L << (UI64VEC3 - 130)) | (1L << (UI64VEC4 - 130)) | (1L << (IMAGE1D - 130)) | (1L << (IMAGE2D - 130)) | (1L << (IMAGE3D - 130)) | (1L << (UIMAGE1D - 130)) | (1L << (UIMAGE2D - 130)) | (1L << (UIMAGE3D - 130)) | (1L << (IIMAGE1D - 130)) | (1L << (IIMAGE2D - 130)) | (1L << (IIMAGE3D - 130)) | (1L << (SAMPLER1D - 130)) | (1L << (SAMPLER2D - 130)) | (1L << (SAMPLER3D - 130)) | (1L << (SAMPLER2DRECT - 130)) | (1L << (SAMPLER1DSHADOW - 130)) | (1L << (SAMPLER2DSHADOW - 130)) | (1L << (SAMPLER2DRECTSHADOW - 130)) | (1L << (SAMPLER1DARRAY - 130)) | (1L << (SAMPLER2DARRAY - 130)) | (1L << (SAMPLER1DARRAYSHADOW - 130)) | (1L << (SAMPLER2DARRAYSHADOW - 130)) | (1L << (ISAMPLER1D - 130)) | (1L << (ISAMPLER2D - 130)) | (1L << (ISAMPLER2DRECT - 130)) | (1L << (ISAMPLER3D - 130)) | (1L << (ISAMPLER1DARRAY - 130)) | (1L << (ISAMPLER2DARRAY - 130)) | (1L << (USAMPLER1D - 130)) | (1L << (USAMPLER2D - 130)) | (1L << (USAMPLER2DRECT - 130)) | (1L << (USAMPLER3D - 130)) | (1L << (USAMPLER1DARRAY - 130)) | (1L << (USAMPLER2DARRAY - 130)) | (1L << (SAMPLER2DMS - 130)) | (1L << (ISAMPLER2DMS - 130)) | (1L << (USAMPLER2DMS - 130)) | (1L << (SAMPLER2DMSARRAY - 130)) | (1L << (ISAMPLER2DMSARRAY - 130)) | (1L << (USAMPLER2DMSARRAY - 130)) | (1L << (IMAGE2DRECT - 130)) | (1L << (IMAGE1DARRAY - 130)) | (1L << (IMAGE2DARRAY - 130)) | (1L << (IMAGE2DMS - 130)) | (1L << (IMAGE2DMSARRAY - 130)) | (1L << (IIMAGE2DRECT - 130)) | (1L << (IIMAGE1DARRAY - 130)) | (1L << (IIMAGE2DARRAY - 130)) | (1L << (IIMAGE2DMS - 130)) | (1L << (IIMAGE2DMSARRAY - 130)) | (1L << (UIMAGE2DRECT - 130)) | (1L << (UIMAGE1DARRAY - 130)) | (1L << (UIMAGE2DARRAY - 130)) | (1L << (UIMAGE2DMS - 130)) | (1L << (UIMAGE2DMSARRAY - 130)) | (1L << (SAMPLERCUBE - 130)) | (1L << (SAMPLERCUBESHADOW - 130)) | (1L << (SAMPLERBUFFER - 130)) | (1L << (SAMPLERCUBEARRAY - 130)) | (1L << (SAMPLERCUBEARRAYSHADOW - 130)))) != 0) || ((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (ISAMPLERCUBE - 194)) | (1L << (ISAMPLERBUFFER - 194)) | (1L << (ISAMPLERCUBEARRAY - 194)) | (1L << (USAMPLERCUBE - 194)) | (1L << (USAMPLERBUFFER - 194)) | (1L << (USAMPLERCUBEARRAY - 194)) | (1L << (IMAGECUBE - 194)) | (1L << (IMAGEBUFFER - 194)) | (1L << (IMAGECUBEARRAY - 194)) | (1L << (IIMAGECUBE - 194)) | (1L << (IIMAGEBUFFER - 194)) | (1L << (IIMAGECUBEARRAY - 194)) | (1L << (UIMAGECUBE - 194)) | (1L << (UIMAGEBUFFER - 194)) | (1L << (UIMAGECUBEARRAY - 194)) | (1L << (INC_OP - 194)) | (1L << (DEC_OP - 194)) | (1L << (VOID - 194)) | (1L << (LPAREN - 194)) | (1L << (PLUS_OP - 194)) | (1L << (MINUS_OP - 194)) | (1L << (NOT_OP - 194)) | (1L << (BNEG_OP - 194)) | (1L << (IDENTIFIER - 194)))) != 0)) {
				{
				setState(810);
				condition();
				}
			}

			setState(813);
			match(SEMICOLON);
			setState(815);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 35)) & ~0x3f) == 0 && ((1L << (_la - 35)) & ((1L << (ATOMIC_UINT - 35)) | (1L << (STRUCT - 35)) | (1L << (UINT16CONSTANT - 35)) | (1L << (INT16CONSTANT - 35)) | (1L << (UINT32CONSTANT - 35)) | (1L << (INT32CONSTANT - 35)) | (1L << (UINT64CONSTANT - 35)) | (1L << (INT64CONSTANT - 35)) | (1L << (FLOAT16CONSTANT - 35)) | (1L << (FLOAT32CONSTANT - 35)) | (1L << (FLOAT64CONSTANT - 35)) | (1L << (BOOLCONSTANT - 35)) | (1L << (BOOL - 35)) | (1L << (BVEC2 - 35)) | (1L << (BVEC3 - 35)) | (1L << (BVEC4 - 35)) | (1L << (FLOAT16 - 35)) | (1L << (F16VEC2 - 35)) | (1L << (F16VEC3 - 35)) | (1L << (F16VEC4 - 35)) | (1L << (F16MAT2X2 - 35)) | (1L << (F16MAT2X3 - 35)) | (1L << (F16MAT2X4 - 35)) | (1L << (F16MAT3X2 - 35)) | (1L << (F16MAT3X3 - 35)) | (1L << (F16MAT3X4 - 35)) | (1L << (F16MAT4X2 - 35)) | (1L << (F16MAT4X3 - 35)) | (1L << (F16MAT4X4 - 35)) | (1L << (FLOAT32 - 35)) | (1L << (F32VEC2 - 35)) | (1L << (F32VEC3 - 35)) | (1L << (F32VEC4 - 35)) | (1L << (F32MAT2X2 - 35)) | (1L << (F32MAT2X3 - 35)) | (1L << (F32MAT2X4 - 35)) | (1L << (F32MAT3X2 - 35)) | (1L << (F32MAT3X3 - 35)) | (1L << (F32MAT3X4 - 35)) | (1L << (F32MAT4X2 - 35)) | (1L << (F32MAT4X3 - 35)) | (1L << (F32MAT4X4 - 35)) | (1L << (FLOAT64 - 35)) | (1L << (F64VEC2 - 35)) | (1L << (F64VEC3 - 35)) | (1L << (F64VEC4 - 35)) | (1L << (F64MAT2X2 - 35)) | (1L << (F64MAT2X3 - 35)) | (1L << (F64MAT2X4 - 35)) | (1L << (F64MAT3X2 - 35)) | (1L << (F64MAT3X3 - 35)))) != 0) || ((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (F64MAT3X4 - 99)) | (1L << (F64MAT4X2 - 99)) | (1L << (F64MAT4X3 - 99)) | (1L << (F64MAT4X4 - 99)) | (1L << (INT8 - 99)) | (1L << (I8VEC2 - 99)) | (1L << (I8VEC3 - 99)) | (1L << (I8VEC4 - 99)) | (1L << (UINT8 - 99)) | (1L << (UI8VEC2 - 99)) | (1L << (UI8VEC3 - 99)) | (1L << (UI8VEC4 - 99)) | (1L << (INT16 - 99)) | (1L << (I16VEC2 - 99)) | (1L << (I16VEC3 - 99)) | (1L << (I16VEC4 - 99)) | (1L << (UINT16 - 99)) | (1L << (UI16VEC2 - 99)) | (1L << (UI16VEC3 - 99)) | (1L << (UI16VEC4 - 99)) | (1L << (INT32 - 99)) | (1L << (I32VEC2 - 99)) | (1L << (I32VEC3 - 99)) | (1L << (I32VEC4 - 99)) | (1L << (UINT32 - 99)) | (1L << (UI32VEC2 - 99)) | (1L << (UI32VEC3 - 99)) | (1L << (UI32VEC4 - 99)) | (1L << (INT64 - 99)) | (1L << (I64VEC2 - 99)) | (1L << (I64VEC3 - 99)) | (1L << (I64VEC4 - 99)) | (1L << (UINT64 - 99)) | (1L << (UI64VEC2 - 99)) | (1L << (UI64VEC3 - 99)) | (1L << (UI64VEC4 - 99)) | (1L << (IMAGE1D - 99)) | (1L << (IMAGE2D - 99)) | (1L << (IMAGE3D - 99)) | (1L << (UIMAGE1D - 99)) | (1L << (UIMAGE2D - 99)) | (1L << (UIMAGE3D - 99)) | (1L << (IIMAGE1D - 99)) | (1L << (IIMAGE2D - 99)) | (1L << (IIMAGE3D - 99)) | (1L << (SAMPLER1D - 99)) | (1L << (SAMPLER2D - 99)) | (1L << (SAMPLER3D - 99)) | (1L << (SAMPLER2DRECT - 99)) | (1L << (SAMPLER1DSHADOW - 99)) | (1L << (SAMPLER2DSHADOW - 99)) | (1L << (SAMPLER2DRECTSHADOW - 99)) | (1L << (SAMPLER1DARRAY - 99)) | (1L << (SAMPLER2DARRAY - 99)) | (1L << (SAMPLER1DARRAYSHADOW - 99)) | (1L << (SAMPLER2DARRAYSHADOW - 99)) | (1L << (ISAMPLER1D - 99)) | (1L << (ISAMPLER2D - 99)) | (1L << (ISAMPLER2DRECT - 99)) | (1L << (ISAMPLER3D - 99)) | (1L << (ISAMPLER1DARRAY - 99)) | (1L << (ISAMPLER2DARRAY - 99)) | (1L << (USAMPLER1D - 99)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (USAMPLER2D - 163)) | (1L << (USAMPLER2DRECT - 163)) | (1L << (USAMPLER3D - 163)) | (1L << (USAMPLER1DARRAY - 163)) | (1L << (USAMPLER2DARRAY - 163)) | (1L << (SAMPLER2DMS - 163)) | (1L << (ISAMPLER2DMS - 163)) | (1L << (USAMPLER2DMS - 163)) | (1L << (SAMPLER2DMSARRAY - 163)) | (1L << (ISAMPLER2DMSARRAY - 163)) | (1L << (USAMPLER2DMSARRAY - 163)) | (1L << (IMAGE2DRECT - 163)) | (1L << (IMAGE1DARRAY - 163)) | (1L << (IMAGE2DARRAY - 163)) | (1L << (IMAGE2DMS - 163)) | (1L << (IMAGE2DMSARRAY - 163)) | (1L << (IIMAGE2DRECT - 163)) | (1L << (IIMAGE1DARRAY - 163)) | (1L << (IIMAGE2DARRAY - 163)) | (1L << (IIMAGE2DMS - 163)) | (1L << (IIMAGE2DMSARRAY - 163)) | (1L << (UIMAGE2DRECT - 163)) | (1L << (UIMAGE1DARRAY - 163)) | (1L << (UIMAGE2DARRAY - 163)) | (1L << (UIMAGE2DMS - 163)) | (1L << (UIMAGE2DMSARRAY - 163)) | (1L << (SAMPLERCUBE - 163)) | (1L << (SAMPLERCUBESHADOW - 163)) | (1L << (SAMPLERBUFFER - 163)) | (1L << (SAMPLERCUBEARRAY - 163)) | (1L << (SAMPLERCUBEARRAYSHADOW - 163)) | (1L << (ISAMPLERCUBE - 163)) | (1L << (ISAMPLERBUFFER - 163)) | (1L << (ISAMPLERCUBEARRAY - 163)) | (1L << (USAMPLERCUBE - 163)) | (1L << (USAMPLERBUFFER - 163)) | (1L << (USAMPLERCUBEARRAY - 163)) | (1L << (IMAGECUBE - 163)) | (1L << (IMAGEBUFFER - 163)) | (1L << (IMAGECUBEARRAY - 163)) | (1L << (IIMAGECUBE - 163)) | (1L << (IIMAGEBUFFER - 163)) | (1L << (IIMAGECUBEARRAY - 163)) | (1L << (UIMAGECUBE - 163)) | (1L << (UIMAGEBUFFER - 163)) | (1L << (UIMAGECUBEARRAY - 163)) | (1L << (INC_OP - 163)) | (1L << (DEC_OP - 163)) | (1L << (VOID - 163)))) != 0) || ((((_la - 231)) & ~0x3f) == 0 && ((1L << (_la - 231)) & ((1L << (LPAREN - 231)) | (1L << (PLUS_OP - 231)) | (1L << (MINUS_OP - 231)) | (1L << (NOT_OP - 231)) | (1L << (BNEG_OP - 231)) | (1L << (IDENTIFIER - 231)))) != 0)) {
				{
				setState(814);
				expression();
				}
			}

			setState(817);
			match(RPAREN);
			setState(818);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public TerminalNode CONTINUE() { return getToken(GLSLParser.CONTINUE, 0); }
		public TerminalNode BREAK() { return getToken(GLSLParser.BREAK, 0); }
		public TerminalNode RETURN() { return getToken(GLSLParser.RETURN, 0); }
		public TerminalNode DISCARD() { return getToken(GLSLParser.DISCARD, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_jumpStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(827);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONTINUE:
				{
				setState(820);
				match(CONTINUE);
				}
				break;
			case BREAK:
				{
				setState(821);
				match(BREAK);
				}
				break;
			case RETURN:
				{
				setState(822);
				match(RETURN);
				setState(824);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 35)) & ~0x3f) == 0 && ((1L << (_la - 35)) & ((1L << (ATOMIC_UINT - 35)) | (1L << (STRUCT - 35)) | (1L << (UINT16CONSTANT - 35)) | (1L << (INT16CONSTANT - 35)) | (1L << (UINT32CONSTANT - 35)) | (1L << (INT32CONSTANT - 35)) | (1L << (UINT64CONSTANT - 35)) | (1L << (INT64CONSTANT - 35)) | (1L << (FLOAT16CONSTANT - 35)) | (1L << (FLOAT32CONSTANT - 35)) | (1L << (FLOAT64CONSTANT - 35)) | (1L << (BOOLCONSTANT - 35)) | (1L << (BOOL - 35)) | (1L << (BVEC2 - 35)) | (1L << (BVEC3 - 35)) | (1L << (BVEC4 - 35)) | (1L << (FLOAT16 - 35)) | (1L << (F16VEC2 - 35)) | (1L << (F16VEC3 - 35)) | (1L << (F16VEC4 - 35)) | (1L << (F16MAT2X2 - 35)) | (1L << (F16MAT2X3 - 35)) | (1L << (F16MAT2X4 - 35)) | (1L << (F16MAT3X2 - 35)) | (1L << (F16MAT3X3 - 35)) | (1L << (F16MAT3X4 - 35)) | (1L << (F16MAT4X2 - 35)) | (1L << (F16MAT4X3 - 35)) | (1L << (F16MAT4X4 - 35)) | (1L << (FLOAT32 - 35)) | (1L << (F32VEC2 - 35)) | (1L << (F32VEC3 - 35)) | (1L << (F32VEC4 - 35)) | (1L << (F32MAT2X2 - 35)) | (1L << (F32MAT2X3 - 35)) | (1L << (F32MAT2X4 - 35)) | (1L << (F32MAT3X2 - 35)) | (1L << (F32MAT3X3 - 35)) | (1L << (F32MAT3X4 - 35)) | (1L << (F32MAT4X2 - 35)) | (1L << (F32MAT4X3 - 35)) | (1L << (F32MAT4X4 - 35)) | (1L << (FLOAT64 - 35)) | (1L << (F64VEC2 - 35)) | (1L << (F64VEC3 - 35)) | (1L << (F64VEC4 - 35)) | (1L << (F64MAT2X2 - 35)) | (1L << (F64MAT2X3 - 35)) | (1L << (F64MAT2X4 - 35)) | (1L << (F64MAT3X2 - 35)) | (1L << (F64MAT3X3 - 35)))) != 0) || ((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (F64MAT3X4 - 99)) | (1L << (F64MAT4X2 - 99)) | (1L << (F64MAT4X3 - 99)) | (1L << (F64MAT4X4 - 99)) | (1L << (INT8 - 99)) | (1L << (I8VEC2 - 99)) | (1L << (I8VEC3 - 99)) | (1L << (I8VEC4 - 99)) | (1L << (UINT8 - 99)) | (1L << (UI8VEC2 - 99)) | (1L << (UI8VEC3 - 99)) | (1L << (UI8VEC4 - 99)) | (1L << (INT16 - 99)) | (1L << (I16VEC2 - 99)) | (1L << (I16VEC3 - 99)) | (1L << (I16VEC4 - 99)) | (1L << (UINT16 - 99)) | (1L << (UI16VEC2 - 99)) | (1L << (UI16VEC3 - 99)) | (1L << (UI16VEC4 - 99)) | (1L << (INT32 - 99)) | (1L << (I32VEC2 - 99)) | (1L << (I32VEC3 - 99)) | (1L << (I32VEC4 - 99)) | (1L << (UINT32 - 99)) | (1L << (UI32VEC2 - 99)) | (1L << (UI32VEC3 - 99)) | (1L << (UI32VEC4 - 99)) | (1L << (INT64 - 99)) | (1L << (I64VEC2 - 99)) | (1L << (I64VEC3 - 99)) | (1L << (I64VEC4 - 99)) | (1L << (UINT64 - 99)) | (1L << (UI64VEC2 - 99)) | (1L << (UI64VEC3 - 99)) | (1L << (UI64VEC4 - 99)) | (1L << (IMAGE1D - 99)) | (1L << (IMAGE2D - 99)) | (1L << (IMAGE3D - 99)) | (1L << (UIMAGE1D - 99)) | (1L << (UIMAGE2D - 99)) | (1L << (UIMAGE3D - 99)) | (1L << (IIMAGE1D - 99)) | (1L << (IIMAGE2D - 99)) | (1L << (IIMAGE3D - 99)) | (1L << (SAMPLER1D - 99)) | (1L << (SAMPLER2D - 99)) | (1L << (SAMPLER3D - 99)) | (1L << (SAMPLER2DRECT - 99)) | (1L << (SAMPLER1DSHADOW - 99)) | (1L << (SAMPLER2DSHADOW - 99)) | (1L << (SAMPLER2DRECTSHADOW - 99)) | (1L << (SAMPLER1DARRAY - 99)) | (1L << (SAMPLER2DARRAY - 99)) | (1L << (SAMPLER1DARRAYSHADOW - 99)) | (1L << (SAMPLER2DARRAYSHADOW - 99)) | (1L << (ISAMPLER1D - 99)) | (1L << (ISAMPLER2D - 99)) | (1L << (ISAMPLER2DRECT - 99)) | (1L << (ISAMPLER3D - 99)) | (1L << (ISAMPLER1DARRAY - 99)) | (1L << (ISAMPLER2DARRAY - 99)) | (1L << (USAMPLER1D - 99)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (USAMPLER2D - 163)) | (1L << (USAMPLER2DRECT - 163)) | (1L << (USAMPLER3D - 163)) | (1L << (USAMPLER1DARRAY - 163)) | (1L << (USAMPLER2DARRAY - 163)) | (1L << (SAMPLER2DMS - 163)) | (1L << (ISAMPLER2DMS - 163)) | (1L << (USAMPLER2DMS - 163)) | (1L << (SAMPLER2DMSARRAY - 163)) | (1L << (ISAMPLER2DMSARRAY - 163)) | (1L << (USAMPLER2DMSARRAY - 163)) | (1L << (IMAGE2DRECT - 163)) | (1L << (IMAGE1DARRAY - 163)) | (1L << (IMAGE2DARRAY - 163)) | (1L << (IMAGE2DMS - 163)) | (1L << (IMAGE2DMSARRAY - 163)) | (1L << (IIMAGE2DRECT - 163)) | (1L << (IIMAGE1DARRAY - 163)) | (1L << (IIMAGE2DARRAY - 163)) | (1L << (IIMAGE2DMS - 163)) | (1L << (IIMAGE2DMSARRAY - 163)) | (1L << (UIMAGE2DRECT - 163)) | (1L << (UIMAGE1DARRAY - 163)) | (1L << (UIMAGE2DARRAY - 163)) | (1L << (UIMAGE2DMS - 163)) | (1L << (UIMAGE2DMSARRAY - 163)) | (1L << (SAMPLERCUBE - 163)) | (1L << (SAMPLERCUBESHADOW - 163)) | (1L << (SAMPLERBUFFER - 163)) | (1L << (SAMPLERCUBEARRAY - 163)) | (1L << (SAMPLERCUBEARRAYSHADOW - 163)) | (1L << (ISAMPLERCUBE - 163)) | (1L << (ISAMPLERBUFFER - 163)) | (1L << (ISAMPLERCUBEARRAY - 163)) | (1L << (USAMPLERCUBE - 163)) | (1L << (USAMPLERBUFFER - 163)) | (1L << (USAMPLERCUBEARRAY - 163)) | (1L << (IMAGECUBE - 163)) | (1L << (IMAGEBUFFER - 163)) | (1L << (IMAGECUBEARRAY - 163)) | (1L << (IIMAGECUBE - 163)) | (1L << (IIMAGEBUFFER - 163)) | (1L << (IIMAGECUBEARRAY - 163)) | (1L << (UIMAGECUBE - 163)) | (1L << (UIMAGEBUFFER - 163)) | (1L << (UIMAGECUBEARRAY - 163)) | (1L << (INC_OP - 163)) | (1L << (DEC_OP - 163)) | (1L << (VOID - 163)))) != 0) || ((((_la - 231)) & ~0x3f) == 0 && ((1L << (_la - 231)) & ((1L << (LPAREN - 231)) | (1L << (PLUS_OP - 231)) | (1L << (MINUS_OP - 231)) | (1L << (NOT_OP - 231)) | (1L << (BNEG_OP - 231)) | (1L << (IDENTIFIER - 231)))) != 0)) {
					{
					setState(823);
					expression();
					}
				}

				}
				break;
			case DISCARD:
				{
				setState(826);
				match(DISCARD);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(829);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DemoteStatementContext extends ParserRuleContext {
		public TerminalNode DEMOTE() { return getToken(GLSLParser.DEMOTE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLParser.SEMICOLON, 0); }
		public DemoteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_demoteStatement; }
	}

	public final DemoteStatementContext demoteStatement() throws RecognitionException {
		DemoteStatementContext _localctx = new DemoteStatementContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_demoteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(831);
			match(DEMOTE);
			setState(832);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return postfixExpression_sempred((PostfixExpressionContext)_localctx, predIndex);
		case 56:
			return arraySpecifier_sempred((ArraySpecifierContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean arraySpecifier_sempred(ArraySpecifierContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0120\u0345\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\3\2\3\2\7\2"+
		"\u00a7\n\2\f\2\16\2\u00aa\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3\u00b2\n\3\3"+
		"\3\5\3\u00b5\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00bd\n\4\3\5\3\5\3\5\5\5"+
		"\u00c2\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00cd\n\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\5\6\u00d6\n\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\5\f\u00f6\n\f\3\r\3\r\3\r\5\r\u00fb\n\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0108\n\r\7\r\u010a\n\r\f\r\16\r\u010d"+
		"\13\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\7\20"+
		"\u011b\n\20\f\20\16\20\u011e\13\20\5\20\u0120\n\20\3\20\3\20\3\21\3\21"+
		"\5\21\u0126\n\21\3\22\3\22\3\22\3\22\5\22\u012c\n\22\3\23\3\23\3\24\3"+
		"\24\3\24\7\24\u0133\n\24\f\24\16\24\u0136\13\24\3\25\3\25\3\25\7\25\u013b"+
		"\n\25\f\25\16\25\u013e\13\25\3\26\3\26\3\26\7\26\u0143\n\26\f\26\16\26"+
		"\u0146\13\26\3\27\3\27\3\27\7\27\u014b\n\27\f\27\16\27\u014e\13\27\3\30"+
		"\3\30\3\30\7\30\u0153\n\30\f\30\16\30\u0156\13\30\3\31\3\31\3\31\7\31"+
		"\u015b\n\31\f\31\16\31\u015e\13\31\3\32\3\32\3\32\7\32\u0163\n\32\f\32"+
		"\16\32\u0166\13\32\3\33\3\33\3\33\7\33\u016b\n\33\f\33\16\33\u016e\13"+
		"\33\3\34\3\34\3\34\7\34\u0173\n\34\f\34\16\34\u0176\13\34\3\35\3\35\3"+
		"\35\7\35\u017b\n\35\f\35\16\35\u017e\13\35\3\36\3\36\3\36\7\36\u0183\n"+
		"\36\f\36\16\36\u0186\13\36\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u018e\n"+
		"\37\f\37\16\37\u0191\13\37\3 \3 \3 \3 \3 \5 \u0198\n \3!\3!\3\"\3\"\3"+
		"\"\7\"\u019f\n\"\f\"\16\"\u01a2\13\"\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u01b8\n$\5$\u01ba\n$\3$\3$\3$\3$\3$\3$"+
		"\7$\u01c2\n$\f$\16$\u01c5\13$\5$\u01c7\n$\3$\3$\5$\u01cb\n$\3%\5%\u01ce"+
		"\n%\3%\3%\3%\3%\3%\5%\u01d5\n%\3&\3&\3&\7&\u01da\n&\f&\16&\u01dd\13&\5"+
		"&\u01df\n&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\5(\u01eb\n(\3)\5)\u01ee\n"+
		")\3)\3)\5)\u01f2\n)\3*\3*\3*\3*\3*\7*\u01f9\n*\f*\16*\u01fc\13*\3*\3*"+
		"\3*\3+\3+\3+\5+\u0204\n+\3+\3+\3+\3+\3+\5+\u020b\n+\3,\3,\3,\3-\5-\u0211"+
		"\n-\3-\3-\7-\u0215\n-\f-\16-\u0218\13-\3.\3.\5.\u021c\n.\3.\3.\5.\u0220"+
		"\n.\3/\5/\u0223\n/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60"+
		"\u023d\n\60\3\60\3\60\3\60\3\60\3\60\5\60\u0244\n\60\3\61\3\61\3\61\3"+
		"\61\3\61\7\61\u024b\n\61\f\61\16\61\u024e\13\61\3\61\3\61\3\62\3\62\3"+
		"\62\5\62\u0255\n\62\3\62\5\62\u0258\n\62\3\63\3\63\3\64\3\64\3\65\3\65"+
		"\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\6\67\u0268\n\67\r\67\16\67\u0269"+
		"\38\38\38\78\u026f\n8\f8\168\u0272\138\39\39\59\u0276\n9\3:\3:\3:\5:\u027b"+
		"\n:\3:\3:\3:\3:\3:\5:\u0282\n:\3:\7:\u0285\n:\f:\16:\u0288\13:\3;\3;\3"+
		";\3;\5;\u028e\n;\3<\3<\3=\3=\3>\3>\5>\u0296\n>\3>\3>\3>\3>\3?\6?\u029d"+
		"\n?\r?\16?\u029e\3@\3@\3@\3@\3A\3A\3A\7A\u02a8\nA\fA\16A\u02ab\13A\3B"+
		"\3B\5B\u02af\nB\3C\3C\3C\3C\3C\7C\u02b6\nC\fC\16C\u02b9\13C\3C\5C\u02bc"+
		"\nC\5C\u02be\nC\3C\5C\u02c1\nC\3D\3D\5D\u02c5\nD\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3E\5E\u02d2\nE\3F\3F\7F\u02d6\nF\fF\16F\u02d9\13F\3F\3F\3G\3"+
		"G\3H\3H\3H\3I\3I\3J\5J\u02e5\nJ\3J\3J\3J\3J\3J\3J\3J\5J\u02ee\nJ\3K\3"+
		"K\3K\3K\3K\3K\5K\u02f6\nK\3L\5L\u02f9\nL\3L\3L\3L\3L\3L\3L\7L\u0301\n"+
		"L\fL\16L\u0304\13L\3L\3L\3M\3M\3M\5M\u030b\nM\3M\3M\3N\5N\u0310\nN\3N"+
		"\3N\3N\3N\3N\3N\3O\5O\u0319\nO\3O\3O\3O\3O\3O\3O\3O\3O\3P\5P\u0324\nP"+
		"\3P\3P\3P\3P\3P\5P\u032b\nP\3P\5P\u032e\nP\3P\3P\5P\u0332\nP\3P\3P\3P"+
		"\3Q\3Q\3Q\3Q\5Q\u033b\nQ\3Q\5Q\u033e\nQ\3Q\3Q\3R\3R\3R\3R\2\4\30rS\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNP"+
		"RTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e"+
		"\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\2\21\3\2"+
		"\u010b\u010c\3\2\u010e\u010f\3\2\u0111\u0114\3\2\4\7\4\2\u00d3\u00d4\u00f2"+
		"\u00f5\3\2\u00f6\u00f8\3\2\u00f2\u00f3\3\2\u00d6\u00d7\4\2\u00d8\u00d9"+
		"\u00f9\u00fa\3\2\u00da\u00db\4\2\u00df\u00e8\u00ff\u00ff\3\2\t\13\3\2"+
		"\20\22\3\2>\u0088\6\2%%\u0089\u0095\u0097\u00d2\u00d5\u00d5\2\u0387\2"+
		"\u00a4\3\2\2\2\4\u00b4\3\2\2\2\6\u00bc\3\2\2\2\b\u00be\3\2\2\2\n\u00d0"+
		"\3\2\2\2\f\u00d9\3\2\2\2\16\u00db\3\2\2\2\20\u00df\3\2\2\2\22\u00e1\3"+
		"\2\2\2\24\u00e4\3\2\2\2\26\u00f5\3\2\2\2\30\u00fa\3\2\2\2\32\u010e\3\2"+
		"\2\2\34\u0111\3\2\2\2\36\u0114\3\2\2\2 \u0125\3\2\2\2\"\u012b\3\2\2\2"+
		"$\u012d\3\2\2\2&\u012f\3\2\2\2(\u0137\3\2\2\2*\u013f\3\2\2\2,\u0147\3"+
		"\2\2\2.\u014f\3\2\2\2\60\u0157\3\2\2\2\62\u015f\3\2\2\2\64\u0167\3\2\2"+
		"\2\66\u016f\3\2\2\28\u0177\3\2\2\2:\u017f\3\2\2\2<\u0187\3\2\2\2>\u0197"+
		"\3\2\2\2@\u0199\3\2\2\2B\u019b\3\2\2\2D\u01a3\3\2\2\2F\u01ca\3\2\2\2H"+
		"\u01cd\3\2\2\2J\u01de\3\2\2\2L\u01e0\3\2\2\2N\u01ea\3\2\2\2P\u01f1\3\2"+
		"\2\2R\u01f3\3\2\2\2T\u0203\3\2\2\2V\u020c\3\2\2\2X\u0210\3\2\2\2Z\u0219"+
		"\3\2\2\2\\\u0222\3\2\2\2^\u0243\3\2\2\2`\u0245\3\2\2\2b\u0257\3\2\2\2"+
		"d\u0259\3\2\2\2f\u025b\3\2\2\2h\u025d\3\2\2\2j\u025f\3\2\2\2l\u0267\3"+
		"\2\2\2n\u026b\3\2\2\2p\u0273\3\2\2\2r\u0277\3\2\2\2t\u028d\3\2\2\2v\u028f"+
		"\3\2\2\2x\u0291\3\2\2\2z\u0293\3\2\2\2|\u029c\3\2\2\2~\u02a0\3\2\2\2\u0080"+
		"\u02a4\3\2\2\2\u0082\u02ac\3\2\2\2\u0084\u02c0\3\2\2\2\u0086\u02c4\3\2"+
		"\2\2\u0088\u02d1\3\2\2\2\u008a\u02d3\3\2\2\2\u008c\u02dc\3\2\2\2\u008e"+
		"\u02de\3\2\2\2\u0090\u02e1\3\2\2\2\u0092\u02e4\3\2\2\2\u0094\u02f5\3\2"+
		"\2\2\u0096\u02f8\3\2\2\2\u0098\u030a\3\2\2\2\u009a\u030f\3\2\2\2\u009c"+
		"\u0318\3\2\2\2\u009e\u0323\3\2\2\2\u00a0\u033d\3\2\2\2\u00a2\u0341\3\2"+
		"\2\2\u00a4\u00a8\5\4\3\2\u00a5\u00a7\5\6\4\2\u00a6\u00a5\3\2\2\2\u00a7"+
		"\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\3\2"+
		"\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac\7\2\2\3\u00ac\3\3\2\2\2\u00ad\u00ae"+
		"\7\u0102\2\2\u00ae\u00af\7\u0109\2\2\u00af\u00b1\7\u0119\2\2\u00b0\u00b2"+
		"\7\u011a\2\2\u00b1\u00b0\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\3\2\2"+
		"\2\u00b3\u00b5\7\u011d\2\2\u00b4\u00ad\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5"+
		"\5\3\2\2\2\u00b6\u00bd\5\22\n\2\u00b7\u00bd\5F$\2\u00b8\u00bd\5\b\5\2"+
		"\u00b9\u00bd\5\n\6\2\u00ba\u00bd\5\16\b\2\u00bb\u00bd\7\u00ed\2\2\u00bc"+
		"\u00b6\3\2\2\2\u00bc\u00b7\3\2\2\2\u00bc\u00b8\3\2\2\2\u00bc\u00b9\3\2"+
		"\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd\7\3\2\2\2\u00be\u00bf"+
		"\7\u0102\2\2\u00bf\u00c1\7\u010a\2\2\u00c0\u00c2\7\u0118\2\2\u00c1\u00c0"+
		"\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00cc\3\2\2\2\u00c3\u00c4\t\2\2\2\u00c4"+
		"\u00c5\7\u0116\2\2\u00c5\u00c6\t\3\2\2\u00c6\u00cd\7\u00ea\2\2\u00c7\u00c8"+
		"\7\u010d\2\2\u00c8\u00c9\7\u0116\2\2\u00c9\u00ca\7\u0110\2\2\u00ca\u00cd"+
		"\7\u0117\2\2\u00cb\u00cd\7\u011a\2\2\u00cc\u00c3\3\2\2\2\u00cc\u00c7\3"+
		"\2\2\2\u00cc\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\7\u011d\2\2"+
		"\u00cf\t\3\2\2\2\u00d0\u00d1\7\u0102\2\2\u00d1\u00d2\7\u0108\2\2\u00d2"+
		"\u00d5\7\u011a\2\2\u00d3\u00d4\7\u0115\2\2\u00d4\u00d6\5\f\7\2\u00d5\u00d3"+
		"\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8\7\u011d\2"+
		"\2\u00d8\13\3\2\2\2\u00d9\u00da\t\4\2\2\u00da\r\3\2\2\2\u00db\u00dc\5"+
		"`\61\2\u00dc\u00dd\5\20\t\2\u00dd\u00de\7\u00ed\2\2\u00de\17\3\2\2\2\u00df"+
		"\u00e0\t\5\2\2\u00e0\21\3\2\2\2\u00e1\u00e2\5H%\2\u00e2\u00e3\5\u008a"+
		"F\2\u00e3\23\3\2\2\2\u00e4\u00e5\7\u0103\2\2\u00e5\25\3\2\2\2\u00e6\u00f6"+
		"\5\24\13\2\u00e7\u00f6\7\65\2\2\u00e8\u00f6\7\64\2\2\u00e9\u00f6\7\67"+
		"\2\2\u00ea\u00f6\7\66\2\2\u00eb\u00f6\79\2\2\u00ec\u00f6\78\2\2\u00ed"+
		"\u00f6\7:\2\2\u00ee\u00f6\7;\2\2\u00ef\u00f6\7<\2\2\u00f0\u00f6\7=\2\2"+
		"\u00f1\u00f2\7\u00e9\2\2\u00f2\u00f3\5B\"\2\u00f3\u00f4\7\u00ea\2\2\u00f4"+
		"\u00f6\3\2\2\2\u00f5\u00e6\3\2\2\2\u00f5\u00e7\3\2\2\2\u00f5\u00e8\3\2"+
		"\2\2\u00f5\u00e9\3\2\2\2\u00f5\u00ea\3\2\2\2\u00f5\u00eb\3\2\2\2\u00f5"+
		"\u00ec\3\2\2\2\u00f5\u00ed\3\2\2\2\u00f5\u00ee\3\2\2\2\u00f5\u00ef\3\2"+
		"\2\2\u00f5\u00f0\3\2\2\2\u00f5\u00f1\3\2\2\2\u00f6\27\3\2\2\2\u00f7\u00f8"+
		"\b\r\1\2\u00f8\u00fb\5\26\f\2\u00f9\u00fb\5\32\16\2\u00fa\u00f7\3\2\2"+
		"\2\u00fa\u00f9\3\2\2\2\u00fb\u010b\3\2\2\2\u00fc\u0107\f\4\2\2\u00fd\u00fe"+
		"\7\u00ee\2\2\u00fe\u00ff\5B\"\2\u00ff\u0100\7\u00ef\2\2\u0100\u0108\3"+
		"\2\2\2\u0101\u0102\7\u00f1\2\2\u0102\u0108\7\u0103\2\2\u0103\u0104\7\u00f1"+
		"\2\2\u0104\u0108\5\34\17\2\u0105\u0108\7\u00d3\2\2\u0106\u0108\7\u00d4"+
		"\2\2\u0107\u00fd\3\2\2\2\u0107\u0101\3\2\2\2\u0107\u0103\3\2\2\2\u0107"+
		"\u0105\3\2\2\2\u0107\u0106\3\2\2\2\u0108\u010a\3\2\2\2\u0109\u00fc\3\2"+
		"\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\31\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u010f\5 \21\2\u010f\u0110\5\36\20"+
		"\2\u0110\33\3\2\2\2\u0111\u0112\5\24\13\2\u0112\u0113\5\36\20\2\u0113"+
		"\35\3\2\2\2\u0114\u011f\7\u00e9\2\2\u0115\u0120\3\2\2\2\u0116\u0120\7"+
		"\u00d5\2\2\u0117\u011c\5> \2\u0118\u0119\7\u00f0\2\2\u0119\u011b\5> \2"+
		"\u011a\u0118\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a\3\2\2\2\u011c\u011d"+
		"\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011f\u0115\3\2\2\2\u011f"+
		"\u0116\3\2\2\2\u011f\u0117\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122\7\u00ea"+
		"\2\2\u0122\37\3\2\2\2\u0123\u0126\5p9\2\u0124\u0126\5\24\13\2\u0125\u0123"+
		"\3\2\2\2\u0125\u0124\3\2\2\2\u0126!\3\2\2\2\u0127\u012c\5\30\r\2\u0128"+
		"\u0129\5$\23\2\u0129\u012a\5\"\22\2\u012a\u012c\3\2\2\2\u012b\u0127\3"+
		"\2\2\2\u012b\u0128\3\2\2\2\u012c#\3\2\2\2\u012d\u012e\t\6\2\2\u012e%\3"+
		"\2\2\2\u012f\u0134\5\"\22\2\u0130\u0131\t\7\2\2\u0131\u0133\5\"\22\2\u0132"+
		"\u0130\3\2\2\2\u0133\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2"+
		"\2\2\u0135\'\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u013c\5&\24\2\u0138\u0139"+
		"\t\b\2\2\u0139\u013b\5&\24\2\u013a\u0138\3\2\2\2\u013b\u013e\3\2\2\2\u013c"+
		"\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d)\3\2\2\2\u013e\u013c\3\2\2\2"+
		"\u013f\u0144\5(\25\2\u0140\u0141\t\t\2\2\u0141\u0143\5(\25\2\u0142\u0140"+
		"\3\2\2\2\u0143\u0146\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0145\3\2\2\2\u0145"+
		"+\3\2\2\2\u0146\u0144\3\2\2\2\u0147\u014c\5*\26\2\u0148\u0149\t\n\2\2"+
		"\u0149\u014b\5*\26\2\u014a\u0148\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014a"+
		"\3\2\2\2\u014c\u014d\3\2\2\2\u014d-\3\2\2\2\u014e\u014c\3\2\2\2\u014f"+
		"\u0154\5,\27\2\u0150\u0151\t\13\2\2\u0151\u0153\5,\27\2\u0152\u0150\3"+
		"\2\2\2\u0153\u0156\3\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155"+
		"/\3\2\2\2\u0156\u0154\3\2\2\2\u0157\u015c\5.\30\2\u0158\u0159\7\u00fb"+
		"\2\2\u0159\u015b\5.\30\2\u015a\u0158\3\2\2\2\u015b\u015e\3\2\2\2\u015c"+
		"\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d\61\3\2\2\2\u015e\u015c\3\2\2"+
		"\2\u015f\u0164\5\60\31\2\u0160\u0161\7\u00fd\2\2\u0161\u0163\5\60\31\2"+
		"\u0162\u0160\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0165"+
		"\3\2\2\2\u0165\63\3\2\2\2\u0166\u0164\3\2\2\2\u0167\u016c\5\62\32\2\u0168"+
		"\u0169\7\u00fc\2\2\u0169\u016b\5\62\32\2\u016a\u0168\3\2\2\2\u016b\u016e"+
		"\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\65\3\2\2\2\u016e"+
		"\u016c\3\2\2\2\u016f\u0174\5\64\33\2\u0170\u0171\7\u00dc\2\2\u0171\u0173"+
		"\5\64\33\2\u0172\u0170\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2"+
		"\u0174\u0175\3\2\2\2\u0175\67\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u017c"+
		"\5\66\34\2\u0178\u0179\7\u00dd\2\2\u0179\u017b\5\66\34\2\u017a\u0178\3"+
		"\2\2\2\u017b\u017e\3\2\2\2\u017c\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"9\3\2\2\2\u017e\u017c\3\2\2\2\u017f\u0184\58\35\2\u0180\u0181\7\u00de"+
		"\2\2\u0181\u0183\58\35\2\u0182\u0180\3\2\2\2\u0183\u0186\3\2\2\2\u0184"+
		"\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185;\3\2\2\2\u0186\u0184\3\2\2\2"+
		"\u0187\u018f\5:\36\2\u0188\u0189\7\u00fe\2\2\u0189\u018a\5B\"\2\u018a"+
		"\u018b\7\3\2\2\u018b\u018c\5> \2\u018c\u018e\3\2\2\2\u018d\u0188\3\2\2"+
		"\2\u018e\u0191\3\2\2\2\u018f\u018d\3\2\2\2\u018f\u0190\3\2\2\2\u0190="+
		"\3\2\2\2\u0191\u018f\3\2\2\2\u0192\u0198\5<\37\2\u0193\u0194\5\"\22\2"+
		"\u0194\u0195\5@!\2\u0195\u0196\5> \2\u0196\u0198\3\2\2\2\u0197\u0192\3"+
		"\2\2\2\u0197\u0193\3\2\2\2\u0198?\3\2\2\2\u0199\u019a\t\f\2\2\u019aA\3"+
		"\2\2\2\u019b\u01a0\5> \2\u019c\u019d\7\u00f0\2\2\u019d\u019f\5> \2\u019e"+
		"\u019c\3\2\2\2\u019f\u01a2\3\2\2\2\u01a0\u019e\3\2\2\2\u01a0\u01a1\3\2"+
		"\2\2\u01a1C\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a3\u01a4\5<\37\2\u01a4E\3\2"+
		"\2\2\u01a5\u01a6\5H%\2\u01a6\u01a7\7\u00ed\2\2\u01a7\u01cb\3\2\2\2\u01a8"+
		"\u01a9\5V,\2\u01a9\u01aa\7\u00ed\2\2\u01aa\u01cb\3\2\2\2\u01ab\u01ac\7"+
		"\f\2\2\u01ac\u01ad\5d\63\2\u01ad\u01ae\5p9\2\u01ae\u01af\7\u00ed\2\2\u01af"+
		"\u01cb\3\2\2\2\u01b0\u01b1\5l\67\2\u01b1\u01b2\7\u0103\2\2\u01b2\u01b3"+
		"\7\u00eb\2\2\u01b3\u01b4\5|?\2\u01b4\u01b9\7\u00ec\2\2\u01b5\u01b7\7\u0103"+
		"\2\2\u01b6\u01b8\5r:\2\u01b7\u01b6\3\2\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01ba"+
		"\3\2\2\2\u01b9\u01b5\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb"+
		"\u01bc\7\u00ed\2\2\u01bc\u01cb\3\2\2\2\u01bd\u01c6\5l\67\2\u01be\u01c3"+
		"\7\u0103\2\2\u01bf\u01c0\7\u00f0\2\2\u01c0\u01c2\7\u0103\2\2\u01c1\u01bf"+
		"\3\2\2\2\u01c2\u01c5\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4"+
		"\u01c7\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c6\u01be\3\2\2\2\u01c6\u01c7\3\2"+
		"\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01c9\7\u00ed\2\2\u01c9\u01cb\3\2\2\2\u01ca"+
		"\u01a5\3\2\2\2\u01ca\u01a8\3\2\2\2\u01ca\u01ab\3\2\2\2\u01ca\u01b0\3\2"+
		"\2\2\u01ca\u01bd\3\2\2\2\u01cbG\3\2\2\2\u01cc\u01ce\5R*\2\u01cd\u01cc"+
		"\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0\5L\'\2\u01d0"+
		"\u01d1\7\u00e9\2\2\u01d1\u01d2\5J&\2\u01d2\u01d4\7\u00ea\2\2\u01d3\u01d5"+
		"\5R*\2\u01d4\u01d3\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5I\3\2\2\2\u01d6\u01db"+
		"\5P)\2\u01d7\u01d8\7\u00f0\2\2\u01d8\u01da\5P)\2\u01d9\u01d7\3\2\2\2\u01da"+
		"\u01dd\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\u01df\3\2"+
		"\2\2\u01dd\u01db\3\2\2\2\u01de\u01d6\3\2\2\2\u01de\u01df\3\2\2\2\u01df"+
		"K\3\2\2\2\u01e0\u01e1\5\\/\2\u01e1\u01e2\7\u0103\2\2\u01e2M\3\2\2\2\u01e3"+
		"\u01e4\5p9\2\u01e4\u01e5\7\u0103\2\2\u01e5\u01eb\3\2\2\2\u01e6\u01e7\5"+
		"p9\2\u01e7\u01e8\7\u0103\2\2\u01e8\u01e9\5r:\2\u01e9\u01eb\3\2\2\2\u01ea"+
		"\u01e3\3\2\2\2\u01ea\u01e6\3\2\2\2\u01ebO\3\2\2\2\u01ec\u01ee\5l\67\2"+
		"\u01ed\u01ec\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f2"+
		"\5N(\2\u01f0\u01f2\5\\/\2\u01f1\u01ed\3\2\2\2\u01f1\u01f0\3\2\2\2\u01f2"+
		"Q\3\2\2\2\u01f3\u01f4\7\u00ee\2\2\u01f4\u01f5\7\u00ee\2\2\u01f5\u01fa"+
		"\5T+\2\u01f6\u01f7\7\u00f0\2\2\u01f7\u01f9\5T+\2\u01f8\u01f6\3\2\2\2\u01f9"+
		"\u01fc\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fd\3\2"+
		"\2\2\u01fc\u01fa\3\2\2\2\u01fd\u01fe\7\u00ef\2\2\u01fe\u01ff\7\u00ef\2"+
		"\2\u01ffS\3\2\2\2\u0200\u0201\7\u0103\2\2\u0201\u0202\7\3\2\2\u0202\u0204"+
		"\7\3\2\2\u0203\u0200\3\2\2\2\u0203\u0204\3\2\2\2\u0204\u0205\3\2\2\2\u0205"+
		"\u020a\7\u0103\2\2\u0206\u0207\7\u00e9\2\2\u0207\u0208\5D#\2\u0208\u0209"+
		"\7\u00ea\2\2\u0209\u020b\3\2\2\2\u020a\u0206\3\2\2\2\u020a\u020b\3\2\2"+
		"\2\u020bU\3\2\2\2\u020c\u020d\5\\/\2\u020d\u020e\5X-\2\u020eW\3\2\2\2"+
		"\u020f\u0211\5Z.\2\u0210\u020f\3\2\2\2\u0210\u0211\3\2\2\2\u0211\u0216"+
		"\3\2\2\2\u0212\u0213\7\u00f0\2\2\u0213\u0215\5Z.\2\u0214\u0212\3\2\2\2"+
		"\u0215\u0218\3\2\2\2\u0216\u0214\3\2\2\2\u0216\u0217\3\2\2\2\u0217Y\3"+
		"\2\2\2\u0218\u0216\3\2\2\2\u0219\u021b\7\u0103\2\2\u021a\u021c\5r:\2\u021b"+
		"\u021a\3\2\2\2\u021b\u021c\3\2\2\2\u021c\u021f\3\2\2\2\u021d\u021e\7\u00ff"+
		"\2\2\u021e\u0220\5\u0084C\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220"+
		"[\3\2\2\2\u0221\u0223\5l\67\2\u0222\u0221\3\2\2\2\u0222\u0223\3\2\2\2"+
		"\u0223\u0224\3\2\2\2\u0224\u0225\5p9\2\u0225]\3\2\2\2\u0226\u0244\7\r"+
		"\2\2\u0227\u0244\7\6\2\2\u0228\u0244\7\7\2\2\u0229\u0244\7\b\2\2\u022a"+
		"\u0244\7\23\2\2\u022b\u0244\7\25\2\2\u022c\u0244\7\24\2\2\u022d\u0244"+
		"\7\4\2\2\u022e\u0244\7\32\2\2\u022f\u0244\7\26\2\2\u0230\u0244\7\5\2\2"+
		"\u0231\u0244\7\35\2\2\u0232\u0244\7\27\2\2\u0233\u0244\7\30\2\2\u0234"+
		"\u0244\7\31\2\2\u0235\u0244\7\33\2\2\u0236\u0244\7\34\2\2\u0237\u023c"+
		"\7\36\2\2\u0238\u0239\7\u00e9\2\2\u0239\u023a\5n8\2\u023a\u023b\7\u00ea"+
		"\2\2\u023b\u023d\3\2\2\2\u023c\u0238\3\2\2\2\u023c\u023d\3\2\2\2\u023d"+
		"\u0244\3\2\2\2\u023e\u0244\7\37\2\2\u023f\u0244\7 \2\2\u0240\u0244\7!"+
		"\2\2\u0241\u0244\7\"\2\2\u0242\u0244\7#\2\2\u0243\u0226\3\2\2\2\u0243"+
		"\u0227\3\2\2\2\u0243\u0228\3\2\2\2\u0243\u0229\3\2\2\2\u0243\u022a\3\2"+
		"\2\2\u0243\u022b\3\2\2\2\u0243\u022c\3\2\2\2\u0243\u022d\3\2\2\2\u0243"+
		"\u022e\3\2\2\2\u0243\u022f\3\2\2\2\u0243\u0230\3\2\2\2\u0243\u0231\3\2"+
		"\2\2\u0243\u0232\3\2\2\2\u0243\u0233\3\2\2\2\u0243\u0234\3\2\2\2\u0243"+
		"\u0235\3\2\2\2\u0243\u0236\3\2\2\2\u0243\u0237\3\2\2\2\u0243\u023e\3\2"+
		"\2\2\u0243\u023f\3\2\2\2\u0243\u0240\3\2\2\2\u0243\u0241\3\2\2\2\u0243"+
		"\u0242\3\2\2\2\u0244_\3\2\2\2\u0245\u0246\7$\2\2\u0246\u0247\7\u00e9\2"+
		"\2\u0247\u024c\5b\62\2\u0248\u0249\7\u00f0\2\2\u0249\u024b\5b\62\2\u024a"+
		"\u0248\3\2\2\2\u024b\u024e\3\2\2\2\u024c\u024a\3\2\2\2\u024c\u024d\3\2"+
		"\2\2\u024d\u024f\3\2\2\2\u024e\u024c\3\2\2\2\u024f\u0250\7\u00ea\2\2\u0250"+
		"a\3\2\2\2\u0251\u0254\7\u0103\2\2\u0252\u0253\7\u00ff\2\2\u0253\u0255"+
		"\5D#\2\u0254\u0252\3\2\2\2\u0254\u0255\3\2\2\2\u0255\u0258\3\2\2\2\u0256"+
		"\u0258\7\35\2\2\u0257\u0251\3\2\2\2\u0257\u0256\3\2\2\2\u0258c\3\2\2\2"+
		"\u0259\u025a\t\r\2\2\u025ae\3\2\2\2\u025b\u025c\t\16\2\2\u025cg\3\2\2"+
		"\2\u025d\u025e\7\17\2\2\u025ei\3\2\2\2\u025f\u0260\7\16\2\2\u0260k\3\2"+
		"\2\2\u0261\u0268\5^\60\2\u0262\u0268\5`\61\2\u0263\u0268\5d\63\2\u0264"+
		"\u0268\5f\64\2\u0265\u0268\5h\65\2\u0266\u0268\5j\66\2\u0267\u0261\3\2"+
		"\2\2\u0267\u0262\3\2\2\2\u0267\u0263\3\2\2\2\u0267\u0264\3\2\2\2\u0267"+
		"\u0265\3\2\2\2\u0267\u0266\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u0267\3\2"+
		"\2\2\u0269\u026a\3\2\2\2\u026am\3\2\2\2\u026b\u0270\7\u0103\2\2\u026c"+
		"\u026d\7\u00f0\2\2\u026d\u026f\7\u0103\2\2\u026e\u026c\3\2\2\2\u026f\u0272"+
		"\3\2\2\2\u0270\u026e\3\2\2\2\u0270\u0271\3\2\2\2\u0271o\3\2\2\2\u0272"+
		"\u0270\3\2\2\2\u0273\u0275\5t;\2\u0274\u0276\5r:\2\u0275\u0274\3\2\2\2"+
		"\u0275\u0276\3\2\2\2\u0276q\3\2\2\2\u0277\u0278\b:\1\2\u0278\u027a\7\u00ee"+
		"\2\2\u0279\u027b\5D#\2\u027a\u0279\3\2\2\2\u027a\u027b\3\2\2\2\u027b\u027c"+
		"\3\2\2\2\u027c\u027d\7\u00ef\2\2\u027d\u0286\3\2\2\2\u027e\u027f\f\4\2"+
		"\2\u027f\u0281\7\u00ee\2\2\u0280\u0282\5D#\2\u0281\u0280\3\2\2\2\u0281"+
		"\u0282\3\2\2\2\u0282\u0283\3\2\2\2\u0283\u0285\7\u00ef\2\2\u0284\u027e"+
		"\3\2\2\2\u0285\u0288\3\2\2\2\u0286\u0284\3\2\2\2\u0286\u0287\3\2\2\2\u0287"+
		"s\3\2\2\2\u0288\u0286\3\2\2\2\u0289\u028e\5x=\2\u028a\u028e\5v<\2\u028b"+
		"\u028e\5z>\2\u028c\u028e\7\u0103\2\2\u028d\u0289\3\2\2\2\u028d\u028a\3"+
		"\2\2\2\u028d\u028b\3\2\2\2\u028d\u028c\3\2\2\2\u028eu\3\2\2\2\u028f\u0290"+
		"\t\17\2\2\u0290w\3\2\2\2\u0291\u0292\t\20\2\2\u0292y\3\2\2\2\u0293\u0295"+
		"\7&\2\2\u0294\u0296\7\u0103\2\2\u0295\u0294\3\2\2\2\u0295\u0296\3\2\2"+
		"\2\u0296\u0297\3\2\2\2\u0297\u0298\7\u00eb\2\2\u0298\u0299\5|?\2\u0299"+
		"\u029a\7\u00ec\2\2\u029a{\3\2\2\2\u029b\u029d\5~@\2\u029c\u029b\3\2\2"+
		"\2\u029d\u029e\3\2\2\2\u029e\u029c\3\2\2\2\u029e\u029f\3\2\2\2\u029f}"+
		"\3\2\2\2\u02a0\u02a1\5\\/\2\u02a1\u02a2\5\u0080A\2\u02a2\u02a3\7\u00ed"+
		"\2\2\u02a3\177\3\2\2\2\u02a4\u02a9\5\u0082B\2\u02a5\u02a6\7\u00f0\2\2"+
		"\u02a6\u02a8\5\u0082B\2\u02a7\u02a5\3\2\2\2\u02a8\u02ab\3\2\2\2\u02a9"+
		"\u02a7\3\2\2\2\u02a9\u02aa\3\2\2\2\u02aa\u0081\3\2\2\2\u02ab\u02a9\3\2"+
		"\2\2\u02ac\u02ae\7\u0103\2\2\u02ad\u02af\5r:\2\u02ae\u02ad\3\2\2\2\u02ae"+
		"\u02af\3\2\2\2\u02af\u0083\3\2\2\2\u02b0\u02c1\5> \2\u02b1\u02bd\7\u00eb"+
		"\2\2\u02b2\u02b7\5\u0084C\2\u02b3\u02b4\7\u00f0\2\2\u02b4\u02b6\5\u0084"+
		"C\2\u02b5\u02b3\3\2\2\2\u02b6\u02b9\3\2\2\2\u02b7\u02b5\3\2\2\2\u02b7"+
		"\u02b8\3\2\2\2\u02b8\u02bb\3\2\2\2\u02b9\u02b7\3\2\2\2\u02ba\u02bc\7\u00f0"+
		"\2\2\u02bb\u02ba\3\2\2\2\u02bb\u02bc\3\2\2\2\u02bc\u02be\3\2\2\2\u02bd"+
		"\u02b2\3\2\2\2\u02bd\u02be\3\2\2\2\u02be\u02bf\3\2\2\2\u02bf\u02c1\7\u00ec"+
		"\2\2\u02c0\u02b0\3\2\2\2\u02c0\u02b1\3\2\2\2\u02c1\u0085\3\2\2\2\u02c2"+
		"\u02c5\5\u008aF\2\u02c3\u02c5\5\u0088E\2\u02c4\u02c2\3\2\2\2\u02c4\u02c3"+
		"\3\2\2\2\u02c5\u0087\3\2\2\2\u02c6\u02d2\5\u008cG\2\u02c7\u02d2\5\u008e"+
		"H\2\u02c8\u02d2\5\u0090I\2\u02c9\u02d2\5\u0092J\2\u02ca\u02d2\5\u0096"+
		"L\2\u02cb\u02d2\5\u0098M\2\u02cc\u02d2\5\u009eP\2\u02cd\u02d2\5\u009a"+
		"N\2\u02ce\u02d2\5\u009cO\2\u02cf\u02d2\5\u00a0Q\2\u02d0\u02d2\5\u00a2"+
		"R\2\u02d1\u02c6\3\2\2\2\u02d1\u02c7\3\2\2\2\u02d1\u02c8\3\2\2\2\u02d1"+
		"\u02c9\3\2\2\2\u02d1\u02ca\3\2\2\2\u02d1\u02cb\3\2\2\2\u02d1\u02cc\3\2"+
		"\2\2\u02d1\u02cd\3\2\2\2\u02d1\u02ce\3\2\2\2\u02d1\u02cf\3\2\2\2\u02d1"+
		"\u02d0\3\2\2\2\u02d2\u0089\3\2\2\2\u02d3\u02d7\7\u00eb\2\2\u02d4\u02d6"+
		"\5\u0086D\2\u02d5\u02d4\3\2\2\2\u02d6\u02d9\3\2\2\2\u02d7\u02d5\3\2\2"+
		"\2\u02d7\u02d8\3\2\2\2\u02d8\u02da\3\2\2\2\u02d9\u02d7\3\2\2\2\u02da\u02db"+
		"\7\u00ec\2\2\u02db\u008b\3\2\2\2\u02dc\u02dd\5F$\2\u02dd\u008d\3\2\2\2"+
		"\u02de\u02df\5B\"\2\u02df\u02e0\7\u00ed\2\2\u02e0\u008f\3\2\2\2\u02e1"+
		"\u02e2\7\u00ed\2\2\u02e2\u0091\3\2\2\2\u02e3\u02e5\5R*\2\u02e4\u02e3\3"+
		"\2\2\2\u02e4\u02e5\3\2\2\2\u02e5\u02e6\3\2\2\2\u02e6\u02e7\7\'\2\2\u02e7"+
		"\u02e8\7\u00e9\2\2\u02e8\u02e9\5B\"\2\u02e9\u02ea\7\u00ea\2\2\u02ea\u02ed"+
		"\5\u0086D\2\u02eb\u02ec\7(\2\2\u02ec\u02ee\5\u0086D\2\u02ed\u02eb\3\2"+
		"\2\2\u02ed\u02ee\3\2\2\2\u02ee\u0093\3\2\2\2\u02ef\u02f6\5B\"\2\u02f0"+
		"\u02f1\5\\/\2\u02f1\u02f2\7\u0103\2\2\u02f2\u02f3\7\u00ff\2\2\u02f3\u02f4"+
		"\5\u0084C\2\u02f4\u02f6\3\2\2\2\u02f5\u02ef\3\2\2\2\u02f5\u02f0\3\2\2"+
		"\2\u02f6\u0095\3\2\2\2\u02f7\u02f9\5R*\2\u02f8\u02f7\3\2\2\2\u02f8\u02f9"+
		"\3\2\2\2\u02f9\u02fa\3\2\2\2\u02fa\u02fb\7)\2\2\u02fb\u02fc\7\u00e9\2"+
		"\2\u02fc\u02fd\5B\"\2\u02fd\u02fe\7\u00ea\2\2\u02fe\u0302\7\u00eb\2\2"+
		"\u02ff\u0301\5\u0086D\2\u0300\u02ff\3\2\2\2\u0301\u0304\3\2\2\2\u0302"+
		"\u0300\3\2\2\2\u0302\u0303\3\2\2\2\u0303\u0305\3\2\2\2\u0304\u0302\3\2"+
		"\2\2\u0305\u0306\7\u00ec\2\2\u0306\u0097\3\2\2\2\u0307\u0308\7*\2\2\u0308"+
		"\u030b\5B\"\2\u0309\u030b\7+\2\2\u030a\u0307\3\2\2\2\u030a\u0309\3\2\2"+
		"\2\u030b\u030c\3\2\2\2\u030c\u030d\7\3\2\2\u030d\u0099\3\2\2\2\u030e\u0310"+
		"\5R*\2\u030f\u030e\3\2\2\2\u030f\u0310\3\2\2\2\u0310\u0311\3\2\2\2\u0311"+
		"\u0312\7,\2\2\u0312\u0313\7\u00e9\2\2\u0313\u0314\5\u0094K\2\u0314\u0315"+
		"\7\u00ea\2\2\u0315\u0316\5\u0086D\2\u0316\u009b\3\2\2\2\u0317\u0319\5"+
		"R*\2\u0318\u0317\3\2\2\2\u0318\u0319\3\2\2\2\u0319\u031a\3\2\2\2\u031a"+
		"\u031b\7-\2\2\u031b\u031c\5\u0086D\2\u031c\u031d\7,\2\2\u031d\u031e\7"+
		"\u00e9\2\2\u031e\u031f\5B\"\2\u031f\u0320\7\u00ea\2\2\u0320\u0321\7\u00ed"+
		"\2\2\u0321\u009d\3\2\2\2\u0322\u0324\5R*\2\u0323\u0322\3\2\2\2\u0323\u0324"+
		"\3\2\2\2\u0324\u0325\3\2\2\2\u0325\u0326\7.\2\2\u0326\u032a\7\u00e9\2"+
		"\2\u0327\u032b\5\u0090I\2\u0328\u032b\5\u008eH\2\u0329\u032b\5\u008cG"+
		"\2\u032a\u0327\3\2\2\2\u032a\u0328\3\2\2\2\u032a\u0329\3\2\2\2\u032b\u032d"+
		"\3\2\2\2\u032c\u032e\5\u0094K\2\u032d\u032c\3\2\2\2\u032d\u032e\3\2\2"+
		"\2\u032e\u032f\3\2\2\2\u032f\u0331\7\u00ed\2\2\u0330\u0332\5B\"\2\u0331"+
		"\u0330\3\2\2\2\u0331\u0332\3\2\2\2\u0332\u0333\3\2\2\2\u0333\u0334\7\u00ea"+
		"\2\2\u0334\u0335\5\u0086D\2\u0335\u009f\3\2\2\2\u0336\u033e\7/\2\2\u0337"+
		"\u033e\7\60\2\2\u0338\u033a\7\61\2\2\u0339\u033b\5B\"\2\u033a\u0339\3"+
		"\2\2\2\u033a\u033b\3\2\2\2\u033b\u033e\3\2\2\2\u033c\u033e\7\62\2\2\u033d"+
		"\u0336\3\2\2\2\u033d\u0337\3\2\2\2\u033d\u0338\3\2\2\2\u033d\u033c\3\2"+
		"\2\2\u033e\u033f\3\2\2\2\u033f\u0340\7\u00ed\2\2\u0340\u00a1\3\2\2\2\u0341"+
		"\u0342\7\63\2\2\u0342\u0343\7\u00ed\2\2\u0343\u00a3\3\2\2\2Y\u00a8\u00b1"+
		"\u00b4\u00bc\u00c1\u00cc\u00d5\u00f5\u00fa\u0107\u010b\u011c\u011f\u0125"+
		"\u012b\u0134\u013c\u0144\u014c\u0154\u015c\u0164\u016c\u0174\u017c\u0184"+
		"\u018f\u0197\u01a0\u01b7\u01b9\u01c3\u01c6\u01ca\u01cd\u01d4\u01db\u01de"+
		"\u01ea\u01ed\u01f1\u01fa\u0203\u020a\u0210\u0216\u021b\u021f\u0222\u023c"+
		"\u0243\u024c\u0254\u0257\u0267\u0269\u0270\u0275\u027a\u0281\u0286\u028d"+
		"\u0295\u029e\u02a9\u02ae\u02b7\u02bb\u02bd\u02c0\u02c4\u02d1\u02d7\u02e4"+
		"\u02ed\u02f5\u02f8\u0302\u030a\u030f\u0318\u0323\u032a\u032d\u0331\u033a"+
		"\u033d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}