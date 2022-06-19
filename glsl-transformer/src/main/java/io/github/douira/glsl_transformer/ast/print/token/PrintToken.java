package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.ASTNode;
import io.github.douira.glsl_transformer.ast.print.TokenRole;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public abstract class PrintToken {
  private final ASTNode source;
  private final TokenChannel channel;
  private final TokenRole role;

  public PrintToken(ASTNode source, TokenChannel channel, TokenRole role) {
    this.source = source;
    this.channel = channel;
    this.role = role;
  }

  public PrintToken(ASTNode source, TokenRole role) {
    this(source, TokenChannel.DEFAULT, role);
  }

  public PrintToken(ASTNode source, TokenChannel channel) {
    this(source, channel, TokenRole.DEFAULT);
  }

  public PrintToken(ASTNode source) {
    this(source, TokenChannel.DEFAULT);
  }

  public abstract String getContent();

  public ASTNode getSource() {
    return source;
  }

  public TokenChannel getChannel() {
    return channel;
  }

  public TokenRole getRole() {
    return role;
  }
}
