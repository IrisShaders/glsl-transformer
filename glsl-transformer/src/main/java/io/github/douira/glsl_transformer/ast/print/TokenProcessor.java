package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

public interface TokenProcessor {
  String generateString();

  void appendToken(PrintToken token);

  void appendDirectly(String content);

  void appendDirectly(char content);
}
