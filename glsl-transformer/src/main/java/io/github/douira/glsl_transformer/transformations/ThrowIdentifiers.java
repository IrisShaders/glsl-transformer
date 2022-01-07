package io.github.douira.glsl_transformer.transformations;

import java.util.Collection;
import java.util.stream.Collectors;

import com.github.bsideup.jabel.Desugar;

import io.github.douira.glsl_transformer.transform.SemanticException;

/**
 * This transformation throws a semantic exception when it encounters an
 * identifier containing a specific string.
 */
public class ThrowIdentifiers extends FindIdentifiers {
  /**
   * A target to search for in identifiers contains a string to search for and a
   * messag to put in the exception.
   */
  @Desugar
  public record IllegalTarget(String contained, String message) {
  }

  /**
   * Creates a new transformation of this type with the given targets.
   * 
   * @param targets The targets to search for
   */
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
