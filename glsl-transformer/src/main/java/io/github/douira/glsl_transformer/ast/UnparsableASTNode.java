package io.github.douira.glsl_transformer.ast;

/**
 * The unparsable AST node is basically just a wrapper around a terminal node
 * that contains a custom string. The only difference being that it's treated in
 * a special way by the printer. The string it produces with {@link #getText()}
 * is directly inserted instead of being passed through the print interval
 * system as a token.
 */
public abstract class UnparsableASTNode extends ASTNode {
  @Override
  public String getText() {
    return getPrinted();
  }
}
