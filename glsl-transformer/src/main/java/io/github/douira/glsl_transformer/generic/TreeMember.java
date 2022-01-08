package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A tree member has a parent and its tokens can be omitted. This interface
 * serves as the basis for all tree parse tree nodes.
 */
public interface TreeMember extends ParseTree {
  /**
   * Gets the parent of this node. The parent is an {@link ExtendedContext}
   * because in this tree all parents should be an extended context.
   */
  public ExtendedContext getParent();

  /**
   * Omits the tokens this node encompasses from the next local root. For terminal
   * nodes this is always at least the parent's local root. Other nodes may be
   * their own local root.
   */
  public void omitTokens();
}