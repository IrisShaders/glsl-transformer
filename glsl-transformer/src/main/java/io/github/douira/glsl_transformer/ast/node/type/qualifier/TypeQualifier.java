package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ListASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TypeQualifier extends ListASTNode<TypeQualifierPart> {
  public TypeQualifier(Stream<TypeQualifierPart> children) {
    super(children);
  }

  public List<TypeQualifierPart> getParts() {
    return getChildren();
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitTypeQualifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTypeQualifier(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTypeQualifier(this);
  }
}
