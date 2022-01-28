package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.core.target.ThrowTarget;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * The wrap identifier transformation wraps the usage of a certain identifier
 * with new code by replacing its usage with a new expression and inserting code
 * that takes care of handling the conversion from the new to the old value. It
 * also checks that the wrapped value isn't already present in the code.
 */
public abstract class WrapIdentifier<T> extends Transformation<T> {
  /**
   * Creates a new wrap identifier transformation.
   * 
   * @param wrappingReplacer The replacer phase that should replace a target
   *                         identifier with a replacement expression or
   *                         identifier
   * @param wrappingInjector A transformation phase that does the additional
   *                         code injection, usually providing a definition
   *                         for the newly inserted identifier in the form of
   *                         an external declaration of some sort
   */
  public WrapIdentifier(TransformationPhase<T> wrappingReplacer, TransformationPhase<T> wrappingInjector) {
    // throw if the wrap result already exists
    addPhase(new SearchTerminals<T>(new ThrowTarget<T>() {
      @Override
      public String getMessage(TreeMember node, String match) {
        return "The wrapper identifier '" + getNeedle() + "' shouldn't already be present in the code!";
      }

      @Override
      public String getNeedle() {
        return getWrapResult();
      }
    }));

    addPhase(wrappingReplacer);
    addConcurrentPhase(wrappingInjector);
  }

  /**
   * Returns the wrap result that will be preset after wrapping. This is only used
   * for detection of the identifier prior to wrapping for throwing an error if
   * it's present.
   * 
   * @return The identifier that's inserted for the wrapping
   */
  public abstract String getWrapResult();
}
