package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class InterpolationQualifier extends TypeQualifierPart {
  public enum InterpolationType implements TokenAssociatedEnum {
    SMOOTH(GLSLLexer.SMOOTH),
    FLAT(GLSLLexer.FLAT),
    NOPERSPECTIVE(GLSLLexer.NOPERSPECTIVE);

    public final int tokenType;

    private InterpolationType(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static InterpolationType fromToken(Token token) {
      return TypeUtil.enumFromToken(InterpolationType.values(), token);
    }
  }

  public InterpolationType interpolationType;

  public InterpolationQualifier(InterpolationType interpolationType) {
    this.interpolationType = interpolationType;
  }

  @Override
  public QualifierType getQualifierType() {
    return QualifierType.INTERPOLATION;
  }

  @Override
  public <R> R typeQualifierPartAccept(ASTVisitor<R> visitor) {
    return visitor.visitInterpolationQualifier(this);
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
