package me.douira.antlr_experiments;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;

public class CachingIntervalSet extends IntervalSet {
  protected Interval lastIntervalHit;

  /**
   * Copied from ANTLR's IntervalSet.contains but with an addition of caching.
   */
  @Override
  public boolean contains(int el) {
    //if readonly, then allow cache hits
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
}
