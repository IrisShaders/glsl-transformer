package io.github.douira.glsl_transformer.ast.node.external_declaration;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser.PragmaStatementContext;
import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class PragmaStatement extends ExternalDeclaration {
  public boolean stdGL;
  public PragmaType type;
  public String customName;
  public PragmaState state;

  public enum PragmaType implements TokenAssociatedEnum {
    DEBUG(GLSLLexer.PRAGMA_DEBUG),
    OPTIMIZE(GLSLLexer.PRAGMA_OPTIMIZE),
    INVARIANT(GLSLLexer.PRAGMA_INVARIANT),
    CUSTOM(GLSLLexer.NR_IDENTIFIER);

    public int tokenType;

    private PragmaType(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static PragmaType fromToken(Token token) {
      return TypeUtil.enumFromToken(PragmaType.values(), token);
    }
  }

  public enum PragmaState implements TokenAssociatedEnum {
    ON(GLSLLexer.NR_ON),
    OFF(GLSLLexer.NR_OFF),
    ALL(GLSLLexer.NR_ALL);

    public int tokenType;

    private PragmaState(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static PragmaState fromToken(Token token) {
      return TypeUtil.enumFromToken(PragmaState.values(), token);
    }
  }

  public PragmaStatement(boolean stdGL, String customPragmaName) {
    this.stdGL = stdGL;
    this.type = PragmaType.CUSTOM;
    this.customName = customPragmaName;
  }

  public PragmaStatement(boolean stdGL, PragmaType type, PragmaState state) {
    this.stdGL = stdGL;
    this.type = type;
    this.state = state;
  }

  public static PragmaStatement from(PragmaStatementContext ctx) {
    var stdGL = ctx.stdGL != null;
    var type = PragmaType.fromToken(ctx.type);
    return type == PragmaType.CUSTOM
        ? new PragmaStatement(stdGL, ctx.type.getText())
        : new PragmaStatement(stdGL, type, PragmaState.fromToken(ctx.state));
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.PRAGMA_STATEMENT;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitPragmaStatement(this);
  }
}
