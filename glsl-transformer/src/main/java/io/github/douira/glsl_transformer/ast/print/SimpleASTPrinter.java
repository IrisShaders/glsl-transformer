package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public class SimpleASTPrinter extends ASTPrinter {
  private StringBuilder builder = new StringBuilder();

  @Override
  protected String generateString() {
    return builder.toString();
  }

  @Override
  protected void emitToken(PrintToken token) {
    builder.append(token.getContent());
  }
}
