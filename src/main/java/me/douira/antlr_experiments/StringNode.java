package me.douira.antlr_experiments;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class StringNode extends TerminalNodeImpl {
  static private class ReplacementToken extends CommonToken {
    public ReplacementToken(String text) {
      //type 0 because -1 makes it count as EOF and never be printed
      super(0, text);
    }
  }

  public StringNode(String text) {
    super(new ReplacementToken(text));
  }
}
