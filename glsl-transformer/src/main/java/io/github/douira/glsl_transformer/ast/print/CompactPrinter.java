package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.print.token.*;
import io.github.douira.glsl_transformer.token_filter.TokenChannel;

public class CompactPrinter extends DelegateTokenProcessor {
  public CompactPrinter(TokenProcessor delegate) {
    super(delegate);
  }

  public CompactPrinter() {
    this(new SimplePrinter());
  }

  @Override
  public void appendToken(PrintToken token) {
    if (token.isCommonFormattingNewline()) {
      token = new LiteralToken(
          TokenChannel.WHITESPACE, TokenRole.COMMON_FORMATTING, " ");
    }
    super.appendToken(token);
  }
}
