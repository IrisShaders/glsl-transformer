package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.SourceLocation;

/**
 * A problem found by the type analysis.
 *
 * @param code     the kind of problem
 * @param message  a description of the problem
 * @param node     the node the problem was found at
 * @param location the source location of the closest ancestor of the node that
 *                 has one, or null if there is none. Source locations are only
 *                 present if the AST was built with them.
 */
public record Diagnostic(DiagnosticCode code, String message, ASTNode node, SourceLocation location) {
  public Diagnostic(DiagnosticCode code, String message, ASTNode node) {
    this(code, message, node, findLocation(node));
  }

  private static SourceLocation findLocation(ASTNode node) {
    for (var current = node; current != null; current = current.getParent()) {
      if (current.getSourceLocation() != null) {
        return current.getSourceLocation();
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return code + ": " + message;
  }
}
