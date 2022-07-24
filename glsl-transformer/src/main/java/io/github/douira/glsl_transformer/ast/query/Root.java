package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.query.index.*;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.ast.transform.ASTTransformer;
import io.github.douira.glsl_transformer.util.Passthrough;

/**
 * Each AST has a root referenced by all its members. This class manages the
 * indexes of the tree and is updated each time a node is added or removed from
 * the tree. It also contains methods for indexing nodes for building a tree and
 * for performing replacement operations on the tree using the stored indexes.
 */
public class Root {
  /**
   * The node index is used to query nodes by their type. There are various
   * available node index implementations that provide different trade-offs
   * between performance, memory consumption and query capabilities.
   */
  public final NodeIndex nodeIndex;

  /**
   * The identifier index is used to query identifiers by their name or prefixes
   * on their name. There are various available identifier index implementations
   * that provide different trade-offs between performance, memory consumption and
   * query capabilities.
   */
  public final IdentifierIndex<?> identifierIndex;
  private List<? extends ASTNode> nodeList;

  private static Deque<Root> activeBuildRoots = new ArrayDeque<>();

  /**
   * Returns the currently active build root. When nodes are constructed within a
   * build session, this method returns the root of the build session. Nodes can't
   * be constructed with children if they have no root since the children must be
   * registered with the root.
   * 
   * @return the currently active build root
   */
  public static Root getActiveBuildRoot() {
    return activeBuildRoots.peekLast();
  }

  /**
   * Runs the given consumer with the given root as the active build root.
   * 
   * @param <R>          The return type of the consumer
   * @param instance     The root to run the consumer with
   * @param rootConsumer The consumer to run
   * @return The return value of the consumer
   */
  protected static final synchronized <R> R withActiveBuildRoot(
      Root instance,
      Function<Root, R> rootConsumer) {
    activeBuildRoots.addLast(instance);
    try {
      return rootConsumer.apply(instance);
    } finally {
      activeBuildRoots.removeLast();
    }
  }

  /**
   * Runs the given builder supplier with the given root as the active build root
   * and then registers the returned node with the root. This is necessary since
   * the node will have the correct root but will not be registered until it is
   * either manually registered as it is being done here or added as a child to
   * another node with the root.
   * 
   * @param <NodeType> The type of the node to build
   * @param instance   The root to run the builder with
   * @param builder    The builder to run
   * @return The built and registered node
   */
  public static synchronized <NodeType extends ASTNode> NodeType indexNodes(
      Root instance, Supplier<NodeType> builder) {
    return withActiveBuildRoot(instance, root -> {
      var result = builder.get();
      root.registerNode(result);
      return result;
    });
  }

  /**
   * Runs the given builder supplier with a new root as the active build root.
   * 
   * @param <NodeType> The type of the node to build
   * @param builder    The builder to run
   * @return The built and registered node
   */
  public static <NodeType extends ASTNode> NodeType indexNodes(
      Supplier<NodeType> builder) {
    return indexNodes(new Root(), builder);
  }

  /**
   * Runs the given builder supplier with the same root as a given tree node as
   * the active build root.
   * 
   * @param <NodeType>       The type of the node to build
   * @param parentTreeMember The tree member to get the root from
   * @param builder          The builder to run
   * @return The built and registered node
   */
  public static <NodeType extends ASTNode> NodeType indexNodes(
      ASTNode parentTreeMember, Supplier<NodeType> builder) {
    return indexNodes(parentTreeMember.getRoot(), builder);
  }

  /**
   * Runs a given runnable with the given root as the active build root. This is
   * used for constructing nodes with children without registering the constructed
   * root node with the root or for registering it manually.
   * 
   * @param instance The root to use as the active build root
   * @param session  The runnable to run
   */
  public static synchronized void indexBuildSession(Root instance, Runnable session) {
    withActiveBuildRoot(instance, root -> {
      session.run();
      return null;
    });
  }

  /**
   * Runs the given runnable with a new root as the active build root.
   * 
   * @param session The runnable to run
   */
  public static void indexBuildSession(Runnable session) {
    indexBuildSession(new Root(), session);
  }

  /**
   * Runs the given runnable with the same root as a given tree node as the active
   * build root.
   * 
   * @param treeMember The tree member to get the root from
   * @param session    The runnable to run
   */
  public static void indexBuildSession(ASTNode treeMember, Runnable session) {
    indexBuildSession(treeMember.getRoot(), session);
  }

