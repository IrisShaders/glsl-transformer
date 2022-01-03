package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The semantic exception should be thrown by a transformation phase when the
 * parsed code has semantic errors or there is some other content-related reason
 * why the transformation process should be halted.
 */
public class SemanticException extends RuntimeException {
  /**
   * The contained node that may be the cause of the exception. Can be
   * {@code null}.
   */
  private ParseTree node;

  /**
   * Creates a new empty semantic exception.
   */
  public SemanticException() {
  }

  /**
   * Creates a new semantic exception with a message string.
   * 
   * @param message The message of the exception
   */
  public SemanticException(String message) {
    super(message);
  }

  /**
   * Creates a new semantic exception with a message string and a parse tree node.
   * The node can be given if the error occurred in a specific node or while
   * processing a specific node.
   * 
   * @param message The message of the exception
   * @param node    The parse tree node to store in the exception
   */
  public SemanticException(String message, ParseTree node) {
    this(message);
    this.node = node;
  }

  /**
   * Returns the exception's parse tree node if there is one.
   * 
   * @return The node
   */
  public ParseTree getNode() {
    return node;
  }
}
