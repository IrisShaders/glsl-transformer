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
 * This phase finds targets in identifiers and triggers their handlers. The
 * behavior of the targets can be customized with the various available classes.
 */
public class ProcessIdentifiers extends WalkPhase {
  protected Collection<HandlerTarget> targets;

  /**
   * Creates a new identifier search phase with the given targets.
   * 
   * @param targets The targets to search for
   */
  public ProcessIdentifiers(Collection<HandlerTarget> targets) {
    this.targets = targets;
  }

  /**
   * Creates a new identifier search phase with only a single target.
   * 
   * @param target The target to search for
   */
  public ProcessIdentifiers(HandlerTarget target) {
    this(CompatUtil.listOf(target));
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    Token token = node.getSymbol();
    if (token.getType() == GLSLLexer.IDENTIFIER) {
      String text = token.getText();
      for (var target : targets) {
        if (text.contains(target.getNeedle())) {
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
}
