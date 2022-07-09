package io.github.douira.glsl_transformer.ast;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.declaration.Declaration;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.VersionStatement.Profile;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
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
import io.github.douira.glsl_transformer.ast.node.type.initializer.Initializer;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.LayoutQualifier;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.parse_ast.Type;

/**
 * The AST builder is a visitor of the parse tree (not an AST visitor) that
 * generates AST nodes from parse tree nodes. Sometimes more or fewer nodes are
 * generated than are in the parse tree depending on how the parse tree is
 * nested and how fine-grained the AST nodes have to be. Information about the
 * relationship between a parse tree and an AST is encoded in this visitor.
 */
public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  public static ASTNode build(ParseTree ctx) {
    return Root.indexNodes(() -> doBuild(ctx));
  }

  public static ASTNode build(Root rootInstance, ParseTree ctx) {
    return Root.indexNodes(rootInstance, () -> doBuild(ctx));
  }

  public static ASTNode buildSubtreeFor(ASTNode parentTreeMember, ParseTree ctx) {
    return Root.indexNodes(parentTreeMember, () -> doBuild(ctx));
  }

  private static ASTNode doBuild(ParseTree ctx) {
    // try {
    return new ASTBuilder().visit(ctx);
    // } catch (Exception e) {
    // throw new Exception("Failed to build AST for " + ctx.getText(), e);
    // }
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration().stream().map(
        (declaration) -> (ExternalDeclaration) visitExternalDeclaration(declaration));
    return versionStatement == null
        ? new TranslationUnit(externalDeclarations)
        : new TranslationUnit(versionStatement, externalDeclarations);
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    if (ctx == null) {
      return null;
    }
    var version = Integer.parseInt(ctx.version.getText());
    return ctx.profile == null
        ? new VersionStatement(version)
        : new VersionStatement(version, Profile.fromToken(ctx.profile));
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public PragmaStatement visitPragmaStatement(PragmaStatementContext ctx) {
    var stdGL = ctx.stdGL != null;
    var type = PragmaType.fromToken(ctx.type);
    return type == PragmaType.CUSTOM
        ? new PragmaStatement(stdGL, ctx.type.getText())
        : new PragmaStatement(stdGL, type, PragmaState.fromToken(ctx.state));
  }

  @Override
  public ExtensionStatement visitExtensionStatement(ExtensionStatementContext ctx) {
    var extensionName = ctx.extensionName.getText();
    return ctx.extensionBehavior == null
        ? new ExtensionStatement(extensionName)
        : new ExtensionStatement(
            extensionName, ExtensionBehavior.fromToken(ctx.extensionBehavior));
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
        (Expression) visit(ctx.condition),
        (Expression) visit(ctx.trueAlternative),
        (Expression) visit(ctx.falseAlternative));
  }

  @Override
  public FunctionCallExpression visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
    // TODO: actual function call building
    return null;
  }

  @Override
  public GroupingExpression visitGroupingExpression(GroupingExpressionContext ctx) {
    return new GroupingExpression((Expression) visit(ctx.value));
  }

  @Override
  public MemberAccessExpression visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    return new MemberAccessExpression(
        (Expression) visit(ctx.operand),
        new Identifier(ctx.member.getText()));
  }

  @Override
  public LengthAccessExpression visitLengthAccessExpression(LengthAccessExpressionContext ctx) {
    return new LengthAccessExpression((Expression) visit(ctx.operand));
  }

  @Override
  public UnaryExpression visitPostfixExpression(PostfixExpressionContext ctx) {
    var operand = (Expression) visit(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLParser.INC_OP:
        return new IncrementPostfixExpression(operand);
      case GLSLParser.DEC_OP:
        return new DecrementPostfixExpression(operand);
      default:
        throw new IllegalArgumentException("Unknown postfix operator: " + ctx.op.getText());
    }
  }

  @Override
  public UnaryExpression visitPrefixExpression(PrefixExpressionContext ctx) {
    var operand = (Expression) visit(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLLexer.INC_OP:
        return new IncrementPrefixExpression(operand);
      case GLSLLexer.DEC_OP:
        return new DecrementPrefixExpression(operand);
      case GLSLLexer.PLUS_OP:
        return new IdentityExpression(operand);
      case GLSLLexer.MINUS_OP:
        return new NegationExpression(operand);
      case GLSLLexer.BOOL_NOT_OP:
        return new BooleanNotExpression(operand);
      case GLSLLexer.BIT_NEG_OP:
        return new BitwiseNotExpression(operand);
      default:
        throw new IllegalStateException("Unexpected prefix operator type" + ctx.op.getText());
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
      var right = (Expression) visit(sequence.right);
      expressions.add(right);

      left = sequence.left;
    } while (left instanceof SequenceExpressionContext);

    expressions.add((Expression) visit(left));
    Collections.reverse(expressions);
    return new SequenceExpression(expressions);
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
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return new LiteralExpression(
            literalType, tokenContent.equals("true"));
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
  }

  @Override
  public BinaryExpression visitAdditiveExpression(AdditiveExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.PLUS_OP:
        return new AdditionExpression(left, right);
      case GLSLLexer.MINUS_OP:
        return new SubtractionExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown additive operator: " + ctx.op.getText());
    }
  }

  @Override
  public ArrayAccessExpression visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {
    return new ArrayAccessExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BinaryExpression visitAssignmentExpression(AssignmentExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
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
  }

  @Override
  public BitwiseAndExpression visitBitwiseAndExpression(BitwiseAndExpressionContext ctx) {
    return new BitwiseAndExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BitwiseXorExpression visitBitwiseExclusiveOrExpression(BitwiseExclusiveOrExpressionContext ctx) {
    return new BitwiseXorExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BitwiseOrExpression visitBitwiseInclusiveOrExpression(BitwiseInclusiveOrExpressionContext ctx) {
    return new BitwiseOrExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BinaryExpression visitEqualityExpression(EqualityExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.EQ_OP:
        return new EqualityExpression(left, right);
      case GLSLLexer.NE_OP:
        return new InequalityExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown equality operator: " + ctx.op.getText());
    }
  }

  @Override
  public BooleanAndExpression visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
    return new BooleanAndExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BooleanXorExpression visitLogicalExclusiveOrExpression(LogicalExclusiveOrExpressionContext ctx) {
    return new BooleanXorExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BooleanOrExpression visitLogicalInclusiveOrExpression(LogicalInclusiveOrExpressionContext ctx) {
    return new BooleanOrExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public BinaryExpression visitRelationalExpression(RelationalExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
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
  }

  @Override
  public BinaryExpression visitShiftExpression(ShiftExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.LEFT_OP:
        return new LeftShiftExpression(left, right);
      case GLSLLexer.RIGHT_OP:
        return new RightShiftExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown shift operator: " + ctx.op.getText());
    }
  }

  @Override
  public BinaryExpression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
    var left = (Expression) visit(ctx.left);
    var right = (Expression) visit(ctx.right);
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
  }

  @Override
  public ASTNode visitReferenceExpression(ReferenceExpressionContext ctx) {
    return new ReferenceExpression((Identifier) visit(ctx.variableIdentifier()));
  }

  @Override
  public LayoutQualifier visitLayoutQualifier(LayoutQualifierContext ctx) {
    return (LayoutQualifier) super.visitLayoutQualifier(ctx); // TODO: LayoutQualifier
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
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
    var expression = ctx.expression();
    return expression == null
        ? new ReturnStatement()
        : new ReturnStatement((Expression) visit(expression));
  }

  @Override
  public DiscardStatement visitDiscardStatement(DiscardStatementContext ctx) {
    return new DiscardStatement();
  }

  @Override
  public DemoteStatement visitDemoteStatement(DemoteStatementContext ctx) {
    return new DemoteStatement();
  }

  @Override
  public DeclarationStatement visitDeclarationStatement(DeclarationStatementContext ctx) {
    return new DeclarationStatement((Declaration) visit(ctx.getChild(0))); // TODO: Declaration
  }

  @Override
  public ExpressionStatement visitExpressionStatement(ExpressionStatementContext ctx) {
    return new ExpressionStatement((Expression) visit(ctx.expression()));
  }

  @Override
  public EmptyStatement visitEmptyStatement(EmptyStatementContext ctx) {
    return new EmptyStatement();
  }

  @Override
  public SelectionStatement visitSelectionStatement(SelectionStatementContext ctx) {
    // unwrap the nested selection statements that are created through "else if"
    // chains
    var conditions = Stream.<Expression>builder();
    var statements = Stream.<Statement>builder();
    SelectionStatementContext nextSelection = ctx;
    do {
      conditions.add((Expression) visit(nextSelection.condition));
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
    return new SelectionStatement(conditions.build(), statements.build());
  }

  @Override
  public SwitchStatement visitSwitchStatement(SwitchStatementContext ctx) {
    return new SwitchStatement(
        (Expression) visit(ctx.condition),
        visitCompoundStatement(ctx.compoundStatement()));
  }

  @Override
  public DefaultStatement visitDefaultCaseLabel(DefaultCaseLabelContext ctx) {
    return new DefaultStatement();
  }

  @Override
  public CaseStatement visitValuedCaseLabel(ValuedCaseLabelContext ctx) {
    return new CaseStatement((Expression) visit(ctx.expression()));
  }

  @Override
  public ForLoopStatement visitForStatement(ForStatementContext ctx) {
    Expression initExpression = null;
    Declaration initDeclaration = null; // TODO: Declaration
    Expression condition = null;
    IterationConditionInitializer iterationConditionInitializer = null;
    Expression incrementer = null;

    var initExpressionStatement = ctx.expressionStatement();
    if (initExpressionStatement != null) {
      initExpression = (Expression) visit(initExpressionStatement.expression());
    } else {
      var initDeclarationStatement = ctx.declarationStatement();
      if (initDeclarationStatement != null) {
        initDeclaration = (Declaration) visit(initDeclarationStatement.declaration());
      }
    }

    if (ctx.condition != null) {
      condition = (Expression) visit(ctx.condition);
    } else if (ctx.initCondition != null) {
      iterationConditionInitializer = visitIterationCondition(ctx.initCondition);
    }

    if (ctx.incrementer != null) {
      incrementer = (Expression) visit(ctx.incrementer);
    }

    return new ForLoopStatement(
        initExpression,
        initDeclaration,
        condition,
        iterationConditionInitializer,
        incrementer,
        visitStatement(ctx.statement()));
  }

  @Override
  public WhileLoopStatement visitWhileStatement(WhileStatementContext ctx) {
    return ctx.condition != null
        ? new WhileLoopStatement(
            (Expression) visit(ctx.condition),
            visitStatement(ctx.loopBody))
        : new WhileLoopStatement(
            visitIterationCondition(ctx.initCondition),
            visitStatement(ctx.loopBody));
  }

  @Override
  public DoWhileLoopStatement visitDoWhileStatement(DoWhileStatementContext ctx) {
    return new DoWhileLoopStatement(
        visitStatement(ctx.loopBody),
        (Expression) visit(ctx.condition));
  }

  @Override
  public IterationConditionInitializer visitIterationCondition(IterationConditionContext ctx) {
    // TODO: FullySpecifiedType and Initalizer
    return new IterationConditionInitializer(
        (FullySpecifiedType) visit(ctx.fullySpecifiedType()),
        (Initializer) visit(ctx.initializer()));
  }

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    var type = node.getSymbol().getType();
    if (type == GLSLLexer.IDENTIFIER) {
      return new Identifier(node.getText());
    }
    throw new IllegalStateException("Unhandled terminal node: " + node.getText());
  }
}
