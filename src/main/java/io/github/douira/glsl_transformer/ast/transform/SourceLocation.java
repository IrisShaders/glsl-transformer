package io.github.douira.glsl_transformer.ast.transform;

/**
 * Immutable class representing a source location.
 */
public class SourceLocation {
  public final int parsedLine; // line within the actual parsed source

  public SourceLocation(int parsedLine) {
    this.parsedLine = parsedLine;
  }

  public boolean canPrint() {
    return false;
  }

  public SourceLocation createFromPrevious(int parsedLine) {
    return new SourceLocation(parsedLine);
  }

  public SourceLocation createFromPrevious(int parsedLine, int line) {
    return new PresentSourceLocation(parsedLine, line);
  }

  public SourceLocation createFromPrevious(int parsedLine, int line, int source) {
    return new NumberedSourceLocation(parsedLine, line, source);
  }

  public SourceLocation createFromPrevious(int parsedLine, int line, String sourceName) {
    return new NamedSourceLocation(parsedLine, line, sourceName);
  }
}
