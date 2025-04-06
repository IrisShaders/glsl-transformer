package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.LineDirectiveMarker;
import io.github.douira.glsl_transformer.ast.print.token.LiteralToken;
import io.github.douira.glsl_transformer.ast.print.token.PrintToken;
import io.github.douira.glsl_transformer.ast.transform.NamedSourceLocation;
import io.github.douira.glsl_transformer.ast.transform.NumberedSourceLocation;
import io.github.douira.glsl_transformer.ast.transform.PresentSourceLocation;

// TODO: handles some cases weirdly and results in line directive being appended after some code
/**
 * Inserts #line directives that make the compiler report errors as if they were
 * happening in the original source file.
 * <p>
 * The "#line line source" directive specifies the line and the source file. The
 * line number sets the line number of the line of the directive itself.
 */
public class LineAnnotator extends DelegateTokenProcessor {
  private int printedLines = 0;
  private PresentSourceLocation lastPrintedLocation;
  private int lastLocationPrintedLines = 0;

  public LineAnnotator(TokenProcessor delegate) {
    super(delegate);
  }

  private void incrementLines() {
    printedLines++;
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token instanceof LineDirectiveMarker lineDirectiveMarker) {
      var location = lineDirectiveMarker.location;

      if (!location.needsPrint(printedLines, lastPrintedLocation, lastLocationPrintedLines)) {
        return;
      }

      super.appendToken(new LiteralToken("#line "));
      super.appendToken(new LiteralToken(Integer.toString(location.line)));
      if (location instanceof NumberedSourceLocation numbered) {
        super.appendToken(new LiteralToken(" "));
        super.appendToken(new LiteralToken(Integer.toString(numbered.sourceNumber)));
      } else if (location instanceof NamedSourceLocation named) {
        super.appendToken(new LiteralToken(" \""));
        super.appendToken(new LiteralToken(named.sourceName));
        super.appendToken(new LiteralToken("\""));
      }
      super.appendToken(new LiteralToken("\n"));

      incrementLines();

      lastPrintedLocation = location;
      lastLocationPrintedLines = printedLines;
    } else {
      if (token.endsWithNewline()) {
        incrementLines();
      }
      super.appendToken(token);
    }
  }
}
