package io.github.douira.glsl_transformer.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.core.target.ParsedReplaceTargetImpl;
import io.github.douira.glsl_transformer.core.target.TerminalReplaceTargetImpl;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * This implementation of the search terminals transformation phase uses static
 * fields to return the targets and the terminal token type.
 */
public class SearchTerminalsImpl<T> extends SearchTerminals<T> {
  /**
   * The target type of token to replace
   */
  private int terminalTokenType;

  /**
   * The list of targets to process for each targeted context.
   */
  protected Collection<HandlerTarget<T>> targets;

  /**
   * Creates a new target search phase with the given targets.
   * 
   * @param terminalTokenType The type of the tokens to search in
   * @param targets           The targets to search for
   */
  public SearchTerminalsImpl(int terminalTokenType, Collection<HandlerTarget<T>> targets) {
    this.terminalTokenType = terminalTokenType;
    this.targets = targets;
  }

  /**
   * Creates a new target search phase with only a single target.
   * 
   * @param terminalTokenType The type of the token to search in
   * @param target            The target to search for
   */
  public SearchTerminalsImpl(int terminalTokenType, HandlerTarget<T> target) {
    this(terminalTokenType, CompatUtil.listOf(target));
  }

  /**
   * Creates a new target search phase with only the terminal token type given.
   * 
   * @param terminalTokenType The type of the token to search in
   */
  public SearchTerminalsImpl(int terminalTokenType) {
    this();
    this.terminalTokenType = terminalTokenType;
  }

  /**
   * Creates a new identifier search phase with with given targets.
   * 
   * @param targets The targets to search for in identifiers
   */
  public SearchTerminalsImpl(Collection<HandlerTarget<T>> targets) {
    this(IDENTIFIER, targets);
  }

  /**
   * Creates a new identifier search phase with only a single target.
   * 
   * @param target The target to search for in identifiers
   */
  public SearchTerminalsImpl(HandlerTarget<T> target) {
    this(CompatUtil.listOf(target));
  }

  /**
   * Creates a new empty identifier search with a hash set.
   */
  public SearchTerminalsImpl() {
    this(new HashSet<>());
  }

  @Override
  public Collection<HandlerTarget<T>> getTargets() {
    return targets;
  }

  @Override
  protected int getTerminalTokenType() {
    return terminalTokenType;
  }

  /**
   * Sets the terminal token type. Use {@link SearchTerminals#IDENTIFIER} to
   * search for identifiers.
   * 
   * @param terminalTokenType The terminal token type
   */
  public void setTerminalTokenType(int terminalTokenType) {
    this.terminalTokenType = terminalTokenType;
  }

  /**
   * Adds a target for processing.
   * 
   * @param target The target to add to the collection of targets
   */
  public void addTarget(HandlerTarget<T> target) {
    targets.add(target);
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * nodes parsed from the given string using a specified parser method.
   * 
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
   */
  public void addReplacement(
      String needle, String newContent,
      Function<GLSLParser, ExtendedContext> parseMethod) {
    addTarget(new ParsedReplaceTargetImpl<>(needle, newContent, parseMethod));
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * expression nodes parsed from the given string.
   * 
   * @param needle            The needle (search string)
   * @param expressionContent The new content to parse into an expression
   */
  public void addReplacementExpression(String needle, String expressionContent) {
    addReplacement(needle, expressionContent, GLSLParser::expression);
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * unparsed string nodes.
   * 
   * @param needle          The needle (search string)
   * @param terminalContent The new terminal content to insert as a string node
   */
  public void addReplacementTerminal(String needle, String terminalContent) {
    addTarget(new TerminalReplaceTargetImpl<>(needle, terminalContent));
  }

  /**
   * Creates a new identifier replacement transformation with a replacement
   * target that replaces matching terminal nodes with new nodes parsed from the
   * given string using a specified parser method.
   * 
   * @see #addReplacement(String, String, Function)
   * 
   * @param <T>         The job parameter type
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
   * @return The configured identifier replacement transformation
   */
  public static <T> SearchTerminals<T> withReplacement(
      String needle, String newContent,
      Function<GLSLParser, ExtendedContext> parseMethod) {
    return new SearchTerminalsImpl<T>() {
      {
        addReplacement(needle, newContent, parseMethod);
      }
    };
  }

  /**
   * Creates a new identifier replacement transformation with a replacement target
   * that replaces matching terminal nodes with new expression nodes parsed from
   * the given string.
   * 
   * @param <T>               The job parameter type
   * @param needle            The needle (search string)
   * @param expressionContent The new content to parse into an expression
   * @return The configured identifier replacement transformation
   */
  public static <T> SearchTerminals<T> withReplacementExpression(String needle, String expressionContent) {
    return new SearchTerminalsImpl<T>() {
      {
        addReplacementExpression(needle, expressionContent);
      }
    };
  }

  /**
   * Creates a new identifier replacement transformation with a replacement target
   * that replaces matching terminal nodes with new unparsed string nodes.
   * 
   * @param <T>             The job parameter type
   * @param needle          The needle (search string)
   * @param terminalContent The new terminal content to insert as a string node
   * @return The configured identifier replacement transformation
   */
  public static <T> SearchTerminals<T> withReplacementTerminal(String needle, String terminalContent) {
    return new SearchTerminalsImpl<T>() {
      {
        addReplacementTerminal(needle, terminalContent);
      }
    };
  }
}
