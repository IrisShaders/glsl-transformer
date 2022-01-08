package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.generic.MoveCheckable;

/**
 * The dynamic parse tree walker can with structural modification of a node's
 * child array. This enables injection of new nodes without disallowing such
 * modifications to happen during tree walking.
 */
public class DynamicParseTreeWalker extends ParseTreeWalker {
  /**
   * The default instance of the dynamic parse tree walker.
   */
  public static final DynamicParseTreeWalker DEFAULT = new DynamicParseTreeWalker();

  /**
   * {@inheritDoc}
   * 
   * Copied from ANTLR's
   * {@link org.antlr.v4.runtime.tree.ParseTreeWalker#walk(org.antlr.v4.runtime.tree.ParseTreeListener, org.antlr.v4.runtime.tree.ParseTree)}
   * but with compensation for items being added to the child array.
   * 
   * TODO: Check if using empty terminal nodes in general messes up ANTLR's parse
   * tree matching (with patterns or xpath)
   * 
   * @implNote Node removal never reduces the length of the array so "removal" is
   *           not an issue. (since removed nodes are instead replaced with empty
   *           terminal nodes) Node addition after the current position is ok
   *           since the length is dynamically determined in the loop. The only
   *           problematic thing is node addition before the current position for
   *           which the current iteration index needs to be compensated for. This
   *           is done by fast-forwarding the iteration index after the walk
   *           happened until the current node is found again.
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
      var child = ruleNode.getChild(i);
      walk(listener, child);

      // if the walk added items before the current index
      // then the current item was moved forewards.
      while (!MoveCheckable.replaces(child, ruleNode.getChild(i))) {
        i++;
      }
    }

    exitRule(listener, ruleNode);
  }
}
