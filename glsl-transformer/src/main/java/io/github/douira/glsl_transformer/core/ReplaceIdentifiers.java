package io.github.douira.glsl_transformer.core;

import java.util.HashSet;
import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.ParsedReplaceTarget;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ReplaceIdentifiers extends ProcessIdentifiers {
  public ReplaceIdentifiers() {
    super(new HashSet<>());
  }

  public void addReplacement(String needle, String newContent, Function<GLSLParser, ExtendedContext> parseMethod) {
    targets.add(new ParsedReplaceTarget(needle, newContent, parseMethod));
  }

  public void addReplacementExpression(String needle, String expressionContent) {
    addReplacement(needle, expressionContent, GLSLParser::expression);
  }
}
