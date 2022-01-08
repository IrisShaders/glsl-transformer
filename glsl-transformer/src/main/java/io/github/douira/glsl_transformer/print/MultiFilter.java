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

  /**
   * Creates a new multi filter with the given list of subfilters and the behavior
   * options.
   * 
   * @see ArrayList#ArrayList(Collection)
   * 
   * @param subfilters   The subfilters to add initially
   * @param conjunction  If all filters are required to allow a token for it to be
   *                     globally allowed
   * @param shortCircuit If the evaluation of the filter should be stopped early
   *                     if the outcome can't change anymore.
   */
  public MultiFilter(Collection<? extends TokenFilter> subfilters, boolean conjunction, boolean shortCircuit) {
    super(subfilters);
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  /**
   * Creates a new multi filter with an initial size capacity and the behavior
   * options.
   * 
   * @see ArrayList#ArrayList(int)
   * 
   * @param initialCapacity The initial list capacity
   * @param conjunction     If all filters are required to allow a token for it to
   *                        be globally allowed
   * @param shortCircuit    If the evaluation of the filter should be stopped
   *                        early if the outcome can't change anymore.
   */
  public MultiFilter(int initialCapacity, boolean conjunction, boolean shortCircuit) {
    super(initialCapacity);
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  /**
   * Creates a new multi filter the behavior options.
   * 
   * @see ArrayList#ArrayList()
   * 
   * @param conjunction  If all filters are required to allow a token for it to
   *                     be globally allowed
   * @param shortCircuit If the evaluation of the filter should be stopped
   *                     early if the outcome can't change anymore.
   */
  public MultiFilter(boolean conjunction, boolean shortCircuit) {
    this.conjunction = conjunction;
    this.shortCircuit = shortCircuit;
  }

  /**
   * Creates a new default multi filter with the given list of subfilters.
   * 
   * @see ArrayList#ArrayList(Collection)
   * 
   * @param subfilters The subfilters to add initially
   */
  public MultiFilter(Collection<? extends TokenFilter> subfilters) {
    super(subfilters);
  }

  /**
   * Creates a new default multi filter with an initial size capacity.
   * 
   * @see ArrayList#ArrayList(int)
   * 
   * @param initialCapacity The initial list capacity
   */
  public MultiFilter(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Creates a new empty default multi filter.
   * 
   * @see ArrayList#ArrayList()
   */
  public MultiFilter() {
  }

  /**
   * Sets the conjunction behavior flag
   * 
   * @param conjunction The new flag state
   */
  public void setConjunction(boolean conjunction) {
    this.conjunction = conjunction;
  }

  /**
   * Sets the conjunction short circuit flag
   * 
   * @param shortCircuit The new flag state
   */
  public void setShortCircuit(boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }

  @Override
  public void resetState() {
    for (var filter : this) {
      filter.resetState();
    }
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
}
