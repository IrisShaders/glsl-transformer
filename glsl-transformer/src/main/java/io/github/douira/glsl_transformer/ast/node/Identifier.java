package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class Identifier extends ASTNode {
  public String name;

  public Identifier(String name) {
    this.name = name;
  }

  public Identifier(Token token) {
    this(token.getText());
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitIdentifier(this);
  }
}
