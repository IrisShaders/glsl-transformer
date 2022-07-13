package io.github.douira.glsl_transformer.cst.core.target;

import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * This terminal replacement target uses a given string to generate terminal
 * nodes to use as replacement nodes for replacing found targets.
 * 
 * Since the string terminal node is immutable, it's only created once and then
 * re-used if it needs to be printed multiple times.
 */
public class TerminalReplaceTargetImpl<T extends JobParameters> extends TerminalReplaceTarget<T> {
  private final String terminalContent;
  private TreeMember cacheNode;

  /**
   * Creates a new terminal replace target with a given needle and content of the
   * terminal node to create as a replacement.
   * 
   * @param needle          The needle (search string)
   * @param terminalContent The new string node content.
   */
  public TerminalReplaceTargetImpl(String needle, String terminalContent) {
    super(needle);
    this.terminalContent = terminalContent;
  }

  @Override
  protected String getTerminalContent() {
    return terminalContent;
  }

  @Override
  protected TreeMember getReplacement(TreeMember node, String match) {
    if (cacheNode == null) {
      cacheNode = super.getReplacement(node, match);
    }
    return cacheNode;
  }
}
