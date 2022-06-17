package io.github.douira.glsl_transformer.ast;

import java.util.List;

public abstract class ListlikeASTNode<Child extends ASTNode> extends InnerASTNode implements List<Child> {

}
