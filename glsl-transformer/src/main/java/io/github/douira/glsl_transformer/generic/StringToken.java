package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

/**
 * The string token allows the creation of a custom token with any string as the
 * token's content.
 */
public class StringToken extends CommonToken {
  /**
   * Creates a new string token with a given string.
   * 
   * @param text The string to make a token out of
   */
  public StringToken(String text) {
    // type 0 because -1 would make it count as EOF and never get printed
    super(Token.INVALID_TYPE, text);
  }
}
