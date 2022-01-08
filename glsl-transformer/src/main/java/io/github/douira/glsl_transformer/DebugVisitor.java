package io.github.douira.glsl_transformer;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * The debug visitor prints the tree and some other information to the console.
 */
public class DebugVisitor extends GLSLParserBaseVisitor<Void> {
  private int maxDepth;

  /**
   * Creates a new debug visitor
   */
  public DebugVisitor() {
    this(Integer.MAX_VALUE);
  }

  /**
   * Creates a new debug visitor but with a maximum tree printing depth.
   * 
   * @param maxDepth The maximum depth to which the tree will be printed
   */
  public DebugVisitor(int maxDepth) {
    this.maxDepth = maxDepth;
  }

  @Override
  public Void visitChildren(RuleNode node) {
    var context = (ExtendedContext) node.getRuleContext();

    var depth = (context.getPayload()).depth();

    if (depth > maxDepth) {
      return null;
    }

    // indent with the tree depth
    var prefix = CompatUtil.repeat(". ", depth);
    int n = node.getChildCount();

    // print the name of the node
    var name = node.getPayload().getClass().getSimpleName();
    if (n > 1 || name.indexOf("Expression") == -1) {
      System.out.println(prefix + "---" + name);
    }

    for (int i = 0; i < n; i++) {
      ParseTree c = node.getChild(i);

      if (n > 1) {
        System.out.println(prefix + i + "/" + n + " in " + name);
        System.out.println(prefix + c.getText());
      }

      if (c instanceof TerminalNode terminal) {
        System.out.println(prefix + terminal.getSymbol().getText());
      }

      c.accept(this);
    }

    return null;
  }
}
