package io.github.douira.glsl_transformer.ast.typing;

public class TypeAnalysisException extends RuntimeException {
  public TypeAnalysisException(String message) {
    super(message);
  }

  public TypeAnalysisException(String message, Throwable cause) {
    super(message, cause);
  }

  public TypeAnalysisException(Throwable cause) {
    super(cause);
  }

  public TypeAnalysisException() {
    super();
  }
}
