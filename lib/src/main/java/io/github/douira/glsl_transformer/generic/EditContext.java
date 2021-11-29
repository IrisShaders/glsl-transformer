package io.github.douira.glsl_transformer.generic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class EditContext {
  public record LocalRootData(CachingIntervalSet omissionSet, BufferedTokenStream tokenStream) {
  };

  private Map<ParseTree, LocalRootData> localRoots = new HashMap<>();
  private Map<ParseTree, ParseTree> localRootCache = new HashMap<>();

  private boolean editingFinished = false;

  public EditContext(ParseTree root, BufferedTokenStream rootTokenStream) {
    registerLocalRoot(root, rootTokenStream);
  }

  public void finishEditing() {
    editingFinished = true;
    for (var value : localRoots.values()) {
      value.omissionSet().setReadonly(true);
    }
  }

  public LocalRootData getLocalRootData(ParseTree localRoot) {
    return localRoots.get(localRoot);
  }

  public void registerLocalRoot(ParseTree localRoot, BufferedTokenStream tokenStream) {
    localRoots.put(localRoot, new LocalRootData(new CachingIntervalSet(), tokenStream));
  }

  public void omitNodeTokens(ParseTree node) {
    if (editingFinished) {
      throw new IllegalStateException("Can't add intervals to editing context when editing is already finished!");
    }

    var localRoot = localRootCache.get(node);
    if (localRoot == null) {
      var traversedParents = new LinkedList<ParseTree>();
      var ancestor = node;
      while (ancestor != null) {
        traversedParents.add(ancestor);
        localRoot = ancestor;
        ancestor = localRoot.getParent();

        var cacheResult = localRootCache.get(ancestor);
        if (cacheResult != null) {
          localRoot = cacheResult;
          break;
        }
      }

      for (var traversedNode : traversedParents) {
        localRootCache.put(traversedNode, localRoot);
      }
    }

    var interval = node.getSourceInterval();
    localRoots.get(localRoot).omissionSet().add(interval.a, interval.b);
  }
}
