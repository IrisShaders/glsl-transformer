package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.JobParameters;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * The wrap target is used for detecting the presence of a wrap result.
 */
public abstract class WrapThrowTarget<T extends JobParameters> extends ThrowTarget<T> {
  @Override
  public String getMessage(TreeMember node, String match) {
    return "The wrapper identifier '" + getNeedle() + "' shouldn't already be present in the code!";
  }

  @Override
  public String getNeedle() {
    return getWrapResult();
  }

  /**
   * Returns the wrap result that will be preset after wrapping. This is only used
   * for detection of the identifier prior to wrapping for throwing an error if
   * it's present.
   * 
   * @return The identifier that's inserted for the wrapping
   */
  protected abstract String getWrapResult();
}
