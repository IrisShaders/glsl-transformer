package io.github.douira.glsl_transformer.transformations;

import java.util.Collection;
import java.util.stream.Collectors;

import io.github.douira.glsl_transformer.transform.SemanticException;

public class ThrowIdentifiers extends FindIdentifiers {
  public record IllegalTarget(String contained, String message) {
  }

  public ThrowIdentifiers(Collection<IllegalTarget> targets) {
    super(targets.stream()
        .map(
            target -> new Target(
                target.contained(),
                (node, token) -> {
                  throw new SemanticException(target.message(), node);
                }))
        .collect(Collectors.toList()));
  }
}
