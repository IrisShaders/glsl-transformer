package io.github.douira.glsl_transformer.core;

import java.util.Collection;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.transform.WalkPhase;
import io.github.douira.glsl_transformer.tree.TreeMember;

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
public abstract class SearchTerminals<T> extends WalkPhase<T> {
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
  private boolean exactMatch = true;

  /**
   * Returns the collection of targets to search for. This method should be
   * efficient as it's called for every visited terminal node.
   * 
   * @return The targets to search for
   */
  protected abstract Collection<HandlerTarget<T>> getTargets();

  /**
   * Returns the terminal token type to match the target's needles against. The
   * available tokens are static {@code int} fields on {@link GLSLLexer}.
   * 
   * @return The terminal token type
   */
  protected int getTerminalTokenType() {
    return GLSLLexer.IDENTIFIER;
  };

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

  /**
   * Makes this search phase not use exact matching.
   */
  public void allowInexactMatches() {
    exactMatch = false;
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
}
