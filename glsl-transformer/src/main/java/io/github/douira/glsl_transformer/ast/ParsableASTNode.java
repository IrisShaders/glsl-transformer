package io.github.douira.glsl_transformer.ast;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.generic.ExtendedContext;
import io.github.douira.glsl_transformer.transform.TransformationManager;

/**
 * The parsable a AST node defines how AST nodes that can be converted back into
 * parse tree nodes are handled. The construction of AST nodes can vary a lot
 * and is not defined here.
 */
public abstract class ParsableASTNode extends ASTNode {
  /**
   * This method is overwritten by the implementing class to specify with which
   * parser method the generated string should be parsed.
   * 
   * @return A method reference from
   *         {@link io.github.douira.glsl_transformer.GLSLParser}
   */
  protected abstract Function<GLSLParser, ExtendedContext> getOutputParseMethod();

  /**
   * Parses this AST node's generated code into a parse tree node without
   * attaching a parent node.
   * 
   * @see #getParsed(ExtendedContext)
   * 
   * @return The AST node's code parsed into a node
   */
  public ExtendedContext getParsed() {
    return getParsed(null);
  }

  /**
   * Parses the code generated by this AST node with the parser method supplied by
   * {@link #getOutputParseMethod()} into a parse tree node..
   * 
   * @param parent The parent node to attach to the parsed node
   * @return The AST node's code parsed into a node
   */
  public ExtendedContext getParsed(ExtendedContext parent) {
    return TransformationManager.INTERNAL.parse(
        getPrinted(), parent, getOutputParseMethod());
  }
}