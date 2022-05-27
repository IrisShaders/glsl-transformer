package io.github.douira.glsl_transformer.core;

import java.util.*;
import java.util.function.*;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.core.target.*;
import io.github.douira.glsl_transformer.transform.*;
import io.github.douira.glsl_transformer.tree.*;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * This phase finds targets in specified target token types (usually
 * identifiers) and triggers their handlers. The behavior of the targets can be
 * customized with the various available core targets.
 * 
 * By default, an exact string match of the text in the terminal node and the
 * needle search string is required. However, this behavior can be configured.
 * 
 * If something other than terminals should be searched, simply extend the walk
 * phase yourself and do something when it visits the parse context of
 * interest. (meaning without using this class as it's specifically for visiting
 * terminals)
 */
public class SearchTerminals<T extends JobParameters> extends ConfigurableTransformation<T> {
  /**
   * The identifier token type.
   */
  public static final int IDENTIFIER = GLSLLexer.IDENTIFIER;

  /**
   * The token type that matches any terminal token.
   */
  public static final int ANY_TYPE = Token.INVALID_TYPE;

  /**
   * The default collection of targets that is added onto when adding targets
   * individually. This field is not used if the getter method or the
   * configuration property is overwritten.
   */
  protected Collection<HandlerTarget<T>> targetsDirect = new ArrayList<HandlerTarget<T>>(0);

  private Supplier<Boolean> requireFullMatch = once(this::getRequireFullMatch);
  private Supplier<Integer> terminalTokenType = once(this::getTerminalTokenType);
  private Supplier<Collection<HandlerTarget<T>>> targets = once(this::getTargets);

  private class TerminalVisitor extends WalkPhase<T> {
    @Override
    public void visitTerminal(TerminalNode node) {
      Token token = node.getSymbol();
      if (token == null) {
        return;
      }

      // check this token if the accepted type is the invalid type or if it matches
      // the token's type
      var targetType = terminalTokenType();
      if (targetType == Token.INVALID_TYPE || targetType == token.getType()) {
        String text = token.getText();

        // TODO: this could be optimized using a trie if there are very many needles
        var targets = targets();
        if (targets == null) {
          return;
        }
        for (var target : targets) {
          if (findNeedle(text, target)) {
            if (!(node instanceof TreeMember)) {
              throw new IllegalStateException(
                  "All nodes in the parse tree should be a TreeMember except for when they are errors! Then the tree is broken anyways.");
            }

            target.setPlanner(getPlanner());
            target.handleResult((TreeMember) node, text);
          }
        }
      }
    }
  }

  {
    addEndDependent(new TerminalVisitor().activation(this::isActive));
  }

  /**
   * Overwrite to make type more specific
   */
  @Override
  public SearchTerminals<T> activation(Supplier<Boolean> activation) {
    super.activation(activation);
    return this;
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
    var needle = target.getNeedle();
    if (needle == null) {
      return false;
    }
    return requireFullMatch()
        ? content.equals(needle)
        : content.contains(needle);
  }

  /**
   * Adds a target for processing.
   * 
   * @param target The target to add to the collection of targets
   * @return This object
   */
  public SearchTerminals<T> addTarget(HandlerTarget<T> target) {
    targetsDirect.add(target);
    return this;
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * nodes parsed from the given string using a specified parser method.
   * 
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
   * @return This object
   */
  public SearchTerminals<T> addReplacement(
      String needle, String newContent,
      Function<GLSLParser, ExtendedContext> parseMethod) {
    addTarget(new ParsedReplaceTargetImpl<T>(needle, newContent, parseMethod));
    return this;
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * expression nodes parsed from the given string.
   * 
   * @param needle            The needle (search string)
   * @param expressionContent The new content to parse into an expression
   * @return This object
   */
  public SearchTerminals<T> addReplacementExpression(String needle, String expressionContent) {
    addReplacement(needle, expressionContent, GLSLParser::expression);
    return this;
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * unparsed string nodes.
   * 
   * @param needle          The needle (search string)
   * @param terminalContent The new terminal content to insert as a string node
   * @return This object
   */
  public SearchTerminals<T> addReplacementTerminal(String needle, String terminalContent) {
    addTarget(new TerminalReplaceTargetImpl<>(needle, terminalContent));
    return this;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected int getTerminalTokenType() {
    return IDENTIFIER;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected boolean getRequireFullMatch() {
    return true;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected Collection<HandlerTarget<T>> getTargets() {
    if (targetsDirect.isEmpty()) {
      throw new IllegalStateException("No targets are set");
    }
    return targetsDirect;
  }

  // the rest of this class is just configuration methods
  // #region Configuration methods

  /**
   * Sets the terminal token type to match the target's needles against. The
   * available tokens are static {@code int} fields on {@link GLSLLexer}.
   * 
   * @param terminalTokenType The terminal token type to match targets on
   * @return This object
   */
  public SearchTerminals<T> terminalTokenType(int terminalTokenType) {
    this.terminalTokenType = swapSupplier(this.terminalTokenType, terminalTokenType);
    return this;
  }

  /**
   * Sets if the search should be done exactly or also larger strings that contain
   * the needle are allowed. True by default.
   * 
   * @param requireFullMatch If the whole token must match the target's needle
   * @return This object
   */
  public SearchTerminals<T> requireFullMatch(boolean requireFullMatch) {
    this.requireFullMatch = swapSupplier(this.requireFullMatch, requireFullMatch);
    return this;
  }

  /**
   * Sets the collection of targets to act on.
   * 
   * @param targets The targets to act on
   * @return This object
   */
  public SearchTerminals<T> targets(Collection<HandlerTarget<T>> targets) {
    this.targets = swapSupplier(this.targets, targets);
    return this;
  }

  /**
   * Sets the single target to act on. No other targets will be considered.
   * 
   * @param target The target to act on
   * @return This object
   */
  public SearchTerminals<T> singleTarget(HandlerTarget<T> target) {
    return targets(CompatUtil.listOf(target));
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param terminalTokenType The value supplier
   * @return This object
   */
  public SearchTerminals<T> terminalTokenType(Supplier<Integer> terminalTokenType) {
    this.terminalTokenType = swapSupplier(this.terminalTokenType, terminalTokenType);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param requireFullMatch The value supplier
   * @return This object
   */
  public SearchTerminals<T> requireFullMatch(Supplier<Boolean> requireFullMatch) {
    this.requireFullMatch = swapSupplier(this.requireFullMatch, requireFullMatch);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param targets The value supplier
   * @return This object
   */
  public SearchTerminals<T> targets(Supplier<Collection<HandlerTarget<T>>> targets) {
    this.targets = swapSupplier(this.targets, targets);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public SearchTerminals<T> terminalTokenType(CachePolicy newPolicy) {
    this.terminalTokenType = swapPolicy(this.terminalTokenType, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public SearchTerminals<T> requireFullMatch(CachePolicy newPolicy) {
    this.requireFullMatch = swapPolicy(this.requireFullMatch, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public SearchTerminals<T> targets(CachePolicy newPolicy) {
    this.targets = swapPolicy(this.targets, newPolicy);
    return this;
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final int terminalTokenType() {
    return terminalTokenType.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final boolean requireFullMatch() {
    return requireFullMatch.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final Collection<HandlerTarget<T>> targets() {
    return targets.get();
  }
  // #endregion
}
