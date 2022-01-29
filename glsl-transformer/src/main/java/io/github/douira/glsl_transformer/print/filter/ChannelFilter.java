package io.github.douira.glsl_transformer.print.filter;

import java.util.EnumSet;

import org.antlr.v4.runtime.Token;

/**
 * The channel filter accepts all tokens that are not from a lexer channel that
 * is on the given list of disallowed channels.
 */
public class ChannelFilter<T> extends TokenFilter<T> {
  private final EnumSet<TokenChannel> disallowedChannels;

  /**
   * Creates a new channel filter with the given disallowed channels.
   * 
   * @param disallowedChannels The disallowed channels
   */
  public ChannelFilter(EnumSet<TokenChannel> disallowedChannels) {
    this.disallowedChannels = disallowedChannels;
  }

  /**
   * Creates a new channel filter with a single disallowed channel.
   * 
   * @param disallowedChannel The disallowed channel
   */
  public ChannelFilter(TokenChannel disallowedChannel) {
    this(EnumSet.of(disallowedChannel));
  }

  @Override
  public boolean isTokenAllowed(Token token) {
    return !disallowedChannels.contains(TokenChannel.getTokenChannel(token));
  }
}
