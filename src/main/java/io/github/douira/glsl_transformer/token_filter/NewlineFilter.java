package io.github.douira.glsl_transformer.token_filter;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.transform.JobParameters;

/**
 * The newline filter filters out regular unnecessary newlines if there is more
 * than one.
 */
public class NewlineFilter<J extends JobParameters> extends TokenFilter<J> {
  private boolean lastWasNewline;

  @Override
  public void resetState() {
    super.resetState();
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
