package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

public abstract class ExtendedParser extends Parser {
  public ExtendedParser(TokenStream input) {
    super(input);
  }

  @Override
  public TerminalNode createTerminalNode(ParserRuleContext parent, Token token) {
    return new ExtendedTerminalNode(parent, token);
  }
}
