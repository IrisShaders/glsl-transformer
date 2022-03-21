package io.github.douira.glsl_transformer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import static org.fusesource.jansi.Ansi.ansi;

import io.github.douira.glsl_transformer.util.CompatUtil;

public class PrintTreeDebug extends PrintTree {

  protected void addIndentation(StringBuilder builder) {
    builder.append(
        ansi()
            .fgBrightBlack()
            .a(CompatUtil.repeat("│", depth - 1) + (depth > 0 ? "├" : ""))
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
  public void processEnterRule(ParserRuleContext ctx, StringBuilder builder) {
    addIndentation(builder);
    super.processEnterRule(ctx, builder);
  }

  @Override
  public void processExitRule(ParserRuleContext ctx, StringBuilder builder) {
  }

  @Override
  public void processVisitTerminal(TerminalNode node, StringBuilder builder) {
    addIndentation(builder);
    super.processVisitTerminal(node, builder);
  }
}
