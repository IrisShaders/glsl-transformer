package io.github.douira.glsl_transformer.ast;

import java.util.List;

public interface ListNode<Child extends ASTNode> {
  List<Child> getChildren();
}
