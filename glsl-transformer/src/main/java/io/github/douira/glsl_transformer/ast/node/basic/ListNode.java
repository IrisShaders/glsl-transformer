package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.List;

public interface ListNode<Child extends ASTNode> {
  List<Child> getChildren();
}
