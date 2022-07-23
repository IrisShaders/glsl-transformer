package io.github.douira.glsl_transformer.ast.query;

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
  void iterateClasses(
      ASTNode node,
      BiConsumer<Class<? extends ASTNode>, ASTNode> consumer) {
    Class<? extends ASTNode> clazz = node.getClass();
    while (clazz != null
        && clazz != InnerASTNode.class
        && clazz != ASTNode.class
        && clazz != ListASTNode.class) {
      consumer.accept(clazz, node);
      clazz = (Class<? extends ASTNode>) clazz.getSuperclass();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void add(ASTNode node) {
    iterateClasses(node, (clazz, toAdd) -> {
      var set = (Set<ASTNode>) index.get(clazz);
      if (set == null) {
        set = bucketConstructor.get();
        index.put(clazz, set);
      }
      set.add(toAdd);
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void remove(ASTNode node) {
    iterateClasses(node, (clazz, toAdd) -> {
      var set = (Set<ASTNode>) index.get(clazz);
      if (set == null) {
        return;
      }
      set.remove(toAdd);
    });
  }
}
