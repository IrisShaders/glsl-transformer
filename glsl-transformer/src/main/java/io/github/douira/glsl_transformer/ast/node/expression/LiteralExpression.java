package io.github.douira.glsl_transformer.ast.node.expression;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.parse_ast.Type;

public class LiteralExpression extends TerminalExpression {
  public Type literalType;
  public boolean booleanValue;
  public long integerValue;
  public double floatingValue;

  public LiteralExpression(Type literalType, boolean booleanValue) {
    this.literalType = literalType;
    this.booleanValue = booleanValue;
  }

  public LiteralExpression(Type literalType, long integerValue) {
    this.literalType = literalType;
    this.integerValue = integerValue;
  }

  public LiteralExpression(Type literalType, double floatingValue) {
    this.literalType = literalType;
    this.floatingValue = floatingValue;
  }

  public Number getNumber() {
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return booleanValue ? 1 : 0;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        return integerValue;
      case FLOATING_POINT:
        return floatingValue;
      default:
        throw new RuntimeException("Unsupported literal type: " + literalType);
    }
  }

  public boolean isPositive() {
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return booleanValue;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        return integerValue > 0;
      case FLOATING_POINT:
        return floatingValue > 0;
      default:
        throw new RuntimeException("Unsupported literal type: " + literalType);
    }
  }

  public static LiteralExpression from(Token content) {
    var literalType = Type.ofLiteralTokenType(content.getType());
    var tokenContent = content.getText();
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return new LiteralExpression(
            literalType, tokenContent.equals("true"));
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        return new LiteralExpression(
            literalType, Long.parseLong(tokenContent));
      case FLOATING_POINT:
        return new LiteralExpression(
            literalType, Float.parseFloat(tokenContent));
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.LITERAL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitLiteralExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterLiteralExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitLiteralExpression(this);
  }
}
