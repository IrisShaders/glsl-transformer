package io.github.douira.glsl_transformer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.transform.WalkPhase;

public abstract class PrintTree extends WalkPhase<StringBuilder> {
  int depth;

  private static final int nameSuffixLength = "Context".length();

  protected void processRuleToggle(ParserRuleContext ctx, StringBuilder builder) {
    var name = ctx.getClass().getSimpleName();
    builder.append(name.substring(0, name.length() - nameSuffixLength));
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
    builder.append(node.toString().replace("{", "{    \\}"));
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
    processEnterRule(ctx, getJobParameters());
    depth++;
  }

  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    depth--;
    processExitRule(ctx, getJobParameters());
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    processVisitTerminal(node, getJobParameters());
  }

  @Override
  public void visitErrorNode(ErrorNode node) {
    processVisitErrorNode(node, getJobParameters());
  }
}
