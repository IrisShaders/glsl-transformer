package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;

public interface ASTListener extends GeneralASTListener {
  default void enterTranslationUnit(TranslationUnit node) {
  }

  default void exitTranslationUnit(TranslationUnit node) {
  }

  default void enterExternalDeclaration(ExternalDeclaration node) {
  }

  default void exitExternalDeclaration(ExternalDeclaration node) {
  }

  default void enterLayoutDefaults(LayoutDefaults node) {
  }

  default void exitLayoutDefaults(LayoutDefaults node) {
  }

  default void enterExpression(Expression node) {
  }

  default void exitExpression(Expression node) {
  }

  default void enterUnaryExpression(UnaryExpression node) {
  }

  default void exitUnaryExpression(UnaryExpression node) {
  }

  default void enterBitwiseNotExpression(BitwiseNotExpression node) {
  }

  default void exitBitwiseNotExpression(BitwiseNotExpression node) {
  }

  default void enterBooleanNotExpression(BooleanNotExpression node) {
  }

  default void exitBooleanNotExpression(BooleanNotExpression node) {
  }

  default void enterDecrementPostfixExpression(DecrementPostfixExpression node) {
  }

  default void exitDecrementPostfixExpression(DecrementPostfixExpression node) {
  }

  default void enterDecrementPrefixExpression(DecrementPrefixExpression node) {
  }

  default void exitDecrementPrefixExpression(DecrementPrefixExpression node) {
  }

  default void enterFunctionCallExpression(FunctionCallExpression node) {
  }

  default void exitFunctionCallExpression(FunctionCallExpression node) {
  }

  default void enterGroupingExpression(GroupingExpression node) {
  }

  default void exitGroupingExpression(GroupingExpression node) {
  }

  default void enterIncrementPostfixExpression(IncrementPostfixExpression node) {
  }

  default void exitIncrementPostfixExpression(IncrementPostfixExpression node) {
  }

  default void enterIncrementPrefixExpression(IncrementPrefixExpression node) {
  }

  default void exitIncrementPrefixExpression(IncrementPrefixExpression node) {
  }

  default void enterMemberAccessExpression(MemberAccessExpression node) {
  }

  default void exitMemberAccessExpression(MemberAccessExpression node) {
  }

  default void enterLengthAccessExpression(LengthAccessExpression node) {
  }

  default void exitLengthAccessExpression(LengthAccessExpression node) {
  }

  default void enterNegationExpression(NegationExpression node) {
  }

  default void exitNegationExpression(NegationExpression node) {
  }

  default void enterIdentityExpression(IdentityExpression node) {
  }

  default void exitIdentityExpression(IdentityExpression node) {
  }

  default void enterBinaryExpression(BinaryExpression node) {
  }

  default void exitBinaryExpression(BinaryExpression node) {
  }

  default void enterArrayAccessExpression(ArrayAccessExpression node) {
  }

  default void exitArrayAccessExpression(ArrayAccessExpression node) {
  }

  default void enterMultiplicationExpression(MultiplicationExpression node) {
  }

  default void exitMultiplicationExpression(MultiplicationExpression node) {
  }

  default void enterDivisionExpression(DivisionExpression node) {
  }

  default void exitDivisionExpression(DivisionExpression node) {
  }

  default void enterModuloExpression(ModuloExpression node) {
  }

  default void exitModuloExpression(ModuloExpression node) {
  }

  default void enterAdditionExpression(AdditionExpression node) {
  }

  default void exitAdditionExpression(AdditionExpression node) {
  }

  default void enterSubtractionExpression(SubtractionExpression node) {
  }

  default void exitSubtractionExpression(SubtractionExpression node) {
  }

  default void enterShiftLeftExpression(ShiftLeftExpression node) {
  }

  default void exitShiftLeftExpression(ShiftLeftExpression node) {
  }

  default void enterShiftRightExpression(ShiftRightExpression node) {
  }

