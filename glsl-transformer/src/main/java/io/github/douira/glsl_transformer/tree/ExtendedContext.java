package io.github.douira.glsl_transformer.tree;

import java.util.LinkedList;
import java.util.Optional;

import com.github.bsideup.jabel.Desugar;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import io.github.douira.glsl_transformer.print.CachingIntervalSet;

/**
 * Implements custom behavior in parse rule contexts. This class is used as the
 * super class for all contexts in the parser.
 * 
 * This class is not meant to be constructed manually but is the base class
 * which ANTLR extends in the generated parser code.
 * 
 * The token indexes of the token intervals of new nodes have nothing to do with
 * the token indexes of existing nodes. Therefore, new nodes are registered as
 * local roots that have their own token index domain and a set of intervals in
 * which non-hidden tokens should not be printed.
 * 
 * Local root nodes store information about which nodes in their subtree have
 * been removed. The printer then uses this to not print any non-hidden tokens
 * contained within any local root's omission set.
 */
public abstract class ExtendedContext extends ParserRuleContext implements TreeMember {
  @Desugar
  private static record LocalRoot(CachingIntervalSet omissionSet, BufferedTokenStream tokenStream) {
    LocalRoot(BufferedTokenStream tokenStream) {
      this(new CachingIntervalSet(), tokenStream);
    }
  };

  private ParseTree previousNode;

  /**
   * A reference to a node closer to the local root. For local roots or the
   * root node this is the node itself. For all other nodes it can actually be the
   * local root or some other node further up the tree (without skipping a local
   * root).
   */
  private ExtendedContext localRootRef;

  /**
   * A reference to the tree's global root node. This is not a node closer to it,
   * but the a actual root node. This is initialized to the current node and then
   * later copied from the parent if this is a local root.
   * 
   * This is only correct for local root nodes. Other nodes look up their local
   * root and then check its root reference.
   */
  private ExtendedContext root = this;

  /**
   * If this node is the root node and it has been set to be readonly.
   */
  private boolean rootAndReadonly = false;

  /**
   * The local root data combines an omission set and a token stream.
   * 
   * @see #getOmissionSet()
   * @see #getTokenStream()
   */
  private Optional<LocalRoot> localRoot = Optional.empty();

  /**
   * Creates a new extended parser rule context. This is required for the
   * generated parse code to be valid.
   * 
   * @param parent              The parent node
   * @param invokingStateNumber The invoking state number
   */
  public ExtendedContext(ParserRuleContext parent, int invokingStateNumber) {
    super(parent, invokingStateNumber);
    updateRoot();
  }

  /**
   * Creates a new extended parser rule context without a parent. This is also
   * used in the generated code.
   */
  public ExtendedContext() {
    updateRoot();
  }

  @Override
  public ParseTree getPreviousNode() {
    return previousNode;
  }

  @Override
  public void setPreviousNode(ParseTree previousNode) {
    this.previousNode = previousNode;
  }

  /** Override to make type more specific */
  @Override
  public ExtendedContext getParent() {
    return (ExtendedContext) super.getParent();
  }

  private void updateRoot() {
    if (parent != null) {
      root = ((ExtendedContext) parent).root;
    }
  }

  /**
   * Sets the parent and copies the root reference from the parent. This requires
   * the parent to have been set up before the child.
   * 
   * @param parent The paren to set on this node
   */
  public void setParent(ExtendedContext parent) {
    super.setParent(parent);
    updateRoot();
  }

  /**
   * Registers this node as a new local root node together with its
   * corresponding token stream. This means it has its own omission set and token
   * interval space. The token interval of omitted nodes are added to the omission
   * set of the closest local root ancestor.
   * 
   * @param tokenStream The token stream from which this node was parsed
   */
  public void makeLocalRoot(BufferedTokenStream tokenStream) {
    // stop if already set up
    if (localRoot.isPresent() && localRootRef == this) {
      return;
    }

    localRoot = Optional.of(new LocalRoot(tokenStream));
    localRootRef = this;
  }

  /**
   * Checks if this node is a local root.
   * 
   * @return {@code true} if the node is a local root
   */
  public boolean isLocalRoot() {
    return localRootRef == this;
  }

