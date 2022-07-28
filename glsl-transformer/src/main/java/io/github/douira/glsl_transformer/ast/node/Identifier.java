package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class Identifier extends ASTNode {
  private String name;

  public Identifier(String name) {
    this.name = name;
    validateContents(name);
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
    validateContents(name);
    getRoot().identifierIndex.remove(this);
    this.name = name;
    getRoot().identifierIndex.add(this);
  }

  private void validateContents(String str) {
    // verify identifier contents to be only a-z, A-Z, 0-9 and _
    // and does not start with a digit
    if (str.length() == 0) {
      throw new IllegalArgumentException("Identifier cannot be empty");
    }
    if (!Character.isLetter(str.charAt(0)) && str.charAt(0) != '_') {
      throw new IllegalArgumentException("Identifier must start with a letter or underscore");
    }
    for (int i = 1; i < str.length(); i++) {
      char c = str.charAt(i);
      if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_')) {
        throw new IllegalArgumentException("Invalid identifier name: " + str);
      }
    }
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitIdentifier(this);
  }
}
