package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.transform.JobParameters;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A terminal replace target replaces the target with a terminal string node.
 */
public abstract class TerminalReplaceTarget<T extends JobParameters> extends ReplaceTarget<T> {

  /**
   * Creates a new terminal placement target with a search string.
   * 
   * @param needle The search string
   */
  public TerminalReplaceTarget(String needle) {
    super(needle);
  }

  /**
   * Creates a new terminal replace target with no search string. The
   * {@link #getNeedle()} method should be overwritten if this constructor is
   * used.
   * 
   * @see io.github.douira.glsl_transformer.core.target.HandlerTargetImpl#HandlerTargetImpl()
   */
  protected TerminalReplaceTarget() {
  }

  /**
   * Returns the content to insert as a terminal replacement node.
   * 
   * @return The string to insert as a terminal replacement node
   */
  protected abstract String getTerminalContent();

  @Override
  protected TreeMember getReplacement(TreeMember node, String match) {
    return new StringNode(getTerminalContent());
  }
}
