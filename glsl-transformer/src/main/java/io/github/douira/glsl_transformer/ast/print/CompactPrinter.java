package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;
import io.github.douira.glsl_transformer.cst.token_filter.TokenChannel;

public class CompactPrinter extends DelegateTokenProcessor<TokenProcessor> {
  public CompactPrinter(TokenProcessor delegate) {
    super(delegate);
  }

  public CompactPrinter() {
    this(new SimplePrinter());
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token.getRole() == TokenRole.COMMON_FORMATTING
        && token.getContent().equals("\n")) {
      token = new LiteralToken(
          TokenChannel.WHITESPACE, TokenRole.COMMON_FORMATTING, " ");
    }
    super.appendToken(token);
  }
}
