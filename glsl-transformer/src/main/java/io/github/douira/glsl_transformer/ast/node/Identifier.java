package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.ast.*;

public class Identifier extends ASTNode {
  public String name;

  public Identifier(String name) {
    this.name = name;
  }

  public static Identifier from(TerminalNode node) {
    return new Identifier(node.getText());
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitIdentifier(this);
  }
}
