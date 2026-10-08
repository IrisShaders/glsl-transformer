package io.github.douira.glsl_transformer.ast.transform;

public class NamedSourceLocation extends PresentSourceLocation {
  public final String sourceName;

  public NamedSourceLocation(int parsedLine, int line, String sourceName) {
    super(parsedLine, line);
    this.sourceName = sourceName;
  }

  @Override
  public boolean needsPrint(int printedLines, PresentSourceLocation lastPrinted, int lastLocationPrintedLines) {
    if (lastPrinted == null) {
      return true;
    }

    // check for potential hiding when permitted by the line numbers and sources are identical
    return !(printedLines + lastPrinted.line - lastLocationPrintedLines == line &&
        lastPrinted instanceof NamedSourceLocation lastNamed &&
        sourceName.equals(lastNamed.sourceName));
  }

  @Override
  public SourceLocation createFromPrevious(int parsedLine) {
    return new NamedSourceLocation(parsedLine, line, sourceName);
  }

  @Override
  public SourceLocation createFromPrevious(int parsedLine, int line) {
    return new NamedSourceLocation(parsedLine, line, sourceName);
  }
}
