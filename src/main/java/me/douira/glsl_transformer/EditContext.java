package me.douira.glsl_transformer;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.SyntaxTree;

public class EditContext {
  private CachingIntervalSet deletedTokens = new CachingIntervalSet();

  private boolean editingFinished = false;

  public void finishEditing() {
    editingFinished = true;
    deletedTokens.setReadonly(true);
  }

  public boolean allowToken(Token token) {
    return token.getChannel() != Token.DEFAULT_CHANNEL || !deletedTokens.contains(token.getTokenIndex());
  }

  public void markDeleted(SyntaxTree node) {
    markDeleted(node.getSourceInterval());
  }

  public void markDeleted(Interval interval) {
    if (editingFinished) {
      throw new IllegalStateException("Can't add intervals to editing context when editing is already finished!");
    }
    deletedTokens.add(interval.a, interval.b);
  }
}
