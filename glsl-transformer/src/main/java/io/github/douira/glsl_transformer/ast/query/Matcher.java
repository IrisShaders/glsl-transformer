package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * Instances of the matcher can match a node against a stored pattern. This
 * avoids a separate equality implementation for each node type.
 */
public class Matcher<T extends ASTNode> {
  /**
   * The node of the pattern being matched.
   */
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

  /**
   * Creates a new matcher for the given pattern and wildcard prefix.
   * 
   * @param pattern            The pattern to match
   * @param wildcardIdentifier The prefix for wildcard identifiers
   */
  public Matcher(T pattern, String wildcardIdentifier) {
    this.pattern = pattern;
    this.wildcardPrefix = wildcardIdentifier;
  }

  /**
   * Creates a new matcher for the given pattern and no wildcard prefix.
   * 
   * @param pattern The pattern to match
   */
  public Matcher(T pattern) {
    this(pattern, null);
  }

  /**
   * Creates a new matcher that matches the pattern parsed from the given string,
   * parser method and visitor method. There is also a given wildcard prefix.
   * 
   * @param <RuleType>         The type of the parser rule context
   * @param input              The string to parse
   * @param parseMethod        The parser method to use
   * @param visitMethod        The build visitor method to use
   * @param wildcardIdentifier The wildcard prefix
   */
  public <RuleType extends ExtendedContext> Matcher(String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod,
      String wildcardIdentifier) {
    this(ASTBuilder.build(
        EnhancedParser.getInternalInstance().parse(input, parseMethod),
        visitMethod),
        wildcardIdentifier);
  }

  /**
   * Creates a new matcher that matches the pattern parsed from the given string,
   * parser method and visitor method. There is no wildcard prefix.
   * 
   * @param <RuleType>  The type of the parser rule context
   * @param input       The string to parse
   * @param parseMethod The parser method to use
   * @param visitMethod The build visitor method to use
   */
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
      if (nodeWildcards != null
          && patternItem instanceof NodeWildcard wildcard
          && wildcard.test(node)) {
        if (collectMatches) {
          nodeMatches.put(wildcard.name, node);
        }
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

  /**
   * Prepares the matcher for matching. It parses the pattern and stores the
   * resulting items. This can be used to pre-compute this list of items.
   * Otherwise, this will be calculated on demand
   */
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
   * 
   * @param tree The tree to match
   * @return True if the tree matches the pattern, false otherwise
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

  /**
   * Matches the given tree and collect matching string wildcards, data wildcard
   * and node wildcards. It uses the default data and node wildcard maps.
   * 
   * @param tree The tree to match
   * @return True if the tree matches the pattern, false otherwise
   */
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

  /**
   * Matches the given tree and collect matching string wildcards, data wildcard
   * and node wildcards using the given data and node match maps.
   * 
   * @param tree        The tree to match
   * @param dataMatches The data match map to use
   * @param nodeMatches The node match map to use
   * @return
   */
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

  /**
   * Returns the data match map.
   * 
   * @return The data match map
   */
  public Map<String, Object> getDataMatches() {
    return dataMatches;
  }

  /**
   * Returns the node match map.
   * 
   * @return The node match map
   */
  public Map<String, ASTNode> getNodeMatches() {
    return nodeMatches;
  }

  /**
   * Gets a data match with the given name.
   * 
   * @param name The name of the data match
   * @return The data match or null if not found
   */
  public Object getDataMatch(String name) {
    return dataMatches.get(name);
  }

  /**
   * Gets a data match with the given name as a string.
   * 
   * @param name The name of the data match
   * @return The data match or null if either not found or not a string
   */
  public String getStringDataMatch(String name) {
    var result = dataMatches.get(name);
    return result instanceof String str ? str : null;
  }

  /**
   * Gets a node match with the given name.
   * 
   * @param name The name of the node match
   * @return The node match or null if not found
   */
  public ASTNode getNodeMatch(String name) {
    return nodeMatches.get(name);
  }

  /**
   * Gets a node match with the given name if it is available as the given class.
   * 
   * @param <R>   The type of the node match
   * @param name  The name of the node match
   * @param clazz The class of the node match
   * @return The node match or null if not found or not of the given class
   */
  public <R extends ASTNode> R getNodeMatch(String name, Class<R> clazz) {
    var result = nodeMatches.get(name);
    return clazz.isInstance(result) ? clazz.cast(result) : null;
  }

  @SuppressWarnings("unchecked")
  public Class<? extends T> getPatternClass() {
    return (Class<? extends T>) pattern.getClass();
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

  /**
   * Marks the given node as an any wildcard with the given name. The wildcard
   * will match any node in the same position in the tree.
   * 
   * @param name        The name of the wildcard
   * @param patternNode The node to mark as an any wildcard
   */
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

  /**
   * Marks the given node as a predicate wildcard with the given name. The
   * predicate wildcard will match any node in the same position in the tree that
   * matches the predicate.
   * 
   * @param name           The name of the wildcard
   * @param patternNode    The node to mark as a predicate wildcard
   * @param matchPredicate The predicate to match the node with
   */
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

  /**
   * Marks the given node as a class wildcard with the given name. The wildcard
   * will match any node in the same position in the tree that is an instance of
   * the given class. If any subclass should be matched, use a superclass for
   * matching.
   * 
   * @param name        The name of the wildcard
   * @param patternNode The node to mark as a class wildcard
   * @param clazz       The class to match the node with
   */
  public void markClassWildcard(
      String name,
      ASTNode patternNode,
      Class<? extends ASTNode> clazz) {
    markWildcard(patternNode, new ClassWildcard(name, clazz));
  }

  /**
   * Marks the given node as a class wildcard with the given name where the class
   * is extracted from the pattern node itself. This will match any node in the
   * same position in the tree that is the same class as the pattern node.
   * 
   * @param name        The name of the wildcard
   * @param patternNode The node to mark as a class wildcard
   */
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

  /**
   * Marks the given node as a classed predicate wildcard with the given name. The
   * wildcard will match any node in the same position in the tree that is an
   * instance of the given class and matches the predicate.
   * 
   * @param <R>         The type of the node match
   * @param name        The name of the wildcard
   * @param patternNode The node to mark as a classed predicate wildcard
   * @param clazz       The class to match the node with
   * @param predicate   The predicate to match the node with
   */
  public <R extends ASTNode> void markClassedPredicateWildcard(
      String name,
      ASTNode patternNode,
      Class<R> clazz,
      Predicate<R> predicate) {
    markWildcard(patternNode, new ClassedPredicateWildcard<>(name, clazz, predicate));
  }
}
