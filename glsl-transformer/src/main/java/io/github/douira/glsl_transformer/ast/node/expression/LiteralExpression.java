package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.util.Type;
import io.github.douira.glsl_transformer.util.Type.NumberType;

public class LiteralExpression extends TerminalExpression {
  public Type literalType;
  public boolean booleanValue;
  public long integerValue;
  public IntegerFormat integerFormat;
  public double floatingValue;

  public enum IntegerFormat {
    DECIMAL,
    HEXADECIMAL,
    OCTAL
  }

  public LiteralExpression(Type literalType, boolean booleanValue) {
    this.literalType = literalType;
    this.booleanValue = booleanValue;
  }

  public LiteralExpression(Type literalType, long integerValue) {
    this(literalType, integerValue, IntegerFormat.DECIMAL);
  }

  public LiteralExpression(Type literalType, long integerValue, IntegerFormat integerFormat) {
    this.literalType = literalType;
    this.integerValue = integerValue;
    this.integerFormat = integerFormat;
  }

  public LiteralExpression(Type literalType, double floatingValue) {
    this.literalType = literalType;
    this.floatingValue = floatingValue;
  }

  public Number getNumber() {
    var bitDepth = literalType.getBitDepth();
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return booleanValue ? 1 : 0;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        switch (bitDepth) {
          case 8:
            return Byte.valueOf((byte) integerValue);
          case 16:
            return Short.valueOf((short) integerValue);
          case 32:
            return Integer.valueOf((int) integerValue);
          case 64:
            return Long.valueOf(integerValue);
          default:
            throw new IllegalArgumentException("Unsupported bit depth: " + bitDepth);
        }
      case FLOATING_POINT:
        if (bitDepth == 64) {
          return Double.valueOf(floatingValue);
        }
        return Float.valueOf((float) floatingValue);
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  public boolean isPositive() {
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return true;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        return integerValue > 0l;
      case FLOATING_POINT:
        return floatingValue > 0.0d;
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  public boolean isNonZero() {
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return booleanValue;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        return integerValue != 0l;
      case FLOATING_POINT:
        return floatingValue != 0.0d;
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  public int getIntegerRadix() {
    return integerFormat == IntegerFormat.HEXADECIMAL
        ? 16
        : integerFormat == IntegerFormat.OCTAL
            ? 8
            : 10;
  }

  public boolean isBoolean() {
    return literalType.getNumberType() == NumberType.BOOLEAN;
  }

  public boolean isInteger() {
    return literalType.getNumberType() == NumberType.SIGNED_INTEGER
        || literalType.getNumberType() == NumberType.UNSIGNED_INTEGER;
  }

  public boolean isFloatingPoint() {
    return literalType.getNumberType() == NumberType.FLOATING_POINT;
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
    super.enterNode(listener);
    listener.enterLiteralExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitLiteralExpression(this);
  }
}
