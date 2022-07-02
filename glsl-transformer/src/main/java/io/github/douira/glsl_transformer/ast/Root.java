package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public class Root implements NodeTracker {
  public ASTNode rootNode;
  public final NodeIndex nodeIndex = new NodeIndex();
  public final IdentifierIndex identifierIndex = new IdentifierIndex();

  public void registerChild(ASTNode child) {
    nodeIndex.add(child);
    if (child instanceof Identifier) {
      identifierIndex.add((Identifier) child);
    }
  }

  public void unregisterChild(ASTNode child) {
    nodeIndex.remove(child);
    if (child instanceof Identifier) {
      identifierIndex.remove((Identifier) child);
    }
  }
}
