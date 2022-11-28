package io.github.douira.glsl_transformer.test_util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.cst.transform.WalkPhase;
import io.github.douira.glsl_transformer.job_parameter.WrappedParameters;

public abstract class PrintCST extends WalkPhase<WrappedParameters<StringBuilder>> {
  int depth;

  private static final int nameSuffixLength = "Context".length();

  protected String getRuleName(ParserRuleContext ctx) {
    var name = ctx.getClass().getSimpleName();
    return name.substring(0, name.length() - nameSuffixLength);
  }

  protected String getTerminalContent(TerminalNode node) {
    return node.toString().replace("{", "{    \\}");
  }

  protected void processRuleToggle(ParserRuleContext ctx, StringBuilder builder) {
    builder.append(getRuleName(ctx));
    builder.append('\n');
  }

  public void processEnterRule(ParserRuleContext ctx, StringBuilder builder) {
    processRuleToggle(ctx, builder);
  }

  public void processExitRule(ParserRuleContext ctx, StringBuilder builder) {
    processRuleToggle(ctx, builder);
  }

  public void processVisitTerminal(TerminalNode node, StringBuilder builder) {
    // this replacement makes syntax highlighting of the snapshot file not as bad
    builder.append(getTerminalContent(node));
    builder.append('\n');
  }

  public void processVisitErrorNode(ErrorNode node, StringBuilder builder) {
    builder.append("!ERROR");
    builder.append('\n');
  }

  @Override
  public void resetState() {
    depth = 0;
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    processEnterRule(ctx, getJobParameters().getContents());
    depth++;
  }

  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    depth--;
    processExitRule(ctx, getJobParameters().getContents());
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    processVisitTerminal(node, getJobParameters().getContents());
  }

  @Override
  public void visitErrorNode(ErrorNode node) {
    processVisitErrorNode(node, getJobParameters().getContents());
  }
}
