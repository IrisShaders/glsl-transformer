package io.github.douira.glsl_transformer.core.target;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A parsed replace target generates a new parsed node from a stored string each
 * time a replacement is handled.
 */
public class ParsedReplaceTarget<T> extends HandlerTargetSimple<T> {
  private final String newContent;
  private final Function<GLSLParser, ExtendedContext> parseMethod;

  /**
   * Create a new parsed replacement target with a given needle and new content to
   * be parsed.
   * 
   * @param needle      The needle (search string)
   * @param newContent  The new content to parse into a node
   * @param parseMethod The parser method to create the new node with
   */
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
