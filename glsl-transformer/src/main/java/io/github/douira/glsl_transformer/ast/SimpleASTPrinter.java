package io.github.douira.glsl_transformer.ast;

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
