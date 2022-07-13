package io.github.douira.glsl_transformer.cst.node;

/**
 * The unparsable CST node is basically just a wrapper around a terminal node
 * that contains a custom string. The only difference being that it's treated in
 * a special way by the printer. The string it produces with {@link #getText()}
 * is directly inserted instead of being passed through the print interval
 * system as a token.
 */
public abstract class UnparsableCSTNode extends TerminalCSTNode {
  @Override
  public String getText() {
    return getPrinted();
  }

  /**
   * If true, the printer will insert a newline before each group of consecutive
   * unparsable CST nodes.
   * 
   * @return If newline insertion should happen with this node
   */
  public boolean doNewlineInsertion() {
    return true;
  }
}
