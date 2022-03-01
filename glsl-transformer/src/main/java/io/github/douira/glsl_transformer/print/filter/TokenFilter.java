package io.github.douira.glsl_transformer.print.filter;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.transform.LifecycleUserImpl;

/**
 * A token filter is an object that can check if given tokens should be printed
 * or not.
 */
public abstract class TokenFilter<T> extends LifecycleUserImpl<T> {

  /**
   * Checks if the token should be printed.
   * 
   * @param token The token to check
   * @return {@code true} if the given token should be printed
   */
  public abstract boolean isTokenAllowed(Token token);

  /**
   * Resets the filter's state. Does nothing by default.
   */
  public void resetState() {
  }

  /**
   * Joins two arbitrary token filters into a new filter. They may be null,
   * regular filters or multi filters and will be joined accordingly. The returned
   * instance is either a or b or a new multi filter containing a, b, or their
   * contents.
   * 
   * If a multi filter is generated, the settings from the first multi filter in
   * the parameters are copied.
   * 
   * @param <T> The job parameter type
   * @param a   A token filter. May be {@code null}.
   * @param b   A token filter. May be {@code null}.
   * @return The joined token filter
   */
  public static <T> TokenFilter<T> join(TokenFilter<T> a, TokenFilter<T> b) {
    if (a == null) {
      return b;
    } else if (b == null) {
      return a;
    } else if (MultiFilter.class.isInstance(b)) {
      if (MultiFilter.class.isInstance(a)) {
        MultiFilter<T> bMulti = (MultiFilter<T>) b;
        MultiFilter<T> aMulti = (MultiFilter<T>) a;
        var multi = aMulti.clone();
        multi.addAll(bMulti);
        return multi;
      } else {
        return join(b, a);
      }
    } else if (MultiFilter.class.isInstance(a)) {
      MultiFilter<T> aMulti = (MultiFilter<T>) a;
      var multi = aMulti.clone();
      multi.add(b);
      return multi;
    } else {
      var multi = new MultiFilter<T>();
      multi.add(a);
      multi.add(b);
      return multi;
    }
  }
}
