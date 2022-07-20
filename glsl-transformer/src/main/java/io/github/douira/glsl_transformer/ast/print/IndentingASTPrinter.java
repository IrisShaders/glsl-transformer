package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;

public class IndentingASTPrinter extends SimpleASTPrinter {
  private final char indent;
  private final int indentMultiplier;
  private int indentLevel = 0;
  private boolean indentationPrinted = false;

  public IndentingASTPrinter(char indent, int indentMultiplier) {
    this.indent = indent;
    this.indentMultiplier = indentMultiplier;
  }

  public IndentingASTPrinter() {
    this('\t', 1);
  }

  @Override
  protected void appendToken(PrintToken token) {
    if (token instanceof IndentMarker indentMarker) {
      indentLevel += indentMarker.indentDelta;
    } else {
      var content = token.getContent();
      var isNewline = content != null && content.endsWith("\n");

      if (!indentationPrinted && !isNewline) {
        indentationPrinted = true;
        if (indentLevel > 0) {
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
