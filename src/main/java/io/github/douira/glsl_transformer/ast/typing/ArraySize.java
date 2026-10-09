package io.github.douira.glsl_transformer.ast.typing;

/**
 * The size of one dimension of an array type. The size is either known, the
 * array is declared unsized ({@code float a[]}), or the size is given by an
 * expression the value of which could not be determined.
 *
 * @param kind   the kind of size
 * @param length the number of elements for known sizes, 0 otherwise
 */
public record ArraySize(Kind kind, int length) {
  public enum Kind {
    KNOWN,
    UNSIZED,
    UNKNOWN
  }

  public static final ArraySize UNSIZED = new ArraySize(Kind.UNSIZED, 0);
  public static final ArraySize UNKNOWN = new ArraySize(Kind.UNKNOWN, 0);

  public static ArraySize of(int length) {
    return new ArraySize(Kind.KNOWN, length);
  }

  public boolean isKnown() {
    return kind == Kind.KNOWN;
  }

  /**
   * Returns whether an array of this size can be used where an array of the
   * other size is expected. Sizes are only incompatible if both are known and
   * different.
   *
   * @param other the other size
   * @return true if the sizes are compatible
   */
  public boolean isCompatibleWith(ArraySize other) {
    return !isKnown() || !other.isKnown() || length == other.length;
  }

  @Override
  public String toString() {
    return isKnown() ? Integer.toString(length) : kind == Kind.UNSIZED ? "" : "?";
  }
}
