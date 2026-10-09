package io.github.douira.glsl_transformer.ast.typing;

/**
 * Thrown by the type analysis in strict mode when the first problem is found.
 */
public class TypeAnalysisException extends RuntimeException {
  private final Diagnostic diagnostic;

  public TypeAnalysisException(Diagnostic diagnostic) {
    super(diagnostic.toString());
    this.diagnostic = diagnostic;
  }

  public Diagnostic getDiagnostic() {
    return diagnostic;
  }
}
