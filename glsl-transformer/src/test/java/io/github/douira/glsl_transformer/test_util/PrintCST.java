package io.github.douira.glsl_transformer.test_util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.GLSLParserBaseListener;

public abstract class PrintCST extends GLSLParserBaseListener {
  int depth = 0;
  public StringBuilder builder = new StringBuilder();

  private static final int nameSuffixLength = "Context".length();

  protected String getRuleName(ParserRuleContext ctx) {
    var name = ctx.getClass().getSimpleName();
    return name.substring(0, name.length() - nameSuffixLength);
  }

  protected String getTerminalContent(TerminalNode node) {
    return node.toString().replace("{", "{    \\}");
  }

  protected void processRuleToggle(ParserRuleContext ctx) {
    builder.append(getRuleName(ctx));
    builder.append('\n');
  }

  public void processEnterRule(ParserRuleContext ctx) {
    processRuleToggle(ctx);
  }

  public void processExitRule(ParserRuleContext ctx) {
    processRuleToggle(ctx);
  }

  public void processVisitTerminal(TerminalNode node) {
    // this replacement makes syntax highlighting of the snapshot file not as bad
    builder.append(getTerminalContent(node));
    builder.append('\n');
  }

  public void processVisitErrorNode(ErrorNode node) {
    builder.append("!ERROR");
    builder.append('\n');
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    processEnterRule(ctx);
    depth++;
  }

  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    depth--;
    processExitRule(ctx);
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    processVisitTerminal(node);
  }

  @Override
  public void visitErrorNode(ErrorNode node) {
    processVisitErrorNode(node);
  }
}
