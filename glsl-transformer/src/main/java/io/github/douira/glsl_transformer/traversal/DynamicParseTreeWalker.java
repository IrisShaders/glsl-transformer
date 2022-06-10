package io.github.douira.glsl_transformer.traversal;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.print.EmptyTerminalNode;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The dynamic parse tree walker can handle some structural modification of a
 * node's child array. This enables injection of new nodes without disallowing
 * such modifications to happen during tree walking.
 */
public class DynamicParseTreeWalker extends ParseTreeWalker {
  private int depth = -1;

  public static void walkTree(ParseTreeListener listener, ParseTree tree) {
    new DynamicParseTreeWalker().walk(listener, tree);
  }

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
    depth++;

    if (tree instanceof ErrorNode errorNode) {
      listener.visitErrorNode(errorNode);
      return;
    } else if (tree instanceof TerminalNode terminalNode) {
      listener.visitTerminal(terminalNode);
      return;
    }

    var node = (ExtendedContext) tree;
    enterRule(listener, node);

    if (!(listener instanceof PartialParseTreeListener partialListener
        && (partialListener.isFinished(depth) || partialListener.isDeepEnough(node, depth)))) {
      for (var i = 0; i < node.getChildCount(); i++) {
        var child = node.getChild(i);
        if (child instanceof EmptyTerminalNode) {
          continue;
        }

        walk(listener, child);

        // if the walk added items before the current index
        // then the current item was moved forwards.
        while (!MoveCheckable.replaces(child, node.getChild(i))) {
          i++;
        }

        if (listener instanceof PartialParseTreeListener partialListener && partialListener.isFinished(depth)) {
          break;
        }
      }
    }

    // compact the tree by removing empty terminal nodes after walking
    if (node.children != null) {
      node.children.removeIf(child -> child instanceof EmptyTerminalNode);
    }

    exitRule(listener, node);
    depth--;
  }
}
