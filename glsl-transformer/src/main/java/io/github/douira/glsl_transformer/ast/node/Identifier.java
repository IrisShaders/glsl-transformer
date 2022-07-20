package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class Identifier extends ASTNode {
  private String name;

  public Identifier(String name) {
    this.name = name;
  }

  public Identifier(Token token) {
    this(token.getText());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (this.name.equals(name)) {
      return;
    }
    getRoot().identifierIndex.remove(this);
    this.name = name;
    getRoot().identifierIndex.add(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitIdentifier(this);
  }
}
