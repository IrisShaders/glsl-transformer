package io.github.douira.glsl_transformer.test_util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrintTreeSnapshot extends PrintTree {

  @Override
  public void processEnterRule(ParserRuleContext ctx, StringBuilder builder) {
    builder.append('(');
    super.processEnterRule(ctx, builder);
  }

  @Override
  public void processExitRule(ParserRuleContext ctx, StringBuilder builder) {
    builder.append(')');
    super.processExitRule(ctx, builder);
  }

  @Override
  public void processVisitTerminal(TerminalNode node, StringBuilder builder) {
    builder.append("- ");
    super.processVisitTerminal(node, builder);
  }
}
