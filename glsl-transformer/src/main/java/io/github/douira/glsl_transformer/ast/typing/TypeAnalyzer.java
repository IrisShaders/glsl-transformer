package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
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
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

/**
 * Performs type analysis on an AST. This is done by visiting each node and
 * setting its type field.
 * 
 * Currently WIP and doesn't do much at all yet.
 */
public class TypeAnalyzer implements ASTVisitor<Type> {
  public Type analyze(ASTNode node) {
    node.accept(this);
    return node.getType();
  }

  private static void assertTypesMatch(Type a, Type b) {
    if (!a.equals(b)) {
      throw new RuntimeException("Types do not match: " + a + " and " + b);
    }
  }

  private static void assertArrayIndex(Type indexType) {
    // signed ints may be used, but only as dynamic indices not as constant integral
    // expressions (since that would be a compile-time error)
    if (!(indexType instanceof NumericValueType valueType && valueType.type.isScalar()
        && (valueType.type.getNumberType().isInteger()))) {
      throw new RuntimeException("Array index must be an integer");
    }
  }

  @Override
  public Type visit(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public Type visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitAdditionAssignmentExpression(node);
  }

  @Override
  public Type visitAdditionExpression(AdditionExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitAdditionExpression(node);
  }

  @Override
  public Type visitArrayAccessExpression(ArrayAccessExpression node) {
    assertArrayIndex(visit(node.getRight()));
    var left = visit(node.getLeft());
    if (!(left instanceof ArrayType arrayType)) {
      throw new RuntimeException("Array access on non-array type");
    }
    return arrayType.elementType;
  }

  @Override
  public Type visitArraySpecifier(ArraySpecifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitArraySpecifier(node);
  }

  @Override
  public Type visitAssignmentExpression(AssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitAssignmentExpression(node);
  }

  @Override
  public Type visitBinaryExpression(BinaryExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBinaryExpression(node);
  }

  @Override
  public Type visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseAndAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseAndExpression(BitwiseAndExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseAndExpression(node);
  }

  @Override
  public Type visitBitwiseNotExpression(BitwiseNotExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseNotExpression(node);
  }

  @Override
  public Type visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseOrAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseOrExpression(BitwiseOrExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseOrExpression(node);
  }

  @Override
  public Type visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseXorAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseXorExpression(BitwiseXorExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBitwiseXorExpression(node);
  }

  @Override
  public Type visitBooleanAndExpression(BooleanAndExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBooleanAndExpression(node);
  }

  @Override
  public Type visitBooleanNotExpression(BooleanNotExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBooleanNotExpression(node);
  }

  @Override
  public Type visitBooleanOrExpression(BooleanOrExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBooleanOrExpression(node);
  }

  @Override
  public Type visitBooleanXorExpression(BooleanXorExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBooleanXorExpression(node);
  }

  @Override
  public Type visitBreakStatement(BreakStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitBreakStatement(node);
  }

  @Override
  public Type visitFixedTypeSpecifier(FixedTypeSpecifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFixedTypeSpecifier(node);
  }

  @Override
  public Type visitNumericTypeSpecifier(NumericTypeSpecifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitNumericTypeSpecifier(node);
  }

  @Override
  public Type visitCaseLabelStatement(CaseLabelStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitCaseLabelStatement(node);
  }

  @Override
  public Type visitCaseStatement(CaseStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitCaseStatement(node);
  }

  @Override
  public Type visitCompoundStatement(CompoundStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitCompoundStatement(node);
  }

  @Override
  public Type visitConditionExpression(ConditionExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitConditionExpression(node);
  }

  @Override
  public Type visitContinueStatement(ContinueStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitContinueStatement(node);
  }

