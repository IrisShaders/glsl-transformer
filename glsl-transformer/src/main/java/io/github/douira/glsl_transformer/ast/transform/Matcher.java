package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * Instances of the matcher can match a node against a stored pattern. This
 * avoids a separate equality implementation for each node type.
 */
public class Matcher<T extends ASTNode> {
  protected final T pattern;
  private final String wildcardPrefix;
  private Map<String, Object> dataMatches;
  private Map<String, ASTNode> nodeMatches;
  private Map<ASTNode, NodeWildcard> nodeWildcards;
  private boolean collectMatches = false;
  private List<Object> patternItems;
  private int patternItemsSize;
  private int matchIndex;
  private boolean matches;

  public Matcher(T pattern, String wildcardIdentifier) {
    this.pattern = pattern;
    this.wildcardPrefix = wildcardIdentifier;
  }

  public Matcher(T pattern) {
    this(pattern, null);
  }

  public <RuleType extends ExtendedContext> Matcher(String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod,
      String wildcardIdentifier) {
    this(ASTBuilder.build(
        EnhancedParser.getInternalInstance().parse(input, parseMethod),
        visitMethod),
        wildcardIdentifier);
  }

  public <RuleType extends ExtendedContext> Matcher(String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod) {
    this(input, parseMethod, visitMethod, null);
  }

  private ASTVisitor<?> matchVisitor = new ASTVoidVisitor() {
    @Override
    public Void visit(ASTNode node) {
      if (!matches || matchIndex >= patternItemsSize) {
        matches = false;
        return null;
      }
      var patternItem = patternItems.get(matchIndex++);

      // match either a wildcard node
      if (collectMatches
          && nodeWildcards != null
          && patternItem instanceof NodeWildcard wildcard
          && wildcard.test(node)) {
        nodeMatches.put(wildcard.name, node);
        return null;
      }

      // or the class exactly
      if (node.getClass() != patternItem.getClass()) {
        matches = false;
        return null;
      }
      return node.accept(this);
    }

    @Override
    public void visitVoidData(Object data) {
      // Data has to only match by equality since these might also be strings.
      if (!matches
          || matchIndex >= patternItemsSize
          || data instanceof ASTNode) {
        matches = false;
        return;
      }
      var patternItem = patternItems.get(matchIndex++);

      // match either a wildcard or a data item exactly
      if (wildcardPrefix != null
          && patternItem instanceof String str
          && str.startsWith(wildcardPrefix)) {
        // the wildcard was matched, but the string is only collected if enabled
        if (collectMatches) {
          dataMatches.put(str.substring(wildcardPrefix.length()), data);
        }
        return;
      }

      if (!Objects.equals(data, patternItem)) {
        matches = false;
        return;
      }
    }
  };

  public void preparePatternItems() {
    if (patternItems != null) {
      return;
    }

    // visit the pattern and collect all items (ASTNodes and data),
    // don't collect subtrees of node wildcards
    patternItems = new ArrayList<>();
    new ASTVoidVisitor() {
      @Override
      public Void visit(ASTNode node) {
        if (nodeWildcards != null) {
          var wildcard = nodeWildcards.get(node);
          if (wildcard != null) {
            patternItems.add(wildcard);
            return null;
          }
        }
        patternItems.add(node);
        node.accept(this);
        return null;
      }

      @Override
      public void visitVoidData(Object data) {
        patternItems.add(data);
      }
    }.startVisit(pattern);
    patternItemsSize = patternItems.size();
  }

  /**
   * Traverse the given tree and the pattern at the same time and make sure they
   * are the same at each visit step.
   */
  public boolean matches(T tree) {
    if (tree == null) {
      return false;
    }
    preparePatternItems();
    matchIndex = 0;
    matches = true;
    matchVisitor.startVisit(tree);
    return matches;
  }

  private void ensureMatchMaps() {
    if (dataMatches == null) {
      dataMatches = new HashMap<>();
    }
    if (nodeMatches == null) {
      nodeMatches = new HashMap<>();
    }
  }

