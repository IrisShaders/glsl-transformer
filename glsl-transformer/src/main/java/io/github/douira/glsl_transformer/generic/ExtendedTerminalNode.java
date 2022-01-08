package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class ExtendedTerminalNode extends TerminalNodeImpl implements TreeMember {
  public ExtendedTerminalNode(ParserRuleContext parent, Token symbol) {
    super(symbol);
    setParent(parent);
  }

  protected ExtendedTerminalNode(ExtendedContext parent) {
    this(parent, null);
  }

  /** Override to make type more specific */
  @Override
  public ExtendedContext getParent() {
    return (ExtendedContext) super.getParent();
  }

  @Override
  public void omitTokens() {
    var parent = getParent();
    if (parent == null) {
      throw new IllegalStateException(
          "A terminal node can't be a local root node and therefore can't be the root node. It must have a parent node but null was found!");
    }

    parent.omitTokens(getSourceInterval());
  }
}
