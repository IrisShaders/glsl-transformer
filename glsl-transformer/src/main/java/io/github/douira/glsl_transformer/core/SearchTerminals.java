package io.github.douira.glsl_transformer.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.core.target.ParsedReplaceTarget;
import io.github.douira.glsl_transformer.core.target.TerminalReplaceTarget;
import io.github.douira.glsl_transformer.transform.WalkPhase;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.TreeMember;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * This phase finds targets in specified target token types (usually
 * identifiers) and triggers their handlers. The behavior of the targets can be
 * customized with the various available classes.
 * 
 * By default, an exact string match of the text in the terminal node and the
 * needle search string is required. However, this behavior can be configured.
 */
public class SearchTerminals<T> extends WalkPhase<T> {
  /**
   * A constant for easy access to the identifier token type.
   */
  public static final int IDENTIFIER = GLSLLexer.IDENTIFIER;

  /**
   * The list of targets to process for each targeted context.
   */
  protected final Collection<HandlerTarget<T>> targets;

  /**
   * The target type of token to replace
   */
  private int terminalTokenType;

  /**
   * If string matching is done exactly or also larger strings that contain the
   * needle are allowed.
   */
  private boolean exactMatch = true;

  /**
   * Creates a new target search phase with the given targets.
   * 
   * @param terminalTokenType The type of the tokens to search in
   * @param targets           The targets to search for
   */
  public SearchTerminals(int terminalTokenType, Collection<HandlerTarget<T>> targets) {
    this.terminalTokenType = terminalTokenType;
    this.targets = targets;
  }

  /**
   * Creates a new target search phase with only a single target.
   * 
   * @param terminalTokenType The type of the token to search in
   * @param target            The target to search for
   */
  public SearchTerminals(int terminalTokenType, HandlerTarget<T> target) {
    this(terminalTokenType, CompatUtil.listOf(target));
  }

  /**
   * Creates a new identifier search phase with with given targets.
   * 
   * @param targets The targets to search for in identifiers
   */
  public SearchTerminals(Collection<HandlerTarget<T>> targets) {
    this(IDENTIFIER, targets);
  }

  /**
   * Creates a new identifier search phase with only a single target.
   * 
   * @param target The target to search for in identifiers
   */
  public SearchTerminals(HandlerTarget<T> target) {
    this(CompatUtil.listOf(target));
  }

  /**
   * Creates a new empty identifier search with a hash set.
   */
  public SearchTerminals() {
    this(new HashSet<>());
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    Token token = node.getSymbol();
    if (token.getType() == terminalTokenType) {
      String text = token.getText();
      for (var target : targets) {
        if (findNeedle(text, target)) {
          if (!(node instanceof TreeMember)) {
            throw new IllegalStateException(
                "All nodes in the parse tree should be a TreeMember except for when they are errors! Then the tree is broken anyways.");
          }

          target.setCollector(getCollector());
          target.handleResult((TreeMember) node, text);
        }
      }
    }
  }

  /**
   * Makes this search phase not use exact matching.
   */
  public void allowInexactMatches() {
    exactMatch = false;
  }

  /**
   * Sets the terminal token type. Use {@link #IDENTIFIER} to search for
   * identifiers.
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
   * Checks if the given content contains a needle. This should be overwritten if
   * the matching should be done differently, like using regex or case-insensitive
   * matching.
   * 
   * @param content The content to search in
   * @param target  The target being searched for
   * @return If the target was found in the content
   */
  protected boolean findNeedle(String content, HandlerTarget<T> target) {
    return exactMatch
        ? content.equals(target.getNeedle())
        : content.contains(target.getNeedle());
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
    addTarget(new ParsedReplaceTarget<>(needle, newContent, parseMethod));
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
    addTarget(new TerminalReplaceTarget<>(needle, terminalContent));
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
    return new SearchTerminals<T>() {
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
    return new SearchTerminals<T>() {
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
    return new SearchTerminals<T>() {
      {
        addReplacementTerminal(needle, terminalContent);
      }
    };
  }
}
