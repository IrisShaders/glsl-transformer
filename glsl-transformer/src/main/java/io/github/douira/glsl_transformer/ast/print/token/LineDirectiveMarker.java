package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.ast.transform.PresentSourceLocation;

public class LineDirectiveMarker extends Marker {
  public final PresentSourceLocation location;

  public LineDirectiveMarker(PresentSourceLocation location) {
    this.location = location;
  }
}
