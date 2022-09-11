package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public abstract class DelegateTokenProcessor implements TokenProcessor {
  protected final TokenProcessor delegate;

  public DelegateTokenProcessor(TokenProcessor delegate) {
    this.delegate = delegate;
  }

  @Override
  public String generateString() {
    return delegate.generateString();
  }

  @Override
  public void appendToken(PrintToken token) {
    delegate.appendToken(token);
  }

  @Override
  public void appendDirectly(String content) {
    delegate.appendDirectly(content);
  }

  @Override
  public void appendDirectly(char content) {
    delegate.appendDirectly(content);
  }
}
