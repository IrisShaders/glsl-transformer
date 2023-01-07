package io.github.douira.glsl_transformer.ast.transform;

/**
 * Immutable class representing a source location.
 */
public class SourceLocation {
  public static final int NONE = -1;
  public static final SourceLocation PLACEHOLDER = new SourceLocation();

  public final int line;
  public final int source;

  public SourceLocation(int line, int source) {
    this.line = line;
    this.source = source;
  }

  public SourceLocation(int line) {
    this(line, NONE);
  }

  public SourceLocation() {
    this(NONE);
  }

  public static SourceLocation fromPrevious(SourceLocation previous, int line) {
    if (previous == null) {
      return new SourceLocation(line);
    }
    if (previous.line == line) {
      return previous;
    }
    return new SourceLocation(line, previous.source);
  }

  public static SourceLocation fromPrevious(SourceLocation previous, int line, int source) {
    if (previous == null) {
      return new SourceLocation(line, source);
    }
    if (previous.line == line && previous.source == source) {
      return previous;
    }
    return new SourceLocation(line, source == NONE ? previous.source : source);
  }

  public boolean hasLine() {
    return line != NONE;
  }

  public boolean hasSource() {
    return source != NONE;
  }

  public String toLineDirective() {
    return "#line " + line + (hasSource() ? " " + source : "") + "\n";
  }
}
