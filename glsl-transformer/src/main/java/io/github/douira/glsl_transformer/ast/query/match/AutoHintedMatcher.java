package io.github.douira.glsl_transformer.ast.query.match;

import java.util.function.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;

/**
 * The auto-hinted matcher is a matcher that automatically determines the hint
 * by finding the longest identifier in the pattern. If that hint is not the
 * best (most distinguishing) hint, then the manually hinted
 * {@link HintedMatcher} should be used instead.
 */
public class AutoHintedMatcher<T extends ASTNode> extends HintedMatcher<T> {
  public <RuleType extends ParserRuleContext> AutoHintedMatcher(String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod, String wildcardPrefix) {
    super(input, parseMethod, visitMethod, wildcardPrefix, null);
  }

  public AutoHintedMatcher(T pattern, String wildcardPrefix) {
    super(pattern, wildcardPrefix, null);
  }

  public AutoHintedMatcher(T pattern) {
    super(pattern, null);
  }

  public <RuleType extends ParserRuleContext> AutoHintedMatcher(String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod) {
    super(input, parseMethod, visitMethod, null);
  }

  public AutoHintedMatcher(String input, Function<String, T> patternParser, String wildcardPrefix) {
    super(input, patternParser, wildcardPrefix, null);
  }

  public AutoHintedMatcher(String input, Function<String, T> patternParser) {
    super(input, patternParser, null);
  }

  private void determineHint() {
    preparePatternItems();
    String longestHint = null;
    var hintLength = 0;
    for (var item : patternItems) {
      if (item instanceof Identifier id) {
        var idContent = id.getName();
        if (idContent.length() > hintLength
            && (wildcardPrefix == null || !idContent.startsWith(wildcardPrefix))) {
          longestHint = idContent;
          hintLength = idContent.length();
        }
      }
    }
    if (longestHint == null) {
      throw new IllegalArgumentException(
          "The provided pattern must contain a non-wildcard identifier to use as the hint!");
    }
    hint = longestHint;
  }

  @Override
  public String getHint() {
    if (hint == null) {
      determineHint();
    }
    return super.getHint();
  }
}
