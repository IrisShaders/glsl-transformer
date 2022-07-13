package io.github.douira.glsl_transformer.cst.token_filter;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

/**
 * The newline filter filters out regular unnecessary newlines if there is more
 * than one.
 */
public class NewlineFilter<T extends JobParameters> extends TokenFilter<T> {
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
