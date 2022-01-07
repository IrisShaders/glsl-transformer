package io.github.douira.glsl_transformer.transformations;

import java.util.Collection;
import java.util.function.BiConsumer;

import com.github.bsideup.jabel.Desugar;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.transform.WalkPhase;

/**
 * This transformation finds strings in identifiers in the tree and triggers a
 * function when it finds one.
 */
public class FindIdentifiers extends WalkPhase {
  /**
   * A target contains a string to search for in identifiers and a function that
   * accepts the found node and the token it contains.
   */
  @Desugar
  public record Target(String contained, BiConsumer<TerminalNode, Token> action) {
  }

  private Collection<Target> targets;

  /**
   * Creates a new transformation of this type with the given targets.
   * 
   * @param targets The targets to search for
   */
  public FindIdentifiers(Collection<Target> targets) {
    this.targets = targets;
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    Token token = node.getSymbol();
    if (token.getType() == GLSLLexer.IDENTIFIER) {
      String text = token.getText();
      for (var target : targets) {
        if (text.contains(target.contained())) {
          target.action().accept(node, token);
        }
      }
    }
  }
}
