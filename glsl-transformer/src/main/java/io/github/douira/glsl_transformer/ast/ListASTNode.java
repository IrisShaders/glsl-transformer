package io.github.douira.glsl_transformer.ast;

import java.util.*;

public abstract class ListASTNode<Child> extends InnerASTNode {
  public List<Child> children;

  public ListASTNode() {
    this(new ArrayList<>());
  }

  public ListASTNode(List<Child> children) {
    this.children = children;
  }
}
