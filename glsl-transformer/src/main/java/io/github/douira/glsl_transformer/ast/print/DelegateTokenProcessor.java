package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public abstract class DelegateTokenProcessor<R extends TokenProcessor> implements TokenProcessor {
  protected final R delegate;

  public DelegateTokenProcessor(R delegate) {
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
}
