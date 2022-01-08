package io.github.douira.glsl_transformer.print;

import java.util.EnumSet;

import org.antlr.v4.runtime.Token;

/**
 * The channel filter accepts all tokens that are not from a lexer channel that
 * is on the given list of disallowed channels.
 */
public class ChannelFilter implements TokenFilter {
  private EnumSet<TokenChannel> disallowedChannels;

  public ChannelFilter(EnumSet<TokenChannel> disallowedChannels) {
    this.disallowedChannels = disallowedChannels;
  }

  public ChannelFilter(TokenChannel disallowedChannel) {
    this(EnumSet.of(disallowedChannel));
  }

  @Override
  public boolean isTokenAllowed(Token token) {
    return !disallowedChannels.contains(TokenFilter.getTokenChannel(token));
  }
}
