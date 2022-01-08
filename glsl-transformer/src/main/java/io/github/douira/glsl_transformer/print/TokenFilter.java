package io.github.douira.glsl_transformer.print;

import org.antlr.v4.runtime.Token;

/**
 * A token filter is an object that can check if given tokens should be printed
 * or not.
 */
public interface TokenFilter {
  /**
   * A list of the possible channels a token can have. This must correspond to the
   * generated Lexer's {@code channelNames} array.
   */
  static enum TokenChannel {
    DEFAULT,
    HIDDEN,
    WHITESPACE,
    COMMENTS,
    PREPROCESSOR;
  }

  /**
   * Returns the channel enum for this token's integer channel number.
   * 
   * @param token The token to get the channel of
   * @return The channel of the token
   */
  static TokenChannel getTokenChannel(Token token) {
    return TokenChannel.values()[token.getChannel()];
  }

  /**
   * Resets the filter's state. Does nothing by default.
   */
  default public void resetState() {
  }

  /**
   * Checks if the token should be printed.
   * 
   * @param token The token to check
   * @return {@code true} if the given token should be printed
   */
  public boolean isTokenAllowed(Token token);
}
