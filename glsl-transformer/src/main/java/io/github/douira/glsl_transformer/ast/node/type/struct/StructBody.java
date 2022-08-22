package io.github.douira.glsl_transformer.ast.node.type.struct;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ListASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class StructBody extends ListASTNode<StructMember> {
  public StructBody(Stream<StructMember> children) {
    super(children);
  }

  public List<StructMember> getMembers() {
    return getChildren();
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitStructBody(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterStructBody(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitStructBody(this);
  }

  @Override
  public StructBody clone() {
    return (StructBody) super.clone();
  }

  @Override
  public StructBody cloneInto(Root root) {
    return (StructBody) super.cloneInto(root);
  }

  @Override
  public StructBody cloneSeparate() {
    return (StructBody) super.cloneSeparate();
  }
}
