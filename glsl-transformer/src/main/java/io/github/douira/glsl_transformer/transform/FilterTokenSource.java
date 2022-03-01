package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;

import io.github.douira.glsl_transformer.print.filter.TokenFilter;

/**
 * The filter token source wraps another token source but reads tokens from it
 * until a given filter accepts one. When it encounters EOF it immediately
 * returns it.
 */
public class FilterTokenSource implements TokenSource {
  private TokenSource source;
  private TokenFilter<?> filter;

  /**
   * Creates a new filtering token source with a given token source to wrap.
   * 
   * @param tokenSource The real token source to get tokens from
   */
  public FilterTokenSource(TokenSource tokenSource) {
    source = tokenSource;
  }

  /**
   * Sets the token source on this filtering token source wrapper. May not be
   * {@code null}.
   * 
   * @param tokenSource The new token source
   */
  public void setTokenSource(TokenSource tokenSource) {
    source = tokenSource;
  }

  /**
   * Sets the token filter on this filtering token source wrapper. Set to
   * {@code null} to effectively disable any manipulation of the tokens generated
   * by the contained token source.
   * 
   * @param tokenFilter The new token filter
   */
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    filter = tokenFilter;
    filter.init();
  }

  /**
   * Uses {@link org.antlr.v4.runtime.TokenSource#nextToken()} on the real token
   * source to filter to get tokens and then filters them with the contained token
   * filter if there is one.
   * 
   * {@inheritDoc}
   */
  @Override
  public Token nextToken() {
    if (source == null) {
      throw new IllegalStateException("Missing a token source but a token was requested!");
    }

    if (filter == null) {
      return source.nextToken();
    }

    while (true) {
      var token = source.nextToken();
      if (token.getType() == Token.EOF) {
        return token;
      }
      if (filter.isTokenAllowed(token)) {
        return token;
      }
    }
  }

  @Override
  public int getLine() {
    return source.getLine();
  }

  @Override
  public int getCharPositionInLine() {
    return source.getCharPositionInLine();
  }

  @Override
  public CharStream getInputStream() {
    return source.getInputStream();
  }

  @Override
  public String getSourceName() {
    return source.getSourceName();
  }

  @Override
  public void setTokenFactory(TokenFactory<?> factory) {
    source.setTokenFactory(factory);
  }

  @Override
  public TokenFactory<?> getTokenFactory() {
    return source.getTokenFactory();
  }
}
