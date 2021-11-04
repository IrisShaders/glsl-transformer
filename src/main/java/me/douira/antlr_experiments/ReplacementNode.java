package me.douira.antlr_experiments;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class ReplacementNode extends TerminalNodeImpl {
  static private class ReplacementToken extends CommonToken {
    public ReplacementToken(String text) {
      super(-1, text);
    }
  }

  public ReplacementNode(String text) {
    super(new ReplacementToken(text));
  }
}
