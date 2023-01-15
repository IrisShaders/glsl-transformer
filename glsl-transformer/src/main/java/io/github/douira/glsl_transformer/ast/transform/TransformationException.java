package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.Supplier;

/**
 * Exception thrown when a transformation fails or is not possible.
 */
public class TransformationException extends RuntimeException {
  public TransformationException(String message) {
    super(message);
  }

  public TransformationException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransformationException(Throwable cause) {
    super(cause);
  }

  public TransformationException() {
    super();
  }

  public static Runnable thrower(String message) {
    return () -> {
      throw new TransformationException(message);
    };
  }

  public static Supplier<TransformationException> supplier(String message) {
    return () -> new TransformationException(message);
  }
}