  default void exitShiftRightExpression(ShiftRightExpression node) {
  }

  default void enterLessThanExpression(LessThanExpression node) {
  }

  default void exitLessThanExpression(LessThanExpression node) {
  }

  default void enterGreaterThanExpression(GreaterThanExpression node) {
  }

  default void exitGreaterThanExpression(GreaterThanExpression node) {
  }

  default void enterLessThanEqualExpression(LessThanEqualExpression node) {
  }

  default void exitLessThanEqualExpression(LessThanEqualExpression node) {
  }

  default void enterGreaterThanEqualExpression(GreaterThanEqualExpression node) {
  }

  default void exitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
  }

  default void enterEqualExpression(EqualExpression node) {
  }

  default void exitEqualExpression(EqualExpression node) {
  }

  default void enterNotEqualExpression(NotEqualExpression node) {
  }

  default void exitNotEqualExpression(NotEqualExpression node) {
  }

  default void enterBitwiseAndExpression(BitwiseAndExpression node) {
  }

  default void exitBitwiseAndExpression(BitwiseAndExpression node) {
  }

  default void enterBitwiseXorExpression(BitwiseXorExpression node) {
  }

  default void exitBitwiseXorExpression(BitwiseXorExpression node) {
  }

  default void enterBitwiseOrExpression(BitwiseOrExpression node) {
  }

  default void exitBitwiseOrExpression(BitwiseOrExpression node) {
  }

  default void enterBooleanAndExpression(BooleanAndExpression node) {
  }

  default void exitBooleanAndExpression(BooleanAndExpression node) {
  }

  default void enterBooleanXorExpression(BooleanXorExpression node) {
  }

  default void exitBooleanXorExpression(BooleanXorExpression node) {
  }

  default void enterBooleanOrExpression(BooleanOrExpression node) {
  }

  default void exitBooleanOrExpression(BooleanOrExpression node) {
  }

  default void enterAssignmentExpression(AssignmentExpression node) {
  }

  default void exitAssignmentExpression(AssignmentExpression node) {
  }

  default void enterMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
  }

  default void exitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
  }

  default void enterDivisionAssignmentExpression(DivisionAssignmentExpression node) {
  }

  default void exitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
  }

  default void enterModuloAssignmentExpression(ModuloAssignmentExpression node) {
  }

  default void exitModuloAssignmentExpression(ModuloAssignmentExpression node) {
  }

  default void enterAdditionAssignmentExpression(AdditionAssignmentExpression node) {
  }

  default void exitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
  }

  default void enterSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
  }

  default void exitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
  }

  default void enterLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
  }

  default void exitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
  }

  default void enterRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
  }

  default void exitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
  }

  default void enterBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
  }

  default void exitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
  }

  default void enterBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
  }

  default void exitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
  }

  default void enterBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
  }

  default void exitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
  }

  default void enterTernaryExpression(TernaryExpression node) {
  }

  default void exitTernaryExpression(TernaryExpression node) {
  }

  default void enterConditionExpression(ConditionExpression node) {
  }

  default void exitConditionExpression(ConditionExpression node) {
  }

  default void enterManyExpression(ManyExpression node) {
  }

  default void exitManyExpression(ManyExpression node) {
  }

  default void enterSequenceExpression(SequenceExpression node) {
  }

  default void exitSequenceExpression(SequenceExpression node) {
  }

  default void enterReferenceExpression(ReferenceExpression node) {
  }

  default void exitReferenceExpression(ReferenceExpression node) {
  }

  default void enterLiteralExpression(LiteralExpression node) {
  }

  default void exitLiteralExpression(LiteralExpression node) {
  }

  default void enterStatement(Statement node) {
  }

  default void exitStatement(Statement node) {
  }

  default void enterCompoundStatement(CompoundStatement node) {
  }

  default void exitCompoundStatement(CompoundStatement node) {
  }
}
