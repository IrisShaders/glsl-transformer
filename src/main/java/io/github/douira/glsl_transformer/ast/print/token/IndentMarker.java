package io.github.douira.glsl_transformer.ast.print.token;

public class IndentMarker extends Marker {
  public final int indentDelta;

  public IndentMarker(int indentDelta) {
    this.indentDelta = indentDelta;
  }

  public static IndentMarker create(int indentDelta) {
    return new IndentMarker(indentDelta);
  }

  public static IndentMarker indent() {
    return new IndentMarker(1);
  }

  public static IndentMarker unindent() {
    return new IndentMarker(-1);
  }
}
