package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

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

  public LineAnnotator() {
    super(new SimplePrinter());
  }

  @Override
  public void appendToken(PrintToken token) {
    // TODO: use the source location object that some nodes may have to emit #line
    // before the node
  }
}
