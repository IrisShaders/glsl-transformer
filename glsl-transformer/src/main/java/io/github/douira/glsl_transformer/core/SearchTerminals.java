package io.github.douira.glsl_transformer.core;

import static io.github.douira.glsl_transformer.util.ConfigUtil.*;

import java.util.*;
import java.util.function.Function;

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
 * customized with the various available target subclasses.
 * 
 * By default, an exact string match of the text in the terminal node and the
 * needle search string is required. However, this behavior can be configured.
 * 
 * If something other than terminals should be searched, simply extend the walk
 * phase yourself and do something when it visits the parse context of
 * interest.
 */
//TODO: actually make it configurable, write some tests
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
   * If string matching is done exactly or also larger strings that contain the
   * needle are allowed.
   */
  private boolean requireFullMatch = true;

  /**
   * The target type of token to replace
   */
  private int terminalTokenType = IDENTIFIER;

  /**
   * The list of targets to process for each targeted context.
   */
  protected Collection<HandlerTarget<T>> targets;

  private class TerminalVisitor extends WalkPhase<T> {
    @Override
    public void visitTerminal(TerminalNode node) {
      Token token = node.getSymbol();
      if (token == null) {
        return;
      }

      // check this token if the accepted type is the invalid type or if it matches
      // the token's type
      var targetType = getTerminalTokenType();
      if (targetType == Token.INVALID_TYPE || targetType == token.getType()) {
        String text = token.getText();

        // TODO: this could be optimized using a trie if there are very many needles
        var targets = getTargets();
        if (targets == null) {
          return;
        }
        for (var target : getTargets()) {
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
    addEndDependent(new TerminalVisitor());
  }

  public SearchTerminals<T> targets(Collection<HandlerTarget<T>> targets) {
    this.targets = targets;
    return this;
  }

  public SearchTerminals<T> target(HandlerTarget<T> target) {
    this.targets = CompatUtil.setOf(target);
    return this;
  }

  /**
   * Sets the terminal token type. Use {@link SearchTerminals#IDENTIFIER} to
   * search for identifiers.
   * 
   * @param terminalTokenType The terminal token type
   */
  public SearchTerminals<T> terminalTokenType(int terminalTokenType) {
    this.terminalTokenType = terminalTokenType;
    return this;
  }

  public SearchTerminals<T> requireFullMatch(boolean requireFullMatch) {
    this.requireFullMatch = requireFullMatch;
    return this;
  }

  /**
   * Returns the collection of targets to search for. This method should be
   * efficient as it's called for every visited terminal node.
   * 
   * @return The targets to search for
   */
  protected Collection<HandlerTarget<T>> getTargets() {
    return withDefault(targets, () -> new ArrayList<HandlerTarget<T>>(0));
  }

  /**
   * Returns the terminal token type to match the target's needles against. The
   * available tokens are static {@code int} fields on {@link GLSLLexer}.
   * 
   * @return The terminal token type
   */
  protected int getTerminalTokenType() {
    return terminalTokenType;
  }

  protected boolean getRequireFullMatch() {
    return requireFullMatch;
  }

  /**
   * Adds a target for processing.
   * 
   * @param target The target to add to the collection of targets
   */
  public SearchTerminals<T> addTarget(HandlerTarget<T> target) {
    targets.add(target);
    return this;
  }

  /**
   * Adds a replacement target that replaces matching terminal nodes with new
   * nodes parsed from the given string using a specified parser method.
   * 
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
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
   */
  public SearchTerminals<T> addReplacementTerminal(String needle, String terminalContent) {
    addTarget(new TerminalReplaceTargetImpl<>(needle, terminalContent));
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
    return getRequireFullMatch()
        ? content.equals(target.getNeedle())
        : content.contains(target.getNeedle());
  }
}
