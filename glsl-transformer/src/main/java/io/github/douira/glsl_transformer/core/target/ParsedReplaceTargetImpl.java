package io.github.douira.glsl_transformer.core.target;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.transform.JobParameters;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A parsed replace target generates a new parsed node from a stored string each
 * time a replacement is handled.
 */
public class ParsedReplaceTargetImpl<T extends JobParameters> extends ParsedReplaceTarget<T> {
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
  public ParsedReplaceTargetImpl(String needle, String newContent, Function<GLSLParser, ExtendedContext> parseMethod) {
    super(needle);
    this.newContent = newContent;
    this.parseMethod = parseMethod;
  }

  @Override
  protected String getNewContent(TreeMember node, String match) {
    return newContent;
  }

  @Override
  protected Function<GLSLParser, ExtendedContext> getParseMethod(TreeMember node, String match) {
    return parseMethod;
  }
}
