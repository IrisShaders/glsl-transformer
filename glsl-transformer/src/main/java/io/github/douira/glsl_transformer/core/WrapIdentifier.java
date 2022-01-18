package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.core.target.ThrowTarget;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

public class WrapIdentifier<P> extends Transformation<P> {
  public WrapIdentifier(
      String wrapTarget,
      String wrapResult,
      HandlerTarget<P> replaceTarget,
      TransformationPhase<P> wrappingInjector) {
    // throw if the wrap result already exists
    addPhase(new SearchTerminals<P>(
        ThrowTarget.fromMessage(wrapResult,
            "The wrapper '" + wrapResult + "' can't already be in the string!")));

    // replace the wrap target with the wrap result
    addPhase(new SearchTerminals<P>(replaceTarget));

    // inject the wrapper code
    addPhase(wrappingInjector);

    // TODO: does the wrapping injector need to be told about something?
    // should there be some kind of communication system for phases that were not
    // directly constructed within a transformation?
  }
}
