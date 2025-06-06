package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression.IntegerFormat;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective.ExtensionBehavior;
import io.github.douira.glsl_transformer.ast.node.external_declaration.LayoutDefaults.LayoutMode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.PragmaDirective.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.InterpolationQualifier.InterpolationType;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.PrecisionQualifier.PrecisionLevel;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.BuiltinFixedTypeSpecifier.BuiltinType;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.util.Type;
import io.github.douira.glsl_transformer.util.Type.NumberType;

/**
 * The AST builder is a visitor of the parse tree (not an AST visitor) that
 * generates AST nodes from parse tree nodes. Sometimes more or fewer nodes are
 * generated than are in the parse tree depending on how the parse tree is
 * nested and how fine-grained the AST nodes have to be. Information about the
 * relationship between a parse tree and an AST is encoded in this visitor.
 */
public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  private static SourceLocation lastSourceLocation;
  private static BufferedTokenStream tokenStream = null;

  public static void setTokenStream(BufferedTokenStream tokenStream) {
    ASTBuilder.tokenStream = tokenStream;
  }

  public static void unsetTokenStream() {
    ASTBuilder.tokenStream = null;
  }

  /**
   * Builds an AST from the given parse tree with the given root.
   *
   * @param rootInstance The root instance
   * @param ctx          The parse tree
   * @return The built AST
   */
  public static ASTNode build(Root rootInstance, ParseTree ctx) {
    return rootInstance.indexNodes(() -> buildInternal(ctx));
  }

  /**
   * Builds an AST of a specific type from the given parse tree with a given root.
   *
   * @param <T>          The type of the parse tree
   * @param <N>          The type of the AST node
   * @param rootInstance The root instance
   * @param ctx          The parse tree
   * @param visitMethod  The build method reference to this class
   * @return The built AST
   */
  public static <T extends ParseTree, N extends ASTNode> N build(
      Root rootInstance,
      T ctx,
      BiFunction<ASTBuilder, T, N> visitMethod) {
    return rootInstance.indexNodes(() -> buildInternal(ctx, visitMethod));
  }

  /**
   * Builds a subtree with the given root.
   *
   * @param rootInstance The root instance
   * @param ctx          The parse tree
   * @return The built AST
   */
  public static ASTNode buildSubtree(Root rootInstance, ParseTree ctx) {
    return rootInstance.indexNodes(() -> buildInternal(ctx));
  }

  /**
   * Builds a subtree of a specific type with a given root instance.
   *
   * @param <T>          The type of the parse tree
   * @param <N>          The type of the AST node
   * @param rootInstance The root instance
   * @param ctx          The parse tree
   * @param visitMethod  The build method reference to this class
   * @return The built AST
   */
  public static <T extends ParseTree, N extends ASTNode> N buildSubtree(
      Root rootInstance,
      T ctx,
      BiFunction<ASTBuilder, T, N> visitMethod) {
    return rootInstance.indexNodes(() -> buildInternal(ctx, visitMethod));
  }

  private static void setupVisit() {
    // init last source location with line zero to allow for reconstruction
    if (tokenStream != null) {
      lastSourceLocation = new SourceLocation(0);
    }
  }

  private static ASTNode buildInternal(ParseTree ctx) {
    setupVisit();
    return new ASTBuilder().visit(ctx);
  }

  private static <T extends ParseTree, N extends ASTNode> N buildInternal(
      T ctx,
      BiFunction<ASTBuilder, T, N> visitMethod) {
    setupVisit();
    return visitMethod.apply(new ASTBuilder(), ctx);
  }

  private static <N, R> R applySafe(N ctx, Function<N, R> visitMethod) {
    return ctx == null ? null : visitMethod.apply(ctx);
  }

  private static Identifier makeIdentifier(Token name) {
    if (name == null) {
      return null;
    }
    if (name.getType() != GLSLLexer.IDENTIFIER) {
      throw new IllegalStateException("Expected identifier, got: " + name.getText());
    }
    return new Identifier(name);
  }

  // ANTLR lexer grammar rule for line directives:
  // '#line' WS_frag DIGIT+ (WS_frag (DIGIT+ | PP_STRING))? (NEWLINE | WS_frag)* NEWLINE
  private static final Pattern lineDirective = Pattern.compile(
      "#line[\\t\\r\\u000C ]+(\\d+)(?:[\\t\\r\\u000C ]+(?:(\\d+)|\"([^\"]*)\"))?.*", Pattern.DOTALL);

  private static SourceLocation readLineDirective(ParserRuleContext ctx) {
    if (tokenStream == null) {
      return null;
    }

    var parsedLine = ctx.start.getLine();

    SourceLocation newSourceLocation = null;
    var tokens = tokenStream.getHiddenTokensToLeft(
        ctx.start.getTokenIndex(), GLSLLexer.PREPROCESSOR);
    if (tokens != null) {
      for (Token token : tokens) {
        if (token.getType() == GLSLLexer.NR_LINE) {
          // parse the line directive and update the current source location with it
          Matcher matcher = lineDirective.matcher(token.getText());
          if (matcher.matches()) {
            var line = Integer.parseInt(matcher.group(1));
            if (matcher.group(2) != null) {
              var sourceNumber = Integer.parseInt(matcher.group(2));
              newSourceLocation = lastSourceLocation.createFromPrevious(parsedLine, line, sourceNumber);
            } else if (matcher.group(3) != null) {
              var sourceName = matcher.group(3);
              newSourceLocation = lastSourceLocation.createFromPrevious(parsedLine, line, sourceName);
            } else {
              newSourceLocation = lastSourceLocation.createFromPrevious(parsedLine, line);
            }
            lastSourceLocation = newSourceLocation;
          }
        }
      }
    }

    // if no source location was parsed, reconstruct it by offsetting with lexed lines since the last one
    if (newSourceLocation == null) {
      if (lastSourceLocation instanceof PresentSourceLocation present) {
        newSourceLocation = present.createFromPrevious(parsedLine,
            present.line + parsedLine - present.parsedLine);
      } else {
        newSourceLocation = lastSourceLocation.createFromPrevious(parsedLine);
      }
      lastSourceLocation = newSourceLocation;
    }

    return newSourceLocation;
  }

  private void setSourceLocationSafe(ASTNode node, SourceLocation location) {
    if (node != null) {
      node.setSourceLocation(location);
    }
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration()
        .stream().map(this::visitExternalDeclaration);
    return versionStatement == null
        ? new TranslationUnit(externalDeclarations)
        : new TranslationUnit(versionStatement, externalDeclarations);
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    if (ctx == null) {
      return null;
    }
    return new VersionStatement(
        applySafe(ctx.version, Version::fromToken),
        applySafe(ctx.profile, Profile::fromToken));
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public PragmaDirective visitPragmaDirective(PragmaDirectiveContext ctx) {
    var stdGL = ctx.stdGL != null;
    var type = PragmaType.fromToken(ctx.type);
    return switch (type) {
      case PragmaType.CUSTOM -> new PragmaDirective(stdGL, ctx.type.getText());
      case PragmaType.OPTIONNV -> new PragmaDirective(stdGL, type, PragmaOption.fromToken(ctx.option), PragmaState.fromToken(ctx.state));
      default -> new PragmaDirective(stdGL, type, PragmaState.fromToken(ctx.state));
    };
  }

  @Override
  public ExtensionDirective visitExtensionDirective(ExtensionDirectiveContext ctx) {
    var extensionName = ctx.extensionName.getText();
    return new ExtensionDirective(
        extensionName, applySafe(ctx.extensionBehavior, ExtensionBehavior::fromToken));
  }

  @Override
  public CustomDirective visitCustomDirective(CustomDirectiveContext ctx) {
    return new CustomDirective(applySafe(ctx.content, Token::getText));
  }

  @Override
  public IncludeDirective visitIncludeDirective(IncludeDirectiveContext ctx) {
    return new IncludeDirective(applySafe(ctx.content, Token::getText), ctx.angleStart != null);
  }

  @Override
  public LayoutDefaults visitLayoutDefaults(LayoutDefaultsContext ctx) {
    return new LayoutDefaults(
        visitLayoutQualifier(ctx.layoutQualifier()),
        LayoutMode.fromToken(ctx.layoutMode));
  }

  @Override
  public ConditionExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
    return new ConditionExpression(
        visitExpression(ctx.condition),
        visitExpression(ctx.trueAlternative),
        visitExpression(ctx.falseAlternative));
  }

  @Override
  public FunctionCallExpression visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
    var functionIdentifier = ctx.IDENTIFIER();
    Identifier functionName = null;
    TypeSpecifier functionType = null;
    if (functionIdentifier != null) {
      functionName = visitIdentifier(functionIdentifier);
    } else {
      functionType = visitTypeSpecifier(ctx.typeSpecifier());
    }

    var parameters = ctx.parameters.stream().map(this::visitExpression);
    return functionName != null
        ? new FunctionCallExpression(functionName, parameters)
        : new FunctionCallExpression(functionType, parameters);
  }

  @Override
  public GroupingExpression visitGroupingExpression(GroupingExpressionContext ctx) {
    var expression = visitExpression(ctx.value);
    return expression instanceof GroupingExpression grouping
        ? grouping
        : new GroupingExpression(expression);
  }

  @Override
  public MemberAccessExpression visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    return new MemberAccessExpression(
        visitExpression(ctx.operand),
        new Identifier(ctx.member));
  }

  @Override
  public LengthAccessExpression visitLengthAccessExpression(LengthAccessExpressionContext ctx) {
    return new LengthAccessExpression(visitExpression(ctx.operand));
  }

  @Override
  public UnaryExpression visitPostfixExpression(PostfixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    return switch (ctx.op.getType()) {
      case GLSLParser.INC_OP -> new IncrementPostfixExpression(operand);
      case GLSLParser.DEC_OP -> new DecrementPostfixExpression(operand);
      default -> throw new IllegalArgumentException("Unknown postfix operator: " + ctx.op.getText());
    };
  }

  @Override
  public UnaryExpression visitPrefixExpression(PrefixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    return switch (ctx.op.getType()) {
      case GLSLLexer.INC_OP -> new IncrementPrefixExpression(operand);
      case GLSLLexer.DEC_OP -> new DecrementPrefixExpression(operand);
      case GLSLLexer.PLUS_OP -> new IdentityExpression(operand);
      case GLSLLexer.MINUS_OP -> new NegationExpression(operand);
      case GLSLLexer.LOGICAL_NOT_OP -> new BooleanNotExpression(operand);
      case GLSLLexer.BITWISE_NEG_OP -> new BitwiseNotExpression(operand);
      default -> throw new IllegalStateException("Unexpected prefix operator type" + ctx.op.getText());
    };
  }

  private static final Pattern intExtractor = Pattern.compile(
      "(0x|0|)(.*?)(?:us|ul|u|s|l)?$", Pattern.CASE_INSENSITIVE);
  private static final Pattern floatExtractor = Pattern.compile(
      "(.*?)(?:f|hf|lf)?$", Pattern.CASE_INSENSITIVE);

  @Override
  public LiteralExpression visitLiteralExpression(LiteralExpressionContext ctx) {
    // start and end token are the same as there is one token in this rule
    var content = ctx.getStart();

    // special case for string type
    if (content.getType() == GLSLLexer.STRING_START) {
      if (ctx.children.size() == 2) {
        return new LiteralExpression("");
      }

      if (ctx.children.size() == 3) {
        return new LiteralExpression(ctx.SL_CONTENT(0).getText());
      }

      // multi-part string
      var builder = new StringBuilder();
      for (var child : ctx.children) {
        if (child instanceof TerminalNode terminal && terminal.getSymbol().getType() == GLSLLexer.SL_CONTENT) {
          builder.append(child.getText());
        }
      }
      return new LiteralExpression(builder.toString());
    }

    var literalType = Type.ofLiteralTokenType(content.getType());
    var tokenContent = content.getText();
    var numberType = literalType.getNumberType();
    switch (numberType) {
      case BOOLEAN:
        return new LiteralExpression(tokenContent.equals("true"));
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        var intMatcher = intExtractor.matcher(tokenContent);
        if (!intMatcher.matches()) {
          throw new IllegalStateException("Unexpected integer literal: " + tokenContent);
        }
        var prefix = intMatcher.group(1);
        tokenContent = intMatcher.group(2);
        if (tokenContent.isEmpty()) {
          if (!prefix.equals("0")) {
            throw new IllegalStateException("Unexpected prefix: " + prefix);
          }
          return new LiteralExpression(literalType, 0);
        }
        int radix;
        IntegerFormat format;
        if (prefix.equals("0x")) {
          radix = 16;
          format = IntegerFormat.HEXADECIMAL;
        } else if (prefix.equals("0")) {
          radix = 8;
          format = IntegerFormat.OCTAL;
        } else {
          radix = 10;
          format = IntegerFormat.DECIMAL;
        }
        // TODO: parsing -9223372036854775808L doesn't work because the negative min
        // value is larger than the positive max value but we aren't getting the
        // negation sign in here
        long value = Long.parseUnsignedLong(tokenContent, radix);
        if (numberType == NumberType.SIGNED_INTEGER && (value < 0 || value > Long.MAX_VALUE)) {
          throw new IllegalStateException("Integer literal too large: " + tokenContent);
        }
        return new LiteralExpression(literalType, value, format);
      case FLOATING_POINT:
        var floatMatcher = floatExtractor.matcher(tokenContent);
        if (!floatMatcher.matches()) {
          throw new IllegalStateException("Unexpected floating point literal: " + tokenContent);
        }
        tokenContent = floatMatcher.group(1);
        return new LiteralExpression(literalType, Double.parseDouble(tokenContent));
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  @Override
  public BinaryExpression visitAdditiveExpression(AdditiveExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.PLUS_OP -> new AdditionExpression(left, right);
      case GLSLLexer.MINUS_OP -> new SubtractionExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown additive operator: " + ctx.op.getText());
    };
  }

  @Override
  public ArrayAccessExpression visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {
    return new ArrayAccessExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitAssignmentExpression(AssignmentExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.ASSIGN_OP -> new AssignmentExpression(left, right);
      case GLSLLexer.MUL_ASSIGN -> new MultiplicationAssignmentExpression(left, right);
      case GLSLLexer.DIV_ASSIGN -> new DivisionAssignmentExpression(left, right);
      case GLSLLexer.MOD_ASSIGN -> new ModuloAssignmentExpression(left, right);
      case GLSLLexer.ADD_ASSIGN -> new AdditionAssignmentExpression(left, right);
      case GLSLLexer.SUB_ASSIGN -> new SubtractionAssignmentExpression(left, right);
      case GLSLLexer.AND_ASSIGN -> new BitwiseAndAssignmentExpression(left, right);
      case GLSLLexer.XOR_ASSIGN -> new BitwiseXorAssignmentExpression(left, right);
      case GLSLLexer.OR_ASSIGN -> new BitwiseOrAssignmentExpression(left, right);
      case GLSLLexer.LEFT_ASSIGN -> new LeftShiftAssignmentExpression(left, right);
      case GLSLLexer.RIGHT_ASSIGN -> new RightShiftAssignmentExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown assignment operator: " + ctx.op.getText());
    };
  }

  @Override
  public BitwiseAndExpression visitBitwiseAndExpression(BitwiseAndExpressionContext ctx) {
    return new BitwiseAndExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BitwiseXorExpression visitBitwiseExclusiveOrExpression(BitwiseExclusiveOrExpressionContext ctx) {
    return new BitwiseXorExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BitwiseOrExpression visitBitwiseInclusiveOrExpression(BitwiseInclusiveOrExpressionContext ctx) {
    return new BitwiseOrExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitEqualityExpression(EqualityExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.EQ_OP -> new EqualityExpression(left, right);
      case GLSLLexer.NE_OP -> new InequalityExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown equality operator: " + ctx.op.getText());
    };
  }

  @Override
  public BooleanAndExpression visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
    return new BooleanAndExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BooleanXorExpression visitLogicalExclusiveOrExpression(LogicalExclusiveOrExpressionContext ctx) {
    return new BooleanXorExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BooleanOrExpression visitLogicalInclusiveOrExpression(LogicalInclusiveOrExpressionContext ctx) {
    return new BooleanOrExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitRelationalExpression(RelationalExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.LT_OP -> new LessThanExpression(left, right);
      case GLSLLexer.GT_OP -> new GreaterThanExpression(left, right);
      case GLSLLexer.LE_OP -> new LessThanEqualExpression(left, right);
      case GLSLLexer.GE_OP -> new GreaterThanEqualExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown relational operator: " + ctx.op.getText());
    };
  }

  @Override
  public BinaryExpression visitShiftExpression(ShiftExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.LEFT_OP -> new LeftShiftExpression(left, right);
      case GLSLLexer.RIGHT_OP -> new RightShiftExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown shift operator: " + ctx.op.getText());
    };
  }

  @Override
  public BinaryExpression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    return switch (ctx.op.getType()) {
      case GLSLLexer.TIMES_OP -> new MultiplicationExpression(left, right);
      case GLSLLexer.DIV_OP -> new DivisionExpression(left, right);
      case GLSLLexer.MOD_OP -> new ModuloExpression(left, right);
      default -> throw new IllegalArgumentException("Unknown multiplicative operator: " + ctx.op.getText());
    };
  }

  @Override
  public ReferenceExpression visitReferenceExpression(ReferenceExpressionContext ctx) {
    return new ReferenceExpression(visitIdentifier(ctx.IDENTIFIER()));
  }

  @Override
  public CompoundStatement visitCompoundStatement(CompoundStatementContext ctx) {
    return new CompoundStatement(ctx.statement().stream().map(this::visitStatement));
  }

  @Override
  public ContinueStatement visitContinueStatement(ContinueStatementContext ctx) {
    return new ContinueStatement();
  }

  @Override
  public BreakStatement visitBreakStatement(BreakStatementContext ctx) {
    return new BreakStatement();
  }

  @Override
  public ReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
    return new ReturnStatement(applySafe(ctx.expression(), this::visitExpression));
  }

  @Override
  public DiscardStatement visitDiscardStatement(DiscardStatementContext ctx) {
    return new DiscardStatement();
  }

  @Override
  public ASTNode visitIgnoreIntersectionStatement(IgnoreIntersectionStatementContext ctx) {
    return new IgnoreIntersectionStatement();
  }

  @Override
  public ASTNode visitTerminateRayStatement(TerminateRayStatementContext ctx) {
    return new TerminateRayStatement();
  }

  @Override
  public DemoteStatement visitDemoteStatement(DemoteStatementContext ctx) {
    return new DemoteStatement();
  }

  @Override
  public DeclarationStatement visitDeclarationStatement(DeclarationStatementContext ctx) {
    return new DeclarationStatement(visitDeclaration(ctx.declaration()));
  }

  @Override
  public ExpressionStatement visitExpressionStatement(ExpressionStatementContext ctx) {
    return new ExpressionStatement(visitExpression(ctx.expression()));
  }

  @Override
  public EmptyStatement visitEmptyStatement(EmptyStatementContext ctx) {
    return new EmptyStatement();
  }

  @Override
  public SelectionStatement visitSelectionStatement(SelectionStatementContext ctx) {
    return new SelectionStatement(
        visitExpression(ctx.condition),
        visitStatement(ctx.ifTrue),
        applySafe(ctx.ifFalse, this::visitStatement));
  }

  @Override
  public SwitchStatement visitSwitchStatement(SwitchStatementContext ctx) {
    var compoundStatementCtx = ctx.compoundStatement();
    var sourceLocation = readLineDirective(compoundStatementCtx);
    var node = new SwitchStatement(
        visitExpression(ctx.condition),
        visitCompoundStatement(compoundStatementCtx));
    setSourceLocationSafe(node, sourceLocation);
    return node;
  }

  @Override
  public DefaultStatement visitDefaultCaseLabel(DefaultCaseLabelContext ctx) {
    return new DefaultStatement();
  }

  @Override
  public CaseStatement visitValuedCaseLabel(ValuedCaseLabelContext ctx) {
    return new CaseStatement(visitExpression(ctx.expression()));
  }

  @Override
  public ForLoopStatement visitForStatement(ForStatementContext ctx) {
    Expression initExpression = null;
    Declaration initDeclaration = null;

    var initExpressionStatement = ctx.expressionStatement();
    if (initExpressionStatement != null) {
      initExpression = visitExpression(initExpressionStatement.expression());
    } else {
      var initDeclarationStatement = ctx.declarationStatement();
      if (initDeclarationStatement != null) {
        initDeclaration = visitDeclaration(initDeclarationStatement.declaration());
      }
    }
    return new ForLoopStatement(
        initExpression,
        initDeclaration,
        applySafe(ctx.condition, this::visitExpression),
        applySafe(ctx.initCondition, this::visitIterationCondition),
        applySafe(ctx.incrementer, this::visitExpression),
        visitStatement(ctx.statement()));
  }

  @Override
  public WhileLoopStatement visitWhileStatement(WhileStatementContext ctx) {
    return ctx.condition != null
        ? new WhileLoopStatement(
        visitExpression(ctx.condition),
        visitStatement(ctx.loopBody))
        : new WhileLoopStatement(
        visitIterationCondition(ctx.initCondition),
        visitStatement(ctx.loopBody));
  }

  @Override
  public DoWhileLoopStatement visitDoWhileStatement(DoWhileStatementContext ctx) {
    return new DoWhileLoopStatement(
        visitStatement(ctx.loopBody),
        visitExpression(ctx.condition));
  }

  @Override
  public IterationConditionInitializer visitIterationCondition(IterationConditionContext ctx) {
    return new IterationConditionInitializer(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        new Identifier(ctx.name),
        visitInitializer(ctx.initializer()));
  }

  @Override
  public ArraySpecifier visitArraySpecifier(ArraySpecifierContext ctx) {
    return new ArraySpecifier(ctx.arraySpecifierSegment().stream()
        .map(child -> applySafe(child.expression(), this::visitExpression)));
  }

  @Override
  public FunctionDefinition visitFunctionDefinition(FunctionDefinitionContext ctx) {
    var compoundStatementCtx = ctx.compoundStatement();
    var sourceLocation = readLineDirective(compoundStatementCtx);
    var node = new FunctionDefinition(
        visitFunctionPrototype(ctx.functionPrototype()),
        visitCompoundStatement(compoundStatementCtx));
    setSourceLocationSafe(node, sourceLocation);
    return node;
  }

  @Override
  public FunctionPrototype visitFunctionPrototype(FunctionPrototypeContext ctx) {
    var returnType = visitFullySpecifiedType(ctx.fullySpecifiedType());
    var name = visitIdentifier(ctx.IDENTIFIER());
    return new FunctionPrototype(returnType, name,
        applySafe(ctx.functionParameterList().parameters,
            parameters -> parameters.stream().map(this::visitParameterDeclaration)));
  }

  @Override
  public DeclarationMember visitDeclarationMember(DeclarationMemberContext ctx) {
    var arraySpecifier = ctx.arraySpecifier();
    var name = visitIdentifier(ctx.IDENTIFIER());
    var initializer = ctx.initializer();
    return new DeclarationMember(
        name,
        applySafe(arraySpecifier, this::visitArraySpecifier),
        applySafe(initializer, this::visitInitializer));
  }

  @Override
  public FullySpecifiedType visitFullySpecifiedType(FullySpecifiedTypeContext ctx) {
    return new FullySpecifiedType(
        applySafe(ctx.typeQualifier(), this::visitTypeQualifier),
        visitTypeSpecifier(ctx.typeSpecifier()));
  }

  @Override
  public FunctionParameter visitParameterDeclaration(ParameterDeclarationContext ctx) {
    return new FunctionParameter(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        makeIdentifier(ctx.parameterName),
        applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
  }

  @Override
  public FunctionDeclaration visitFunctionDeclaration(FunctionDeclarationContext ctx) {
    return new FunctionDeclaration(visitFunctionPrototype(ctx.functionPrototype()));
  }

  @Override
  public TypeAndInitDeclaration visitTypeAndInitDeclaration(TypeAndInitDeclarationContext ctx) {
    return new TypeAndInitDeclaration(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        ctx.declarationMembers.stream().map(this::visitDeclarationMember));
  }

  @Override
  public PrecisionDeclaration visitPrecisionDeclaration(PrecisionDeclarationContext ctx) {
    return new PrecisionDeclaration(
        visitPrecisionQualifier(ctx.precisionQualifier()),
        visitTypeSpecifier(ctx.typeSpecifier()));
  }

  @Override
  public InterfaceBlockDeclaration visitInterfaceBlockDeclaration(InterfaceBlockDeclarationContext ctx) {
    var typeQualifier = visitTypeQualifier(ctx.typeQualifier());
    var name = new Identifier(ctx.blockName);
    var structBody = visitStructBody(ctx.structBody());
    if (ctx.variableName != null) {
      var variableName = new Identifier(ctx.variableName);
      var arraySpecifierContext = ctx.arraySpecifier();
      if (arraySpecifierContext != null) {
        var arraySpecifier = visitArraySpecifier(arraySpecifierContext);
        return new InterfaceBlockDeclaration(
            typeQualifier, name, structBody, variableName, arraySpecifier);
      } else {
        return new InterfaceBlockDeclaration(typeQualifier, name, structBody, variableName);
      }
    }
    return new InterfaceBlockDeclaration(typeQualifier, name, structBody);
  }

  @Override
  public VariableDeclaration visitVariableDeclaration(VariableDeclarationContext ctx) {
    return new VariableDeclaration(
        visitTypeQualifier(ctx.typeQualifier()),
        ctx.variableNames.stream().map(ASTBuilder::makeIdentifier));
  }

  @Override
  public Initializer visitInitializer(InitializerContext ctx) {
    var expressionContext = ctx.finiteExpression();
    if (expressionContext != null) {
      return new ExpressionInitializer(visitExpression(expressionContext));
    }
    var initializers = ctx.initializers;
    return initializers == null
        ? new NestedInitializer()
        : new NestedInitializer(initializers.stream().map(this::visitInitializer));
  }

  @Override
  public NamedLayoutQualifierPart visitNamedLayoutQualifier(NamedLayoutQualifierContext ctx) {
    return new NamedLayoutQualifierPart(
        new Identifier(ctx.getStart()),
        applySafe(ctx.expression(), this::visitExpression));
  }

  @Override
  public SharedLayoutQualifierPart visitSharedLayoutQualifier(SharedLayoutQualifierContext ctx) {
    return new SharedLayoutQualifierPart();
  }

  public LayoutQualifierPart visitLayoutQualifierPart(LayoutQualifierIdContext ctx) {
    return (LayoutQualifierPart) visit(ctx);
  }

  @Override
  public LayoutQualifier visitLayoutQualifier(LayoutQualifierContext ctx) {
    var parts = new LinkedList<LayoutQualifierPart>();
    for (var partContext : ctx.layoutQualifiers) {
      var part = visitLayoutQualifierPart(partContext);

      // named layout qualifiers require extra processing
      if (part instanceof NamedLayoutQualifierPart named) {
        // check for sequence expression that has to be flattened
        if (named.getExpression() instanceof SequenceExpression sequence) {
          // the first expression is for the first part
          var expressions = sequence.getExpressions().iterator();
          parts.add(new NamedLayoutQualifierPart(named.getName(), expressions.next()));

          // any following assignment and reference expressions are named layout
          // qualifiers,
          // produce shared layout qualifiers if the name is "shared"
          while (expressions.hasNext()) {
            var expression = expressions.next();
            if (expression instanceof AssignmentExpression assignment) {
              var left = assignment.getLeft();
              if (left instanceof ReferenceExpression ref) {
                parts.add(new NamedLayoutQualifierPart(ref.getIdentifier(), assignment.getRight()));
              } else {
                throw new IllegalArgumentException(
                    "Unexpected left hand side in assignment expression of layout qualifier sequence: " + left);
              }
            } else if (expression instanceof ReferenceExpression reference) {
              var id = reference.getIdentifier();
              if (id.getName().equals("shared")) {
                parts.add(new SharedLayoutQualifierPart());
              } else {
                parts.add(new NamedLayoutQualifierPart(id));
              }
            } else {
              throw new IllegalArgumentException("Unexpected expression in sequence expression in layout qualifier");
            }
          }
        } else {
          parts.add(named);
        }
      } else {
        parts.add(part);
      }
    }

    return new LayoutQualifier(parts.stream());
  }

  @Override
  public PreciseQualifier visitPreciseQualifier(PreciseQualifierContext ctx) {
    return new PreciseQualifier();
  }

  @Override
  public InvariantQualifier visitInvariantQualifier(InvariantQualifierContext ctx) {
    return new InvariantQualifier();
  }

  @Override
  public InterpolationQualifier visitInterpolationQualifier(InterpolationQualifierContext ctx) {
    return new InterpolationQualifier(InterpolationType.fromToken(ctx.getStart()));
  }

  @Override
  public PrecisionQualifier visitPrecisionQualifier(PrecisionQualifierContext ctx) {
    return new PrecisionQualifier(PrecisionLevel.fromToken(ctx.getStart()));
  }

  @Override
  public ASTNode visitStorageQualifier(StorageQualifierContext ctx) {
    return ctx.typeNames.isEmpty()
        ? new StorageQualifier(StorageType.fromToken(ctx.getStart()))
        : new StorageQualifier(
        ctx.typeNames.stream().map(ASTBuilder::makeIdentifier));
  }

  @Override
  public StructBody visitStructBody(StructBodyContext ctx) {
    return new StructBody(ctx.structMember().stream().map(this::visitStructMember));
  }

  @Override
  public StructMember visitStructMember(StructMemberContext ctx) {
    return new StructMember(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        ctx.structDeclarators.stream().map(this::visitStructDeclarator));
  }

  @Override
  public StructDeclarator visitStructDeclarator(StructDeclaratorContext ctx) {
    return new StructDeclarator(
        new Identifier(ctx.getStart()),
        applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
  }

  @Override
  public TypeSpecifier visitTypeSpecifier(TypeSpecifierContext ctx) {
    var arraySpecifier = applySafe(ctx.arraySpecifier(), this::visitArraySpecifier);

    var builtinTypeFixed = ctx.builtinTypeSpecifierFixed();
    if (builtinTypeFixed != null) {
      var type = BuiltinType.fromToken(builtinTypeFixed.getStart());
      return new BuiltinFixedTypeSpecifier(type, arraySpecifier);
    }

    var builtinNumericType = ctx.builtinTypeSpecifierParseable();
    if (builtinNumericType != null) {
      var type = Type.fromToken(builtinNumericType.getStart());
      return new BuiltinNumericTypeSpecifier(type, arraySpecifier);
    }

    var structSpecifierContext = ctx.structSpecifier();
    if (structSpecifierContext != null) {
      return new StructSpecifier(
          applySafe(structSpecifierContext.IDENTIFIER(), this::visitIdentifier),
          visitStructBody(structSpecifierContext.structBody()),
          arraySpecifier);
    }

    var identifier = visitIdentifier(ctx.IDENTIFIER());
    return new TypeReference(identifier, arraySpecifier);
  }

  @Override
  public TypeQualifier visitTypeQualifier(TypeQualifierContext ctx) {
    return new TypeQualifier(
        ctx.children.stream().map(child -> (TypeQualifierPart) visit(child)));
  }

  @Override
  public Expression visitExpression(ExpressionContext ctx) {
    return ctx.items.size() == 1
        ? visitExpression(ctx.items.get(0))
        : new SequenceExpression(ctx.items.stream().map(this::visitExpression));
  }

  public Expression visitExpression(FiniteExpressionContext ctx) {
    return (Expression) visit(ctx);
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    var sourceLocation = readLineDirective(ctx);
    var node = (Statement) super.visitStatement(ctx);
    setSourceLocationSafe(node, sourceLocation);
    return node;
  }

  @Override
  public ExternalDeclaration visitExternalDeclaration(ExternalDeclarationContext ctx) {
    var sourceLocation = readLineDirective(ctx);

    // wrap in an extra layer since we can't inherit from both external declaration
    // and declaration
    var result = super.visitExternalDeclaration(ctx);
    if (result instanceof Declaration declaration) {
      var node = new DeclarationExternalDeclaration(declaration);
      setSourceLocationSafe(node, sourceLocation);
      return node;
    }
    setSourceLocationSafe(result, sourceLocation);
    return (ExternalDeclaration) result;
  }

  public Declaration visitDeclaration(DeclarationContext ctx) {
    return (Declaration) visit(ctx);
  }

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    throw new AssertionError(
        "visitTerminal should never be called instead of allowing the normal visitation to reach terminal nodes automatically. Content of this node: "
            + node.getText());
  }

  public Identifier visitIdentifier(TerminalNode identifier) {
    return makeIdentifier(identifier.getSymbol());
  }
}
