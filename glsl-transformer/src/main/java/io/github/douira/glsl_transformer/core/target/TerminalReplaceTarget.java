package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.tree.TreeMember;

public class TerminalReplaceTarget extends ReplaceTarget {
  private String terminalContent;

  public TerminalReplaceTarget(String needle, String terminalContent) {
    super(needle);
    this.terminalContent = terminalContent;
  }

  @Override
  public TreeMember getReplacement(TreeMember node, String match) {
    return new StringNode(terminalContent);
  }
}
