package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;

@FunctionalInterface
public interface TriRootOnlyTransformation<A extends ASTNode> {
  void accept(A a, A b, A c, Root rootA, Root rootB, Root rootC);
}
