package io.github.douira.glsl_transformer.core.target;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.transform.JobParameters;
import io.github.douira.glsl_transformer.tree.*;

/**
 * A parsed replace target generates a node from parsing a string for each
 * replacement.
 */
public abstract class ParsedReplaceTarget<T extends JobParameters> extends HandlerTargetImpl<T> {
  /**
   * Creates a new parsed replace target that takes a search string. The
   * replacement is given by implementing the
   * {@link #getNewContent(TreeMember, String)} and
   * {@link #getParseMethod(TreeMember, String)} methods.
   * 
   * @param needle The search string
   */
  public ParsedReplaceTarget(String needle) {
    super(needle);
  }

  /**
   * Creates a new parsed replace target with no search string. In addition to the
   * methods for generating replacement nodes, the {@link #getNeedle()} method
   * should be overwritten if this constructor is used.
   * 
   * @see io.github.douira.glsl_transformer.core.target.HandlerTargetImpl#HandlerTargetImpl()
   */
  protected ParsedReplaceTarget() {
  }

  /**
   * Returns the string to be parsed with the parser function returned by
   * {@link #getParseMethod(TreeMember, String)} in order to create a new node
   * that is inserted as a
   * replacement for the found target.
   * 
   * @param node  The node that contains the token
   * @param match The token text that contains the needle
   * @return The string to parse as a new node
   */
  protected abstract String getNewContent(TreeMember node, String match);

  /**
   * Returns the method for parsing the string returned by
   * {@link #getNewContent(TreeMember, String)} into a new node that replaces the
   * node that was found to contain the needle
   * 
   * @param node  The node that contains the token
   * @param match The token text that contains the needle
   * @return The parser method that should be used for parsing the new node as a
   *         replacement for the node found to contain the needle
   */
  protected abstract Function<GLSLParser, ExtendedContext> getParseMethod(TreeMember node, String match);

  @Override
  public void handleResult(TreeMember node, String match) {
    replaceNode(node, getNewContent(node, match), getParseMethod(node, match));
  }
}
