package io.github.douira.glsl_transformer.print;

import org.antlr.v4.runtime.Token;

/**
 * A token filter is an object that can check if given tokens should be printed
 * or not.
 */
public interface TokenFilter {
  /**
   * Checks if the token should be printed.
   * 
   * @param token The token to check
   * @return {@code true} if the given token should be printed
   */
  public boolean isTokenAllowed(Token token);
}
