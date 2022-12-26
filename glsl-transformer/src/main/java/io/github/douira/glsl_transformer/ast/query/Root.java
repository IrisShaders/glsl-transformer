package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.query.index.*;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.ast.transform.ASTParser;
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
  public final NodeIndex<?> nodeIndex;

  /**
   * The identifier index is used to query identifiers by their name or prefixes
   * on their name. There are various available identifier index implementations
   * that provide different trade-offs between performance, memory consumption and
   * query capabilities.
   */
  public final IdentifierIndex<?, ?> identifierIndex;

  public static final Supplier<NodeIndex<?>> nodeIndexFactoryDefault = NodeIndex::withUnordered;
  public static final Supplier<IdentifierIndex<?, ?>> identifierIndexFactoryDefault = IdentifierIndex::withOnlyExact;
  public static Supplier<NodeIndex<?>> nodeIndexFactory = nodeIndexFactoryDefault;
  public static Supplier<IdentifierIndex<?, ?>> identifierIndexFactory = identifierIndexFactoryDefault;

  // internal utility state
  private static Deque<Root> activeBuildRoots = new ArrayDeque<>();
  private List<? extends ASTNode> nodeList;
  private boolean activity;

  /**
   * Constructs a new root with the given node and identifier indexes.
   * 
   * @param nodeIndex       The node index
   * @param identifierIndex The identifier index
   */
  public Root(NodeIndex<?> nodeIndex, IdentifierIndex<?, ?> identifierIndex) {
    this.nodeIndex = nodeIndex;
    this.identifierIndex = identifierIndex;
  }

  public Root() {
    this(nodeIndexFactory.get(), identifierIndexFactory.get());
  }

  /**
   * Constructs a new root with the default node and identifier indexes which have
   * the least amount of functionality but are also the most efficient.
   */
  public static Root withExactUnordered() {
    return new Root(NodeIndex.withUnordered(), IdentifierIndex.withOnlyExact());
  }

  public static Root withExactOrdered() {
    return new Root(NodeIndex.withOrdered(), IdentifierIndex.withOnlyExact());
  }

  public static Root withExactOrderedBoth() {
    return new Root(NodeIndex.withOrdered(), IdentifierIndex.withOnlyExact(LinkedHashSet::new));
  }

  public static Root withPrefixUnordered() {
    return new Root(NodeIndex.withUnordered(), PrefixIdentifierIndex.withPrefix());
  }

  public static Root withPrefixOrdered() {
    return new Root(NodeIndex.withOrdered(), PrefixIdentifierIndex.withPrefix());
  }

  public static Root withPrefixOrderedBoth() {
    return new Root(NodeIndex.withOrdered(), PrefixIdentifierIndex.withPrefix(LinkedHashSet::new));
  }

  public static void resetRootFactories() {
    nodeIndexFactory = nodeIndexFactoryDefault;
    identifierIndexFactory = identifierIndexFactoryDefault;
  }

  /**
   * Returns the currently active build root. When nodes are constructed within a
   * build session, this method returns the root of the build session. Nodes can't
   * be constructed with children if they have no root since the children must be
   * registered with the root.
   * 
   * @return the currently active build root
   */
  public static Root getActiveBuildRoot() {
    return activeBuildRoots.peekFirst();
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
    activeBuildRoots.push(instance);
    try {
      return rootConsumer.apply(instance);
    } finally {
      activeBuildRoots.pop();
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
   * Returns the identifier index as a prefix identifier index if it is one.
   * Otherwise, it throws.
   * 
   * @return The prefix identifier index
   */
  public PrefixIdentifierIndex<?, ?> getPrefixIdentifierIndex() {
    if (identifierIndex instanceof PrefixIdentifierIndex<?, ?> index) {
      return index;
    } else {
      throw new IllegalStateException("The identifier index is not a prefix index");
    }
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

  public void unregisterIdentifierRename(Identifier identifier) {
    identifierIndex.remove(identifier);
  }

  public void registerIdentifierRename(Identifier identifier) {
    identifierIndex.add(identifier);
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
   * @return Whether anything was renamed
   */
  public boolean rename(String oldName, String newName) {
    return identifierIndex.rename(oldName, newName);
  }

  /**
   * Processes all target nodes from the given stream with the given consumer.
   * This method ensures there is no concurrent modification of the node index by
   * collecting the stream into a shared list.
   * 
   * @param <T>      The type of the target nodes
   * @param targets  The stream of target nodes to process
   * @param replacer The consumer to process the target nodes with
   * @return Whether anything was processed
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> boolean process(Stream<? extends T> targets, Consumer<? super T> replacer) {
    ensureEmptyNodeList();
    if (targets == null) {
      return false;
    }
    var typedList = (List<T>) nodeList;
    targets.forEach(typedList::add);
    var activity = false;
    for (var node : typedList) {
      if (node != null) {
        replacer.accept(node);
        activity = true;
      }
    }
    return activity;
  }

  /**
   * Processes all identifiers with the given name with the given consumer.
   * 
   * @param name     The name of the identifiers to process
   * @param replacer The consumer to process the identifiers with
   * @return Whether anything was processed
   */
  public boolean process(String name, Consumer<Identifier> replacer) {
    return process(identifierIndex.getStream(name), replacer);
  }

  /**
   * Replaces all reference expressions containing identifiers with the given name
   * with the given replacement expression. Identifiers that are not part of a
   * reference expression are not replaced since an expression would be impossible
   * as a replacement.
   * 
   * @param t          The AST transformer
   * @param name       The name of the identifiers to target
   * @param expression The content of the replacement expression
   */
  public void replaceReferenceExpressions(
      ASTParser t,
      String name,
      String expression) {
    replaceReferenceExpressions(
        t,
        identifierIndex.getStream(name),
        expression);
  }

  /**
   * Replaces all reference expressions containing identifiers with the given name
   * with the given replacement expression. Identifiers that are not part of a
   * reference expression are not replaced since an expression would be impossible
   * as a replacement.
   * 
   * @param t          The AST transformer
   * @param name       The name of the identifiers to target
   * @param expression The content of the replacement expression
   * @return Whether any replacements were made
   */
  public boolean replaceReferenceExpressionsReport(
      ASTParser t,
      String name,
      String expression) {
    return replaceReferenceExpressionsReport(
        t,
        identifierIndex.getStream(name),
        expression);
  }

  /**
   * Replaces all reference expressions containing the given identifiers from the
   * given stream with the given replacement expression.
   * 
   * @param t          The AST transformer
   * @param targets    The stream of identifiers to target
   * @param expression The content of the replacement expression
   */
  public void replaceReferenceExpressions(
      ASTParser t,
      Stream<Identifier> targets,
      String expression) {
    process(targets, identifier -> {
      var parent = identifier.getParent();
      if (!(parent instanceof ReferenceExpression)) {
        return;
      }
      parent.replaceByAndDelete(
          t.parseExpression(identifier, expression));
    });
  }

  /**
   * Replaces all reference expressions containing the given identifiers from the
   * given stream with the given replacement expression.
   * 
   * @param t          The AST transformer
   * @param targets    The stream of identifiers to target
   * @param expression The content of the replacement expression
   * @return Whether any replacements were made
   */
  public boolean replaceReferenceExpressionsReport(
      ASTParser t,
      Stream<Identifier> targets,
      String expression) {
    activity = false;
    process(targets, identifier -> {
      var parent = identifier.getParent();
      if (!(parent instanceof ReferenceExpression)) {
        return;
      }
      parent.replaceByAndDelete(
          t.parseExpression(identifier, expression));
      activity = true;
    });
    return activity;
  }

  /**
   * Replaces all expressions from the given stream with the given replacement
   * expression.
   * 
   * @param t          The AST transformer
   * @param targets    The stream of expressions to target
   * @param expression The content of the replacement expression
   * @return Whether any replacements were made
   */
  public boolean replaceExpressions(
      ASTParser t,
      Stream<? extends Expression> targets,
      String expression) {
    return process(targets, node -> {
      node.replaceByAndDelete(
          t.parseExpression(node, expression));
    });
  }

  /**
   * Replaces all expressions from the given list with the given replacement
   * expression but without storing the targets in an intermediary list under the
   * assumption that this list will not be modified by the replacement.
   * 
   * @param t          The AST transformer
   * @param targets    The list of expressions to target
   * @param expression The content of the replacement expression
   * @return Whether any replacements were made
   */
  public static boolean replaceExpressionsConcurrent(
      ASTParser t,
      List<? extends Expression> targets,
      String expression) {
    for (var node : targets) {
      node.replaceByAndDelete(
          t.parseExpression(node, expression));
    }
    return !targets.isEmpty();
  }

  /**
   * Processes all matches of nodes from the given stream matched with the given
   * matcher.
   * 
   * @param <T>                 The type of the matched nodes
   * @param t                   The AST transformer
   * @param matchTargetChildren The stream of nodes to match
   * @param matcher             The matcher to match the nodes with
   * @param replacer            The consumer to process the matched nodes with
   * @return Whether anything was processed
   */
  public <T extends ASTNode> boolean processMatches(
      ASTParser t,
      Stream<? extends ASTNode> matchTargetChildren,
      Matcher<T> matcher,
      Consumer<? super T> replacer) {
    var matchClass = matcher.getPatternClass();
    return process(matchTargetChildren
        .map(node -> node.getAncestor(matchClass))
        .distinct()
        .filter(matcher::matches),
        replacer);
  }

  /**
   * Processes all matches of nodes in the tree that match the given hinted
   * matcher. The hint is used to identify the nodes to match.
   * 
   * @param <T>           The type of the matched nodes
   * @param t             The AST transformer
   * @param hintedMatcher The matcher to match the nodes with
   * @param replacer      The consumer to process the matched nodes with
   * @return Whether anything was processed
   */
  public <T extends ASTNode> boolean processMatches(
      ASTParser t,
      HintedMatcher<T> hintedMatcher,
      Consumer<? super T> replacer) {
    return processMatches(t,
        identifierIndex.getStream(hintedMatcher.getHint()), hintedMatcher, replacer);
  }

  /**
   * Replaces expressions from the given stream that match the given matcher with
   * new expressions created from the given string.
   * 
   * @param <T>                 The type of the matched expression nodes
   * @param t                   The AST transformer
   * @param matchTargetChildren The stream of nodes to match
   * @param matcher             The matcher to match the nodes with
   * @param expression          The content of the replacement expression
   * @return Whether anything was processed
   */
  public <T extends Expression> boolean replaceExpressionMatches(
      ASTParser t,
      Stream<? extends ASTNode> matchTargetChildren,
      Matcher<T> matcher,
      String expression) {
    var matchClass = matcher.getPatternClass();
    return replaceExpressions(t,
        matchTargetChildren
            .map(node -> node.getAncestor(matchClass))
            .distinct()
            .filter(matcher::matches),
        expression);
  }

  /**
   * Replaces expressions all matches of expression nodes in the tree that match
   * the given hinted matcher with new expressions created from the given string.
   * 
   * @param <T>           The type of the matched expression nodes
   * @param t             The AST transformer
   * @param hintedMatcher The matcher to match the nodes with
   * @param expression    The content of the replacement expression
   * @return Whether anything was processed
   */
  public <T extends Expression> boolean replaceExpressionMatches(
      ASTParser t,
      HintedMatcher<T> hintedMatcher,
      String expression) {
    return replaceExpressionMatches(t,
        identifierIndex.getStream(hintedMatcher.getHint()), hintedMatcher, expression);
  }
}
