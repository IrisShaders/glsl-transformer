package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression.IntegerFormat;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionStatement.ExtensionBehavior;
import io.github.douira.glsl_transformer.ast.node.external_declaration.LayoutDefaults.LayoutMode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.PragmaStatement.*;
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

/**
 * The AST builder is a visitor of the parse tree (not an AST visitor) that
 * generates AST nodes from parse tree nodes. Sometimes more or fewer nodes are
 * generated than are in the parse tree depending on how the parse tree is
 * nested and how fine-grained the AST nodes have to be. Information about the
 * relationship between a parse tree and an AST is encoded in this visitor.
 */
public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  private static final Deque<Interval> sourceLineStack = new ArrayDeque<>();

  /**
   * Builds an AST from the given parse tree with a new root.
   * 
   * @param ctx The parse tree
   * @return The built AST
   */
  public static ASTNode build(ParseTree ctx) {
    return Root.indexNodes(() -> buildInternal(ctx));
  }

  /**
   * Builds an AST from the given parse tree with the given root.
   * 
   * @param rootInstance The root instance
   * @param ctx          The parse tree
   * @return The built AST
   */
  public static ASTNode build(Root rootInstance, ParseTree ctx) {
    return Root.indexNodes(rootInstance, () -> buildInternal(ctx));
  }

  /**
   * Builds an AST of a specific type from the given parse tree with a new root.
   * 
   * @param <TreeType>   The type of the parse tree
   * @param <ReturnType> The type of the AST node
   * @param ctx          The parse tree
   * @param visitMethod  The build method reference to this class
   * @return The built AST
   */
  public static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType build(
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return Root.indexNodes(() -> buildInternal(ctx, visitMethod));
  }

  public static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType build(
      Root rootInstance,
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return Root.indexNodes(rootInstance, () -> buildInternal(ctx, visitMethod));
  }

  /**
   * Builds a subtree that has the same root as the given AST node.
   * 
   * @param parentTreeMember The parent tree member
   * @param ctx              The parse tree
   * @return The built AST
   */
  public static ASTNode buildSubtree(ASTNode parentTreeMember, ParseTree ctx) {
    return Root.indexNodes(parentTreeMember, () -> buildInternal(ctx));
  }

  /**
   * Builds a subtree of a specific type that has the same root as the given AST
   * node.
   * 
   * @param <TreeType>       The type of the parse tree
   * @param <ReturnType>     The type of the AST node
   * @param parentTreeMember The parent tree member
   * @param ctx              The parse tree
   * @param visitMethod      The build method reference to this class
   * @return The built AST
   */
  public static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType buildSubtree(
      ASTNode parentTreeMember,
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return Root.indexNodes(parentTreeMember, () -> buildInternal(ctx, visitMethod));
  }

  private static ASTNode buildInternal(ParseTree ctx) {
    return new ASTBuilder().visit(ctx);
  }

  private static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType buildInternal(
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return visitMethod.apply(new ASTBuilder(), ctx);
  }

  private static <N, R> R applySafe(N ctx, Function<N, R> visitMethod) {
    return ctx == null ? null : visitMethod.apply(ctx);
  }

  private static void startConstruction(Token token) {
    var line = token.getLine();
    sourceLineStack.push(Interval.of(line, line));
  }

  private static void startConstruction(ParseTree tree) {
    if (tree instanceof ParserRuleContext ctx) {
      sourceLineStack.push(Interval.of(ctx.start.getLine(), ctx.stop.getLine()));
    } else if (tree instanceof TerminalNodeImpl ctx) {
      startConstruction(ctx.getSymbol());
    } else {
      throw new IllegalStateException("Can't handle unknown parse tree type " + tree.getClass());
    }
  }

  private static void endConstruction() {
    sourceLineStack.pop();
  }

  private static <R extends ASTNode> R constructSimple(
      ParseTree ctx, Supplier<R> constructor) {
    startConstruction(ctx);
    var result = constructor.get();
    endConstruction();
    return result;
  }

  public static Interval getActiveSourceLines() {
    var sourceLines = sourceLineStack.peekFirst();
    return sourceLines == null ? ASTNode.SYNTHETIC_SOURCE : sourceLines;
  }

  private static Identifier makeIdentifier(Token name) {
    if (name == null) {
      return null;
    }
    if (name.getType() != GLSLLexer.IDENTIFIER) {
      throw new IllegalStateException("Expected identifier, got: " + name.getText());
    }
    startConstruction(name);
    var result = new Identifier(name);
    endConstruction();
    return result;
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration()
        .stream().map(this::visitExternalDeclaration);
    startConstruction(ctx);
    try {
      return versionStatement == null
          ? new TranslationUnit(externalDeclarations)
          : new TranslationUnit(versionStatement, externalDeclarations);
    } finally {
      endConstruction();
    }
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    if (ctx == null) {
      return null;
    }
    startConstruction(ctx);
    try {
      return new VersionStatement(
          applySafe(ctx.version, Version::fromToken),
          applySafe(ctx.profile, Profile::fromToken));
    } finally {
      endConstruction();
    }
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return constructSimple(ctx, EmptyDeclaration::new);
  }

  @Override
  public PragmaStatement visitPragmaStatement(PragmaStatementContext ctx) {
    var stdGL = ctx.stdGL != null;
    var type = PragmaType.fromToken(ctx.type);
    startConstruction(ctx);
    try {
      return type == PragmaType.CUSTOM
          ? new PragmaStatement(stdGL, ctx.type.getText())
          : new PragmaStatement(stdGL, type, PragmaState.fromToken(ctx.state));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ExtensionStatement visitExtensionStatement(ExtensionStatementContext ctx) {
    var extensionName = ctx.extensionName.getText();
    startConstruction(ctx);
    try {
      return new ExtensionStatement(
          extensionName, applySafe(ctx.extensionBehavior, ExtensionBehavior::fromToken));
    } finally {
      endConstruction();
    }
  }

  @Override
  public CustomDirectiveStatement visitCustomDirectiveStatement(
      CustomDirectiveStatementContext ctx) {
    return new CustomDirectiveStatement(applySafe(ctx.content, Token::getText));
  }

  @Override
  public IncludeStatement visitIncludeStatement(IncludeStatementContext ctx) {
    return new IncludeStatement(applySafe(ctx.content, Token::getText), ctx.angleStart != null);
  }

  @Override
  public LayoutDefaults visitLayoutDefaults(LayoutDefaultsContext ctx) {
    startConstruction(ctx);
    try {
      return new LayoutDefaults(
          visitLayoutQualifier(ctx.layoutQualifier()),
          LayoutMode.fromToken(ctx.layoutMode));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ConditionExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new ConditionExpression(
          visitExpression(ctx.condition),
          visitExpression(ctx.trueAlternative),
          visitExpression(ctx.falseAlternative));
    } finally {
      endConstruction();
    }
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
    startConstruction(ctx);
    try {
      return functionName != null
          ? new FunctionCallExpression(functionName, parameters)
          : new FunctionCallExpression(functionType, parameters);
    } finally {
      endConstruction();
    }
  }

  @Override
  public GroupingExpression visitGroupingExpression(GroupingExpressionContext ctx) {
    var expression = visitExpression(ctx.value);
    startConstruction(ctx);
    try {
      return expression instanceof GroupingExpression grouping
          ? grouping
          : new GroupingExpression(expression);
    } finally {
      endConstruction();
    }
  }

  @Override
  public MemberAccessExpression visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new MemberAccessExpression(
          visitExpression(ctx.operand),
          new Identifier(ctx.member));
    } finally {
      endConstruction();
    }
  }

  @Override
  public LengthAccessExpression visitLengthAccessExpression(LengthAccessExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new LengthAccessExpression(visitExpression(ctx.operand));
    } finally {
      endConstruction();
    }
  }

  @Override
  public UnaryExpression visitPostfixExpression(PostfixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLParser.INC_OP:
          return new IncrementPostfixExpression(operand);
        case GLSLParser.DEC_OP:
          return new DecrementPostfixExpression(operand);
        default:
          throw new IllegalArgumentException("Unknown postfix operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public UnaryExpression visitPrefixExpression(PrefixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.INC_OP:
          return new IncrementPrefixExpression(operand);
        case GLSLLexer.DEC_OP:
          return new DecrementPrefixExpression(operand);
        case GLSLLexer.PLUS_OP:
          return new IdentityExpression(operand);
        case GLSLLexer.MINUS_OP:
          return new NegationExpression(operand);
        case GLSLLexer.LOGICAL_NOT_OP:
          return new BooleanNotExpression(operand);
        case GLSLLexer.BITWISE_NEG_OP:
          return new BitwiseNotExpression(operand);
        default:
          throw new IllegalStateException("Unexpected prefix operator type" + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public SequenceExpression visitSequenceExpression(SequenceExpressionContext ctx) {
    // SequenceExpressions in the parse tree are nested in the left operand
    ExpressionContext left = ctx;
    var expressions = new ArrayList<Expression>();

    // collect the nested sequence expressions
    do {
      var sequence = (SequenceExpressionContext) left;
      if (sequence.right instanceof SequenceExpressionContext) {
        throw new IllegalStateException("Sequence expressions should not be nested on the right operand!");
      }
      var right = visitExpression(sequence.right);
      expressions.add(right);

      left = sequence.left;
    } while (left instanceof SequenceExpressionContext);

    expressions.add(visitExpression(left));
    Collections.reverse(expressions);

    // converting to stream and back is fine
    // since the child list has to copy anyways
    startConstruction(ctx);
    try {
      return new SequenceExpression(expressions.stream());
    } finally {
      endConstruction();
    }
  }

  private static final Pattern intExtractor = Pattern.compile(
      "(.*?)(?:us|ul|u|s)?$", Pattern.CASE_INSENSITIVE);
  private static final Pattern floatExtractor = Pattern.compile(
      "(.*?)(?:f|hf|lf)?$", Pattern.CASE_INSENSITIVE);

  @Override
  public LiteralExpression visitLiteralExpression(LiteralExpressionContext ctx) {
    // start and end token are the same as there is one token in this rule
    var content = ctx.getStart();
    var literalType = Type.ofLiteralTokenType(content.getType());
    var tokenContent = content.getText();
    startConstruction(ctx);
    try {
      switch (literalType.getNumberType()) {
        case BOOLEAN:
          return new LiteralExpression(tokenContent.equals("true"));
        case SIGNED_INTEGER:
        case UNSIGNED_INTEGER:
          var intMatcher = intExtractor.matcher(tokenContent);
          intMatcher.matches();
          tokenContent = intMatcher.group(1);
          if (tokenContent.equals("0")) {
            return new LiteralExpression(
                literalType, 0);
          }
          if (tokenContent.startsWith("0x")) {
            return new LiteralExpression(
                literalType,
                Long.parseLong(tokenContent.substring(2), 16),
                IntegerFormat.HEXADECIMAL);
          } else if (tokenContent.startsWith("0")) {
            return new LiteralExpression(
                literalType,
                Long.parseLong(tokenContent.substring(1), 8),
                IntegerFormat.OCTAL);
          } else {
            return new LiteralExpression(
                literalType,
                Long.parseLong(tokenContent, 10), IntegerFormat.DECIMAL);
          }
        case FLOATING_POINT:
          var floatMatcher = floatExtractor.matcher(tokenContent);
          floatMatcher.matches();
          tokenContent = floatMatcher.group(1);
          return new LiteralExpression(
              literalType, Double.parseDouble(tokenContent));
        default:
          throw new IllegalArgumentException("Unsupported literal type: " + literalType);
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitAdditiveExpression(AdditiveExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.PLUS_OP:
          return new AdditionExpression(left, right);
        case GLSLLexer.MINUS_OP:
          return new SubtractionExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown additive operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public ArrayAccessExpression visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new ArrayAccessExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitAssignmentExpression(AssignmentExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.ASSIGN_OP:
          return new AssignmentExpression(left, right);
        case GLSLLexer.MUL_ASSIGN:
          return new MultiplicationAssignmentExpression(left, right);
        case GLSLLexer.DIV_ASSIGN:
          return new DivisionAssignmentExpression(left, right);
        case GLSLLexer.MOD_ASSIGN:
          return new ModuloAssignmentExpression(left, right);
        case GLSLLexer.ADD_ASSIGN:
          return new AdditionAssignmentExpression(left, right);
        case GLSLLexer.SUB_ASSIGN:
          return new SubtractionAssignmentExpression(left, right);
        case GLSLLexer.AND_ASSIGN:
          return new BitwiseAndAssignmentExpression(left, right);
        case GLSLLexer.XOR_ASSIGN:
          return new BitwiseXorAssignmentExpression(left, right);
        case GLSLLexer.OR_ASSIGN:
          return new BitwiseOrAssignmentExpression(left, right);
        case GLSLLexer.LEFT_ASSIGN:
          return new LeftShiftAssignmentExpression(left, right);
        case GLSLLexer.RIGHT_ASSIGN:
          return new RightShiftAssignmentExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown assignment operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public BitwiseAndExpression visitBitwiseAndExpression(BitwiseAndExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BitwiseAndExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BitwiseXorExpression visitBitwiseExclusiveOrExpression(BitwiseExclusiveOrExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BitwiseXorExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BitwiseOrExpression visitBitwiseInclusiveOrExpression(BitwiseInclusiveOrExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BitwiseOrExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitEqualityExpression(EqualityExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.EQ_OP:
          return new EqualityExpression(left, right);
        case GLSLLexer.NE_OP:
          return new InequalityExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown equality operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public BooleanAndExpression visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BooleanAndExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BooleanXorExpression visitLogicalExclusiveOrExpression(LogicalExclusiveOrExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BooleanXorExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BooleanOrExpression visitLogicalInclusiveOrExpression(LogicalInclusiveOrExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new BooleanOrExpression(
          visitExpression(ctx.left),
          visitExpression(ctx.right));
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitRelationalExpression(RelationalExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.LT_OP:
          return new LessThanExpression(left, right);
        case GLSLLexer.GT_OP:
          return new GreaterThanExpression(left, right);
        case GLSLLexer.LE_OP:
          return new LessThanEqualExpression(left, right);
        case GLSLLexer.GE_OP:
          return new GreaterThanEqualExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown relational operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitShiftExpression(ShiftExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.LEFT_OP:
          return new LeftShiftExpression(left, right);
        case GLSLLexer.RIGHT_OP:
          return new RightShiftExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown shift operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public BinaryExpression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    startConstruction(ctx);
    try {
      switch (ctx.op.getType()) {
        case GLSLLexer.TIMES_OP:
          return new MultiplicationExpression(left, right);
        case GLSLLexer.DIV_OP:
          return new DivisionExpression(left, right);
        case GLSLLexer.MOD_OP:
          return new ModuloExpression(left, right);
        default:
          throw new IllegalArgumentException("Unknown multiplicative operator: " + ctx.op.getText());
      }
    } finally {
      endConstruction();
    }
  }

  @Override
  public ReferenceExpression visitReferenceExpression(ReferenceExpressionContext ctx) {
    startConstruction(ctx);
    try {
      return new ReferenceExpression(visitIdentifier(ctx.IDENTIFIER()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public CompoundStatement visitCompoundStatement(CompoundStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new CompoundStatement(ctx.statement().stream().map(this::visitStatement));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ContinueStatement visitContinueStatement(ContinueStatementContext ctx) {
    return constructSimple(ctx, ContinueStatement::new);
  }

  @Override
  public BreakStatement visitBreakStatement(BreakStatementContext ctx) {
    return constructSimple(ctx, BreakStatement::new);
  }

  @Override
  public ReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new ReturnStatement(applySafe(ctx.expression(), this::visitExpression));
    } finally {
      endConstruction();
    }
  }

  @Override
  public DiscardStatement visitDiscardStatement(DiscardStatementContext ctx) {
    return constructSimple(ctx, DiscardStatement::new);
  }

  @Override
  public DemoteStatement visitDemoteStatement(DemoteStatementContext ctx) {
    return constructSimple(ctx, DemoteStatement::new);
  }

  @Override
  public DeclarationStatement visitDeclarationStatement(DeclarationStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new DeclarationStatement(visitDeclaration(ctx.declaration()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ExpressionStatement visitExpressionStatement(ExpressionStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new ExpressionStatement(visitExpression(ctx.expression()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public EmptyStatement visitEmptyStatement(EmptyStatementContext ctx) {
    return constructSimple(ctx, EmptyStatement::new);
  }

  @Override
  public SelectionStatement visitSelectionStatement(SelectionStatementContext ctx) {
    // unwrap the nested selection statements that are created through "else if"
    // chains
    var conditions = Stream.<Expression>builder();
    var statements = Stream.<Statement>builder();
    SelectionStatementContext nextSelection = ctx;
    do {
      conditions.add(visitExpression(nextSelection.condition));
      statements.add(visitStatement(nextSelection.ifTrue));
      var ifFalse = nextSelection.ifFalse;
      nextSelection = null;
      if (ifFalse != null) {
        var nestedSelectionStatement = ifFalse.selectionStatement();
        if (nestedSelectionStatement != null) {
          nextSelection = nestedSelectionStatement;
        } else {
          // add a regular else branch, has no control flow attribute
          // since they are only present on the whole selection statement
          conditions.add(null);
          statements.add(visitStatement(ifFalse));
        }
      }
    } while (nextSelection != null);
    startConstruction(ctx);
    try {
      return new SelectionStatement(conditions.build(), statements.build());
    } finally {
      endConstruction();
    }
  }

  @Override
  public SwitchStatement visitSwitchStatement(SwitchStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new SwitchStatement(
          visitExpression(ctx.condition),
          visitCompoundStatement(ctx.compoundStatement()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public DefaultStatement visitDefaultCaseLabel(DefaultCaseLabelContext ctx) {
    return constructSimple(ctx, DefaultStatement::new);
  }

  @Override
  public CaseStatement visitValuedCaseLabel(ValuedCaseLabelContext ctx) {
    startConstruction(ctx);
    try {
      return new CaseStatement(visitExpression(ctx.expression()));
    } finally {
      endConstruction();
    }
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
    startConstruction(ctx);
    try {
      return new ForLoopStatement(
          initExpression,
          initDeclaration,
          applySafe(ctx.condition, this::visitExpression),
          applySafe(ctx.initCondition, this::visitIterationCondition),
          applySafe(ctx.incrementer, this::visitExpression),
          visitStatement(ctx.statement()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public WhileLoopStatement visitWhileStatement(WhileStatementContext ctx) {
    startConstruction(ctx);
    try {
      return ctx.condition != null
          ? new WhileLoopStatement(
              visitExpression(ctx.condition),
              visitStatement(ctx.loopBody))
          : new WhileLoopStatement(
              visitIterationCondition(ctx.initCondition),
              visitStatement(ctx.loopBody));
    } finally {
      endConstruction();
    }
  }

  @Override
  public DoWhileLoopStatement visitDoWhileStatement(DoWhileStatementContext ctx) {
    startConstruction(ctx);
    try {
      return new DoWhileLoopStatement(
          visitStatement(ctx.loopBody),
          visitExpression(ctx.condition));
    } finally {
      endConstruction();
    }
  }

  @Override
  public IterationConditionInitializer visitIterationCondition(IterationConditionContext ctx) {
    startConstruction(ctx);
    try {
      return new IterationConditionInitializer(
          visitFullySpecifiedType(ctx.fullySpecifiedType()),
          new Identifier(ctx.name),
          visitInitializer(ctx.initializer()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ArraySpecifier visitArraySpecifier(ArraySpecifierContext ctx) {
    startConstruction(ctx);
    try {
      return new ArraySpecifier(ctx.arraySpecifierSegment().stream()
          .<Expression>map(child -> applySafe(child.expression(), this::visitExpression)));
    } finally {
      endConstruction();
    }
  }

  @Override
  public FunctionDefinition visitFunctionDefinition(FunctionDefinitionContext ctx) {
    startConstruction(ctx);
    try {
      return new FunctionDefinition(
          visitFunctionPrototype(ctx.functionPrototype()),
          visitCompoundStatement(ctx.compoundStatement()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public FunctionPrototype visitFunctionPrototype(FunctionPrototypeContext ctx) {
    var returnType = visitFullySpecifiedType(ctx.fullySpecifiedType());
    var name = visitIdentifier(ctx.IDENTIFIER());
    startConstruction(ctx);
    try {
      return new FunctionPrototype(returnType, name,
          applySafe(ctx.functionParameterList().parameters,
              parameters -> parameters.stream().map(this::visitParameterDeclaration)));
    } finally {
      endConstruction();
    }
  }

  @Override
  public DeclarationMember visitDeclarationMember(DeclarationMemberContext ctx) {
    var arraySpecifier = ctx.arraySpecifier();
    var name = visitIdentifier(ctx.IDENTIFIER());
    var initializer = ctx.initializer();
    startConstruction(ctx);
    try {
      return new DeclarationMember(
          name,
          applySafe(arraySpecifier, this::visitArraySpecifier),
          applySafe(initializer, this::visitInitializer));
    } finally {
      endConstruction();
    }
  }

  @Override
  public FullySpecifiedType visitFullySpecifiedType(FullySpecifiedTypeContext ctx) {
    startConstruction(ctx);
    try {
      return new FullySpecifiedType(
          applySafe(ctx.typeQualifier(), this::visitTypeQualifier),
          visitTypeSpecifier(ctx.typeSpecifier()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public FunctionParameter visitParameterDeclaration(ParameterDeclarationContext ctx) {
    startConstruction(ctx);
    try {
      return new FunctionParameter(
          visitFullySpecifiedType(ctx.fullySpecifiedType()),
          makeIdentifier(ctx.parameterName),
          applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
    } finally {
      endConstruction();
    }
  }

  @Override
  public FunctionDeclaration visitFunctionDeclaration(FunctionDeclarationContext ctx) {
    startConstruction(ctx);
    try {
      return new FunctionDeclaration(visitFunctionPrototype(ctx.functionPrototype()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public TypeAndInitDeclaration visitTypeAndInitDeclaration(TypeAndInitDeclarationContext ctx) {
    startConstruction(ctx);
    try {
      return new TypeAndInitDeclaration(
          visitFullySpecifiedType(ctx.fullySpecifiedType()),
          ctx.declarationMembers.stream().map(this::visitDeclarationMember));
    } finally {
      endConstruction();
    }
  }

  @Override
  public PrecisionDeclaration visitPrecisionDeclaration(PrecisionDeclarationContext ctx) {
    startConstruction(ctx);
    try {
      return new PrecisionDeclaration(
          visitPrecisionQualifier(ctx.precisionQualifier()),
          visitTypeSpecifier(ctx.typeSpecifier()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public InterfaceBlockDeclaration visitInterfaceBlockDeclaration(InterfaceBlockDeclarationContext ctx) {
    var typeQualifier = visitTypeQualifier(ctx.typeQualifier());
    var name = new Identifier(ctx.blockName);
    var structBody = visitStructBody(ctx.structBody());
    startConstruction(ctx);
    try {
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
    } finally {
      endConstruction();
    }
  }

  @Override
  public VariableDeclaration visitVariableDeclaration(VariableDeclarationContext ctx) {
    startConstruction(ctx);
    try {
      return new VariableDeclaration(
          visitTypeQualifier(ctx.typeQualifier()),
          ctx.variableNames.stream().map(ASTBuilder::makeIdentifier));
    } finally {
      endConstruction();
    }
  }

  @Override
  public Initializer visitInitializer(InitializerContext ctx) {
    startConstruction(ctx);
    try {
      var expressionContext = ctx.expression();
      if (expressionContext != null) {
        return new ExpressionInitializer(visitExpression(expressionContext));
      }
      var initializers = ctx.initializers;
      return initializers == null
          ? new NestedInitializer()
          : new NestedInitializer(initializers.stream().map(this::visitInitializer));
    } finally {
      endConstruction();
    }

  }

  @Override
  public NamedLayoutQualifierPart visitNamedLayoutQualifier(NamedLayoutQualifierContext ctx) {
    startConstruction(ctx);
    try {
      return new NamedLayoutQualifierPart(
          new Identifier(ctx.getStart()),
          applySafe(ctx.expression(), this::visitExpression));
    } finally {
      endConstruction();
    }
  }

  @Override
  public SharedLayoutQualifierPart visitSharedLayoutQualifier(SharedLayoutQualifierContext ctx) {
    return constructSimple(ctx, SharedLayoutQualifierPart::new);
  }

  public LayoutQualifierPart visitLayoutQualifierPart(LayoutQualifierIdContext ctx) {
    return (LayoutQualifierPart) visit(ctx);
  }

  @Override
  public LayoutQualifier visitLayoutQualifier(LayoutQualifierContext ctx) {
    startConstruction(ctx);
    try {
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
                  throw new IllegalArgumentException("Unexpected left hand side in assignment expression of layout qualifier sequence: " + left);
                }
              } else if (expression instanceof ReferenceExpression reference) {
                var id = reference.getIdentifier();
                if (id.equals("shared")) {
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
    } finally {
      endConstruction();
    }
  }

  @Override
  public PreciseQualifier visitPreciseQualifier(PreciseQualifierContext ctx) {
    return constructSimple(ctx, PreciseQualifier::new);
  }

  @Override
  public InvariantQualifier visitInvariantQualifier(InvariantQualifierContext ctx) {
    return constructSimple(ctx, InvariantQualifier::new);
  }

  @Override
  public InterpolationQualifier visitInterpolationQualifier(InterpolationQualifierContext ctx) {
    startConstruction(ctx);
    try {
      return new InterpolationQualifier(InterpolationType.fromToken(ctx.getStart()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public PrecisionQualifier visitPrecisionQualifier(PrecisionQualifierContext ctx) {
    startConstruction(ctx);
    try {
      return new PrecisionQualifier(PrecisionLevel.fromToken(ctx.getStart()));
    } finally {
      endConstruction();
    }
  }

  @Override
  public ASTNode visitStorageQualifier(StorageQualifierContext ctx) {
    startConstruction(ctx);
    try {
      return ctx.typeNames.isEmpty()
          ? new StorageQualifier(StorageType.fromToken(ctx.getStart()))
          : new StorageQualifier(
              ctx.typeNames.stream().map(ASTBuilder::makeIdentifier));
    } finally {
      endConstruction();
    }
  }

  @Override
  public StructBody visitStructBody(StructBodyContext ctx) {
    startConstruction(ctx);
    try {
      return new StructBody(ctx.structMember().stream().map(this::visitStructMember));
    } finally {
      endConstruction();
    }
  }

  @Override
  public StructMember visitStructMember(StructMemberContext ctx) {
    startConstruction(ctx);
    try {
      return new StructMember(
          visitFullySpecifiedType(ctx.fullySpecifiedType()),
          ctx.structDeclarators.stream().map(this::visitStructDeclarator));
    } finally {
      endConstruction();
    }
  }

  @Override
  public StructDeclarator visitStructDeclarator(StructDeclaratorContext ctx) {
    startConstruction(ctx);
    try {
      return new StructDeclarator(
          new Identifier(ctx.getStart()),
          applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
    } finally {
      endConstruction();
    }
  }

  @Override
  public TypeSpecifier visitTypeSpecifier(TypeSpecifierContext ctx) {
    startConstruction(ctx);
    try {
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
    } finally {
      endConstruction();
    }
  }

  @Override
  public TypeQualifier visitTypeQualifier(TypeQualifierContext ctx) {
    startConstruction(ctx);
    try {
      return new TypeQualifier(
          ctx.children.stream().map(child -> (TypeQualifierPart) visit(child)));
    } finally {
      endConstruction();
    }
  }

  public Expression visitExpression(ExpressionContext ctx) {
    return (Expression) visit(ctx);
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
  }

  @Override
  public ExternalDeclaration visitExternalDeclaration(ExternalDeclarationContext ctx) {
    // wrap in an extra layer since we can't inherit from both external declaration
    // and declaration
    var result = super.visitExternalDeclaration(ctx);
    if (result instanceof Declaration declaration) {
      startConstruction(ctx);
      try {
        return new DeclarationExternalDeclaration(declaration);
      } finally {
        endConstruction();
      }
    }
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
