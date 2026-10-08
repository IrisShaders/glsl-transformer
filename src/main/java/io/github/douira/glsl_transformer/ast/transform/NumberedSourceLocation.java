package io.github.douira.glsl_transformer.ast.transform;

public class NumberedSourceLocation extends PresentSourceLocation {
  public final int sourceNumber;

  public NumberedSourceLocation(int parsedLine, int line, int sourceNumber) {
    super(parsedLine, line);
    this.sourceNumber = sourceNumber;
  }

  @Override
  public boolean needsPrint(int printedLines, PresentSourceLocation lastPrinted, int lastLocationPrintedLines) {
    if (lastPrinted == null) {
      return true;
    }

    // check for potential hiding when permitted by the line numbers and sources are identical
    return !(printedLines + lastPrinted.line - lastLocationPrintedLines == line &&
        lastPrinted instanceof NumberedSourceLocation lastNumbered &&
        sourceNumber == lastNumbered.sourceNumber);
  }

  @Override
  public SourceLocation createFromPrevious(int parsedLine) {
    return new NumberedSourceLocation(parsedLine, line, sourceNumber);
  }

  @Override
  public SourceLocation createFromPrevious(int parsedLine, int line) {
    return new NumberedSourceLocation(parsedLine, line, sourceNumber);
  }
}
