package me.douira.antlr_experiments;

import java.io.PrintStream;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.misc.Interval;

public class GLSLTreeVisitor extends GLSLParserBaseVisitor<String> {
  private PrintStream stream;
  private int maxDepth;

  public GLSLTreeVisitor(PrintStream stream) {
    this(stream, Integer.MAX_VALUE);
  }

  public GLSLTreeVisitor(PrintStream stream, int maxDepth) {
    this.stream = stream;
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
    stream.println(prefix + "---" + name);

    // stream.println(prefix + node.getText());

    /*
     * Print the text that includes the tokens for this node. This does not print
     * modified parsed nodes but rather looks up where the current node came from
     * and then prints that part of the input stream.
     */
    var startToken = context.start;
    var stopToken = context.stop;
    if (stopToken == null) {
      stopToken = startToken;
    }
    int startIndex = startToken.getStartIndex();
    int stopIndex = stopToken.getStopIndex();
    if (startIndex <= stopIndex) {
      stream.println(prefix + startToken.getInputStream().getText(new Interval(startIndex, stopIndex)));
    } else {
      stream.println(prefix + "[Empty]");
    }

    stream.println();

    StringBuilder builder = new StringBuilder();
    int n = node.getChildCount();
    for (int i = 0; i < n; i++) {
      stream.println(prefix + i + " of " + n + " " + name);
      ParseTree c = node.getChild(i);
      stream.println(prefix + "|||" + c.getText());
      builder.append(c.accept(this));
    }

    return builder.toString();
  }

  @Override
  public String visitTerminal(TerminalNode node) {
    return node.getSymbol().getText();
  }
}
