package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;

public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  default R visitTranslationUnit(TranslationUnit node) {
    var result = initialResult();
    result = visitSafe(result, node.versionStatement);
    visitChildren(result, node);
    return result;
  }

  default R visitVersionStatement(VersionStatement node) {
    return defaultResult();
  }

  default R visitExternalDeclaration(ExternalDeclaration node) {
    return superNodeTypeResult();
  }

  default R visitEmptyDeclaration(EmptyDeclaration node) {
    return defaultResult();
  }

  default R visitPragmaStatement(PragmaStatement node) {
    return defaultResult();
  }

  default R visitExtensionStatement(ExtensionStatement node) {
    return superNodeTypeResult();
  }

  default R visitLayoutDefaults(LayoutDefaults node) {
    return visit(node.qualifier); // TODO: LayoutQualifier
  }

  default R visitExpression(Expression node) {
    return superNodeTypeResult();
  }

  default R visitUnaryExpression(UnaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitBitwiseNotExpression(BitwiseNotExpression node) {
    return visit(node.operand);
  }

  default R visitBooleanNotExpression(BooleanNotExpression node) {
    return visit(node.operand);
  }

  default R visitDecrementPostfixExpression(DecrementPostfixExpression node) {
    return visit(node.operand);
  }

  default R visitDecrementPrefixExpression(DecrementPrefixExpression node) {
    return visit(node.operand);
  }

  default R visitFunctionCallExpression(FunctionCallExpression node) {
    return visit(node.functionCall); // TODO: FunctionCall
  }

  default R visitGroupingExpression(GroupingExpression node) {
    return visit(node.operand);
  }

  default R visitIncrementPostfixExpression(IncrementPostfixExpression node) {
    return visit(node.operand);
  }

  default R visitIncrementPrefixExpression(IncrementPrefixExpression node) {
    return visit(node.operand);
  }

  default R visitMemberAccessExpression(MemberAccessExpression node) {
    return visit(node.operand);
  }

  default R visitLengthAccessExpression(LengthAccessExpression node) {
    return visit(node.operand);
  }

  default R visitNegationExpression(NegationExpression node) {
    return visit(node.operand);
  }

  default R visitIdentityExpression(IdentityExpression node) {
    return visit(node.operand);
  }

  default R visitBinaryExpression(BinaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitArrayAccessExpression(ArrayAccessExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitMultiplicationExpression(MultiplicationExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitDivisionExpression(DivisionExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitModuloExpression(ModuloExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitAdditionExpression(AdditionExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitSubtractionExpression(SubtractionExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitShiftLeftExpression(ShiftLeftExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitShiftRightExpression(ShiftRightExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitLessThanExpression(LessThanExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitGreaterThanExpression(GreaterThanExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitLessThanEqualExpression(LessThanEqualExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitEqualExpression(EqualExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitNotEqualExpression(NotEqualExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseAndExpression(BitwiseAndExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseXorExpression(BitwiseXorExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseOrExpression(BitwiseOrExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBooleanAndExpression(BooleanAndExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBooleanXorExpression(BooleanXorExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBooleanOrExpression(BooleanOrExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitAssignmentExpression(AssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    return visitTwoChildren(node.left, node.right);
  }

  default R visitTernaryExpression(TernaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitConditionExpression(ConditionExpression node) {
    return visitThreeChildren(
        node.getCondition(),
        node.getTrueExpression(),
        node.getFalseExpression());
  }

  default R visitManyExpression(ManyExpression node) {
    return superNodeTypeResult();
  }

  default R visitSequenceExpression(SequenceExpression node) {
    return visitChildren(node);
  }

  default R visitTerminalExpression(TerminalExpression node) {
    return superNodeTypeResult();
  }

  default R visitReferenceExpression(ReferenceExpression node) {
    return visit(node.identifier);
  }

  default R visitLiteralExpression(LiteralExpression node) {
    return defaultResult();
  }

  default R visitStatement(Statement node) {
    return superNodeTypeResult();
  }

  default R visitEmptyStatement(EmptyStatement node) {
    return defaultResult();
  }

  default R visitCompoundStatement(CompoundStatement node) {
    return visitChildren(node);
  }

  default R visitIdentifier(Identifier node) {
    return defaultResult();
  }
}
