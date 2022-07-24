package io.github.douira.glsl_transformer.ast.query;

import java.util.function.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * A hinted matcher contains an additional string that can be used to find
 * identifiers whose matching ancestor might match the pattern.
 */
public class HintedMatcher<T extends ASTNode> extends Matcher<T> {
  public final String hint;

  public <RuleType extends ExtendedContext> HintedMatcher(String input, Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod, String wildcardIdentifier, String hint) {
    super(input, parseMethod, visitMethod, wildcardIdentifier);
    this.hint = hint;
  }

  public HintedMatcher(T pattern, String wildcardIdentifier, String hint) {
    super(pattern, wildcardIdentifier);
    this.hint = hint;
  }

  public HintedMatcher(T pattern, String hint) {
    super(pattern);
    this.hint = hint;
  }

  public <RuleType extends ExtendedContext> HintedMatcher(String input, Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod, String hint) {
    super(input, parseMethod, visitMethod);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, T> patternParser, String wildcardIdentifier, String hint) {
    super(input, patternParser, wildcardIdentifier);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, T> patternParser, String hint) {
    super(input, patternParser);
    this.hint = hint;
  }
}
