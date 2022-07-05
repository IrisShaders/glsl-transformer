package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.print.TokenRole;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public class LiteralToken extends PrintToken {
  public String content;

  public LiteralToken(TokenChannel channel, TokenRole role, String content) {
    super(channel, role);
    this.content = content;
  }

  public LiteralToken(TokenRole role, String content) {
    super(role);
    this.content = content;
  }

  public LiteralToken(TokenChannel channel, String content) {
    super(channel);
    this.content = content;
  }

  public LiteralToken(String content) {
    this.content = content;
  }

  @Override
  public String getContent() {
    return content;
  }
}
