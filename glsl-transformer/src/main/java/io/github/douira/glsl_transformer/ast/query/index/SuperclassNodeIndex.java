package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.*;

import io.github.douira.glsl_transformer.ast.node.basic.*;

/**
 * The superclass node index also creates index entries for the superclasses for
 * all nodes. This means querying for Expression returns all nodes that extend
 * Expression.
 */
public class SuperclassNodeIndex extends NodeIndex {
  public SuperclassNodeIndex(Supplier<Set<ASTNode>> bucketConstructor) {
    super(bucketConstructor);
  }

  public SuperclassNodeIndex() {
  }

  public static SuperclassNodeIndex withHashSetBuckets() {
    return new SuperclassNodeIndex(HashSet::new);
  }

  public static SuperclassNodeIndex withLinkedHashSetBuckets() {
    return new SuperclassNodeIndex(LinkedHashSet::new);
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
        set = bucketConstructor.get();
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
