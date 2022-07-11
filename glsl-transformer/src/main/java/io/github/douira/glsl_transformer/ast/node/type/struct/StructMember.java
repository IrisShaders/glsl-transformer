package io.github.douira.glsl_transformer.ast.node.type.struct;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class StructMember extends InnerASTNode {
  protected FullySpecifiedType type;
  public final List<StructDeclarator> declarators;

  public StructMember(FullySpecifiedType type, List<StructDeclarator> declarators) {
    this.type = setup(type);
    this.declarators = new ChildNodeList<>(declarators, this);
  }

  public StructMember(FullySpecifiedType type, Stream<StructDeclarator> declarators) {
    this.type = setup(type);
    this.declarators = ChildNodeList.collect(declarators, this);
  }

  public FullySpecifiedType getType() {
    return type;
  }

  public void setType(FullySpecifiedType type) {
    updateParents(this.type, type);
    this.type = type;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitStructMember(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterStructMember(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitStructMember(this);
  }
}
