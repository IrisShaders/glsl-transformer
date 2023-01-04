package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;

/**
 * Inserts #line directives that make the compiler report errors as if they were
 * happening in the original source file.
 * 
 * The "#line line source" directive specifies the line and the source file. The
 * line number sets the line number of the line of the directive itself.
 */
public class LineAnnotator extends DelegateTokenProcessor {
  public LineAnnotator(TokenProcessor delegate) {
    super(delegate);
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token instanceof LineDirectiveMarker lineDirectiveMarker) {
      var location = lineDirectiveMarker.location;
      super.appendDirectly("#line " + Integer.toString(location.line)
          + (location.hasSource() ? " " + Integer.toString(location.source) : "") + "\n");
    } else {
      super.appendToken(token);
    }
  }
}
