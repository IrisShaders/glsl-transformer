package io.github.douira.glsl_transformer.ast;

import java.util.function.Function;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.BuiltinTypeSpecifierParseableContext;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * A tensor abstractly represents the many multidimensional number types that
 * GLSL has. It does this within a single data structure by modeling scalars,
 * vectors and matrices as n-dimensional and also taking the bit depth into
 * account.
 */
public class Tensor extends ParsableTerminalASTNode {
  private Type type;

  /**
   * Creates a new tensor with the given type.
   *
   * @param type The type of the new tensor
   */
  public Tensor(Type type) {
    this.type = type;
  }

  /**
   * Creates a new tensor with the type given as a lexer token index.
   *
   * @see GLSLLexer
   *
   * @param tokenType The token type index
   */
  public Tensor(int tokenType) {
    this(Type.ofTokenType(tokenType));
  }

  /**
   * Creates a new tensor from the given type specifier parse tree node.
   *
   * @param ctx The parse tree node to read the type from
   */
  public Tensor(BuiltinTypeSpecifierParseableContext ctx) {
    this(getTypeSpecifierType(ctx));
  }

  /**
   * Creates a new tensor by parsing the type specified in the given string. The
   * string should contain a builtin type specifier. See the parser grammar for
   * details.
   *
   * @param str The string to parse as a GLSL type specifier
   */
  public Tensor(String str) {
    this(TransformationManager.INTERNAL.parse(str, GLSLParser::builtinTypeSpecifierParseable));
  }

  private static int getTypeSpecifierType(BuiltinTypeSpecifierParseableContext ctx) {
    var children = ctx.children;
    if (children.size() != 1) {
      throw new IllegalArgumentException("Invalid type specifier context given. It must have exactly one child.");
    }

    if (children.get(0) instanceof TerminalNode terminalNode) {
      return terminalNode.getSymbol().getType();
    } else {
      throw new IllegalArgumentException(
          "Type specifier context child has the wrong structure. It should be a terminal node.");
    }
  }

  @Override
  protected String getPrinted() {
    String compactName = type.getCompactName();
    return compactName != null ? compactName : type.getExplicitName();
  }

  @Override
  protected Function<GLSLParser, ExtendedContext> getOutputParseMethod() {
    return GLSLParser::builtinTypeSpecifierParseable;
  }

  /**
   * Returns the type contained in this tensor node.
   * 
   * @return The type of this tensor
   */
  public Type getType() {
    return type;
  }

  /**
   * Sets the type of this tensor.
   * 
   * @param type The new type of this tensor
   */
  public void setType(Type type) {
    this.type = type;
  }
}
