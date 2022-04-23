package io.github.douira.glsl_transformer.print;

import io.github.douira.glsl_transformer.tree.*;

/**
 * The empty terminal node is a placeholder in parse tree children lists in
 * order to prevent exceptions from being thrown because the parse tree walker
 * expects child lists not to change length during iteration. The print visitor
 * ignores it during printing because it's token is null.
 */
public class EmptyTerminalNode extends ExtendedTerminalNode {
  /**
   * Creates a new empty terminal node with a given previous node. This method is
   * not to be used productively as it is only for compactness in certain
   * unusual situations (like in tests).
   * 
   * @param previousNode The previous node to keep a reference to
   */
  public EmptyTerminalNode(TreeMember previousNode) {
    super(previousNode.getParent());
    setPreviousNode(previousNode);
  }

  /**
   * Creates a new empty terminal node. The previous node and parent node are set
   * when it is inserted as a child.
   */
  public EmptyTerminalNode() {
  }
}
