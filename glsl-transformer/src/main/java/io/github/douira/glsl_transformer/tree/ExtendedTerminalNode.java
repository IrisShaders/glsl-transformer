package io.github.douira.glsl_transformer.tree;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * The extended terminal node has additional functionality over the regular
 * terminal node. Most importantly, it implements the functionality required to
 * be a tree member.
 */
public class ExtendedTerminalNode extends TerminalNodeImpl implements TreeMember {
  private ParseTree previousNode;

  /**
   * Creates a new extended terminal node with a parent node and a token as the
   * symbol.
   * 
   * @param parent The parent node. This is a parser rule context because the
   *               parser passes a parser rule context when it creates new
   *               terminal nodes.
   * @param symbol The token to contain with this terminal node
   */
  public ExtendedTerminalNode(ParserRuleContext parent, Token symbol) {
    super(symbol);
    setParent(parent);
  }

  /**
   * Creates a null-token terminal node that has an extended context as a parent.
   * This should only be used when the node is a placeholder for something. It
   * will not be printed since the token is null.
   * 
   * @param parent The parent node
   */
  public ExtendedTerminalNode(ExtendedContext parent) {
    this(parent, null);
  }

  /**
   * Creates a null-token and parentless terminal node. This is to be used when
   * the node is a placeholder and it will be added to a parse tree node. Adding
   * it as a child using a node's addChild method will attach it as a parent.
   */
  public ExtendedTerminalNode() {
    this(null);
  }

  /** Override to make type more specific */
  @Override
  public ExtendedContext getParent() {
    return (ExtendedContext) super.getParent();
  }

  @Override
  public void processRemoval() {
    var parent = getParent();
    if (parent == null) {
      throw new IllegalStateException(
          "A terminal node can't be a local root node and therefore can't be the root node. It must have a parent node but null was found!");
    }

    parent.processRemoval(getSourceInterval());
  }

  @Override
  public ParseTree getPreviousNode() {
    return previousNode;
  }

  @Override
  public void setPreviousNode(ParseTree previousNode) {
    this.previousNode = previousNode;
  }
}
