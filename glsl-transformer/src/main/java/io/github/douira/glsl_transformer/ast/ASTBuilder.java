package io.github.douira.glsl_transformer.ast;

import java.util.*;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.SelectionStatement.Section;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;

public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  public static ASTNode build(ParseTree ctx) {
    return new ASTBuilder().visit(ctx);
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
    return ctx == null ? null : VersionStatement.from(ctx);
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public PragmaStatement visitPragmaStatement(PragmaStatementContext ctx) {
    return PragmaStatement.from(ctx);
  }

  @Override
  public ExtensionStatement visitExtensionStatement(ExtensionStatementContext ctx) {
    return ExtensionStatement.from(ctx);
  }

  @Override
  public LayoutDefaults visitLayoutDefaults(LayoutDefaultsContext ctx) {
    return LayoutDefaults.from(visitLayoutQualifier(ctx.layoutQualifier()), ctx);
  }

  @Override
  public ConditionExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
    return new ConditionExpression(
        (Expression) visit(ctx.condition),
        (Expression) visit(ctx.trueAlternative),
        (Expression) visit(ctx.falseAlternative));
  }

  @Override
  public ASTNode visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
    return new FunctionCallExpression(
        (InnerASTNode) visit(ctx.functionCall())); // TODO: FunctionCall
  }

  @Override
  public ASTNode visitGroupingExpression(GroupingExpressionContext ctx) {
    return new GroupingExpression((Expression) visit(ctx.value));
  }

  @Override
  public ASTNode visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    return new MemberAccessExpression(
        (Expression) visit(ctx.operand),
        ctx.member.getText());
  }

  @Override
  public ASTNode visitLengthAccessExpression(LengthAccessExpressionContext ctx) {
    return new LengthAccessExpression((Expression) visit(ctx.operand));
  }

  @Override
  public ASTNode visitPostfixExpression(PostfixExpressionContext ctx) {
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
  public ASTNode visitPrefixExpression(PrefixExpressionContext ctx) {
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
      var right = visit(ctx.right);
      if (right instanceof SequenceExpression) {
        throw new IllegalStateException("Sequence expressions should not be nested on the right operand!");
      }
      expressions.add((Expression) right);

      left = ctx.left;
    } while (left instanceof SequenceExpressionContext);

    expressions.add((Expression) visit(left));
    Collections.reverse(expressions);
    return new SequenceExpression(expressions);
  }

  @Override
  public LiteralExpression visitLiteralExpression(LiteralExpressionContext ctx) {
    // start and end token are the same as there is one token in this rule
    return LiteralExpression.from(ctx.getStart());
  }

  @Override
  public ASTNode visitAdditiveExpression(AdditiveExpressionContext ctx) {
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
  public ASTNode visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {
    return new ArrayAccessExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitAssignmentExpression(AssignmentExpressionContext ctx) {
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
  public ASTNode visitBitwiseAndExpression(BitwiseAndExpressionContext ctx) {
    return new BitwiseAndExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitBitwiseExclusiveOrExpression(BitwiseExclusiveOrExpressionContext ctx) {
    return new BitwiseXorExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitBitwiseInclusiveOrExpression(BitwiseInclusiveOrExpressionContext ctx) {
    return new BitwiseOrExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitEqualityExpression(EqualityExpressionContext ctx) {
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
  public ASTNode visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
    return new BooleanAndExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitLogicalExclusiveOrExpression(LogicalExclusiveOrExpressionContext ctx) {
    return new BooleanXorExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitLogicalInclusiveOrExpression(LogicalInclusiveOrExpressionContext ctx) {
    return new BooleanOrExpression(
        (Expression) visit(ctx.left),
        (Expression) visit(ctx.right));
  }

  @Override
  public ASTNode visitRelationalExpression(RelationalExpressionContext ctx) {
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
  public ASTNode visitShiftExpression(ShiftExpressionContext ctx) {
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
  public ASTNode visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
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
  public InnerASTNode visitLayoutQualifier(LayoutQualifierContext ctx) {
    return (InnerASTNode) super.visitLayoutQualifier(ctx); // TODO: LayoutQualifier
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
  }

  @Override
  public CompoundStatement visitCompoundStatement(CompoundStatementContext ctx) {
    return new CompoundStatement(ctx.statement().stream().map(
        (statement) -> (Statement) visitStatement(statement)));
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
    return new ReturnStatement((Expression) visit(ctx.expression()));
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
    return new DeclarationStatement((InnerASTNode) visit(ctx.getChild(0))); // TODO: Declaration
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
    var sections = Stream.<Section>builder();
    SelectionStatementContext nextSelection = ctx;
    do {
      sections.add(new Section(
          (Expression) visit(nextSelection.condition),
          (Statement) visit(nextSelection.ifTrue)));
      var ifFalse = nextSelection.ifFalse;
      nextSelection = null;
      if (ifFalse != null) {
        var nestedSelectionStatement = ifFalse.selectionStatement();
        if (nestedSelectionStatement != null) {
          nextSelection = nestedSelectionStatement;
        } else {
          // add a regular else branch
          sections.add(new Section((Statement) visit(ifFalse)));
        }
      }
    } while (nextSelection != null);
    return new SelectionStatement(sections.build());
  }

  @Override
  public SwitchStatement visitSwitchStatement(SwitchStatementContext ctx) {
    // TODO
    return null;
  }

  @Override
  public ForLoopStatement visitForStatement(ForStatementContext ctx) {
    // TODO
    return null;
  }

  @Override
  public WhileLoopStatement visitWhileStatement(WhileStatementContext ctx) {
    var condition = ctx.condition;
    var expression = condition.expression();
    return expression != null
        ? new WhileLoopStatement(
            (Expression) visit(expression),
            (Statement) visit(ctx.loopBody))
        : new WhileLoopStatement(
            // TODO visit IterationConditionInitializer properly
            (IterationConditionInitializer) visit(condition),
            (Statement) visit(ctx.loopBody));
  }

  @Override
  public DoWhileLoopStatement visitDoWhileStatement(DoWhileStatementContext ctx) {
    return new DoWhileLoopStatement(
        (Statement) visit(ctx.loopBody),
        (Expression) visit(ctx.condition));
  }

  @Override
  public ASTNode visitAttribute(AttributeContext ctx) {
    // TODO
    return null;
  }

  @Override
  public ASTNode visitSingleAttribute(SingleAttributeContext ctx) {
    // TODO
    return null;
  }

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    var type = node.getSymbol().getType();
    if (type == GLSLLexer.IDENTIFIER) {
      return Identifier.from(node);
    }
    throw new IllegalStateException("Unhandled terminal node: " + node.getText());
  }
}
