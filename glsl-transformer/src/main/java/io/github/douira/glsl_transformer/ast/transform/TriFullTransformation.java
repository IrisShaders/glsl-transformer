package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;

@FunctionalInterface
public interface TriFullTransformation<A extends ASTNode, T extends JobParameters> {
  void accept(A a, A b, A c, Root rootA, Root rootB, Root rootC, T jobParameters);
}
