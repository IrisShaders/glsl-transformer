package me.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class StringNode extends TerminalNodeImpl {
  static private class ReplacementToken extends CommonToken {
    public ReplacementToken(String text) {
      // type 0 because -1 would make it count as EOF and never get printed
      super(Token.INVALID_TYPE, text);
    }
  }

  public StringNode() {
    this(null);
  }

  public StringNode(String text) {
    super(new ReplacementToken(text));
  }
}
