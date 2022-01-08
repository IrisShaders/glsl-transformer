package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The empty terminal node is a placeholder in parse tree children lists in
 * order to prevent exceptions from being thrown because the parse tree walker
 * expects child lists not to change length during iteration. The print visitor
 * ignores it during printing because it's token is null.
 */
public class EmptyTerminalNode extends ExtendedTerminalNode implements MoveCheckable {
  private ParseTree previousNode;

  /**
   * Creates a new empty terminal node with a given previous node.
   * 
   * @param previousNode The previous node to keep a reference to
   */
  public EmptyTerminalNode(TreeMember previousNode) {
    super(previousNode.getParent());
    this.previousNode = previousNode;
  }

  @Override
  public boolean replacesNode(ParseTree node) {
    return previousNode == node;
  }
}
