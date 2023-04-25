package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

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
import io.github.douira.glsl_transformer.ast.traversal.ASTListenerVisitor;

/**
 * Performs type analysis on an AST. This is done by visiting each node and
 * setting its type field.
 * 
 * Currently WIP and doesn't do much at all yet.
 * 
 * TODO:
 * two options:
 * - type analysis with a tree and the scopes are on a stack.
 * - type analysis (potentially including data and control flow analysis) with a
 * graph and the scopes are maybe not in a stack? or only for analysis
 * 
 * 
 */
public class TypeAnalyzer extends ASTListenerVisitor<Type> {
  private Deque<Scope> scopeStack = new ArrayDeque<>();

  public Type analyze(ASTNode node) {
    node.accept(this);
    return node.getType();
  }

  private static void assertTypesMatch(Type a, Type b) {
    if (!a.equals(b)) {
      throw new TypeAnalysisException("Types do not match: " + a + " and " + b);
    }
  }

  private static void assertArrayIndex(Type indexType) {
    // signed ints may be used, but only as dynamic indices not as constant integral
    // expressions (since that would be a compile-time error)
    if (!(indexType instanceof NumericValueType valueType && valueType.type.isScalar()
        && (valueType.type.getNumberType().isInteger()))) {
      throw new TypeAnalysisException("Array index must be an integer");
    }
  }

  @Override
  public Type visit(ASTNode node) {
    var type = node.accept(this);
    node.assignType(type);
    return type;
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
    // TODO: does this method even need to ever run or is this handled by each type
    // of AST node?
    throw new IllegalStateException("Aggregate result should never be needed!");
  }

  @Override
  public Type visitIdentifier(Identifier node) {
    // TODO: should identifiers even be handled generally? or just specifically at
    // each type of AST node
    throw new IllegalStateException("Identifier should never be visited!");
  }

  @Override
  public Type visitReferenceExpression(ReferenceExpression node) {
    // reference expressions only refer to existing variables
    var name = node.getIdentifier().getName();
    var scope = scopeStack.peekFirst();
    while (scope != null) {
      var declaringNode = scope.declarations.get(name);
      if (declaringNode != null) {
        return declaringNode.getType();
      }
      scope = scope.parent;
    }
    throw new TypeAnalysisException("Could not find declaration for identifier " + name);
  }

  @Override
  public Type visitCompoundStatement(CompoundStatement node) {
    // TODO: not all compound statements create scopes (e.g. function bodies)
    // TODO: do if/else statements create scopes even if they are technically
    // compound statements (or rather, can be)?
    var scope = new Scope(scopeStack.peekFirst());
    scopeStack.addFirst(scope);
    return new ScopeForming(scope);
  }

  @Override
  public void exitCompoundStatement(CompoundStatement node) {
    scopeStack.removeFirst();
  }

