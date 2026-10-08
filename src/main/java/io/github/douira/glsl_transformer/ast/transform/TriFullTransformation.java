package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;

@FunctionalInterface
public interface TriFullTransformation<N extends ASTNode, J extends JobParameters> {
  void accept(N a, N b, N c, Root rootA, Root rootB, Root rootC, J jobParameters);
}
