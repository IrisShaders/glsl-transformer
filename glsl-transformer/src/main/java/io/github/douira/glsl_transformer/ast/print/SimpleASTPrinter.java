package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;

public class SimpleASTPrinter extends ASTPrinter {
  protected StringBuilder builder = new StringBuilder();

  @Override
  protected String generateString() {
    return builder.toString();
  }

  @Override
  protected void appendToken(PrintToken token) {
    var content = token.getContent();
    if (content != null) {
      builder.append(content);
    }
  }
}
