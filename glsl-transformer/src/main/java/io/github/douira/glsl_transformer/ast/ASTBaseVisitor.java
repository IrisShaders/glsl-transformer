package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.Identifier;

public class ASTBaseVisitor<R> extends AbstractASTVisitor<R> implements ASTVisitor<R> {
  @Override
  public R visitIdentifier(Identifier identifier) {
    return visitDefault(identifier);
  }

}