  @Override
  public Type visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitAdditionAssignmentExpression(node);
  }

  @Override
  public Type visitAdditionExpression(AdditionExpression node) {
    // TODO Auto-generated method stub
    return super.visitAdditionExpression(node);
  }

  @Override
  public Type visitArrayAccessExpression(ArrayAccessExpression node) {
    assertArrayIndex(visit(node.getRight()));
    var left = visit(node.getLeft());
    if (!(left instanceof ArrayType arrayType)) {
      throw new TypeAnalysisException("Array access on non-array type");
    }
    return arrayType.elementType;
  }

  @Override
  public Type visitArraySpecifier(ArraySpecifier node) {
    // TODO Auto-generated method stub
    return super.visitArraySpecifier(node);
  }

  @Override
  public Type visitAssignmentExpression(AssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitAssignmentExpression(node);
  }

  @Override
  public Type visitBinaryExpression(BinaryExpression node) {
    // TODO Auto-generated method stub
    return super.visitBinaryExpression(node);
  }

  @Override
  public Type visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseAndAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseAndExpression(BitwiseAndExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseAndExpression(node);
  }

  @Override
  public Type visitBitwiseNotExpression(BitwiseNotExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseNotExpression(node);
  }

  @Override
  public Type visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseOrAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseOrExpression(BitwiseOrExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseOrExpression(node);
  }

  @Override
  public Type visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseXorAssignmentExpression(node);
  }

  @Override
  public Type visitBitwiseXorExpression(BitwiseXorExpression node) {
    // TODO Auto-generated method stub
    return super.visitBitwiseXorExpression(node);
  }

  @Override
  public Type visitBooleanAndExpression(BooleanAndExpression node) {
    // TODO Auto-generated method stub
    return super.visitBooleanAndExpression(node);
  }

  @Override
  public Type visitBooleanNotExpression(BooleanNotExpression node) {
    // TODO Auto-generated method stub
    return super.visitBooleanNotExpression(node);
  }

  @Override
  public Type visitBooleanOrExpression(BooleanOrExpression node) {
    // TODO Auto-generated method stub
    return super.visitBooleanOrExpression(node);
  }

  @Override
  public Type visitBooleanXorExpression(BooleanXorExpression node) {
    // TODO Auto-generated method stub
    return super.visitBooleanXorExpression(node);
  }

  @Override
  public Type visitBreakStatement(BreakStatement node) {
    // TODO Auto-generated method stub
    return super.visitBreakStatement(node);
  }

  @Override
  public Type visitFixedTypeSpecifier(FixedTypeSpecifier node) {
    // TODO Auto-generated method stub
    return super.visitFixedTypeSpecifier(node);
  }

  @Override
  public Type visitNumericTypeSpecifier(NumericTypeSpecifier node) {
    // TODO Auto-generated method stub
    return super.visitNumericTypeSpecifier(node);
  }

  @Override
  public Type visitCaseLabelStatement(CaseLabelStatement node) {
    // TODO Auto-generated method stub
    return super.visitCaseLabelStatement(node);
  }

  @Override
  public Type visitCaseStatement(CaseStatement node) {
    // TODO Auto-generated method stub
    return super.visitCaseStatement(node);
  }

  @Override
  public Type visitConditionExpression(ConditionExpression node) {
    // TODO Auto-generated method stub
    return super.visitConditionExpression(node);
  }

  @Override
  public Type visitContinueStatement(ContinueStatement node) {
    // TODO Auto-generated method stub
    return super.visitContinueStatement(node);
  }

  @Override
  public Type visitCustomDirective(CustomDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitDeclaration(Declaration node) {
    // TODO Auto-generated method stub
    return super.visitDeclaration(node);
  }

  @Override
  public Type visitDeclarationExternalDeclaration(DeclarationExternalDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitDeclarationExternalDeclaration(node);
  }

  @Override
  public Type visitDeclarationMember(DeclarationMember node) {
    // TODO Auto-generated method stub
    return super.visitDeclarationMember(node);
  }

  @Override
  public Type visitDeclarationStatement(DeclarationStatement node) {
    // TODO Auto-generated method stub
    return super.visitDeclarationStatement(node);
  }

  @Override
  public Type visitDecrementPostfixExpression(DecrementPostfixExpression node) {
    // TODO Auto-generated method stub
    return super.visitDecrementPostfixExpression(node);
  }

  @Override
  public Type visitDecrementPrefixExpression(DecrementPrefixExpression node) {
    // TODO Auto-generated method stub
    return super.visitDecrementPrefixExpression(node);
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
    return super.visitDivisionAssignmentExpression(node);
  }

  @Override
  public Type visitDivisionExpression(DivisionExpression node) {
    // TODO Auto-generated method stub
    return super.visitDivisionExpression(node);
  }

  @Override
  public Type visitDoWhileLoopStatement(DoWhileLoopStatement node) {
    // TODO Auto-generated method stub
    return super.visitDoWhileLoopStatement(node);
  }

  @Override
  public Type visitEmptyDeclaration(EmptyDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitEmptyDeclaration(node);
  }

  @Override
  public Type visitEmptyStatement(EmptyStatement node) {
    // TODO Auto-generated method stub
    return super.visitEmptyStatement(node);
  }

  @Override
  public Type visitEqualityExpression(EqualityExpression node) {
    // TODO Auto-generated method stub
    return super.visitEqualityExpression(node);
  }

  @Override
  public Type visitExpression(Expression node) {
    // TODO Auto-generated method stub
    return super.visitExpression(node);
  }

  @Override
  public Type visitExpressionInitializer(ExpressionInitializer node) {
    // TODO Auto-generated method stub
    return super.visitExpressionInitializer(node);
  }

  @Override
  public Type visitExpressionStatement(ExpressionStatement node) {
    // TODO Auto-generated method stub
    return super.visitExpressionStatement(node);
  }

  @Override
  public Type visitExtensionDirective(ExtensionDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitExternalDeclaration(ExternalDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitExternalDeclaration(node);
  }

  @Override
  public Type visitForLoopStatement(ForLoopStatement node) {
    // TODO Auto-generated method stub
    return super.visitForLoopStatement(node);
  }

  @Override
  public Type visitFullySpecifiedType(FullySpecifiedType node) {
    // TODO Auto-generated method stub
    return super.visitFullySpecifiedType(node);
  }

  @Override
  public Type visitFunctionCallExpression(FunctionCallExpression node) {
    // TODO Auto-generated method stub
    return super.visitFunctionCallExpression(node);
  }

  @Override
  public Type visitFunctionDeclaration(FunctionDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitFunctionDeclaration(node);
  }

  @Override
  public Type visitFunctionDefinition(FunctionDefinition node) {
    // TODO Auto-generated method stub
    return super.visitFunctionDefinition(node);
  }

  @Override
  public Type visitFunctionParameter(FunctionParameter node) {
    // TODO Auto-generated method stub
    return super.visitFunctionParameter(node);
  }

  @Override
  public Type visitFunctionPrototype(FunctionPrototype node) {
    // TODO Auto-generated method stub
    return super.visitFunctionPrototype(node);
  }

  @Override
  public Type visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    // TODO Auto-generated method stub
    return super.visitGreaterThanEqualExpression(node);
  }

  @Override
  public Type visitGreaterThanExpression(GreaterThanExpression node) {
    // TODO Auto-generated method stub
    return super.visitGreaterThanExpression(node);
  }

  @Override
  public Type visitGroupingExpression(GroupingExpression node) {
    // TODO Auto-generated method stub
    return super.visitGroupingExpression(node);
  }

  @Override
  public Type visitIdentityExpression(IdentityExpression node) {
    return visit(node.getOperand()); // TODO: correct with booleans?
  }

  @Override
  public Type visitIgnoreIntersectionStatement(IgnoreIntersectionStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitIncludeDirective(IncludeDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitIncrementPostfixExpression(IncrementPostfixExpression node) {
    // TODO Auto-generated method stub
    return super.visitIncrementPostfixExpression(node);
  }

  @Override
  public Type visitIncrementPrefixExpression(IncrementPrefixExpression node) {
    // TODO Auto-generated method stub
    return super.visitIncrementPrefixExpression(node);
  }

  @Override
  public Type visitInequalityExpression(InequalityExpression node) {
    // TODO Auto-generated method stub
    return super.visitInequalityExpression(node);
  }

  @Override
  public Type visitInitializer(Initializer node) {
    // TODO Auto-generated method stub
    return super.visitInitializer(node);
  }

  @Override
  public Type visitInterfaceBlockDeclaration(InterfaceBlockDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitInterfaceBlockDeclaration(node);
  }

  @Override
  public Type visitIterationConditionInitializer(IterationConditionInitializer node) {
    // TODO Auto-generated method stub
    return super.visitIterationConditionInitializer(node);
  }

  @Override
  public Type visitLayoutDefaults(LayoutDefaults node) {
    // TODO Auto-generated method stub
    return super.visitLayoutDefaults(node);
  }

  @Override
  public Type visitLayoutQualifier(LayoutQualifier node) {
    // TODO Auto-generated method stub
    return super.visitLayoutQualifier(node);
  }

  @Override
  public Type visitLayoutQualifierPart(LayoutQualifierPart node) {
    // TODO Auto-generated method stub
    return super.visitLayoutQualifierPart(node);
  }

  @Override
  public Type visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitLeftShiftAssignmentExpression(node);
  }

  @Override
  public Type visitLeftShiftExpression(LeftShiftExpression node) {
    // TODO Auto-generated method stub
    return super.visitLeftShiftExpression(node);
  }

  @Override
  public Type visitLengthAccessExpression(LengthAccessExpression node) {
    // TODO Auto-generated method stub
    return super.visitLengthAccessExpression(node);
  }

  @Override
  public Type visitLessThanEqualExpression(LessThanEqualExpression node) {
    // TODO Auto-generated method stub
    return super.visitLessThanEqualExpression(node);
  }

  @Override
  public Type visitLessThanExpression(LessThanExpression node) {
    // TODO Auto-generated method stub
    return super.visitLessThanExpression(node);
  }

  @Override
  public Type visitLiteralExpression(LiteralExpression node) {
    // TODO Auto-generated method stub
    return super.visitLiteralExpression(node);
  }

  @Override
  public Type visitLoopStatement(LoopStatement node) {
    // TODO Auto-generated method stub
    return super.visitLoopStatement(node);
  }

  @Override
  public Type visitManyExpression(ManyExpression node) {
    // TODO Auto-generated method stub
    return super.visitManyExpression(node);
  }

  @Override
  public Type visitManyStatement(ManyStatement node) {
    // TODO Auto-generated method stub
    return super.visitManyStatement(node);
  }

  @Override
  public Type visitMemberAccessExpression(MemberAccessExpression node) {
    // TODO Auto-generated method stub
    return super.visitMemberAccessExpression(node);
  }

  @Override
  public Type visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitModuloAssignmentExpression(node);
  }

  @Override
  public Type visitModuloExpression(ModuloExpression node) {
    // TODO Auto-generated method stub
    return super.visitModuloExpression(node);
  }

  @Override
  public Type visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitMultiplicationAssignmentExpression(node);
  }

  @Override
  public Type visitMultiplicationExpression(MultiplicationExpression node) {
    // TODO Auto-generated method stub
    return super.visitMultiplicationExpression(node);
  }

  @Override
  public Type visitNamedLayoutQualifierPart(NamedLayoutQualifierPart node) {
    // TODO Auto-generated method stub
    return super.visitNamedLayoutQualifierPart(node);
  }

  @Override
  public Type visitNegationExpression(NegationExpression node) {
    // TODO Auto-generated method stub
    return super.visitNegationExpression(node);
  }

  @Override
  public Type visitNestedInitializer(NestedInitializer node) {
    // TODO Auto-generated method stub
    return super.visitNestedInitializer(node);
  }

  @Override
  public Type visitPragmaDirective(PragmaDirective node) {
    return Type.VOID;
  }

  @Override
  public Type visitPrecisionDeclaration(PrecisionDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitPrecisionDeclaration(node);
  }

  @Override
  public Type visitReturnStatement(ReturnStatement node) {
    // TODO Auto-generated method stub
    return super.visitReturnStatement(node);
  }

  @Override
  public Type visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitRightShiftAssignmentExpression(node);
  }

  @Override
  public Type visitRightShiftExpression(RightShiftExpression node) {
    // TODO Auto-generated method stub
    return super.visitRightShiftExpression(node);
  }

  @Override
  public Type visitSelectionStatement(SelectionStatement node) {
    // TODO Auto-generated method stub
    return super.visitSelectionStatement(node);
  }

  @Override
  public Type visitSemiTerminalStatement(SemiTerminalStatement node) {
    // TODO Auto-generated method stub
    return super.visitSemiTerminalStatement(node);
  }

  @Override
  public Type visitSequenceExpression(SequenceExpression node) {
    // TODO Auto-generated method stub
    return super.visitSequenceExpression(node);
  }

  @Override
  public Type visitStatement(Statement node) {
    // TODO Auto-generated method stub
    return super.visitStatement(node);
  }

  @Override
  public Type visitStructBody(StructBody node) {
    // TODO Auto-generated method stub
    return super.visitStructBody(node);
  }

  @Override
  public Type visitStructDeclarator(StructDeclarator node) {
    // TODO Auto-generated method stub
    return super.visitStructDeclarator(node);
  }

  @Override
  public Type visitStructMember(StructMember node) {
    // TODO Auto-generated method stub
    return super.visitStructMember(node);
  }

  @Override
  public Type visitStructSpecifier(StructSpecifier node) {
    // TODO Auto-generated method stub
    return super.visitStructSpecifier(node);
  }

  @Override
  public Type visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    // TODO Auto-generated method stub
    return super.visitSubtractionAssignmentExpression(node);
  }

  @Override
  public Type visitSubtractionExpression(SubtractionExpression node) {
    // TODO Auto-generated method stub
    return super.visitSubtractionExpression(node);
  }

  @Override
  public Type visitSwitchStatement(SwitchStatement node) {
    // TODO Auto-generated method stub
    return super.visitSwitchStatement(node);
  }

  @Override
  public Type visitTerminalExpression(TerminalExpression node) {
    // TODO Auto-generated method stub
    return super.visitTerminalExpression(node);
  }

  @Override
  public Type visitTerminalStatement(TerminalStatement node) {
    // TODO Auto-generated method stub
    return super.visitTerminalStatement(node);
  }

  @Override
  public Type visitTerminateRayStatement(TerminateRayStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitTernaryExpression(TernaryExpression node) {
    // TODO Auto-generated method stub
    return super.visitTernaryExpression(node);
  }

  @Override
  public Type visitTranslationUnit(TranslationUnit node) {
    // TODO Auto-generated method stub
    return super.visitTranslationUnit(node);
  }

  @Override
  public Type visitTypeAndInitDeclaration(TypeAndInitDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitTypeAndInitDeclaration(node);
  }

  @Override
  public Type visitTypeQualifier(TypeQualifier node) {
    // TODO Auto-generated method stub
    return super.visitTypeQualifier(node);
  }

  @Override
  public Type visitTypeQualifierPart(TypeQualifierPart node) {
    // TODO Auto-generated method stub
    return super.visitTypeQualifierPart(node);
  }

  @Override
  public Type visitTypeReference(TypeReference node) {
    // TODO Auto-generated method stub
    return super.visitTypeReference(node);
  }

  @Override
  public Type visitTypeSpecifier(TypeSpecifier node) {
    // TODO Auto-generated method stub
    return super.visitTypeSpecifier(node);
  }

  @Override
  public Type visitUnaryExpression(UnaryExpression node) {
    // TODO Auto-generated method stub
    return super.visitUnaryExpression(node);
  }

  @Override
  public Type visitVariableDeclaration(VariableDeclaration node) {
    // TODO Auto-generated method stub
    return super.visitVariableDeclaration(node);
  }

  @Override
  public Type visitVersionStatement(VersionStatement node) {
    return Type.VOID;
  }

  @Override
  public Type visitWhileLoopStatement(WhileLoopStatement node) {
    // TODO Auto-generated method stub
    return super.visitWhileLoopStatement(node);
  }
}
