package io.github.douira.glsl_transformer.test_util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrintTreeSnapshot extends PrintCST {
  @Override
  public void processEnterRule(ParserRuleContext ctx) {
    builder.append('(');
    super.processEnterRule(ctx);
  }

  @Override
  public void processExitRule(ParserRuleContext ctx) {
    builder.append(')');
    super.processExitRule(ctx);
  }

  @Override
  public void processVisitTerminal(TerminalNode node) {
    builder.append("- ");
    super.processVisitTerminal(node);
  }
}
