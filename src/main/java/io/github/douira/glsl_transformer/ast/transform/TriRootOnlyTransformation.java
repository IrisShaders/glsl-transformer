package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;

@FunctionalInterface
public interface TriRootOnlyTransformation<N extends ASTNode> {
  void accept(N a, N b, N c, Root rootA, Root rootB, Root rootC);
}