  /**
   * Runs the given consumer of a registration pass-through function with the
   * given root as the active build root. The consumer receives a pass-through
   * function that takes a constructed node and registers it with the root. This
   * is helpful for constructing trees manually and registering them inline.
   * 
   * @param <NodeType>         The type of the nodes to register
   * @param instance           the root to register the nodes with
   * @param registererConsumer The consumer to run
   */
  public static synchronized <NodeType extends ASTNode> void indexSeparateTrees(
      Root instance, Consumer<Passthrough<NodeType>> registererConsumer) {
    withActiveBuildRoot(instance, root -> {
      registererConsumer.accept(Passthrough.of(root::registerNode));
      return null;
    });
  }

  /**
   * Runs the given consumer of a registration pass-through function with a new
   * root as the active build root.
   * 
   * @param <NodeType> The type of the nodes to register
   * @param registerer The consumer to run
   */
  public static <NodeType extends ASTNode> void indexSeparateTrees(
      Consumer<Passthrough<NodeType>> registerer) {
    indexSeparateTrees(new Root(), registerer);
  }

  /**
   * Runs the given consumer of a registration pass-through function with the same
   * root as a given tree node as the active build root.
   * 
   * @param <NodeType> The type of the nodes to register
   * @param treeMember The tree member to get the root from
   * @param registerer The consumer to run
   */
  public static <NodeType extends ASTNode> void indexSeparateTrees(
      ASTNode treeMember, Consumer<Passthrough<NodeType>> registerer) {
    indexSeparateTrees(treeMember.getRoot(), registerer);
  }

  /**
   * Constructs a new root with the given node and identifier indexes.
   * 
   * @param nodeIndex       The node index
   * @param identifierIndex The identifier index
   */
  public Root(NodeIndex nodeIndex, IdentifierIndex<?> identifierIndex) {
    this.nodeIndex = nodeIndex;
    this.identifierIndex = identifierIndex;
  }

  /**
   * Constructs a new root with the default node and identifier indexes which have
   * the least amount of functionality but are also the most efficient.
   */
  public Root() {
    this(new NodeIndex(), IdentifierIndex.withPrefix());
  }

  /**
   * Registers the given node with this root.
   * 
   * @param node The node to register
   */
  public void registerNode(ASTNode node) {
    nodeIndex.add(node);
    if (node instanceof Identifier identifier) {
      identifierIndex.add(identifier);
    }
  }

  /**
   * Unregisters the given node from this root.
   * 
   * @param node The node to unregister
   */
  public void unregisterNode(ASTNode node) {
    nodeIndex.remove(node);
    if (node instanceof Identifier identifier) {
      identifierIndex.remove(identifier);
    }
  }

  /**
   * Merges the given root into this root. This will merge the node index and the
   * identifier index.
   * 
   * @param other The root to merge into this root
   */
  public void merge(Root other) {
    nodeIndex.merge(other.nodeIndex);
    identifierIndex.merge(other.identifierIndex);
  }

  private void ensureEmptyNodeList() {
    if (nodeList == null) {
      nodeList = new ArrayList<>();
    } else {
      nodeList.clear();
    }
  }

  /**
   * Renames all identifiers with the given old name to the given new name.
   * 
   * @param oldName The old name
   * @param newName The new name
   */
  @SuppressWarnings("unchecked")
  public void rename(String oldName, String newName) {
    // rename all uses a fast path without a lambda
    ensureEmptyNodeList();
    var set = identifierIndex.get(oldName);
    if (set == null) {
      return;
    }
    var identifierList = (List<Identifier>) nodeList;
    identifierList.addAll(set);
    for (var identifier : identifierList) {
      identifier.setName(newName);
    }
  }

  /**
   * Processes all target nodes from the given stream with the given consumer.
   * This method ensures there is no concurrent modification of the node index by
   * collecting the stream into a shared list.
   * 
   * @param <T>      The type of the target nodes
   * @param targets  The stream of target nodes to process
   * @param replacer The consumer to process the target nodes with
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> void process(Stream<? extends T> targets, Consumer<? super T> replacer) {
    ensureEmptyNodeList();
    if (targets == null) {
      return;
    }
    var typedList = (List<T>) nodeList;
    targets.forEach(typedList::add);
    for (var node : typedList) {
      if (node != null) {
        replacer.accept(node);
      }
    }
  }

  /**
   * Processes all identifiers with the given name with the given consumer.
   * 
   * @param name     The name of the identifiers to process
   * @param replacer The consumer to process the identifiers with
   */
  public void process(String name, Consumer<Identifier> replacer) {
    process(identifierIndex.getStream(name), replacer);
  }

