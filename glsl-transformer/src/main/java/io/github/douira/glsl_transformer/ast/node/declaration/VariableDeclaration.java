package io.github.douira.glsl_transformer.ast.node.declaration;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.TypeQualifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class VariableDeclaration extends Declaration {
  protected TypeQualifier typeQualifier;
  public final List<Identifier> names;

  public VariableDeclaration(TypeQualifier typeQualifier, Stream<Identifier> names) {
    this.typeQualifier = typeQualifier;
    this.names = ChildNodeList.collect(names, this);
  }

  public VariableDeclaration(TypeQualifier typeQualifier) {
    this(typeQualifier, Stream.empty());
  }

  public TypeQualifier getTypeQualifier() {
    return typeQualifier;
  }

  public void setTypeQualifier(TypeQualifier typeQualifier) {
    updateParents(this.typeQualifier, typeQualifier);
    this.typeQualifier = typeQualifier;
  }

  @Override
  public DeclarationType getDeclarationType() {
    return DeclarationType.VARIABLE;
  }

  @Override
  public <R> R declarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitVariableDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterVariableDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitVariableDeclaration(this);
  }
}
