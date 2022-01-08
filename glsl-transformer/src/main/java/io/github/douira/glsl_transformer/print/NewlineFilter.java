package io.github.douira.glsl_transformer.print;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;

/**
 * The newline filter filters out regular unnecessary newlines if there is more
 * than one.
 */
public class NewlineFilter implements TokenFilter {
  private boolean lastWasNewline;

  @Override
  public void resetState() {
    lastWasNewline = false;
  }

  @Override
  public boolean isTokenAllowed(Token token) {
    var isNewline = token.getType() == GLSLLexer.EOL;
    var allowToken = !(lastWasNewline && isNewline);
    lastWasNewline = isNewline;
    return allowToken;
  }
}
