package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.ASTNode;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public class LiteralToken extends PrintToken {
  public String content;

  public LiteralToken(ASTNode source, TokenChannel channel, String content) {
    super(source, channel);
    this.content = content;
  }

  public LiteralToken(ASTNode source, String content) {
    super(source);
    this.content = content;
  }

  @Override
  public String getContent() {
    return content;
  }
}
