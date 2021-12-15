package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The dynamic parse tree walker can with structural modification of a node's
 * child array. This enables injection of new nodes without disallowing such
 * modifications to happen during tree walking.
 */
public class DynamicParseTreeWalker extends ParseTreeWalker {
  public static final DynamicParseTreeWalker DEFAULT = new DynamicParseTreeWalker();

  /**
   * {@inheritDoc}
   * 
   * Copied from ANTLR's
   * {@link org.antlr.v4.runtime.tree.ParseTreeWalker#walk(org.antlr.v4.runtime.tree.ParseTreeListener, org.antlr.v4.runtime.tree.ParseTree)}
   * but with smart detection of structural modifications to the child array.
   * 
   * TODO: Implement detection and adaptation
   */
  @Override
  public void walk(ParseTreeListener listener, ParseTree tree) {
    if (tree instanceof ErrorNode errorNode) {
      listener.visitErrorNode(errorNode);
      return;
    } else if (tree instanceof TerminalNode terminalNode) {
      listener.visitTerminal(terminalNode);
      return;
    }

    var ruleNode = (RuleNode) tree;
    enterRule(listener, ruleNode);

    for (var i = 0; i < ruleNode.getChildCount(); i++) {
      walk(listener, ruleNode.getChild(i));
    }

    /*NOTES:
    node removal never reduces the length of the array so "removal" is not an issue.
    node addition after the current position is ok since the length is dynamically determined.
    the only problematic thing is node addition before the current position
    for which the current iteration index needs to be compensated for.
    */

    exitRule(listener, ruleNode);
  }
}
