// Generated from /Users/christopher/Documents/dev/antlr-experiments/src/main/antlr/GLSLOther.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GLSLOtherParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRAGMA_DEBUG_ON=1, PRAGMA_DEBUG_OFF=2, PRAGMA_OPTIMIZE_ON=3, PRAGMA_OPTIMIZE_OFF=4, 
		PRAGMA_INVARIANT_ALL=5, EXTENSION=6, COLON=7, UNIFORM=8, BUFFER=9, IN_TOK=10, 
		OUT_TOK=11, INOUT_TOK=12, HIGHP=13, MEDIUMP=14, LOWP=15, PRECISION=16, 
		VERSION=17, INTCONSTANT=18, CONST_TOK=19, PRECISE=20, INVARIANT=21, SMOOTH=22, 
		FLAT=23, NOPERSPECTIVE=24, CENTROID=25, SAMPLE=26, ATTRIBUTE=27, COHERENT=28, 
		VOLATILE=29, RESTRICT=30, VARYING=31, READONLY=32, WRITEONLY=33, SHARED=34, 
		LAYOUT_TOK=35, UINTCONSTANT=36, ROW_MAJOR=37, PACKED_TOK=38, FLOATCONSTANT=39, 
		BOOLCONSTANT=40, INC_OP=41, DEC_OP=42, VOID_TOK=43, LEFT_OP=44, RIGHT_OP=45, 
		LE_OP=46, GE_OP=47, EQ_OP=48, NE_OP=49, AND_OP=50, XOR_OP=51, OR_OP=52, 
		MUL_ASSIGN=53, DIV_ASSIGN=54, MOD_ASSIGN=55, ADD_ASSIGN=56, SUB_ASSIGN=57, 
		LEFT_ASSIGN=58, RIGHT_ASSIGN=59, AND_ASSIGN=60, XOR_ASSIGN=61, OR_ASSIGN=62, 
		FLOAT_TOK=63, INT_TOK=64, UINT_TOK=65, BOOL_TOK=66, SAMPLERCUBE=67, SAMPLERCUBESHADOW=68, 
		SAMPLERBUFFER=69, SAMPLERCUBEARRAY=70, SAMPLERCUBEARRAYSHADOW=71, ISAMPLERCUBE=72, 
		ISAMPLERBUFFER=73, ISAMPLERCUBEARRAY=74, USAMPLERCUBE=75, USAMPLERBUFFER=76, 
		USAMPLERCUBEARRAY=77, IMAGECUBE=78, IMAGEBUFFER=79, IMAGECUBEARRAY=80, 
		IIMAGECUBE=81, IIMAGEBUFFER=82, IIMAGECUBEARRAY=83, UIMAGECUBE=84, UIMAGEBUFFER=85, 
		UIMAGECUBEARRAY=86, ATOMIC_UINT=87, STRUCT=88, IF=89, ELSE=90, SWITCH=91, 
		CASE=92, DEFAULT=93, WHILE=94, DO=95, FOR=96, CONTINUE=97, BREAK=98, RETURN=99, 
		DISCARD=100, VEC2=101, VEC3=102, VEC4=103, BVEC2=104, BVEC3=105, BVEC4=106, 
		IVEC2=107, IVEC3=108, IVEC4=109, UVEC2=110, UVEC3=111, UVEC4=112, MAT2X2=113, 
		MAT2X3=114, MAT2X4=115, MAT3X2=116, MAT3X3=117, MAT3X4=118, MAT4X2=119, 
		MAT4X3=120, MAT4X4=121, IMAGE1D=122, IMAGE2D=123, IMAGE3D=124, UIMAGE1D=125, 
		UIMAGE2D=126, UIMAGE3D=127, IIMAGE1D=128, IIMAGE2D=129, IIMAGE3D=130, 
		SAMPLER1D=131, SAMPLER2D=132, SAMPLER3D=133, SAMPLER2DRECT=134, SAMPLEREXTERNALOES=135, 
		SAMPLER1DSHADOW=136, SAMPLER2DSHADOW=137, SAMPLER2DRECTSHADOW=138, SAMPLER1DARRAY=139, 
		SAMPLER2DARRAY=140, SAMPLER1DARRAYSHADOW=141, SAMPLER2DARRAYSHADOW=142, 
		ISAMPLER1D=143, ISAMPLER2D=144, ISAMPLER2DRECT=145, ISAMPLER3D=146, ISAMPLER1DARRAY=147, 
		ISAMPLER2DARRAY=148, USAMPLER1D=149, USAMPLER2D=150, USAMPLER2DRECT=151, 
		USAMPLER3D=152, USAMPLER1DARRAY=153, USAMPLER2DARRAY=154, SAMPLER2DMS=155, 
		ISAMPLER2DMS=156, USAMPLER2DMS=157, SAMPLER2DMSARRAY=158, ISAMPLER2DMSARRAY=159, 
		USAMPLER2DMSARRAY=160, IMAGE2DRECT=161, IMAGE1DARRAY=162, IMAGE2DARRAY=163, 
		IMAGE2DMS=164, IMAGE2DMSARRAY=165, IIMAGE2DRECT=166, IIMAGE1DARRAY=167, 
		IIMAGE2DARRAY=168, IIMAGE2DMS=169, IIMAGE2DMSARRAY=170, UIMAGE2DRECT=171, 
		UIMAGE1DARRAY=172, UIMAGE2DARRAY=173, UIMAGE2DMS=174, UIMAGE2DMSARRAY=175, 
		LPAREN=176, RPAREN=177, LBRACE=178, RBRACE=179, SEMICOLON=180, LBRACKET=181, 
		RBRACKET=182, COMMA=183, DOT=184, PLUS_OP=185, MINUS_OP=186, NOT_OP=187, 
		BNEG_OP=188, TIMES_OP=189, DIV_OP=190, MOD_OP=191, LT_OP=192, GT_OP=193, 
		BAND_OP=194, BOR_OP=195, BXOR_OP=196, QUERY_OP=197, ASSIGN_OP=198, IDENTIFIER=199, 
		COMMENT=200, WS=201, EOL=202;
	public static final int
		RULE_translation_unit = 0, RULE_version_statement = 1, RULE_pragma_statement = 2, 
		RULE_extension_statement_list = 3, RULE_extension_statement = 4, RULE_external_declaration_list = 5, 
		RULE_variable_identifier = 6, RULE_primary_expression = 7, RULE_postfix_expression = 8, 
		RULE_integer_expression = 9, RULE_function_call_generic = 10, RULE_function_call_header_no_parameters = 11, 
		RULE_function_call_header_with_parameters = 12, RULE_function_call_header = 13, 
		RULE_function_identifier = 14, RULE_method_call_generic = 15, RULE_method_call_header_no_parameters = 16, 
		RULE_method_call_header_with_parameters = 17, RULE_method_call_header = 18, 
		RULE_unary_expression = 19, RULE_unary_operator = 20, RULE_multiplicative_expression = 21, 
		RULE_additive_expression = 22, RULE_shift_expression = 23, RULE_relational_expression = 24, 
		RULE_equality_expression = 25, RULE_and_expression = 26, RULE_exclusive_or_expression = 27, 
		RULE_inclusive_or_expression = 28, RULE_logical_and_expression = 29, RULE_logical_xor_expression = 30, 
		RULE_logical_or_expression = 31, RULE_conditional_expression = 32, RULE_assignment_expression = 33, 
		RULE_assignment_operator = 34, RULE_expression = 35, RULE_constant_expression = 36, 
		RULE_declaration = 37, RULE_function_prototype = 38, RULE_function_declarator = 39, 
		RULE_function_header_with_parameters = 40, RULE_function_header = 41, 
		RULE_parameter_declarator = 42, RULE_parameter_declaration = 43, RULE_parameter_qualifier = 44, 
		RULE_parameter_direction_qualifier = 45, RULE_parameter_type_specifier = 46, 
		RULE_init_declarator_list = 47, RULE_single_declaration = 48, RULE_fully_specified_type = 49, 
		RULE_layout_qualifier = 50, RULE_layout_qualifier_id_list = 51, RULE_integer_constant = 52, 
		RULE_layout_qualifier_id = 53, RULE_interface_block_layout_qualifier = 54, 
		RULE_interpolation_qualifier = 55, RULE_type_qualifier = 56, RULE_auxiliary_storage_qualifier = 57, 
		RULE_storage_qualifier = 58, RULE_array_specifier = 59, RULE_type_specifier = 60, 
		RULE_type_specifier_nonarray = 61, RULE_builtin_type_specifier_nonarray = 62, 
		RULE_precision_qualifier = 63, RULE_struct_specifier = 64, RULE_member_list = 65, 
		RULE_member_declaration = 66, RULE_struct_declarator_list = 67, RULE_struct_declarator = 68, 
		RULE_initializer = 69, RULE_initializer_list = 70, RULE_declaration_statement = 71, 
		RULE_statement = 72, RULE_statement_no_short_if = 73, RULE_statement_no_trailing_substatement = 74, 
		RULE_block_statement = 75, RULE_statement_list = 76, RULE_expression_statement = 77, 
		RULE_empty_statement = 78, RULE_if_then_statement = 79, RULE_if_then_else_statement = 80, 
		RULE_if_then_else_statement_no_short_if = 81, RULE_condition = 82, RULE_switch_statement = 83, 
		RULE_switch_body = 84, RULE_case_label = 85, RULE_case_label_list = 86, 
		RULE_case_statement = 87, RULE_case_statement_list = 88, RULE_do_statement = 89, 
		RULE_for_statement = 90, RULE_for_statement_no_short_if = 91, RULE_while_statement = 92, 
		RULE_while_statement_no_short_if = 93, RULE_for_init_statement = 94, RULE_jump_statement = 95, 
		RULE_external_declaration = 96, RULE_function_definition = 97, RULE_interface_block = 98, 
		RULE_basic_interface_block = 99, RULE_interface_qualifier = 100, RULE_instance_name = 101, 
		RULE_layout_defaults = 102;
	private static String[] makeRuleNames() {
		return new String[] {
			"translation_unit", "version_statement", "pragma_statement", "extension_statement_list", 
			"extension_statement", "external_declaration_list", "variable_identifier", 
			"primary_expression", "postfix_expression", "integer_expression", "function_call_generic", 
			"function_call_header_no_parameters", "function_call_header_with_parameters", 
			"function_call_header", "function_identifier", "method_call_generic", 
			"method_call_header_no_parameters", "method_call_header_with_parameters", 
			"method_call_header", "unary_expression", "unary_operator", "multiplicative_expression", 
			"additive_expression", "shift_expression", "relational_expression", "equality_expression", 
			"and_expression", "exclusive_or_expression", "inclusive_or_expression", 
			"logical_and_expression", "logical_xor_expression", "logical_or_expression", 
			"conditional_expression", "assignment_expression", "assignment_operator", 
			"expression", "constant_expression", "declaration", "function_prototype", 
			"function_declarator", "function_header_with_parameters", "function_header", 
			"parameter_declarator", "parameter_declaration", "parameter_qualifier", 
			"parameter_direction_qualifier", "parameter_type_specifier", "init_declarator_list", 
			"single_declaration", "fully_specified_type", "layout_qualifier", "layout_qualifier_id_list", 
			"integer_constant", "layout_qualifier_id", "interface_block_layout_qualifier", 
			"interpolation_qualifier", "type_qualifier", "auxiliary_storage_qualifier", 
			"storage_qualifier", "array_specifier", "type_specifier", "type_specifier_nonarray", 
			"builtin_type_specifier_nonarray", "precision_qualifier", "struct_specifier", 
			"member_list", "member_declaration", "struct_declarator_list", "struct_declarator", 
			"initializer", "initializer_list", "declaration_statement", "statement", 
			"statement_no_short_if", "statement_no_trailing_substatement", "block_statement", 
			"statement_list", "expression_statement", "empty_statement", "if_then_statement", 
			"if_then_else_statement", "if_then_else_statement_no_short_if", "condition", 
			"switch_statement", "switch_body", "case_label", "case_label_list", "case_statement", 
			"case_statement_list", "do_statement", "for_statement", "for_statement_no_short_if", 
			"while_statement", "while_statement_no_short_if", "for_init_statement", 
			"jump_statement", "external_declaration", "function_definition", "interface_block", 
			"basic_interface_block", "interface_qualifier", "instance_name", "layout_defaults"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "':'", "'uniform'", "'buffer'", 
			"'in'", "'out'", "'inout'", "'highp'", "'mediump'", "'lowp'", "'precision'", 
			null, null, "'const'", "'precise'", "'invariant'", "'smooth'", "'flat'", 
			"'noperspective'", "'centroid'", "'sample'", "'attribute'", "'coherent'", 
			"'volatile'", "'restrict'", "'varying'", "'readonly'", "'writeonly'", 
			"'shared'", "'layout'", null, "'row_major'", "'packed'", null, null, 
			"'++'", "'--'", "'void'", "'<<'", "'>>'", "'<='", "'>='", "'=='", "'!='", 
			"'&&'", "'^^'", "'||'", "'*='", "'/='", "'%='", "'+='", "'-='", "'<<='", 
			"'>>='", "'&='", "'^='", "'|='", "'float'", "'int'", "'uint'", "'bool'", 
			"'samplerCube'", "'samplerCubeShadow'", "'samplerBuffer'", "'samplerCubeArray'", 
			"'samplerCubeArrayShadow'", "'isamplerCube'", "'isamplerBuffer'", "'isamplerCubeArray'", 
			"'usamplerCube'", "'usamplerBuffer'", "'usamplerCubeArray'", "'imageCube'", 
			"'imageBuffer'", "'imageCubeArray'", "'iimageCube'", "'iimageBuffer'", 
			"'iimageCubeArray'", "'uimageCube'", "'uimageBuffer'", "'uimageCubeArray'", 
			"'atomic_uint'", "'struct'", "'if'", "'else'", "'switch'", "'case'", 
			"'default'", "'while'", "'do'", "'for'", "'continue'", "'break'", "'return'", 
			"'discard'", "'vec2'", "'vec3'", "'vec4'", "'bvec2'", "'bvec3'", "'bvec4'", 
			"'ivec2'", "'ivec3'", "'ivec4'", "'uvec2'", "'uvec3'", "'uvec4'", null, 
			"'mat2x3'", "'mat2x4'", "'mat3x2'", null, "'mat3x4'", "'mat4x2'", "'mat4x3'", 
			null, "'image1D'", "'image2D'", "'image3D'", "'uimage1D'", "'uimage2D'", 
			"'uimage3D'", "'iimage1D'", "'iimage2D'", "'iimage3D'", "'sampler1D'", 
			"'sampler2D'", "'sampler3D'", "'sampler2DRect'", "'samplerExternalOES'", 
			"'sampler1DShadow'", "'sampler2DShadow'", "'sampler2DRectShadow'", "'sampler1DArray'", 
			"'sampler2DArray'", "'sampler1DArrayShadow'", "'sampler2DArrayShadow'", 
			"'isampler1D'", "'isampler2D'", "'isampler2DRect'", "'isampler3D'", "'isampler1DArray'", 
			"'isampler2DArray'", "'usampler1D'", "'usampler2D'", "'usampler2DRect'", 
			"'usampler3D'", "'usampler1DArray'", "'usampler2DArray'", "'sampler2DMS'", 
			"'isampler2DMS'", "'usampler2DMS'", "'sampler2DMSArray'", "'isampler2DMSArray'", 
			"'usampler2DMSArray'", "'image2DRect'", "'image1DArray'", "'image2DArray'", 
			"'image2DMS'", "'image2DMSArray'", "'iimage2DRect'", "'iimage1DArray'", 
			"'iimage2DArray'", "'iimage2DMS'", "'iimage2DMSArray'", "'uimage2DRect'", 
			"'uimage1DArray'", "'uimage2DArray'", "'uimage2DMS'", "'uimage2DMSArray'", 
			"'('", "')'", "'{'", "'}'", "';'", "'['", "']'", "','", "'.'", "'+'", 
			"'-'", "'!'", "'~'", "'*'", "'/'", "'%'", "'<'", "'>'", "'&'", "'|'", 
			"'^'", "'?'", "'='", null, null, null, "'\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PRAGMA_DEBUG_ON", "PRAGMA_DEBUG_OFF", "PRAGMA_OPTIMIZE_ON", "PRAGMA_OPTIMIZE_OFF", 
			"PRAGMA_INVARIANT_ALL", "EXTENSION", "COLON", "UNIFORM", "BUFFER", "IN_TOK", 
			"OUT_TOK", "INOUT_TOK", "HIGHP", "MEDIUMP", "LOWP", "PRECISION", "VERSION", 
			"INTCONSTANT", "CONST_TOK", "PRECISE", "INVARIANT", "SMOOTH", "FLAT", 
			"NOPERSPECTIVE", "CENTROID", "SAMPLE", "ATTRIBUTE", "COHERENT", "VOLATILE", 
			"RESTRICT", "VARYING", "READONLY", "WRITEONLY", "SHARED", "LAYOUT_TOK", 
			"UINTCONSTANT", "ROW_MAJOR", "PACKED_TOK", "FLOATCONSTANT", "BOOLCONSTANT", 
			"INC_OP", "DEC_OP", "VOID_TOK", "LEFT_OP", "RIGHT_OP", "LE_OP", "GE_OP", 
			"EQ_OP", "NE_OP", "AND_OP", "XOR_OP", "OR_OP", "MUL_ASSIGN", "DIV_ASSIGN", 
			"MOD_ASSIGN", "ADD_ASSIGN", "SUB_ASSIGN", "LEFT_ASSIGN", "RIGHT_ASSIGN", 
			"AND_ASSIGN", "XOR_ASSIGN", "OR_ASSIGN", "FLOAT_TOK", "INT_TOK", "UINT_TOK", 
			"BOOL_TOK", "SAMPLERCUBE", "SAMPLERCUBESHADOW", "SAMPLERBUFFER", "SAMPLERCUBEARRAY", 
			"SAMPLERCUBEARRAYSHADOW", "ISAMPLERCUBE", "ISAMPLERBUFFER", "ISAMPLERCUBEARRAY", 
			"USAMPLERCUBE", "USAMPLERBUFFER", "USAMPLERCUBEARRAY", "IMAGECUBE", "IMAGEBUFFER", 
			"IMAGECUBEARRAY", "IIMAGECUBE", "IIMAGEBUFFER", "IIMAGECUBEARRAY", "UIMAGECUBE", 
			"UIMAGEBUFFER", "UIMAGECUBEARRAY", "ATOMIC_UINT", "STRUCT", "IF", "ELSE", 
			"SWITCH", "CASE", "DEFAULT", "WHILE", "DO", "FOR", "CONTINUE", "BREAK", 
			"RETURN", "DISCARD", "VEC2", "VEC3", "VEC4", "BVEC2", "BVEC3", "BVEC4", 
			"IVEC2", "IVEC3", "IVEC4", "UVEC2", "UVEC3", "UVEC4", "MAT2X2", "MAT2X3", 
			"MAT2X4", "MAT3X2", "MAT3X3", "MAT3X4", "MAT4X2", "MAT4X3", "MAT4X4", 
			"IMAGE1D", "IMAGE2D", "IMAGE3D", "UIMAGE1D", "UIMAGE2D", "UIMAGE3D", 
			"IIMAGE1D", "IIMAGE2D", "IIMAGE3D", "SAMPLER1D", "SAMPLER2D", "SAMPLER3D", 
			"SAMPLER2DRECT", "SAMPLEREXTERNALOES", "SAMPLER1DSHADOW", "SAMPLER2DSHADOW", 
			"SAMPLER2DRECTSHADOW", "SAMPLER1DARRAY", "SAMPLER2DARRAY", "SAMPLER1DARRAYSHADOW", 
			"SAMPLER2DARRAYSHADOW", "ISAMPLER1D", "ISAMPLER2D", "ISAMPLER2DRECT", 
			"ISAMPLER3D", "ISAMPLER1DARRAY", "ISAMPLER2DARRAY", "USAMPLER1D", "USAMPLER2D", 
			"USAMPLER2DRECT", "USAMPLER3D", "USAMPLER1DARRAY", "USAMPLER2DARRAY", 
			"SAMPLER2DMS", "ISAMPLER2DMS", "USAMPLER2DMS", "SAMPLER2DMSARRAY", "ISAMPLER2DMSARRAY", 
			"USAMPLER2DMSARRAY", "IMAGE2DRECT", "IMAGE1DARRAY", "IMAGE2DARRAY", "IMAGE2DMS", 
			"IMAGE2DMSARRAY", "IIMAGE2DRECT", "IIMAGE1DARRAY", "IIMAGE2DARRAY", "IIMAGE2DMS", 
			"IIMAGE2DMSARRAY", "UIMAGE2DRECT", "UIMAGE1DARRAY", "UIMAGE2DARRAY", 
			"UIMAGE2DMS", "UIMAGE2DMSARRAY", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"SEMICOLON", "LBRACKET", "RBRACKET", "COMMA", "DOT", "PLUS_OP", "MINUS_OP", 
			"NOT_OP", "BNEG_OP", "TIMES_OP", "DIV_OP", "MOD_OP", "LT_OP", "GT_OP", 
			"BAND_OP", "BOR_OP", "BXOR_OP", "QUERY_OP", "ASSIGN_OP", "IDENTIFIER", 
			"COMMENT", "WS", "EOL"
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
	public String getGrammarFileName() { return "GLSLOther.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GLSLOtherParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Translation_unitContext extends ParserRuleContext {
		public Version_statementContext version_statement() {
			return getRuleContext(Version_statementContext.class,0);
		}
		public Extension_statement_listContext extension_statement_list() {
			return getRuleContext(Extension_statement_listContext.class,0);
		}
		public External_declaration_listContext external_declaration_list() {
			return getRuleContext(External_declaration_listContext.class,0);
		}
		public Translation_unitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translation_unit; }
	}

	public final Translation_unitContext translation_unit() throws RecognitionException {
		Translation_unitContext _localctx = new Translation_unitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_translation_unit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			version_statement();
			setState(207);
			extension_statement_list(0);
			setState(208);
			external_declaration_list(0);
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

	public static class Version_statementContext extends ParserRuleContext {
		public TerminalNode VERSION() { return getToken(GLSLOtherParser.VERSION, 0); }
		public TerminalNode INTCONSTANT() { return getToken(GLSLOtherParser.INTCONSTANT, 0); }
		public TerminalNode EOL() { return getToken(GLSLOtherParser.EOL, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Version_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_version_statement; }
	}

	public final Version_statementContext version_statement() throws RecognitionException {
		Version_statementContext _localctx = new Version_statementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_version_statement);
		try {
			setState(218);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				match(VERSION);
				setState(212);
				match(INTCONSTANT);
				setState(213);
				match(EOL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(214);
				match(VERSION);
				setState(215);
				match(INTCONSTANT);
				setState(216);
				match(IDENTIFIER);
				setState(217);
				match(EOL);
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

	public static class Pragma_statementContext extends ParserRuleContext {
		public TerminalNode PRAGMA_DEBUG_ON() { return getToken(GLSLOtherParser.PRAGMA_DEBUG_ON, 0); }
		public TerminalNode EOL() { return getToken(GLSLOtherParser.EOL, 0); }
		public TerminalNode PRAGMA_DEBUG_OFF() { return getToken(GLSLOtherParser.PRAGMA_DEBUG_OFF, 0); }
		public TerminalNode PRAGMA_OPTIMIZE_ON() { return getToken(GLSLOtherParser.PRAGMA_OPTIMIZE_ON, 0); }
		public TerminalNode PRAGMA_OPTIMIZE_OFF() { return getToken(GLSLOtherParser.PRAGMA_OPTIMIZE_OFF, 0); }
		public TerminalNode PRAGMA_INVARIANT_ALL() { return getToken(GLSLOtherParser.PRAGMA_INVARIANT_ALL, 0); }
		public Pragma_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma_statement; }
	}

	public final Pragma_statementContext pragma_statement() throws RecognitionException {
		Pragma_statementContext _localctx = new Pragma_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_pragma_statement);
		try {
			setState(230);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRAGMA_DEBUG_ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				match(PRAGMA_DEBUG_ON);
				setState(221);
				match(EOL);
				}
				break;
			case PRAGMA_DEBUG_OFF:
				enterOuterAlt(_localctx, 2);
				{
				setState(222);
				match(PRAGMA_DEBUG_OFF);
				setState(223);
				match(EOL);
				}
				break;
			case PRAGMA_OPTIMIZE_ON:
				enterOuterAlt(_localctx, 3);
				{
				setState(224);
				match(PRAGMA_OPTIMIZE_ON);
				setState(225);
				match(EOL);
				}
				break;
			case PRAGMA_OPTIMIZE_OFF:
				enterOuterAlt(_localctx, 4);
				{
				setState(226);
				match(PRAGMA_OPTIMIZE_OFF);
				setState(227);
				match(EOL);
				}
				break;
			case PRAGMA_INVARIANT_ALL:
				enterOuterAlt(_localctx, 5);
				{
				setState(228);
				match(PRAGMA_INVARIANT_ALL);
				setState(229);
				match(EOL);
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

	public static class Extension_statement_listContext extends ParserRuleContext {
		public Extension_statement_listContext extension_statement_list() {
			return getRuleContext(Extension_statement_listContext.class,0);
		}
		public Extension_statementContext extension_statement() {
			return getRuleContext(Extension_statementContext.class,0);
		}
		public Extension_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_statement_list; }
	}

	public final Extension_statement_listContext extension_statement_list() throws RecognitionException {
		return extension_statement_list(0);
	}

	private Extension_statement_listContext extension_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Extension_statement_listContext _localctx = new Extension_statement_listContext(_ctx, _parentState);
		Extension_statement_listContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_extension_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			}
			_ctx.stop = _input.LT(-1);
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Extension_statement_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_extension_statement_list);
					setState(233);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(234);
					extension_statement();
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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

	public static class Extension_statementContext extends ParserRuleContext {
		public Token extension_name;
		public Token extension_status;
		public TerminalNode EXTENSION() { return getToken(GLSLOtherParser.EXTENSION, 0); }
		public TerminalNode COLON() { return getToken(GLSLOtherParser.COLON, 0); }
		public TerminalNode EOL() { return getToken(GLSLOtherParser.EOL, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSLOtherParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSLOtherParser.IDENTIFIER, i);
		}
		public Extension_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_statement; }
	}

	public final Extension_statementContext extension_statement() throws RecognitionException {
		Extension_statementContext _localctx = new Extension_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_extension_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(EXTENSION);
			setState(241);
			((Extension_statementContext)_localctx).extension_name = match(IDENTIFIER);
			setState(242);
			match(COLON);
			setState(243);
			((Extension_statementContext)_localctx).extension_status = match(IDENTIFIER);
			setState(244);
			match(EOL);
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

	public static class External_declaration_listContext extends ParserRuleContext {
		public External_declaration_listContext prefix;
		public External_declarationContext single;
		public External_declarationContext lastDecl;
		public Extension_statementContext lastExtension;
		public External_declarationContext external_declaration() {
			return getRuleContext(External_declarationContext.class,0);
		}
		public External_declaration_listContext external_declaration_list() {
			return getRuleContext(External_declaration_listContext.class,0);
		}
		public Extension_statementContext extension_statement() {
			return getRuleContext(Extension_statementContext.class,0);
		}
		public External_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_external_declaration_list; }
	}

	public final External_declaration_listContext external_declaration_list() throws RecognitionException {
		return external_declaration_list(0);
	}

	private External_declaration_listContext external_declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		External_declaration_listContext _localctx = new External_declaration_listContext(_ctx, _parentState);
		External_declaration_listContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_external_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(247);
			((External_declaration_listContext)_localctx).single = external_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(255);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(253);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new External_declaration_listContext(_parentctx, _parentState);
						_localctx.prefix = _prevctx;
						_localctx.prefix = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_external_declaration_list);
						setState(249);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(250);
						((External_declaration_listContext)_localctx).lastDecl = external_declaration();
						}
						break;
					case 2:
						{
						_localctx = new External_declaration_listContext(_parentctx, _parentState);
						_localctx.prefix = _prevctx;
						_localctx.prefix = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_external_declaration_list);
						setState(251);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(252);
						((External_declaration_listContext)_localctx).lastExtension = extension_statement();
						}
						break;
					}
					} 
				}
				setState(257);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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

	public static class Variable_identifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Variable_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_identifier; }
	}

	public final Variable_identifierContext variable_identifier() throws RecognitionException {
		Variable_identifierContext _localctx = new Variable_identifierContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variable_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
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

	public static class Primary_expressionContext extends ParserRuleContext {
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode INTCONSTANT() { return getToken(GLSLOtherParser.INTCONSTANT, 0); }
		public TerminalNode UINTCONSTANT() { return getToken(GLSLOtherParser.UINTCONSTANT, 0); }
		public TerminalNode FLOATCONSTANT() { return getToken(GLSLOtherParser.FLOATCONSTANT, 0); }
		public TerminalNode BOOLCONSTANT() { return getToken(GLSLOtherParser.BOOLCONSTANT, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary_expression; }
	}

	public final Primary_expressionContext primary_expression() throws RecognitionException {
		Primary_expressionContext _localctx = new Primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_primary_expression);
		try {
			setState(269);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				variable_identifier();
				}
				break;
			case INTCONSTANT:
				enterOuterAlt(_localctx, 2);
				{
				setState(261);
				match(INTCONSTANT);
				}
				break;
			case UINTCONSTANT:
				enterOuterAlt(_localctx, 3);
				{
				setState(262);
				match(UINTCONSTANT);
				}
				break;
			case FLOATCONSTANT:
				enterOuterAlt(_localctx, 4);
				{
				setState(263);
				match(FLOATCONSTANT);
				}
				break;
			case BOOLCONSTANT:
				enterOuterAlt(_localctx, 5);
				{
				setState(264);
				match(BOOLCONSTANT);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(265);
				match(LPAREN);
				setState(266);
				expression();
				setState(267);
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

	public static class Postfix_expressionContext extends ParserRuleContext {
		public Primary_expressionContext primary_expression() {
			return getRuleContext(Primary_expressionContext.class,0);
		}
		public Function_call_genericContext function_call_generic() {
			return getRuleContext(Function_call_genericContext.class,0);
		}
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(GLSLOtherParser.LBRACKET, 0); }
		public Integer_expressionContext integer_expression() {
			return getRuleContext(Integer_expressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(GLSLOtherParser.RBRACKET, 0); }
		public TerminalNode DOT() { return getToken(GLSLOtherParser.DOT, 0); }
		public Method_call_genericContext method_call_generic() {
			return getRuleContext(Method_call_genericContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public TerminalNode INC_OP() { return getToken(GLSLOtherParser.INC_OP, 0); }
		public TerminalNode DEC_OP() { return getToken(GLSLOtherParser.DEC_OP, 0); }
		public Postfix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expression; }
	}

	public final Postfix_expressionContext postfix_expression() throws RecognitionException {
		return postfix_expression(0);
	}

	private Postfix_expressionContext postfix_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Postfix_expressionContext _localctx = new Postfix_expressionContext(_ctx, _parentState);
		Postfix_expressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_postfix_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(272);
				primary_expression();
				}
				break;
			case 2:
				{
				setState(273);
				function_call_generic();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(293);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(291);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new Postfix_expressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
						setState(276);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(277);
						match(LBRACKET);
						setState(278);
						integer_expression();
						setState(279);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new Postfix_expressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
						setState(281);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(282);
						match(DOT);
						setState(283);
						method_call_generic();
						}
						break;
					case 3:
						{
						_localctx = new Postfix_expressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
						setState(284);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(285);
						match(DOT);
						setState(286);
						match(IDENTIFIER);
						}
						break;
					case 4:
						{
						_localctx = new Postfix_expressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
						setState(287);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(288);
						match(INC_OP);
						}
						break;
					case 5:
						{
						_localctx = new Postfix_expressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
						setState(289);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(290);
						match(DEC_OP);
						}
						break;
					}
					} 
				}
				setState(295);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	public static class Integer_expressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Integer_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_expression; }
	}

	public final Integer_expressionContext integer_expression() throws RecognitionException {
		Integer_expressionContext _localctx = new Integer_expressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_integer_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			expression();
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

	public static class Function_call_genericContext extends ParserRuleContext {
		public Function_call_header_with_parametersContext function_call_header_with_parameters() {
			return getRuleContext(Function_call_header_with_parametersContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Function_call_header_no_parametersContext function_call_header_no_parameters() {
			return getRuleContext(Function_call_header_no_parametersContext.class,0);
		}
		public Function_call_genericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call_generic; }
	}

	public final Function_call_genericContext function_call_generic() throws RecognitionException {
		Function_call_genericContext _localctx = new Function_call_genericContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_function_call_generic);
		try {
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(298);
				function_call_header_with_parameters(0);
				setState(299);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				function_call_header_no_parameters();
				setState(302);
				match(RPAREN);
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

	public static class Function_call_header_no_parametersContext extends ParserRuleContext {
		public Function_call_headerContext function_call_header() {
			return getRuleContext(Function_call_headerContext.class,0);
		}
		public TerminalNode VOID_TOK() { return getToken(GLSLOtherParser.VOID_TOK, 0); }
		public Function_call_header_no_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call_header_no_parameters; }
	}

	public final Function_call_header_no_parametersContext function_call_header_no_parameters() throws RecognitionException {
		Function_call_header_no_parametersContext _localctx = new Function_call_header_no_parametersContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_function_call_header_no_parameters);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				function_call_header();
				setState(307);
				match(VOID_TOK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(309);
				function_call_header();
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

	public static class Function_call_header_with_parametersContext extends ParserRuleContext {
		public Function_call_headerContext function_call_header() {
			return getRuleContext(Function_call_headerContext.class,0);
		}
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public Function_call_header_with_parametersContext function_call_header_with_parameters() {
			return getRuleContext(Function_call_header_with_parametersContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Function_call_header_with_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call_header_with_parameters; }
	}

	public final Function_call_header_with_parametersContext function_call_header_with_parameters() throws RecognitionException {
		return function_call_header_with_parameters(0);
	}

	private Function_call_header_with_parametersContext function_call_header_with_parameters(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Function_call_header_with_parametersContext _localctx = new Function_call_header_with_parametersContext(_ctx, _parentState);
		Function_call_header_with_parametersContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_function_call_header_with_parameters, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(313);
			function_call_header();
			setState(314);
			assignment_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(321);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Function_call_header_with_parametersContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_function_call_header_with_parameters);
					setState(316);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(317);
					match(COMMA);
					setState(318);
					assignment_expression();
					}
					} 
				}
				setState(323);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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

	public static class Function_call_headerContext extends ParserRuleContext {
		public Function_identifierContext function_identifier() {
			return getRuleContext(Function_identifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Function_call_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call_header; }
	}

	public final Function_call_headerContext function_call_header() throws RecognitionException {
		Function_call_headerContext _localctx = new Function_call_headerContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_function_call_header);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			function_identifier();
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(325);
				array_specifier(0);
				}
			}

			setState(328);
			match(LPAREN);
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

	public static class Function_identifierContext extends ParserRuleContext {
		public Builtin_type_specifier_nonarrayContext builtin_type_specifier_nonarray() {
			return getRuleContext(Builtin_type_specifier_nonarrayContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public Function_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_identifier; }
	}

	public final Function_identifierContext function_identifier() throws RecognitionException {
		Function_identifierContext _localctx = new Function_identifierContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_function_identifier);
		try {
			setState(332);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(330);
				builtin_type_specifier_nonarray();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(331);
				variable_identifier();
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

	public static class Method_call_genericContext extends ParserRuleContext {
		public Method_call_header_with_parametersContext method_call_header_with_parameters() {
			return getRuleContext(Method_call_header_with_parametersContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Method_call_header_no_parametersContext method_call_header_no_parameters() {
			return getRuleContext(Method_call_header_no_parametersContext.class,0);
		}
		public Method_call_genericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call_generic; }
	}

	public final Method_call_genericContext method_call_generic() throws RecognitionException {
		Method_call_genericContext _localctx = new Method_call_genericContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_method_call_generic);
		try {
			setState(340);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(334);
				method_call_header_with_parameters(0);
				setState(335);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				method_call_header_no_parameters();
				setState(338);
				match(RPAREN);
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

	public static class Method_call_header_no_parametersContext extends ParserRuleContext {
		public Method_call_headerContext method_call_header() {
			return getRuleContext(Method_call_headerContext.class,0);
		}
		public TerminalNode VOID_TOK() { return getToken(GLSLOtherParser.VOID_TOK, 0); }
		public Method_call_header_no_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call_header_no_parameters; }
	}

	public final Method_call_header_no_parametersContext method_call_header_no_parameters() throws RecognitionException {
		Method_call_header_no_parametersContext _localctx = new Method_call_header_no_parametersContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_method_call_header_no_parameters);
		try {
			setState(346);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(342);
				method_call_header();
				setState(343);
				match(VOID_TOK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(345);
				method_call_header();
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

	public static class Method_call_header_with_parametersContext extends ParserRuleContext {
		public Method_call_headerContext method_call_header() {
			return getRuleContext(Method_call_headerContext.class,0);
		}
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public Method_call_header_with_parametersContext method_call_header_with_parameters() {
			return getRuleContext(Method_call_header_with_parametersContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Method_call_header_with_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call_header_with_parameters; }
	}

	public final Method_call_header_with_parametersContext method_call_header_with_parameters() throws RecognitionException {
		return method_call_header_with_parameters(0);
	}

	private Method_call_header_with_parametersContext method_call_header_with_parameters(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Method_call_header_with_parametersContext _localctx = new Method_call_header_with_parametersContext(_ctx, _parentState);
		Method_call_header_with_parametersContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_method_call_header_with_parameters, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(349);
			method_call_header();
			setState(350);
			assignment_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(357);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Method_call_header_with_parametersContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_method_call_header_with_parameters);
					setState(352);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(353);
					match(COMMA);
					setState(354);
					assignment_expression();
					}
					} 
				}
				setState(359);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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

	public static class Method_call_headerContext extends ParserRuleContext {
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public Method_call_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call_header; }
	}

	public final Method_call_headerContext method_call_header() throws RecognitionException {
		Method_call_headerContext _localctx = new Method_call_headerContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_method_call_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			variable_identifier();
			setState(361);
			match(LPAREN);
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

	public static class Unary_expressionContext extends ParserRuleContext {
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public TerminalNode INC_OP() { return getToken(GLSLOtherParser.INC_OP, 0); }
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public TerminalNode DEC_OP() { return getToken(GLSLOtherParser.DEC_OP, 0); }
		public Unary_operatorContext unary_operator() {
			return getRuleContext(Unary_operatorContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_unary_expression);
		try {
			setState(371);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTCONSTANT:
			case UINTCONSTANT:
			case FLOATCONSTANT:
			case BOOLCONSTANT:
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
			case LPAREN:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(363);
				postfix_expression(0);
				}
				break;
			case INC_OP:
				enterOuterAlt(_localctx, 2);
				{
				setState(364);
				match(INC_OP);
				setState(365);
				unary_expression();
				}
				break;
			case DEC_OP:
				enterOuterAlt(_localctx, 3);
				{
				setState(366);
				match(DEC_OP);
				setState(367);
				unary_expression();
				}
				break;
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
				enterOuterAlt(_localctx, 4);
				{
				setState(368);
				unary_operator();
				setState(369);
				unary_expression();
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

	public static class Unary_operatorContext extends ParserRuleContext {
		public TerminalNode PLUS_OP() { return getToken(GLSLOtherParser.PLUS_OP, 0); }
		public TerminalNode MINUS_OP() { return getToken(GLSLOtherParser.MINUS_OP, 0); }
		public TerminalNode NOT_OP() { return getToken(GLSLOtherParser.NOT_OP, 0); }
		public TerminalNode BNEG_OP() { return getToken(GLSLOtherParser.BNEG_OP, 0); }
		public Unary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_operator; }
	}

	public final Unary_operatorContext unary_operator() throws RecognitionException {
		Unary_operatorContext _localctx = new Unary_operatorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_unary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			_la = _input.LA(1);
			if ( !(((((_la - 185)) & ~0x3f) == 0 && ((1L << (_la - 185)) & ((1L << (PLUS_OP - 185)) | (1L << (MINUS_OP - 185)) | (1L << (NOT_OP - 185)) | (1L << (BNEG_OP - 185)))) != 0)) ) {
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

	public static class Multiplicative_expressionContext extends ParserRuleContext {
		public Unary_expressionContext unary_expression;
		public List<Unary_expressionContext> operands = new ArrayList<Unary_expressionContext>();
		public Token TIMES_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token DIV_OP;
		public Token MOD_OP;
		public List<Unary_expressionContext> unary_expression() {
			return getRuleContexts(Unary_expressionContext.class);
		}
		public Unary_expressionContext unary_expression(int i) {
			return getRuleContext(Unary_expressionContext.class,i);
		}
		public List<TerminalNode> TIMES_OP() { return getTokens(GLSLOtherParser.TIMES_OP); }
		public TerminalNode TIMES_OP(int i) {
			return getToken(GLSLOtherParser.TIMES_OP, i);
		}
		public List<TerminalNode> DIV_OP() { return getTokens(GLSLOtherParser.DIV_OP); }
		public TerminalNode DIV_OP(int i) {
			return getToken(GLSLOtherParser.DIV_OP, i);
		}
		public List<TerminalNode> MOD_OP() { return getTokens(GLSLOtherParser.MOD_OP); }
		public TerminalNode MOD_OP(int i) {
			return getToken(GLSLOtherParser.MOD_OP, i);
		}
		public Multiplicative_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression; }
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multiplicative_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			((Multiplicative_expressionContext)_localctx).unary_expression = unary_expression();
			((Multiplicative_expressionContext)_localctx).operands.add(((Multiplicative_expressionContext)_localctx).unary_expression);
			setState(384);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(379);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case TIMES_OP:
						{
						setState(376);
						((Multiplicative_expressionContext)_localctx).TIMES_OP = match(TIMES_OP);
						((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).TIMES_OP);
						}
						break;
					case DIV_OP:
						{
						setState(377);
						((Multiplicative_expressionContext)_localctx).DIV_OP = match(DIV_OP);
						((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).DIV_OP);
						}
						break;
					case MOD_OP:
						{
						setState(378);
						((Multiplicative_expressionContext)_localctx).MOD_OP = match(MOD_OP);
						((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).MOD_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(381);
					((Multiplicative_expressionContext)_localctx).unary_expression = unary_expression();
					((Multiplicative_expressionContext)_localctx).operands.add(((Multiplicative_expressionContext)_localctx).unary_expression);
					}
					} 
				}
				setState(386);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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

	public static class Additive_expressionContext extends ParserRuleContext {
		public Multiplicative_expressionContext multiplicative_expression;
		public List<Multiplicative_expressionContext> operands = new ArrayList<Multiplicative_expressionContext>();
		public Token PLUS_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token MINUS_OP;
		public List<Multiplicative_expressionContext> multiplicative_expression() {
			return getRuleContexts(Multiplicative_expressionContext.class);
		}
		public Multiplicative_expressionContext multiplicative_expression(int i) {
			return getRuleContext(Multiplicative_expressionContext.class,i);
		}
		public List<TerminalNode> PLUS_OP() { return getTokens(GLSLOtherParser.PLUS_OP); }
		public TerminalNode PLUS_OP(int i) {
			return getToken(GLSLOtherParser.PLUS_OP, i);
		}
		public List<TerminalNode> MINUS_OP() { return getTokens(GLSLOtherParser.MINUS_OP); }
		public TerminalNode MINUS_OP(int i) {
			return getToken(GLSLOtherParser.MINUS_OP, i);
		}
		public Additive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression; }
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_additive_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			((Additive_expressionContext)_localctx).multiplicative_expression = multiplicative_expression();
			((Additive_expressionContext)_localctx).operands.add(((Additive_expressionContext)_localctx).multiplicative_expression);
			setState(395);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(390);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case PLUS_OP:
						{
						setState(388);
						((Additive_expressionContext)_localctx).PLUS_OP = match(PLUS_OP);
						((Additive_expressionContext)_localctx).operators.add(((Additive_expressionContext)_localctx).PLUS_OP);
						}
						break;
					case MINUS_OP:
						{
						setState(389);
						((Additive_expressionContext)_localctx).MINUS_OP = match(MINUS_OP);
						((Additive_expressionContext)_localctx).operators.add(((Additive_expressionContext)_localctx).MINUS_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(392);
					((Additive_expressionContext)_localctx).multiplicative_expression = multiplicative_expression();
					((Additive_expressionContext)_localctx).operands.add(((Additive_expressionContext)_localctx).multiplicative_expression);
					}
					} 
				}
				setState(397);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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

	public static class Shift_expressionContext extends ParserRuleContext {
		public Additive_expressionContext additive_expression;
		public List<Additive_expressionContext> operands = new ArrayList<Additive_expressionContext>();
		public Token LEFT_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token RIGHT_OP;
		public List<Additive_expressionContext> additive_expression() {
			return getRuleContexts(Additive_expressionContext.class);
		}
		public Additive_expressionContext additive_expression(int i) {
			return getRuleContext(Additive_expressionContext.class,i);
		}
		public List<TerminalNode> LEFT_OP() { return getTokens(GLSLOtherParser.LEFT_OP); }
		public TerminalNode LEFT_OP(int i) {
			return getToken(GLSLOtherParser.LEFT_OP, i);
		}
		public List<TerminalNode> RIGHT_OP() { return getTokens(GLSLOtherParser.RIGHT_OP); }
		public TerminalNode RIGHT_OP(int i) {
			return getToken(GLSLOtherParser.RIGHT_OP, i);
		}
		public Shift_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shift_expression; }
	}

	public final Shift_expressionContext shift_expression() throws RecognitionException {
		Shift_expressionContext _localctx = new Shift_expressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_shift_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			((Shift_expressionContext)_localctx).additive_expression = additive_expression();
			((Shift_expressionContext)_localctx).operands.add(((Shift_expressionContext)_localctx).additive_expression);
			setState(406);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(401);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LEFT_OP:
						{
						setState(399);
						((Shift_expressionContext)_localctx).LEFT_OP = match(LEFT_OP);
						((Shift_expressionContext)_localctx).operators.add(((Shift_expressionContext)_localctx).LEFT_OP);
						}
						break;
					case RIGHT_OP:
						{
						setState(400);
						((Shift_expressionContext)_localctx).RIGHT_OP = match(RIGHT_OP);
						((Shift_expressionContext)_localctx).operators.add(((Shift_expressionContext)_localctx).RIGHT_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(403);
					((Shift_expressionContext)_localctx).additive_expression = additive_expression();
					((Shift_expressionContext)_localctx).operands.add(((Shift_expressionContext)_localctx).additive_expression);
					}
					} 
				}
				setState(408);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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

	public static class Relational_expressionContext extends ParserRuleContext {
		public Shift_expressionContext shift_expression;
		public List<Shift_expressionContext> operands = new ArrayList<Shift_expressionContext>();
		public Token LT_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token GT_OP;
		public Token LE_OP;
		public Token GE_OP;
		public List<Shift_expressionContext> shift_expression() {
			return getRuleContexts(Shift_expressionContext.class);
		}
		public Shift_expressionContext shift_expression(int i) {
			return getRuleContext(Shift_expressionContext.class,i);
		}
		public List<TerminalNode> LT_OP() { return getTokens(GLSLOtherParser.LT_OP); }
		public TerminalNode LT_OP(int i) {
			return getToken(GLSLOtherParser.LT_OP, i);
		}
		public List<TerminalNode> GT_OP() { return getTokens(GLSLOtherParser.GT_OP); }
		public TerminalNode GT_OP(int i) {
			return getToken(GLSLOtherParser.GT_OP, i);
		}
		public List<TerminalNode> LE_OP() { return getTokens(GLSLOtherParser.LE_OP); }
		public TerminalNode LE_OP(int i) {
			return getToken(GLSLOtherParser.LE_OP, i);
		}
		public List<TerminalNode> GE_OP() { return getTokens(GLSLOtherParser.GE_OP); }
		public TerminalNode GE_OP(int i) {
			return getToken(GLSLOtherParser.GE_OP, i);
		}
		public Relational_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expression; }
	}

	public final Relational_expressionContext relational_expression() throws RecognitionException {
		Relational_expressionContext _localctx = new Relational_expressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_relational_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			((Relational_expressionContext)_localctx).shift_expression = shift_expression();
			((Relational_expressionContext)_localctx).operands.add(((Relational_expressionContext)_localctx).shift_expression);
			setState(419);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(414);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LT_OP:
						{
						setState(410);
						((Relational_expressionContext)_localctx).LT_OP = match(LT_OP);
						((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LT_OP);
						}
						break;
					case GT_OP:
						{
						setState(411);
						((Relational_expressionContext)_localctx).GT_OP = match(GT_OP);
						((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GT_OP);
						}
						break;
					case LE_OP:
						{
						setState(412);
						((Relational_expressionContext)_localctx).LE_OP = match(LE_OP);
						((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LE_OP);
						}
						break;
					case GE_OP:
						{
						setState(413);
						((Relational_expressionContext)_localctx).GE_OP = match(GE_OP);
						((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GE_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(416);
					((Relational_expressionContext)_localctx).shift_expression = shift_expression();
					((Relational_expressionContext)_localctx).operands.add(((Relational_expressionContext)_localctx).shift_expression);
					}
					} 
				}
				setState(421);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
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

	public static class Equality_expressionContext extends ParserRuleContext {
		public Relational_expressionContext relational_expression;
		public List<Relational_expressionContext> operands = new ArrayList<Relational_expressionContext>();
		public Token EQ_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token NE_OP;
		public List<Relational_expressionContext> relational_expression() {
			return getRuleContexts(Relational_expressionContext.class);
		}
		public Relational_expressionContext relational_expression(int i) {
			return getRuleContext(Relational_expressionContext.class,i);
		}
		public List<TerminalNode> EQ_OP() { return getTokens(GLSLOtherParser.EQ_OP); }
		public TerminalNode EQ_OP(int i) {
			return getToken(GLSLOtherParser.EQ_OP, i);
		}
		public List<TerminalNode> NE_OP() { return getTokens(GLSLOtherParser.NE_OP); }
		public TerminalNode NE_OP(int i) {
			return getToken(GLSLOtherParser.NE_OP, i);
		}
		public Equality_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality_expression; }
	}

	public final Equality_expressionContext equality_expression() throws RecognitionException {
		Equality_expressionContext _localctx = new Equality_expressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_equality_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			((Equality_expressionContext)_localctx).relational_expression = relational_expression();
			((Equality_expressionContext)_localctx).operands.add(((Equality_expressionContext)_localctx).relational_expression);
			setState(430);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(425);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case EQ_OP:
						{
						setState(423);
						((Equality_expressionContext)_localctx).EQ_OP = match(EQ_OP);
						((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).EQ_OP);
						}
						break;
					case NE_OP:
						{
						setState(424);
						((Equality_expressionContext)_localctx).NE_OP = match(NE_OP);
						((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).NE_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(427);
					((Equality_expressionContext)_localctx).relational_expression = relational_expression();
					((Equality_expressionContext)_localctx).operands.add(((Equality_expressionContext)_localctx).relational_expression);
					}
					} 
				}
				setState(432);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
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

	public static class And_expressionContext extends ParserRuleContext {
		public Equality_expressionContext equality_expression;
		public List<Equality_expressionContext> operands = new ArrayList<Equality_expressionContext>();
		public Token BAND_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<Equality_expressionContext> equality_expression() {
			return getRuleContexts(Equality_expressionContext.class);
		}
		public Equality_expressionContext equality_expression(int i) {
			return getRuleContext(Equality_expressionContext.class,i);
		}
		public List<TerminalNode> BAND_OP() { return getTokens(GLSLOtherParser.BAND_OP); }
		public TerminalNode BAND_OP(int i) {
			return getToken(GLSLOtherParser.BAND_OP, i);
		}
		public And_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expression; }
	}

	public final And_expressionContext and_expression() throws RecognitionException {
		And_expressionContext _localctx = new And_expressionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_and_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(433);
			((And_expressionContext)_localctx).equality_expression = equality_expression();
			((And_expressionContext)_localctx).operands.add(((And_expressionContext)_localctx).equality_expression);
			setState(438);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(434);
					((And_expressionContext)_localctx).BAND_OP = match(BAND_OP);
					((And_expressionContext)_localctx).operators.add(((And_expressionContext)_localctx).BAND_OP);
					setState(435);
					((And_expressionContext)_localctx).equality_expression = equality_expression();
					((And_expressionContext)_localctx).operands.add(((And_expressionContext)_localctx).equality_expression);
					}
					} 
				}
				setState(440);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
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

	public static class Exclusive_or_expressionContext extends ParserRuleContext {
		public And_expressionContext and_expression;
		public List<And_expressionContext> operands = new ArrayList<And_expressionContext>();
		public Token BXOR_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<And_expressionContext> and_expression() {
			return getRuleContexts(And_expressionContext.class);
		}
		public And_expressionContext and_expression(int i) {
			return getRuleContext(And_expressionContext.class,i);
		}
		public List<TerminalNode> BXOR_OP() { return getTokens(GLSLOtherParser.BXOR_OP); }
		public TerminalNode BXOR_OP(int i) {
			return getToken(GLSLOtherParser.BXOR_OP, i);
		}
		public Exclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusive_or_expression; }
	}

	public final Exclusive_or_expressionContext exclusive_or_expression() throws RecognitionException {
		Exclusive_or_expressionContext _localctx = new Exclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_exclusive_or_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			((Exclusive_or_expressionContext)_localctx).and_expression = and_expression();
			((Exclusive_or_expressionContext)_localctx).operands.add(((Exclusive_or_expressionContext)_localctx).and_expression);
			setState(446);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(442);
					((Exclusive_or_expressionContext)_localctx).BXOR_OP = match(BXOR_OP);
					((Exclusive_or_expressionContext)_localctx).operators.add(((Exclusive_or_expressionContext)_localctx).BXOR_OP);
					setState(443);
					((Exclusive_or_expressionContext)_localctx).and_expression = and_expression();
					((Exclusive_or_expressionContext)_localctx).operands.add(((Exclusive_or_expressionContext)_localctx).and_expression);
					}
					} 
				}
				setState(448);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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

	public static class Inclusive_or_expressionContext extends ParserRuleContext {
		public Exclusive_or_expressionContext exclusive_or_expression;
		public List<Exclusive_or_expressionContext> operands = new ArrayList<Exclusive_or_expressionContext>();
		public Token BOR_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<Exclusive_or_expressionContext> exclusive_or_expression() {
			return getRuleContexts(Exclusive_or_expressionContext.class);
		}
		public Exclusive_or_expressionContext exclusive_or_expression(int i) {
			return getRuleContext(Exclusive_or_expressionContext.class,i);
		}
		public List<TerminalNode> BOR_OP() { return getTokens(GLSLOtherParser.BOR_OP); }
		public TerminalNode BOR_OP(int i) {
			return getToken(GLSLOtherParser.BOR_OP, i);
		}
		public Inclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusive_or_expression; }
	}

	public final Inclusive_or_expressionContext inclusive_or_expression() throws RecognitionException {
		Inclusive_or_expressionContext _localctx = new Inclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_inclusive_or_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
			((Inclusive_or_expressionContext)_localctx).exclusive_or_expression = exclusive_or_expression();
			((Inclusive_or_expressionContext)_localctx).operands.add(((Inclusive_or_expressionContext)_localctx).exclusive_or_expression);
			setState(454);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(450);
					((Inclusive_or_expressionContext)_localctx).BOR_OP = match(BOR_OP);
					((Inclusive_or_expressionContext)_localctx).operators.add(((Inclusive_or_expressionContext)_localctx).BOR_OP);
					setState(451);
					((Inclusive_or_expressionContext)_localctx).exclusive_or_expression = exclusive_or_expression();
					((Inclusive_or_expressionContext)_localctx).operands.add(((Inclusive_or_expressionContext)_localctx).exclusive_or_expression);
					}
					} 
				}
				setState(456);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
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

	public static class Logical_and_expressionContext extends ParserRuleContext {
		public Inclusive_or_expressionContext inclusive_or_expression;
		public List<Inclusive_or_expressionContext> operands = new ArrayList<Inclusive_or_expressionContext>();
		public Token AND_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<Inclusive_or_expressionContext> inclusive_or_expression() {
			return getRuleContexts(Inclusive_or_expressionContext.class);
		}
		public Inclusive_or_expressionContext inclusive_or_expression(int i) {
			return getRuleContext(Inclusive_or_expressionContext.class,i);
		}
		public List<TerminalNode> AND_OP() { return getTokens(GLSLOtherParser.AND_OP); }
		public TerminalNode AND_OP(int i) {
			return getToken(GLSLOtherParser.AND_OP, i);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_logical_and_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(457);
			((Logical_and_expressionContext)_localctx).inclusive_or_expression = inclusive_or_expression();
			((Logical_and_expressionContext)_localctx).operands.add(((Logical_and_expressionContext)_localctx).inclusive_or_expression);
			setState(462);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(458);
					((Logical_and_expressionContext)_localctx).AND_OP = match(AND_OP);
					((Logical_and_expressionContext)_localctx).operators.add(((Logical_and_expressionContext)_localctx).AND_OP);
					setState(459);
					((Logical_and_expressionContext)_localctx).inclusive_or_expression = inclusive_or_expression();
					((Logical_and_expressionContext)_localctx).operands.add(((Logical_and_expressionContext)_localctx).inclusive_or_expression);
					}
					} 
				}
				setState(464);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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

	public static class Logical_xor_expressionContext extends ParserRuleContext {
		public Logical_and_expressionContext logical_and_expression;
		public List<Logical_and_expressionContext> operands = new ArrayList<Logical_and_expressionContext>();
		public Token XOR_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<Logical_and_expressionContext> logical_and_expression() {
			return getRuleContexts(Logical_and_expressionContext.class);
		}
		public Logical_and_expressionContext logical_and_expression(int i) {
			return getRuleContext(Logical_and_expressionContext.class,i);
		}
		public List<TerminalNode> XOR_OP() { return getTokens(GLSLOtherParser.XOR_OP); }
		public TerminalNode XOR_OP(int i) {
			return getToken(GLSLOtherParser.XOR_OP, i);
		}
		public Logical_xor_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_xor_expression; }
	}

	public final Logical_xor_expressionContext logical_xor_expression() throws RecognitionException {
		Logical_xor_expressionContext _localctx = new Logical_xor_expressionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_logical_xor_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			((Logical_xor_expressionContext)_localctx).logical_and_expression = logical_and_expression();
			((Logical_xor_expressionContext)_localctx).operands.add(((Logical_xor_expressionContext)_localctx).logical_and_expression);
			setState(470);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(466);
					((Logical_xor_expressionContext)_localctx).XOR_OP = match(XOR_OP);
					((Logical_xor_expressionContext)_localctx).operators.add(((Logical_xor_expressionContext)_localctx).XOR_OP);
					setState(467);
					((Logical_xor_expressionContext)_localctx).logical_and_expression = logical_and_expression();
					((Logical_xor_expressionContext)_localctx).operands.add(((Logical_xor_expressionContext)_localctx).logical_and_expression);
					}
					} 
				}
				setState(472);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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

	public static class Logical_or_expressionContext extends ParserRuleContext {
		public Logical_xor_expressionContext logical_xor_expression;
		public List<Logical_xor_expressionContext> operands = new ArrayList<Logical_xor_expressionContext>();
		public Token OR_OP;
		public List<Token> operators = new ArrayList<Token>();
		public List<Logical_xor_expressionContext> logical_xor_expression() {
			return getRuleContexts(Logical_xor_expressionContext.class);
		}
		public Logical_xor_expressionContext logical_xor_expression(int i) {
			return getRuleContext(Logical_xor_expressionContext.class,i);
		}
		public List<TerminalNode> OR_OP() { return getTokens(GLSLOtherParser.OR_OP); }
		public TerminalNode OR_OP(int i) {
			return getToken(GLSLOtherParser.OR_OP, i);
		}
		public Logical_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_or_expression; }
	}

	public final Logical_or_expressionContext logical_or_expression() throws RecognitionException {
		Logical_or_expressionContext _localctx = new Logical_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_logical_or_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(473);
			((Logical_or_expressionContext)_localctx).logical_xor_expression = logical_xor_expression();
			((Logical_or_expressionContext)_localctx).operands.add(((Logical_or_expressionContext)_localctx).logical_xor_expression);
			setState(478);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(474);
					((Logical_or_expressionContext)_localctx).OR_OP = match(OR_OP);
					((Logical_or_expressionContext)_localctx).operators.add(((Logical_or_expressionContext)_localctx).OR_OP);
					setState(475);
					((Logical_or_expressionContext)_localctx).logical_xor_expression = logical_xor_expression();
					((Logical_or_expressionContext)_localctx).operands.add(((Logical_or_expressionContext)_localctx).logical_xor_expression);
					}
					} 
				}
				setState(480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
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

	public static class Conditional_expressionContext extends ParserRuleContext {
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public List<TerminalNode> QUERY_OP() { return getTokens(GLSLOtherParser.QUERY_OP); }
		public TerminalNode QUERY_OP(int i) {
			return getToken(GLSLOtherParser.QUERY_OP, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(GLSLOtherParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GLSLOtherParser.COLON, i);
		}
		public List<Assignment_expressionContext> assignment_expression() {
			return getRuleContexts(Assignment_expressionContext.class);
		}
		public Assignment_expressionContext assignment_expression(int i) {
			return getRuleContext(Assignment_expressionContext.class,i);
		}
		public Conditional_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional_expression; }
	}

	public final Conditional_expressionContext conditional_expression() throws RecognitionException {
		Conditional_expressionContext _localctx = new Conditional_expressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_conditional_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			logical_or_expression();
			setState(489);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(482);
					match(QUERY_OP);
					setState(483);
					expression();
					setState(484);
					match(COLON);
					setState(485);
					assignment_expression();
					}
					} 
				}
				setState(491);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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

	public static class Assignment_expressionContext extends ParserRuleContext {
		public Conditional_expressionContext conditional_expression() {
			return getRuleContext(Conditional_expressionContext.class,0);
		}
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public Assignment_operatorContext assignment_operator() {
			return getRuleContext(Assignment_operatorContext.class,0);
		}
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public Assignment_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_expression; }
	}

	public final Assignment_expressionContext assignment_expression() throws RecognitionException {
		Assignment_expressionContext _localctx = new Assignment_expressionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_assignment_expression);
		try {
			setState(497);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(492);
				conditional_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(493);
				unary_expression();
				setState(494);
				assignment_operator();
				setState(495);
				assignment_expression();
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

	public static class Assignment_operatorContext extends ParserRuleContext {
		public TerminalNode ASSIGN_OP() { return getToken(GLSLOtherParser.ASSIGN_OP, 0); }
		public TerminalNode MUL_ASSIGN() { return getToken(GLSLOtherParser.MUL_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(GLSLOtherParser.DIV_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(GLSLOtherParser.MOD_ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(GLSLOtherParser.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(GLSLOtherParser.SUB_ASSIGN, 0); }
		public TerminalNode LEFT_ASSIGN() { return getToken(GLSLOtherParser.LEFT_ASSIGN, 0); }
		public TerminalNode RIGHT_ASSIGN() { return getToken(GLSLOtherParser.RIGHT_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(GLSLOtherParser.AND_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(GLSLOtherParser.XOR_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(GLSLOtherParser.OR_ASSIGN, 0); }
		public Assignment_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_operator; }
	}

	public final Assignment_operatorContext assignment_operator() throws RecognitionException {
		Assignment_operatorContext _localctx = new Assignment_operatorContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_assignment_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL_ASSIGN) | (1L << DIV_ASSIGN) | (1L << MOD_ASSIGN) | (1L << ADD_ASSIGN) | (1L << SUB_ASSIGN) | (1L << LEFT_ASSIGN) | (1L << RIGHT_ASSIGN) | (1L << AND_ASSIGN) | (1L << XOR_ASSIGN) | (1L << OR_ASSIGN))) != 0) || _la==ASSIGN_OP) ) {
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
		public Assignment_expressionContext assignment_expression;
		public List<Assignment_expressionContext> operands = new ArrayList<Assignment_expressionContext>();
		public Token COMMA;
		public List<Token> operators = new ArrayList<Token>();
		public List<Assignment_expressionContext> assignment_expression() {
			return getRuleContexts(Assignment_expressionContext.class);
		}
		public Assignment_expressionContext assignment_expression(int i) {
			return getRuleContext(Assignment_expressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSLOtherParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSLOtherParser.COMMA, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(501);
			((ExpressionContext)_localctx).assignment_expression = assignment_expression();
			((ExpressionContext)_localctx).operands.add(((ExpressionContext)_localctx).assignment_expression);
			setState(506);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(502);
				((ExpressionContext)_localctx).COMMA = match(COMMA);
				((ExpressionContext)_localctx).operators.add(((ExpressionContext)_localctx).COMMA);
				setState(503);
				((ExpressionContext)_localctx).assignment_expression = assignment_expression();
				((ExpressionContext)_localctx).operands.add(((ExpressionContext)_localctx).assignment_expression);
				}
				}
				setState(508);
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

	public static class Constant_expressionContext extends ParserRuleContext {
		public Conditional_expressionContext conditional_expression() {
			return getRuleContext(Conditional_expressionContext.class,0);
		}
		public Constant_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant_expression; }
	}

	public final Constant_expressionContext constant_expression() throws RecognitionException {
		Constant_expressionContext _localctx = new Constant_expressionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_constant_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(509);
			conditional_expression();
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
		public Function_prototypeContext function_prototype() {
			return getRuleContext(Function_prototypeContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public Init_declarator_listContext init_declarator_list() {
			return getRuleContext(Init_declarator_listContext.class,0);
		}
		public TerminalNode PRECISION() { return getToken(GLSLOtherParser.PRECISION, 0); }
		public Precision_qualifierContext precision_qualifier() {
			return getRuleContext(Precision_qualifierContext.class,0);
		}
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Interface_blockContext interface_block() {
			return getRuleContext(Interface_blockContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_declaration);
		try {
			setState(523);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(511);
				function_prototype();
				setState(512);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(514);
				init_declarator_list(0);
				setState(515);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(517);
				match(PRECISION);
				setState(518);
				precision_qualifier();
				setState(519);
				type_specifier();
				setState(520);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(522);
				interface_block();
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

	public static class Function_prototypeContext extends ParserRuleContext {
		public Function_declaratorContext function_declarator() {
			return getRuleContext(Function_declaratorContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Function_prototypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_prototype; }
	}

	public final Function_prototypeContext function_prototype() throws RecognitionException {
		Function_prototypeContext _localctx = new Function_prototypeContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_function_prototype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(525);
			function_declarator();
			setState(526);
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

	public static class Function_declaratorContext extends ParserRuleContext {
		public Function_headerContext function_header() {
			return getRuleContext(Function_headerContext.class,0);
		}
		public Function_header_with_parametersContext function_header_with_parameters() {
			return getRuleContext(Function_header_with_parametersContext.class,0);
		}
		public Function_declaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_declarator; }
	}

	public final Function_declaratorContext function_declarator() throws RecognitionException {
		Function_declaratorContext _localctx = new Function_declaratorContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_function_declarator);
		try {
			setState(530);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(528);
				function_header();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(529);
				function_header_with_parameters(0);
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

	public static class Function_header_with_parametersContext extends ParserRuleContext {
		public Function_headerContext function_header() {
			return getRuleContext(Function_headerContext.class,0);
		}
		public Parameter_declarationContext parameter_declaration() {
			return getRuleContext(Parameter_declarationContext.class,0);
		}
		public Function_header_with_parametersContext function_header_with_parameters() {
			return getRuleContext(Function_header_with_parametersContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Function_header_with_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_header_with_parameters; }
	}

	public final Function_header_with_parametersContext function_header_with_parameters() throws RecognitionException {
		return function_header_with_parameters(0);
	}

	private Function_header_with_parametersContext function_header_with_parameters(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Function_header_with_parametersContext _localctx = new Function_header_with_parametersContext(_ctx, _parentState);
		Function_header_with_parametersContext _prevctx = _localctx;
		int _startState = 80;
		enterRecursionRule(_localctx, 80, RULE_function_header_with_parameters, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(533);
			function_header();
			setState(534);
			parameter_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(541);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Function_header_with_parametersContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_function_header_with_parameters);
					setState(536);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(537);
					match(COMMA);
					setState(538);
					parameter_declaration();
					}
					} 
				}
				setState(543);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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

	public static class Function_headerContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public Function_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_header; }
	}

	public final Function_headerContext function_header() throws RecognitionException {
		Function_headerContext _localctx = new Function_headerContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_function_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(544);
			fully_specified_type();
			setState(545);
			variable_identifier();
			setState(546);
			match(LPAREN);
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

	public static class Parameter_declaratorContext extends ParserRuleContext {
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Parameter_declaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_declarator; }
	}

	public final Parameter_declaratorContext parameter_declarator() throws RecognitionException {
		Parameter_declaratorContext _localctx = new Parameter_declaratorContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_parameter_declarator);
		try {
			setState(555);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(548);
				type_specifier();
				setState(549);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(551);
				type_specifier();
				setState(552);
				match(IDENTIFIER);
				setState(553);
				array_specifier(0);
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

	public static class Parameter_declarationContext extends ParserRuleContext {
		public Parameter_qualifierContext parameter_qualifier() {
			return getRuleContext(Parameter_qualifierContext.class,0);
		}
		public Parameter_declaratorContext parameter_declarator() {
			return getRuleContext(Parameter_declaratorContext.class,0);
		}
		public Parameter_type_specifierContext parameter_type_specifier() {
			return getRuleContext(Parameter_type_specifierContext.class,0);
		}
		public Parameter_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_declaration; }
	}

	public final Parameter_declarationContext parameter_declaration() throws RecognitionException {
		Parameter_declarationContext _localctx = new Parameter_declarationContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_parameter_declaration);
		try {
			setState(563);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(557);
				parameter_qualifier();
				setState(558);
				parameter_declarator();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(560);
				parameter_qualifier();
				setState(561);
				parameter_type_specifier();
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

	public static class Parameter_qualifierContext extends ParserRuleContext {
		public TerminalNode CONST_TOK() { return getToken(GLSLOtherParser.CONST_TOK, 0); }
		public Parameter_qualifierContext parameter_qualifier() {
			return getRuleContext(Parameter_qualifierContext.class,0);
		}
		public TerminalNode PRECISE() { return getToken(GLSLOtherParser.PRECISE, 0); }
		public Parameter_direction_qualifierContext parameter_direction_qualifier() {
			return getRuleContext(Parameter_direction_qualifierContext.class,0);
		}
		public Precision_qualifierContext precision_qualifier() {
			return getRuleContext(Precision_qualifierContext.class,0);
		}
		public Parameter_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_qualifier; }
	}

	public final Parameter_qualifierContext parameter_qualifier() throws RecognitionException {
		Parameter_qualifierContext _localctx = new Parameter_qualifierContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_parameter_qualifier);
		try {
			setState(576);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case STRUCT:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case CONST_TOK:
				enterOuterAlt(_localctx, 2);
				{
				setState(566);
				match(CONST_TOK);
				setState(567);
				parameter_qualifier();
				}
				break;
			case PRECISE:
				enterOuterAlt(_localctx, 3);
				{
				setState(568);
				match(PRECISE);
				setState(569);
				parameter_qualifier();
				}
				break;
			case IN_TOK:
			case OUT_TOK:
			case INOUT_TOK:
				enterOuterAlt(_localctx, 4);
				{
				setState(570);
				parameter_direction_qualifier();
				setState(571);
				parameter_qualifier();
				}
				break;
			case HIGHP:
			case MEDIUMP:
			case LOWP:
				enterOuterAlt(_localctx, 5);
				{
				setState(573);
				precision_qualifier();
				setState(574);
				parameter_qualifier();
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

	public static class Parameter_direction_qualifierContext extends ParserRuleContext {
		public TerminalNode IN_TOK() { return getToken(GLSLOtherParser.IN_TOK, 0); }
		public TerminalNode OUT_TOK() { return getToken(GLSLOtherParser.OUT_TOK, 0); }
		public TerminalNode INOUT_TOK() { return getToken(GLSLOtherParser.INOUT_TOK, 0); }
		public Parameter_direction_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_direction_qualifier; }
	}

	public final Parameter_direction_qualifierContext parameter_direction_qualifier() throws RecognitionException {
		Parameter_direction_qualifierContext _localctx = new Parameter_direction_qualifierContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_parameter_direction_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(578);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IN_TOK) | (1L << OUT_TOK) | (1L << INOUT_TOK))) != 0)) ) {
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

	public static class Parameter_type_specifierContext extends ParserRuleContext {
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Parameter_type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_type_specifier; }
	}

	public final Parameter_type_specifierContext parameter_type_specifier() throws RecognitionException {
		Parameter_type_specifierContext _localctx = new Parameter_type_specifierContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_parameter_type_specifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(580);
			type_specifier();
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

	public static class Init_declarator_listContext extends ParserRuleContext {
		public Single_declarationContext single_declaration() {
			return getRuleContext(Single_declarationContext.class,0);
		}
		public Init_declarator_listContext init_declarator_list() {
			return getRuleContext(Init_declarator_listContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(GLSLOtherParser.ASSIGN_OP, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public Init_declarator_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init_declarator_list; }
	}

	public final Init_declarator_listContext init_declarator_list() throws RecognitionException {
		return init_declarator_list(0);
	}

	private Init_declarator_listContext init_declarator_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Init_declarator_listContext _localctx = new Init_declarator_listContext(_ctx, _parentState);
		Init_declarator_listContext _prevctx = _localctx;
		int _startState = 94;
		enterRecursionRule(_localctx, 94, RULE_init_declarator_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(583);
			single_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(606);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(604);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
					case 1:
						{
						_localctx = new Init_declarator_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_init_declarator_list);
						setState(585);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(586);
						match(COMMA);
						setState(587);
						match(IDENTIFIER);
						}
						break;
					case 2:
						{
						_localctx = new Init_declarator_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_init_declarator_list);
						setState(588);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(589);
						match(COMMA);
						setState(590);
						match(IDENTIFIER);
						setState(591);
						array_specifier(0);
						}
						break;
					case 3:
						{
						_localctx = new Init_declarator_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_init_declarator_list);
						setState(592);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(593);
						match(COMMA);
						setState(594);
						match(IDENTIFIER);
						setState(595);
						array_specifier(0);
						setState(596);
						match(ASSIGN_OP);
						setState(597);
						initializer();
						}
						break;
					case 4:
						{
						_localctx = new Init_declarator_listContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_init_declarator_list);
						setState(599);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(600);
						match(COMMA);
						setState(601);
						match(IDENTIFIER);
						setState(602);
						match(ASSIGN_OP);
						setState(603);
						initializer();
						}
						break;
					}
					} 
				}
				setState(608);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
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

	public static class Single_declarationContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(GLSLOtherParser.ASSIGN_OP, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public TerminalNode INVARIANT() { return getToken(GLSLOtherParser.INVARIANT, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode PRECISE() { return getToken(GLSLOtherParser.PRECISE, 0); }
		public Single_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_declaration; }
	}

	public final Single_declarationContext single_declaration() throws RecognitionException {
		Single_declarationContext _localctx = new Single_declarationContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_single_declaration);
		try {
			setState(632);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(609);
				fully_specified_type();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(610);
				fully_specified_type();
				setState(611);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(613);
				fully_specified_type();
				setState(614);
				match(IDENTIFIER);
				setState(615);
				array_specifier(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(617);
				fully_specified_type();
				setState(618);
				match(IDENTIFIER);
				setState(619);
				array_specifier(0);
				setState(620);
				match(ASSIGN_OP);
				setState(621);
				initializer();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(623);
				fully_specified_type();
				setState(624);
				match(IDENTIFIER);
				setState(625);
				match(ASSIGN_OP);
				setState(626);
				initializer();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(628);
				match(INVARIANT);
				setState(629);
				variable_identifier();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(630);
				match(PRECISE);
				setState(631);
				variable_identifier();
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

	public static class Fully_specified_typeContext extends ParserRuleContext {
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Type_qualifierContext type_qualifier() {
			return getRuleContext(Type_qualifierContext.class,0);
		}
		public Fully_specified_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fully_specified_type; }
	}

	public final Fully_specified_typeContext fully_specified_type() throws RecognitionException {
		Fully_specified_typeContext _localctx = new Fully_specified_typeContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_fully_specified_type);
		try {
			setState(638);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case STRUCT:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(634);
				type_specifier();
				}
				break;
			case UNIFORM:
			case IN_TOK:
			case OUT_TOK:
			case HIGHP:
			case MEDIUMP:
			case LOWP:
			case CONST_TOK:
			case PRECISE:
			case INVARIANT:
			case SMOOTH:
			case FLAT:
			case NOPERSPECTIVE:
			case CENTROID:
			case SAMPLE:
			case ATTRIBUTE:
			case COHERENT:
			case VOLATILE:
			case RESTRICT:
			case VARYING:
			case READONLY:
			case WRITEONLY:
			case SHARED:
			case LAYOUT_TOK:
				enterOuterAlt(_localctx, 2);
				{
				setState(635);
				type_qualifier();
				setState(636);
				type_specifier();
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

	public static class Layout_qualifierContext extends ParserRuleContext {
		public TerminalNode LAYOUT_TOK() { return getToken(GLSLOtherParser.LAYOUT_TOK, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public Layout_qualifier_id_listContext layout_qualifier_id_list() {
			return getRuleContext(Layout_qualifier_id_listContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Layout_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_qualifier; }
	}

	public final Layout_qualifierContext layout_qualifier() throws RecognitionException {
		Layout_qualifierContext _localctx = new Layout_qualifierContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_layout_qualifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(640);
			match(LAYOUT_TOK);
			setState(641);
			match(LPAREN);
			setState(642);
			layout_qualifier_id_list(0);
			setState(643);
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

	public static class Layout_qualifier_id_listContext extends ParserRuleContext {
		public Layout_qualifier_idContext layout_qualifier_id() {
			return getRuleContext(Layout_qualifier_idContext.class,0);
		}
		public Layout_qualifier_id_listContext layout_qualifier_id_list() {
			return getRuleContext(Layout_qualifier_id_listContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Layout_qualifier_id_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_qualifier_id_list; }
	}

	public final Layout_qualifier_id_listContext layout_qualifier_id_list() throws RecognitionException {
		return layout_qualifier_id_list(0);
	}

	private Layout_qualifier_id_listContext layout_qualifier_id_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Layout_qualifier_id_listContext _localctx = new Layout_qualifier_id_listContext(_ctx, _parentState);
		Layout_qualifier_id_listContext _prevctx = _localctx;
		int _startState = 102;
		enterRecursionRule(_localctx, 102, RULE_layout_qualifier_id_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(646);
			layout_qualifier_id();
			}
			_ctx.stop = _input.LT(-1);
			setState(653);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Layout_qualifier_id_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_layout_qualifier_id_list);
					setState(648);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(649);
					match(COMMA);
					setState(650);
					layout_qualifier_id();
					}
					} 
				}
				setState(655);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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

	public static class Integer_constantContext extends ParserRuleContext {
		public TerminalNode INTCONSTANT() { return getToken(GLSLOtherParser.INTCONSTANT, 0); }
		public TerminalNode UINTCONSTANT() { return getToken(GLSLOtherParser.UINTCONSTANT, 0); }
		public Integer_constantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_constant; }
	}

	public final Integer_constantContext integer_constant() throws RecognitionException {
		Integer_constantContext _localctx = new Integer_constantContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_integer_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(656);
			_la = _input.LA(1);
			if ( !(_la==INTCONSTANT || _la==UINTCONSTANT) ) {
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

	public static class Layout_qualifier_idContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSLOtherParser.ASSIGN_OP, 0); }
		public Integer_constantContext integer_constant() {
			return getRuleContext(Integer_constantContext.class,0);
		}
		public Interface_block_layout_qualifierContext interface_block_layout_qualifier() {
			return getRuleContext(Interface_block_layout_qualifierContext.class,0);
		}
		public Layout_qualifier_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_qualifier_id; }
	}

	public final Layout_qualifier_idContext layout_qualifier_id() throws RecognitionException {
		Layout_qualifier_idContext _localctx = new Layout_qualifier_idContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_layout_qualifier_id);
		try {
			setState(663);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(658);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(659);
				match(IDENTIFIER);
				setState(660);
				match(ASSIGN_OP);
				setState(661);
				integer_constant();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(662);
				interface_block_layout_qualifier();
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

	public static class Interface_block_layout_qualifierContext extends ParserRuleContext {
		public TerminalNode ROW_MAJOR() { return getToken(GLSLOtherParser.ROW_MAJOR, 0); }
		public TerminalNode PACKED_TOK() { return getToken(GLSLOtherParser.PACKED_TOK, 0); }
		public Interface_block_layout_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interface_block_layout_qualifier; }
	}

	public final Interface_block_layout_qualifierContext interface_block_layout_qualifier() throws RecognitionException {
		Interface_block_layout_qualifierContext _localctx = new Interface_block_layout_qualifierContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_interface_block_layout_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(665);
			_la = _input.LA(1);
			if ( !(_la==ROW_MAJOR || _la==PACKED_TOK) ) {
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

	public static class Interpolation_qualifierContext extends ParserRuleContext {
		public TerminalNode SMOOTH() { return getToken(GLSLOtherParser.SMOOTH, 0); }
		public TerminalNode FLAT() { return getToken(GLSLOtherParser.FLAT, 0); }
		public TerminalNode NOPERSPECTIVE() { return getToken(GLSLOtherParser.NOPERSPECTIVE, 0); }
		public Interpolation_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interpolation_qualifier; }
	}

	public final Interpolation_qualifierContext interpolation_qualifier() throws RecognitionException {
		Interpolation_qualifierContext _localctx = new Interpolation_qualifierContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_interpolation_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(667);
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

	public static class Type_qualifierContext extends ParserRuleContext {
		public TerminalNode INVARIANT() { return getToken(GLSLOtherParser.INVARIANT, 0); }
		public TerminalNode PRECISE() { return getToken(GLSLOtherParser.PRECISE, 0); }
		public Auxiliary_storage_qualifierContext auxiliary_storage_qualifier() {
			return getRuleContext(Auxiliary_storage_qualifierContext.class,0);
		}
		public Storage_qualifierContext storage_qualifier() {
			return getRuleContext(Storage_qualifierContext.class,0);
		}
		public Interpolation_qualifierContext interpolation_qualifier() {
			return getRuleContext(Interpolation_qualifierContext.class,0);
		}
		public Layout_qualifierContext layout_qualifier() {
			return getRuleContext(Layout_qualifierContext.class,0);
		}
		public Precision_qualifierContext precision_qualifier() {
			return getRuleContext(Precision_qualifierContext.class,0);
		}
		public Type_qualifierContext type_qualifier() {
			return getRuleContext(Type_qualifierContext.class,0);
		}
		public Type_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_qualifier; }
	}

	public final Type_qualifierContext type_qualifier() throws RecognitionException {
		Type_qualifierContext _localctx = new Type_qualifierContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_type_qualifier);
		try {
			setState(695);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(669);
				match(INVARIANT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(670);
				match(PRECISE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(671);
				auxiliary_storage_qualifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(672);
				storage_qualifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(673);
				interpolation_qualifier();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(674);
				layout_qualifier();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(675);
				precision_qualifier();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(676);
				match(PRECISE);
				setState(677);
				type_qualifier();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(678);
				match(INVARIANT);
				setState(679);
				type_qualifier();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(680);
				interpolation_qualifier();
				setState(681);
				type_qualifier();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(683);
				layout_qualifier();
				setState(684);
				type_qualifier();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(686);
				auxiliary_storage_qualifier();
				setState(687);
				type_qualifier();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(689);
				storage_qualifier();
				setState(690);
				type_qualifier();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(692);
				precision_qualifier();
				setState(693);
				type_qualifier();
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

	public static class Auxiliary_storage_qualifierContext extends ParserRuleContext {
		public TerminalNode CENTROID() { return getToken(GLSLOtherParser.CENTROID, 0); }
		public TerminalNode SAMPLE() { return getToken(GLSLOtherParser.SAMPLE, 0); }
		public Auxiliary_storage_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_auxiliary_storage_qualifier; }
	}

	public final Auxiliary_storage_qualifierContext auxiliary_storage_qualifier() throws RecognitionException {
		Auxiliary_storage_qualifierContext _localctx = new Auxiliary_storage_qualifierContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_auxiliary_storage_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(697);
			_la = _input.LA(1);
			if ( !(_la==CENTROID || _la==SAMPLE) ) {
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

	public static class Storage_qualifierContext extends ParserRuleContext {
		public TerminalNode CONST_TOK() { return getToken(GLSLOtherParser.CONST_TOK, 0); }
		public TerminalNode ATTRIBUTE() { return getToken(GLSLOtherParser.ATTRIBUTE, 0); }
		public TerminalNode VARYING() { return getToken(GLSLOtherParser.VARYING, 0); }
		public TerminalNode IN_TOK() { return getToken(GLSLOtherParser.IN_TOK, 0); }
		public TerminalNode OUT_TOK() { return getToken(GLSLOtherParser.OUT_TOK, 0); }
		public TerminalNode UNIFORM() { return getToken(GLSLOtherParser.UNIFORM, 0); }
		public TerminalNode COHERENT() { return getToken(GLSLOtherParser.COHERENT, 0); }
		public TerminalNode VOLATILE() { return getToken(GLSLOtherParser.VOLATILE, 0); }
		public TerminalNode RESTRICT() { return getToken(GLSLOtherParser.RESTRICT, 0); }
		public TerminalNode READONLY() { return getToken(GLSLOtherParser.READONLY, 0); }
		public TerminalNode WRITEONLY() { return getToken(GLSLOtherParser.WRITEONLY, 0); }
		public TerminalNode SHARED() { return getToken(GLSLOtherParser.SHARED, 0); }
		public Storage_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storage_qualifier; }
	}

	public final Storage_qualifierContext storage_qualifier() throws RecognitionException {
		Storage_qualifierContext _localctx = new Storage_qualifierContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_storage_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << IN_TOK) | (1L << OUT_TOK) | (1L << CONST_TOK) | (1L << ATTRIBUTE) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << VARYING) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED))) != 0)) ) {
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

	public static class Array_specifierContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(GLSLOtherParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(GLSLOtherParser.RBRACKET, 0); }
		public Constant_expressionContext constant_expression() {
			return getRuleContext(Constant_expressionContext.class,0);
		}
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Array_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_specifier; }
	}

	public final Array_specifierContext array_specifier() throws RecognitionException {
		return array_specifier(0);
	}

	private Array_specifierContext array_specifier(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Array_specifierContext _localctx = new Array_specifierContext(_ctx, _parentState);
		Array_specifierContext _prevctx = _localctx;
		int _startState = 118;
		enterRecursionRule(_localctx, 118, RULE_array_specifier, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(708);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(702);
				match(LBRACKET);
				setState(703);
				match(RBRACKET);
				}
				break;
			case 2:
				{
				setState(704);
				match(LBRACKET);
				setState(705);
				constant_expression();
				setState(706);
				match(RBRACKET);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(720);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(718);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
					case 1:
						{
						_localctx = new Array_specifierContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_array_specifier);
						setState(710);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(711);
						match(LBRACKET);
						setState(712);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new Array_specifierContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_array_specifier);
						setState(713);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(714);
						match(LBRACKET);
						setState(715);
						constant_expression();
						setState(716);
						match(RBRACKET);
						}
						break;
					}
					} 
				}
				setState(722);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
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

	public static class Type_specifierContext extends ParserRuleContext {
		public Type_specifier_nonarrayContext type_specifier_nonarray() {
			return getRuleContext(Type_specifier_nonarrayContext.class,0);
		}
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier; }
	}

	public final Type_specifierContext type_specifier() throws RecognitionException {
		Type_specifierContext _localctx = new Type_specifierContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_type_specifier);
		try {
			setState(727);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(723);
				type_specifier_nonarray();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(724);
				type_specifier_nonarray();
				setState(725);
				array_specifier(0);
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

	public static class Type_specifier_nonarrayContext extends ParserRuleContext {
		public Builtin_type_specifier_nonarrayContext builtin_type_specifier_nonarray() {
			return getRuleContext(Builtin_type_specifier_nonarrayContext.class,0);
		}
		public Struct_specifierContext struct_specifier() {
			return getRuleContext(Struct_specifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Type_specifier_nonarrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_nonarray; }
	}

	public final Type_specifier_nonarrayContext type_specifier_nonarray() throws RecognitionException {
		Type_specifier_nonarrayContext _localctx = new Type_specifier_nonarrayContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_type_specifier_nonarray);
		try {
			setState(732);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(729);
				builtin_type_specifier_nonarray();
				}
				break;
			case STRUCT:
				enterOuterAlt(_localctx, 2);
				{
				setState(730);
				struct_specifier();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(731);
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

	public static class Builtin_type_specifier_nonarrayContext extends ParserRuleContext {
		public TerminalNode VOID_TOK() { return getToken(GLSLOtherParser.VOID_TOK, 0); }
		public TerminalNode FLOAT_TOK() { return getToken(GLSLOtherParser.FLOAT_TOK, 0); }
		public TerminalNode INT_TOK() { return getToken(GLSLOtherParser.INT_TOK, 0); }
		public TerminalNode UINT_TOK() { return getToken(GLSLOtherParser.UINT_TOK, 0); }
		public TerminalNode BOOL_TOK() { return getToken(GLSLOtherParser.BOOL_TOK, 0); }
		public TerminalNode VEC2() { return getToken(GLSLOtherParser.VEC2, 0); }
		public TerminalNode VEC3() { return getToken(GLSLOtherParser.VEC3, 0); }
		public TerminalNode VEC4() { return getToken(GLSLOtherParser.VEC4, 0); }
		public TerminalNode BVEC2() { return getToken(GLSLOtherParser.BVEC2, 0); }
		public TerminalNode BVEC3() { return getToken(GLSLOtherParser.BVEC3, 0); }
		public TerminalNode BVEC4() { return getToken(GLSLOtherParser.BVEC4, 0); }
		public TerminalNode IVEC2() { return getToken(GLSLOtherParser.IVEC2, 0); }
		public TerminalNode IVEC3() { return getToken(GLSLOtherParser.IVEC3, 0); }
		public TerminalNode IVEC4() { return getToken(GLSLOtherParser.IVEC4, 0); }
		public TerminalNode UVEC2() { return getToken(GLSLOtherParser.UVEC2, 0); }
		public TerminalNode UVEC3() { return getToken(GLSLOtherParser.UVEC3, 0); }
		public TerminalNode UVEC4() { return getToken(GLSLOtherParser.UVEC4, 0); }
		public TerminalNode MAT2X2() { return getToken(GLSLOtherParser.MAT2X2, 0); }
		public TerminalNode MAT2X3() { return getToken(GLSLOtherParser.MAT2X3, 0); }
		public TerminalNode MAT2X4() { return getToken(GLSLOtherParser.MAT2X4, 0); }
		public TerminalNode MAT3X2() { return getToken(GLSLOtherParser.MAT3X2, 0); }
		public TerminalNode MAT3X3() { return getToken(GLSLOtherParser.MAT3X3, 0); }
		public TerminalNode MAT3X4() { return getToken(GLSLOtherParser.MAT3X4, 0); }
		public TerminalNode MAT4X2() { return getToken(GLSLOtherParser.MAT4X2, 0); }
		public TerminalNode MAT4X3() { return getToken(GLSLOtherParser.MAT4X3, 0); }
		public TerminalNode MAT4X4() { return getToken(GLSLOtherParser.MAT4X4, 0); }
		public TerminalNode SAMPLER1D() { return getToken(GLSLOtherParser.SAMPLER1D, 0); }
		public TerminalNode SAMPLER2D() { return getToken(GLSLOtherParser.SAMPLER2D, 0); }
		public TerminalNode SAMPLER2DRECT() { return getToken(GLSLOtherParser.SAMPLER2DRECT, 0); }
		public TerminalNode SAMPLER3D() { return getToken(GLSLOtherParser.SAMPLER3D, 0); }
		public TerminalNode SAMPLERCUBE() { return getToken(GLSLOtherParser.SAMPLERCUBE, 0); }
		public TerminalNode SAMPLEREXTERNALOES() { return getToken(GLSLOtherParser.SAMPLEREXTERNALOES, 0); }
		public TerminalNode SAMPLER1DSHADOW() { return getToken(GLSLOtherParser.SAMPLER1DSHADOW, 0); }
		public TerminalNode SAMPLER2DSHADOW() { return getToken(GLSLOtherParser.SAMPLER2DSHADOW, 0); }
		public TerminalNode SAMPLER2DRECTSHADOW() { return getToken(GLSLOtherParser.SAMPLER2DRECTSHADOW, 0); }
		public TerminalNode SAMPLERCUBESHADOW() { return getToken(GLSLOtherParser.SAMPLERCUBESHADOW, 0); }
		public TerminalNode SAMPLER1DARRAY() { return getToken(GLSLOtherParser.SAMPLER1DARRAY, 0); }
		public TerminalNode SAMPLER2DARRAY() { return getToken(GLSLOtherParser.SAMPLER2DARRAY, 0); }
		public TerminalNode SAMPLER1DARRAYSHADOW() { return getToken(GLSLOtherParser.SAMPLER1DARRAYSHADOW, 0); }
		public TerminalNode SAMPLER2DARRAYSHADOW() { return getToken(GLSLOtherParser.SAMPLER2DARRAYSHADOW, 0); }
		public TerminalNode SAMPLERBUFFER() { return getToken(GLSLOtherParser.SAMPLERBUFFER, 0); }
		public TerminalNode SAMPLERCUBEARRAY() { return getToken(GLSLOtherParser.SAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLERCUBEARRAYSHADOW() { return getToken(GLSLOtherParser.SAMPLERCUBEARRAYSHADOW, 0); }
		public TerminalNode ISAMPLER1D() { return getToken(GLSLOtherParser.ISAMPLER1D, 0); }
		public TerminalNode ISAMPLER2D() { return getToken(GLSLOtherParser.ISAMPLER2D, 0); }
		public TerminalNode ISAMPLER2DRECT() { return getToken(GLSLOtherParser.ISAMPLER2DRECT, 0); }
		public TerminalNode ISAMPLER3D() { return getToken(GLSLOtherParser.ISAMPLER3D, 0); }
		public TerminalNode ISAMPLERCUBE() { return getToken(GLSLOtherParser.ISAMPLERCUBE, 0); }
		public TerminalNode ISAMPLER1DARRAY() { return getToken(GLSLOtherParser.ISAMPLER1DARRAY, 0); }
		public TerminalNode ISAMPLER2DARRAY() { return getToken(GLSLOtherParser.ISAMPLER2DARRAY, 0); }
		public TerminalNode ISAMPLERBUFFER() { return getToken(GLSLOtherParser.ISAMPLERBUFFER, 0); }
		public TerminalNode ISAMPLERCUBEARRAY() { return getToken(GLSLOtherParser.ISAMPLERCUBEARRAY, 0); }
		public TerminalNode USAMPLER1D() { return getToken(GLSLOtherParser.USAMPLER1D, 0); }
		public TerminalNode USAMPLER2D() { return getToken(GLSLOtherParser.USAMPLER2D, 0); }
		public TerminalNode USAMPLER2DRECT() { return getToken(GLSLOtherParser.USAMPLER2DRECT, 0); }
		public TerminalNode USAMPLER3D() { return getToken(GLSLOtherParser.USAMPLER3D, 0); }
		public TerminalNode USAMPLERCUBE() { return getToken(GLSLOtherParser.USAMPLERCUBE, 0); }
		public TerminalNode USAMPLER1DARRAY() { return getToken(GLSLOtherParser.USAMPLER1DARRAY, 0); }
		public TerminalNode USAMPLER2DARRAY() { return getToken(GLSLOtherParser.USAMPLER2DARRAY, 0); }
		public TerminalNode USAMPLERBUFFER() { return getToken(GLSLOtherParser.USAMPLERBUFFER, 0); }
		public TerminalNode USAMPLERCUBEARRAY() { return getToken(GLSLOtherParser.USAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLER2DMS() { return getToken(GLSLOtherParser.SAMPLER2DMS, 0); }
		public TerminalNode ISAMPLER2DMS() { return getToken(GLSLOtherParser.ISAMPLER2DMS, 0); }
		public TerminalNode USAMPLER2DMS() { return getToken(GLSLOtherParser.USAMPLER2DMS, 0); }
		public TerminalNode SAMPLER2DMSARRAY() { return getToken(GLSLOtherParser.SAMPLER2DMSARRAY, 0); }
		public TerminalNode ISAMPLER2DMSARRAY() { return getToken(GLSLOtherParser.ISAMPLER2DMSARRAY, 0); }
		public TerminalNode USAMPLER2DMSARRAY() { return getToken(GLSLOtherParser.USAMPLER2DMSARRAY, 0); }
		public TerminalNode IMAGE1D() { return getToken(GLSLOtherParser.IMAGE1D, 0); }
		public TerminalNode IMAGE2D() { return getToken(GLSLOtherParser.IMAGE2D, 0); }
		public TerminalNode IMAGE3D() { return getToken(GLSLOtherParser.IMAGE3D, 0); }
		public TerminalNode IMAGE2DRECT() { return getToken(GLSLOtherParser.IMAGE2DRECT, 0); }
		public TerminalNode IMAGECUBE() { return getToken(GLSLOtherParser.IMAGECUBE, 0); }
		public TerminalNode IMAGEBUFFER() { return getToken(GLSLOtherParser.IMAGEBUFFER, 0); }
		public TerminalNode IMAGE1DARRAY() { return getToken(GLSLOtherParser.IMAGE1DARRAY, 0); }
		public TerminalNode IMAGE2DARRAY() { return getToken(GLSLOtherParser.IMAGE2DARRAY, 0); }
		public TerminalNode IMAGECUBEARRAY() { return getToken(GLSLOtherParser.IMAGECUBEARRAY, 0); }
		public TerminalNode IMAGE2DMS() { return getToken(GLSLOtherParser.IMAGE2DMS, 0); }
		public TerminalNode IMAGE2DMSARRAY() { return getToken(GLSLOtherParser.IMAGE2DMSARRAY, 0); }
		public TerminalNode IIMAGE1D() { return getToken(GLSLOtherParser.IIMAGE1D, 0); }
		public TerminalNode IIMAGE2D() { return getToken(GLSLOtherParser.IIMAGE2D, 0); }
		public TerminalNode IIMAGE3D() { return getToken(GLSLOtherParser.IIMAGE3D, 0); }
		public TerminalNode IIMAGE2DRECT() { return getToken(GLSLOtherParser.IIMAGE2DRECT, 0); }
		public TerminalNode IIMAGECUBE() { return getToken(GLSLOtherParser.IIMAGECUBE, 0); }
		public TerminalNode IIMAGEBUFFER() { return getToken(GLSLOtherParser.IIMAGEBUFFER, 0); }
		public TerminalNode IIMAGE1DARRAY() { return getToken(GLSLOtherParser.IIMAGE1DARRAY, 0); }
		public TerminalNode IIMAGE2DARRAY() { return getToken(GLSLOtherParser.IIMAGE2DARRAY, 0); }
		public TerminalNode IIMAGECUBEARRAY() { return getToken(GLSLOtherParser.IIMAGECUBEARRAY, 0); }
		public TerminalNode IIMAGE2DMS() { return getToken(GLSLOtherParser.IIMAGE2DMS, 0); }
		public TerminalNode IIMAGE2DMSARRAY() { return getToken(GLSLOtherParser.IIMAGE2DMSARRAY, 0); }
		public TerminalNode UIMAGE1D() { return getToken(GLSLOtherParser.UIMAGE1D, 0); }
		public TerminalNode UIMAGE2D() { return getToken(GLSLOtherParser.UIMAGE2D, 0); }
		public TerminalNode UIMAGE3D() { return getToken(GLSLOtherParser.UIMAGE3D, 0); }
		public TerminalNode UIMAGE2DRECT() { return getToken(GLSLOtherParser.UIMAGE2DRECT, 0); }
		public TerminalNode UIMAGECUBE() { return getToken(GLSLOtherParser.UIMAGECUBE, 0); }
		public TerminalNode UIMAGEBUFFER() { return getToken(GLSLOtherParser.UIMAGEBUFFER, 0); }
		public TerminalNode UIMAGE1DARRAY() { return getToken(GLSLOtherParser.UIMAGE1DARRAY, 0); }
		public TerminalNode UIMAGE2DARRAY() { return getToken(GLSLOtherParser.UIMAGE2DARRAY, 0); }
		public TerminalNode UIMAGECUBEARRAY() { return getToken(GLSLOtherParser.UIMAGECUBEARRAY, 0); }
		public TerminalNode UIMAGE2DMS() { return getToken(GLSLOtherParser.UIMAGE2DMS, 0); }
		public TerminalNode UIMAGE2DMSARRAY() { return getToken(GLSLOtherParser.UIMAGE2DMSARRAY, 0); }
		public TerminalNode ATOMIC_UINT() { return getToken(GLSLOtherParser.ATOMIC_UINT, 0); }
		public Builtin_type_specifier_nonarrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtin_type_specifier_nonarray; }
	}

	public final Builtin_type_specifier_nonarrayContext builtin_type_specifier_nonarray() throws RecognitionException {
		Builtin_type_specifier_nonarrayContext _localctx = new Builtin_type_specifier_nonarrayContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_builtin_type_specifier_nonarray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(734);
			_la = _input.LA(1);
			if ( !(_la==VOID_TOK || _la==FLOAT_TOK || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (INT_TOK - 64)) | (1L << (UINT_TOK - 64)) | (1L << (BOOL_TOK - 64)) | (1L << (SAMPLERCUBE - 64)) | (1L << (SAMPLERCUBESHADOW - 64)) | (1L << (SAMPLERBUFFER - 64)) | (1L << (SAMPLERCUBEARRAY - 64)) | (1L << (SAMPLERCUBEARRAYSHADOW - 64)) | (1L << (ISAMPLERCUBE - 64)) | (1L << (ISAMPLERBUFFER - 64)) | (1L << (ISAMPLERCUBEARRAY - 64)) | (1L << (USAMPLERCUBE - 64)) | (1L << (USAMPLERBUFFER - 64)) | (1L << (USAMPLERCUBEARRAY - 64)) | (1L << (IMAGECUBE - 64)) | (1L << (IMAGEBUFFER - 64)) | (1L << (IMAGECUBEARRAY - 64)) | (1L << (IIMAGECUBE - 64)) | (1L << (IIMAGEBUFFER - 64)) | (1L << (IIMAGECUBEARRAY - 64)) | (1L << (UIMAGECUBE - 64)) | (1L << (UIMAGEBUFFER - 64)) | (1L << (UIMAGECUBEARRAY - 64)) | (1L << (ATOMIC_UINT - 64)) | (1L << (VEC2 - 64)) | (1L << (VEC3 - 64)) | (1L << (VEC4 - 64)) | (1L << (BVEC2 - 64)) | (1L << (BVEC3 - 64)) | (1L << (BVEC4 - 64)) | (1L << (IVEC2 - 64)) | (1L << (IVEC3 - 64)) | (1L << (IVEC4 - 64)) | (1L << (UVEC2 - 64)) | (1L << (UVEC3 - 64)) | (1L << (UVEC4 - 64)) | (1L << (MAT2X2 - 64)) | (1L << (MAT2X3 - 64)) | (1L << (MAT2X4 - 64)) | (1L << (MAT3X2 - 64)) | (1L << (MAT3X3 - 64)) | (1L << (MAT3X4 - 64)) | (1L << (MAT4X2 - 64)) | (1L << (MAT4X3 - 64)) | (1L << (MAT4X4 - 64)) | (1L << (IMAGE1D - 64)) | (1L << (IMAGE2D - 64)) | (1L << (IMAGE3D - 64)) | (1L << (UIMAGE1D - 64)) | (1L << (UIMAGE2D - 64)) | (1L << (UIMAGE3D - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (IIMAGE1D - 128)) | (1L << (IIMAGE2D - 128)) | (1L << (IIMAGE3D - 128)) | (1L << (SAMPLER1D - 128)) | (1L << (SAMPLER2D - 128)) | (1L << (SAMPLER3D - 128)) | (1L << (SAMPLER2DRECT - 128)) | (1L << (SAMPLEREXTERNALOES - 128)) | (1L << (SAMPLER1DSHADOW - 128)) | (1L << (SAMPLER2DSHADOW - 128)) | (1L << (SAMPLER2DRECTSHADOW - 128)) | (1L << (SAMPLER1DARRAY - 128)) | (1L << (SAMPLER2DARRAY - 128)) | (1L << (SAMPLER1DARRAYSHADOW - 128)) | (1L << (SAMPLER2DARRAYSHADOW - 128)) | (1L << (ISAMPLER1D - 128)) | (1L << (ISAMPLER2D - 128)) | (1L << (ISAMPLER2DRECT - 128)) | (1L << (ISAMPLER3D - 128)) | (1L << (ISAMPLER1DARRAY - 128)) | (1L << (ISAMPLER2DARRAY - 128)) | (1L << (USAMPLER1D - 128)) | (1L << (USAMPLER2D - 128)) | (1L << (USAMPLER2DRECT - 128)) | (1L << (USAMPLER3D - 128)) | (1L << (USAMPLER1DARRAY - 128)) | (1L << (USAMPLER2DARRAY - 128)) | (1L << (SAMPLER2DMS - 128)) | (1L << (ISAMPLER2DMS - 128)) | (1L << (USAMPLER2DMS - 128)) | (1L << (SAMPLER2DMSARRAY - 128)) | (1L << (ISAMPLER2DMSARRAY - 128)) | (1L << (USAMPLER2DMSARRAY - 128)) | (1L << (IMAGE2DRECT - 128)) | (1L << (IMAGE1DARRAY - 128)) | (1L << (IMAGE2DARRAY - 128)) | (1L << (IMAGE2DMS - 128)) | (1L << (IMAGE2DMSARRAY - 128)) | (1L << (IIMAGE2DRECT - 128)) | (1L << (IIMAGE1DARRAY - 128)) | (1L << (IIMAGE2DARRAY - 128)) | (1L << (IIMAGE2DMS - 128)) | (1L << (IIMAGE2DMSARRAY - 128)) | (1L << (UIMAGE2DRECT - 128)) | (1L << (UIMAGE1DARRAY - 128)) | (1L << (UIMAGE2DARRAY - 128)) | (1L << (UIMAGE2DMS - 128)) | (1L << (UIMAGE2DMSARRAY - 128)))) != 0)) ) {
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

	public static class Precision_qualifierContext extends ParserRuleContext {
		public TerminalNode HIGHP() { return getToken(GLSLOtherParser.HIGHP, 0); }
		public TerminalNode MEDIUMP() { return getToken(GLSLOtherParser.MEDIUMP, 0); }
		public TerminalNode LOWP() { return getToken(GLSLOtherParser.LOWP, 0); }
		public Precision_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_precision_qualifier; }
	}

	public final Precision_qualifierContext precision_qualifier() throws RecognitionException {
		Precision_qualifierContext _localctx = new Precision_qualifierContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_precision_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(736);
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

	public static class Struct_specifierContext extends ParserRuleContext {
		public TerminalNode STRUCT() { return getToken(GLSLOtherParser.STRUCT, 0); }
		public TerminalNode LBRACE() { return getToken(GLSLOtherParser.LBRACE, 0); }
		public Member_listContext member_list() {
			return getRuleContext(Member_listContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSLOtherParser.RBRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Struct_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_specifier; }
	}

	public final Struct_specifierContext struct_specifier() throws RecognitionException {
		Struct_specifierContext _localctx = new Struct_specifierContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_struct_specifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(738);
			match(STRUCT);
			setState(740);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(739);
				match(IDENTIFIER);
				}
			}

			setState(742);
			match(LBRACE);
			setState(743);
			member_list(0);
			setState(744);
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

	public static class Member_listContext extends ParserRuleContext {
		public Member_declarationContext member_declaration() {
			return getRuleContext(Member_declarationContext.class,0);
		}
		public Member_listContext member_list() {
			return getRuleContext(Member_listContext.class,0);
		}
		public Member_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member_list; }
	}

	public final Member_listContext member_list() throws RecognitionException {
		return member_list(0);
	}

	private Member_listContext member_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Member_listContext _localctx = new Member_listContext(_ctx, _parentState);
		Member_listContext _prevctx = _localctx;
		int _startState = 130;
		enterRecursionRule(_localctx, 130, RULE_member_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(747);
			member_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(753);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Member_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_member_list);
					setState(749);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(750);
					member_declaration();
					}
					} 
				}
				setState(755);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
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

	public static class Member_declarationContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public Struct_declarator_listContext struct_declarator_list() {
			return getRuleContext(Struct_declarator_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public Member_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member_declaration; }
	}

	public final Member_declarationContext member_declaration() throws RecognitionException {
		Member_declarationContext _localctx = new Member_declarationContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_member_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(756);
			fully_specified_type();
			setState(757);
			struct_declarator_list(0);
			setState(758);
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

	public static class Struct_declarator_listContext extends ParserRuleContext {
		public Struct_declaratorContext struct_declarator() {
			return getRuleContext(Struct_declaratorContext.class,0);
		}
		public Struct_declarator_listContext struct_declarator_list() {
			return getRuleContext(Struct_declarator_listContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Struct_declarator_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_declarator_list; }
	}

	public final Struct_declarator_listContext struct_declarator_list() throws RecognitionException {
		return struct_declarator_list(0);
	}

	private Struct_declarator_listContext struct_declarator_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Struct_declarator_listContext _localctx = new Struct_declarator_listContext(_ctx, _parentState);
		Struct_declarator_listContext _prevctx = _localctx;
		int _startState = 134;
		enterRecursionRule(_localctx, 134, RULE_struct_declarator_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(761);
			struct_declarator();
			}
			_ctx.stop = _input.LT(-1);
			setState(768);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Struct_declarator_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_struct_declarator_list);
					setState(763);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(764);
					match(COMMA);
					setState(765);
					struct_declarator();
					}
					} 
				}
				setState(770);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
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

	public static class Struct_declaratorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Struct_declaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_declarator; }
	}

	public final Struct_declaratorContext struct_declarator() throws RecognitionException {
		Struct_declaratorContext _localctx = new Struct_declaratorContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_struct_declarator);
		try {
			setState(774);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(771);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(772);
				match(IDENTIFIER);
				setState(773);
				array_specifier(0);
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

	public static class InitializerContext extends ParserRuleContext {
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(GLSLOtherParser.LBRACE, 0); }
		public Initializer_listContext initializer_list() {
			return getRuleContext(Initializer_listContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSLOtherParser.RBRACE, 0); }
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_initializer);
		try {
			setState(786);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(776);
				assignment_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(777);
				match(LBRACE);
				setState(778);
				initializer_list(0);
				setState(779);
				match(RBRACE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(781);
				match(LBRACE);
				setState(782);
				initializer_list(0);
				setState(783);
				match(COMMA);
				setState(784);
				match(RBRACE);
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

	public static class Initializer_listContext extends ParserRuleContext {
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public Initializer_listContext initializer_list() {
			return getRuleContext(Initializer_listContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(GLSLOtherParser.COMMA, 0); }
		public Initializer_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer_list; }
	}

	public final Initializer_listContext initializer_list() throws RecognitionException {
		return initializer_list(0);
	}

	private Initializer_listContext initializer_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Initializer_listContext _localctx = new Initializer_listContext(_ctx, _parentState);
		Initializer_listContext _prevctx = _localctx;
		int _startState = 140;
		enterRecursionRule(_localctx, 140, RULE_initializer_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(789);
			initializer();
			}
			_ctx.stop = _input.LT(-1);
			setState(796);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Initializer_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_initializer_list);
					setState(791);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(792);
					match(COMMA);
					setState(793);
					initializer();
					}
					} 
				}
				setState(798);
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

	public static class Declaration_statementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public Declaration_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_statement; }
	}

	public final Declaration_statementContext declaration_statement() throws RecognitionException {
		Declaration_statementContext _localctx = new Declaration_statementContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_declaration_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(799);
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

	public static class StatementContext extends ParserRuleContext {
		public If_then_statementContext if_then_statement() {
			return getRuleContext(If_then_statementContext.class,0);
		}
		public If_then_else_statementContext if_then_else_statement() {
			return getRuleContext(If_then_else_statementContext.class,0);
		}
		public For_statementContext for_statement() {
			return getRuleContext(For_statementContext.class,0);
		}
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public Statement_no_trailing_substatementContext statement_no_trailing_substatement() {
			return getRuleContext(Statement_no_trailing_substatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_statement);
		try {
			setState(806);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(801);
				if_then_statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(802);
				if_then_else_statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(803);
				for_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(804);
				while_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(805);
				statement_no_trailing_substatement();
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

	public static class Statement_no_short_ifContext extends ParserRuleContext {
		public If_then_else_statement_no_short_ifContext if_then_else_statement_no_short_if() {
			return getRuleContext(If_then_else_statement_no_short_ifContext.class,0);
		}
		public For_statement_no_short_ifContext for_statement_no_short_if() {
			return getRuleContext(For_statement_no_short_ifContext.class,0);
		}
		public While_statement_no_short_ifContext while_statement_no_short_if() {
			return getRuleContext(While_statement_no_short_ifContext.class,0);
		}
		public Statement_no_trailing_substatementContext statement_no_trailing_substatement() {
			return getRuleContext(Statement_no_trailing_substatementContext.class,0);
		}
		public Statement_no_short_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_no_short_if; }
	}

	public final Statement_no_short_ifContext statement_no_short_if() throws RecognitionException {
		Statement_no_short_ifContext _localctx = new Statement_no_short_ifContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_statement_no_short_if);
		try {
			setState(812);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(808);
				if_then_else_statement_no_short_if();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(809);
				for_statement_no_short_if();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(810);
				while_statement_no_short_if();
				}
				break;
			case UNIFORM:
			case BUFFER:
			case IN_TOK:
			case OUT_TOK:
			case HIGHP:
			case MEDIUMP:
			case LOWP:
			case PRECISION:
			case INTCONSTANT:
			case CONST_TOK:
			case PRECISE:
			case INVARIANT:
			case SMOOTH:
			case FLAT:
			case NOPERSPECTIVE:
			case CENTROID:
			case SAMPLE:
			case ATTRIBUTE:
			case COHERENT:
			case VOLATILE:
			case RESTRICT:
			case VARYING:
			case READONLY:
			case WRITEONLY:
			case SHARED:
			case LAYOUT_TOK:
			case UINTCONSTANT:
			case FLOATCONSTANT:
			case BOOLCONSTANT:
			case INC_OP:
			case DEC_OP:
			case VOID_TOK:
			case FLOAT_TOK:
			case INT_TOK:
			case UINT_TOK:
			case BOOL_TOK:
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
			case ATOMIC_UINT:
			case STRUCT:
			case SWITCH:
			case DO:
			case CONTINUE:
			case BREAK:
			case RETURN:
			case DISCARD:
			case VEC2:
			case VEC3:
			case VEC4:
			case BVEC2:
			case BVEC3:
			case BVEC4:
			case IVEC2:
			case IVEC3:
			case IVEC4:
			case UVEC2:
			case UVEC3:
			case UVEC4:
			case MAT2X2:
			case MAT2X3:
			case MAT2X4:
			case MAT3X2:
			case MAT3X3:
			case MAT3X4:
			case MAT4X2:
			case MAT4X3:
			case MAT4X4:
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
			case SAMPLEREXTERNALOES:
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
			case LPAREN:
			case LBRACE:
			case SEMICOLON:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(811);
				statement_no_trailing_substatement();
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

	public static class Statement_no_trailing_substatementContext extends ParserRuleContext {
		public Block_statementContext block_statement() {
			return getRuleContext(Block_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Empty_statementContext empty_statement() {
			return getRuleContext(Empty_statementContext.class,0);
		}
		public Declaration_statementContext declaration_statement() {
			return getRuleContext(Declaration_statementContext.class,0);
		}
		public Switch_statementContext switch_statement() {
			return getRuleContext(Switch_statementContext.class,0);
		}
		public Do_statementContext do_statement() {
			return getRuleContext(Do_statementContext.class,0);
		}
		public Jump_statementContext jump_statement() {
			return getRuleContext(Jump_statementContext.class,0);
		}
		public Statement_no_trailing_substatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_no_trailing_substatement; }
	}

	public final Statement_no_trailing_substatementContext statement_no_trailing_substatement() throws RecognitionException {
		Statement_no_trailing_substatementContext _localctx = new Statement_no_trailing_substatementContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_statement_no_trailing_substatement);
		try {
			setState(821);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(814);
				block_statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(815);
				expression_statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(816);
				empty_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(817);
				declaration_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(818);
				switch_statement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(819);
				do_statement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(820);
				jump_statement();
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

	public static class Block_statementContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GLSLOtherParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSLOtherParser.RBRACE, 0); }
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Block_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_statement; }
	}

	public final Block_statementContext block_statement() throws RecognitionException {
		Block_statementContext _localctx = new Block_statementContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_block_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(823);
			match(LBRACE);
			setState(825);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (UNIFORM - 8)) | (1L << (BUFFER - 8)) | (1L << (IN_TOK - 8)) | (1L << (OUT_TOK - 8)) | (1L << (HIGHP - 8)) | (1L << (MEDIUMP - 8)) | (1L << (LOWP - 8)) | (1L << (PRECISION - 8)) | (1L << (INTCONSTANT - 8)) | (1L << (CONST_TOK - 8)) | (1L << (PRECISE - 8)) | (1L << (INVARIANT - 8)) | (1L << (SMOOTH - 8)) | (1L << (FLAT - 8)) | (1L << (NOPERSPECTIVE - 8)) | (1L << (CENTROID - 8)) | (1L << (SAMPLE - 8)) | (1L << (ATTRIBUTE - 8)) | (1L << (COHERENT - 8)) | (1L << (VOLATILE - 8)) | (1L << (RESTRICT - 8)) | (1L << (VARYING - 8)) | (1L << (READONLY - 8)) | (1L << (WRITEONLY - 8)) | (1L << (SHARED - 8)) | (1L << (LAYOUT_TOK - 8)) | (1L << (UINTCONSTANT - 8)) | (1L << (FLOATCONSTANT - 8)) | (1L << (BOOLCONSTANT - 8)) | (1L << (INC_OP - 8)) | (1L << (DEC_OP - 8)) | (1L << (VOID_TOK - 8)) | (1L << (FLOAT_TOK - 8)) | (1L << (INT_TOK - 8)) | (1L << (UINT_TOK - 8)) | (1L << (BOOL_TOK - 8)) | (1L << (SAMPLERCUBE - 8)) | (1L << (SAMPLERCUBESHADOW - 8)) | (1L << (SAMPLERBUFFER - 8)) | (1L << (SAMPLERCUBEARRAY - 8)) | (1L << (SAMPLERCUBEARRAYSHADOW - 8)))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ISAMPLERCUBE - 72)) | (1L << (ISAMPLERBUFFER - 72)) | (1L << (ISAMPLERCUBEARRAY - 72)) | (1L << (USAMPLERCUBE - 72)) | (1L << (USAMPLERBUFFER - 72)) | (1L << (USAMPLERCUBEARRAY - 72)) | (1L << (IMAGECUBE - 72)) | (1L << (IMAGEBUFFER - 72)) | (1L << (IMAGECUBEARRAY - 72)) | (1L << (IIMAGECUBE - 72)) | (1L << (IIMAGEBUFFER - 72)) | (1L << (IIMAGECUBEARRAY - 72)) | (1L << (UIMAGECUBE - 72)) | (1L << (UIMAGEBUFFER - 72)) | (1L << (UIMAGECUBEARRAY - 72)) | (1L << (ATOMIC_UINT - 72)) | (1L << (STRUCT - 72)) | (1L << (IF - 72)) | (1L << (SWITCH - 72)) | (1L << (WHILE - 72)) | (1L << (DO - 72)) | (1L << (FOR - 72)) | (1L << (CONTINUE - 72)) | (1L << (BREAK - 72)) | (1L << (RETURN - 72)) | (1L << (DISCARD - 72)) | (1L << (VEC2 - 72)) | (1L << (VEC3 - 72)) | (1L << (VEC4 - 72)) | (1L << (BVEC2 - 72)) | (1L << (BVEC3 - 72)) | (1L << (BVEC4 - 72)) | (1L << (IVEC2 - 72)) | (1L << (IVEC3 - 72)) | (1L << (IVEC4 - 72)) | (1L << (UVEC2 - 72)) | (1L << (UVEC3 - 72)) | (1L << (UVEC4 - 72)) | (1L << (MAT2X2 - 72)) | (1L << (MAT2X3 - 72)) | (1L << (MAT2X4 - 72)) | (1L << (MAT3X2 - 72)) | (1L << (MAT3X3 - 72)) | (1L << (MAT3X4 - 72)) | (1L << (MAT4X2 - 72)) | (1L << (MAT4X3 - 72)) | (1L << (MAT4X4 - 72)) | (1L << (IMAGE1D - 72)) | (1L << (IMAGE2D - 72)) | (1L << (IMAGE3D - 72)) | (1L << (UIMAGE1D - 72)) | (1L << (UIMAGE2D - 72)) | (1L << (UIMAGE3D - 72)) | (1L << (IIMAGE1D - 72)) | (1L << (IIMAGE2D - 72)) | (1L << (IIMAGE3D - 72)) | (1L << (SAMPLER1D - 72)) | (1L << (SAMPLER2D - 72)) | (1L << (SAMPLER3D - 72)) | (1L << (SAMPLER2DRECT - 72)) | (1L << (SAMPLEREXTERNALOES - 72)))) != 0) || ((((_la - 136)) & ~0x3f) == 0 && ((1L << (_la - 136)) & ((1L << (SAMPLER1DSHADOW - 136)) | (1L << (SAMPLER2DSHADOW - 136)) | (1L << (SAMPLER2DRECTSHADOW - 136)) | (1L << (SAMPLER1DARRAY - 136)) | (1L << (SAMPLER2DARRAY - 136)) | (1L << (SAMPLER1DARRAYSHADOW - 136)) | (1L << (SAMPLER2DARRAYSHADOW - 136)) | (1L << (ISAMPLER1D - 136)) | (1L << (ISAMPLER2D - 136)) | (1L << (ISAMPLER2DRECT - 136)) | (1L << (ISAMPLER3D - 136)) | (1L << (ISAMPLER1DARRAY - 136)) | (1L << (ISAMPLER2DARRAY - 136)) | (1L << (USAMPLER1D - 136)) | (1L << (USAMPLER2D - 136)) | (1L << (USAMPLER2DRECT - 136)) | (1L << (USAMPLER3D - 136)) | (1L << (USAMPLER1DARRAY - 136)) | (1L << (USAMPLER2DARRAY - 136)) | (1L << (SAMPLER2DMS - 136)) | (1L << (ISAMPLER2DMS - 136)) | (1L << (USAMPLER2DMS - 136)) | (1L << (SAMPLER2DMSARRAY - 136)) | (1L << (ISAMPLER2DMSARRAY - 136)) | (1L << (USAMPLER2DMSARRAY - 136)) | (1L << (IMAGE2DRECT - 136)) | (1L << (IMAGE1DARRAY - 136)) | (1L << (IMAGE2DARRAY - 136)) | (1L << (IMAGE2DMS - 136)) | (1L << (IMAGE2DMSARRAY - 136)) | (1L << (IIMAGE2DRECT - 136)) | (1L << (IIMAGE1DARRAY - 136)) | (1L << (IIMAGE2DARRAY - 136)) | (1L << (IIMAGE2DMS - 136)) | (1L << (IIMAGE2DMSARRAY - 136)) | (1L << (UIMAGE2DRECT - 136)) | (1L << (UIMAGE1DARRAY - 136)) | (1L << (UIMAGE2DARRAY - 136)) | (1L << (UIMAGE2DMS - 136)) | (1L << (UIMAGE2DMSARRAY - 136)) | (1L << (LPAREN - 136)) | (1L << (LBRACE - 136)) | (1L << (SEMICOLON - 136)) | (1L << (PLUS_OP - 136)) | (1L << (MINUS_OP - 136)) | (1L << (NOT_OP - 136)) | (1L << (BNEG_OP - 136)) | (1L << (IDENTIFIER - 136)))) != 0)) {
				{
				setState(824);
				statement_list();
				}
			}

			setState(827);
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

	public static class Statement_listContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_list; }
	}

	public final Statement_listContext statement_list() throws RecognitionException {
		Statement_listContext _localctx = new Statement_listContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_statement_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(830); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(829);
				statement();
				}
				}
				setState(832); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (UNIFORM - 8)) | (1L << (BUFFER - 8)) | (1L << (IN_TOK - 8)) | (1L << (OUT_TOK - 8)) | (1L << (HIGHP - 8)) | (1L << (MEDIUMP - 8)) | (1L << (LOWP - 8)) | (1L << (PRECISION - 8)) | (1L << (INTCONSTANT - 8)) | (1L << (CONST_TOK - 8)) | (1L << (PRECISE - 8)) | (1L << (INVARIANT - 8)) | (1L << (SMOOTH - 8)) | (1L << (FLAT - 8)) | (1L << (NOPERSPECTIVE - 8)) | (1L << (CENTROID - 8)) | (1L << (SAMPLE - 8)) | (1L << (ATTRIBUTE - 8)) | (1L << (COHERENT - 8)) | (1L << (VOLATILE - 8)) | (1L << (RESTRICT - 8)) | (1L << (VARYING - 8)) | (1L << (READONLY - 8)) | (1L << (WRITEONLY - 8)) | (1L << (SHARED - 8)) | (1L << (LAYOUT_TOK - 8)) | (1L << (UINTCONSTANT - 8)) | (1L << (FLOATCONSTANT - 8)) | (1L << (BOOLCONSTANT - 8)) | (1L << (INC_OP - 8)) | (1L << (DEC_OP - 8)) | (1L << (VOID_TOK - 8)) | (1L << (FLOAT_TOK - 8)) | (1L << (INT_TOK - 8)) | (1L << (UINT_TOK - 8)) | (1L << (BOOL_TOK - 8)) | (1L << (SAMPLERCUBE - 8)) | (1L << (SAMPLERCUBESHADOW - 8)) | (1L << (SAMPLERBUFFER - 8)) | (1L << (SAMPLERCUBEARRAY - 8)) | (1L << (SAMPLERCUBEARRAYSHADOW - 8)))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ISAMPLERCUBE - 72)) | (1L << (ISAMPLERBUFFER - 72)) | (1L << (ISAMPLERCUBEARRAY - 72)) | (1L << (USAMPLERCUBE - 72)) | (1L << (USAMPLERBUFFER - 72)) | (1L << (USAMPLERCUBEARRAY - 72)) | (1L << (IMAGECUBE - 72)) | (1L << (IMAGEBUFFER - 72)) | (1L << (IMAGECUBEARRAY - 72)) | (1L << (IIMAGECUBE - 72)) | (1L << (IIMAGEBUFFER - 72)) | (1L << (IIMAGECUBEARRAY - 72)) | (1L << (UIMAGECUBE - 72)) | (1L << (UIMAGEBUFFER - 72)) | (1L << (UIMAGECUBEARRAY - 72)) | (1L << (ATOMIC_UINT - 72)) | (1L << (STRUCT - 72)) | (1L << (IF - 72)) | (1L << (SWITCH - 72)) | (1L << (WHILE - 72)) | (1L << (DO - 72)) | (1L << (FOR - 72)) | (1L << (CONTINUE - 72)) | (1L << (BREAK - 72)) | (1L << (RETURN - 72)) | (1L << (DISCARD - 72)) | (1L << (VEC2 - 72)) | (1L << (VEC3 - 72)) | (1L << (VEC4 - 72)) | (1L << (BVEC2 - 72)) | (1L << (BVEC3 - 72)) | (1L << (BVEC4 - 72)) | (1L << (IVEC2 - 72)) | (1L << (IVEC3 - 72)) | (1L << (IVEC4 - 72)) | (1L << (UVEC2 - 72)) | (1L << (UVEC3 - 72)) | (1L << (UVEC4 - 72)) | (1L << (MAT2X2 - 72)) | (1L << (MAT2X3 - 72)) | (1L << (MAT2X4 - 72)) | (1L << (MAT3X2 - 72)) | (1L << (MAT3X3 - 72)) | (1L << (MAT3X4 - 72)) | (1L << (MAT4X2 - 72)) | (1L << (MAT4X3 - 72)) | (1L << (MAT4X4 - 72)) | (1L << (IMAGE1D - 72)) | (1L << (IMAGE2D - 72)) | (1L << (IMAGE3D - 72)) | (1L << (UIMAGE1D - 72)) | (1L << (UIMAGE2D - 72)) | (1L << (UIMAGE3D - 72)) | (1L << (IIMAGE1D - 72)) | (1L << (IIMAGE2D - 72)) | (1L << (IIMAGE3D - 72)) | (1L << (SAMPLER1D - 72)) | (1L << (SAMPLER2D - 72)) | (1L << (SAMPLER3D - 72)) | (1L << (SAMPLER2DRECT - 72)) | (1L << (SAMPLEREXTERNALOES - 72)))) != 0) || ((((_la - 136)) & ~0x3f) == 0 && ((1L << (_la - 136)) & ((1L << (SAMPLER1DSHADOW - 136)) | (1L << (SAMPLER2DSHADOW - 136)) | (1L << (SAMPLER2DRECTSHADOW - 136)) | (1L << (SAMPLER1DARRAY - 136)) | (1L << (SAMPLER2DARRAY - 136)) | (1L << (SAMPLER1DARRAYSHADOW - 136)) | (1L << (SAMPLER2DARRAYSHADOW - 136)) | (1L << (ISAMPLER1D - 136)) | (1L << (ISAMPLER2D - 136)) | (1L << (ISAMPLER2DRECT - 136)) | (1L << (ISAMPLER3D - 136)) | (1L << (ISAMPLER1DARRAY - 136)) | (1L << (ISAMPLER2DARRAY - 136)) | (1L << (USAMPLER1D - 136)) | (1L << (USAMPLER2D - 136)) | (1L << (USAMPLER2DRECT - 136)) | (1L << (USAMPLER3D - 136)) | (1L << (USAMPLER1DARRAY - 136)) | (1L << (USAMPLER2DARRAY - 136)) | (1L << (SAMPLER2DMS - 136)) | (1L << (ISAMPLER2DMS - 136)) | (1L << (USAMPLER2DMS - 136)) | (1L << (SAMPLER2DMSARRAY - 136)) | (1L << (ISAMPLER2DMSARRAY - 136)) | (1L << (USAMPLER2DMSARRAY - 136)) | (1L << (IMAGE2DRECT - 136)) | (1L << (IMAGE1DARRAY - 136)) | (1L << (IMAGE2DARRAY - 136)) | (1L << (IMAGE2DMS - 136)) | (1L << (IMAGE2DMSARRAY - 136)) | (1L << (IIMAGE2DRECT - 136)) | (1L << (IIMAGE1DARRAY - 136)) | (1L << (IIMAGE2DARRAY - 136)) | (1L << (IIMAGE2DMS - 136)) | (1L << (IIMAGE2DMSARRAY - 136)) | (1L << (UIMAGE2DRECT - 136)) | (1L << (UIMAGE1DARRAY - 136)) | (1L << (UIMAGE2DARRAY - 136)) | (1L << (UIMAGE2DMS - 136)) | (1L << (UIMAGE2DMSARRAY - 136)) | (1L << (LPAREN - 136)) | (1L << (LBRACE - 136)) | (1L << (SEMICOLON - 136)) | (1L << (PLUS_OP - 136)) | (1L << (MINUS_OP - 136)) | (1L << (NOT_OP - 136)) | (1L << (BNEG_OP - 136)) | (1L << (IDENTIFIER - 136)))) != 0) );
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

	public static class Expression_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_expression_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(834);
			expression();
			setState(835);
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

	public static class Empty_statementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public Empty_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_empty_statement; }
	}

	public final Empty_statementContext empty_statement() throws RecognitionException {
		Empty_statementContext _localctx = new Empty_statementContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_empty_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(837);
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

	public static class If_then_statementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GLSLOtherParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public If_then_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_statement; }
	}

	public final If_then_statementContext if_then_statement() throws RecognitionException {
		If_then_statementContext _localctx = new If_then_statementContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_if_then_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(839);
			match(IF);
			setState(840);
			match(LPAREN);
			setState(841);
			expression();
			setState(842);
			match(RPAREN);
			setState(843);
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

	public static class If_then_else_statementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GLSLOtherParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Statement_no_short_ifContext statement_no_short_if() {
			return getRuleContext(Statement_no_short_ifContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(GLSLOtherParser.ELSE, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public If_then_else_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_else_statement; }
	}

	public final If_then_else_statementContext if_then_else_statement() throws RecognitionException {
		If_then_else_statementContext _localctx = new If_then_else_statementContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_if_then_else_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(845);
			match(IF);
			setState(846);
			match(LPAREN);
			setState(847);
			expression();
			setState(848);
			match(RPAREN);
			setState(849);
			statement_no_short_if();
			setState(850);
			match(ELSE);
			setState(851);
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

	public static class If_then_else_statement_no_short_ifContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GLSLOtherParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public List<Statement_no_short_ifContext> statement_no_short_if() {
			return getRuleContexts(Statement_no_short_ifContext.class);
		}
		public Statement_no_short_ifContext statement_no_short_if(int i) {
			return getRuleContext(Statement_no_short_ifContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GLSLOtherParser.ELSE, 0); }
		public If_then_else_statement_no_short_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_else_statement_no_short_if; }
	}

	public final If_then_else_statement_no_short_ifContext if_then_else_statement_no_short_if() throws RecognitionException {
		If_then_else_statement_no_short_ifContext _localctx = new If_then_else_statement_no_short_ifContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_if_then_else_statement_no_short_if);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(853);
			match(IF);
			setState(854);
			match(LPAREN);
			setState(855);
			expression();
			setState(856);
			match(RPAREN);
			setState(857);
			statement_no_short_if();
			setState(858);
			match(ELSE);
			setState(859);
			statement_no_short_if();
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
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSLOtherParser.ASSIGN_OP, 0); }
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
		enterRule(_localctx, 164, RULE_condition);
		try {
			setState(867);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(861);
				expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(862);
				fully_specified_type();
				setState(863);
				match(IDENTIFIER);
				setState(864);
				match(ASSIGN_OP);
				setState(865);
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

	public static class Switch_statementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(GLSLOtherParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Switch_bodyContext switch_body() {
			return getRuleContext(Switch_bodyContext.class,0);
		}
		public Switch_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_statement; }
	}

	public final Switch_statementContext switch_statement() throws RecognitionException {
		Switch_statementContext _localctx = new Switch_statementContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_switch_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(869);
			match(SWITCH);
			setState(870);
			match(LPAREN);
			setState(871);
			expression();
			setState(872);
			match(RPAREN);
			setState(873);
			switch_body();
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

	public static class Switch_bodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GLSLOtherParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSLOtherParser.RBRACE, 0); }
		public Case_statement_listContext case_statement_list() {
			return getRuleContext(Case_statement_listContext.class,0);
		}
		public Switch_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_body; }
	}

	public final Switch_bodyContext switch_body() throws RecognitionException {
		Switch_bodyContext _localctx = new Switch_bodyContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_switch_body);
		try {
			setState(881);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(875);
				match(LBRACE);
				setState(876);
				match(RBRACE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(877);
				match(LBRACE);
				setState(878);
				case_statement_list(0);
				setState(879);
				match(RBRACE);
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

	public static class Case_labelContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(GLSLOtherParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GLSLOtherParser.COLON, 0); }
		public TerminalNode DEFAULT() { return getToken(GLSLOtherParser.DEFAULT, 0); }
		public Case_labelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_label; }
	}

	public final Case_labelContext case_label() throws RecognitionException {
		Case_labelContext _localctx = new Case_labelContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_case_label);
		try {
			setState(889);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(883);
				match(CASE);
				setState(884);
				expression();
				setState(885);
				match(COLON);
				}
				break;
			case DEFAULT:
				enterOuterAlt(_localctx, 2);
				{
				setState(887);
				match(DEFAULT);
				setState(888);
				match(COLON);
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

	public static class Case_label_listContext extends ParserRuleContext {
		public Case_labelContext case_label() {
			return getRuleContext(Case_labelContext.class,0);
		}
		public Case_label_listContext case_label_list() {
			return getRuleContext(Case_label_listContext.class,0);
		}
		public Case_label_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_label_list; }
	}

	public final Case_label_listContext case_label_list() throws RecognitionException {
		return case_label_list(0);
	}

	private Case_label_listContext case_label_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Case_label_listContext _localctx = new Case_label_listContext(_ctx, _parentState);
		Case_label_listContext _prevctx = _localctx;
		int _startState = 172;
		enterRecursionRule(_localctx, 172, RULE_case_label_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(892);
			case_label();
			}
			_ctx.stop = _input.LT(-1);
			setState(898);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Case_label_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_case_label_list);
					setState(894);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(895);
					case_label();
					}
					} 
				}
				setState(900);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
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

	public static class Case_statementContext extends ParserRuleContext {
		public Case_label_listContext case_label_list() {
			return getRuleContext(Case_label_listContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public Case_statementContext case_statement() {
			return getRuleContext(Case_statementContext.class,0);
		}
		public Case_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_statement; }
	}

	public final Case_statementContext case_statement() throws RecognitionException {
		return case_statement(0);
	}

	private Case_statementContext case_statement(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Case_statementContext _localctx = new Case_statementContext(_ctx, _parentState);
		Case_statementContext _prevctx = _localctx;
		int _startState = 174;
		enterRecursionRule(_localctx, 174, RULE_case_statement, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(902);
			case_label_list(0);
			setState(903);
			statement();
			}
			_ctx.stop = _input.LT(-1);
			setState(909);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Case_statementContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_case_statement);
					setState(905);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(906);
					statement();
					}
					} 
				}
				setState(911);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
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

	public static class Case_statement_listContext extends ParserRuleContext {
		public Case_statementContext case_statement() {
			return getRuleContext(Case_statementContext.class,0);
		}
		public Case_statement_listContext case_statement_list() {
			return getRuleContext(Case_statement_listContext.class,0);
		}
		public Case_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_statement_list; }
	}

	public final Case_statement_listContext case_statement_list() throws RecognitionException {
		return case_statement_list(0);
	}

	private Case_statement_listContext case_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Case_statement_listContext _localctx = new Case_statement_listContext(_ctx, _parentState);
		Case_statement_listContext _prevctx = _localctx;
		int _startState = 176;
		enterRecursionRule(_localctx, 176, RULE_case_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(913);
			case_statement(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(919);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Case_statement_listContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_case_statement_list);
					setState(915);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(916);
					case_statement(0);
					}
					} 
				}
				setState(921);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
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

	public static class Do_statementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(GLSLOtherParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(GLSLOtherParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public Do_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_statement; }
	}

	public final Do_statementContext do_statement() throws RecognitionException {
		Do_statementContext _localctx = new Do_statementContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_do_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			match(DO);
			setState(923);
			statement();
			setState(924);
			match(WHILE);
			setState(925);
			match(LPAREN);
			setState(926);
			expression();
			setState(927);
			match(RPAREN);
			setState(928);
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

	public static class For_statementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GLSLOtherParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public For_init_statementContext for_init_statement() {
			return getRuleContext(For_init_statementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public For_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_statement; }
	}

	public final For_statementContext for_statement() throws RecognitionException {
		For_statementContext _localctx = new For_statementContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_for_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(930);
			match(FOR);
			setState(931);
			match(LPAREN);
			setState(932);
			for_init_statement();
			setState(934);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (UNIFORM - 8)) | (1L << (IN_TOK - 8)) | (1L << (OUT_TOK - 8)) | (1L << (HIGHP - 8)) | (1L << (MEDIUMP - 8)) | (1L << (LOWP - 8)) | (1L << (INTCONSTANT - 8)) | (1L << (CONST_TOK - 8)) | (1L << (PRECISE - 8)) | (1L << (INVARIANT - 8)) | (1L << (SMOOTH - 8)) | (1L << (FLAT - 8)) | (1L << (NOPERSPECTIVE - 8)) | (1L << (CENTROID - 8)) | (1L << (SAMPLE - 8)) | (1L << (ATTRIBUTE - 8)) | (1L << (COHERENT - 8)) | (1L << (VOLATILE - 8)) | (1L << (RESTRICT - 8)) | (1L << (VARYING - 8)) | (1L << (READONLY - 8)) | (1L << (WRITEONLY - 8)) | (1L << (SHARED - 8)) | (1L << (LAYOUT_TOK - 8)) | (1L << (UINTCONSTANT - 8)) | (1L << (FLOATCONSTANT - 8)) | (1L << (BOOLCONSTANT - 8)) | (1L << (INC_OP - 8)) | (1L << (DEC_OP - 8)) | (1L << (VOID_TOK - 8)) | (1L << (FLOAT_TOK - 8)) | (1L << (INT_TOK - 8)) | (1L << (UINT_TOK - 8)) | (1L << (BOOL_TOK - 8)) | (1L << (SAMPLERCUBE - 8)) | (1L << (SAMPLERCUBESHADOW - 8)) | (1L << (SAMPLERBUFFER - 8)) | (1L << (SAMPLERCUBEARRAY - 8)) | (1L << (SAMPLERCUBEARRAYSHADOW - 8)))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ISAMPLERCUBE - 72)) | (1L << (ISAMPLERBUFFER - 72)) | (1L << (ISAMPLERCUBEARRAY - 72)) | (1L << (USAMPLERCUBE - 72)) | (1L << (USAMPLERBUFFER - 72)) | (1L << (USAMPLERCUBEARRAY - 72)) | (1L << (IMAGECUBE - 72)) | (1L << (IMAGEBUFFER - 72)) | (1L << (IMAGECUBEARRAY - 72)) | (1L << (IIMAGECUBE - 72)) | (1L << (IIMAGEBUFFER - 72)) | (1L << (IIMAGECUBEARRAY - 72)) | (1L << (UIMAGECUBE - 72)) | (1L << (UIMAGEBUFFER - 72)) | (1L << (UIMAGECUBEARRAY - 72)) | (1L << (ATOMIC_UINT - 72)) | (1L << (STRUCT - 72)) | (1L << (VEC2 - 72)) | (1L << (VEC3 - 72)) | (1L << (VEC4 - 72)) | (1L << (BVEC2 - 72)) | (1L << (BVEC3 - 72)) | (1L << (BVEC4 - 72)) | (1L << (IVEC2 - 72)) | (1L << (IVEC3 - 72)) | (1L << (IVEC4 - 72)) | (1L << (UVEC2 - 72)) | (1L << (UVEC3 - 72)) | (1L << (UVEC4 - 72)) | (1L << (MAT2X2 - 72)) | (1L << (MAT2X3 - 72)) | (1L << (MAT2X4 - 72)) | (1L << (MAT3X2 - 72)) | (1L << (MAT3X3 - 72)) | (1L << (MAT3X4 - 72)) | (1L << (MAT4X2 - 72)) | (1L << (MAT4X3 - 72)) | (1L << (MAT4X4 - 72)) | (1L << (IMAGE1D - 72)) | (1L << (IMAGE2D - 72)) | (1L << (IMAGE3D - 72)) | (1L << (UIMAGE1D - 72)) | (1L << (UIMAGE2D - 72)) | (1L << (UIMAGE3D - 72)) | (1L << (IIMAGE1D - 72)) | (1L << (IIMAGE2D - 72)) | (1L << (IIMAGE3D - 72)) | (1L << (SAMPLER1D - 72)) | (1L << (SAMPLER2D - 72)) | (1L << (SAMPLER3D - 72)) | (1L << (SAMPLER2DRECT - 72)) | (1L << (SAMPLEREXTERNALOES - 72)))) != 0) || ((((_la - 136)) & ~0x3f) == 0 && ((1L << (_la - 136)) & ((1L << (SAMPLER1DSHADOW - 136)) | (1L << (SAMPLER2DSHADOW - 136)) | (1L << (SAMPLER2DRECTSHADOW - 136)) | (1L << (SAMPLER1DARRAY - 136)) | (1L << (SAMPLER2DARRAY - 136)) | (1L << (SAMPLER1DARRAYSHADOW - 136)) | (1L << (SAMPLER2DARRAYSHADOW - 136)) | (1L << (ISAMPLER1D - 136)) | (1L << (ISAMPLER2D - 136)) | (1L << (ISAMPLER2DRECT - 136)) | (1L << (ISAMPLER3D - 136)) | (1L << (ISAMPLER1DARRAY - 136)) | (1L << (ISAMPLER2DARRAY - 136)) | (1L << (USAMPLER1D - 136)) | (1L << (USAMPLER2D - 136)) | (1L << (USAMPLER2DRECT - 136)) | (1L << (USAMPLER3D - 136)) | (1L << (USAMPLER1DARRAY - 136)) | (1L << (USAMPLER2DARRAY - 136)) | (1L << (SAMPLER2DMS - 136)) | (1L << (ISAMPLER2DMS - 136)) | (1L << (USAMPLER2DMS - 136)) | (1L << (SAMPLER2DMSARRAY - 136)) | (1L << (ISAMPLER2DMSARRAY - 136)) | (1L << (USAMPLER2DMSARRAY - 136)) | (1L << (IMAGE2DRECT - 136)) | (1L << (IMAGE1DARRAY - 136)) | (1L << (IMAGE2DARRAY - 136)) | (1L << (IMAGE2DMS - 136)) | (1L << (IMAGE2DMSARRAY - 136)) | (1L << (IIMAGE2DRECT - 136)) | (1L << (IIMAGE1DARRAY - 136)) | (1L << (IIMAGE2DARRAY - 136)) | (1L << (IIMAGE2DMS - 136)) | (1L << (IIMAGE2DMSARRAY - 136)) | (1L << (UIMAGE2DRECT - 136)) | (1L << (UIMAGE1DARRAY - 136)) | (1L << (UIMAGE2DARRAY - 136)) | (1L << (UIMAGE2DMS - 136)) | (1L << (UIMAGE2DMSARRAY - 136)) | (1L << (LPAREN - 136)) | (1L << (PLUS_OP - 136)) | (1L << (MINUS_OP - 136)) | (1L << (NOT_OP - 136)) | (1L << (BNEG_OP - 136)) | (1L << (IDENTIFIER - 136)))) != 0)) {
				{
				setState(933);
				condition();
				}
			}

			setState(936);
			match(SEMICOLON);
			setState(938);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 18)) & ~0x3f) == 0 && ((1L << (_la - 18)) & ((1L << (INTCONSTANT - 18)) | (1L << (UINTCONSTANT - 18)) | (1L << (FLOATCONSTANT - 18)) | (1L << (BOOLCONSTANT - 18)) | (1L << (INC_OP - 18)) | (1L << (DEC_OP - 18)) | (1L << (VOID_TOK - 18)) | (1L << (FLOAT_TOK - 18)) | (1L << (INT_TOK - 18)) | (1L << (UINT_TOK - 18)) | (1L << (BOOL_TOK - 18)) | (1L << (SAMPLERCUBE - 18)) | (1L << (SAMPLERCUBESHADOW - 18)) | (1L << (SAMPLERBUFFER - 18)) | (1L << (SAMPLERCUBEARRAY - 18)) | (1L << (SAMPLERCUBEARRAYSHADOW - 18)) | (1L << (ISAMPLERCUBE - 18)) | (1L << (ISAMPLERBUFFER - 18)) | (1L << (ISAMPLERCUBEARRAY - 18)) | (1L << (USAMPLERCUBE - 18)) | (1L << (USAMPLERBUFFER - 18)) | (1L << (USAMPLERCUBEARRAY - 18)) | (1L << (IMAGECUBE - 18)) | (1L << (IMAGEBUFFER - 18)) | (1L << (IMAGECUBEARRAY - 18)) | (1L << (IIMAGECUBE - 18)))) != 0) || ((((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & ((1L << (IIMAGEBUFFER - 82)) | (1L << (IIMAGECUBEARRAY - 82)) | (1L << (UIMAGECUBE - 82)) | (1L << (UIMAGEBUFFER - 82)) | (1L << (UIMAGECUBEARRAY - 82)) | (1L << (ATOMIC_UINT - 82)) | (1L << (VEC2 - 82)) | (1L << (VEC3 - 82)) | (1L << (VEC4 - 82)) | (1L << (BVEC2 - 82)) | (1L << (BVEC3 - 82)) | (1L << (BVEC4 - 82)) | (1L << (IVEC2 - 82)) | (1L << (IVEC3 - 82)) | (1L << (IVEC4 - 82)) | (1L << (UVEC2 - 82)) | (1L << (UVEC3 - 82)) | (1L << (UVEC4 - 82)) | (1L << (MAT2X2 - 82)) | (1L << (MAT2X3 - 82)) | (1L << (MAT2X4 - 82)) | (1L << (MAT3X2 - 82)) | (1L << (MAT3X3 - 82)) | (1L << (MAT3X4 - 82)) | (1L << (MAT4X2 - 82)) | (1L << (MAT4X3 - 82)) | (1L << (MAT4X4 - 82)) | (1L << (IMAGE1D - 82)) | (1L << (IMAGE2D - 82)) | (1L << (IMAGE3D - 82)) | (1L << (UIMAGE1D - 82)) | (1L << (UIMAGE2D - 82)) | (1L << (UIMAGE3D - 82)) | (1L << (IIMAGE1D - 82)) | (1L << (IIMAGE2D - 82)) | (1L << (IIMAGE3D - 82)) | (1L << (SAMPLER1D - 82)) | (1L << (SAMPLER2D - 82)) | (1L << (SAMPLER3D - 82)) | (1L << (SAMPLER2DRECT - 82)) | (1L << (SAMPLEREXTERNALOES - 82)) | (1L << (SAMPLER1DSHADOW - 82)) | (1L << (SAMPLER2DSHADOW - 82)) | (1L << (SAMPLER2DRECTSHADOW - 82)) | (1L << (SAMPLER1DARRAY - 82)) | (1L << (SAMPLER2DARRAY - 82)) | (1L << (SAMPLER1DARRAYSHADOW - 82)) | (1L << (SAMPLER2DARRAYSHADOW - 82)) | (1L << (ISAMPLER1D - 82)) | (1L << (ISAMPLER2D - 82)) | (1L << (ISAMPLER2DRECT - 82)))) != 0) || ((((_la - 146)) & ~0x3f) == 0 && ((1L << (_la - 146)) & ((1L << (ISAMPLER3D - 146)) | (1L << (ISAMPLER1DARRAY - 146)) | (1L << (ISAMPLER2DARRAY - 146)) | (1L << (USAMPLER1D - 146)) | (1L << (USAMPLER2D - 146)) | (1L << (USAMPLER2DRECT - 146)) | (1L << (USAMPLER3D - 146)) | (1L << (USAMPLER1DARRAY - 146)) | (1L << (USAMPLER2DARRAY - 146)) | (1L << (SAMPLER2DMS - 146)) | (1L << (ISAMPLER2DMS - 146)) | (1L << (USAMPLER2DMS - 146)) | (1L << (SAMPLER2DMSARRAY - 146)) | (1L << (ISAMPLER2DMSARRAY - 146)) | (1L << (USAMPLER2DMSARRAY - 146)) | (1L << (IMAGE2DRECT - 146)) | (1L << (IMAGE1DARRAY - 146)) | (1L << (IMAGE2DARRAY - 146)) | (1L << (IMAGE2DMS - 146)) | (1L << (IMAGE2DMSARRAY - 146)) | (1L << (IIMAGE2DRECT - 146)) | (1L << (IIMAGE1DARRAY - 146)) | (1L << (IIMAGE2DARRAY - 146)) | (1L << (IIMAGE2DMS - 146)) | (1L << (IIMAGE2DMSARRAY - 146)) | (1L << (UIMAGE2DRECT - 146)) | (1L << (UIMAGE1DARRAY - 146)) | (1L << (UIMAGE2DARRAY - 146)) | (1L << (UIMAGE2DMS - 146)) | (1L << (UIMAGE2DMSARRAY - 146)) | (1L << (LPAREN - 146)) | (1L << (PLUS_OP - 146)) | (1L << (MINUS_OP - 146)) | (1L << (NOT_OP - 146)) | (1L << (BNEG_OP - 146)) | (1L << (IDENTIFIER - 146)))) != 0)) {
				{
				setState(937);
				expression();
				}
			}

			setState(940);
			match(RPAREN);
			setState(941);
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

	public static class For_statement_no_short_ifContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GLSLOtherParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public For_init_statementContext for_init_statement() {
			return getRuleContext(For_init_statementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Statement_no_short_ifContext statement_no_short_if() {
			return getRuleContext(Statement_no_short_ifContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public For_statement_no_short_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_statement_no_short_if; }
	}

	public final For_statement_no_short_ifContext for_statement_no_short_if() throws RecognitionException {
		For_statement_no_short_ifContext _localctx = new For_statement_no_short_ifContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_for_statement_no_short_if);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(943);
			match(FOR);
			setState(944);
			match(LPAREN);
			setState(945);
			for_init_statement();
			setState(947);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (UNIFORM - 8)) | (1L << (IN_TOK - 8)) | (1L << (OUT_TOK - 8)) | (1L << (HIGHP - 8)) | (1L << (MEDIUMP - 8)) | (1L << (LOWP - 8)) | (1L << (INTCONSTANT - 8)) | (1L << (CONST_TOK - 8)) | (1L << (PRECISE - 8)) | (1L << (INVARIANT - 8)) | (1L << (SMOOTH - 8)) | (1L << (FLAT - 8)) | (1L << (NOPERSPECTIVE - 8)) | (1L << (CENTROID - 8)) | (1L << (SAMPLE - 8)) | (1L << (ATTRIBUTE - 8)) | (1L << (COHERENT - 8)) | (1L << (VOLATILE - 8)) | (1L << (RESTRICT - 8)) | (1L << (VARYING - 8)) | (1L << (READONLY - 8)) | (1L << (WRITEONLY - 8)) | (1L << (SHARED - 8)) | (1L << (LAYOUT_TOK - 8)) | (1L << (UINTCONSTANT - 8)) | (1L << (FLOATCONSTANT - 8)) | (1L << (BOOLCONSTANT - 8)) | (1L << (INC_OP - 8)) | (1L << (DEC_OP - 8)) | (1L << (VOID_TOK - 8)) | (1L << (FLOAT_TOK - 8)) | (1L << (INT_TOK - 8)) | (1L << (UINT_TOK - 8)) | (1L << (BOOL_TOK - 8)) | (1L << (SAMPLERCUBE - 8)) | (1L << (SAMPLERCUBESHADOW - 8)) | (1L << (SAMPLERBUFFER - 8)) | (1L << (SAMPLERCUBEARRAY - 8)) | (1L << (SAMPLERCUBEARRAYSHADOW - 8)))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ISAMPLERCUBE - 72)) | (1L << (ISAMPLERBUFFER - 72)) | (1L << (ISAMPLERCUBEARRAY - 72)) | (1L << (USAMPLERCUBE - 72)) | (1L << (USAMPLERBUFFER - 72)) | (1L << (USAMPLERCUBEARRAY - 72)) | (1L << (IMAGECUBE - 72)) | (1L << (IMAGEBUFFER - 72)) | (1L << (IMAGECUBEARRAY - 72)) | (1L << (IIMAGECUBE - 72)) | (1L << (IIMAGEBUFFER - 72)) | (1L << (IIMAGECUBEARRAY - 72)) | (1L << (UIMAGECUBE - 72)) | (1L << (UIMAGEBUFFER - 72)) | (1L << (UIMAGECUBEARRAY - 72)) | (1L << (ATOMIC_UINT - 72)) | (1L << (STRUCT - 72)) | (1L << (VEC2 - 72)) | (1L << (VEC3 - 72)) | (1L << (VEC4 - 72)) | (1L << (BVEC2 - 72)) | (1L << (BVEC3 - 72)) | (1L << (BVEC4 - 72)) | (1L << (IVEC2 - 72)) | (1L << (IVEC3 - 72)) | (1L << (IVEC4 - 72)) | (1L << (UVEC2 - 72)) | (1L << (UVEC3 - 72)) | (1L << (UVEC4 - 72)) | (1L << (MAT2X2 - 72)) | (1L << (MAT2X3 - 72)) | (1L << (MAT2X4 - 72)) | (1L << (MAT3X2 - 72)) | (1L << (MAT3X3 - 72)) | (1L << (MAT3X4 - 72)) | (1L << (MAT4X2 - 72)) | (1L << (MAT4X3 - 72)) | (1L << (MAT4X4 - 72)) | (1L << (IMAGE1D - 72)) | (1L << (IMAGE2D - 72)) | (1L << (IMAGE3D - 72)) | (1L << (UIMAGE1D - 72)) | (1L << (UIMAGE2D - 72)) | (1L << (UIMAGE3D - 72)) | (1L << (IIMAGE1D - 72)) | (1L << (IIMAGE2D - 72)) | (1L << (IIMAGE3D - 72)) | (1L << (SAMPLER1D - 72)) | (1L << (SAMPLER2D - 72)) | (1L << (SAMPLER3D - 72)) | (1L << (SAMPLER2DRECT - 72)) | (1L << (SAMPLEREXTERNALOES - 72)))) != 0) || ((((_la - 136)) & ~0x3f) == 0 && ((1L << (_la - 136)) & ((1L << (SAMPLER1DSHADOW - 136)) | (1L << (SAMPLER2DSHADOW - 136)) | (1L << (SAMPLER2DRECTSHADOW - 136)) | (1L << (SAMPLER1DARRAY - 136)) | (1L << (SAMPLER2DARRAY - 136)) | (1L << (SAMPLER1DARRAYSHADOW - 136)) | (1L << (SAMPLER2DARRAYSHADOW - 136)) | (1L << (ISAMPLER1D - 136)) | (1L << (ISAMPLER2D - 136)) | (1L << (ISAMPLER2DRECT - 136)) | (1L << (ISAMPLER3D - 136)) | (1L << (ISAMPLER1DARRAY - 136)) | (1L << (ISAMPLER2DARRAY - 136)) | (1L << (USAMPLER1D - 136)) | (1L << (USAMPLER2D - 136)) | (1L << (USAMPLER2DRECT - 136)) | (1L << (USAMPLER3D - 136)) | (1L << (USAMPLER1DARRAY - 136)) | (1L << (USAMPLER2DARRAY - 136)) | (1L << (SAMPLER2DMS - 136)) | (1L << (ISAMPLER2DMS - 136)) | (1L << (USAMPLER2DMS - 136)) | (1L << (SAMPLER2DMSARRAY - 136)) | (1L << (ISAMPLER2DMSARRAY - 136)) | (1L << (USAMPLER2DMSARRAY - 136)) | (1L << (IMAGE2DRECT - 136)) | (1L << (IMAGE1DARRAY - 136)) | (1L << (IMAGE2DARRAY - 136)) | (1L << (IMAGE2DMS - 136)) | (1L << (IMAGE2DMSARRAY - 136)) | (1L << (IIMAGE2DRECT - 136)) | (1L << (IIMAGE1DARRAY - 136)) | (1L << (IIMAGE2DARRAY - 136)) | (1L << (IIMAGE2DMS - 136)) | (1L << (IIMAGE2DMSARRAY - 136)) | (1L << (UIMAGE2DRECT - 136)) | (1L << (UIMAGE1DARRAY - 136)) | (1L << (UIMAGE2DARRAY - 136)) | (1L << (UIMAGE2DMS - 136)) | (1L << (UIMAGE2DMSARRAY - 136)) | (1L << (LPAREN - 136)) | (1L << (PLUS_OP - 136)) | (1L << (MINUS_OP - 136)) | (1L << (NOT_OP - 136)) | (1L << (BNEG_OP - 136)) | (1L << (IDENTIFIER - 136)))) != 0)) {
				{
				setState(946);
				condition();
				}
			}

			setState(949);
			match(SEMICOLON);
			setState(951);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 18)) & ~0x3f) == 0 && ((1L << (_la - 18)) & ((1L << (INTCONSTANT - 18)) | (1L << (UINTCONSTANT - 18)) | (1L << (FLOATCONSTANT - 18)) | (1L << (BOOLCONSTANT - 18)) | (1L << (INC_OP - 18)) | (1L << (DEC_OP - 18)) | (1L << (VOID_TOK - 18)) | (1L << (FLOAT_TOK - 18)) | (1L << (INT_TOK - 18)) | (1L << (UINT_TOK - 18)) | (1L << (BOOL_TOK - 18)) | (1L << (SAMPLERCUBE - 18)) | (1L << (SAMPLERCUBESHADOW - 18)) | (1L << (SAMPLERBUFFER - 18)) | (1L << (SAMPLERCUBEARRAY - 18)) | (1L << (SAMPLERCUBEARRAYSHADOW - 18)) | (1L << (ISAMPLERCUBE - 18)) | (1L << (ISAMPLERBUFFER - 18)) | (1L << (ISAMPLERCUBEARRAY - 18)) | (1L << (USAMPLERCUBE - 18)) | (1L << (USAMPLERBUFFER - 18)) | (1L << (USAMPLERCUBEARRAY - 18)) | (1L << (IMAGECUBE - 18)) | (1L << (IMAGEBUFFER - 18)) | (1L << (IMAGECUBEARRAY - 18)) | (1L << (IIMAGECUBE - 18)))) != 0) || ((((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & ((1L << (IIMAGEBUFFER - 82)) | (1L << (IIMAGECUBEARRAY - 82)) | (1L << (UIMAGECUBE - 82)) | (1L << (UIMAGEBUFFER - 82)) | (1L << (UIMAGECUBEARRAY - 82)) | (1L << (ATOMIC_UINT - 82)) | (1L << (VEC2 - 82)) | (1L << (VEC3 - 82)) | (1L << (VEC4 - 82)) | (1L << (BVEC2 - 82)) | (1L << (BVEC3 - 82)) | (1L << (BVEC4 - 82)) | (1L << (IVEC2 - 82)) | (1L << (IVEC3 - 82)) | (1L << (IVEC4 - 82)) | (1L << (UVEC2 - 82)) | (1L << (UVEC3 - 82)) | (1L << (UVEC4 - 82)) | (1L << (MAT2X2 - 82)) | (1L << (MAT2X3 - 82)) | (1L << (MAT2X4 - 82)) | (1L << (MAT3X2 - 82)) | (1L << (MAT3X3 - 82)) | (1L << (MAT3X4 - 82)) | (1L << (MAT4X2 - 82)) | (1L << (MAT4X3 - 82)) | (1L << (MAT4X4 - 82)) | (1L << (IMAGE1D - 82)) | (1L << (IMAGE2D - 82)) | (1L << (IMAGE3D - 82)) | (1L << (UIMAGE1D - 82)) | (1L << (UIMAGE2D - 82)) | (1L << (UIMAGE3D - 82)) | (1L << (IIMAGE1D - 82)) | (1L << (IIMAGE2D - 82)) | (1L << (IIMAGE3D - 82)) | (1L << (SAMPLER1D - 82)) | (1L << (SAMPLER2D - 82)) | (1L << (SAMPLER3D - 82)) | (1L << (SAMPLER2DRECT - 82)) | (1L << (SAMPLEREXTERNALOES - 82)) | (1L << (SAMPLER1DSHADOW - 82)) | (1L << (SAMPLER2DSHADOW - 82)) | (1L << (SAMPLER2DRECTSHADOW - 82)) | (1L << (SAMPLER1DARRAY - 82)) | (1L << (SAMPLER2DARRAY - 82)) | (1L << (SAMPLER1DARRAYSHADOW - 82)) | (1L << (SAMPLER2DARRAYSHADOW - 82)) | (1L << (ISAMPLER1D - 82)) | (1L << (ISAMPLER2D - 82)) | (1L << (ISAMPLER2DRECT - 82)))) != 0) || ((((_la - 146)) & ~0x3f) == 0 && ((1L << (_la - 146)) & ((1L << (ISAMPLER3D - 146)) | (1L << (ISAMPLER1DARRAY - 146)) | (1L << (ISAMPLER2DARRAY - 146)) | (1L << (USAMPLER1D - 146)) | (1L << (USAMPLER2D - 146)) | (1L << (USAMPLER2DRECT - 146)) | (1L << (USAMPLER3D - 146)) | (1L << (USAMPLER1DARRAY - 146)) | (1L << (USAMPLER2DARRAY - 146)) | (1L << (SAMPLER2DMS - 146)) | (1L << (ISAMPLER2DMS - 146)) | (1L << (USAMPLER2DMS - 146)) | (1L << (SAMPLER2DMSARRAY - 146)) | (1L << (ISAMPLER2DMSARRAY - 146)) | (1L << (USAMPLER2DMSARRAY - 146)) | (1L << (IMAGE2DRECT - 146)) | (1L << (IMAGE1DARRAY - 146)) | (1L << (IMAGE2DARRAY - 146)) | (1L << (IMAGE2DMS - 146)) | (1L << (IMAGE2DMSARRAY - 146)) | (1L << (IIMAGE2DRECT - 146)) | (1L << (IIMAGE1DARRAY - 146)) | (1L << (IIMAGE2DARRAY - 146)) | (1L << (IIMAGE2DMS - 146)) | (1L << (IIMAGE2DMSARRAY - 146)) | (1L << (UIMAGE2DRECT - 146)) | (1L << (UIMAGE1DARRAY - 146)) | (1L << (UIMAGE2DARRAY - 146)) | (1L << (UIMAGE2DMS - 146)) | (1L << (UIMAGE2DMSARRAY - 146)) | (1L << (LPAREN - 146)) | (1L << (PLUS_OP - 146)) | (1L << (MINUS_OP - 146)) | (1L << (NOT_OP - 146)) | (1L << (BNEG_OP - 146)) | (1L << (IDENTIFIER - 146)))) != 0)) {
				{
				setState(950);
				expression();
				}
			}

			setState(953);
			match(RPAREN);
			setState(954);
			statement_no_short_if();
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

	public static class While_statementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GLSLOtherParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public While_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement; }
	}

	public final While_statementContext while_statement() throws RecognitionException {
		While_statementContext _localctx = new While_statementContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(956);
			match(WHILE);
			setState(957);
			match(LPAREN);
			setState(958);
			condition();
			setState(959);
			match(RPAREN);
			setState(960);
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

	public static class While_statement_no_short_ifContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GLSLOtherParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSLOtherParser.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSLOtherParser.RPAREN, 0); }
		public Statement_no_short_ifContext statement_no_short_if() {
			return getRuleContext(Statement_no_short_ifContext.class,0);
		}
		public While_statement_no_short_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement_no_short_if; }
	}

	public final While_statement_no_short_ifContext while_statement_no_short_if() throws RecognitionException {
		While_statement_no_short_ifContext _localctx = new While_statement_no_short_ifContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_while_statement_no_short_if);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(962);
			match(WHILE);
			setState(963);
			match(LPAREN);
			setState(964);
			condition();
			setState(965);
			match(RPAREN);
			setState(966);
			statement_no_short_if();
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

	public static class For_init_statementContext extends ParserRuleContext {
		public Empty_statementContext empty_statement() {
			return getRuleContext(Empty_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Declaration_statementContext declaration_statement() {
			return getRuleContext(Declaration_statementContext.class,0);
		}
		public For_init_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_init_statement; }
	}

	public final For_init_statementContext for_init_statement() throws RecognitionException {
		For_init_statementContext _localctx = new For_init_statementContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_for_init_statement);
		try {
			setState(971);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(968);
				empty_statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(969);
				expression_statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(970);
				declaration_statement();
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

	public static class Jump_statementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(GLSLOtherParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public TerminalNode BREAK() { return getToken(GLSLOtherParser.BREAK, 0); }
		public TerminalNode RETURN() { return getToken(GLSLOtherParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DISCARD() { return getToken(GLSLOtherParser.DISCARD, 0); }
		public Jump_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump_statement; }
	}

	public final Jump_statementContext jump_statement() throws RecognitionException {
		Jump_statementContext _localctx = new Jump_statementContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_jump_statement);
		try {
			setState(985);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(973);
				match(CONTINUE);
				setState(974);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(975);
				match(BREAK);
				setState(976);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(977);
				match(RETURN);
				setState(978);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(979);
				match(RETURN);
				setState(980);
				expression();
				setState(981);
				match(SEMICOLON);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(983);
				match(DISCARD);
				setState(984);
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

	public static class External_declarationContext extends ParserRuleContext {
		public Function_definitionContext function_definition() {
			return getRuleContext(Function_definitionContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public Pragma_statementContext pragma_statement() {
			return getRuleContext(Pragma_statementContext.class,0);
		}
		public Layout_defaultsContext layout_defaults() {
			return getRuleContext(Layout_defaultsContext.class,0);
		}
		public External_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_external_declaration; }
	}

	public final External_declarationContext external_declaration() throws RecognitionException {
		External_declarationContext _localctx = new External_declarationContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_external_declaration);
		try {
			setState(991);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(987);
				function_definition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(988);
				declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(989);
				pragma_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(990);
				layout_defaults();
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

	public static class Function_definitionContext extends ParserRuleContext {
		public Function_prototypeContext function_prototype() {
			return getRuleContext(Function_prototypeContext.class,0);
		}
		public Block_statementContext block_statement() {
			return getRuleContext(Block_statementContext.class,0);
		}
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_function_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(993);
			function_prototype();
			setState(994);
			block_statement();
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

	public static class Interface_blockContext extends ParserRuleContext {
		public Basic_interface_blockContext basic_interface_block() {
			return getRuleContext(Basic_interface_blockContext.class,0);
		}
		public Layout_qualifierContext layout_qualifier() {
			return getRuleContext(Layout_qualifierContext.class,0);
		}
		public Interface_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interface_block; }
	}

	public final Interface_blockContext interface_block() throws RecognitionException {
		Interface_blockContext _localctx = new Interface_blockContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_interface_block);
		try {
			setState(1000);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNIFORM:
			case BUFFER:
			case IN_TOK:
			case OUT_TOK:
			case COHERENT:
			case VOLATILE:
			case RESTRICT:
			case READONLY:
			case WRITEONLY:
				enterOuterAlt(_localctx, 1);
				{
				setState(996);
				basic_interface_block();
				}
				break;
			case LAYOUT_TOK:
				enterOuterAlt(_localctx, 2);
				{
				setState(997);
				layout_qualifier();
				setState(998);
				basic_interface_block();
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

	public static class Basic_interface_blockContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public TerminalNode LBRACE() { return getToken(GLSLOtherParser.LBRACE, 0); }
		public Member_listContext member_list() {
			return getRuleContext(Member_listContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSLOtherParser.RBRACE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public List<Interface_qualifierContext> interface_qualifier() {
			return getRuleContexts(Interface_qualifierContext.class);
		}
		public Interface_qualifierContext interface_qualifier(int i) {
			return getRuleContext(Interface_qualifierContext.class,i);
		}
		public Instance_nameContext instance_name() {
			return getRuleContext(Instance_nameContext.class,0);
		}
		public Basic_interface_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basic_interface_block; }
	}

	public final Basic_interface_blockContext basic_interface_block() throws RecognitionException {
		Basic_interface_blockContext _localctx = new Basic_interface_blockContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_basic_interface_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1003); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1002);
				interface_qualifier();
				}
				}
				setState(1005); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN_TOK) | (1L << OUT_TOK) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY))) != 0) );
			setState(1007);
			match(IDENTIFIER);
			setState(1008);
			match(LBRACE);
			setState(1009);
			member_list(0);
			setState(1010);
			match(RBRACE);
			setState(1012);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(1011);
				instance_name();
				}
			}

			setState(1014);
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

	public static class Interface_qualifierContext extends ParserRuleContext {
		public TerminalNode IN_TOK() { return getToken(GLSLOtherParser.IN_TOK, 0); }
		public TerminalNode OUT_TOK() { return getToken(GLSLOtherParser.OUT_TOK, 0); }
		public TerminalNode UNIFORM() { return getToken(GLSLOtherParser.UNIFORM, 0); }
		public TerminalNode BUFFER() { return getToken(GLSLOtherParser.BUFFER, 0); }
		public TerminalNode COHERENT() { return getToken(GLSLOtherParser.COHERENT, 0); }
		public TerminalNode VOLATILE() { return getToken(GLSLOtherParser.VOLATILE, 0); }
		public TerminalNode RESTRICT() { return getToken(GLSLOtherParser.RESTRICT, 0); }
		public TerminalNode READONLY() { return getToken(GLSLOtherParser.READONLY, 0); }
		public TerminalNode WRITEONLY() { return getToken(GLSLOtherParser.WRITEONLY, 0); }
		public Interface_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interface_qualifier; }
	}

	public final Interface_qualifierContext interface_qualifier() throws RecognitionException {
		Interface_qualifierContext _localctx = new Interface_qualifierContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_interface_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1016);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN_TOK) | (1L << OUT_TOK) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY))) != 0)) ) {
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

	public static class Instance_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSLOtherParser.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public Instance_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instance_name; }
	}

	public final Instance_nameContext instance_name() throws RecognitionException {
		Instance_nameContext _localctx = new Instance_nameContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_instance_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1018);
			match(IDENTIFIER);
			setState(1020);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(1019);
				array_specifier(0);
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

	public static class Layout_defaultsContext extends ParserRuleContext {
		public Layout_qualifierContext layout_qualifier() {
			return getRuleContext(Layout_qualifierContext.class,0);
		}
		public TerminalNode UNIFORM() { return getToken(GLSLOtherParser.UNIFORM, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSLOtherParser.SEMICOLON, 0); }
		public TerminalNode IN_TOK() { return getToken(GLSLOtherParser.IN_TOK, 0); }
		public TerminalNode OUT_TOK() { return getToken(GLSLOtherParser.OUT_TOK, 0); }
		public TerminalNode BUFFER() { return getToken(GLSLOtherParser.BUFFER, 0); }
		public Layout_defaultsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_defaults; }
	}

	public final Layout_defaultsContext layout_defaults() throws RecognitionException {
		Layout_defaultsContext _localctx = new Layout_defaultsContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_layout_defaults);
		try {
			setState(1038);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1022);
				layout_qualifier();
				setState(1023);
				match(UNIFORM);
				setState(1024);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1026);
				layout_qualifier();
				setState(1027);
				match(IN_TOK);
				setState(1028);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1030);
				layout_qualifier();
				setState(1031);
				match(OUT_TOK);
				setState(1032);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1034);
				layout_qualifier();
				setState(1035);
				match(BUFFER);
				setState(1036);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return extension_statement_list_sempred((Extension_statement_listContext)_localctx, predIndex);
		case 5:
			return external_declaration_list_sempred((External_declaration_listContext)_localctx, predIndex);
		case 8:
			return postfix_expression_sempred((Postfix_expressionContext)_localctx, predIndex);
		case 12:
			return function_call_header_with_parameters_sempred((Function_call_header_with_parametersContext)_localctx, predIndex);
		case 17:
			return method_call_header_with_parameters_sempred((Method_call_header_with_parametersContext)_localctx, predIndex);
		case 40:
			return function_header_with_parameters_sempred((Function_header_with_parametersContext)_localctx, predIndex);
		case 47:
			return init_declarator_list_sempred((Init_declarator_listContext)_localctx, predIndex);
		case 51:
			return layout_qualifier_id_list_sempred((Layout_qualifier_id_listContext)_localctx, predIndex);
		case 59:
			return array_specifier_sempred((Array_specifierContext)_localctx, predIndex);
		case 65:
			return member_list_sempred((Member_listContext)_localctx, predIndex);
		case 67:
			return struct_declarator_list_sempred((Struct_declarator_listContext)_localctx, predIndex);
		case 70:
			return initializer_list_sempred((Initializer_listContext)_localctx, predIndex);
		case 86:
			return case_label_list_sempred((Case_label_listContext)_localctx, predIndex);
		case 87:
			return case_statement_sempred((Case_statementContext)_localctx, predIndex);
		case 88:
			return case_statement_list_sempred((Case_statement_listContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean extension_statement_list_sempred(Extension_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean external_declaration_list_sempred(External_declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean postfix_expression_sempred(Postfix_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean function_call_header_with_parameters_sempred(Function_call_header_with_parametersContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean method_call_header_with_parameters_sempred(Method_call_header_with_parametersContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean function_header_with_parameters_sempred(Function_header_with_parametersContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean init_declarator_list_sempred(Init_declarator_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 4);
		case 12:
			return precpred(_ctx, 3);
		case 13:
			return precpred(_ctx, 2);
		case 14:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean layout_qualifier_id_list_sempred(Layout_qualifier_id_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean array_specifier_sempred(Array_specifierContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 2);
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean member_list_sempred(Member_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean struct_declarator_list_sempred(Struct_declarator_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean initializer_list_sempred(Initializer_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean case_label_list_sempred(Case_label_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 21:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean case_statement_sempred(Case_statementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 22:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean case_statement_list_sempred(Case_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 23:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u00cc\u0413\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00dd\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\5\4\u00e9\n\4\3\5\3\5\3\5\7\5\u00ee\n\5\f\5\16\5\u00f1"+
		"\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u0100\n"+
		"\7\f\7\16\7\u0103\13\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t"+
		"\u0110\n\t\3\n\3\n\3\n\5\n\u0115\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0126\n\n\f\n\16\n\u0129\13\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0133\n\f\3\r\3\r\3\r\3\r\5\r\u0139\n\r\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u0142\n\16\f\16\16\16\u0145\13"+
		"\16\3\17\3\17\5\17\u0149\n\17\3\17\3\17\3\20\3\20\5\20\u014f\n\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u0157\n\21\3\22\3\22\3\22\3\22\5\22\u015d"+
		"\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u0166\n\23\f\23\16\23\u0169"+
		"\13\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0176"+
		"\n\25\3\26\3\26\3\27\3\27\3\27\3\27\5\27\u017e\n\27\3\27\7\27\u0181\n"+
		"\27\f\27\16\27\u0184\13\27\3\30\3\30\3\30\5\30\u0189\n\30\3\30\7\30\u018c"+
		"\n\30\f\30\16\30\u018f\13\30\3\31\3\31\3\31\5\31\u0194\n\31\3\31\7\31"+
		"\u0197\n\31\f\31\16\31\u019a\13\31\3\32\3\32\3\32\3\32\3\32\5\32\u01a1"+
		"\n\32\3\32\7\32\u01a4\n\32\f\32\16\32\u01a7\13\32\3\33\3\33\3\33\5\33"+
		"\u01ac\n\33\3\33\7\33\u01af\n\33\f\33\16\33\u01b2\13\33\3\34\3\34\3\34"+
		"\7\34\u01b7\n\34\f\34\16\34\u01ba\13\34\3\35\3\35\3\35\7\35\u01bf\n\35"+
		"\f\35\16\35\u01c2\13\35\3\36\3\36\3\36\7\36\u01c7\n\36\f\36\16\36\u01ca"+
		"\13\36\3\37\3\37\3\37\7\37\u01cf\n\37\f\37\16\37\u01d2\13\37\3 \3 \3 "+
		"\7 \u01d7\n \f \16 \u01da\13 \3!\3!\3!\7!\u01df\n!\f!\16!\u01e2\13!\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\7\"\u01ea\n\"\f\"\16\"\u01ed\13\"\3#\3#\3#\3#\3"+
		"#\5#\u01f4\n#\3$\3$\3%\3%\3%\7%\u01fb\n%\f%\16%\u01fe\13%\3&\3&\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u020e\n\'\3(\3(\3(\3)\3"+
		")\5)\u0215\n)\3*\3*\3*\3*\3*\3*\3*\7*\u021e\n*\f*\16*\u0221\13*\3+\3+"+
		"\3+\3+\3,\3,\3,\3,\3,\3,\3,\5,\u022e\n,\3-\3-\3-\3-\3-\3-\5-\u0236\n-"+
		"\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u0243\n.\3/\3/\3\60\3\60\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\7\61\u025f\n\61\f\61\16\61\u0262\13\61"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\5\62\u027b\n\62\3\63\3\63"+
		"\3\63\3\63\5\63\u0281\n\63\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\7\65\u028e\n\65\f\65\16\65\u0291\13\65\3\66\3\66\3\67\3\67"+
		"\3\67\3\67\3\67\5\67\u029a\n\67\38\38\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3"+
		":\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\5:\u02ba\n:\3;\3"+
		";\3<\3<\3=\3=\3=\3=\3=\3=\3=\5=\u02c7\n=\3=\3=\3=\3=\3=\3=\3=\3=\7=\u02d1"+
		"\n=\f=\16=\u02d4\13=\3>\3>\3>\3>\5>\u02da\n>\3?\3?\3?\5?\u02df\n?\3@\3"+
		"@\3A\3A\3B\3B\5B\u02e7\nB\3B\3B\3B\3B\3C\3C\3C\3C\3C\7C\u02f2\nC\fC\16"+
		"C\u02f5\13C\3D\3D\3D\3D\3E\3E\3E\3E\3E\3E\7E\u0301\nE\fE\16E\u0304\13"+
		"E\3F\3F\3F\5F\u0309\nF\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\5G\u0315\nG\3H\3"+
		"H\3H\3H\3H\3H\7H\u031d\nH\fH\16H\u0320\13H\3I\3I\3J\3J\3J\3J\3J\5J\u0329"+
		"\nJ\3K\3K\3K\3K\5K\u032f\nK\3L\3L\3L\3L\3L\3L\3L\5L\u0338\nL\3M\3M\5M"+
		"\u033c\nM\3M\3M\3N\6N\u0341\nN\rN\16N\u0342\3O\3O\3O\3P\3P\3Q\3Q\3Q\3"+
		"Q\3Q\3Q\3R\3R\3R\3R\3R\3R\3R\3R\3S\3S\3S\3S\3S\3S\3S\3S\3T\3T\3T\3T\3"+
		"T\3T\5T\u0366\nT\3U\3U\3U\3U\3U\3U\3V\3V\3V\3V\3V\3V\5V\u0374\nV\3W\3"+
		"W\3W\3W\3W\3W\5W\u037c\nW\3X\3X\3X\3X\3X\7X\u0383\nX\fX\16X\u0386\13X"+
		"\3Y\3Y\3Y\3Y\3Y\3Y\7Y\u038e\nY\fY\16Y\u0391\13Y\3Z\3Z\3Z\3Z\3Z\7Z\u0398"+
		"\nZ\fZ\16Z\u039b\13Z\3[\3[\3[\3[\3[\3[\3[\3[\3\\\3\\\3\\\3\\\5\\\u03a9"+
		"\n\\\3\\\3\\\5\\\u03ad\n\\\3\\\3\\\3\\\3]\3]\3]\3]\5]\u03b6\n]\3]\3]\5"+
		"]\u03ba\n]\3]\3]\3]\3^\3^\3^\3^\3^\3^\3_\3_\3_\3_\3_\3_\3`\3`\3`\5`\u03ce"+
		"\n`\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\5a\u03dc\na\3b\3b\3b\3b\5b\u03e2"+
		"\nb\3c\3c\3c\3d\3d\3d\3d\5d\u03eb\nd\3e\6e\u03ee\ne\re\16e\u03ef\3e\3"+
		"e\3e\3e\3e\5e\u03f7\ne\3e\3e\3f\3f\3g\3g\5g\u03ff\ng\3h\3h\3h\3h\3h\3"+
		"h\3h\3h\3h\3h\3h\3h\3h\3h\3h\3h\5h\u0411\nh\3h\2\21\b\f\22\32$R`hx\u0084"+
		"\u0088\u008e\u00ae\u00b0\u00b2i\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082"+
		"\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a"+
		"\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2"+
		"\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca"+
		"\u00cc\u00ce\2\r\3\2\u00bb\u00be\4\2\67@\u00c8\u00c8\3\2\f\16\4\2\24\24"+
		"&&\3\2\'(\3\2\30\32\3\2\33\34\6\2\n\n\f\r\25\25\35$\5\2--AYg\u00b1\3\2"+
		"\17\21\5\2\n\r\36 \"#\2\u043c\2\u00d0\3\2\2\2\4\u00dc\3\2\2\2\6\u00e8"+
		"\3\2\2\2\b\u00ea\3\2\2\2\n\u00f2\3\2\2\2\f\u00f8\3\2\2\2\16\u0104\3\2"+
		"\2\2\20\u010f\3\2\2\2\22\u0114\3\2\2\2\24\u012a\3\2\2\2\26\u0132\3\2\2"+
		"\2\30\u0138\3\2\2\2\32\u013a\3\2\2\2\34\u0146\3\2\2\2\36\u014e\3\2\2\2"+
		" \u0156\3\2\2\2\"\u015c\3\2\2\2$\u015e\3\2\2\2&\u016a\3\2\2\2(\u0175\3"+
		"\2\2\2*\u0177\3\2\2\2,\u0179\3\2\2\2.\u0185\3\2\2\2\60\u0190\3\2\2\2\62"+
		"\u019b\3\2\2\2\64\u01a8\3\2\2\2\66\u01b3\3\2\2\28\u01bb\3\2\2\2:\u01c3"+
		"\3\2\2\2<\u01cb\3\2\2\2>\u01d3\3\2\2\2@\u01db\3\2\2\2B\u01e3\3\2\2\2D"+
		"\u01f3\3\2\2\2F\u01f5\3\2\2\2H\u01f7\3\2\2\2J\u01ff\3\2\2\2L\u020d\3\2"+
		"\2\2N\u020f\3\2\2\2P\u0214\3\2\2\2R\u0216\3\2\2\2T\u0222\3\2\2\2V\u022d"+
		"\3\2\2\2X\u0235\3\2\2\2Z\u0242\3\2\2\2\\\u0244\3\2\2\2^\u0246\3\2\2\2"+
		"`\u0248\3\2\2\2b\u027a\3\2\2\2d\u0280\3\2\2\2f\u0282\3\2\2\2h\u0287\3"+
		"\2\2\2j\u0292\3\2\2\2l\u0299\3\2\2\2n\u029b\3\2\2\2p\u029d\3\2\2\2r\u02b9"+
		"\3\2\2\2t\u02bb\3\2\2\2v\u02bd\3\2\2\2x\u02c6\3\2\2\2z\u02d9\3\2\2\2|"+
		"\u02de\3\2\2\2~\u02e0\3\2\2\2\u0080\u02e2\3\2\2\2\u0082\u02e4\3\2\2\2"+
		"\u0084\u02ec\3\2\2\2\u0086\u02f6\3\2\2\2\u0088\u02fa\3\2\2\2\u008a\u0308"+
		"\3\2\2\2\u008c\u0314\3\2\2\2\u008e\u0316\3\2\2\2\u0090\u0321\3\2\2\2\u0092"+
		"\u0328\3\2\2\2\u0094\u032e\3\2\2\2\u0096\u0337\3\2\2\2\u0098\u0339\3\2"+
		"\2\2\u009a\u0340\3\2\2\2\u009c\u0344\3\2\2\2\u009e\u0347\3\2\2\2\u00a0"+
		"\u0349\3\2\2\2\u00a2\u034f\3\2\2\2\u00a4\u0357\3\2\2\2\u00a6\u0365\3\2"+
		"\2\2\u00a8\u0367\3\2\2\2\u00aa\u0373\3\2\2\2\u00ac\u037b\3\2\2\2\u00ae"+
		"\u037d\3\2\2\2\u00b0\u0387\3\2\2\2\u00b2\u0392\3\2\2\2\u00b4\u039c\3\2"+
		"\2\2\u00b6\u03a4\3\2\2\2\u00b8\u03b1\3\2\2\2\u00ba\u03be\3\2\2\2\u00bc"+
		"\u03c4\3\2\2\2\u00be\u03cd\3\2\2\2\u00c0\u03db\3\2\2\2\u00c2\u03e1\3\2"+
		"\2\2\u00c4\u03e3\3\2\2\2\u00c6\u03ea\3\2\2\2\u00c8\u03ed\3\2\2\2\u00ca"+
		"\u03fa\3\2\2\2\u00cc\u03fc\3\2\2\2\u00ce\u0410\3\2\2\2\u00d0\u00d1\5\4"+
		"\3\2\u00d1\u00d2\5\b\5\2\u00d2\u00d3\5\f\7\2\u00d3\3\3\2\2\2\u00d4\u00dd"+
		"\3\2\2\2\u00d5\u00d6\7\23\2\2\u00d6\u00d7\7\24\2\2\u00d7\u00dd\7\u00cc"+
		"\2\2\u00d8\u00d9\7\23\2\2\u00d9\u00da\7\24\2\2\u00da\u00db\7\u00c9\2\2"+
		"\u00db\u00dd\7\u00cc\2\2\u00dc\u00d4\3\2\2\2\u00dc\u00d5\3\2\2\2\u00dc"+
		"\u00d8\3\2\2\2\u00dd\5\3\2\2\2\u00de\u00df\7\3\2\2\u00df\u00e9\7\u00cc"+
		"\2\2\u00e0\u00e1\7\4\2\2\u00e1\u00e9\7\u00cc\2\2\u00e2\u00e3\7\5\2\2\u00e3"+
		"\u00e9\7\u00cc\2\2\u00e4\u00e5\7\6\2\2\u00e5\u00e9\7\u00cc\2\2\u00e6\u00e7"+
		"\7\7\2\2\u00e7\u00e9\7\u00cc\2\2\u00e8\u00de\3\2\2\2\u00e8\u00e0\3\2\2"+
		"\2\u00e8\u00e2\3\2\2\2\u00e8\u00e4\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\7"+
		"\3\2\2\2\u00ea\u00ef\b\5\1\2\u00eb\u00ec\f\3\2\2\u00ec\u00ee\5\n\6\2\u00ed"+
		"\u00eb\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f0\t\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7\b\2\2\u00f3\u00f4"+
		"\7\u00c9\2\2\u00f4\u00f5\7\t\2\2\u00f5\u00f6\7\u00c9\2\2\u00f6\u00f7\7"+
		"\u00cc\2\2\u00f7\13\3\2\2\2\u00f8\u00f9\b\7\1\2\u00f9\u00fa\5\u00c2b\2"+
		"\u00fa\u0101\3\2\2\2\u00fb\u00fc\f\4\2\2\u00fc\u0100\5\u00c2b\2\u00fd"+
		"\u00fe\f\3\2\2\u00fe\u0100\5\n\6\2\u00ff\u00fb\3\2\2\2\u00ff\u00fd\3\2"+
		"\2\2\u0100\u0103\3\2\2\2\u0101\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102"+
		"\r\3\2\2\2\u0103\u0101\3\2\2\2\u0104\u0105\7\u00c9\2\2\u0105\17\3\2\2"+
		"\2\u0106\u0110\5\16\b\2\u0107\u0110\7\24\2\2\u0108\u0110\7&\2\2\u0109"+
		"\u0110\7)\2\2\u010a\u0110\7*\2\2\u010b\u010c\7\u00b2\2\2\u010c\u010d\5"+
		"H%\2\u010d\u010e\7\u00b3\2\2\u010e\u0110\3\2\2\2\u010f\u0106\3\2\2\2\u010f"+
		"\u0107\3\2\2\2\u010f\u0108\3\2\2\2\u010f\u0109\3\2\2\2\u010f\u010a\3\2"+
		"\2\2\u010f\u010b\3\2\2\2\u0110\21\3\2\2\2\u0111\u0112\b\n\1\2\u0112\u0115"+
		"\5\20\t\2\u0113\u0115\5\26\f\2\u0114\u0111\3\2\2\2\u0114\u0113\3\2\2\2"+
		"\u0115\u0127\3\2\2\2\u0116\u0117\f\b\2\2\u0117\u0118\7\u00b7\2\2\u0118"+
		"\u0119\5\24\13\2\u0119\u011a\7\u00b8\2\2\u011a\u0126\3\2\2\2\u011b\u011c"+
		"\f\7\2\2\u011c\u011d\7\u00ba\2\2\u011d\u0126\5 \21\2\u011e\u011f\f\6\2"+
		"\2\u011f\u0120\7\u00ba\2\2\u0120\u0126\7\u00c9\2\2\u0121\u0122\f\5\2\2"+
		"\u0122\u0126\7+\2\2\u0123\u0124\f\4\2\2\u0124\u0126\7,\2\2\u0125\u0116"+
		"\3\2\2\2\u0125\u011b\3\2\2\2\u0125\u011e\3\2\2\2\u0125\u0121\3\2\2\2\u0125"+
		"\u0123\3\2\2\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2"+
		"\2\2\u0128\23\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u012b\5H%\2\u012b\25\3"+
		"\2\2\2\u012c\u012d\5\32\16\2\u012d\u012e\7\u00b3\2\2\u012e\u0133\3\2\2"+
		"\2\u012f\u0130\5\30\r\2\u0130\u0131\7\u00b3\2\2\u0131\u0133\3\2\2\2\u0132"+
		"\u012c\3\2\2\2\u0132\u012f\3\2\2\2\u0133\27\3\2\2\2\u0134\u0135\5\34\17"+
		"\2\u0135\u0136\7-\2\2\u0136\u0139\3\2\2\2\u0137\u0139\5\34\17\2\u0138"+
		"\u0134\3\2\2\2\u0138\u0137\3\2\2\2\u0139\31\3\2\2\2\u013a\u013b\b\16\1"+
		"\2\u013b\u013c\5\34\17\2\u013c\u013d\5D#\2\u013d\u0143\3\2\2\2\u013e\u013f"+
		"\f\3\2\2\u013f\u0140\7\u00b9\2\2\u0140\u0142\5D#\2\u0141\u013e\3\2\2\2"+
		"\u0142\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\33"+
		"\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u0148\5\36\20\2\u0147\u0149\5x=\2\u0148"+
		"\u0147\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b\7\u00b2"+
		"\2\2\u014b\35\3\2\2\2\u014c\u014f\5~@\2\u014d\u014f\5\16\b\2\u014e\u014c"+
		"\3\2\2\2\u014e\u014d\3\2\2\2\u014f\37\3\2\2\2\u0150\u0151\5$\23\2\u0151"+
		"\u0152\7\u00b3\2\2\u0152\u0157\3\2\2\2\u0153\u0154\5\"\22\2\u0154\u0155"+
		"\7\u00b3\2\2\u0155\u0157\3\2\2\2\u0156\u0150\3\2\2\2\u0156\u0153\3\2\2"+
		"\2\u0157!\3\2\2\2\u0158\u0159\5&\24\2\u0159\u015a\7-\2\2\u015a\u015d\3"+
		"\2\2\2\u015b\u015d\5&\24\2\u015c\u0158\3\2\2\2\u015c\u015b\3\2\2\2\u015d"+
		"#\3\2\2\2\u015e\u015f\b\23\1\2\u015f\u0160\5&\24\2\u0160\u0161\5D#\2\u0161"+
		"\u0167\3\2\2\2\u0162\u0163\f\3\2\2\u0163\u0164\7\u00b9\2\2\u0164\u0166"+
		"\5D#\2\u0165\u0162\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0167"+
		"\u0168\3\2\2\2\u0168%\3\2\2\2\u0169\u0167\3\2\2\2\u016a\u016b\5\16\b\2"+
		"\u016b\u016c\7\u00b2\2\2\u016c\'\3\2\2\2\u016d\u0176\5\22\n\2\u016e\u016f"+
		"\7+\2\2\u016f\u0176\5(\25\2\u0170\u0171\7,\2\2\u0171\u0176\5(\25\2\u0172"+
		"\u0173\5*\26\2\u0173\u0174\5(\25\2\u0174\u0176\3\2\2\2\u0175\u016d\3\2"+
		"\2\2\u0175\u016e\3\2\2\2\u0175\u0170\3\2\2\2\u0175\u0172\3\2\2\2\u0176"+
		")\3\2\2\2\u0177\u0178\t\2\2\2\u0178+\3\2\2\2\u0179\u0182\5(\25\2\u017a"+
		"\u017e\7\u00bf\2\2\u017b\u017e\7\u00c0\2\2\u017c\u017e\7\u00c1\2\2\u017d"+
		"\u017a\3\2\2\2\u017d\u017b\3\2\2\2\u017d\u017c\3\2\2\2\u017e\u017f\3\2"+
		"\2\2\u017f\u0181\5(\25\2\u0180\u017d\3\2\2\2\u0181\u0184\3\2\2\2\u0182"+
		"\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183-\3\2\2\2\u0184\u0182\3\2\2\2"+
		"\u0185\u018d\5,\27\2\u0186\u0189\7\u00bb\2\2\u0187\u0189\7\u00bc\2\2\u0188"+
		"\u0186\3\2\2\2\u0188\u0187\3\2\2\2\u0189\u018a\3\2\2\2\u018a\u018c\5,"+
		"\27\2\u018b\u0188\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e/\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0198\5.\30\2"+
		"\u0191\u0194\7.\2\2\u0192\u0194\7/\2\2\u0193\u0191\3\2\2\2\u0193\u0192"+
		"\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0197\5.\30\2\u0196\u0193\3\2\2\2\u0197"+
		"\u019a\3\2\2\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\61\3\2\2"+
		"\2\u019a\u0198\3\2\2\2\u019b\u01a5\5\60\31\2\u019c\u01a1\7\u00c2\2\2\u019d"+
		"\u01a1\7\u00c3\2\2\u019e\u01a1\7\60\2\2\u019f\u01a1\7\61\2\2\u01a0\u019c"+
		"\3\2\2\2\u01a0\u019d\3\2\2\2\u01a0\u019e\3\2\2\2\u01a0\u019f\3\2\2\2\u01a1"+
		"\u01a2\3\2\2\2\u01a2\u01a4\5\60\31\2\u01a3\u01a0\3\2\2\2\u01a4\u01a7\3"+
		"\2\2\2\u01a5\u01a3\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\63\3\2\2\2\u01a7"+
		"\u01a5\3\2\2\2\u01a8\u01b0\5\62\32\2\u01a9\u01ac\7\62\2\2\u01aa\u01ac"+
		"\7\63\2\2\u01ab\u01a9\3\2\2\2\u01ab\u01aa\3\2\2\2\u01ac\u01ad\3\2\2\2"+
		"\u01ad\u01af\5\62\32\2\u01ae\u01ab\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0\u01ae"+
		"\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\65\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b3"+
		"\u01b8\5\64\33\2\u01b4\u01b5\7\u00c4\2\2\u01b5\u01b7\5\64\33\2\u01b6\u01b4"+
		"\3\2\2\2\u01b7\u01ba\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3\2\2\2\u01b9"+
		"\67\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01c0\5\66\34\2\u01bc\u01bd\7\u00c6"+
		"\2\2\u01bd\u01bf\5\66\34\2\u01be\u01bc\3\2\2\2\u01bf\u01c2\3\2\2\2\u01c0"+
		"\u01be\3\2\2\2\u01c0\u01c1\3\2\2\2\u01c19\3\2\2\2\u01c2\u01c0\3\2\2\2"+
		"\u01c3\u01c8\58\35\2\u01c4\u01c5\7\u00c5\2\2\u01c5\u01c7\58\35\2\u01c6"+
		"\u01c4\3\2\2\2\u01c7\u01ca\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c8\u01c9\3\2"+
		"\2\2\u01c9;\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u01d0\5:\36\2\u01cc\u01cd"+
		"\7\64\2\2\u01cd\u01cf\5:\36\2\u01ce\u01cc\3\2\2\2\u01cf\u01d2\3\2\2\2"+
		"\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1=\3\2\2\2\u01d2\u01d0\3"+
		"\2\2\2\u01d3\u01d8\5<\37\2\u01d4\u01d5\7\65\2\2\u01d5\u01d7\5<\37\2\u01d6"+
		"\u01d4\3\2\2\2\u01d7\u01da\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d8\u01d9\3\2"+
		"\2\2\u01d9?\3\2\2\2\u01da\u01d8\3\2\2\2\u01db\u01e0\5> \2\u01dc\u01dd"+
		"\7\66\2\2\u01dd\u01df\5> \2\u01de\u01dc\3\2\2\2\u01df\u01e2\3\2\2\2\u01e0"+
		"\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1A\3\2\2\2\u01e2\u01e0\3\2\2\2"+
		"\u01e3\u01eb\5@!\2\u01e4\u01e5\7\u00c7\2\2\u01e5\u01e6\5H%\2\u01e6\u01e7"+
		"\7\t\2\2\u01e7\u01e8\5D#\2\u01e8\u01ea\3\2\2\2\u01e9\u01e4\3\2\2\2\u01ea"+
		"\u01ed\3\2\2\2\u01eb\u01e9\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ecC\3\2\2\2"+
		"\u01ed\u01eb\3\2\2\2\u01ee\u01f4\5B\"\2\u01ef\u01f0\5(\25\2\u01f0\u01f1"+
		"\5F$\2\u01f1\u01f2\5D#\2\u01f2\u01f4\3\2\2\2\u01f3\u01ee\3\2\2\2\u01f3"+
		"\u01ef\3\2\2\2\u01f4E\3\2\2\2\u01f5\u01f6\t\3\2\2\u01f6G\3\2\2\2\u01f7"+
		"\u01fc\5D#\2\u01f8\u01f9\7\u00b9\2\2\u01f9\u01fb\5D#\2\u01fa\u01f8\3\2"+
		"\2\2\u01fb\u01fe\3\2\2\2\u01fc\u01fa\3\2\2\2\u01fc\u01fd\3\2\2\2\u01fd"+
		"I\3\2\2\2\u01fe\u01fc\3\2\2\2\u01ff\u0200\5B\"\2\u0200K\3\2\2\2\u0201"+
		"\u0202\5N(\2\u0202\u0203\7\u00b6\2\2\u0203\u020e\3\2\2\2\u0204\u0205\5"+
		"`\61\2\u0205\u0206\7\u00b6\2\2\u0206\u020e\3\2\2\2\u0207\u0208\7\22\2"+
		"\2\u0208\u0209\5\u0080A\2\u0209\u020a\5z>\2\u020a\u020b\7\u00b6\2\2\u020b"+
		"\u020e\3\2\2\2\u020c\u020e\5\u00c6d\2\u020d\u0201\3\2\2\2\u020d\u0204"+
		"\3\2\2\2\u020d\u0207\3\2\2\2\u020d\u020c\3\2\2\2\u020eM\3\2\2\2\u020f"+
		"\u0210\5P)\2\u0210\u0211\7\u00b3\2\2\u0211O\3\2\2\2\u0212\u0215\5T+\2"+
		"\u0213\u0215\5R*\2\u0214\u0212\3\2\2\2\u0214\u0213\3\2\2\2\u0215Q\3\2"+
		"\2\2\u0216\u0217\b*\1\2\u0217\u0218\5T+\2\u0218\u0219\5X-\2\u0219\u021f"+
		"\3\2\2\2\u021a\u021b\f\3\2\2\u021b\u021c\7\u00b9\2\2\u021c\u021e\5X-\2"+
		"\u021d\u021a\3\2\2\2\u021e\u0221\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220"+
		"\3\2\2\2\u0220S\3\2\2\2\u0221\u021f\3\2\2\2\u0222\u0223\5d\63\2\u0223"+
		"\u0224\5\16\b\2\u0224\u0225\7\u00b2\2\2\u0225U\3\2\2\2\u0226\u0227\5z"+
		">\2\u0227\u0228\7\u00c9\2\2\u0228\u022e\3\2\2\2\u0229\u022a\5z>\2\u022a"+
		"\u022b\7\u00c9\2\2\u022b\u022c\5x=\2\u022c\u022e\3\2\2\2\u022d\u0226\3"+
		"\2\2\2\u022d\u0229\3\2\2\2\u022eW\3\2\2\2\u022f\u0230\5Z.\2\u0230\u0231"+
		"\5V,\2\u0231\u0236\3\2\2\2\u0232\u0233\5Z.\2\u0233\u0234\5^\60\2\u0234"+
		"\u0236\3\2\2\2\u0235\u022f\3\2\2\2\u0235\u0232\3\2\2\2\u0236Y\3\2\2\2"+
		"\u0237\u0243\3\2\2\2\u0238\u0239\7\25\2\2\u0239\u0243\5Z.\2\u023a\u023b"+
		"\7\26\2\2\u023b\u0243\5Z.\2\u023c\u023d\5\\/\2\u023d\u023e\5Z.\2\u023e"+
		"\u0243\3\2\2\2\u023f\u0240\5\u0080A\2\u0240\u0241\5Z.\2\u0241\u0243\3"+
		"\2\2\2\u0242\u0237\3\2\2\2\u0242\u0238\3\2\2\2\u0242\u023a\3\2\2\2\u0242"+
		"\u023c\3\2\2\2\u0242\u023f\3\2\2\2\u0243[\3\2\2\2\u0244\u0245\t\4\2\2"+
		"\u0245]\3\2\2\2\u0246\u0247\5z>\2\u0247_\3\2\2\2\u0248\u0249\b\61\1\2"+
		"\u0249\u024a\5b\62\2\u024a\u0260\3\2\2\2\u024b\u024c\f\6\2\2\u024c\u024d"+
		"\7\u00b9\2\2\u024d\u025f\7\u00c9\2\2\u024e\u024f\f\5\2\2\u024f\u0250\7"+
		"\u00b9\2\2\u0250\u0251\7\u00c9\2\2\u0251\u025f\5x=\2\u0252\u0253\f\4\2"+
		"\2\u0253\u0254\7\u00b9\2\2\u0254\u0255\7\u00c9\2\2\u0255\u0256\5x=\2\u0256"+
		"\u0257\7\u00c8\2\2\u0257\u0258\5\u008cG\2\u0258\u025f\3\2\2\2\u0259\u025a"+
		"\f\3\2\2\u025a\u025b\7\u00b9\2\2\u025b\u025c\7\u00c9\2\2\u025c\u025d\7"+
		"\u00c8\2\2\u025d\u025f\5\u008cG\2\u025e\u024b\3\2\2\2\u025e\u024e\3\2"+
		"\2\2\u025e\u0252\3\2\2\2\u025e\u0259\3\2\2\2\u025f\u0262\3\2\2\2\u0260"+
		"\u025e\3\2\2\2\u0260\u0261\3\2\2\2\u0261a\3\2\2\2\u0262\u0260\3\2\2\2"+
		"\u0263\u027b\5d\63\2\u0264\u0265\5d\63\2\u0265\u0266\7\u00c9\2\2\u0266"+
		"\u027b\3\2\2\2\u0267\u0268\5d\63\2\u0268\u0269\7\u00c9\2\2\u0269\u026a"+
		"\5x=\2\u026a\u027b\3\2\2\2\u026b\u026c\5d\63\2\u026c\u026d\7\u00c9\2\2"+
		"\u026d\u026e\5x=\2\u026e\u026f\7\u00c8\2\2\u026f\u0270\5\u008cG\2\u0270"+
		"\u027b\3\2\2\2\u0271\u0272\5d\63\2\u0272\u0273\7\u00c9\2\2\u0273\u0274"+
		"\7\u00c8\2\2\u0274\u0275\5\u008cG\2\u0275\u027b\3\2\2\2\u0276\u0277\7"+
		"\27\2\2\u0277\u027b\5\16\b\2\u0278\u0279\7\26\2\2\u0279\u027b\5\16\b\2"+
		"\u027a\u0263\3\2\2\2\u027a\u0264\3\2\2\2\u027a\u0267\3\2\2\2\u027a\u026b"+
		"\3\2\2\2\u027a\u0271\3\2\2\2\u027a\u0276\3\2\2\2\u027a\u0278\3\2\2\2\u027b"+
		"c\3\2\2\2\u027c\u0281\5z>\2\u027d\u027e\5r:\2\u027e\u027f\5z>\2\u027f"+
		"\u0281\3\2\2\2\u0280\u027c\3\2\2\2\u0280\u027d\3\2\2\2\u0281e\3\2\2\2"+
		"\u0282\u0283\7%\2\2\u0283\u0284\7\u00b2\2\2\u0284\u0285\5h\65\2\u0285"+
		"\u0286\7\u00b3\2\2\u0286g\3\2\2\2\u0287\u0288\b\65\1\2\u0288\u0289\5l"+
		"\67\2\u0289\u028f\3\2\2\2\u028a\u028b\f\3\2\2\u028b\u028c\7\u00b9\2\2"+
		"\u028c\u028e\5l\67\2\u028d\u028a\3\2\2\2\u028e\u0291\3\2\2\2\u028f\u028d"+
		"\3\2\2\2\u028f\u0290\3\2\2\2\u0290i\3\2\2\2\u0291\u028f\3\2\2\2\u0292"+
		"\u0293\t\5\2\2\u0293k\3\2\2\2\u0294\u029a\7\u00c9\2\2\u0295\u0296\7\u00c9"+
		"\2\2\u0296\u0297\7\u00c8\2\2\u0297\u029a\5j\66\2\u0298\u029a\5n8\2\u0299"+
		"\u0294\3\2\2\2\u0299\u0295\3\2\2\2\u0299\u0298\3\2\2\2\u029am\3\2\2\2"+
		"\u029b\u029c\t\6\2\2\u029co\3\2\2\2\u029d\u029e\t\7\2\2\u029eq\3\2\2\2"+
		"\u029f\u02ba\7\27\2\2\u02a0\u02ba\7\26\2\2\u02a1\u02ba\5t;\2\u02a2\u02ba"+
		"\5v<\2\u02a3\u02ba\5p9\2\u02a4\u02ba\5f\64\2\u02a5\u02ba\5\u0080A\2\u02a6"+
		"\u02a7\7\26\2\2\u02a7\u02ba\5r:\2\u02a8\u02a9\7\27\2\2\u02a9\u02ba\5r"+
		":\2\u02aa\u02ab\5p9\2\u02ab\u02ac\5r:\2\u02ac\u02ba\3\2\2\2\u02ad\u02ae"+
		"\5f\64\2\u02ae\u02af\5r:\2\u02af\u02ba\3\2\2\2\u02b0\u02b1\5t;\2\u02b1"+
		"\u02b2\5r:\2\u02b2\u02ba\3\2\2\2\u02b3\u02b4\5v<\2\u02b4\u02b5\5r:\2\u02b5"+
		"\u02ba\3\2\2\2\u02b6\u02b7\5\u0080A\2\u02b7\u02b8\5r:\2\u02b8\u02ba\3"+
		"\2\2\2\u02b9\u029f\3\2\2\2\u02b9\u02a0\3\2\2\2\u02b9\u02a1\3\2\2\2\u02b9"+
		"\u02a2\3\2\2\2\u02b9\u02a3\3\2\2\2\u02b9\u02a4\3\2\2\2\u02b9\u02a5\3\2"+
		"\2\2\u02b9\u02a6\3\2\2\2\u02b9\u02a8\3\2\2\2\u02b9\u02aa\3\2\2\2\u02b9"+
		"\u02ad\3\2\2\2\u02b9\u02b0\3\2\2\2\u02b9\u02b3\3\2\2\2\u02b9\u02b6\3\2"+
		"\2\2\u02bas\3\2\2\2\u02bb\u02bc\t\b\2\2\u02bcu\3\2\2\2\u02bd\u02be\t\t"+
		"\2\2\u02bew\3\2\2\2\u02bf\u02c0\b=\1\2\u02c0\u02c1\7\u00b7\2\2\u02c1\u02c7"+
		"\7\u00b8\2\2\u02c2\u02c3\7\u00b7\2\2\u02c3\u02c4\5J&\2\u02c4\u02c5\7\u00b8"+
		"\2\2\u02c5\u02c7\3\2\2\2\u02c6\u02bf\3\2\2\2\u02c6\u02c2\3\2\2\2\u02c7"+
		"\u02d2\3\2\2\2\u02c8\u02c9\f\4\2\2\u02c9\u02ca\7\u00b7\2\2\u02ca\u02d1"+
		"\7\u00b8\2\2\u02cb\u02cc\f\3\2\2\u02cc\u02cd\7\u00b7\2\2\u02cd\u02ce\5"+
		"J&\2\u02ce\u02cf\7\u00b8\2\2\u02cf\u02d1\3\2\2\2\u02d0\u02c8\3\2\2\2\u02d0"+
		"\u02cb\3\2\2\2\u02d1\u02d4\3\2\2\2\u02d2\u02d0\3\2\2\2\u02d2\u02d3\3\2"+
		"\2\2\u02d3y\3\2\2\2\u02d4\u02d2\3\2\2\2\u02d5\u02da\5|?\2\u02d6\u02d7"+
		"\5|?\2\u02d7\u02d8\5x=\2\u02d8\u02da\3\2\2\2\u02d9\u02d5\3\2\2\2\u02d9"+
		"\u02d6\3\2\2\2\u02da{\3\2\2\2\u02db\u02df\5~@\2\u02dc\u02df\5\u0082B\2"+
		"\u02dd\u02df\7\u00c9\2\2\u02de\u02db\3\2\2\2\u02de\u02dc\3\2\2\2\u02de"+
		"\u02dd\3\2\2\2\u02df}\3\2\2\2\u02e0\u02e1\t\n\2\2\u02e1\177\3\2\2\2\u02e2"+
		"\u02e3\t\13\2\2\u02e3\u0081\3\2\2\2\u02e4\u02e6\7Z\2\2\u02e5\u02e7\7\u00c9"+
		"\2\2\u02e6\u02e5\3\2\2\2\u02e6\u02e7\3\2\2\2\u02e7\u02e8\3\2\2\2\u02e8"+
		"\u02e9\7\u00b4\2\2\u02e9\u02ea\5\u0084C\2\u02ea\u02eb\7\u00b5\2\2\u02eb"+
		"\u0083\3\2\2\2\u02ec\u02ed\bC\1\2\u02ed\u02ee\5\u0086D\2\u02ee\u02f3\3"+
		"\2\2\2\u02ef\u02f0\f\3\2\2\u02f0\u02f2\5\u0086D\2\u02f1\u02ef\3\2\2\2"+
		"\u02f2\u02f5\3\2\2\2\u02f3\u02f1\3\2\2\2\u02f3\u02f4\3\2\2\2\u02f4\u0085"+
		"\3\2\2\2\u02f5\u02f3\3\2\2\2\u02f6\u02f7\5d\63\2\u02f7\u02f8\5\u0088E"+
		"\2\u02f8\u02f9\7\u00b6\2\2\u02f9\u0087\3\2\2\2\u02fa\u02fb\bE\1\2\u02fb"+
		"\u02fc\5\u008aF\2\u02fc\u0302\3\2\2\2\u02fd\u02fe\f\3\2\2\u02fe\u02ff"+
		"\7\u00b9\2\2\u02ff\u0301\5\u008aF\2\u0300\u02fd\3\2\2\2\u0301\u0304\3"+
		"\2\2\2\u0302\u0300\3\2\2\2\u0302\u0303\3\2\2\2\u0303\u0089\3\2\2\2\u0304"+
		"\u0302\3\2\2\2\u0305\u0309\7\u00c9\2\2\u0306\u0307\7\u00c9\2\2\u0307\u0309"+
		"\5x=\2\u0308\u0305\3\2\2\2\u0308\u0306\3\2\2\2\u0309\u008b\3\2\2\2\u030a"+
		"\u0315\5D#\2\u030b\u030c\7\u00b4\2\2\u030c\u030d\5\u008eH\2\u030d\u030e"+
		"\7\u00b5\2\2\u030e\u0315\3\2\2\2\u030f\u0310\7\u00b4\2\2\u0310\u0311\5"+
		"\u008eH\2\u0311\u0312\7\u00b9\2\2\u0312\u0313\7\u00b5\2\2\u0313\u0315"+
		"\3\2\2\2\u0314\u030a\3\2\2\2\u0314\u030b\3\2\2\2\u0314\u030f\3\2\2\2\u0315"+
		"\u008d\3\2\2\2\u0316\u0317\bH\1\2\u0317\u0318\5\u008cG\2\u0318\u031e\3"+
		"\2\2\2\u0319\u031a\f\3\2\2\u031a\u031b\7\u00b9\2\2\u031b\u031d\5\u008c"+
		"G\2\u031c\u0319\3\2\2\2\u031d\u0320\3\2\2\2\u031e\u031c\3\2\2\2\u031e"+
		"\u031f\3\2\2\2\u031f\u008f\3\2\2\2\u0320\u031e\3\2\2\2\u0321\u0322\5L"+
		"\'\2\u0322\u0091\3\2\2\2\u0323\u0329\5\u00a0Q\2\u0324\u0329\5\u00a2R\2"+
		"\u0325\u0329\5\u00b6\\\2\u0326\u0329\5\u00ba^\2\u0327\u0329\5\u0096L\2"+
		"\u0328\u0323\3\2\2\2\u0328\u0324\3\2\2\2\u0328\u0325\3\2\2\2\u0328\u0326"+
		"\3\2\2\2\u0328\u0327\3\2\2\2\u0329\u0093\3\2\2\2\u032a\u032f\5\u00a4S"+
		"\2\u032b\u032f\5\u00b8]\2\u032c\u032f\5\u00bc_\2\u032d\u032f\5\u0096L"+
		"\2\u032e\u032a\3\2\2\2\u032e\u032b\3\2\2\2\u032e\u032c\3\2\2\2\u032e\u032d"+
		"\3\2\2\2\u032f\u0095\3\2\2\2\u0330\u0338\5\u0098M\2\u0331\u0338\5\u009c"+
		"O\2\u0332\u0338\5\u009eP\2\u0333\u0338\5\u0090I\2\u0334\u0338\5\u00a8"+
		"U\2\u0335\u0338\5\u00b4[\2\u0336\u0338\5\u00c0a\2\u0337\u0330\3\2\2\2"+
		"\u0337\u0331\3\2\2\2\u0337\u0332\3\2\2\2\u0337\u0333\3\2\2\2\u0337\u0334"+
		"\3\2\2\2\u0337\u0335\3\2\2\2\u0337\u0336\3\2\2\2\u0338\u0097\3\2\2\2\u0339"+
		"\u033b\7\u00b4\2\2\u033a\u033c\5\u009aN\2\u033b\u033a\3\2\2\2\u033b\u033c"+
		"\3\2\2\2\u033c\u033d\3\2\2\2\u033d\u033e\7\u00b5\2\2\u033e\u0099\3\2\2"+
		"\2\u033f\u0341\5\u0092J\2\u0340\u033f\3\2\2\2\u0341\u0342\3\2\2\2\u0342"+
		"\u0340\3\2\2\2\u0342\u0343\3\2\2\2\u0343\u009b\3\2\2\2\u0344\u0345\5H"+
		"%\2\u0345\u0346\7\u00b6\2\2\u0346\u009d\3\2\2\2\u0347\u0348\7\u00b6\2"+
		"\2\u0348\u009f\3\2\2\2\u0349\u034a\7[\2\2\u034a\u034b\7\u00b2\2\2\u034b"+
		"\u034c\5H%\2\u034c\u034d\7\u00b3\2\2\u034d\u034e\5\u0092J\2\u034e\u00a1"+
		"\3\2\2\2\u034f\u0350\7[\2\2\u0350\u0351\7\u00b2\2\2\u0351\u0352\5H%\2"+
		"\u0352\u0353\7\u00b3\2\2\u0353\u0354\5\u0094K\2\u0354\u0355\7\\\2\2\u0355"+
		"\u0356\5\u0092J\2\u0356\u00a3\3\2\2\2\u0357\u0358\7[\2\2\u0358\u0359\7"+
		"\u00b2\2\2\u0359\u035a\5H%\2\u035a\u035b\7\u00b3\2\2\u035b\u035c\5\u0094"+
		"K\2\u035c\u035d\7\\\2\2\u035d\u035e\5\u0094K\2\u035e\u00a5\3\2\2\2\u035f"+
		"\u0366\5H%\2\u0360\u0361\5d\63\2\u0361\u0362\7\u00c9\2\2\u0362\u0363\7"+
		"\u00c8\2\2\u0363\u0364\5\u008cG\2\u0364\u0366\3\2\2\2\u0365\u035f\3\2"+
		"\2\2\u0365\u0360\3\2\2\2\u0366\u00a7\3\2\2\2\u0367\u0368\7]\2\2\u0368"+
		"\u0369\7\u00b2\2\2\u0369\u036a\5H%\2\u036a\u036b\7\u00b3\2\2\u036b\u036c"+
		"\5\u00aaV\2\u036c\u00a9\3\2\2\2\u036d\u036e\7\u00b4\2\2\u036e\u0374\7"+
		"\u00b5\2\2\u036f\u0370\7\u00b4\2\2\u0370\u0371\5\u00b2Z\2\u0371\u0372"+
		"\7\u00b5\2\2\u0372\u0374\3\2\2\2\u0373\u036d\3\2\2\2\u0373\u036f\3\2\2"+
		"\2\u0374\u00ab\3\2\2\2\u0375\u0376\7^\2\2\u0376\u0377\5H%\2\u0377\u0378"+
		"\7\t\2\2\u0378\u037c\3\2\2\2\u0379\u037a\7_\2\2\u037a\u037c\7\t\2\2\u037b"+
		"\u0375\3\2\2\2\u037b\u0379\3\2\2\2\u037c\u00ad\3\2\2\2\u037d\u037e\bX"+
		"\1\2\u037e\u037f\5\u00acW\2\u037f\u0384\3\2\2\2\u0380\u0381\f\3\2\2\u0381"+
		"\u0383\5\u00acW\2\u0382\u0380\3\2\2\2\u0383\u0386\3\2\2\2\u0384\u0382"+
		"\3\2\2\2\u0384\u0385\3\2\2\2\u0385\u00af\3\2\2\2\u0386\u0384\3\2\2\2\u0387"+
		"\u0388\bY\1\2\u0388\u0389\5\u00aeX\2\u0389\u038a\5\u0092J\2\u038a\u038f"+
		"\3\2\2\2\u038b\u038c\f\3\2\2\u038c\u038e\5\u0092J\2\u038d\u038b\3\2\2"+
		"\2\u038e\u0391\3\2\2\2\u038f\u038d\3\2\2\2\u038f\u0390\3\2\2\2\u0390\u00b1"+
		"\3\2\2\2\u0391\u038f\3\2\2\2\u0392\u0393\bZ\1\2\u0393\u0394\5\u00b0Y\2"+
		"\u0394\u0399\3\2\2\2\u0395\u0396\f\3\2\2\u0396\u0398\5\u00b0Y\2\u0397"+
		"\u0395\3\2\2\2\u0398\u039b\3\2\2\2\u0399\u0397\3\2\2\2\u0399\u039a\3\2"+
		"\2\2\u039a\u00b3\3\2\2\2\u039b\u0399\3\2\2\2\u039c\u039d\7a\2\2\u039d"+
		"\u039e\5\u0092J\2\u039e\u039f\7`\2\2\u039f\u03a0\7\u00b2\2\2\u03a0\u03a1"+
		"\5H%\2\u03a1\u03a2\7\u00b3\2\2\u03a2\u03a3\7\u00b6\2\2\u03a3\u00b5\3\2"+
		"\2\2\u03a4\u03a5\7b\2\2\u03a5\u03a6\7\u00b2\2\2\u03a6\u03a8\5\u00be`\2"+
		"\u03a7\u03a9\5\u00a6T\2\u03a8\u03a7\3\2\2\2\u03a8\u03a9\3\2\2\2\u03a9"+
		"\u03aa\3\2\2\2\u03aa\u03ac\7\u00b6\2\2\u03ab\u03ad\5H%\2\u03ac\u03ab\3"+
		"\2\2\2\u03ac\u03ad\3\2\2\2\u03ad\u03ae\3\2\2\2\u03ae\u03af\7\u00b3\2\2"+
		"\u03af\u03b0\5\u0092J\2\u03b0\u00b7\3\2\2\2\u03b1\u03b2\7b\2\2\u03b2\u03b3"+
		"\7\u00b2\2\2\u03b3\u03b5\5\u00be`\2\u03b4\u03b6\5\u00a6T\2\u03b5\u03b4"+
		"\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6\u03b7\3\2\2\2\u03b7\u03b9\7\u00b6\2"+
		"\2\u03b8\u03ba\5H%\2\u03b9\u03b8\3\2\2\2\u03b9\u03ba\3\2\2\2\u03ba\u03bb"+
		"\3\2\2\2\u03bb\u03bc\7\u00b3\2\2\u03bc\u03bd\5\u0094K\2\u03bd\u00b9\3"+
		"\2\2\2\u03be\u03bf\7`\2\2\u03bf\u03c0\7\u00b2\2\2\u03c0\u03c1\5\u00a6"+
		"T\2\u03c1\u03c2\7\u00b3\2\2\u03c2\u03c3\5\u0092J\2\u03c3\u00bb\3\2\2\2"+
		"\u03c4\u03c5\7`\2\2\u03c5\u03c6\7\u00b2\2\2\u03c6\u03c7\5\u00a6T\2\u03c7"+
		"\u03c8\7\u00b3\2\2\u03c8\u03c9\5\u0094K\2\u03c9\u00bd\3\2\2\2\u03ca\u03ce"+
		"\5\u009eP\2\u03cb\u03ce\5\u009cO\2\u03cc\u03ce\5\u0090I\2\u03cd\u03ca"+
		"\3\2\2\2\u03cd\u03cb\3\2\2\2\u03cd\u03cc\3\2\2\2\u03ce\u00bf\3\2\2\2\u03cf"+
		"\u03d0\7c\2\2\u03d0\u03dc\7\u00b6\2\2\u03d1\u03d2\7d\2\2\u03d2\u03dc\7"+
		"\u00b6\2\2\u03d3\u03d4\7e\2\2\u03d4\u03dc\7\u00b6\2\2\u03d5\u03d6\7e\2"+
		"\2\u03d6\u03d7\5H%\2\u03d7\u03d8\7\u00b6\2\2\u03d8\u03dc\3\2\2\2\u03d9"+
		"\u03da\7f\2\2\u03da\u03dc\7\u00b6\2\2\u03db\u03cf\3\2\2\2\u03db\u03d1"+
		"\3\2\2\2\u03db\u03d3\3\2\2\2\u03db\u03d5\3\2\2\2\u03db\u03d9\3\2\2\2\u03dc"+
		"\u00c1\3\2\2\2\u03dd\u03e2\5\u00c4c\2\u03de\u03e2\5L\'\2\u03df\u03e2\5"+
		"\6\4\2\u03e0\u03e2\5\u00ceh\2\u03e1\u03dd\3\2\2\2\u03e1\u03de\3\2\2\2"+
		"\u03e1\u03df\3\2\2\2\u03e1\u03e0\3\2\2\2\u03e2\u00c3\3\2\2\2\u03e3\u03e4"+
		"\5N(\2\u03e4\u03e5\5\u0098M\2\u03e5\u00c5\3\2\2\2\u03e6\u03eb\5\u00c8"+
		"e\2\u03e7\u03e8\5f\64\2\u03e8\u03e9\5\u00c8e\2\u03e9\u03eb\3\2\2\2\u03ea"+
		"\u03e6\3\2\2\2\u03ea\u03e7\3\2\2\2\u03eb\u00c7\3\2\2\2\u03ec\u03ee\5\u00ca"+
		"f\2\u03ed\u03ec\3\2\2\2\u03ee\u03ef\3\2\2\2\u03ef\u03ed\3\2\2\2\u03ef"+
		"\u03f0\3\2\2\2\u03f0\u03f1\3\2\2\2\u03f1\u03f2\7\u00c9\2\2\u03f2\u03f3"+
		"\7\u00b4\2\2\u03f3\u03f4\5\u0084C\2\u03f4\u03f6\7\u00b5\2\2\u03f5\u03f7"+
		"\5\u00ccg\2\u03f6\u03f5\3\2\2\2\u03f6\u03f7\3\2\2\2\u03f7\u03f8\3\2\2"+
		"\2\u03f8\u03f9\7\u00b6\2\2\u03f9\u00c9\3\2\2\2\u03fa\u03fb\t\f\2\2\u03fb"+
		"\u00cb\3\2\2\2\u03fc\u03fe\7\u00c9\2\2\u03fd\u03ff\5x=\2\u03fe\u03fd\3"+
		"\2\2\2\u03fe\u03ff\3\2\2\2\u03ff\u00cd\3\2\2\2\u0400\u0401\5f\64\2\u0401"+
		"\u0402\7\n\2\2\u0402\u0403\7\u00b6\2\2\u0403\u0411\3\2\2\2\u0404\u0405"+
		"\5f\64\2\u0405\u0406\7\f\2\2\u0406\u0407\7\u00b6\2\2\u0407\u0411\3\2\2"+
		"\2\u0408\u0409\5f\64\2\u0409\u040a\7\r\2\2\u040a\u040b\7\u00b6\2\2\u040b"+
		"\u0411\3\2\2\2\u040c\u040d\5f\64\2\u040d\u040e\7\13\2\2\u040e\u040f\7"+
		"\u00b6\2\2\u040f\u0411\3\2\2\2\u0410\u0400\3\2\2\2\u0410\u0404\3\2\2\2"+
		"\u0410\u0408\3\2\2\2\u0410\u040c\3\2\2\2\u0411\u00cf\3\2\2\2V\u00dc\u00e8"+
		"\u00ef\u00ff\u0101\u010f\u0114\u0125\u0127\u0132\u0138\u0143\u0148\u014e"+
		"\u0156\u015c\u0167\u0175\u017d\u0182\u0188\u018d\u0193\u0198\u01a0\u01a5"+
		"\u01ab\u01b0\u01b8\u01c0\u01c8\u01d0\u01d8\u01e0\u01eb\u01f3\u01fc\u020d"+
		"\u0214\u021f\u022d\u0235\u0242\u025e\u0260\u027a\u0280\u028f\u0299\u02b9"+
		"\u02c6\u02d0\u02d2\u02d9\u02de\u02e6\u02f3\u0302\u0308\u0314\u031e\u0328"+
		"\u032e\u0337\u033b\u0342\u0365\u0373\u037b\u0384\u038f\u0399\u03a8\u03ac"+
		"\u03b5\u03b9\u03cd\u03db\u03e1\u03ea\u03ef\u03f6\u03fe\u0410";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}