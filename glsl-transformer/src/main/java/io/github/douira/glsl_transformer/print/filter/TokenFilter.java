package io.github.douira.glsl_transformer.print.filter;

import org.antlr.v4.runtime.Token;

/**
 * A token filter is an object that can check if given tokens should be printed
 * or not.
 */
public abstract class TokenFilter {
  /**
   * Joins two arbitrary token filters into a new filter. They may be null,
   * regular filters or multi filters and will be joined accordingly. The returned
   * instance is either a or b or a new multi filter containing a, b, or their
   * contents.
   * 
   * If a multi filter is generated, the settings from the first multi filter in
   * the parameters are copied.
   * 
   * @param a A token filter. May be {@code null}.
   * @param b A token filter. May be {@code null}.
   * @return The joined token filter
   */
  public static TokenFilter join(TokenFilter a, TokenFilter b) {
    if (a == null) {
      return b;
    } else if (b == null) {
      return a;
    } else if (b instanceof MultiFilter bMulti) {
      if (a instanceof MultiFilter aMulti) {
        var multi = aMulti.clone();
        multi.addAll(bMulti);
        return multi;
      } else {
        return join(b, a);
      }
    } else if (a instanceof MultiFilter aMulti) {
      var multi = aMulti.clone();
      multi.add(b);
      return multi;
    } else {
      var multi = new MultiFilter();
      multi.add(a);
      multi.add(b);
      return multi;
    }
  }

  /**
   * Resets the filter's state. Does nothing by default.
   */
  public void resetState() {
  }

  /**
   * Checks if the token should be printed.
   * 
   * @param token The token to check
   * @return {@code true} if the given token should be printed
   */
  public abstract boolean isTokenAllowed(Token token);
}
