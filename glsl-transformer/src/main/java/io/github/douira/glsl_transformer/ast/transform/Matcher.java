package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * Instances of the matcher can match a node against a stored pattern. This
 * avoids a separate equality implementation for each node type.
 */
public class Matcher {
  private final ASTNode pattern;
  private List<Object> patternItems;
  private int patternItemsSize;
  private int matchIndex;
  private boolean matches;

  public Matcher(ASTNode pattern) {
    this.pattern = pattern;
  }

  public Matcher(String input,
      Function<GLSLParser, ? extends ExtendedContext> parseMethod) {
    this(ASTBuilder.build(
        EnhancedParser.getInternalInstance().parse(input, parseMethod)));
  }

  private ASTVisitor<?> matchVisitor = new ASTVoidVisitor() {
    @Override
    public void visitVoid(ASTNode node) {
      // ASTNodes have to match by exact class (the type of the node)
      matches = matches
          && matchIndex < patternItemsSize
          && node.getClass() == patternItems.get(matchIndex++).getClass();
    }

    @Override
    public void visitVoid(Object data) {
      // Data has to only match by equality since these might also be strings.
      matches = matches
          && matchIndex < patternItemsSize
          && !(data instanceof ASTNode)
          && data.equals(patternItems.get(matchIndex++));
    }

    @Override
    public Void visit(ASTNode node) {
      // stop visiting when the match has failed
      if (!matches) {
        return null;
      }
      return super.visit(node);
    }
  };

  /**
   * Traverse the given tree and the pattern at the same time and make sure they
   * are the same at each visit step.
   */
  public boolean matches(ASTNode tree) {
    if (tree == null) {
      return false;
    }
    preparePatternItems();
    matchIndex = 0;
    matches = true;
    matchVisitor.startVisit(tree);
    return matches;
  }

  public void preparePatternItems() {
    if (patternItems == null) {
      patternItems = new ArrayList<>();
      new ASTVoidVisitor() {
        @Override
        public void visitVoid(ASTNode node) {
          patternItems.add(node);
        }

        @Override
        public void visitVoid(Object data) {
          patternItems.add(data);
        }
      }.startVisit(pattern);
      patternItemsSize = patternItems.size();
    }
  }
}
