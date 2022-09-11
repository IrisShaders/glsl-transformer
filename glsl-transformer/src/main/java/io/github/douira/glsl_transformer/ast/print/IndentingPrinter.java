package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;

public class IndentingPrinter extends DelegateTokenProcessor<SimplePrinter> {
  private final char indent;
  private final int indentMultiplier;
  private int indentLevel = 0;
  private boolean indentationPrinted = false;

  public IndentingPrinter(SimplePrinter delegate, char indent, int indentMultiplier) {
    super(delegate);
    this.indent = indent;
    this.indentMultiplier = indentMultiplier;
  }

  public IndentingPrinter() {
    this(new SimplePrinter(), '\t', 1);
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token instanceof IndentMarker indentMarker) {
      indentLevel += indentMarker.indentDelta;
    } else if (!(token instanceof Marker)) {
      var isNewline = token.getContent().endsWith("\n");

      if (!indentationPrinted && !isNewline) {
        indentationPrinted = true;
        if (indentLevel > 0) {
          var builder = delegate.getBuilder();
          for (int i = 0, repeat = indentLevel * indentMultiplier; i < repeat; i++) {
            builder.append(indent);
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
