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

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    updateParents(this.name, name);
    this.name = name;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    updateParents(this.expression, expression);
    this.expression = expression;
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
    super.enterNode(listener);
    listener.enterNamedLayoutQualifierPart(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitNamedLayoutQualifierPart(this);
  }
}
