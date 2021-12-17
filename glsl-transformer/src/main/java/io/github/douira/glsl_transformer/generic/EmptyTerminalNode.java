package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * The empty terminal node is a placeholder in parse tree children lists in
 * order to prevent exceptions from being thrown because the parse tree walker
 * expects child lists not to change length during iteration. The print visitor
 * ignores it during printing because it's token is null.
 */
public class EmptyTerminalNode extends TerminalNodeImpl implements MoveCheckable {
  private ParseTree previousNode;

  /**
   * Creates a new empty terminal node.
   */
  public EmptyTerminalNode() {
    super(null);
  }

  /**
   * Creates a new empty terminal node with a given previous node.
   * 
   * @param previousNode The previous node to keep a reference to
   */
  public EmptyTerminalNode(ParseTree previousNode) {
    this();
    this.previousNode = previousNode;
  }

  @Override
  public boolean replacesNode(ParseTree node) {
    return previousNode == node;
  }
}
