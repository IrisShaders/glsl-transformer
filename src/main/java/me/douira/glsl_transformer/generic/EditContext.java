package me.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.SyntaxTree;

public class EditContext {
  private CachingIntervalSet omittedTokens = new CachingIntervalSet();

  private boolean editingFinished = false;

  public void finishEditing() {
    editingFinished = true;
    omittedTokens.setReadonly(true);
  }

  public boolean checkTokenAllowed(Token token) {
    return token.getChannel() != Token.DEFAULT_CHANNEL || !omittedTokens.contains(token.getTokenIndex());
  }

  public void omitNodeTokens(SyntaxTree node) {
    omitTokenInterval(node.getSourceInterval());
  }

  public void omitTokenInterval(Interval interval) {
    if (editingFinished) {
      throw new IllegalStateException("Can't add intervals to editing context when editing is already finished!");
    }
    omittedTokens.add(interval.a, interval.b);
  }
}
