package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;

public class CachingIntervalSet extends IntervalSet {
  protected Interval lastIntervalHit;

  /**
   * Copied from ANTLR's
   * {@link org.antlr.v4.runtime.misc.IntervalSet#contains(int)} but with an
   * addition of caching. The cache size is 1. If the interval set has been marked
   * as readonly, it will return the last hit if the query is the same. Repeatedly
   * requesting the same query is a common operation and therefore caching it like
   * this can be helpful.
   */
  @Override
  public boolean contains(int el) {
    // if readonly, then allow cache hits
    if (readonly && lastIntervalHit != null && lastIntervalHit.a <= el && lastIntervalHit.b >= el) {
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
      } else { // el >= a && el <= b
        if (readonly) {
          lastIntervalHit = I;
        }
        return true;
      }
    }
    return false;
  }

  public boolean isTokenAllowed(Token token) {
    return token.getChannel() != Token.DEFAULT_CHANNEL || !contains(token.getTokenIndex());
  }
}
