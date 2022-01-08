package io.github.douira.glsl_transformer.transformation;

import java.util.Collection;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.generic.TreeMember;
import io.github.douira.glsl_transformer.transform.WalkPhase;
import io.github.douira.glsl_transformer.transformation.target.HandlerTarget;
import io.github.douira.glsl_transformer.util.CompatUtil;

/**
 * This transformation finds strings in identifiers in the tree and triggers a
 * function when it finds one.
 */
public class ProcessIdentifiers extends WalkPhase {
  private Collection<HandlerTarget> targets;

  /**
   * Creates a new transformation of this type with the given targets.
   * 
   * @param targets The targets to search for
   */
  public ProcessIdentifiers(Collection<HandlerTarget> targets) {
    this.targets = targets;
  }

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
