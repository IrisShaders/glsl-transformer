package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A tree member has a parent and its tokens can be omitted.
 */
public interface TreeMember extends ParseTree {
  public ExtendedContext getParent();

  public void omitTokens();
}
