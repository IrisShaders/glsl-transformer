package io.github.douira.glsl_transformer.ast.print.token;

import java.util.function.Function;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.print.*;

public class ReplaceToken extends PrintToken {
  private PrintToken replacement;
  private Function<PrintToken, Boolean> condition;

  public ReplaceToken(PrintToken replacement, Function<PrintToken, Boolean> condition) {
    this.replacement = replacement;
    this.condition = condition;
  }

  public ReplaceToken(PrintToken replacement, String match, Function<ASTNode, Boolean> condition) {
    this.replacement = replacement;
    this.condition = (token) -> match.equals(token.getContent())
        && condition.apply(token.getSource());
  }

  @Override
  public String calculateContent() {
    return null;
  }

  @Override
  public String getContent() {
    return null;
  }

  public void replace(PrintToken other, ASTPrinterBase printer) {
    if (other.getRole() == TokenRole.COMMON_FORMATTING && condition.apply(other)) {
      printer.replaceToken(replacement);
    }
  }

  public static ReplaceToken fromMatch(
      PrintToken replacement, String match) {
    return new ReplaceToken(
        replacement, (token) -> match.equals(token.getContent()));
  }

  public static ReplaceToken fromNodeCondition(
      PrintToken replacement, Function<ASTNode, Boolean> condition) {
    return new ReplaceToken(
        replacement, (node) -> condition.apply(node.getSource()));
  }

  public static ReplaceToken fromMatchAndNodeCondition(
      PrintToken replacement, String match, Function<ASTNode, Boolean> condition) {
    return new ReplaceToken(
        replacement, (token) -> match.equals(token.getContent())
            && condition.apply(token.getSource()));
  }
}
