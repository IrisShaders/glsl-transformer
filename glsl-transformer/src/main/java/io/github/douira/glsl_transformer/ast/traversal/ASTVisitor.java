package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;

/**
 * The AST visitor knows how to traverse the AST and visit each node.
 * Information about the order and structure of each node is encoded in this
 * visitor.
 */
public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  default R visitTranslationUnit(TranslationUnit node) {
    var result = initialResult();
    result = visitSafe(result, node.getVersionStatement());
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
    return visit(node.getQualifier());
  }

  default R visitExpression(Expression node) {
    return superNodeTypeResult();
  }

  default R visitUnaryExpression(UnaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitBitwiseNotExpression(BitwiseNotExpression node) {
    return visit(node.getOperand());
  }

  default R visitBooleanNotExpression(BooleanNotExpression node) {
    return visit(node.getOperand());
  }

  default R visitDecrementPostfixExpression(DecrementPostfixExpression node) {
    return visit(node.getOperand());
  }

  default R visitDecrementPrefixExpression(DecrementPrefixExpression node) {
    return visit(node.getOperand());
  }

  default R visitFunctionCallExpression(FunctionCallExpression node) {
    return visitChildren(
        visitTwoChildren(
            node.getFunctionName(),
            node.getFunctionSpecifier()),
        node.getParameters());
  }

  default R visitGroupingExpression(GroupingExpression node) {
    return visit(node.getOperand());
  }

  default R visitIncrementPostfixExpression(IncrementPostfixExpression node) {
    return visit(node.getOperand());
  }

  default R visitIncrementPrefixExpression(IncrementPrefixExpression node) {
    return visit(node.getOperand());
  }

  default R visitMemberAccessExpression(MemberAccessExpression node) {
    return visitTwoChildren(node.getOperand(), node.getMember());
  }

  default R visitLengthAccessExpression(LengthAccessExpression node) {
    return visit(node.getOperand());
  }

  default R visitNegationExpression(NegationExpression node) {
    return visit(node.getOperand());
  }

  default R visitIdentityExpression(IdentityExpression node) {
    return visit(node.getOperand());
  }

  default R visitBinaryExpression(BinaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitArrayAccessExpression(ArrayAccessExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitMultiplicationExpression(MultiplicationExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitDivisionExpression(DivisionExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitModuloExpression(ModuloExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitAdditionExpression(AdditionExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitSubtractionExpression(SubtractionExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitLeftShiftExpression(LeftShiftExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitRightShiftExpression(RightShiftExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitLessThanExpression(LessThanExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitGreaterThanExpression(GreaterThanExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitLessThanEqualExpression(LessThanEqualExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitEqualityExpression(EqualityExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitInequalityExpression(InequalityExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseAndExpression(BitwiseAndExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseXorExpression(BitwiseXorExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseOrExpression(BitwiseOrExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBooleanAndExpression(BooleanAndExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBooleanXorExpression(BooleanXorExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBooleanOrExpression(BooleanOrExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitAssignmentExpression(AssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
  }

  default R visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    return visitTwoChildren(node.getLeft(), node.getRight());
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
    return visit(node.getIdentifier());
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

  default R visitDeclarationStatement(DeclarationStatement node) {
    return visit(node.getDeclaration());
  }

  default R visitExpressionStatement(ExpressionStatement node) {
    return visit(node.getExpression());
  }

  default R visitSelectionStatement(SelectionStatement node) {
    var result = initialResult();
    for (int i = 0, size = node.statements.size(); i < size; i++) {
      result = visitSafe(result, node.conditions.get(i));
      result = visitSafe(result, node.statements.get(i));
    }
    return result;
  }

  default R visitSwitchStatement(SwitchStatement node) {
    return visitTwoChildren(node.getExpression(), node.getStatement());
  }

  default R visitCaseStatement(CaseStatement node) {
    return visit(node.getExpression());
  }

  default R visitDefaultStatement(DefaultStatement node) {
    return defaultResult();
  }

  default R visitCaseLabelStatement(CaseLabelStatement node) {
    return superNodeTypeResult();
  }

  default R visitForLoopStatement(ForLoopStatement node) {
    var result = initialResult();
    result = visitSafe(result, node.getInitExpression());
    result = visitSafe(result, node.getInitDeclaration());
    result = visitSafe(result, node.getCondition());
    result = visitSafe(result, node.getIterationConditionInitializer());
    result = visitSafe(result, node.getIncrementer());
    return visit(result, node.getStatement());
  }

  default R visitWhileLoopStatement(WhileLoopStatement node) {
    return visitTwoChildren(node.getCondition(), node.getStatement());
  }

  default R visitDoWhileLoopStatement(DoWhileLoopStatement node) {
    return visitTwoChildren(node.getStatement(), node.getCondition());
  }

  default R visitContinueStatement(ContinueStatement node) {
    return defaultResult();
  }

  default R visitBreakStatement(BreakStatement node) {
    return defaultResult();
  }

  default R visitReturnStatement(ReturnStatement node) {
    return visit(node.getExpression());
  }

  default R visitDiscardStatement(DiscardStatement node) {
    return defaultResult();
  }

  default R visitDemoteStatement(DemoteStatement node) {
    return defaultResult();
  }

  default R visitManyStatement(ManyStatement node) {
    return superNodeTypeResult();
  }

  default R visitLoopStatement(LoopStatement node) {
    return superNodeTypeResult();
  }

  default R visitTerminalStatement(TerminalStatement node) {
    return superNodeTypeResult();
  }

  default R visitSemiTerminalStatement(SemiTerminalStatement node) {
    return superNodeTypeResult();
  }

  default R visitDeclaration(Declaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitDeclarationMember(DeclarationMember node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitFullTypeParameter(FullTypeParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitFunctionDeclaration(FunctionDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitFunctionParameter(FunctionParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitInterfaceBlockDeclaration(InterfaceBlockDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitNamedParameter(NamedParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitPrecisionDeclaration(PrecisionDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitTypeAndInitDeclaration(TypeAndInitDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitVariableDeclaration(VariableDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitExpressionInitializer(ExpressionInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitInitializer(Initializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitNestedInitializer(NestedInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitInterpolationQualifier(InterpolationQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitInvariantQualifier(InvariantQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitLayoutQualifier(LayoutQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitLayoutQualifierPart(LayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitNamedLayoutQualifierPart(NamedLayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitPreciseQualifier(PreciseQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitPrecisionQualifier(PrecisionQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitSharedLayoutQualifierPart(SharedLayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitStorageQualifier(StorageQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitTypeQualifier(TypeQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitTypeQualifierPart(TypeQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitArraySpecifier(ArraySpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitBuiltinFixedTypeSpecifier(BuiltinFixedTypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitBuiltinNumericTypeSpecifier(BuiltinNumericTypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitTypeReference(TypeReference node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitTypeSpecifier(TypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitStructBody(StructBody node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitStructDeclarator(StructDeclarator node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitStructMember(StructMember node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitStructSpecifier(StructSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitFullySpecifiedType(FullySpecifiedType node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitIterationConditionInitializer(IterationConditionInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  default R visitIdentifier(Identifier node) {
    return defaultResult();
  }
}