  /**
   * Finds the local root for this node. If this node is a local root, then this
   * method returns this node. Otherwise it traverses this node's ancestors and
   * returns the first local root it finds. At the end it points all local root
   * pointers to the found local root in order to speed up future local root
   * queries.
   * 
   * @return The local root corresponding to this node
   */
  private ExtendedContext getLocalRoot() {
    if (isLocalRoot()) {
      return this;
    }

    ExtendedContext node = this;
    var traversedAncestors = new LinkedList<ExtendedContext>();
    while (node.localRootRef != node) {
      traversedAncestors.add(node);

      // the root node has a null parent
      var parent = node.getParent();
      if (parent == null) {
        break;
      } else {
        node = parent;
      }
    }

    for (var traversedNode : traversedAncestors) {
      traversedNode.localRootRef = node;
    }

    return node;
  }

  /**
   * Checks if this node is the global root.
   * 
   * @implNote This is faster to check than finding the local root, taking it's
   *           root and checking if it's the same.
   * 
   * @return {@code true} if this node is the global root
   */
  public boolean isRoot() {
    return isLocalRoot() && root == this;
  }

  /**
   * Find the tree's global root by checking the enclosing local root's reference.
   * 
   * @return
   */
  private ExtendedContext getRoot() {
    return getLocalRoot().root;
  }

  /**
   * Marks the tokens encompassed by this node as omitted. Non-hidden tokens will
   * not be printed if they are withing the token source token interval of this
   * node. Other nodes will however, still be printed in order to preserve
   * whitespace.
   */
  @Override
  public void omitTokens() {
    omitTokens(getSourceInterval());
  }

  /**
   * Omits the given token interval on this node's local root.
   * 
   * @see #omitTokens()
   * 
   * @param tokenInterval The token interval to be omitted
   */
  public void omitTokens(Interval tokenInterval) {
    if (isTreeReadonly()) {
      throw new IllegalStateException("Can't add intervals to the omission set if editing is already finished!");
    }

    getLocalRoot().getOmissionSet().add(tokenInterval);
  }

  private boolean isTreeReadonly() {
    return getRoot().rootAndReadonly;
  }

  /**
   * Marks editing on the whole tree as finished. This modifies the root node's
   * readonly state. After this point no further modifications should be made.
   */
  public void finishEditingTree() {
    getRoot().rootAndReadonly = true;
  }

  /**
   * The omission set is present if this node is a local root. Then it contains
   * the token intervals that should be omitted by the printer when printing this
   * local root's subtree.
   * 
   * The omission set is set to be readonly if the tree has been set to readonly.
   * 
   * @return This local root's token stream if this node is a local root,
   *         {@code null} otherwise
   */
  public CachingIntervalSet getOmissionSet() {
    if (localRoot.isPresent()) {
      var omissionSet = localRoot.get().omissionSet();
      if (isTreeReadonly()) {
        omissionSet.setReadonly(true);
      }
      return omissionSet;
    } else {
      return null;
    }
  }

  /**
   * The token stream is present if this node is a local root. Then it's the token
   * stream that was used to construct this local root's subtree.
   * 
   * @return The token stream for this local root if this node is a local root,
   *         {@code null} otherwise
   */
  public BufferedTokenStream getTokenStream() {
    return localRoot.map(LocalRoot::tokenStream).get();
  }

  /**
   * Gets the source interval for this local root that includes the whole token
   * stream with unparsed tokens before and after the parsed tokens that this node
   * encompasses.
   * 
   * @return The full source interval of the contained token stream if this node
   *         is a local root, {@code null} otherwise.
   */
  public Interval getFullSourceInterval() {
    return localRoot.map(
        localRoot -> Interval.of(0, localRoot.tokenStream().size() - 1)).get();
  }

  /**
   * Gets the source interval for this node that covers the largest space. For
   * local roots this is the full source interval covering all tokens in the token
   * stream. For all other nodes it's the regular source interval covering only
   * the tokens parsed for this node.
   * 
   * @return The largest source interval, never {@code null}.
   */
  public Interval getLargestSourceInterval() {
    return localRoot.isPresent() ? getFullSourceInterval() : getSourceInterval();
  }

  /**
   * Finds the index of the first contained child that matches the given type.
   * 
   * @param ctxType The child type to look for
   * @return The index of the first child with the given index. If no child with
   *         that type was found, the length of the child array is returned as the
   *         "last" index.
   */
  public int getChildIndexLike(Class<? extends ParseTree> ctxType) {
    var i = 0;
    while (i < getChildCount() && !ctxType.isInstance(getChild(i))) {
      i++;
    }
    return i;
  }

  /**
   * Adds a child to the list of children with at the given index.
   * 
   * @param index The index to add the node at
   * @param node  The node to add
   */
  public void addChild(int index, ParseTree node) {
    node.setParent(this);
    children.add(index, node);
  }
}
