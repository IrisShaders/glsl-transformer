package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Identifier extends ASTNode {
  public String name;

  public Identifier(String name) {
    this.name = name;
  }

  public static Identifier from(TerminalNode node) {
    return new Identifier(node.getText());
  }
}
