package io.github.douira.glsl_transformer.print.filter;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;

/**
 * A list of the possible channels a token can have. This must correspond to the
 * generated Lexer's {@code channelNames} array.
 */
public enum TokenChannel {
  /**
   * The default channel
   */
  DEFAULT,

  /**
   * The default hidden channel
   */
  HIDDEN,

  /**
   * The custom whitespace channel
   */
  WHITESPACE,

  /**
   * The custom comments channel
   */
  COMMENTS,

  /**
   * The custom preprocessor channel
   */
  PREPROCESSOR;

  private static TokenChannel[] channels = TokenChannel.values();

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

  /**
   * Returns the channel enum for this token's integer channel number.
   * 
   * @param token The token to get the channel of
   * @return The channel of the token
   */
  public static TokenChannel getTokenChannel(Token token) {
    return TokenChannel.channels[token.getChannel()];
  }
}
