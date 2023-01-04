package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.transform.SourceLocation;

public class LineDirectiveMarker extends Marker {
  public final SourceLocation location;

  public LineDirectiveMarker(SourceLocation location) {
    this.location = location;
  }
}