  public boolean matchesExtract(T tree) {
    ensureMatchMaps();
    dataMatches.clear();
    nodeMatches.clear();
    collectMatches = true;
    var succeeded = matches(tree);
    collectMatches = false;
    if (!succeeded) {
      dataMatches.clear();
      nodeMatches.clear();
    }
    return succeeded;
  }

  public boolean matchesExtract(
      T tree,
      Map<String, Object> dataMatches,
      Map<String, ASTNode> nodeMatches) {
    this.dataMatches = dataMatches;
    this.nodeMatches = nodeMatches;
    var succeeded = matchesExtract(tree);
    this.dataMatches = null;
    this.nodeMatches = null;
    return succeeded;
  }

  public Map<String, Object> getDataMatches() {
    return dataMatches;
  }

  public Map<String, ASTNode> getNodeMatches() {
    return nodeMatches;
  }

  public Object getDataMatch(String name) {
    return dataMatches.get(name);
  }

  public String getStringDataMatch(String name) {
    var result = dataMatches.get(name);
    return result instanceof String str ? str : null;
  }

  public ASTNode getNodeMatch(String name) {
    return nodeMatches.get(name);
  }

  public <R extends ASTNode> R getNodeMatch(String name, Class<R> clazz) {
    var result = nodeMatches.get(name);
    return clazz.isInstance(result) ? clazz.cast(result) : null;
  }

  private void ensureWildcardMap() {
    if (nodeWildcards == null) {
      nodeWildcards = new HashMap<>();
    }
  }

  private static abstract class NodeWildcard implements Predicate<ASTNode> {
    final String name;

    NodeWildcard(String name) {
      this.name = name;
    }
  }

  private void markWildcard(ASTNode node, NodeWildcard wildcard) {
    ensureWildcardMap();
    nodeWildcards.put(node, wildcard);
  }

  private static class AnyWildcard extends NodeWildcard {
    AnyWildcard(String name) {
      super(name);
    }

    @Override
    public boolean test(ASTNode node) {
      return true;
    }
  }

  public void markAnyWildcard(String name, ASTNode patternNode) {
    markWildcard(patternNode, new AnyWildcard(name));
  }

  private static class PredicateWildcard extends NodeWildcard {
    final Predicate<ASTNode> predicate;

    PredicateWildcard(String name, Predicate<ASTNode> predicate) {
      super(name);
      this.predicate = predicate;
    }

    @Override
    public boolean test(ASTNode node) {
      return predicate.test(node);
    }
  }

  public void markPredicatedWildcard(
      String name,
      ASTNode patternNode,
      Predicate<ASTNode> matchPredicate) {
    markWildcard(patternNode, new PredicateWildcard(name, matchPredicate));
  }

  private static class ClassWildcard extends NodeWildcard {
    final Class<? extends ASTNode> clazz;

    ClassWildcard(String name, Class<? extends ASTNode> clazz) {
      super(name);
      this.clazz = clazz;
    }

    @Override
    public boolean test(ASTNode node) {
      return clazz.isInstance(node);
    }
  }

  public void markClassWildcard(
      String name,
      ASTNode patternNode,
      Class<? extends ASTNode> clazz) {
    markWildcard(patternNode, new ClassWildcard(name, clazz));
  }

  public void markClassWildcard(
      String name,
      ASTNode patternNode) {
    ensureWildcardMap();
    nodeWildcards.put(patternNode, new ClassWildcard(name, patternNode.getClass()));
  }

  private static class ClassedPredicateWildcard<T extends ASTNode> extends NodeWildcard {
    final Class<T> clazz;
    final Predicate<T> predicate;

    ClassedPredicateWildcard(String name, Class<T> clazz, Predicate<T> predicate) {
      super(name);
      this.clazz = clazz;
      this.predicate = predicate;
    }

    @Override
    public boolean test(ASTNode node) {
      return clazz.isInstance(node) && predicate.test(clazz.cast(node));
    }
  }

  public <R extends ASTNode> void markClassedPredicateWildcard(
      String name,
      ASTNode patternNode,
      Class<R> clazz,
      Predicate<R> predicate) {
    markWildcard(patternNode, new ClassedPredicateWildcard<>(name, clazz, predicate));
  }
}
