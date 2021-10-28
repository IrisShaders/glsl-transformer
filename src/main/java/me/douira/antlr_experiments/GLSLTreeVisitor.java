package me.douira.antlr_experiments;

import java.io.PrintStream;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.misc.Interval;

public class GLSLTreeVisitor extends GLSLBaseVisitor<String> {
  private PrintStream stream;

  public GLSLTreeVisitor(PrintStream stream) {
    this.stream = stream;
  }

  @Override
  public String visitChildren(RuleNode node) {
    var context = (ParserRuleContext) node;
    var prefix = " ".repeat((context.getPayload()).depth());
    stream.println(prefix + node.getPayload().getClass().getSimpleName());
    stream.println(prefix + node.getText());
    stream.println();

    return super.visitChildren(node);
  }
}
