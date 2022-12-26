package io.github.douira.glsl_transformer.ast.query.match;

import java.util.function.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;

/**
 * A hinted matcher contains an additional string that can be used to find
 * identifiers whose matching ancestor might match the pattern. This makes
 * working with matchers much less verbose.
 */
public class HintedMatcher<T extends ASTNode> extends Matcher<T> {
  protected String hint;

  public <RuleType extends ParserRuleContext> HintedMatcher(String input, Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod, String wildcardPrefix, String hint) {
    super(input, parseMethod, visitMethod, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(T pattern, String wildcardPrefix, String hint) {
    super(pattern, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(T pattern, String hint) {
    super(pattern);
    this.hint = hint;
  }

  public <RuleType extends ParserRuleContext> HintedMatcher(String input, Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, T> visitMethod, String hint) {
    super(input, parseMethod, visitMethod);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, T> patternParser, String wildcardPrefix, String hint) {
    super(input, patternParser, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, T> patternParser, String hint) {
    super(input, patternParser);
    this.hint = hint;
  }

  public String getHint() {
    return hint;
  }
}
