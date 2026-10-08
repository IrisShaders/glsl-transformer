package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public class SimplePrinter implements TokenProcessor {
  private StringBuilder builder = new StringBuilder();

  @Override
  public String generateString() {
    return builder.toString();
  }

  @Override
  public void appendToken(PrintToken token) {
    var content = token.getContent();
    if (content != null) {
      builder.append(content);
    }
  }

  public StringBuilder getBuilder() {
    return builder;
  }
}
