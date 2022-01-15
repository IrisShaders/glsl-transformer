package io.github.douira.glsl_transformer.core;

import java.util.Collection;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.transform.WalkPhase;
import io.github.douira.glsl_transformer.tree.TreeMember;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * This phase finds targets in specified target token types (usually
 * identifiers) and triggers their handlers. The behavior of the targets can be
 * customized with the various available classes.
 */
public class SearchTerminals extends WalkPhase {
  /**
   * A constant for easy access to the identifier token type.
   */
  public static final int IDENTIFIER = GLSLLexer.IDENTIFIER;

  /**
   * The list of targets to process for each targeted context.
   */
  protected final Collection<HandlerTarget> targets;

  /**
   * The target type of token to replace
   */
  private int terminalTokenType;

  /**
   * Creates a new target search phase with the given targets.
   * 
   * @param terminalTokenType The type of the token to search in
   * @param targets           The targets to search for
   */
  public SearchTerminals(int terminalTokenType, Collection<HandlerTarget> targets) {
    this.terminalTokenType = terminalTokenType;
    this.targets = targets;
  }

  /**
   * Creates a new target search phase with only a single target.
   * 
   * @param terminalTokenType The type of the token to search in
   * @param target            The target to search for
   */
  public SearchTerminals(int terminalTokenType, HandlerTarget target) {
    this(terminalTokenType, CompatUtil.listOf(target));
  }

  /**
   * Creates a new identifier search phase with with given targets.
   * 
   * @param targets The targets to search for in identifiers
   */
  public SearchTerminals(Collection<HandlerTarget> targets) {
    this(IDENTIFIER, targets);
  }

  /**
   * Creates a new identifier search phase with only a single target.
   * 
   * @param target The target to search for in identifiers
   */
  public SearchTerminals(HandlerTarget target) {
    this(CompatUtil.listOf(target));
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
  public void addTarget(HandlerTarget target) {
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
  protected boolean findNeedle(String content, HandlerTarget target) {
    return content.contains(target.getNeedle());
  }
}
