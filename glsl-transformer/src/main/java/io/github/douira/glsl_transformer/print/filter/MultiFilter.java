package io.github.douira.glsl_transformer.print.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.transform.ExecutionPlanner;

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
public class MultiFilter<T> extends TokenFilter<T> {
  private Collection<TokenFilter<T>> subfilters;

  /**
   * If this is true, then it will require all filters to allow a token for it to
   * be globally allowed.
   */
  private boolean conjunction = true;

  /**
   * If this is true, then it will check all filters even if the outcome can't
   * change anymore. Otherwise it will stop checking the filters early if
   * possible.
   */
  private boolean shortCircuit = false;

  /**
   * Creates a new multi filter with the given list of subfilters and the behavior
   * options.
   * 
   * @see ArrayList#ArrayList(Collection)
   * 
   * @param subfilters   The subfilters to add initially
   * @param conjunction  The conjunction flag state
   * @param shortCircuit The short circuit flag state
   */
  public MultiFilter(Collection<TokenFilter<T>> subfilters, boolean conjunction, boolean shortCircuit) {
    this(subfilters);
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
   * @param conjunction     The conjunction flag state
   * @param shortCircuit    The short circuit flag state
   */
  public MultiFilter(int initialCapacity, boolean conjunction, boolean shortCircuit) {
    this(new ArrayList<>(initialCapacity), conjunction, shortCircuit);
  }

  /**
   * Creates a new multi filter the behavior options.
   * 
   * @see ArrayList#ArrayList()
   * 
   * @param conjunction  The conjunction flag state
   * @param shortCircuit The short circuit flag state
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
  public MultiFilter(Collection<? extends TokenFilter<T>> subfilters) {
    this.subfilters = new ArrayList<>(subfilters);
  }

  /**
   * Creates a new default multi filter with an initial size capacity.
   * 
   * @see ArrayList#ArrayList(int)
   * 
   * @param initialCapacity The initial list capacity
   */
  public MultiFilter(int initialCapacity) {
    this.subfilters = new ArrayList<>(initialCapacity);
  }

  /**
   * Creates a new empty default multi filter.
   * 
   * @see ArrayList#ArrayList()
   */
  public MultiFilter() {
    this.subfilters = new ArrayList<>();
  }

  /**
   * Sets the conjunction behavior flag
   * 
   * @param conjunction The new conjunction flag state
   */
  public void setConjunction(boolean conjunction) {
    this.conjunction = conjunction;
  }

  /**
   * Sets the conjunction short circuit flag
   * 
   * @param shortCircuit The new short circuit flag state
   */
  public void setShortCircuit(boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }

  /**
   * Adds a token filter to the collection of subfilters.
   * 
   * @param filter The filter to add
   * @return {@code true} if the underlying collection changed
   */
  public boolean add(TokenFilter<T> filter) {
    return subfilters.add(filter);
  }

  /**
   * Adds a collection of token filters to the collection of subfilters.
   * 
   * @param newSubfilters The filters to add
   * @return {@code true} if the underlying collection changed
   */
  public boolean addAll(Collection<? extends TokenFilter<T>> newSubfilters) {
    return subfilters.addAll(newSubfilters);
  }

  /**
   * Adds all subfilters contained in an other multi filter to this multi filter's
   * collection of subfilters.
   * 
   * @param other The other multi filter to take subfilters from
   * @return {@code true} if the underlying collection changed
   */
  public boolean addAll(MultiFilter<T> other) {
    return addAll(other.subfilters);
  }

  /**
   * Creates a shallow clone of this multi filter. It copies over the collection
   * of subfilters shallowly and copies the settings.
   */
  public MultiFilter<T> clone() {
    return new MultiFilter<T>(subfilters, conjunction, shortCircuit);
  }

  @Override
  public void resetState() {
    for (var filter : subfilters) {
      filter.resetState();
    }
  }

  @Override
  public boolean isTokenAllowed(Token token) {
    var result = conjunction;
    for (var filter : subfilters) {
      var verdict = filter.isTokenAllowed(token);
      result = conjunction ? result && verdict : result || verdict;
      if (shortCircuit && result != conjunction) {
        return result;
      }
    }
    return result;
  }

  @Override
  public void setPlanner(ExecutionPlanner<T> planner) {
    super.setPlanner(planner);
    for (var subfilter : subfilters) {
      subfilter.setPlanner(planner);
    }
  }
}
