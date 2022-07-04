package io.github.douira.glsl_transformer.ast.print.token;

import java.util.function.Function;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.print.*;

public class ReplaceToken extends PrintToken {
  private PrintToken replacement;
  private Function<PrintToken, Boolean> condition;

  public ReplaceToken(ASTNode source, PrintToken replacement, Function<PrintToken, Boolean> condition) {
    super(source);
    this.replacement = replacement;
    this.condition = condition;
  }

  public ReplaceToken(ASTNode source, PrintToken replacement, String match, Function<ASTNode, Boolean> condition) {
    super(source);
    this.replacement = replacement;
    this.condition = (token) -> match.equals(token.getContent())
        && condition.apply(token.getSource());
  }

  @Override
  public String getContent() {
    return null;
  }

  public void replace(PrintToken other, ASTPrinterUtil printer) {
    if (other.getRole() == TokenRole.COMMON_FORMATTING && condition.apply(other)) {
      printer.replaceToken(replacement);
    }
  }

  public static ReplaceToken fromMatch(
      ASTNode source, PrintToken replacement, String match) {
    return new ReplaceToken(
        source, replacement,
        (token) -> match.equals(token.getContent()));
  }

  public static ReplaceToken fromNodeCondition(
      ASTNode source, PrintToken replacement, Function<ASTNode, Boolean> condition) {
    return new ReplaceToken(
        source, replacement, (node) -> condition.apply(node.getSource()));
  }

  public static ReplaceToken fromMatchAndNodeCondition(
      ASTNode source, PrintToken replacement, String match, Function<ASTNode, Boolean> condition) {
    return new ReplaceToken(
        source, replacement, (token) -> match.equals(token.getContent())
            && condition.apply(token.getSource()));
  }
}
