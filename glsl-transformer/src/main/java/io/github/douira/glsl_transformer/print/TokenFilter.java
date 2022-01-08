package io.github.douira.glsl_transformer.print;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;

/**
 * A token filter is an object that can check if given tokens should be printed
 * or not.
 */
public interface TokenFilter {
  /**
   * A list of the possible channels a token can have. This must correspond to the
   * generated Lexer's {@code channelNames} array.
   */
  public static enum TokenChannel {
    DEFAULT,
    HIDDEN,
    WHITESPACE,
    COMMENTS,
    PREPROCESSOR;

    public static TokenChannel[] channels = TokenChannel.values();

    // sanity check the items and their names
    static {
      if (channels[Token.DEFAULT_CHANNEL] != TokenChannel.DEFAULT) {
        throw new Error("The default channel position should match ANTLR's convention!");
      }
      if (channels[Token.HIDDEN_CHANNEL] != TokenChannel.HIDDEN) {
        throw new Error("The hidden channel position should match ANTLR's convention!");
      }
      for (var i = Token.MIN_USER_CHANNEL_VALUE; i < channels.length; i++) {
        var enumName = channels[i].name();
        var generatedName = GLSLLexer.channelNames[i];
        if (!enumName.equals(generatedName)) {
          throw new Error("The channel with name " + enumName + " at position " + i
              + " has to match the corresponding generated channel name "
              + generatedName + "!");
        }
      }
    }
  }

  /**
   * Returns the channel enum for this token's integer channel number.
   * 
   * @param token The token to get the channel of
   * @return The channel of the token
   */
  static TokenChannel getTokenChannel(Token token) {
    return TokenChannel.channels[token.getChannel()];
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
