package me.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class EmptyTerminalNode extends TerminalNodeImpl {
  static private class EmptyToken extends CommonToken {
    EmptyToken() {
      // type 0 because -1 would make it count as EOF and never get printed
      super(Token.INVALID_TYPE, null);
    }
  }

  public EmptyTerminalNode() {
    super(new EmptyToken());
  }
}
