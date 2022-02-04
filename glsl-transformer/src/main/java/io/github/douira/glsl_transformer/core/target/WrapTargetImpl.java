package io.github.douira.glsl_transformer.core.target;

/**
 * This implementation of the wrap target uses a field for statically holding
 * the wrap result.
 */
public class WrapTargetImpl<T> extends WrapTarget<T> {
  private final String wrapResult;

  /**
   * Creates a new wrap target with a the given string as the needle.
   * 
   * @param wrapResult The wrap target which is the search string
   */
  public WrapTargetImpl(String wrapResult) {
    this.wrapResult = wrapResult;
  }

  @Override
  public String getWrapResult() {
    return wrapResult;
  }
}
