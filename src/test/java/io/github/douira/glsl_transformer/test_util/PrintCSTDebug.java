package io.github.douira.glsl_transformer.test_util;

import static org.fusesource.jansi.Ansi.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrintCSTDebug extends PrintCST {
  protected void addIndentation(StringBuilder builder) {
    builder.append(
        ansi()
            .fgBrightBlack()
            .a("│".repeat(depth - 1) + (depth > 0 ? "├" : ""))
            .reset().toString());
  }

  @Override
  protected String getRuleName(ParserRuleContext ctx) {
    return ansi().fgCyan().a(super.getRuleName(ctx)).reset().toString();
  }

  @Override
  protected String getTerminalContent(TerminalNode node) {
    return ansi().fgBrightGreen().a(super.getTerminalContent(node)).reset().toString();
  }

  @Override
  public void processEnterRule(ParserRuleContext ctx) {
    addIndentation(builder);
    super.processEnterRule(ctx);
  }

  @Override
  public void processExitRule(ParserRuleContext ctx) {
  }

  @Override
  public void processVisitTerminal(TerminalNode node) {
    addIndentation(builder);
    super.processVisitTerminal(node);
  }
}