  @Override
  public Type visitCustomDirective(CustomDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitDeclaration(Declaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDeclaration(node);
  }

  @Override
  public Type visitDeclarationExternalDeclaration(DeclarationExternalDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDeclarationExternalDeclaration(node);
  }

  @Override
  public Type visitDeclarationMember(DeclarationMember node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDeclarationMember(node);
  }

  @Override
  public Type visitDeclarationStatement(DeclarationStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDeclarationStatement(node);
  }

  @Override
  public Type visitDecrementPostfixExpression(DecrementPostfixExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDecrementPostfixExpression(node);
  }

  @Override
  public Type visitDecrementPrefixExpression(DecrementPrefixExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDecrementPrefixExpression(node);
  }

  @Override
  public Type visitDefaultStatement(DefaultStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitDemoteStatement(DemoteStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitDiscardStatement(DiscardStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDivisionAssignmentExpression(node);
  }

  @Override
  public Type visitDivisionExpression(DivisionExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDivisionExpression(node);
  }

  @Override
  public Type visitDoWhileLoopStatement(DoWhileLoopStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitDoWhileLoopStatement(node);
  }

  @Override
  public Type visitEmptyDeclaration(EmptyDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitEmptyDeclaration(node);
  }

  @Override
  public Type visitEmptyStatement(EmptyStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitEmptyStatement(node);
  }

  @Override
  public Type visitEqualityExpression(EqualityExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitEqualityExpression(node);
  }

  @Override
  public Type visitExpression(Expression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitExpression(node);
  }

  @Override
  public Type visitExpressionInitializer(ExpressionInitializer node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitExpressionInitializer(node);
  }

  @Override
  public Type visitExpressionStatement(ExpressionStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitExpressionStatement(node);
  }

  @Override
  public Type visitExtensionDirective(ExtensionDirective node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitExtensionDirective(node);
  }

  @Override
  public Type visitExternalDeclaration(ExternalDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitExternalDeclaration(node);
  }

  @Override
  public Type visitForLoopStatement(ForLoopStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitForLoopStatement(node);
  }

  @Override
  public Type visitFullySpecifiedType(FullySpecifiedType node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFullySpecifiedType(node);
  }

  @Override
  public Type visitFunctionCallExpression(FunctionCallExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFunctionCallExpression(node);
  }

  @Override
  public Type visitFunctionDeclaration(FunctionDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFunctionDeclaration(node);
  }

  @Override
  public Type visitFunctionDefinition(FunctionDefinition node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFunctionDefinition(node);
  }

  @Override
  public Type visitFunctionParameter(FunctionParameter node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFunctionParameter(node);
  }

  @Override
  public Type visitFunctionPrototype(FunctionPrototype node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitFunctionPrototype(node);
  }

  @Override
  public Type visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitGreaterThanEqualExpression(node);
  }

  @Override
  public Type visitGreaterThanExpression(GreaterThanExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitGreaterThanExpression(node);
  }

  @Override
  public Type visitGroupingExpression(GroupingExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitGroupingExpression(node);
  }

  @Override
  public Type visitIdentifier(Identifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIdentifier(node);
  }

  @Override
  public Type visitIdentityExpression(IdentityExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIdentityExpression(node);
  }

  @Override
  public Type visitIgnoreIntersectionStatement(IgnoreIntersectionStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIgnoreIntersectionStatement(node);
  }

  @Override
  public Type visitIncludeDirective(IncludeDirective node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIncludeDirective(node);
  }

  @Override
  public Type visitIncrementPostfixExpression(IncrementPostfixExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIncrementPostfixExpression(node);
  }

  @Override
  public Type visitIncrementPrefixExpression(IncrementPrefixExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIncrementPrefixExpression(node);
  }

  @Override
  public Type visitInequalityExpression(InequalityExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitInequalityExpression(node);
  }

  @Override
  public Type visitInitializer(Initializer node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitInitializer(node);
  }

  @Override
  public Type visitInterfaceBlockDeclaration(InterfaceBlockDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitInterfaceBlockDeclaration(node);
  }

  @Override
  public Type visitIterationConditionInitializer(IterationConditionInitializer node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitIterationConditionInitializer(node);
  }

  @Override
  public Type visitLayoutDefaults(LayoutDefaults node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLayoutDefaults(node);
  }

  @Override
  public Type visitLayoutQualifier(LayoutQualifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLayoutQualifier(node);
  }

  @Override
  public Type visitLayoutQualifierPart(LayoutQualifierPart node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLayoutQualifierPart(node);
  }

  @Override
  public Type visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLeftShiftAssignmentExpression(node);
  }

  @Override
  public Type visitLeftShiftExpression(LeftShiftExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLeftShiftExpression(node);
  }

  @Override
  public Type visitLengthAccessExpression(LengthAccessExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLengthAccessExpression(node);
  }

  @Override
  public Type visitLessThanEqualExpression(LessThanEqualExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLessThanEqualExpression(node);
  }

  @Override
  public Type visitLessThanExpression(LessThanExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLessThanExpression(node);
  }

  @Override
  public Type visitLiteralExpression(LiteralExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLiteralExpression(node);
  }

  @Override
  public Type visitLoopStatement(LoopStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitLoopStatement(node);
  }

  @Override
  public Type visitManyExpression(ManyExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitManyExpression(node);
  }

  @Override
  public Type visitManyStatement(ManyStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitManyStatement(node);
  }

  @Override
  public Type visitMemberAccessExpression(MemberAccessExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitMemberAccessExpression(node);
  }

  @Override
  public Type visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitModuloAssignmentExpression(node);
  }

