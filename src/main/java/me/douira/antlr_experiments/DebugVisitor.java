package me.douira.antlr_experiments;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.misc.Interval;

public class DebugVisitor extends GLSLParserBaseVisitor<String> {
  private int maxDepth;

  public DebugVisitor() {
    this(Integer.MAX_VALUE);
  }

  public DebugVisitor(int maxDepth) {
    this.maxDepth = maxDepth;
  }

  @Override
  public String visitChildren(RuleNode node) {
    var context = (ParserRuleContext) node.getRuleContext();

    var depth = (context.getPayload()).depth();

    if (depth > maxDepth) {
      return null;
    }

    // indent with the tree depth
    var prefix = ". ".repeat(depth);

    // print the name of the node
    var name = node.getPayload().getClass().getSimpleName();
    System.out.println(prefix + "---" + name);

    // System.out.println(prefix + node.getText());

    /*
     * Print the text that includes the tokens for this node. This does not print
     * modified parsed nodes but rather looks up where the current node came from
     * and then prints that part of the input System.out.
     */
    var startToken = context.start;
    var stopToken = context.stop;
    if (stopToken == null) {
      stopToken = startToken;
    }
    int startIndex = startToken.getStartIndex();
    int stopIndex = stopToken.getStopIndex();
    if (startIndex <= stopIndex) {
      System.out.println(prefix + startToken.getInputStream().getText(Interval.of(startIndex, stopIndex)));
    } else {
      System.out.println(prefix + "[Empty]");
    }

    System.out.println();

    StringBuilder builder = new StringBuilder();
    int n = node.getChildCount();
    for (int i = 0; i < n; i++) {
      System.out.println(prefix + i + " of " + n + " " + name);
      ParseTree c = node.getChild(i);
      System.out.println(prefix + "|||" + c.getText());
      builder.append(c.accept(this));
    }

    return builder.toString();
  }

  @Override
  public String visitTerminal(TerminalNode node) {
    return node.getSymbol().getText();
  }
}
