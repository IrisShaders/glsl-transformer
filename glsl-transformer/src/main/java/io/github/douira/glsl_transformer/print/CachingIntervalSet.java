package io.github.douira.glsl_transformer.print;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;

import io.github.douira.glsl_transformer.print.filter.TokenFilter;

/**
 * The caching interval set is a regular interval set
 * {@link org.antlr.v4.runtime.misc.IntervalSet} but the @link
 * org.antlr.v4.runtime.misc.IntervalSet#contains(int)} method also does caching
 * of the queries if the set has been set to readonly. Repeatedly requesting the
 * same query is a common operation and therefore caching it like this can be
 * helpful.
 */
public class CachingIntervalSet extends IntervalSet implements TokenFilter {
  private Interval lastIntervalHit;

  /**
   * {@inheritDoc}
   * 
   * Copied from ANTLR's
   * {@link org.antlr.v4.runtime.misc.IntervalSet#contains(int)} but with an
   * addition of caching. The cache size is 1. If the interval set has been marked
   * as readonly, it will return the last hit if the query is the same.
   */
  @Override
  public boolean contains(int el) {
    // if readonly, then allow cache hits
    if (readonly && lastIntervalHit != null
        && lastIntervalHit.a <= el && lastIntervalHit.b >= el) {
      return true;
    }

    int n = intervals.size();
    int l = 0;
    int r = n - 1;
    // Binary search for the element in the (sorted,
    // disjoint) array of intervals.
    while (l <= r) {
      int m = (l + r) / 2;
      Interval I = intervals.get(m);
      int a = I.a;
      int b = I.b;
      if (b < el) {
        l = m + 1;
      } else if (a > el) {
        r = m - 1;
      } else { // now: el >= a && el <= b
        if (readonly) {
          lastIntervalHit = I;
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Adds the given interval to the set.
   * 
   * @param interval The interval to add to this interval set
   */
  public void add(Interval interval) {
    super.add(interval);
  }

  /**
   * Checks if the given token is covered by this set if it's being used as an
   * omission set. Tokens that are included in one of this interval set's sets and
   * aren't hidden are not printed.
   * 
   * @param token The token to check
   * @return {@code true} if the token should be printed
   */
  public boolean isTokenAllowed(Token token) {
    return token.getChannel() != Token.DEFAULT_CHANNEL || !contains(token.getTokenIndex());
  }
}
