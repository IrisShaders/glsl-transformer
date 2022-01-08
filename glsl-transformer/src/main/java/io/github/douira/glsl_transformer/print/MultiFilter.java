package io.github.douira.glsl_transformer.print;

import java.util.ArrayList;
import java.util.Collection;

import org.antlr.v4.runtime.Token;

/**
 * A multi filter checks all contained filters with either requiring all of them
 * or at least one of them to allow the token. It can also be configured to
 * short-circuit, meaning that it stops checking the filters if the result can't
 * change anymore.
 * 
 * By default it's a conjunction that doesn't short-circuit
 * checking all filters to allow the token and continuing even if an earlier
 * filter has disallowed it. Since filters can have state, it can be desirerable
 * to notify all of them of all tokens.
 */
public class MultiFilter extends ArrayList<TokenFilter> implements TokenFilter {
  private boolean conjunction = true;
  private boolean shortCircuit = false;

  public MultiFilter(Collection<? extends TokenFilter> c, boolean conjunction, boolean shortCircuit) {
    super(c);
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  public MultiFilter(int initialCapacity, boolean conjunction, boolean shortCircuit) {
    super(initialCapacity);
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  public MultiFilter(boolean conjunction, boolean shortCircuit) {
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  public MultiFilter() {
  }

  public MultiFilter(int initialCapacity) {
    super(initialCapacity);
  }

  public MultiFilter(Collection<? extends TokenFilter> c) {
    super(c);
  }

  public void setConjunction(boolean conjunction) {
    this.conjunction = conjunction;
  }

  public void setShortCircuit(boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }

  @Override
  public boolean isTokenAllowed(Token token) {
    var result = conjunction;
    for (var filter : this) {
      var verdict = filter.isTokenAllowed(token);
      result = conjunction ? result && verdict : result || verdict;
      if (shortCircuit && result != conjunction) {
        return result;
      }
    }
    return result;
  }

  @Override
  public void resetState() {
    for (var filter : this) {
      filter.resetState();
    }
  }
}
