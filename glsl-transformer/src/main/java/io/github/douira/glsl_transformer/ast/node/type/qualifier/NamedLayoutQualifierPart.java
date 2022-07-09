package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class NamedLayoutQualifierPart extends LayoutQualifierPart {
  protected Identifier name;
  protected Expression expression; // TODO: nullable

  public NamedLayoutQualifierPart(Identifier name, Expression expression) {
    this.name = setup(name);
    this.expression = setup(expression);
  }

  public NamedLayoutQualifierPart(Identifier name) {
    this.name = setup(name);
  }

  @Override
  public LayoutQualifierType getLayoutQualifierType() {
    return LayoutQualifierType.NAMED;
  }

  @Override
  public <R> R layoutQualifierPartAccept(ASTVisitor<R> visitor) {
    return visitor.visitNamedLayoutQualifierPart(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterNamedLayoutQualifierPart(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitNamedLayoutQualifierPart(this);
  }
}
