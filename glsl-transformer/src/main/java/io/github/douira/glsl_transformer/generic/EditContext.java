package io.github.douira.glsl_transformer.generic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The edit context keeps track of all changes made to a parse tree. It stores
 * which tokens should be omitted during printing and which nodes have been
 * added in the mean time. This is necessary, because the token indexes of the
 * token intervals of new nodes have nothing to do with the token indexes of
 * existing nodes. Therefore, new nodes are registered as "local roots" that
 * have their own token index domain and a set of intervals in which non-hidden
 * tokens should not be printed.
 * 
 * An edit context is tied to a specific parse tree and is passed around for
 * making modifications to it. They are effectively part of the same
 * data structure with the parse tree containing ANTLR's things and the edit
 * context storing the modification information.
 */
public class EditContext {
  /**
   * A local root data record contains the omission set and token stream of a
   * registered local root.
   */
  public record LocalRootData(CachingIntervalSet omissionSet, BufferedTokenStream tokenStream) {
  };

  /**
   * A map from local root nodes to their data which includes an omission set
   * for nodes that were removed from inside their subtrees and a token stream
   * that was generated from parsing the string they were created from.
   */
  private Map<ParseTree, LocalRootData> localRootData = new HashMap<>();

  /**
   * A map from nodes to their corresponding local roots. This datastructure is
   * similar to a union-find structure with path compression.
   */
  private Map<ParseTree, ParseTree> localRootPointers = new HashMap<>();

  private boolean editingFinished = false;

  /**
   * Creates a new edit context with a global root node and a global root token
   * stream. This is the same as creating an empty edit context (which is
   * disallowed) and registering the global root node as a local root node because
   * they are effectively the same.
   * 
   * @param root            The global root node of the parse tree
   * @param rootTokenStream The token stream from which the root node was parsed
   */
  public EditContext(ParseTree root, BufferedTokenStream rootTokenStream) {
    registerLocalRoot(root, rootTokenStream);
  }

  /**
   * Marks the editing on this edit context as finished. After this point no
   * further modifications should be made. Because this sets all omission sets of
   * the local roots to be readonly for better caching performance, making
   * modifications after the edit context has been marked as finished can throw an
   * exception.
   */
  public void finishEditing() {
    editingFinished = true;
    for (var value : localRootData.values()) {
      value.omissionSet().setReadonly(true);
    }
  }

  /**
   * Returns the local root data for a given local root node. This only works for
   * nodes registered as local roots and will fail otherwise.
   * 
   * @param localRoot The node to get the local root data for
   * @return The local root data for the given node
   */
  LocalRootData getLocalRootData(ParseTree localRoot) {
    return localRootData.get(localRoot);
  }

  /**
   * Checks if the given node has been registered as a local root. Since there is
   * nothing about a local root node that conveys this information, this method
   * allows checking for the local root property.
   * 
   * @param node The node to check
   * @return {@code true} if the node is a local root
   */
  boolean isLocalRoot(ParseTree node) {
    return localRootData.containsKey(node);
  }

  /**
   * Registers a given node as a new local root node together with its
   * corresponding token stream. This means it has its own omission set and token
   * interval space. Omissions of certain nodes are added to the omission set of
   * the closest local root ancestor.
   * 
   * @param localRoot   The node to register as a local root
   * @param tokenStream The token stream from which this node was parsed
   */
  public void registerLocalRoot(ParseTree localRoot, BufferedTokenStream tokenStream) {
    localRootPointers.put(localRoot, localRoot);
    localRootData.put(localRoot, new LocalRootData(new CachingIntervalSet(), tokenStream));
  }

  /**
   * Finds the local root for any given node. If the node is a local root itself,
   * the node itself is returned.
   * 
   * @param node The node to find the local root for
   * @return The local root for the given node. {@code null} if the node is
   *         outside the tree or has no parent-path to a local root.
   */
  ParseTree findLocalRootAncestor(ParseTree node) {
    ParseTree localRoot = null;
    var traversedAncestors = new LinkedList<ParseTree>();
    while (localRoot == null) {
      if (node == null) {
        return null;
      }
      traversedAncestors.add(node);
      localRoot = localRootPointers.get(node);
      node = node.getParent();
    }

    // at the end all the traversed ancestors are also cached (path compression)
    // so that the work done in traversal can be reused
    for (var traversedNode : traversedAncestors) {
      localRootPointers.put(traversedNode, localRoot);
    }

    return localRoot;
  }

  /**
   * Marks the tokens encompassed by the given node as omitted in this edit
   * context. Non-hidden tokens will not be printed if they are withing the token
   * source token interval of the node. Other nodes will however, still be printed
   * in order to preserve whitespace.
   * 
   * @param node The node for which the tokens should should be omitted
   */
  public void omitNodeTokens(ParseTree node) {
    if (editingFinished) {
      throw new IllegalStateException("Can't add intervals to editing context when editing is already finished!");
    }

    var interval = node.getSourceInterval();
    localRootData.get(findLocalRootAncestor(node)).omissionSet().add(interval.a, interval.b);
  }
}
