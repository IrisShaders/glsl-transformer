package io.github.douira.glsl_transformer.transformation.target;

import io.github.douira.glsl_transformer.generic.TreeMember;

public abstract class ReplaceTarget extends HandlerTarget {
  public ReplaceTarget(String needle) {
    super(needle);
  }

  @Override
  public void handleResult(TreeMember node, String match) {
    replaceNode(node, getReplacement(node, match));
  }
  
  public abstract TreeMember getReplacement(TreeMember node, String match);
}
