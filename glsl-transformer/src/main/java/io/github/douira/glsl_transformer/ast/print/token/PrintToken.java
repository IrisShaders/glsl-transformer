package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.print.TokenRole;
import io.github.douira.glsl_transformer.token_filter.TokenChannel;

public abstract class PrintToken {
  private ASTNode source;

  private final TokenChannel channel;
  private final TokenRole role;

  public PrintToken(TokenChannel channel, TokenRole role) {
    this.channel = channel;
    this.role = role;
  }

  public PrintToken(TokenRole role) {
    this(TokenChannel.DEFAULT, role);
  }

  public PrintToken(TokenChannel channel) {
    this(channel, TokenRole.DEFAULT);
  }

  public PrintToken() {
    this(TokenChannel.DEFAULT);
  }

  public abstract String getContent();

  public void setSource(ASTNode source) {
    this.source = source;
  }

  public ASTNode getSource() {
    return source;
  }

  public TokenChannel getChannel() {
    return channel;
  }

  public TokenRole getRole() {
    return role;
  }

  public boolean isCommonFormattingNewline() {
    return role == TokenRole.COMMON_FORMATTING && getContent().equals("\n");
  }

  public boolean endsWithNewline() {
    return getContent().endsWith("\n");
  }
}
