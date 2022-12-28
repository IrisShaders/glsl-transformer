package io.github.douira.glsl_transformer.ast.node.abstract_node;

import java.util.List;

public interface ListNode<Child extends ASTNode> {
  List<Child> getChildren();
}
