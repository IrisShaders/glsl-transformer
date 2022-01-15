package io.github.douira.glsl_transformer.core;

import java.util.HashSet;
import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.ParsedReplaceTarget;
import io.github.douira.glsl_transformer.core.target.TerminalReplaceTarget;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * Replaces targeted terminals with the registered replacements. This is a thin
 * wrapper over
 * {@link io.github.douira.glsl_transformer.core.SearchTerminals}
 * and
 * {@link io.github.douira.glsl_transformer.core.target.ParsedReplaceTarget}.
 */
public class ReplaceTerminals extends SearchTerminals {
  /**
   * Creates a new empty terminal node replacement transformation with the a
   * type of token to search in.
   * 
   * @param targetRule The type of the token to search for
   */
  public ReplaceTerminals(int targetRule) {
    super(targetRule, new HashSet<>());
  }

  /**
   * Creates a new empty identifier replacement transformation.
   */
  public ReplaceTerminals() {
    super(new HashSet<>());
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
    addTarget(new ParsedReplaceTarget(needle, newContent, parseMethod));
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
    addTarget(new TerminalReplaceTarget(needle, terminalContent));
  }

  /**
   * Creates a new identifier replacement transformation with a replacement
   * target that replaces matching terminal nodes with new nodes parsed from the
   * given string using a specified parser method.
   * 
   * @see #addReplacement(String, String, Function)
   * 
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
   * @return The configured identifier replacement transformation
   */
  public static ReplaceTerminals withReplacement(
      String needle, String newContent,
      Function<GLSLParser, ExtendedContext> parseMethod) {
    var phase = new ReplaceTerminals();
    phase.addReplacement(needle, newContent, parseMethod);
    return phase;
  }

  /**
   * Creates a new identifier replacement transformation with a replacement target
   * that replaces matching terminal nodes with new expression nodes parsed from
   * the given string.
   * 
   * @param needle            The needle (search string)
   * @param expressionContent The new content to parse into an expression
   * @return The configured identifier replacement transformation
   */
  public static ReplaceTerminals withReplacementExpression(String needle, String expressionContent) {
    var phase = new ReplaceTerminals();
    phase.addReplacementExpression(needle, expressionContent);
    return phase;
  }

  /**
   * Creates a new identifier replacement transformation with a replacement target
   * that replaces matching terminal nodes with new unparsed string nodes.
   * 
   * @param needle          The needle (search string)
   * @param terminalContent The new terminal content to insert as a string node
   * @return The configured identifier replacement transformation
   */
  public static ReplaceTerminals withReplacementTerminal(String needle, String terminalContent) {
    var phase = new ReplaceTerminals();
    phase.addReplacementTerminal(needle, terminalContent);
    return phase;
  }
}
