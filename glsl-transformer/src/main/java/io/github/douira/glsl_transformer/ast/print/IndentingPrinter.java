package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;

public class IndentingPrinter extends DelegateTokenProcessor{
  private final char indent;
  private final int indentMultiplier;
  private int indentLevel = 0;
  private boolean indentationPrinted = false;

  public IndentingPrinter(TokenProcessor delegate, char indent, int indentMultiplier) {
    super(delegate);
    this.indent = indent;
    this.indentMultiplier = indentMultiplier;
  }

  public IndentingPrinter(TokenProcessor delegate) {
    this(delegate, '\t', 1);
  }

  public IndentingPrinter() {
    this(new SimplePrinter());
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token instanceof IndentMarker indentMarker) {
      indentLevel += indentMarker.indentDelta;
    } else if (!(token instanceof Marker)) {
      var isNewline = token.endsWithNewline();

      if (!indentationPrinted && !isNewline) {
        indentationPrinted = true;
        if (indentLevel > 0) {
          for (int i = 0, repeat = indentLevel * indentMultiplier; i < repeat; i++) {
            appendDirectly(indent);
          }
        }
      }

      if (isNewline) {
        indentationPrinted = false;
      }

      super.appendToken(token);
    }
  }
}