  @Override
  public Type visitModuloExpression(ModuloExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitModuloExpression(node);
  }

  @Override
  public Type visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitMultiplicationAssignmentExpression(node);
  }

  @Override
  public Type visitMultiplicationExpression(MultiplicationExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitMultiplicationExpression(node);
  }

  @Override
  public Type visitNamedLayoutQualifierPart(NamedLayoutQualifierPart node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitNamedLayoutQualifierPart(node);
  }

  @Override
  public Type visitNegationExpression(NegationExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitNegationExpression(node);
  }

  @Override
  public Type visitNestedInitializer(NestedInitializer node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitNestedInitializer(node);
  }

  @Override
  public Type visitPragmaDirective(PragmaDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitPrecisionDeclaration(PrecisionDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitPrecisionDeclaration(node);
  }

  @Override
  public Type visitReferenceExpression(ReferenceExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitReferenceExpression(node);
  }

  @Override
  public Type visitReturnStatement(ReturnStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitReturnStatement(node);
  }

  @Override
  public Type visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitRightShiftAssignmentExpression(node);
  }

  @Override
  public Type visitRightShiftExpression(RightShiftExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitRightShiftExpression(node);
  }

  @Override
  public Type visitSelectionStatement(SelectionStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSelectionStatement(node);
  }

  @Override
  public Type visitSemiTerminalStatement(SemiTerminalStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSemiTerminalStatement(node);
  }

  @Override
  public Type visitSequenceExpression(SequenceExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSequenceExpression(node);
  }

  @Override
  public Type visitStatement(Statement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitStatement(node);
  }

  @Override
  public Type visitStructBody(StructBody node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitStructBody(node);
  }

  @Override
  public Type visitStructDeclarator(StructDeclarator node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitStructDeclarator(node);
  }

  @Override
  public Type visitStructMember(StructMember node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitStructMember(node);
  }

  @Override
  public Type visitStructSpecifier(StructSpecifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitStructSpecifier(node);
  }

  @Override
  public Type visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSubtractionAssignmentExpression(node);
  }

  @Override
  public Type visitSubtractionExpression(SubtractionExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSubtractionExpression(node);
  }

  @Override
  public Type visitSwitchStatement(SwitchStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitSwitchStatement(node);
  }

  @Override
  public Type visitTerminalExpression(TerminalExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTerminalExpression(node);
  }

  @Override
  public Type visitTerminalStatement(TerminalStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTerminalStatement(node);
  }

  @Override
  public Type visitTerminateRayStatement(TerminateRayStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTerminateRayStatement(node);
  }

  @Override
  public Type visitTernaryExpression(TernaryExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTernaryExpression(node);
  }

  @Override
  public Type visitTranslationUnit(TranslationUnit node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTranslationUnit(node);
  }

  @Override
  public Type visitTypeAndInitDeclaration(TypeAndInitDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTypeAndInitDeclaration(node);
  }

  @Override
  public Type visitTypeQualifier(TypeQualifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTypeQualifier(node);
  }

  @Override
  public Type visitTypeQualifierPart(TypeQualifierPart node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTypeQualifierPart(node);
  }

  @Override
  public Type visitTypeReference(TypeReference node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTypeReference(node);
  }

  @Override
  public Type visitTypeSpecifier(TypeSpecifier node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitTypeSpecifier(node);
  }

  @Override
  public Type visitUnaryExpression(UnaryExpression node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitUnaryExpression(node);
  }

  @Override
  public Type visitVariableDeclaration(VariableDeclaration node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitVariableDeclaration(node);
  }

  @Override
  public Type visitVersionStatement(VersionStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitVersionStatement(node);
  }

  @Override
  public Type visitWhileLoopStatement(WhileLoopStatement node) {
    // TODO Auto-generated method stub
    return ASTVisitor.super.visitWhileLoopStatement(node);
  }

  @Override
  public Type initialResult() {
    throw new IllegalStateException("Initial result should never be needed!");
  }

  @Override
  public Type superNodeTypeResult() {
    throw new IllegalStateException("Super node type result should never be needed!");
  }

  @Override
  public Type visitData(Object data) {
    throw new IllegalStateException("Visit data should never be needed!");
  }

  @Override
  public Type defaultResult() {
    throw new IllegalStateException("Default result should never be needed!");
  }

  @Override
  public Type aggregateResult(Type aggregate, Type nextResult) {
    // TODO: type casting
    assertTypesMatch(aggregate, nextResult);
    return aggregate;
  }
}
