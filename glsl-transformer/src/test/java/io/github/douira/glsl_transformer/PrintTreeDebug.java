package io.github.douira.glsl_transformer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.util.CompatUtil;

public class PrintTreeDebug extends PrintTree {

  @Override
  public void processEnterRule(ParserRuleContext ctx, StringBuilder builder) {
    builder.append(CompatUtil.repeat("│", depth - 1));
    if (depth > 0) {
      builder.append('├');
    }
    super.processEnterRule(ctx, builder);
  }

  @Override
  public void processExitRule(ParserRuleContext ctx, StringBuilder builder) {
  }

  @Override
  public void processVisitTerminal(TerminalNode node, StringBuilder builder) {
    builder.append(CompatUtil.repeat("│", depth - 1));
    if (depth > 0) {
      builder.append('├');
    }
    super.processVisitTerminal(node, builder);
  }
}
