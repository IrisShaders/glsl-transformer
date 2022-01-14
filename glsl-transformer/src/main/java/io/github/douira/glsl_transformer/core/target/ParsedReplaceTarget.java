package io.github.douira.glsl_transformer.core.target;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.TreeMember;

public class ParsedReplaceTarget extends HandlerTarget {
  private String newContent;
  private Function<GLSLParser, ExtendedContext> parseMethod;

  public ParsedReplaceTarget(String needle, String newContent, Function<GLSLParser, ExtendedContext> parseMethod) {
    super(needle);
    this.newContent = newContent;
    this.parseMethod = parseMethod;
  }

  @Override
  public void handleResult(TreeMember node, String match) {
    replaceNode(node, newContent, parseMethod);
  }
}
