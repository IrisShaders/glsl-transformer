package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.*;

/**
 * The superclass node index also creates index entries for the superclasses for
 * all nodes. This means querying for Expression returns all nodes that extend
 * Expression.
 */
public class SuperclassNodeIndex<S extends Set<ASTNode>> extends NodeIndex<S> {
  public SuperclassNodeIndex(Supplier<S> setFactory) {
    super(setFactory);
  }

  public static SuperclassNodeIndex<HashSet<ASTNode>> withUnordered() {
    return new SuperclassNodeIndex<HashSet<ASTNode>>(HashSet::new);
  }

  public static SuperclassNodeIndex<LinkedHashSet<ASTNode>> withOrdered() {
    return new SuperclassNodeIndex<LinkedHashSet<ASTNode>>(LinkedHashSet::new);
  }

  @SuppressWarnings("unchecked")
  protected void iterateClasses(
      ASTNode node,
      BiConsumer<Class<? extends ASTNode>, ASTNode> consumer) {
    Class<? extends ASTNode> nodeClass = node.getClass();
    while (nodeClass != null
        && nodeClass != InnerASTNode.class
        && nodeClass != ASTNode.class
        && nodeClass != ListASTNode.class) {
      consumer.accept(nodeClass, node);
      nodeClass = (Class<? extends ASTNode>) nodeClass.getSuperclass();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void add(ASTNode node) {
    iterateClasses(node, (nodeClass, toAdd) -> {
      var set = (Set<ASTNode>) index.get(nodeClass);
      if (set == null) {
        set = setFactory.get();
        index.put((Class<ASTNode>)nodeClass, set);
      }
      set.add(toAdd);
    });
  }

  @Override
  public void remove(ASTNode node) {
    iterateClasses(node, (nodeClass, toAdd) -> {
      var set = (Set<ASTNode>) index.get(nodeClass);
      if (set == null) {
        return;
      }
      set.remove(toAdd);
    });
  }
}
