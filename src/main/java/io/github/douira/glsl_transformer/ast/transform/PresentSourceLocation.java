package io.github.douira.glsl_transformer.ast.transform;

public class PresentSourceLocation extends SourceLocation {
  public final int line;

  public PresentSourceLocation(int parsedLine, int line) {
    super(parsedLine);
    this.line = line;
  }

  @Override
  public boolean canPrint() {
    return true;
  }

  public boolean needsPrint(int printedLines, PresentSourceLocation lastPrinted, int lastLocationPrintedLines) {
    // calculate which line number would be assigned to the next line if this line directive is not printed
    int notPrintNextLineResult;
    if (lastPrinted == null) {
      notPrintNextLineResult = printedLines + 1;
    } else {
      notPrintNextLineResult = printedLines + lastPrinted.line - lastLocationPrintedLines;
    }
    return notPrintNextLineResult != line;
  }

  @Override
  public SourceLocation createFromPrevious(int parsedLine) {
    return new PresentSourceLocation(parsedLine, line);
  }
}
