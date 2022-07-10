package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class PrecisionQualifier extends TypeQualifierPart {
  public enum PrecisionLevel implements TokenAssociatedEnum {
    HIGH(GLSLLexer.HIGHP),
    MEDIUM(GLSLLexer.MEDIUMP),
    LOW(GLSLLexer.LOWP);

    public final int tokenType;

    private PrecisionLevel(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static PrecisionLevel fromToken(Token token) {
      return TypeUtil.enumFromToken(PrecisionLevel.values(), token);
    }
  }

  public PrecisionLevel precisionLevel;

  public PrecisionQualifier(PrecisionLevel storageType) {
    this.precisionLevel = storageType;
  }

  @Override
  public QualifierType getQualifierType() {
    return QualifierType.PRECISION;
  }

  @Override
  public <R> R typeQualifierPartAccept(ASTVisitor<R> visitor) {
    return visitor.visitPrecisionQualifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal types have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    // terminal types have no children
  }
}
