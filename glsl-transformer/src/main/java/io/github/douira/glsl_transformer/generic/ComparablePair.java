package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.misc.Pair;

/**
 * The comparable pair can be compared to another comparable pair. They are
 * compared by first looking at any difference in A and then any difference in
 * B. This means the comparison behaves lexicographically. Both components are
 * expected to not be null.
 */
public class ComparablePair<A extends Comparable<A>, B extends Comparable<B>>
    extends Pair<A, B> implements Comparable<ComparablePair<A, B>> {
  /**
   * Creates a new comparable pair with the given parts.
   * 
   * @param a The first part
   * @param b The second part
   */
  public ComparablePair(A a, B b) {
    super(a, b);
  }

  @Override
  public int compareTo(ComparablePair<A, B> o) {
    var resultA = a.compareTo(o.a);
    if (resultA == 0) {
      return b.compareTo(o.b);
    } else {
      return resultA;
    }
  }
}
