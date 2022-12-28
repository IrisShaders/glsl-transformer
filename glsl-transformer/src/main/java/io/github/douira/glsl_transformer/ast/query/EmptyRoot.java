package io.github.douira.glsl_transformer.ast.query;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The empty root overrides a normal root just to do nothing. This allows a root
 * stub to exist without the performance cost of registering and unregistering
 * nodes.
 */
public class EmptyRoot extends Root {
  public EmptyRoot() {
    super(null, null);
  }

  @Override
  public void registerIdentifierRename(Identifier identifier) {
  }

  @Override
  public void registerNode(ASTNode node) {
  }

  @Override
  public void unregisterIdentifierRename(Identifier identifier) {
  }

  @Override
  public void unregisterNode(ASTNode node) {
  }
}
