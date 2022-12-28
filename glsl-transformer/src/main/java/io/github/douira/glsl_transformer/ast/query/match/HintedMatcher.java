package io.github.douira.glsl_transformer.ast.query.match;

import java.util.function.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;

/**
 * A hinted matcher contains an additional string that can be used to find
 * identifiers whose matching ancestor might match the pattern. This makes
 * working with matchers much less verbose.
 */
public class HintedMatcher<N extends ASTNode> extends Matcher<N> {
  protected String hint;

  public <C extends ParserRuleContext> HintedMatcher(String input, Function<GLSLParser, C> parseMethod,
      BiFunction<ASTBuilder, C, N> visitMethod, String wildcardPrefix, String hint) {
    super(input, parseMethod, visitMethod, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(N pattern, String wildcardPrefix, String hint) {
    super(pattern, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(N pattern, String hint) {
    super(pattern);
    this.hint = hint;
  }

  public <C extends ParserRuleContext> HintedMatcher(String input, Function<GLSLParser, C> parseMethod,
      BiFunction<ASTBuilder, C, N> visitMethod, String hint) {
    super(input, parseMethod, visitMethod);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, N> patternParser, String wildcardPrefix, String hint) {
    super(input, patternParser, wildcardPrefix);
    this.hint = hint;
  }

  public HintedMatcher(String input, Function<String, N> patternParser, String hint) {
    super(input, patternParser);
    this.hint = hint;
  }

  public String getHint() {
    return hint;
  }
}
