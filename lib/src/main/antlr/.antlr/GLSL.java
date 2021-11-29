// Generated from /Users/christopher/Documents/dev/antlr-experiments/src/main/antlr/GLSL.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GLSL extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRAGMA_DEBUG_ON=1, PRAGMA_DEBUG_OFF=2, PRAGMA_OPTIMIZE_ON=3, PRAGMA_OPTIMIZE_OFF=4, 
		PRAGMA_INVARIANT_ALL=5, EXTENSION=6, VERSION=7, EOL=8, PP_DEFINE=9, PP_UNDEF=10, 
		PP_IF=11, PP_IFDEF=12, PP_IFNDEF=13, PP_ELSE=14, PP_ELIF=15, PP_ENDIF=16, 
		PP_ERROR=17, PP_LINE=18, REQUIRE=19, ENABLE=20, WARN=21, DISABLE=22, COLON=23, 
		UNIFORM=24, BUFFER=25, IN=26, OUT=27, INOUT=28, HIGHP=29, MEDIUMP=30, 
		LOWP=31, PRECISION=32, INTCONSTANT=33, CONST=34, PRECISE=35, INVARIANT=36, 
		SMOOTH=37, FLAT=38, NOPERSPECTIVE=39, CENTROID=40, SAMPLE=41, PATCH=42, 
		ATTRIBUTE=43, COHERENT=44, VOLATILE=45, RESTRICT=46, VARYING=47, READONLY=48, 
		WRITEONLY=49, SHARED=50, SUBROUTINE=51, LAYOUT=52, UINTCONSTANT=53, FLOATCONSTANT=54, 
		BOOLCONSTANT=55, DOUBLECONSTANT=56, INC_OP=57, DEC_OP=58, VOID=59, LEFT_OP=60, 
		RIGHT_OP=61, LE_OP=62, GE_OP=63, EQ_OP=64, NE_OP=65, AND_OP=66, XOR_OP=67, 
		OR_OP=68, MUL_ASSIGN=69, DIV_ASSIGN=70, MOD_ASSIGN=71, ADD_ASSIGN=72, 
		SUB_ASSIGN=73, LEFT_ASSIGN=74, RIGHT_ASSIGN=75, AND_ASSIGN=76, XOR_ASSIGN=77, 
		OR_ASSIGN=78, FLOAT=79, DOUBLE=80, INT=81, UINT=82, BOOL=83, SAMPLERCUBE=84, 
		SAMPLERCUBESHADOW=85, SAMPLERBUFFER=86, SAMPLERCUBEARRAY=87, SAMPLERCUBEARRAYSHADOW=88, 
		ISAMPLERCUBE=89, ISAMPLERBUFFER=90, ISAMPLERCUBEARRAY=91, USAMPLERCUBE=92, 
		USAMPLERBUFFER=93, USAMPLERCUBEARRAY=94, IMAGECUBE=95, IMAGEBUFFER=96, 
		IMAGECUBEARRAY=97, IIMAGECUBE=98, IIMAGEBUFFER=99, IIMAGECUBEARRAY=100, 
		UIMAGECUBE=101, UIMAGEBUFFER=102, UIMAGECUBEARRAY=103, ATOMIC_UINT=104, 
		STRUCT=105, IF=106, ELSE=107, SWITCH=108, CASE=109, DEFAULT=110, WHILE=111, 
		DO=112, FOR=113, CONTINUE=114, BREAK=115, RETURN=116, DISCARD=117, VEC2=118, 
		VEC3=119, VEC4=120, DVEC2=121, DVEC3=122, DVEC4=123, BVEC2=124, BVEC3=125, 
		BVEC4=126, IVEC2=127, IVEC3=128, IVEC4=129, UVEC2=130, UVEC3=131, UVEC4=132, 
		MAT2X2=133, MAT2X3=134, MAT2X4=135, MAT3X2=136, MAT3X3=137, MAT3X4=138, 
		MAT4X2=139, MAT4X3=140, MAT4X4=141, DMAT2X2=142, DMAT2X3=143, DMAT2X4=144, 
		DMAT3X2=145, DMAT3X3=146, DMAT3X4=147, DMAT4X2=148, DMAT4X3=149, DMAT4X4=150, 
		IMAGE1D=151, IMAGE2D=152, IMAGE3D=153, UIMAGE1D=154, UIMAGE2D=155, UIMAGE3D=156, 
		IIMAGE1D=157, IIMAGE2D=158, IIMAGE3D=159, SAMPLER1D=160, SAMPLER2D=161, 
		SAMPLER3D=162, SAMPLER2DRECT=163, SAMPLEREXTERNALOES=164, SAMPLER1DSHADOW=165, 
		SAMPLER2DSHADOW=166, SAMPLER2DRECTSHADOW=167, SAMPLER1DARRAY=168, SAMPLER2DARRAY=169, 
		SAMPLER1DARRAYSHADOW=170, SAMPLER2DARRAYSHADOW=171, ISAMPLER1D=172, ISAMPLER2D=173, 
		ISAMPLER2DRECT=174, ISAMPLER3D=175, ISAMPLER1DARRAY=176, ISAMPLER2DARRAY=177, 
		USAMPLER1D=178, USAMPLER2D=179, USAMPLER2DRECT=180, USAMPLER3D=181, USAMPLER1DARRAY=182, 
		USAMPLER2DARRAY=183, SAMPLER2DMS=184, ISAMPLER2DMS=185, USAMPLER2DMS=186, 
		SAMPLER2DMSARRAY=187, ISAMPLER2DMSARRAY=188, USAMPLER2DMSARRAY=189, IMAGE2DRECT=190, 
		IMAGE1DARRAY=191, IMAGE2DARRAY=192, IMAGE2DMS=193, IMAGE2DMSARRAY=194, 
		IIMAGE2DRECT=195, IIMAGE1DARRAY=196, IIMAGE2DARRAY=197, IIMAGE2DMS=198, 
		IIMAGE2DMSARRAY=199, UIMAGE2DRECT=200, UIMAGE1DARRAY=201, UIMAGE2DARRAY=202, 
		UIMAGE2DMS=203, UIMAGE2DMSARRAY=204, LPAREN=205, RPAREN=206, LBRACE=207, 
		RBRACE=208, SEMICOLON=209, LBRACKET=210, RBRACKET=211, COMMA=212, DOT=213, 
		PLUS_OP=214, MINUS_OP=215, NOT_OP=216, BNEG_OP=217, TIMES_OP=218, DIV_OP=219, 
		MOD_OP=220, LT_OP=221, GT_OP=222, BAND_OP=223, BOR_OP=224, BXOR_OP=225, 
		QUERY_OP=226, ASSIGN_OP=227, IDENTIFIER=228, COMMENT=229, WS=230;
	public static final int
		RULE_translation_unit = 0, RULE_version_statement = 1, RULE_external_declaration_list = 2, 
		RULE_external_declaration = 3, RULE_pragma_statement = 4, RULE_extension_statement = 5, 
		RULE_extension_state = 6, RULE_layout_defaults = 7, RULE_layout_modes = 8, 
		RULE_function_definition = 9, RULE_variable_identifier = 10, RULE_primary_expression = 11, 
		RULE_postfix_expression = 12, RULE_function_call = 13, RULE_function_call_parameter_list = 14, 
		RULE_function_identifier = 15, RULE_unary_expression = 16, RULE_unary_operator = 17, 
		RULE_multiplicative_expression = 18, RULE_additive_expression = 19, RULE_shift_expression = 20, 
		RULE_relational_expression = 21, RULE_equality_expression = 22, RULE_and_expression = 23, 
		RULE_exclusive_or_expression = 24, RULE_inclusive_or_expression = 25, 
		RULE_logical_and_expression = 26, RULE_logical_xor_expression = 27, RULE_logical_or_expression = 28, 
		RULE_conditional_expression = 29, RULE_assignment_expression = 30, RULE_assignment_operator = 31, 
		RULE_expression = 32, RULE_constant_expression = 33, RULE_declaration = 34, 
		RULE_function_prototype = 35, RULE_function_parameter_list = 36, RULE_function_header = 37, 
		RULE_parameter_declarator = 38, RULE_parameter_declaration = 39, RULE_init_declarator_list = 40, 
		RULE_declaration_member = 41, RULE_fully_specified_type = 42, RULE_storage_qualifier = 43, 
		RULE_layout_qualifier = 44, RULE_layout_qualifier_id = 45, RULE_precision_qualifier = 46, 
		RULE_interpolation_qualifier = 47, RULE_invariant_qualifier = 48, RULE_precise_qualifier = 49, 
		RULE_type_qualifier = 50, RULE_type_name_list = 51, RULE_type_specifier = 52, 
		RULE_array_specifier = 53, RULE_type_specifier_nonarray = 54, RULE_builtin_type_specifier_nonarray = 55, 
		RULE_struct_specifier = 56, RULE_struct_declaration_list = 57, RULE_struct_declaration = 58, 
		RULE_struct_declarator_list = 59, RULE_struct_declarator = 60, RULE_initializer = 61, 
		RULE_statement = 62, RULE_simple_statement = 63, RULE_compound_statement = 64, 
		RULE_statement_list = 65, RULE_declaration_statement = 66, RULE_expression_statement = 67, 
		RULE_empty_statement = 68, RULE_selection_statement = 69, RULE_condition = 70, 
		RULE_switch_statement = 71, RULE_case_label = 72, RULE_while_statement = 73, 
		RULE_do_while_statement = 74, RULE_for_statement = 75, RULE_jump_statement = 76;
	private static String[] makeRuleNames() {
		return new String[] {
			"translation_unit", "version_statement", "external_declaration_list", 
			"external_declaration", "pragma_statement", "extension_statement", "extension_state", 
			"layout_defaults", "layout_modes", "function_definition", "variable_identifier", 
			"primary_expression", "postfix_expression", "function_call", "function_call_parameter_list", 
			"function_identifier", "unary_expression", "unary_operator", "multiplicative_expression", 
			"additive_expression", "shift_expression", "relational_expression", "equality_expression", 
			"and_expression", "exclusive_or_expression", "inclusive_or_expression", 
			"logical_and_expression", "logical_xor_expression", "logical_or_expression", 
			"conditional_expression", "assignment_expression", "assignment_operator", 
			"expression", "constant_expression", "declaration", "function_prototype", 
			"function_parameter_list", "function_header", "parameter_declarator", 
			"parameter_declaration", "init_declarator_list", "declaration_member", 
			"fully_specified_type", "storage_qualifier", "layout_qualifier", "layout_qualifier_id", 
			"precision_qualifier", "interpolation_qualifier", "invariant_qualifier", 
			"precise_qualifier", "type_qualifier", "type_name_list", "type_specifier", 
			"array_specifier", "type_specifier_nonarray", "builtin_type_specifier_nonarray", 
			"struct_specifier", "struct_declaration_list", "struct_declaration", 
			"struct_declarator_list", "struct_declarator", "initializer", "statement", 
			"simple_statement", "compound_statement", "statement_list", "declaration_statement", 
			"expression_statement", "empty_statement", "selection_statement", "condition", 
			"switch_statement", "case_label", "while_statement", "do_while_statement", 
			"for_statement", "jump_statement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "'\n'", null, null, null, 
			null, null, null, null, null, null, null, "'require'", "'enable'", "'warn'", 
			"'disable'", "':'", "'uniform'", "'buffer'", "'in'", "'out'", "'inout'", 
			"'highp'", "'mediump'", "'lowp'", "'precision'", null, "'const'", "'precise'", 
			"'invariant'", "'smooth'", "'flat'", "'noperspective'", "'centroid'", 
			"'sample'", "'patch'", "'attribute'", "'coherent'", "'volatile'", "'restrict'", 
			"'varying'", "'readonly'", "'writeonly'", "'shared'", "'subroutine'", 
			"'layout'", null, null, null, null, "'++'", "'--'", "'void'", "'<<'", 
			"'>>'", "'<='", "'>='", "'=='", "'!='", "'&&'", "'^^'", "'||'", "'*='", 
			"'/='", "'%='", "'+='", "'-='", "'<<='", "'>>='", "'&='", "'^='", "'|='", 
			"'float'", "'double'", "'int'", "'uint'", "'bool'", "'samplerCube'", 
			"'samplerCubeShadow'", "'samplerBuffer'", "'samplerCubeArray'", "'samplerCubeArrayShadow'", 
			"'isamplerCube'", "'isamplerBuffer'", "'isamplerCubeArray'", "'usamplerCube'", 
			"'usamplerBuffer'", "'usamplerCubeArray'", "'imageCube'", "'imageBuffer'", 
			"'imageCubeArray'", "'iimageCube'", "'iimageBuffer'", "'iimageCubeArray'", 
			"'uimageCube'", "'uimageBuffer'", "'uimageCubeArray'", "'atomic_uint'", 
			"'struct'", "'if'", "'else'", "'switch'", "'case'", "'default'", "'while'", 
			"'do'", "'for'", "'continue'", "'break'", "'return'", "'discard'", "'vec2'", 
			"'vec3'", "'vec4'", "'dvec2'", "'dvec3'", "'dvec4'", "'bvec2'", "'bvec3'", 
			"'bvec4'", "'ivec2'", "'ivec3'", "'ivec4'", "'uvec2'", "'uvec3'", "'uvec4'", 
			null, "'mat2x3'", "'mat2x4'", "'mat3x2'", null, "'mat3x4'", "'mat4x2'", 
			"'mat4x3'", null, null, "'dmat2x3'", "'dmat2x4'", "'dmat3x2'", null, 
			"'dmat3x4'", "'dmat4x2'", "'dmat4x3'", null, "'image1D'", "'image2D'", 
			"'image3D'", "'uimage1D'", "'uimage2D'", "'uimage3D'", "'iimage1D'", 
			"'iimage2D'", "'iimage3D'", "'sampler1D'", "'sampler2D'", "'sampler3D'", 
			"'sampler2DRect'", "'samplerExternalOES'", "'sampler1DShadow'", "'sampler2DShadow'", 
			"'sampler2DRectShadow'", "'sampler1DArray'", "'sampler2DArray'", "'sampler1DArrayShadow'", 
			"'sampler2DArrayShadow'", "'isampler1D'", "'isampler2D'", "'isampler2DRect'", 
			"'isampler3D'", "'isampler1DArray'", "'isampler2DArray'", "'usampler1D'", 
			"'usampler2D'", "'usampler2DRect'", "'usampler3D'", "'usampler1DArray'", 
			"'usampler2DArray'", "'sampler2DMS'", "'isampler2DMS'", "'usampler2DMS'", 
			"'sampler2DMSArray'", "'isampler2DMSArray'", "'usampler2DMSArray'", "'image2DRect'", 
			"'image1DArray'", "'image2DArray'", "'image2DMS'", "'image2DMSArray'", 
			"'iimage2DRect'", "'iimage1DArray'", "'iimage2DArray'", "'iimage2DMS'", 
			"'iimage2DMSArray'", "'uimage2DRect'", "'uimage1DArray'", "'uimage2DArray'", 
			"'uimage2DMS'", "'uimage2DMSArray'", "'('", "')'", "'{'", "'}'", "';'", 
			"'['", "']'", "','", "'.'", "'+'", "'-'", "'!'", "'~'", "'*'", "'/'", 
			"'%'", "'<'", "'>'", "'&'", "'|'", "'^'", "'?'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PRAGMA_DEBUG_ON", "PRAGMA_DEBUG_OFF", "PRAGMA_OPTIMIZE_ON", "PRAGMA_OPTIMIZE_OFF", 
			"PRAGMA_INVARIANT_ALL", "EXTENSION", "VERSION", "EOL", "PP_DEFINE", "PP_UNDEF", 
			"PP_IF", "PP_IFDEF", "PP_IFNDEF", "PP_ELSE", "PP_ELIF", "PP_ENDIF", "PP_ERROR", 
			"PP_LINE", "REQUIRE", "ENABLE", "WARN", "DISABLE", "COLON", "UNIFORM", 
			"BUFFER", "IN", "OUT", "INOUT", "HIGHP", "MEDIUMP", "LOWP", "PRECISION", 
			"INTCONSTANT", "CONST", "PRECISE", "INVARIANT", "SMOOTH", "FLAT", "NOPERSPECTIVE", 
			"CENTROID", "SAMPLE", "PATCH", "ATTRIBUTE", "COHERENT", "VOLATILE", "RESTRICT", 
			"VARYING", "READONLY", "WRITEONLY", "SHARED", "SUBROUTINE", "LAYOUT", 
			"UINTCONSTANT", "FLOATCONSTANT", "BOOLCONSTANT", "DOUBLECONSTANT", "INC_OP", 
			"DEC_OP", "VOID", "LEFT_OP", "RIGHT_OP", "LE_OP", "GE_OP", "EQ_OP", "NE_OP", 
			"AND_OP", "XOR_OP", "OR_OP", "MUL_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", 
			"ADD_ASSIGN", "SUB_ASSIGN", "LEFT_ASSIGN", "RIGHT_ASSIGN", "AND_ASSIGN", 
			"XOR_ASSIGN", "OR_ASSIGN", "FLOAT", "DOUBLE", "INT", "UINT", "BOOL", 
			"SAMPLERCUBE", "SAMPLERCUBESHADOW", "SAMPLERBUFFER", "SAMPLERCUBEARRAY", 
			"SAMPLERCUBEARRAYSHADOW", "ISAMPLERCUBE", "ISAMPLERBUFFER", "ISAMPLERCUBEARRAY", 
			"USAMPLERCUBE", "USAMPLERBUFFER", "USAMPLERCUBEARRAY", "IMAGECUBE", "IMAGEBUFFER", 
			"IMAGECUBEARRAY", "IIMAGECUBE", "IIMAGEBUFFER", "IIMAGECUBEARRAY", "UIMAGECUBE", 
			"UIMAGEBUFFER", "UIMAGECUBEARRAY", "ATOMIC_UINT", "STRUCT", "IF", "ELSE", 
			"SWITCH", "CASE", "DEFAULT", "WHILE", "DO", "FOR", "CONTINUE", "BREAK", 
			"RETURN", "DISCARD", "VEC2", "VEC3", "VEC4", "DVEC2", "DVEC3", "DVEC4", 
			"BVEC2", "BVEC3", "BVEC4", "IVEC2", "IVEC3", "IVEC4", "UVEC2", "UVEC3", 
			"UVEC4", "MAT2X2", "MAT2X3", "MAT2X4", "MAT3X2", "MAT3X3", "MAT3X4", 
			"MAT4X2", "MAT4X3", "MAT4X4", "DMAT2X2", "DMAT2X3", "DMAT2X4", "DMAT3X2", 
			"DMAT3X3", "DMAT3X4", "DMAT4X2", "DMAT4X3", "DMAT4X4", "IMAGE1D", "IMAGE2D", 
			"IMAGE3D", "UIMAGE1D", "UIMAGE2D", "UIMAGE3D", "IIMAGE1D", "IIMAGE2D", 
			"IIMAGE3D", "SAMPLER1D", "SAMPLER2D", "SAMPLER3D", "SAMPLER2DRECT", "SAMPLEREXTERNALOES", 
			"SAMPLER1DSHADOW", "SAMPLER2DSHADOW", "SAMPLER2DRECTSHADOW", "SAMPLER1DARRAY", 
			"SAMPLER2DARRAY", "SAMPLER1DARRAYSHADOW", "SAMPLER2DARRAYSHADOW", "ISAMPLER1D", 
			"ISAMPLER2D", "ISAMPLER2DRECT", "ISAMPLER3D", "ISAMPLER1DARRAY", "ISAMPLER2DARRAY", 
			"USAMPLER1D", "USAMPLER2D", "USAMPLER2DRECT", "USAMPLER3D", "USAMPLER1DARRAY", 
			"USAMPLER2DARRAY", "SAMPLER2DMS", "ISAMPLER2DMS", "USAMPLER2DMS", "SAMPLER2DMSARRAY", 
			"ISAMPLER2DMSARRAY", "USAMPLER2DMSARRAY", "IMAGE2DRECT", "IMAGE1DARRAY", 
			"IMAGE2DARRAY", "IMAGE2DMS", "IMAGE2DMSARRAY", "IIMAGE2DRECT", "IIMAGE1DARRAY", 
			"IIMAGE2DARRAY", "IIMAGE2DMS", "IIMAGE2DMSARRAY", "UIMAGE2DRECT", "UIMAGE1DARRAY", 
			"UIMAGE2DARRAY", "UIMAGE2DMS", "UIMAGE2DMSARRAY", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "SEMICOLON", "LBRACKET", "RBRACKET", "COMMA", "DOT", 
			"PLUS_OP", "MINUS_OP", "NOT_OP", "BNEG_OP", "TIMES_OP", "DIV_OP", "MOD_OP", 
			"LT_OP", "GT_OP", "BAND_OP", "BOR_OP", "BXOR_OP", "QUERY_OP", "ASSIGN_OP", 
			"IDENTIFIER", "COMMENT", "WS"
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
	public String getGrammarFileName() { return "GLSL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GLSL(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Translation_unitContext extends ParserRuleContext {
		public Version_statementContext version_statement() {
			return getRuleContext(Version_statementContext.class,0);
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
			setState(154);
			version_statement();
			setState(155);
			external_declaration_list();
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
		public TerminalNode VERSION() { return getToken(GLSL.VERSION, 0); }
		public TerminalNode INTCONSTANT() { return getToken(GLSL.INTCONSTANT, 0); }
		public TerminalNode EOL() { return getToken(GLSL.EOL, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public Version_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_version_statement; }
	}

	public final Version_statementContext version_statement() throws RecognitionException {
		Version_statementContext _localctx = new Version_statementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_version_statement);
		try {
			setState(165);
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
				setState(158);
				match(VERSION);
				setState(159);
				match(INTCONSTANT);
				setState(160);
				match(EOL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(161);
				match(VERSION);
				setState(162);
				match(INTCONSTANT);
				setState(163);
				match(IDENTIFIER);
				setState(164);
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

	public static class External_declaration_listContext extends ParserRuleContext {
		public List<External_declarationContext> external_declaration() {
			return getRuleContexts(External_declarationContext.class);
		}
		public External_declarationContext external_declaration(int i) {
			return getRuleContext(External_declarationContext.class,i);
		}
		public External_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_external_declaration_list; }
	}

	public final External_declaration_listContext external_declaration_list() throws RecognitionException {
		External_declaration_listContext _localctx = new External_declaration_listContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_external_declaration_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(167);
				external_declaration();
				}
				}
				setState(170); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRAGMA_DEBUG_ON) | (1L << PRAGMA_DEBUG_OFF) | (1L << PRAGMA_OPTIMIZE_ON) | (1L << PRAGMA_OPTIMIZE_OFF) | (1L << PRAGMA_INVARIANT_ALL) | (1L << EXTENSION) | (1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << PRECISION) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (STRUCT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)))) != 0) || _la==SEMICOLON || _la==IDENTIFIER );
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
		public Extension_statementContext extension_statement() {
			return getRuleContext(Extension_statementContext.class,0);
		}
		public Layout_defaultsContext layout_defaults() {
			return getRuleContext(Layout_defaultsContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public External_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_external_declaration; }
	}

	public final External_declarationContext external_declaration() throws RecognitionException {
		External_declarationContext _localctx = new External_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_external_declaration);
		try {
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				function_definition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(173);
				declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(174);
				pragma_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
				extension_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				layout_defaults();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(177);
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

	public static class Pragma_statementContext extends ParserRuleContext {
		public TerminalNode PRAGMA_DEBUG_ON() { return getToken(GLSL.PRAGMA_DEBUG_ON, 0); }
		public TerminalNode EOL() { return getToken(GLSL.EOL, 0); }
		public TerminalNode PRAGMA_DEBUG_OFF() { return getToken(GLSL.PRAGMA_DEBUG_OFF, 0); }
		public TerminalNode PRAGMA_OPTIMIZE_ON() { return getToken(GLSL.PRAGMA_OPTIMIZE_ON, 0); }
		public TerminalNode PRAGMA_OPTIMIZE_OFF() { return getToken(GLSL.PRAGMA_OPTIMIZE_OFF, 0); }
		public TerminalNode PRAGMA_INVARIANT_ALL() { return getToken(GLSL.PRAGMA_INVARIANT_ALL, 0); }
		public Pragma_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma_statement; }
	}

	public final Pragma_statementContext pragma_statement() throws RecognitionException {
		Pragma_statementContext _localctx = new Pragma_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_pragma_statement);
		try {
			setState(190);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRAGMA_DEBUG_ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				match(PRAGMA_DEBUG_ON);
				setState(181);
				match(EOL);
				}
				break;
			case PRAGMA_DEBUG_OFF:
				enterOuterAlt(_localctx, 2);
				{
				setState(182);
				match(PRAGMA_DEBUG_OFF);
				setState(183);
				match(EOL);
				}
				break;
			case PRAGMA_OPTIMIZE_ON:
				enterOuterAlt(_localctx, 3);
				{
				setState(184);
				match(PRAGMA_OPTIMIZE_ON);
				setState(185);
				match(EOL);
				}
				break;
			case PRAGMA_OPTIMIZE_OFF:
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				match(PRAGMA_OPTIMIZE_OFF);
				setState(187);
				match(EOL);
				}
				break;
			case PRAGMA_INVARIANT_ALL:
				enterOuterAlt(_localctx, 5);
				{
				setState(188);
				match(PRAGMA_INVARIANT_ALL);
				setState(189);
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

	public static class Extension_statementContext extends ParserRuleContext {
		public TerminalNode EXTENSION() { return getToken(GLSL.EXTENSION, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public TerminalNode COLON() { return getToken(GLSL.COLON, 0); }
		public Extension_stateContext extension_state() {
			return getRuleContext(Extension_stateContext.class,0);
		}
		public TerminalNode EOL() { return getToken(GLSL.EOL, 0); }
		public Extension_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_statement; }
	}

	public final Extension_statementContext extension_statement() throws RecognitionException {
		Extension_statementContext _localctx = new Extension_statementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_extension_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(EXTENSION);
			setState(193);
			match(IDENTIFIER);
			setState(194);
			match(COLON);
			setState(195);
			extension_state();
			setState(196);
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

	public static class Extension_stateContext extends ParserRuleContext {
		public TerminalNode REQUIRE() { return getToken(GLSL.REQUIRE, 0); }
		public TerminalNode ENABLE() { return getToken(GLSL.ENABLE, 0); }
		public TerminalNode WARN() { return getToken(GLSL.WARN, 0); }
		public TerminalNode DISABLE() { return getToken(GLSL.DISABLE, 0); }
		public Extension_stateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_state; }
	}

	public final Extension_stateContext extension_state() throws RecognitionException {
		Extension_stateContext _localctx = new Extension_stateContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_extension_state);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << REQUIRE) | (1L << ENABLE) | (1L << WARN) | (1L << DISABLE))) != 0)) ) {
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

	public static class Layout_defaultsContext extends ParserRuleContext {
		public Layout_qualifierContext layout_qualifier() {
			return getRuleContext(Layout_qualifierContext.class,0);
		}
		public Layout_modesContext layout_modes() {
			return getRuleContext(Layout_modesContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Layout_defaultsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_defaults; }
	}

	public final Layout_defaultsContext layout_defaults() throws RecognitionException {
		Layout_defaultsContext _localctx = new Layout_defaultsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_layout_defaults);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			layout_qualifier();
			setState(201);
			layout_modes();
			setState(202);
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

	public static class Layout_modesContext extends ParserRuleContext {
		public TerminalNode UNIFORM() { return getToken(GLSL.UNIFORM, 0); }
		public TerminalNode IN() { return getToken(GLSL.IN, 0); }
		public TerminalNode OUT() { return getToken(GLSL.OUT, 0); }
		public TerminalNode BUFFER() { return getToken(GLSL.BUFFER, 0); }
		public Layout_modesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_modes; }
	}

	public final Layout_modesContext layout_modes() throws RecognitionException {
		Layout_modesContext _localctx = new Layout_modesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_layout_modes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
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

	public static class Function_definitionContext extends ParserRuleContext {
		public Function_prototypeContext function_prototype() {
			return getRuleContext(Function_prototypeContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_function_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			function_prototype();
			setState(207);
			compound_statement();
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

	public static class Variable_identifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public Variable_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_identifier; }
	}

	public final Variable_identifierContext variable_identifier() throws RecognitionException {
		Variable_identifierContext _localctx = new Variable_identifierContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_variable_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
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
		public TerminalNode INTCONSTANT() { return getToken(GLSL.INTCONSTANT, 0); }
		public TerminalNode UINTCONSTANT() { return getToken(GLSL.UINTCONSTANT, 0); }
		public TerminalNode FLOATCONSTANT() { return getToken(GLSL.FLOATCONSTANT, 0); }
		public TerminalNode BOOLCONSTANT() { return getToken(GLSL.BOOLCONSTANT, 0); }
		public TerminalNode DOUBLECONSTANT() { return getToken(GLSL.DOUBLECONSTANT, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public Primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary_expression; }
	}

	public final Primary_expressionContext primary_expression() throws RecognitionException {
		Primary_expressionContext _localctx = new Primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_primary_expression);
		try {
			setState(221);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				variable_identifier();
				}
				break;
			case INTCONSTANT:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				match(INTCONSTANT);
				}
				break;
			case UINTCONSTANT:
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				match(UINTCONSTANT);
				}
				break;
			case FLOATCONSTANT:
				enterOuterAlt(_localctx, 4);
				{
				setState(214);
				match(FLOATCONSTANT);
				}
				break;
			case BOOLCONSTANT:
				enterOuterAlt(_localctx, 5);
				{
				setState(215);
				match(BOOLCONSTANT);
				}
				break;
			case DOUBLECONSTANT:
				enterOuterAlt(_localctx, 6);
				{
				setState(216);
				match(DOUBLECONSTANT);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 7);
				{
				setState(217);
				match(LPAREN);
				setState(218);
				expression();
				setState(219);
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
		public Function_callContext function_call() {
			return getRuleContext(Function_callContext.class,0);
		}
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(GLSL.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(GLSL.RBRACKET, 0); }
		public TerminalNode DOT() { return getToken(GLSL.DOT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public TerminalNode INC_OP() { return getToken(GLSL.INC_OP, 0); }
		public TerminalNode DEC_OP() { return getToken(GLSL.DEC_OP, 0); }
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
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_postfix_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(224);
				primary_expression();
				}
				break;
			case 2:
				{
				setState(225);
				function_call();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(241);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Postfix_expressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_postfix_expression);
					setState(228);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(237);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LBRACKET:
						{
						setState(229);
						match(LBRACKET);
						setState(230);
						expression();
						setState(231);
						match(RBRACKET);
						}
						break;
					case DOT:
						{
						setState(233);
						match(DOT);
						setState(234);
						match(IDENTIFIER);
						}
						break;
					case INC_OP:
						{
						setState(235);
						match(INC_OP);
						}
						break;
					case DEC_OP:
						{
						setState(236);
						match(DEC_OP);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					} 
				}
				setState(243);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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

	public static class Function_callContext extends ParserRuleContext {
		public Function_identifierContext function_identifier() {
			return getRuleContext(Function_identifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public Function_call_parameter_listContext function_call_parameter_list() {
			return getRuleContext(Function_call_parameter_listContext.class,0);
		}
		public TerminalNode VOID() { return getToken(GLSL.VOID, 0); }
		public Function_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call; }
	}

	public final Function_callContext function_call() throws RecognitionException {
		Function_callContext _localctx = new Function_callContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_function_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			function_identifier();
			setState(254);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==VOID) {
					{
					setState(245);
					match(VOID);
					}
				}

				setState(248);
				match(LPAREN);
				setState(249);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(250);
				match(LPAREN);
				setState(251);
				function_call_parameter_list();
				setState(252);
				match(RPAREN);
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

	public static class Function_call_parameter_listContext extends ParserRuleContext {
		public List<Assignment_expressionContext> assignment_expression() {
			return getRuleContexts(Assignment_expressionContext.class);
		}
		public Assignment_expressionContext assignment_expression(int i) {
			return getRuleContext(Assignment_expressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Function_call_parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call_parameter_list; }
	}

	public final Function_call_parameter_listContext function_call_parameter_list() throws RecognitionException {
		Function_call_parameter_listContext _localctx = new Function_call_parameter_listContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_function_call_parameter_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			assignment_expression();
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(257);
				match(COMMA);
				setState(258);
				assignment_expression();
				}
				}
				setState(263);
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
		enterRule(_localctx, 30, RULE_function_identifier);
		try {
			setState(266);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
			case FLOAT:
			case DOUBLE:
			case INT:
			case UINT:
			case BOOL:
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
			case DVEC2:
			case DVEC3:
			case DVEC4:
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
			case DMAT2X2:
			case DMAT2X3:
			case DMAT2X4:
			case DMAT3X2:
			case DMAT3X3:
			case DMAT3X4:
			case DMAT4X2:
			case DMAT4X3:
			case DMAT4X4:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				builtin_type_specifier_nonarray();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(265);
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

	public static class Unary_expressionContext extends ParserRuleContext {
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Unary_operatorContext unary_operator() {
			return getRuleContext(Unary_operatorContext.class,0);
		}
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_unary_expression);
		try {
			setState(272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTCONSTANT:
			case UINTCONSTANT:
			case FLOATCONSTANT:
			case BOOLCONSTANT:
			case DOUBLECONSTANT:
			case VOID:
			case FLOAT:
			case DOUBLE:
			case INT:
			case UINT:
			case BOOL:
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
			case DVEC2:
			case DVEC3:
			case DVEC4:
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
			case DMAT2X2:
			case DMAT2X3:
			case DMAT2X4:
			case DMAT3X2:
			case DMAT3X3:
			case DMAT3X4:
			case DMAT4X2:
			case DMAT4X3:
			case DMAT4X4:
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
			case LPAREN:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				postfix_expression(0);
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
				setState(269);
				unary_operator();
				setState(270);
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
		public TerminalNode INC_OP() { return getToken(GLSL.INC_OP, 0); }
		public TerminalNode DEC_OP() { return getToken(GLSL.DEC_OP, 0); }
		public TerminalNode PLUS_OP() { return getToken(GLSL.PLUS_OP, 0); }
		public TerminalNode MINUS_OP() { return getToken(GLSL.MINUS_OP, 0); }
		public TerminalNode NOT_OP() { return getToken(GLSL.NOT_OP, 0); }
		public TerminalNode BNEG_OP() { return getToken(GLSL.BNEG_OP, 0); }
		public Unary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_operator; }
	}

	public final Unary_operatorContext unary_operator() throws RecognitionException {
		Unary_operatorContext _localctx = new Unary_operatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			_la = _input.LA(1);
			if ( !(_la==INC_OP || _la==DEC_OP || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)))) != 0)) ) {
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
		public List<Unary_expressionContext> unary_expression() {
			return getRuleContexts(Unary_expressionContext.class);
		}
		public Unary_expressionContext unary_expression(int i) {
			return getRuleContext(Unary_expressionContext.class,i);
		}
		public List<TerminalNode> TIMES_OP() { return getTokens(GLSL.TIMES_OP); }
		public TerminalNode TIMES_OP(int i) {
			return getToken(GLSL.TIMES_OP, i);
		}
		public List<TerminalNode> DIV_OP() { return getTokens(GLSL.DIV_OP); }
		public TerminalNode DIV_OP(int i) {
			return getToken(GLSL.DIV_OP, i);
		}
		public List<TerminalNode> MOD_OP() { return getTokens(GLSL.MOD_OP); }
		public TerminalNode MOD_OP(int i) {
			return getToken(GLSL.MOD_OP, i);
		}
		public Multiplicative_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression; }
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_multiplicative_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			unary_expression();
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 218)) & ~0x3f) == 0 && ((1L << (_la - 218)) & ((1L << (TIMES_OP - 218)) | (1L << (DIV_OP - 218)) | (1L << (MOD_OP - 218)))) != 0)) {
				{
				{
				setState(277);
				_la = _input.LA(1);
				if ( !(((((_la - 218)) & ~0x3f) == 0 && ((1L << (_la - 218)) & ((1L << (TIMES_OP - 218)) | (1L << (DIV_OP - 218)) | (1L << (MOD_OP - 218)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(278);
				unary_expression();
				}
				}
				setState(283);
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

	public static class Additive_expressionContext extends ParserRuleContext {
		public List<Multiplicative_expressionContext> multiplicative_expression() {
			return getRuleContexts(Multiplicative_expressionContext.class);
		}
		public Multiplicative_expressionContext multiplicative_expression(int i) {
			return getRuleContext(Multiplicative_expressionContext.class,i);
		}
		public List<TerminalNode> PLUS_OP() { return getTokens(GLSL.PLUS_OP); }
		public TerminalNode PLUS_OP(int i) {
			return getToken(GLSL.PLUS_OP, i);
		}
		public List<TerminalNode> MINUS_OP() { return getTokens(GLSL.MINUS_OP); }
		public TerminalNode MINUS_OP(int i) {
			return getToken(GLSL.MINUS_OP, i);
		}
		public Additive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression; }
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_additive_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			multiplicative_expression();
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS_OP || _la==MINUS_OP) {
				{
				{
				setState(285);
				_la = _input.LA(1);
				if ( !(_la==PLUS_OP || _la==MINUS_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(286);
				multiplicative_expression();
				}
				}
				setState(291);
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

	public static class Shift_expressionContext extends ParserRuleContext {
		public List<Additive_expressionContext> additive_expression() {
			return getRuleContexts(Additive_expressionContext.class);
		}
		public Additive_expressionContext additive_expression(int i) {
			return getRuleContext(Additive_expressionContext.class,i);
		}
		public List<TerminalNode> LEFT_OP() { return getTokens(GLSL.LEFT_OP); }
		public TerminalNode LEFT_OP(int i) {
			return getToken(GLSL.LEFT_OP, i);
		}
		public List<TerminalNode> RIGHT_OP() { return getTokens(GLSL.RIGHT_OP); }
		public TerminalNode RIGHT_OP(int i) {
			return getToken(GLSL.RIGHT_OP, i);
		}
		public Shift_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shift_expression; }
	}

	public final Shift_expressionContext shift_expression() throws RecognitionException {
		Shift_expressionContext _localctx = new Shift_expressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_shift_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			additive_expression();
			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT_OP || _la==RIGHT_OP) {
				{
				{
				setState(293);
				_la = _input.LA(1);
				if ( !(_la==LEFT_OP || _la==RIGHT_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(294);
				additive_expression();
				}
				}
				setState(299);
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

	public static class Relational_expressionContext extends ParserRuleContext {
		public List<Shift_expressionContext> shift_expression() {
			return getRuleContexts(Shift_expressionContext.class);
		}
		public Shift_expressionContext shift_expression(int i) {
			return getRuleContext(Shift_expressionContext.class,i);
		}
		public List<TerminalNode> LT_OP() { return getTokens(GLSL.LT_OP); }
		public TerminalNode LT_OP(int i) {
			return getToken(GLSL.LT_OP, i);
		}
		public List<TerminalNode> GT_OP() { return getTokens(GLSL.GT_OP); }
		public TerminalNode GT_OP(int i) {
			return getToken(GLSL.GT_OP, i);
		}
		public List<TerminalNode> LE_OP() { return getTokens(GLSL.LE_OP); }
		public TerminalNode LE_OP(int i) {
			return getToken(GLSL.LE_OP, i);
		}
		public List<TerminalNode> GE_OP() { return getTokens(GLSL.GE_OP); }
		public TerminalNode GE_OP(int i) {
			return getToken(GLSL.GE_OP, i);
		}
		public Relational_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expression; }
	}

	public final Relational_expressionContext relational_expression() throws RecognitionException {
		Relational_expressionContext _localctx = new Relational_expressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_relational_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			shift_expression();
			setState(305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LE_OP || _la==GE_OP || _la==LT_OP || _la==GT_OP) {
				{
				{
				setState(301);
				_la = _input.LA(1);
				if ( !(_la==LE_OP || _la==GE_OP || _la==LT_OP || _la==GT_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(302);
				shift_expression();
				}
				}
				setState(307);
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

	public static class Equality_expressionContext extends ParserRuleContext {
		public List<Relational_expressionContext> relational_expression() {
			return getRuleContexts(Relational_expressionContext.class);
		}
		public Relational_expressionContext relational_expression(int i) {
			return getRuleContext(Relational_expressionContext.class,i);
		}
		public List<TerminalNode> EQ_OP() { return getTokens(GLSL.EQ_OP); }
		public TerminalNode EQ_OP(int i) {
			return getToken(GLSL.EQ_OP, i);
		}
		public List<TerminalNode> NE_OP() { return getTokens(GLSL.NE_OP); }
		public TerminalNode NE_OP(int i) {
			return getToken(GLSL.NE_OP, i);
		}
		public Equality_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality_expression; }
	}

	public final Equality_expressionContext equality_expression() throws RecognitionException {
		Equality_expressionContext _localctx = new Equality_expressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_equality_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			relational_expression();
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQ_OP || _la==NE_OP) {
				{
				{
				setState(309);
				_la = _input.LA(1);
				if ( !(_la==EQ_OP || _la==NE_OP) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(310);
				relational_expression();
				}
				}
				setState(315);
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

	public static class And_expressionContext extends ParserRuleContext {
		public List<Equality_expressionContext> equality_expression() {
			return getRuleContexts(Equality_expressionContext.class);
		}
		public Equality_expressionContext equality_expression(int i) {
			return getRuleContext(Equality_expressionContext.class,i);
		}
		public List<TerminalNode> BAND_OP() { return getTokens(GLSL.BAND_OP); }
		public TerminalNode BAND_OP(int i) {
			return getToken(GLSL.BAND_OP, i);
		}
		public And_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expression; }
	}

	public final And_expressionContext and_expression() throws RecognitionException {
		And_expressionContext _localctx = new And_expressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			equality_expression();
			setState(321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BAND_OP) {
				{
				{
				setState(317);
				match(BAND_OP);
				setState(318);
				equality_expression();
				}
				}
				setState(323);
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

	public static class Exclusive_or_expressionContext extends ParserRuleContext {
		public List<And_expressionContext> and_expression() {
			return getRuleContexts(And_expressionContext.class);
		}
		public And_expressionContext and_expression(int i) {
			return getRuleContext(And_expressionContext.class,i);
		}
		public List<TerminalNode> BXOR_OP() { return getTokens(GLSL.BXOR_OP); }
		public TerminalNode BXOR_OP(int i) {
			return getToken(GLSL.BXOR_OP, i);
		}
		public Exclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusive_or_expression; }
	}

	public final Exclusive_or_expressionContext exclusive_or_expression() throws RecognitionException {
		Exclusive_or_expressionContext _localctx = new Exclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_exclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			and_expression();
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BXOR_OP) {
				{
				{
				setState(325);
				match(BXOR_OP);
				setState(326);
				and_expression();
				}
				}
				setState(331);
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

	public static class Inclusive_or_expressionContext extends ParserRuleContext {
		public List<Exclusive_or_expressionContext> exclusive_or_expression() {
			return getRuleContexts(Exclusive_or_expressionContext.class);
		}
		public Exclusive_or_expressionContext exclusive_or_expression(int i) {
			return getRuleContext(Exclusive_or_expressionContext.class,i);
		}
		public List<TerminalNode> BOR_OP() { return getTokens(GLSL.BOR_OP); }
		public TerminalNode BOR_OP(int i) {
			return getToken(GLSL.BOR_OP, i);
		}
		public Inclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusive_or_expression; }
	}

	public final Inclusive_or_expressionContext inclusive_or_expression() throws RecognitionException {
		Inclusive_or_expressionContext _localctx = new Inclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			exclusive_or_expression();
			setState(337);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BOR_OP) {
				{
				{
				setState(333);
				match(BOR_OP);
				setState(334);
				exclusive_or_expression();
				}
				}
				setState(339);
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

	public static class Logical_and_expressionContext extends ParserRuleContext {
		public List<Inclusive_or_expressionContext> inclusive_or_expression() {
			return getRuleContexts(Inclusive_or_expressionContext.class);
		}
		public Inclusive_or_expressionContext inclusive_or_expression(int i) {
			return getRuleContext(Inclusive_or_expressionContext.class,i);
		}
		public List<TerminalNode> AND_OP() { return getTokens(GLSL.AND_OP); }
		public TerminalNode AND_OP(int i) {
			return getToken(GLSL.AND_OP, i);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_logical_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			inclusive_or_expression();
			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_OP) {
				{
				{
				setState(341);
				match(AND_OP);
				setState(342);
				inclusive_or_expression();
				}
				}
				setState(347);
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

	public static class Logical_xor_expressionContext extends ParserRuleContext {
		public List<Logical_and_expressionContext> logical_and_expression() {
			return getRuleContexts(Logical_and_expressionContext.class);
		}
		public Logical_and_expressionContext logical_and_expression(int i) {
			return getRuleContext(Logical_and_expressionContext.class,i);
		}
		public List<TerminalNode> XOR_OP() { return getTokens(GLSL.XOR_OP); }
		public TerminalNode XOR_OP(int i) {
			return getToken(GLSL.XOR_OP, i);
		}
		public Logical_xor_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_xor_expression; }
	}

	public final Logical_xor_expressionContext logical_xor_expression() throws RecognitionException {
		Logical_xor_expressionContext _localctx = new Logical_xor_expressionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_logical_xor_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			logical_and_expression();
			setState(353);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==XOR_OP) {
				{
				{
				setState(349);
				match(XOR_OP);
				setState(350);
				logical_and_expression();
				}
				}
				setState(355);
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

	public static class Logical_or_expressionContext extends ParserRuleContext {
		public List<Logical_xor_expressionContext> logical_xor_expression() {
			return getRuleContexts(Logical_xor_expressionContext.class);
		}
		public Logical_xor_expressionContext logical_xor_expression(int i) {
			return getRuleContext(Logical_xor_expressionContext.class,i);
		}
		public List<TerminalNode> OR_OP() { return getTokens(GLSL.OR_OP); }
		public TerminalNode OR_OP(int i) {
			return getToken(GLSL.OR_OP, i);
		}
		public Logical_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_or_expression; }
	}

	public final Logical_or_expressionContext logical_or_expression() throws RecognitionException {
		Logical_or_expressionContext _localctx = new Logical_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_logical_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			logical_xor_expression();
			setState(361);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OP) {
				{
				{
				setState(357);
				match(OR_OP);
				setState(358);
				logical_xor_expression();
				}
				}
				setState(363);
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

	public static class Conditional_expressionContext extends ParserRuleContext {
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public List<TerminalNode> QUERY_OP() { return getTokens(GLSL.QUERY_OP); }
		public TerminalNode QUERY_OP(int i) {
			return getToken(GLSL.QUERY_OP, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(GLSL.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GLSL.COLON, i);
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
		enterRule(_localctx, 58, RULE_conditional_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			logical_or_expression();
			setState(372);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(365);
					match(QUERY_OP);
					setState(366);
					expression();
					setState(367);
					match(COLON);
					setState(368);
					assignment_expression();
					}
					} 
				}
				setState(374);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
		enterRule(_localctx, 60, RULE_assignment_expression);
		try {
			setState(380);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(375);
				conditional_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(376);
				unary_expression();
				setState(377);
				assignment_operator();
				setState(378);
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
		public TerminalNode ASSIGN_OP() { return getToken(GLSL.ASSIGN_OP, 0); }
		public TerminalNode MUL_ASSIGN() { return getToken(GLSL.MUL_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(GLSL.DIV_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(GLSL.MOD_ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(GLSL.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(GLSL.SUB_ASSIGN, 0); }
		public TerminalNode LEFT_ASSIGN() { return getToken(GLSL.LEFT_ASSIGN, 0); }
		public TerminalNode RIGHT_ASSIGN() { return getToken(GLSL.RIGHT_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(GLSL.AND_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(GLSL.XOR_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(GLSL.OR_ASSIGN, 0); }
		public Assignment_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_operator; }
	}

	public final Assignment_operatorContext assignment_operator() throws RecognitionException {
		Assignment_operatorContext _localctx = new Assignment_operatorContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_assignment_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			_la = _input.LA(1);
			if ( !(((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (MUL_ASSIGN - 69)) | (1L << (DIV_ASSIGN - 69)) | (1L << (MOD_ASSIGN - 69)) | (1L << (ADD_ASSIGN - 69)) | (1L << (SUB_ASSIGN - 69)) | (1L << (LEFT_ASSIGN - 69)) | (1L << (RIGHT_ASSIGN - 69)) | (1L << (AND_ASSIGN - 69)) | (1L << (XOR_ASSIGN - 69)) | (1L << (OR_ASSIGN - 69)))) != 0) || _la==ASSIGN_OP) ) {
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
		public List<Assignment_expressionContext> assignment_expression() {
			return getRuleContexts(Assignment_expressionContext.class);
		}
		public Assignment_expressionContext assignment_expression(int i) {
			return getRuleContext(Assignment_expressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
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
			setState(384);
			assignment_expression();
			setState(389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(385);
				match(COMMA);
				setState(386);
				assignment_expression();
				}
				}
				setState(391);
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
		enterRule(_localctx, 66, RULE_constant_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
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
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Init_declarator_listContext init_declarator_list() {
			return getRuleContext(Init_declarator_listContext.class,0);
		}
		public TerminalNode PRECISION() { return getToken(GLSL.PRECISION, 0); }
		public Precision_qualifierContext precision_qualifier() {
			return getRuleContext(Precision_qualifierContext.class,0);
		}
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Type_qualifierContext type_qualifier() {
			return getRuleContext(Type_qualifierContext.class,0);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSL.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSL.IDENTIFIER, i);
		}
		public List<TerminalNode> LBRACE() { return getTokens(GLSL.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(GLSL.LBRACE, i);
		}
		public Struct_declaration_listContext struct_declaration_list() {
			return getRuleContext(Struct_declaration_listContext.class,0);
		}
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
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
			setState(431);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(394);
				function_prototype();
				setState(395);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(397);
				init_declarator_list();
				setState(398);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(400);
				match(PRECISION);
				setState(401);
				precision_qualifier();
				setState(402);
				type_specifier();
				setState(403);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(405);
				type_qualifier();
				setState(406);
				match(IDENTIFIER);
				setState(407);
				match(LBRACE);
				setState(408);
				struct_declaration_list();
				setState(409);
				match(LBRACE);
				setState(414);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(410);
					match(IDENTIFIER);
					setState(412);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==LBRACKET) {
						{
						setState(411);
						array_specifier(0);
						}
					}

					}
				}

				setState(416);
				match(SEMICOLON);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(418);
				type_qualifier();
				setState(427);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(419);
					match(IDENTIFIER);
					setState(424);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(420);
						match(COMMA);
						setState(421);
						match(IDENTIFIER);
						}
						}
						setState(426);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(429);
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

	public static class Function_prototypeContext extends ParserRuleContext {
		public Function_headerContext function_header() {
			return getRuleContext(Function_headerContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public Function_parameter_listContext function_parameter_list() {
			return getRuleContext(Function_parameter_listContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public Function_prototypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_prototype; }
	}

	public final Function_prototypeContext function_prototype() throws RecognitionException {
		Function_prototypeContext _localctx = new Function_prototypeContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_function_prototype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(433);
			function_header();
			setState(434);
			match(LPAREN);
			setState(435);
			function_parameter_list();
			setState(436);
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

	public static class Function_parameter_listContext extends ParserRuleContext {
		public List<Parameter_declarationContext> parameter_declaration() {
			return getRuleContexts(Parameter_declarationContext.class);
		}
		public Parameter_declarationContext parameter_declaration(int i) {
			return getRuleContext(Parameter_declarationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Function_parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_parameter_list; }
	}

	public final Function_parameter_listContext function_parameter_list() throws RecognitionException {
		Function_parameter_listContext _localctx = new Function_parameter_listContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_function_parameter_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			parameter_declaration();
			setState(443);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(439);
				match(COMMA);
				setState(440);
				parameter_declaration();
				}
				}
				setState(445);
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

	public static class Function_headerContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public Function_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_header; }
	}

	public final Function_headerContext function_header() throws RecognitionException {
		Function_headerContext _localctx = new Function_headerContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_function_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(446);
			fully_specified_type();
			setState(447);
			variable_identifier();
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
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
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
		enterRule(_localctx, 76, RULE_parameter_declarator);
		try {
			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(449);
				type_specifier();
				setState(450);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(452);
				type_specifier();
				setState(453);
				match(IDENTIFIER);
				setState(454);
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
		public Parameter_declaratorContext parameter_declarator() {
			return getRuleContext(Parameter_declaratorContext.class,0);
		}
		public Type_qualifierContext type_qualifier() {
			return getRuleContext(Type_qualifierContext.class,0);
		}
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public Parameter_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_declaration; }
	}

	public final Parameter_declarationContext parameter_declaration() throws RecognitionException {
		Parameter_declarationContext _localctx = new Parameter_declarationContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_parameter_declaration);
		int _la;
		try {
			setState(463);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(459);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT))) != 0)) {
					{
					setState(458);
					type_qualifier();
					}
				}

				setState(461);
				parameter_declarator();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(462);
				fully_specified_type();
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

	public static class Init_declarator_listContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public List<Declaration_memberContext> declaration_member() {
			return getRuleContexts(Declaration_memberContext.class);
		}
		public Declaration_memberContext declaration_member(int i) {
			return getRuleContext(Declaration_memberContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Init_declarator_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init_declarator_list; }
	}

	public final Init_declarator_listContext init_declarator_list() throws RecognitionException {
		Init_declarator_listContext _localctx = new Init_declarator_listContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_init_declarator_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			fully_specified_type();
			setState(467);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(466);
				declaration_member();
				}
			}

			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(469);
				match(COMMA);
				setState(470);
				declaration_member();
				}
				}
				setState(475);
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

	public static class Declaration_memberContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public Array_specifierContext array_specifier() {
			return getRuleContext(Array_specifierContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(GLSL.ASSIGN_OP, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public Declaration_memberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_member; }
	}

	public final Declaration_memberContext declaration_member() throws RecognitionException {
		Declaration_memberContext _localctx = new Declaration_memberContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_declaration_member);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(476);
			match(IDENTIFIER);
			setState(478);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(477);
				array_specifier(0);
				}
			}

			setState(482);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN_OP) {
				{
				setState(480);
				match(ASSIGN_OP);
				setState(481);
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
		enterRule(_localctx, 84, RULE_fully_specified_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT))) != 0)) {
				{
				setState(484);
				type_qualifier();
				}
			}

			setState(487);
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

	public static class Storage_qualifierContext extends ParserRuleContext {
		public TerminalNode CONST() { return getToken(GLSL.CONST, 0); }
		public TerminalNode IN() { return getToken(GLSL.IN, 0); }
		public TerminalNode OUT() { return getToken(GLSL.OUT, 0); }
		public TerminalNode INOUT() { return getToken(GLSL.INOUT, 0); }
		public TerminalNode CENTROID() { return getToken(GLSL.CENTROID, 0); }
		public TerminalNode PATCH() { return getToken(GLSL.PATCH, 0); }
		public TerminalNode SAMPLE() { return getToken(GLSL.SAMPLE, 0); }
		public TerminalNode UNIFORM() { return getToken(GLSL.UNIFORM, 0); }
		public TerminalNode BUFFER() { return getToken(GLSL.BUFFER, 0); }
		public TerminalNode SHARED() { return getToken(GLSL.SHARED, 0); }
		public TerminalNode COHERENT() { return getToken(GLSL.COHERENT, 0); }
		public TerminalNode VOLATILE() { return getToken(GLSL.VOLATILE, 0); }
		public TerminalNode RESTRICT() { return getToken(GLSL.RESTRICT, 0); }
		public TerminalNode READONLY() { return getToken(GLSL.READONLY, 0); }
		public TerminalNode WRITEONLY() { return getToken(GLSL.WRITEONLY, 0); }
		public TerminalNode SUBROUTINE() { return getToken(GLSL.SUBROUTINE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public Type_name_listContext type_name_list() {
			return getRuleContext(Type_name_listContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public Storage_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storage_qualifier; }
	}

	public final Storage_qualifierContext storage_qualifier() throws RecognitionException {
		Storage_qualifierContext _localctx = new Storage_qualifierContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_storage_qualifier);
		int _la;
		try {
			setState(511);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONST:
				enterOuterAlt(_localctx, 1);
				{
				setState(489);
				match(CONST);
				}
				break;
			case IN:
				enterOuterAlt(_localctx, 2);
				{
				setState(490);
				match(IN);
				}
				break;
			case OUT:
				enterOuterAlt(_localctx, 3);
				{
				setState(491);
				match(OUT);
				}
				break;
			case INOUT:
				enterOuterAlt(_localctx, 4);
				{
				setState(492);
				match(INOUT);
				}
				break;
			case CENTROID:
				enterOuterAlt(_localctx, 5);
				{
				setState(493);
				match(CENTROID);
				}
				break;
			case PATCH:
				enterOuterAlt(_localctx, 6);
				{
				setState(494);
				match(PATCH);
				}
				break;
			case SAMPLE:
				enterOuterAlt(_localctx, 7);
				{
				setState(495);
				match(SAMPLE);
				}
				break;
			case UNIFORM:
				enterOuterAlt(_localctx, 8);
				{
				setState(496);
				match(UNIFORM);
				}
				break;
			case BUFFER:
				enterOuterAlt(_localctx, 9);
				{
				setState(497);
				match(BUFFER);
				}
				break;
			case SHARED:
				enterOuterAlt(_localctx, 10);
				{
				setState(498);
				match(SHARED);
				}
				break;
			case COHERENT:
				enterOuterAlt(_localctx, 11);
				{
				setState(499);
				match(COHERENT);
				}
				break;
			case VOLATILE:
				enterOuterAlt(_localctx, 12);
				{
				setState(500);
				match(VOLATILE);
				}
				break;
			case RESTRICT:
				enterOuterAlt(_localctx, 13);
				{
				setState(501);
				match(RESTRICT);
				}
				break;
			case READONLY:
				enterOuterAlt(_localctx, 14);
				{
				setState(502);
				match(READONLY);
				}
				break;
			case WRITEONLY:
				enterOuterAlt(_localctx, 15);
				{
				setState(503);
				match(WRITEONLY);
				}
				break;
			case SUBROUTINE:
				enterOuterAlt(_localctx, 16);
				{
				setState(504);
				match(SUBROUTINE);
				setState(509);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(505);
					match(LPAREN);
					setState(506);
					type_name_list();
					setState(507);
					match(RPAREN);
					}
				}

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
		public TerminalNode LAYOUT() { return getToken(GLSL.LAYOUT, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public List<Layout_qualifier_idContext> layout_qualifier_id() {
			return getRuleContexts(Layout_qualifier_idContext.class);
		}
		public Layout_qualifier_idContext layout_qualifier_id(int i) {
			return getRuleContext(Layout_qualifier_idContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Layout_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_qualifier; }
	}

	public final Layout_qualifierContext layout_qualifier() throws RecognitionException {
		Layout_qualifierContext _localctx = new Layout_qualifierContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_layout_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(513);
			match(LAYOUT);
			setState(514);
			match(LPAREN);
			setState(515);
			layout_qualifier_id();
			setState(520);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(516);
				match(COMMA);
				setState(517);
				layout_qualifier_id();
				}
				}
				setState(522);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(523);
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

	public static class Layout_qualifier_idContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSL.ASSIGN_OP, 0); }
		public Constant_expressionContext constant_expression() {
			return getRuleContext(Constant_expressionContext.class,0);
		}
		public TerminalNode SHARED() { return getToken(GLSL.SHARED, 0); }
		public Layout_qualifier_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout_qualifier_id; }
	}

	public final Layout_qualifier_idContext layout_qualifier_id() throws RecognitionException {
		Layout_qualifier_idContext _localctx = new Layout_qualifier_idContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_layout_qualifier_id);
		int _la;
		try {
			setState(531);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(525);
				match(IDENTIFIER);
				setState(528);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN_OP) {
					{
					setState(526);
					match(ASSIGN_OP);
					setState(527);
					constant_expression();
					}
				}

				}
				break;
			case SHARED:
				enterOuterAlt(_localctx, 2);
				{
				setState(530);
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

	public static class Precision_qualifierContext extends ParserRuleContext {
		public TerminalNode HIGHP() { return getToken(GLSL.HIGHP, 0); }
		public TerminalNode MEDIUMP() { return getToken(GLSL.MEDIUMP, 0); }
		public TerminalNode LOWP() { return getToken(GLSL.LOWP, 0); }
		public Precision_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_precision_qualifier; }
	}

	public final Precision_qualifierContext precision_qualifier() throws RecognitionException {
		Precision_qualifierContext _localctx = new Precision_qualifierContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_precision_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
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

	public static class Interpolation_qualifierContext extends ParserRuleContext {
		public TerminalNode SMOOTH() { return getToken(GLSL.SMOOTH, 0); }
		public TerminalNode FLAT() { return getToken(GLSL.FLAT, 0); }
		public TerminalNode NOPERSPECTIVE() { return getToken(GLSL.NOPERSPECTIVE, 0); }
		public Interpolation_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interpolation_qualifier; }
	}

	public final Interpolation_qualifierContext interpolation_qualifier() throws RecognitionException {
		Interpolation_qualifierContext _localctx = new Interpolation_qualifierContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_interpolation_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(535);
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

	public static class Invariant_qualifierContext extends ParserRuleContext {
		public TerminalNode INVARIANT() { return getToken(GLSL.INVARIANT, 0); }
		public Invariant_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invariant_qualifier; }
	}

	public final Invariant_qualifierContext invariant_qualifier() throws RecognitionException {
		Invariant_qualifierContext _localctx = new Invariant_qualifierContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_invariant_qualifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
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

	public static class Precise_qualifierContext extends ParserRuleContext {
		public TerminalNode PRECISE() { return getToken(GLSL.PRECISE, 0); }
		public Precise_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_precise_qualifier; }
	}

	public final Precise_qualifierContext precise_qualifier() throws RecognitionException {
		Precise_qualifierContext _localctx = new Precise_qualifierContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_precise_qualifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
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

	public static class Type_qualifierContext extends ParserRuleContext {
		public List<Storage_qualifierContext> storage_qualifier() {
			return getRuleContexts(Storage_qualifierContext.class);
		}
		public Storage_qualifierContext storage_qualifier(int i) {
			return getRuleContext(Storage_qualifierContext.class,i);
		}
		public List<Layout_qualifierContext> layout_qualifier() {
			return getRuleContexts(Layout_qualifierContext.class);
		}
		public Layout_qualifierContext layout_qualifier(int i) {
			return getRuleContext(Layout_qualifierContext.class,i);
		}
		public List<Precision_qualifierContext> precision_qualifier() {
			return getRuleContexts(Precision_qualifierContext.class);
		}
		public Precision_qualifierContext precision_qualifier(int i) {
			return getRuleContext(Precision_qualifierContext.class,i);
		}
		public List<Interpolation_qualifierContext> interpolation_qualifier() {
			return getRuleContexts(Interpolation_qualifierContext.class);
		}
		public Interpolation_qualifierContext interpolation_qualifier(int i) {
			return getRuleContext(Interpolation_qualifierContext.class,i);
		}
		public List<Invariant_qualifierContext> invariant_qualifier() {
			return getRuleContexts(Invariant_qualifierContext.class);
		}
		public Invariant_qualifierContext invariant_qualifier(int i) {
			return getRuleContext(Invariant_qualifierContext.class,i);
		}
		public List<Precise_qualifierContext> precise_qualifier() {
			return getRuleContexts(Precise_qualifierContext.class);
		}
		public Precise_qualifierContext precise_qualifier(int i) {
			return getRuleContext(Precise_qualifierContext.class,i);
		}
		public Type_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_qualifier; }
	}

	public final Type_qualifierContext type_qualifier() throws RecognitionException {
		Type_qualifierContext _localctx = new Type_qualifierContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_type_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(547);
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
				case COHERENT:
				case VOLATILE:
				case RESTRICT:
				case READONLY:
				case WRITEONLY:
				case SHARED:
				case SUBROUTINE:
					{
					setState(541);
					storage_qualifier();
					}
					break;
				case LAYOUT:
					{
					setState(542);
					layout_qualifier();
					}
					break;
				case HIGHP:
				case MEDIUMP:
				case LOWP:
					{
					setState(543);
					precision_qualifier();
					}
					break;
				case SMOOTH:
				case FLAT:
				case NOPERSPECTIVE:
					{
					setState(544);
					interpolation_qualifier();
					}
					break;
				case INVARIANT:
					{
					setState(545);
					invariant_qualifier();
					}
					break;
				case PRECISE:
					{
					setState(546);
					precise_qualifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(549); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT))) != 0) );
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

	public static class Type_name_listContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(GLSL.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GLSL.IDENTIFIER, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Type_name_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_name_list; }
	}

	public final Type_name_listContext type_name_list() throws RecognitionException {
		Type_name_listContext _localctx = new Type_name_listContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_type_name_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(551);
			match(IDENTIFIER);
			setState(556);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(552);
				match(COMMA);
				setState(553);
				match(IDENTIFIER);
				}
				}
				setState(558);
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
		enterRule(_localctx, 104, RULE_type_specifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			type_specifier_nonarray();
			setState(561);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(560);
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

	public static class Array_specifierContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(GLSL.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(GLSL.RBRACKET, 0); }
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
		int _startState = 106;
		enterRecursionRule(_localctx, 106, RULE_array_specifier, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(564);
			match(LBRACKET);
			setState(566);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTCONSTANT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)) | (1L << (IDENTIFIER - 214)))) != 0)) {
				{
				setState(565);
				constant_expression();
				}
			}

			setState(568);
			match(RBRACKET);
			}
			_ctx.stop = _input.LT(-1);
			setState(578);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Array_specifierContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_array_specifier);
					setState(570);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(571);
					match(LBRACKET);
					setState(573);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTCONSTANT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)) | (1L << (IDENTIFIER - 214)))) != 0)) {
						{
						setState(572);
						constant_expression();
						}
					}

					setState(575);
					match(RBRACKET);
					}
					} 
				}
				setState(580);
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

	public static class Type_specifier_nonarrayContext extends ParserRuleContext {
		public Builtin_type_specifier_nonarrayContext builtin_type_specifier_nonarray() {
			return getRuleContext(Builtin_type_specifier_nonarrayContext.class,0);
		}
		public Struct_specifierContext struct_specifier() {
			return getRuleContext(Struct_specifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public Type_specifier_nonarrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_nonarray; }
	}

	public final Type_specifier_nonarrayContext type_specifier_nonarray() throws RecognitionException {
		Type_specifier_nonarrayContext _localctx = new Type_specifier_nonarrayContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_type_specifier_nonarray);
		try {
			setState(584);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
			case FLOAT:
			case DOUBLE:
			case INT:
			case UINT:
			case BOOL:
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
			case DVEC2:
			case DVEC3:
			case DVEC4:
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
			case DMAT2X2:
			case DMAT2X3:
			case DMAT2X4:
			case DMAT3X2:
			case DMAT3X3:
			case DMAT3X4:
			case DMAT4X2:
			case DMAT4X3:
			case DMAT4X4:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(581);
				builtin_type_specifier_nonarray();
				}
				break;
			case STRUCT:
				enterOuterAlt(_localctx, 2);
				{
				setState(582);
				struct_specifier();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(583);
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
		public TerminalNode VOID() { return getToken(GLSL.VOID, 0); }
		public TerminalNode FLOAT() { return getToken(GLSL.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(GLSL.DOUBLE, 0); }
		public TerminalNode INT() { return getToken(GLSL.INT, 0); }
		public TerminalNode UINT() { return getToken(GLSL.UINT, 0); }
		public TerminalNode BOOL() { return getToken(GLSL.BOOL, 0); }
		public TerminalNode VEC2() { return getToken(GLSL.VEC2, 0); }
		public TerminalNode VEC3() { return getToken(GLSL.VEC3, 0); }
		public TerminalNode VEC4() { return getToken(GLSL.VEC4, 0); }
		public TerminalNode DVEC2() { return getToken(GLSL.DVEC2, 0); }
		public TerminalNode DVEC3() { return getToken(GLSL.DVEC3, 0); }
		public TerminalNode DVEC4() { return getToken(GLSL.DVEC4, 0); }
		public TerminalNode BVEC2() { return getToken(GLSL.BVEC2, 0); }
		public TerminalNode BVEC3() { return getToken(GLSL.BVEC3, 0); }
		public TerminalNode BVEC4() { return getToken(GLSL.BVEC4, 0); }
		public TerminalNode IVEC2() { return getToken(GLSL.IVEC2, 0); }
		public TerminalNode IVEC3() { return getToken(GLSL.IVEC3, 0); }
		public TerminalNode IVEC4() { return getToken(GLSL.IVEC4, 0); }
		public TerminalNode UVEC2() { return getToken(GLSL.UVEC2, 0); }
		public TerminalNode UVEC3() { return getToken(GLSL.UVEC3, 0); }
		public TerminalNode UVEC4() { return getToken(GLSL.UVEC4, 0); }
		public TerminalNode MAT2X2() { return getToken(GLSL.MAT2X2, 0); }
		public TerminalNode MAT2X3() { return getToken(GLSL.MAT2X3, 0); }
		public TerminalNode MAT2X4() { return getToken(GLSL.MAT2X4, 0); }
		public TerminalNode MAT3X2() { return getToken(GLSL.MAT3X2, 0); }
		public TerminalNode MAT3X3() { return getToken(GLSL.MAT3X3, 0); }
		public TerminalNode MAT3X4() { return getToken(GLSL.MAT3X4, 0); }
		public TerminalNode MAT4X2() { return getToken(GLSL.MAT4X2, 0); }
		public TerminalNode MAT4X3() { return getToken(GLSL.MAT4X3, 0); }
		public TerminalNode MAT4X4() { return getToken(GLSL.MAT4X4, 0); }
		public TerminalNode DMAT2X2() { return getToken(GLSL.DMAT2X2, 0); }
		public TerminalNode DMAT2X3() { return getToken(GLSL.DMAT2X3, 0); }
		public TerminalNode DMAT2X4() { return getToken(GLSL.DMAT2X4, 0); }
		public TerminalNode DMAT3X2() { return getToken(GLSL.DMAT3X2, 0); }
		public TerminalNode DMAT3X3() { return getToken(GLSL.DMAT3X3, 0); }
		public TerminalNode DMAT3X4() { return getToken(GLSL.DMAT3X4, 0); }
		public TerminalNode DMAT4X2() { return getToken(GLSL.DMAT4X2, 0); }
		public TerminalNode DMAT4X3() { return getToken(GLSL.DMAT4X3, 0); }
		public TerminalNode DMAT4X4() { return getToken(GLSL.DMAT4X4, 0); }
		public TerminalNode ATOMIC_UINT() { return getToken(GLSL.ATOMIC_UINT, 0); }
		public TerminalNode SAMPLER2D() { return getToken(GLSL.SAMPLER2D, 0); }
		public TerminalNode SAMPLER3D() { return getToken(GLSL.SAMPLER3D, 0); }
		public TerminalNode SAMPLERCUBE() { return getToken(GLSL.SAMPLERCUBE, 0); }
		public TerminalNode SAMPLER2DSHADOW() { return getToken(GLSL.SAMPLER2DSHADOW, 0); }
		public TerminalNode SAMPLERCUBESHADOW() { return getToken(GLSL.SAMPLERCUBESHADOW, 0); }
		public TerminalNode SAMPLER2DARRAY() { return getToken(GLSL.SAMPLER2DARRAY, 0); }
		public TerminalNode SAMPLER2DARRAYSHADOW() { return getToken(GLSL.SAMPLER2DARRAYSHADOW, 0); }
		public TerminalNode SAMPLERCUBEARRAY() { return getToken(GLSL.SAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLERCUBEARRAYSHADOW() { return getToken(GLSL.SAMPLERCUBEARRAYSHADOW, 0); }
		public TerminalNode ISAMPLER2D() { return getToken(GLSL.ISAMPLER2D, 0); }
		public TerminalNode ISAMPLER3D() { return getToken(GLSL.ISAMPLER3D, 0); }
		public TerminalNode ISAMPLERCUBE() { return getToken(GLSL.ISAMPLERCUBE, 0); }
		public TerminalNode ISAMPLER2DARRAY() { return getToken(GLSL.ISAMPLER2DARRAY, 0); }
		public TerminalNode ISAMPLERCUBEARRAY() { return getToken(GLSL.ISAMPLERCUBEARRAY, 0); }
		public TerminalNode USAMPLER2D() { return getToken(GLSL.USAMPLER2D, 0); }
		public TerminalNode USAMPLER3D() { return getToken(GLSL.USAMPLER3D, 0); }
		public TerminalNode USAMPLERCUBE() { return getToken(GLSL.USAMPLERCUBE, 0); }
		public TerminalNode USAMPLER2DARRAY() { return getToken(GLSL.USAMPLER2DARRAY, 0); }
		public TerminalNode USAMPLERCUBEARRAY() { return getToken(GLSL.USAMPLERCUBEARRAY, 0); }
		public TerminalNode SAMPLER1D() { return getToken(GLSL.SAMPLER1D, 0); }
		public TerminalNode SAMPLER1DSHADOW() { return getToken(GLSL.SAMPLER1DSHADOW, 0); }
		public TerminalNode SAMPLER1DARRAY() { return getToken(GLSL.SAMPLER1DARRAY, 0); }
		public TerminalNode SAMPLER1DARRAYSHADOW() { return getToken(GLSL.SAMPLER1DARRAYSHADOW, 0); }
		public TerminalNode ISAMPLER1D() { return getToken(GLSL.ISAMPLER1D, 0); }
		public TerminalNode ISAMPLER1DARRAY() { return getToken(GLSL.ISAMPLER1DARRAY, 0); }
		public TerminalNode USAMPLER1D() { return getToken(GLSL.USAMPLER1D, 0); }
		public TerminalNode USAMPLER1DARRAY() { return getToken(GLSL.USAMPLER1DARRAY, 0); }
		public TerminalNode SAMPLER2DRECT() { return getToken(GLSL.SAMPLER2DRECT, 0); }
		public TerminalNode SAMPLER2DRECTSHADOW() { return getToken(GLSL.SAMPLER2DRECTSHADOW, 0); }
		public TerminalNode ISAMPLER2DRECT() { return getToken(GLSL.ISAMPLER2DRECT, 0); }
		public TerminalNode USAMPLER2DRECT() { return getToken(GLSL.USAMPLER2DRECT, 0); }
		public TerminalNode SAMPLERBUFFER() { return getToken(GLSL.SAMPLERBUFFER, 0); }
		public TerminalNode ISAMPLERBUFFER() { return getToken(GLSL.ISAMPLERBUFFER, 0); }
		public TerminalNode USAMPLERBUFFER() { return getToken(GLSL.USAMPLERBUFFER, 0); }
		public TerminalNode SAMPLER2DMS() { return getToken(GLSL.SAMPLER2DMS, 0); }
		public TerminalNode ISAMPLER2DMS() { return getToken(GLSL.ISAMPLER2DMS, 0); }
		public TerminalNode USAMPLER2DMS() { return getToken(GLSL.USAMPLER2DMS, 0); }
		public TerminalNode SAMPLER2DMSARRAY() { return getToken(GLSL.SAMPLER2DMSARRAY, 0); }
		public TerminalNode ISAMPLER2DMSARRAY() { return getToken(GLSL.ISAMPLER2DMSARRAY, 0); }
		public TerminalNode USAMPLER2DMSARRAY() { return getToken(GLSL.USAMPLER2DMSARRAY, 0); }
		public TerminalNode IMAGE2D() { return getToken(GLSL.IMAGE2D, 0); }
		public TerminalNode IIMAGE2D() { return getToken(GLSL.IIMAGE2D, 0); }
		public TerminalNode UIMAGE2D() { return getToken(GLSL.UIMAGE2D, 0); }
		public TerminalNode IMAGE3D() { return getToken(GLSL.IMAGE3D, 0); }
		public TerminalNode IIMAGE3D() { return getToken(GLSL.IIMAGE3D, 0); }
		public TerminalNode UIMAGE3D() { return getToken(GLSL.UIMAGE3D, 0); }
		public TerminalNode IMAGECUBE() { return getToken(GLSL.IMAGECUBE, 0); }
		public TerminalNode IIMAGECUBE() { return getToken(GLSL.IIMAGECUBE, 0); }
		public TerminalNode UIMAGECUBE() { return getToken(GLSL.UIMAGECUBE, 0); }
		public TerminalNode IMAGEBUFFER() { return getToken(GLSL.IMAGEBUFFER, 0); }
		public TerminalNode IIMAGEBUFFER() { return getToken(GLSL.IIMAGEBUFFER, 0); }
		public TerminalNode UIMAGEBUFFER() { return getToken(GLSL.UIMAGEBUFFER, 0); }
		public TerminalNode IMAGE1D() { return getToken(GLSL.IMAGE1D, 0); }
		public TerminalNode IIMAGE1D() { return getToken(GLSL.IIMAGE1D, 0); }
		public TerminalNode UIMAGE1D() { return getToken(GLSL.UIMAGE1D, 0); }
		public TerminalNode IMAGE1DARRAY() { return getToken(GLSL.IMAGE1DARRAY, 0); }
		public TerminalNode IIMAGE1DARRAY() { return getToken(GLSL.IIMAGE1DARRAY, 0); }
		public TerminalNode UIMAGE1DARRAY() { return getToken(GLSL.UIMAGE1DARRAY, 0); }
		public TerminalNode IMAGE2DRECT() { return getToken(GLSL.IMAGE2DRECT, 0); }
		public TerminalNode IIMAGE2DRECT() { return getToken(GLSL.IIMAGE2DRECT, 0); }
		public TerminalNode UIMAGE2DRECT() { return getToken(GLSL.UIMAGE2DRECT, 0); }
		public TerminalNode IMAGE2DARRAY() { return getToken(GLSL.IMAGE2DARRAY, 0); }
		public TerminalNode IIMAGE2DARRAY() { return getToken(GLSL.IIMAGE2DARRAY, 0); }
		public TerminalNode UIMAGE2DARRAY() { return getToken(GLSL.UIMAGE2DARRAY, 0); }
		public TerminalNode IMAGECUBEARRAY() { return getToken(GLSL.IMAGECUBEARRAY, 0); }
		public TerminalNode IIMAGECUBEARRAY() { return getToken(GLSL.IIMAGECUBEARRAY, 0); }
		public TerminalNode UIMAGECUBEARRAY() { return getToken(GLSL.UIMAGECUBEARRAY, 0); }
		public TerminalNode IMAGE2DMS() { return getToken(GLSL.IMAGE2DMS, 0); }
		public TerminalNode IIMAGE2DMS() { return getToken(GLSL.IIMAGE2DMS, 0); }
		public TerminalNode UIMAGE2DMS() { return getToken(GLSL.UIMAGE2DMS, 0); }
		public TerminalNode IMAGE2DMSARRAY() { return getToken(GLSL.IMAGE2DMSARRAY, 0); }
		public TerminalNode IIMAGE2DMSARRAY() { return getToken(GLSL.IIMAGE2DMSARRAY, 0); }
		public TerminalNode UIMAGE2DMSARRAY() { return getToken(GLSL.UIMAGE2DMSARRAY, 0); }
		public Builtin_type_specifier_nonarrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtin_type_specifier_nonarray; }
	}

	public final Builtin_type_specifier_nonarrayContext builtin_type_specifier_nonarray() throws RecognitionException {
		Builtin_type_specifier_nonarrayContext _localctx = new Builtin_type_specifier_nonarrayContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_builtin_type_specifier_nonarray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(586);
			_la = _input.LA(1);
			if ( !(_la==VOID || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)))) != 0)) ) {
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
		public TerminalNode STRUCT() { return getToken(GLSL.STRUCT, 0); }
		public TerminalNode LBRACE() { return getToken(GLSL.LBRACE, 0); }
		public Struct_declaration_listContext struct_declaration_list() {
			return getRuleContext(Struct_declaration_listContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(GLSL.RBRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public Struct_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_specifier; }
	}

	public final Struct_specifierContext struct_specifier() throws RecognitionException {
		Struct_specifierContext _localctx = new Struct_specifierContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_struct_specifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(588);
			match(STRUCT);
			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(589);
				match(IDENTIFIER);
				}
			}

			setState(592);
			match(LBRACE);
			setState(593);
			struct_declaration_list();
			setState(594);
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

	public static class Struct_declaration_listContext extends ParserRuleContext {
		public List<Struct_declarationContext> struct_declaration() {
			return getRuleContexts(Struct_declarationContext.class);
		}
		public Struct_declarationContext struct_declaration(int i) {
			return getRuleContext(Struct_declarationContext.class,i);
		}
		public Struct_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_declaration_list; }
	}

	public final Struct_declaration_listContext struct_declaration_list() throws RecognitionException {
		Struct_declaration_listContext _localctx = new Struct_declaration_listContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_struct_declaration_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(596);
				struct_declaration();
				}
				}
				setState(599); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (STRUCT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)))) != 0) || _la==IDENTIFIER );
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

	public static class Struct_declarationContext extends ParserRuleContext {
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public Struct_declarator_listContext struct_declarator_list() {
			return getRuleContext(Struct_declarator_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Struct_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_declaration; }
	}

	public final Struct_declarationContext struct_declaration() throws RecognitionException {
		Struct_declarationContext _localctx = new Struct_declarationContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_struct_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
			fully_specified_type();
			setState(602);
			struct_declarator_list();
			setState(603);
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
		public List<Struct_declaratorContext> struct_declarator() {
			return getRuleContexts(Struct_declaratorContext.class);
		}
		public Struct_declaratorContext struct_declarator(int i) {
			return getRuleContext(Struct_declaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public Struct_declarator_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_declarator_list; }
	}

	public final Struct_declarator_listContext struct_declarator_list() throws RecognitionException {
		Struct_declarator_listContext _localctx = new Struct_declarator_listContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_struct_declarator_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(605);
			struct_declarator();
			setState(610);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(606);
				match(COMMA);
				setState(607);
				struct_declarator();
				}
				}
				setState(612);
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

	public static class Struct_declaratorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
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
		enterRule(_localctx, 120, RULE_struct_declarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(613);
			match(IDENTIFIER);
			setState(615);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACKET) {
				{
				setState(614);
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

	public static class InitializerContext extends ParserRuleContext {
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(GLSL.LBRACE, 0); }
		public List<InitializerContext> initializer() {
			return getRuleContexts(InitializerContext.class);
		}
		public InitializerContext initializer(int i) {
			return getRuleContext(InitializerContext.class,i);
		}
		public TerminalNode RBRACE() { return getToken(GLSL.RBRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GLSL.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GLSL.COMMA, i);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_initializer);
		int _la;
		try {
			int _alt;
			setState(632);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTCONSTANT:
			case UINTCONSTANT:
			case FLOATCONSTANT:
			case BOOLCONSTANT:
			case DOUBLECONSTANT:
			case INC_OP:
			case DEC_OP:
			case VOID:
			case FLOAT:
			case DOUBLE:
			case INT:
			case UINT:
			case BOOL:
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
			case DVEC2:
			case DVEC3:
			case DVEC4:
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
			case DMAT2X2:
			case DMAT2X3:
			case DMAT2X4:
			case DMAT3X2:
			case DMAT3X3:
			case DMAT3X4:
			case DMAT4X2:
			case DMAT4X3:
			case DMAT4X4:
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
			case LPAREN:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(617);
				assignment_expression();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(618);
				match(LBRACE);
				setState(619);
				initializer();
				setState(624);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(620);
						match(COMMA);
						setState(621);
						initializer();
						}
						} 
					}
					setState(626);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
				}
				setState(628);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(627);
					match(COMMA);
					}
				}

				setState(630);
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
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Simple_statementContext simple_statement() {
			return getRuleContext(Simple_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_statement);
		try {
			setState(636);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(634);
				compound_statement();
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
			case INTCONSTANT:
			case CONST:
			case PRECISE:
			case INVARIANT:
			case SMOOTH:
			case FLAT:
			case NOPERSPECTIVE:
			case CENTROID:
			case SAMPLE:
			case PATCH:
			case COHERENT:
			case VOLATILE:
			case RESTRICT:
			case READONLY:
			case WRITEONLY:
			case SHARED:
			case SUBROUTINE:
			case LAYOUT:
			case UINTCONSTANT:
			case FLOATCONSTANT:
			case BOOLCONSTANT:
			case DOUBLECONSTANT:
			case INC_OP:
			case DEC_OP:
			case VOID:
			case FLOAT:
			case DOUBLE:
			case INT:
			case UINT:
			case BOOL:
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
			case VEC2:
			case VEC3:
			case VEC4:
			case DVEC2:
			case DVEC3:
			case DVEC4:
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
			case DMAT2X2:
			case DMAT2X3:
			case DMAT2X4:
			case DMAT3X2:
			case DMAT3X3:
			case DMAT3X4:
			case DMAT4X2:
			case DMAT4X3:
			case DMAT4X4:
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
			case LPAREN:
			case SEMICOLON:
			case PLUS_OP:
			case MINUS_OP:
			case NOT_OP:
			case BNEG_OP:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(635);
				simple_statement();
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

	public static class Simple_statementContext extends ParserRuleContext {
		public Declaration_statementContext declaration_statement() {
			return getRuleContext(Declaration_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Empty_statementContext empty_statement() {
			return getRuleContext(Empty_statementContext.class,0);
		}
		public Selection_statementContext selection_statement() {
			return getRuleContext(Selection_statementContext.class,0);
		}
		public Switch_statementContext switch_statement() {
			return getRuleContext(Switch_statementContext.class,0);
		}
		public Case_labelContext case_label() {
			return getRuleContext(Case_labelContext.class,0);
		}
		public For_statementContext for_statement() {
			return getRuleContext(For_statementContext.class,0);
		}
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public Do_while_statementContext do_while_statement() {
			return getRuleContext(Do_while_statementContext.class,0);
		}
		public Jump_statementContext jump_statement() {
			return getRuleContext(Jump_statementContext.class,0);
		}
		public Simple_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_statement; }
	}

	public final Simple_statementContext simple_statement() throws RecognitionException {
		Simple_statementContext _localctx = new Simple_statementContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_simple_statement);
		try {
			setState(648);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(638);
				declaration_statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(639);
				expression_statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(640);
				empty_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(641);
				selection_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(642);
				switch_statement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(643);
				case_label();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(644);
				for_statement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(645);
				while_statement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(646);
				do_while_statement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(647);
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

	public static class Compound_statementContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GLSL.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSL.RBRACE, 0); }
		public List<Statement_listContext> statement_list() {
			return getRuleContexts(Statement_listContext.class);
		}
		public Statement_listContext statement_list(int i) {
			return getRuleContext(Statement_listContext.class,i);
		}
		public Compound_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement; }
	}

	public final Compound_statementContext compound_statement() throws RecognitionException {
		Compound_statementContext _localctx = new Compound_statementContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_compound_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
			match(LBRACE);
			setState(654);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << PRECISION) | (1L << INTCONSTANT) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (STRUCT - 79)) | (1L << (IF - 79)) | (1L << (SWITCH - 79)) | (1L << (CASE - 79)) | (1L << (DEFAULT - 79)) | (1L << (WHILE - 79)) | (1L << (DO - 79)) | (1L << (FOR - 79)) | (1L << (CONTINUE - 79)) | (1L << (BREAK - 79)) | (1L << (RETURN - 79)) | (1L << (DISCARD - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 207)) & ~0x3f) == 0 && ((1L << (_la - 207)) & ((1L << (LBRACE - 207)) | (1L << (SEMICOLON - 207)) | (1L << (PLUS_OP - 207)) | (1L << (MINUS_OP - 207)) | (1L << (NOT_OP - 207)) | (1L << (BNEG_OP - 207)) | (1L << (IDENTIFIER - 207)))) != 0)) {
				{
				{
				setState(651);
				statement_list();
				}
				}
				setState(656);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(657);
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
		enterRule(_localctx, 130, RULE_statement_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(660); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(659);
					statement();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(662); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
		enterRule(_localctx, 132, RULE_declaration_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(664);
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

	public static class Expression_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_expression_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(666);
			expression();
			setState(667);
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
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Empty_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_empty_statement; }
	}

	public final Empty_statementContext empty_statement() throws RecognitionException {
		Empty_statementContext _localctx = new Empty_statementContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_empty_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(669);
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

	public static class Selection_statementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GLSL.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GLSL.ELSE, 0); }
		public Selection_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection_statement; }
	}

	public final Selection_statementContext selection_statement() throws RecognitionException {
		Selection_statementContext _localctx = new Selection_statementContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_selection_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(671);
			match(IF);
			setState(672);
			match(LPAREN);
			setState(673);
			expression();
			setState(674);
			match(RPAREN);
			setState(675);
			statement();
			setState(678);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(676);
				match(ELSE);
				setState(677);
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
		public Fully_specified_typeContext fully_specified_type() {
			return getRuleContext(Fully_specified_typeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GLSL.IDENTIFIER, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(GLSL.ASSIGN_OP, 0); }
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
		enterRule(_localctx, 140, RULE_condition);
		try {
			setState(686);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(680);
				expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(681);
				fully_specified_type();
				setState(682);
				match(IDENTIFIER);
				setState(683);
				match(ASSIGN_OP);
				setState(684);
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
		public TerminalNode SWITCH() { return getToken(GLSL.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GLSL.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GLSL.RBRACE, 0); }
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Switch_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_statement; }
	}

	public final Switch_statementContext switch_statement() throws RecognitionException {
		Switch_statementContext _localctx = new Switch_statementContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_switch_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(688);
			match(SWITCH);
			setState(689);
			match(LPAREN);
			setState(690);
			expression();
			setState(691);
			match(RPAREN);
			setState(692);
			match(LBRACE);
			setState(694);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << PRECISION) | (1L << INTCONSTANT) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (STRUCT - 79)) | (1L << (IF - 79)) | (1L << (SWITCH - 79)) | (1L << (CASE - 79)) | (1L << (DEFAULT - 79)) | (1L << (WHILE - 79)) | (1L << (DO - 79)) | (1L << (FOR - 79)) | (1L << (CONTINUE - 79)) | (1L << (BREAK - 79)) | (1L << (RETURN - 79)) | (1L << (DISCARD - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 207)) & ~0x3f) == 0 && ((1L << (_la - 207)) & ((1L << (LBRACE - 207)) | (1L << (SEMICOLON - 207)) | (1L << (PLUS_OP - 207)) | (1L << (MINUS_OP - 207)) | (1L << (NOT_OP - 207)) | (1L << (BNEG_OP - 207)) | (1L << (IDENTIFIER - 207)))) != 0)) {
				{
				setState(693);
				statement_list();
				}
			}

			setState(696);
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

	public static class Case_labelContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(GLSL.COLON, 0); }
		public TerminalNode CASE() { return getToken(GLSL.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(GLSL.DEFAULT, 0); }
		public Case_labelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_label; }
	}

	public final Case_labelContext case_label() throws RecognitionException {
		Case_labelContext _localctx = new Case_labelContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_case_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(701);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CASE:
				{
				setState(698);
				match(CASE);
				setState(699);
				expression();
				}
				break;
			case DEFAULT:
				{
				setState(700);
				match(DEFAULT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(703);
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

	public static class While_statementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GLSL.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
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
		enterRule(_localctx, 146, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(705);
			match(WHILE);
			setState(706);
			match(LPAREN);
			setState(707);
			condition();
			setState(708);
			match(RPAREN);
			setState(709);
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

	public static class Do_while_statementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(GLSL.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(GLSL.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public Do_while_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_while_statement; }
	}

	public final Do_while_statementContext do_while_statement() throws RecognitionException {
		Do_while_statementContext _localctx = new Do_while_statementContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_do_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(711);
			match(DO);
			setState(712);
			statement();
			setState(713);
			match(WHILE);
			setState(714);
			match(LPAREN);
			setState(715);
			expression();
			setState(716);
			match(RPAREN);
			setState(717);
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
		public TerminalNode FOR() { return getToken(GLSL.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GLSL.LPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public TerminalNode RPAREN() { return getToken(GLSL.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public Empty_statementContext empty_statement() {
			return getRuleContext(Empty_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Declaration_statementContext declaration_statement() {
			return getRuleContext(Declaration_statementContext.class,0);
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
		enterRule(_localctx, 150, RULE_for_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(719);
			match(FOR);
			setState(720);
			match(LPAREN);
			setState(724);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				{
				setState(721);
				empty_statement();
				}
				break;
			case 2:
				{
				setState(722);
				expression_statement();
				}
				break;
			case 3:
				{
				setState(723);
				declaration_statement();
				}
				break;
			}
			setState(727);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIFORM) | (1L << BUFFER) | (1L << IN) | (1L << OUT) | (1L << INOUT) | (1L << HIGHP) | (1L << MEDIUMP) | (1L << LOWP) | (1L << INTCONSTANT) | (1L << CONST) | (1L << PRECISE) | (1L << INVARIANT) | (1L << SMOOTH) | (1L << FLAT) | (1L << NOPERSPECTIVE) | (1L << CENTROID) | (1L << SAMPLE) | (1L << PATCH) | (1L << COHERENT) | (1L << VOLATILE) | (1L << RESTRICT) | (1L << READONLY) | (1L << WRITEONLY) | (1L << SHARED) | (1L << SUBROUTINE) | (1L << LAYOUT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (STRUCT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)) | (1L << (IDENTIFIER - 214)))) != 0)) {
				{
				setState(726);
				condition();
				}
			}

			setState(729);
			match(SEMICOLON);
			setState(731);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTCONSTANT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)) | (1L << (IDENTIFIER - 214)))) != 0)) {
				{
				setState(730);
				expression();
				}
			}

			setState(733);
			match(RPAREN);
			setState(734);
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

	public static class Jump_statementContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GLSL.SEMICOLON, 0); }
		public TerminalNode CONTINUE() { return getToken(GLSL.CONTINUE, 0); }
		public TerminalNode BREAK() { return getToken(GLSL.BREAK, 0); }
		public TerminalNode RETURN() { return getToken(GLSL.RETURN, 0); }
		public TerminalNode DISCARD() { return getToken(GLSL.DISCARD, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Jump_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump_statement; }
	}

	public final Jump_statementContext jump_statement() throws RecognitionException {
		Jump_statementContext _localctx = new Jump_statementContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_jump_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONTINUE:
				{
				setState(736);
				match(CONTINUE);
				}
				break;
			case BREAK:
				{
				setState(737);
				match(BREAK);
				}
				break;
			case RETURN:
				{
				setState(738);
				match(RETURN);
				setState(740);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTCONSTANT) | (1L << UINTCONSTANT) | (1L << FLOATCONSTANT) | (1L << BOOLCONSTANT) | (1L << DOUBLECONSTANT) | (1L << INC_OP) | (1L << DEC_OP) | (1L << VOID))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (FLOAT - 79)) | (1L << (DOUBLE - 79)) | (1L << (INT - 79)) | (1L << (UINT - 79)) | (1L << (BOOL - 79)) | (1L << (SAMPLERCUBE - 79)) | (1L << (SAMPLERCUBESHADOW - 79)) | (1L << (SAMPLERBUFFER - 79)) | (1L << (SAMPLERCUBEARRAY - 79)) | (1L << (SAMPLERCUBEARRAYSHADOW - 79)) | (1L << (ISAMPLERCUBE - 79)) | (1L << (ISAMPLERBUFFER - 79)) | (1L << (ISAMPLERCUBEARRAY - 79)) | (1L << (USAMPLERCUBE - 79)) | (1L << (USAMPLERBUFFER - 79)) | (1L << (USAMPLERCUBEARRAY - 79)) | (1L << (IMAGECUBE - 79)) | (1L << (IMAGEBUFFER - 79)) | (1L << (IMAGECUBEARRAY - 79)) | (1L << (IIMAGECUBE - 79)) | (1L << (IIMAGEBUFFER - 79)) | (1L << (IIMAGECUBEARRAY - 79)) | (1L << (UIMAGECUBE - 79)) | (1L << (UIMAGEBUFFER - 79)) | (1L << (UIMAGECUBEARRAY - 79)) | (1L << (ATOMIC_UINT - 79)) | (1L << (VEC2 - 79)) | (1L << (VEC3 - 79)) | (1L << (VEC4 - 79)) | (1L << (DVEC2 - 79)) | (1L << (DVEC3 - 79)) | (1L << (DVEC4 - 79)) | (1L << (BVEC2 - 79)) | (1L << (BVEC3 - 79)) | (1L << (BVEC4 - 79)) | (1L << (IVEC2 - 79)) | (1L << (IVEC3 - 79)) | (1L << (IVEC4 - 79)) | (1L << (UVEC2 - 79)) | (1L << (UVEC3 - 79)) | (1L << (UVEC4 - 79)) | (1L << (MAT2X2 - 79)) | (1L << (MAT2X3 - 79)) | (1L << (MAT2X4 - 79)) | (1L << (MAT3X2 - 79)) | (1L << (MAT3X3 - 79)) | (1L << (MAT3X4 - 79)) | (1L << (MAT4X2 - 79)) | (1L << (MAT4X3 - 79)) | (1L << (MAT4X4 - 79)) | (1L << (DMAT2X2 - 79)))) != 0) || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (DMAT2X3 - 143)) | (1L << (DMAT2X4 - 143)) | (1L << (DMAT3X2 - 143)) | (1L << (DMAT3X3 - 143)) | (1L << (DMAT3X4 - 143)) | (1L << (DMAT4X2 - 143)) | (1L << (DMAT4X3 - 143)) | (1L << (DMAT4X4 - 143)) | (1L << (IMAGE1D - 143)) | (1L << (IMAGE2D - 143)) | (1L << (IMAGE3D - 143)) | (1L << (UIMAGE1D - 143)) | (1L << (UIMAGE2D - 143)) | (1L << (UIMAGE3D - 143)) | (1L << (IIMAGE1D - 143)) | (1L << (IIMAGE2D - 143)) | (1L << (IIMAGE3D - 143)) | (1L << (SAMPLER1D - 143)) | (1L << (SAMPLER2D - 143)) | (1L << (SAMPLER3D - 143)) | (1L << (SAMPLER2DRECT - 143)) | (1L << (SAMPLER1DSHADOW - 143)) | (1L << (SAMPLER2DSHADOW - 143)) | (1L << (SAMPLER2DRECTSHADOW - 143)) | (1L << (SAMPLER1DARRAY - 143)) | (1L << (SAMPLER2DARRAY - 143)) | (1L << (SAMPLER1DARRAYSHADOW - 143)) | (1L << (SAMPLER2DARRAYSHADOW - 143)) | (1L << (ISAMPLER1D - 143)) | (1L << (ISAMPLER2D - 143)) | (1L << (ISAMPLER2DRECT - 143)) | (1L << (ISAMPLER3D - 143)) | (1L << (ISAMPLER1DARRAY - 143)) | (1L << (ISAMPLER2DARRAY - 143)) | (1L << (USAMPLER1D - 143)) | (1L << (USAMPLER2D - 143)) | (1L << (USAMPLER2DRECT - 143)) | (1L << (USAMPLER3D - 143)) | (1L << (USAMPLER1DARRAY - 143)) | (1L << (USAMPLER2DARRAY - 143)) | (1L << (SAMPLER2DMS - 143)) | (1L << (ISAMPLER2DMS - 143)) | (1L << (USAMPLER2DMS - 143)) | (1L << (SAMPLER2DMSARRAY - 143)) | (1L << (ISAMPLER2DMSARRAY - 143)) | (1L << (USAMPLER2DMSARRAY - 143)) | (1L << (IMAGE2DRECT - 143)) | (1L << (IMAGE1DARRAY - 143)) | (1L << (IMAGE2DARRAY - 143)) | (1L << (IMAGE2DMS - 143)) | (1L << (IMAGE2DMSARRAY - 143)) | (1L << (IIMAGE2DRECT - 143)) | (1L << (IIMAGE1DARRAY - 143)) | (1L << (IIMAGE2DARRAY - 143)) | (1L << (IIMAGE2DMS - 143)) | (1L << (IIMAGE2DMSARRAY - 143)) | (1L << (UIMAGE2DRECT - 143)) | (1L << (UIMAGE1DARRAY - 143)) | (1L << (UIMAGE2DARRAY - 143)) | (1L << (UIMAGE2DMS - 143)) | (1L << (UIMAGE2DMSARRAY - 143)) | (1L << (LPAREN - 143)))) != 0) || ((((_la - 214)) & ~0x3f) == 0 && ((1L << (_la - 214)) & ((1L << (PLUS_OP - 214)) | (1L << (MINUS_OP - 214)) | (1L << (NOT_OP - 214)) | (1L << (BNEG_OP - 214)) | (1L << (IDENTIFIER - 214)))) != 0)) {
					{
					setState(739);
					expression();
					}
				}

				}
				break;
			case DISCARD:
				{
				setState(742);
				match(DISCARD);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(745);
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
		case 12:
			return postfix_expression_sempred((Postfix_expressionContext)_localctx, predIndex);
		case 53:
			return array_specifier_sempred((Array_specifierContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfix_expression_sempred(Postfix_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean array_specifier_sempred(Array_specifierContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u00e8\u02ee\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\5\3\u00a8\n\3\3\4\6\4\u00ab\n\4\r\4\16\4\u00ac\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\5\5\u00b5\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00c1"+
		"\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00e0\n\r\3"+
		"\16\3\16\3\16\5\16\u00e5\n\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\5\16\u00f0\n\16\7\16\u00f2\n\16\f\16\16\16\u00f5\13\16\3\17\3\17"+
		"\5\17\u00f9\n\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u0101\n\17\3\20\3"+
		"\20\3\20\7\20\u0106\n\20\f\20\16\20\u0109\13\20\3\21\3\21\5\21\u010d\n"+
		"\21\3\22\3\22\3\22\3\22\5\22\u0113\n\22\3\23\3\23\3\24\3\24\3\24\7\24"+
		"\u011a\n\24\f\24\16\24\u011d\13\24\3\25\3\25\3\25\7\25\u0122\n\25\f\25"+
		"\16\25\u0125\13\25\3\26\3\26\3\26\7\26\u012a\n\26\f\26\16\26\u012d\13"+
		"\26\3\27\3\27\3\27\7\27\u0132\n\27\f\27\16\27\u0135\13\27\3\30\3\30\3"+
		"\30\7\30\u013a\n\30\f\30\16\30\u013d\13\30\3\31\3\31\3\31\7\31\u0142\n"+
		"\31\f\31\16\31\u0145\13\31\3\32\3\32\3\32\7\32\u014a\n\32\f\32\16\32\u014d"+
		"\13\32\3\33\3\33\3\33\7\33\u0152\n\33\f\33\16\33\u0155\13\33\3\34\3\34"+
		"\3\34\7\34\u015a\n\34\f\34\16\34\u015d\13\34\3\35\3\35\3\35\7\35\u0162"+
		"\n\35\f\35\16\35\u0165\13\35\3\36\3\36\3\36\7\36\u016a\n\36\f\36\16\36"+
		"\u016d\13\36\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u0175\n\37\f\37\16\37"+
		"\u0178\13\37\3 \3 \3 \3 \3 \5 \u017f\n \3!\3!\3\"\3\"\3\"\7\"\u0186\n"+
		"\"\f\"\16\"\u0189\13\"\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3$\3$\3$\5$\u019f\n$\5$\u01a1\n$\3$\3$\3$\3$\3$\3$\7$\u01a9\n$\f"+
		"$\16$\u01ac\13$\5$\u01ae\n$\3$\3$\5$\u01b2\n$\3%\3%\3%\3%\3%\3&\3&\3&"+
		"\7&\u01bc\n&\f&\16&\u01bf\13&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\5(\u01cb"+
		"\n(\3)\5)\u01ce\n)\3)\3)\5)\u01d2\n)\3*\3*\5*\u01d6\n*\3*\3*\7*\u01da"+
		"\n*\f*\16*\u01dd\13*\3+\3+\5+\u01e1\n+\3+\3+\5+\u01e5\n+\3,\5,\u01e8\n"+
		",\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5"+
		"-\u0200\n-\5-\u0202\n-\3.\3.\3.\3.\3.\7.\u0209\n.\f.\16.\u020c\13.\3."+
		"\3.\3/\3/\3/\5/\u0213\n/\3/\5/\u0216\n/\3\60\3\60\3\61\3\61\3\62\3\62"+
		"\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\6\64\u0226\n\64\r\64\16\64\u0227"+
		"\3\65\3\65\3\65\7\65\u022d\n\65\f\65\16\65\u0230\13\65\3\66\3\66\5\66"+
		"\u0234\n\66\3\67\3\67\3\67\5\67\u0239\n\67\3\67\3\67\3\67\3\67\3\67\5"+
		"\67\u0240\n\67\3\67\7\67\u0243\n\67\f\67\16\67\u0246\13\67\38\38\38\5"+
		"8\u024b\n8\39\39\3:\3:\5:\u0251\n:\3:\3:\3:\3:\3;\6;\u0258\n;\r;\16;\u0259"+
		"\3<\3<\3<\3<\3=\3=\3=\7=\u0263\n=\f=\16=\u0266\13=\3>\3>\5>\u026a\n>\3"+
		"?\3?\3?\3?\3?\7?\u0271\n?\f?\16?\u0274\13?\3?\5?\u0277\n?\3?\3?\5?\u027b"+
		"\n?\3@\3@\5@\u027f\n@\3A\3A\3A\3A\3A\3A\3A\3A\3A\3A\5A\u028b\nA\3B\3B"+
		"\7B\u028f\nB\fB\16B\u0292\13B\3B\3B\3C\6C\u0297\nC\rC\16C\u0298\3D\3D"+
		"\3E\3E\3E\3F\3F\3G\3G\3G\3G\3G\3G\3G\5G\u02a9\nG\3H\3H\3H\3H\3H\3H\5H"+
		"\u02b1\nH\3I\3I\3I\3I\3I\3I\5I\u02b9\nI\3I\3I\3J\3J\3J\5J\u02c0\nJ\3J"+
		"\3J\3K\3K\3K\3K\3K\3K\3L\3L\3L\3L\3L\3L\3L\3L\3M\3M\3M\3M\3M\5M\u02d7"+
		"\nM\3M\5M\u02da\nM\3M\3M\5M\u02de\nM\3M\3M\3M\3N\3N\3N\3N\5N\u02e7\nN"+
		"\3N\5N\u02ea\nN\3N\3N\3N\2\4\32lO\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082"+
		"\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a"+
		"\2\16\3\2\25\30\3\2\32\35\4\2;<\u00d8\u00db\3\2\u00dc\u00de\3\2\u00d8"+
		"\u00d9\3\2>?\4\2@A\u00df\u00e0\3\2BC\4\2GP\u00e5\u00e5\3\2\37!\3\2\')"+
		"\6\2==Qjx\u00a5\u00a7\u00ce\2\u031a\2\u009c\3\2\2\2\4\u00a7\3\2\2\2\6"+
		"\u00aa\3\2\2\2\b\u00b4\3\2\2\2\n\u00c0\3\2\2\2\f\u00c2\3\2\2\2\16\u00c8"+
		"\3\2\2\2\20\u00ca\3\2\2\2\22\u00ce\3\2\2\2\24\u00d0\3\2\2\2\26\u00d3\3"+
		"\2\2\2\30\u00df\3\2\2\2\32\u00e4\3\2\2\2\34\u00f6\3\2\2\2\36\u0102\3\2"+
		"\2\2 \u010c\3\2\2\2\"\u0112\3\2\2\2$\u0114\3\2\2\2&\u0116\3\2\2\2(\u011e"+
		"\3\2\2\2*\u0126\3\2\2\2,\u012e\3\2\2\2.\u0136\3\2\2\2\60\u013e\3\2\2\2"+
		"\62\u0146\3\2\2\2\64\u014e\3\2\2\2\66\u0156\3\2\2\28\u015e\3\2\2\2:\u0166"+
		"\3\2\2\2<\u016e\3\2\2\2>\u017e\3\2\2\2@\u0180\3\2\2\2B\u0182\3\2\2\2D"+
		"\u018a\3\2\2\2F\u01b1\3\2\2\2H\u01b3\3\2\2\2J\u01b8\3\2\2\2L\u01c0\3\2"+
		"\2\2N\u01ca\3\2\2\2P\u01d1\3\2\2\2R\u01d3\3\2\2\2T\u01de\3\2\2\2V\u01e7"+
		"\3\2\2\2X\u0201\3\2\2\2Z\u0203\3\2\2\2\\\u0215\3\2\2\2^\u0217\3\2\2\2"+
		"`\u0219\3\2\2\2b\u021b\3\2\2\2d\u021d\3\2\2\2f\u0225\3\2\2\2h\u0229\3"+
		"\2\2\2j\u0231\3\2\2\2l\u0235\3\2\2\2n\u024a\3\2\2\2p\u024c\3\2\2\2r\u024e"+
		"\3\2\2\2t\u0257\3\2\2\2v\u025b\3\2\2\2x\u025f\3\2\2\2z\u0267\3\2\2\2|"+
		"\u027a\3\2\2\2~\u027e\3\2\2\2\u0080\u028a\3\2\2\2\u0082\u028c\3\2\2\2"+
		"\u0084\u0296\3\2\2\2\u0086\u029a\3\2\2\2\u0088\u029c\3\2\2\2\u008a\u029f"+
		"\3\2\2\2\u008c\u02a1\3\2\2\2\u008e\u02b0\3\2\2\2\u0090\u02b2\3\2\2\2\u0092"+
		"\u02bf\3\2\2\2\u0094\u02c3\3\2\2\2\u0096\u02c9\3\2\2\2\u0098\u02d1\3\2"+
		"\2\2\u009a\u02e9\3\2\2\2\u009c\u009d\5\4\3\2\u009d\u009e\5\6\4\2\u009e"+
		"\3\3\2\2\2\u009f\u00a8\3\2\2\2\u00a0\u00a1\7\t\2\2\u00a1\u00a2\7#\2\2"+
		"\u00a2\u00a8\7\n\2\2\u00a3\u00a4\7\t\2\2\u00a4\u00a5\7#\2\2\u00a5\u00a6"+
		"\7\u00e6\2\2\u00a6\u00a8\7\n\2\2\u00a7\u009f\3\2\2\2\u00a7\u00a0\3\2\2"+
		"\2\u00a7\u00a3\3\2\2\2\u00a8\5\3\2\2\2\u00a9\u00ab\5\b\5\2\u00aa\u00a9"+
		"\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad"+
		"\7\3\2\2\2\u00ae\u00b5\5\24\13\2\u00af\u00b5\5F$\2\u00b0\u00b5\5\n\6\2"+
		"\u00b1\u00b5\5\f\7\2\u00b2\u00b5\5\20\t\2\u00b3\u00b5\7\u00d3\2\2\u00b4"+
		"\u00ae\3\2\2\2\u00b4\u00af\3\2\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b1\3\2"+
		"\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3\3\2\2\2\u00b5\t\3\2\2\2\u00b6\u00b7"+
		"\7\3\2\2\u00b7\u00c1\7\n\2\2\u00b8\u00b9\7\4\2\2\u00b9\u00c1\7\n\2\2\u00ba"+
		"\u00bb\7\5\2\2\u00bb\u00c1\7\n\2\2\u00bc\u00bd\7\6\2\2\u00bd\u00c1\7\n"+
		"\2\2\u00be\u00bf\7\7\2\2\u00bf\u00c1\7\n\2\2\u00c0\u00b6\3\2\2\2\u00c0"+
		"\u00b8\3\2\2\2\u00c0\u00ba\3\2\2\2\u00c0\u00bc\3\2\2\2\u00c0\u00be\3\2"+
		"\2\2\u00c1\13\3\2\2\2\u00c2\u00c3\7\b\2\2\u00c3\u00c4\7\u00e6\2\2\u00c4"+
		"\u00c5\7\31\2\2\u00c5\u00c6\5\16\b\2\u00c6\u00c7\7\n\2\2\u00c7\r\3\2\2"+
		"\2\u00c8\u00c9\t\2\2\2\u00c9\17\3\2\2\2\u00ca\u00cb\5Z.\2\u00cb\u00cc"+
		"\5\22\n\2\u00cc\u00cd\7\u00d3\2\2\u00cd\21\3\2\2\2\u00ce\u00cf\t\3\2\2"+
		"\u00cf\23\3\2\2\2\u00d0\u00d1\5H%\2\u00d1\u00d2\5\u0082B\2\u00d2\25\3"+
		"\2\2\2\u00d3\u00d4\7\u00e6\2\2\u00d4\27\3\2\2\2\u00d5\u00e0\5\26\f\2\u00d6"+
		"\u00e0\7#\2\2\u00d7\u00e0\7\67\2\2\u00d8\u00e0\78\2\2\u00d9\u00e0\79\2"+
		"\2\u00da\u00e0\7:\2\2\u00db\u00dc\7\u00cf\2\2\u00dc\u00dd\5B\"\2\u00dd"+
		"\u00de\7\u00d0\2\2\u00de\u00e0\3\2\2\2\u00df\u00d5\3\2\2\2\u00df\u00d6"+
		"\3\2\2\2\u00df\u00d7\3\2\2\2\u00df\u00d8\3\2\2\2\u00df\u00d9\3\2\2\2\u00df"+
		"\u00da\3\2\2\2\u00df\u00db\3\2\2\2\u00e0\31\3\2\2\2\u00e1\u00e2\b\16\1"+
		"\2\u00e2\u00e5\5\30\r\2\u00e3\u00e5\5\34\17\2\u00e4\u00e1\3\2\2\2\u00e4"+
		"\u00e3\3\2\2\2\u00e5\u00f3\3\2\2\2\u00e6\u00ef\f\4\2\2\u00e7\u00e8\7\u00d4"+
		"\2\2\u00e8\u00e9\5B\"\2\u00e9\u00ea\7\u00d5\2\2\u00ea\u00f0\3\2\2\2\u00eb"+
		"\u00ec\7\u00d7\2\2\u00ec\u00f0\7\u00e6\2\2\u00ed\u00f0\7;\2\2\u00ee\u00f0"+
		"\7<\2\2\u00ef\u00e7\3\2\2\2\u00ef\u00eb\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef"+
		"\u00ee\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00e6\3\2\2\2\u00f2\u00f5\3\2"+
		"\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\33\3\2\2\2\u00f5\u00f3"+
		"\3\2\2\2\u00f6\u0100\5 \21\2\u00f7\u00f9\7=\2\2\u00f8\u00f7\3\2\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\7\u00cf\2\2\u00fb\u0101"+
		"\7\u00d0\2\2\u00fc\u00fd\7\u00cf\2\2\u00fd\u00fe\5\36\20\2\u00fe\u00ff"+
		"\7\u00d0\2\2\u00ff\u0101\3\2\2\2\u0100\u00f8\3\2\2\2\u0100\u00fc\3\2\2"+
		"\2\u0101\35\3\2\2\2\u0102\u0107\5> \2\u0103\u0104\7\u00d6\2\2\u0104\u0106"+
		"\5> \2\u0105\u0103\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u0108\37\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010d\5p9\2"+
		"\u010b\u010d\5\26\f\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d!\3"+
		"\2\2\2\u010e\u0113\5\32\16\2\u010f\u0110\5$\23\2\u0110\u0111\5\"\22\2"+
		"\u0111\u0113\3\2\2\2\u0112\u010e\3\2\2\2\u0112\u010f\3\2\2\2\u0113#\3"+
		"\2\2\2\u0114\u0115\t\4\2\2\u0115%\3\2\2\2\u0116\u011b\5\"\22\2\u0117\u0118"+
		"\t\5\2\2\u0118\u011a\5\"\22\2\u0119\u0117\3\2\2\2\u011a\u011d\3\2\2\2"+
		"\u011b\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c\'\3\2\2\2\u011d\u011b\3"+
		"\2\2\2\u011e\u0123\5&\24\2\u011f\u0120\t\6\2\2\u0120\u0122\5&\24\2\u0121"+
		"\u011f\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121\3\2\2\2\u0123\u0124\3\2"+
		"\2\2\u0124)\3\2\2\2\u0125\u0123\3\2\2\2\u0126\u012b\5(\25\2\u0127\u0128"+
		"\t\7\2\2\u0128\u012a\5(\25\2\u0129\u0127\3\2\2\2\u012a\u012d\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c+\3\2\2\2\u012d\u012b\3\2\2\2"+
		"\u012e\u0133\5*\26\2\u012f\u0130\t\b\2\2\u0130\u0132\5*\26\2\u0131\u012f"+
		"\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134"+
		"-\3\2\2\2\u0135\u0133\3\2\2\2\u0136\u013b\5,\27\2\u0137\u0138\t\t\2\2"+
		"\u0138\u013a\5,\27\2\u0139\u0137\3\2\2\2\u013a\u013d\3\2\2\2\u013b\u0139"+
		"\3\2\2\2\u013b\u013c\3\2\2\2\u013c/\3\2\2\2\u013d\u013b\3\2\2\2\u013e"+
		"\u0143\5.\30\2\u013f\u0140\7\u00e1\2\2\u0140\u0142\5.\30\2\u0141\u013f"+
		"\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144"+
		"\61\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u014b\5\60\31\2\u0147\u0148\7\u00e3"+
		"\2\2\u0148\u014a\5\60\31\2\u0149\u0147\3\2\2\2\u014a\u014d\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c\63\3\2\2\2\u014d\u014b\3\2\2"+
		"\2\u014e\u0153\5\62\32\2\u014f\u0150\7\u00e2\2\2\u0150\u0152\5\62\32\2"+
		"\u0151\u014f\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154"+
		"\3\2\2\2\u0154\65\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u015b\5\64\33\2\u0157"+
		"\u0158\7D\2\2\u0158\u015a\5\64\33\2\u0159\u0157\3\2\2\2\u015a\u015d\3"+
		"\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\67\3\2\2\2\u015d"+
		"\u015b\3\2\2\2\u015e\u0163\5\66\34\2\u015f\u0160\7E\2\2\u0160\u0162\5"+
		"\66\34\2\u0161\u015f\3\2\2\2\u0162\u0165\3\2\2\2\u0163\u0161\3\2\2\2\u0163"+
		"\u0164\3\2\2\2\u01649\3\2\2\2\u0165\u0163\3\2\2\2\u0166\u016b\58\35\2"+
		"\u0167\u0168\7F\2\2\u0168\u016a\58\35\2\u0169\u0167\3\2\2\2\u016a\u016d"+
		"\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c;\3\2\2\2\u016d"+
		"\u016b\3\2\2\2\u016e\u0176\5:\36\2\u016f\u0170\7\u00e4\2\2\u0170\u0171"+
		"\5B\"\2\u0171\u0172\7\31\2\2\u0172\u0173\5> \2\u0173\u0175\3\2\2\2\u0174"+
		"\u016f\3\2\2\2\u0175\u0178\3\2\2\2\u0176\u0174\3\2\2\2\u0176\u0177\3\2"+
		"\2\2\u0177=\3\2\2\2\u0178\u0176\3\2\2\2\u0179\u017f\5<\37\2\u017a\u017b"+
		"\5\"\22\2\u017b\u017c\5@!\2\u017c\u017d\5> \2\u017d\u017f\3\2\2\2\u017e"+
		"\u0179\3\2\2\2\u017e\u017a\3\2\2\2\u017f?\3\2\2\2\u0180\u0181\t\n\2\2"+
		"\u0181A\3\2\2\2\u0182\u0187\5> \2\u0183\u0184\7\u00d6\2\2\u0184\u0186"+
		"\5> \2\u0185\u0183\3\2\2\2\u0186\u0189\3\2\2\2\u0187\u0185\3\2\2\2\u0187"+
		"\u0188\3\2\2\2\u0188C\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u018b\5<\37\2"+
		"\u018bE\3\2\2\2\u018c\u018d\5H%\2\u018d\u018e\7\u00d3\2\2\u018e\u01b2"+
		"\3\2\2\2\u018f\u0190\5R*\2\u0190\u0191\7\u00d3\2\2\u0191\u01b2\3\2\2\2"+
		"\u0192\u0193\7\"\2\2\u0193\u0194\5^\60\2\u0194\u0195\5j\66\2\u0195\u0196"+
		"\7\u00d3\2\2\u0196\u01b2\3\2\2\2\u0197\u0198\5f\64\2\u0198\u0199\7\u00e6"+
		"\2\2\u0199\u019a\7\u00d1\2\2\u019a\u019b\5t;\2\u019b\u01a0\7\u00d1\2\2"+
		"\u019c\u019e\7\u00e6\2\2\u019d\u019f\5l\67\2\u019e\u019d\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f\u01a1\3\2\2\2\u01a0\u019c\3\2\2\2\u01a0\u01a1\3\2"+
		"\2\2\u01a1\u01a2\3\2\2\2\u01a2\u01a3\7\u00d3\2\2\u01a3\u01b2\3\2\2\2\u01a4"+
		"\u01ad\5f\64\2\u01a5\u01aa\7\u00e6\2\2\u01a6\u01a7\7\u00d6\2\2\u01a7\u01a9"+
		"\7\u00e6\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa\u01a8\3\2\2"+
		"\2\u01aa\u01ab\3\2\2\2\u01ab\u01ae\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ad\u01a5"+
		"\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b0\7\u00d3\2"+
		"\2\u01b0\u01b2\3\2\2\2\u01b1\u018c\3\2\2\2\u01b1\u018f\3\2\2\2\u01b1\u0192"+
		"\3\2\2\2\u01b1\u0197\3\2\2\2\u01b1\u01a4\3\2\2\2\u01b2G\3\2\2\2\u01b3"+
		"\u01b4\5L\'\2\u01b4\u01b5\7\u00cf\2\2\u01b5\u01b6\5J&\2\u01b6\u01b7\7"+
		"\u00d0\2\2\u01b7I\3\2\2\2\u01b8\u01bd\5P)\2\u01b9\u01ba\7\u00d6\2\2\u01ba"+
		"\u01bc\5P)\2\u01bb\u01b9\3\2\2\2\u01bc\u01bf\3\2\2\2\u01bd\u01bb\3\2\2"+
		"\2\u01bd\u01be\3\2\2\2\u01beK\3\2\2\2\u01bf\u01bd\3\2\2\2\u01c0\u01c1"+
		"\5V,\2\u01c1\u01c2\5\26\f\2\u01c2M\3\2\2\2\u01c3\u01c4\5j\66\2\u01c4\u01c5"+
		"\7\u00e6\2\2\u01c5\u01cb\3\2\2\2\u01c6\u01c7\5j\66\2\u01c7\u01c8\7\u00e6"+
		"\2\2\u01c8\u01c9\5l\67\2\u01c9\u01cb\3\2\2\2\u01ca\u01c3\3\2\2\2\u01ca"+
		"\u01c6\3\2\2\2\u01cbO\3\2\2\2\u01cc\u01ce\5f\64\2\u01cd\u01cc\3\2\2\2"+
		"\u01cd\u01ce\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d2\5N(\2\u01d0\u01d2"+
		"\5V,\2\u01d1\u01cd\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2Q\3\2\2\2\u01d3\u01d5"+
		"\5V,\2\u01d4\u01d6\5T+\2\u01d5\u01d4\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6"+
		"\u01db\3\2\2\2\u01d7\u01d8\7\u00d6\2\2\u01d8\u01da\5T+\2\u01d9\u01d7\3"+
		"\2\2\2\u01da\u01dd\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc"+
		"S\3\2\2\2\u01dd\u01db\3\2\2\2\u01de\u01e0\7\u00e6\2\2\u01df\u01e1\5l\67"+
		"\2\u01e0\u01df\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e3"+
		"\7\u00e5\2\2\u01e3\u01e5\5|?\2\u01e4\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2"+
		"\u01e5U\3\2\2\2\u01e6\u01e8\5f\64\2\u01e7\u01e6\3\2\2\2\u01e7\u01e8\3"+
		"\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01ea\5j\66\2\u01eaW\3\2\2\2\u01eb\u0202"+
		"\7$\2\2\u01ec\u0202\7\34\2\2\u01ed\u0202\7\35\2\2\u01ee\u0202\7\36\2\2"+
		"\u01ef\u0202\7*\2\2\u01f0\u0202\7,\2\2\u01f1\u0202\7+\2\2\u01f2\u0202"+
		"\7\32\2\2\u01f3\u0202\7\33\2\2\u01f4\u0202\7\64\2\2\u01f5\u0202\7.\2\2"+
		"\u01f6\u0202\7/\2\2\u01f7\u0202\7\60\2\2\u01f8\u0202\7\62\2\2\u01f9\u0202"+
		"\7\63\2\2\u01fa\u01ff\7\65\2\2\u01fb\u01fc\7\u00cf\2\2\u01fc\u01fd\5h"+
		"\65\2\u01fd\u01fe\7\u00d0\2\2\u01fe\u0200\3\2\2\2\u01ff\u01fb\3\2\2\2"+
		"\u01ff\u0200\3\2\2\2\u0200\u0202\3\2\2\2\u0201\u01eb\3\2\2\2\u0201\u01ec"+
		"\3\2\2\2\u0201\u01ed\3\2\2\2\u0201\u01ee\3\2\2\2\u0201\u01ef\3\2\2\2\u0201"+
		"\u01f0\3\2\2\2\u0201\u01f1\3\2\2\2\u0201\u01f2\3\2\2\2\u0201\u01f3\3\2"+
		"\2\2\u0201\u01f4\3\2\2\2\u0201\u01f5\3\2\2\2\u0201\u01f6\3\2\2\2\u0201"+
		"\u01f7\3\2\2\2\u0201\u01f8\3\2\2\2\u0201\u01f9\3\2\2\2\u0201\u01fa\3\2"+
		"\2\2\u0202Y\3\2\2\2\u0203\u0204\7\66\2\2\u0204\u0205\7\u00cf\2\2\u0205"+
		"\u020a\5\\/\2\u0206\u0207\7\u00d6\2\2\u0207\u0209\5\\/\2\u0208\u0206\3"+
		"\2\2\2\u0209\u020c\3\2\2\2\u020a\u0208\3\2\2\2\u020a\u020b\3\2\2\2\u020b"+
		"\u020d\3\2\2\2\u020c\u020a\3\2\2\2\u020d\u020e\7\u00d0\2\2\u020e[\3\2"+
		"\2\2\u020f\u0212\7\u00e6\2\2\u0210\u0211\7\u00e5\2\2\u0211\u0213\5D#\2"+
		"\u0212\u0210\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0216\3\2\2\2\u0214\u0216"+
		"\7\64\2\2\u0215\u020f\3\2\2\2\u0215\u0214\3\2\2\2\u0216]\3\2\2\2\u0217"+
		"\u0218\t\13\2\2\u0218_\3\2\2\2\u0219\u021a\t\f\2\2\u021aa\3\2\2\2\u021b"+
		"\u021c\7&\2\2\u021cc\3\2\2\2\u021d\u021e\7%\2\2\u021ee\3\2\2\2\u021f\u0226"+
		"\5X-\2\u0220\u0226\5Z.\2\u0221\u0226\5^\60\2\u0222\u0226\5`\61\2\u0223"+
		"\u0226\5b\62\2\u0224\u0226\5d\63\2\u0225\u021f\3\2\2\2\u0225\u0220\3\2"+
		"\2\2\u0225\u0221\3\2\2\2\u0225\u0222\3\2\2\2\u0225\u0223\3\2\2\2\u0225"+
		"\u0224\3\2\2\2\u0226\u0227\3\2\2\2\u0227\u0225\3\2\2\2\u0227\u0228\3\2"+
		"\2\2\u0228g\3\2\2\2\u0229\u022e\7\u00e6\2\2\u022a\u022b\7\u00d6\2\2\u022b"+
		"\u022d\7\u00e6\2\2\u022c\u022a\3\2\2\2\u022d\u0230\3\2\2\2\u022e\u022c"+
		"\3\2\2\2\u022e\u022f\3\2\2\2\u022fi\3\2\2\2\u0230\u022e\3\2\2\2\u0231"+
		"\u0233\5n8\2\u0232\u0234\5l\67\2\u0233\u0232\3\2\2\2\u0233\u0234\3\2\2"+
		"\2\u0234k\3\2\2\2\u0235\u0236\b\67\1\2\u0236\u0238\7\u00d4\2\2\u0237\u0239"+
		"\5D#\2\u0238\u0237\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023a\3\2\2\2\u023a"+
		"\u023b\7\u00d5\2\2\u023b\u0244\3\2\2\2\u023c\u023d\f\4\2\2\u023d\u023f"+
		"\7\u00d4\2\2\u023e\u0240\5D#\2\u023f\u023e\3\2\2\2\u023f\u0240\3\2\2\2"+
		"\u0240\u0241\3\2\2\2\u0241\u0243\7\u00d5\2\2\u0242\u023c\3\2\2\2\u0243"+
		"\u0246\3\2\2\2\u0244\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245m\3\2\2\2"+
		"\u0246\u0244\3\2\2\2\u0247\u024b\5p9\2\u0248\u024b\5r:\2\u0249\u024b\7"+
		"\u00e6\2\2\u024a\u0247\3\2\2\2\u024a\u0248\3\2\2\2\u024a\u0249\3\2\2\2"+
		"\u024bo\3\2\2\2\u024c\u024d\t\r\2\2\u024dq\3\2\2\2\u024e\u0250\7k\2\2"+
		"\u024f\u0251\7\u00e6\2\2\u0250\u024f\3\2\2\2\u0250\u0251\3\2\2\2\u0251"+
		"\u0252\3\2\2\2\u0252\u0253\7\u00d1\2\2\u0253\u0254\5t;\2\u0254\u0255\7"+
		"\u00d2\2\2\u0255s\3\2\2\2\u0256\u0258\5v<\2\u0257\u0256\3\2\2\2\u0258"+
		"\u0259\3\2\2\2\u0259\u0257\3\2\2\2\u0259\u025a\3\2\2\2\u025au\3\2\2\2"+
		"\u025b\u025c\5V,\2\u025c\u025d\5x=\2\u025d\u025e\7\u00d3\2\2\u025ew\3"+
		"\2\2\2\u025f\u0264\5z>\2\u0260\u0261\7\u00d6\2\2\u0261\u0263\5z>\2\u0262"+
		"\u0260\3\2\2\2\u0263\u0266\3\2\2\2\u0264\u0262\3\2\2\2\u0264\u0265\3\2"+
		"\2\2\u0265y\3\2\2\2\u0266\u0264\3\2\2\2\u0267\u0269\7\u00e6\2\2\u0268"+
		"\u026a\5l\67\2\u0269\u0268\3\2\2\2\u0269\u026a\3\2\2\2\u026a{\3\2\2\2"+
		"\u026b\u027b\5> \2\u026c\u026d\7\u00d1\2\2\u026d\u0272\5|?\2\u026e\u026f"+
		"\7\u00d6\2\2\u026f\u0271\5|?\2\u0270\u026e\3\2\2\2\u0271\u0274\3\2\2\2"+
		"\u0272\u0270\3\2\2\2\u0272\u0273\3\2\2\2\u0273\u0276\3\2\2\2\u0274\u0272"+
		"\3\2\2\2\u0275\u0277\7\u00d6\2\2\u0276\u0275\3\2\2\2\u0276\u0277\3\2\2"+
		"\2\u0277\u0278\3\2\2\2\u0278\u0279\7\u00d2\2\2\u0279\u027b\3\2\2\2\u027a"+
		"\u026b\3\2\2\2\u027a\u026c\3\2\2\2\u027b}\3\2\2\2\u027c\u027f\5\u0082"+
		"B\2\u027d\u027f\5\u0080A\2\u027e\u027c\3\2\2\2\u027e\u027d\3\2\2\2\u027f"+
		"\177\3\2\2\2\u0280\u028b\5\u0086D\2\u0281\u028b\5\u0088E\2\u0282\u028b"+
		"\5\u008aF\2\u0283\u028b\5\u008cG\2\u0284\u028b\5\u0090I\2\u0285\u028b"+
		"\5\u0092J\2\u0286\u028b\5\u0098M\2\u0287\u028b\5\u0094K\2\u0288\u028b"+
		"\5\u0096L\2\u0289\u028b\5\u009aN\2\u028a\u0280\3\2\2\2\u028a\u0281\3\2"+
		"\2\2\u028a\u0282\3\2\2\2\u028a\u0283\3\2\2\2\u028a\u0284\3\2\2\2\u028a"+
		"\u0285\3\2\2\2\u028a\u0286\3\2\2\2\u028a\u0287\3\2\2\2\u028a\u0288\3\2"+
		"\2\2\u028a\u0289\3\2\2\2\u028b\u0081\3\2\2\2\u028c\u0290\7\u00d1\2\2\u028d"+
		"\u028f\5\u0084C\2\u028e\u028d\3\2\2\2\u028f\u0292\3\2\2\2\u0290\u028e"+
		"\3\2\2\2\u0290\u0291\3\2\2\2\u0291\u0293\3\2\2\2\u0292\u0290\3\2\2\2\u0293"+
		"\u0294\7\u00d2\2\2\u0294\u0083\3\2\2\2\u0295\u0297\5~@\2\u0296\u0295\3"+
		"\2\2\2\u0297\u0298\3\2\2\2\u0298\u0296\3\2\2\2\u0298\u0299\3\2\2\2\u0299"+
		"\u0085\3\2\2\2\u029a\u029b\5F$\2\u029b\u0087\3\2\2\2\u029c\u029d\5B\""+
		"\2\u029d\u029e\7\u00d3\2\2\u029e\u0089\3\2\2\2\u029f\u02a0\7\u00d3\2\2"+
		"\u02a0\u008b\3\2\2\2\u02a1\u02a2\7l\2\2\u02a2\u02a3\7\u00cf\2\2\u02a3"+
		"\u02a4\5B\"\2\u02a4\u02a5\7\u00d0\2\2\u02a5\u02a8\5~@\2\u02a6\u02a7\7"+
		"m\2\2\u02a7\u02a9\5~@\2\u02a8\u02a6\3\2\2\2\u02a8\u02a9\3\2\2\2\u02a9"+
		"\u008d\3\2\2\2\u02aa\u02b1\5B\"\2\u02ab\u02ac\5V,\2\u02ac\u02ad\7\u00e6"+
		"\2\2\u02ad\u02ae\7\u00e5\2\2\u02ae\u02af\5|?\2\u02af\u02b1\3\2\2\2\u02b0"+
		"\u02aa\3\2\2\2\u02b0\u02ab\3\2\2\2\u02b1\u008f\3\2\2\2\u02b2\u02b3\7n"+
		"\2\2\u02b3\u02b4\7\u00cf\2\2\u02b4\u02b5\5B\"\2\u02b5\u02b6\7\u00d0\2"+
		"\2\u02b6\u02b8\7\u00d1\2\2\u02b7\u02b9\5\u0084C\2\u02b8\u02b7\3\2\2\2"+
		"\u02b8\u02b9\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bb\7\u00d2\2\2\u02bb"+
		"\u0091\3\2\2\2\u02bc\u02bd\7o\2\2\u02bd\u02c0\5B\"\2\u02be\u02c0\7p\2"+
		"\2\u02bf\u02bc\3\2\2\2\u02bf\u02be\3\2\2\2\u02c0\u02c1\3\2\2\2\u02c1\u02c2"+
		"\7\31\2\2\u02c2\u0093\3\2\2\2\u02c3\u02c4\7q\2\2\u02c4\u02c5\7\u00cf\2"+
		"\2\u02c5\u02c6\5\u008eH\2\u02c6\u02c7\7\u00d0\2\2\u02c7\u02c8\5~@\2\u02c8"+
		"\u0095\3\2\2\2\u02c9\u02ca\7r\2\2\u02ca\u02cb\5~@\2\u02cb\u02cc\7q\2\2"+
		"\u02cc\u02cd\7\u00cf\2\2\u02cd\u02ce\5B\"\2\u02ce\u02cf\7\u00d0\2\2\u02cf"+
		"\u02d0\7\u00d3\2\2\u02d0\u0097\3\2\2\2\u02d1\u02d2\7s\2\2\u02d2\u02d6"+
		"\7\u00cf\2\2\u02d3\u02d7\5\u008aF\2\u02d4\u02d7\5\u0088E\2\u02d5\u02d7"+
		"\5\u0086D\2\u02d6\u02d3\3\2\2\2\u02d6\u02d4\3\2\2\2\u02d6\u02d5\3\2\2"+
		"\2\u02d7\u02d9\3\2\2\2\u02d8\u02da\5\u008eH\2\u02d9\u02d8\3\2\2\2\u02d9"+
		"\u02da\3\2\2\2\u02da\u02db\3\2\2\2\u02db\u02dd\7\u00d3\2\2\u02dc\u02de"+
		"\5B\"\2\u02dd\u02dc\3\2\2\2\u02dd\u02de\3\2\2\2\u02de\u02df\3\2\2\2\u02df"+
		"\u02e0\7\u00d0\2\2\u02e0\u02e1\5~@\2\u02e1\u0099\3\2\2\2\u02e2\u02ea\7"+
		"t\2\2\u02e3\u02ea\7u\2\2\u02e4\u02e6\7v\2\2\u02e5\u02e7\5B\"\2\u02e6\u02e5"+
		"\3\2\2\2\u02e6\u02e7\3\2\2\2\u02e7\u02ea\3\2\2\2\u02e8\u02ea\7w\2\2\u02e9"+
		"\u02e2\3\2\2\2\u02e9\u02e3\3\2\2\2\u02e9\u02e4\3\2\2\2\u02e9\u02e8\3\2"+
		"\2\2\u02ea\u02eb\3\2\2\2\u02eb\u02ec\7\u00d3\2\2\u02ec\u009b\3\2\2\2L"+
		"\u00a7\u00ac\u00b4\u00c0\u00df\u00e4\u00ef\u00f3\u00f8\u0100\u0107\u010c"+
		"\u0112\u011b\u0123\u012b\u0133\u013b\u0143\u014b\u0153\u015b\u0163\u016b"+
		"\u0176\u017e\u0187\u019e\u01a0\u01aa\u01ad\u01b1\u01bd\u01ca\u01cd\u01d1"+
		"\u01d5\u01db\u01e0\u01e4\u01e7\u01ff\u0201\u020a\u0212\u0215\u0225\u0227"+
		"\u022e\u0233\u0238\u023f\u0244\u024a\u0250\u0259\u0264\u0269\u0272\u0276"+
		"\u027a\u027e\u028a\u0290\u0298\u02a8\u02b0\u02b8\u02bf\u02d6\u02d9\u02dd"+
		"\u02e6\u02e9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}