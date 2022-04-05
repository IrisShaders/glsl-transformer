package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.JobParameters;

/**
 * This implementation of the wrap target uses a field for statically holding
 * the wrap result.
 */
public class WrapThrowTargetImpl<T extends JobParameters> extends WrapThrowTarget<T> {
  private final String wrapResult;

  /**
   * Creates a new wrap target with a the given string as the needle.
   * 
   * @param wrapResult The wrap target which is the search string
   */
  public WrapThrowTargetImpl(String wrapResult) {
    this.wrapResult = wrapResult;
  }

  @Override
  protected String getWrapResult() {
    return wrapResult;
  }
}