  /**
   * Replaces all reference expressions containing identifiers with the given name
   * with the given replacement expression. Identifiers that are not part of a
   * reference expression are not replaced since an expression would be impossible
   * as a replacement.
   * 
   * @param transformer       The AST transformer
   * @param name              The name of the identifiers to target
   * @param expressionContent The content of the replacement expression
   */
  public void replaceReferenceExpressions(
      ASTTransformer<?> transformer,
      String name,
      String expressionContent) {
    replaceReferenceExpressions(
        transformer,
        identifierIndex.getStream(name),
        expressionContent);
  }

  /**
   * Replaces all reference expressions containing the given identifiers from the
   * given stream with the given replacement expression.
   * 
   * @param transformer       The AST transformer
   * @param targets           The stream of identifiers to target
   * @param expressionContent The content of the replacement expression
   */
  public void replaceReferenceExpressions(
      ASTTransformer<?> transformer,
      Stream<Identifier> targets,
      String expressionContent) {
    process(targets, identifier -> {
      var parent = identifier.getParent();
      if (!(parent instanceof ReferenceExpression)) {
        return;
      }
      parent.replaceByAndDelete(
          transformer.parseExpression(identifier, expressionContent));
    });
  }

  /**
   * Replaces all expressions from the given stream with the given replacement
   * expression.
   * 
   * @param transformer       The AST transformer
   * @param targets           The stream of expressions to target
   * @param expressionContent The content of the replacement expression
   */
  public void replaceExpressions(
      ASTTransformer<?> transformer,
      Stream<? extends Expression> targets,
      String expressionContent) {
    process(targets, expression -> {
      expression.replaceByAndDelete(
          transformer.parseExpression(expression, expressionContent));
    });
  }

  /**
   * Replaces all expressions from the given list with the given replacement
   * expression but without storing the targets in an intermediary list under the
   * assumption that this list will not be modified by the replacement.
   * 
   * @param transformer       The AST transformer
   * @param targets           The list of expressions to target
   * @param expressionContent The content of the replacement expression
   */
  public static void replaceExpressionsConcurrent(
      ASTTransformer<?> transformer,
      List<? extends Expression> targets,
      String expressionContent) {
    for (var node : targets) {
      node.replaceByAndDelete(
          transformer.parseExpression(node, expressionContent));
    }
  }

  public <T extends ASTNode> void processMatches(
      ASTTransformer<?> transformer,
      Stream<? extends ASTNode> matchTargetChildren,
      Matcher<T> matcher,
      Consumer<? super T> replacer) {
    var matchClass = matcher.getPatternClass();
    process(matchTargetChildren
        .map(node -> node.getAncestor(matchClass))
        .distinct()
        .filter(matcher::matches),
        replacer);
  }

  public <T extends ASTNode> void processMatches(
      ASTTransformer<?> transformer,
      String matchHint,
      Matcher<T> matcher,
      Consumer<? super T> replacer) {
    processMatches(transformer, identifierIndex.getStream(matchHint), matcher, replacer);
  }

  public <T extends ASTNode> void processMatches(
      ASTTransformer<?> transformer,
      HintedMatcher<T> hintedMatcher,
      Consumer<? super T> replacer) {
    processMatches(transformer, hintedMatcher.hint, hintedMatcher, replacer);
  }

  public <T extends Expression> void replaceExpressionMatches(
      ASTTransformer<?> transformer,
      Stream<? extends ASTNode> matchTargetChildren,
      Matcher<T> matcher,
      String expressionContent) {
    var matchClass = matcher.getPatternClass();
    replaceExpressions(transformer,
        matchTargetChildren
            .map(node -> node.getAncestor(matchClass))
            .distinct()
            .filter(matcher::matches),
        expressionContent);
  }

  public <T extends Expression> void replaceExpressionMatches(
      ASTTransformer<?> transformer,
      String matchHint,
      Matcher<T> matcher,
      String expressionContent) {
    replaceExpressionMatches(transformer, identifierIndex.getStream(matchHint), matcher, expressionContent);
  }

  public <T extends Expression> void replaceExpressionMatches(
      ASTTransformer<?> transformer,
      HintedMatcher<T> hintedMatcher,
      String expressionContent) {
    replaceExpressionMatches(transformer, hintedMatcher.hint, hintedMatcher, expressionContent);
  }
}
