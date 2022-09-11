package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public class LineAnnotator extends DelegateTokenProcessor<TokenProcessor> {
  private int sourceLine = 1;
  private int outputLine = 1;

  public LineAnnotator(TokenProcessor delegate) {
    super(delegate);
  }

  @Override
  public void appendToken(PrintToken token) {
    // TODO Auto-generated method stub
    super.appendToken(token);
  }
}
