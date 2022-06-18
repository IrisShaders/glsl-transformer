package io.github.douira.glsl_transformer.ast.token;

import io.github.douira.glsl_transformer.ast.ASTNode;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public abstract class PrintToken {
  private ASTNode source;
  private TokenChannel channel;

  public PrintToken(ASTNode source, TokenChannel channel) {
    this.source = source;
    this.channel = channel;
  }

  public PrintToken(ASTNode source) {
    this(source, TokenChannel.DEFAULT);
  }

  public abstract String getContent();

  public TokenChannel getChannel() {
    return channel;
  }

  public ASTNode getSource() {
    return source;
  }
}
