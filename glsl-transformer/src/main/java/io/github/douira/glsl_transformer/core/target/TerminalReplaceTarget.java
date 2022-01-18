package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A terminal replace target replaces the target with a terminal string node.
 * Since the string terminal node is immutable, it's only created once and then
 * re-used if it needs to be printed multiple times.
 */
public class TerminalReplaceTarget<P> extends ReplaceTarget<P> {
  private final String terminalContent;
  private TreeMember cacheNode;

  /**
   * Creates a new terminal replace target with a given needle and content of the
   * terminal node to create as a replacement.
   * 
   * @param needle          The needle (search string)
   * @param terminalContent The new string node content.
   */
  public TerminalReplaceTarget(String needle, String terminalContent) {
    super(needle);
    this.terminalContent = terminalContent;
  }

  @Override
  public TreeMember getReplacement(TreeMember node, String match) {
    if (cacheNode == null) {
      cacheNode = new StringNode(terminalContent);
    }
    return cacheNode;
  }
}
