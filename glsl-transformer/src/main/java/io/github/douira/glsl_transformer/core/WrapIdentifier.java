package io.github.douira.glsl_transformer.core;

import static io.github.douira.glsl_transformer.util.ConfigUtil.*;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.*;
import io.github.douira.glsl_transformer.transform.*;
import io.github.douira.glsl_transformer.transform.TransformationPhase.InjectionPoint;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The wrap identifier transformation wraps the usage of a certain identifier
 * with new code by replacing its usage with a new expression and inserting code
 * that takes care of handling the conversion from the new to the old value. It
 * also checks that the wrapped value isn't already present in the code.
 */
public class WrapIdentifier<T extends JobParameters> extends ActivatableTransformation<T> {
  private TransformationPhase<T> wrapResultDetector;
  private String wrapResult;
  private String wrapExpression;
  private Function<GLSLParser, ExtendedContext> parseMethod;

  private TransformationPhase<T> wrappingReplacer;
  private HandlerTarget<T> wrapHandlerTarget;
  private String wrapTarget;

  private TransformationPhase<T> wrappingInjector;
  private InjectionPoint injectionLocation;
  private String injectionExternalDeclaration;

  public WrapIdentifier() {
    chainDependent(getWrapResultDetector().activation(this::isActive));
    chainDependent(getWrappingReplacer().activation(this::isActive));
    chainDependent(getWrappingInjector().activation(this::isActive));
  }

  public WrapIdentifier<T> wrapResultDetector(TransformationPhase<T> wrapResultDetector) {
    this.wrapResultDetector = wrapResultDetector;
    return this;
  }

  public WrapIdentifier<T> wrapResult(String wrapResult) {
    this.wrapResult = wrapResult;
    return this;
  }

  public WrapIdentifier<T> wrapExpression(String wrapExpression) {
    this.wrapExpression = wrapExpression;
    return this;
  }

  public WrapIdentifier<T> parseMethod(Function<GLSLParser, ExtendedContext> parseMethod) {
    this.parseMethod = parseMethod;
    return this;
  }

  public WrapIdentifier<T> wrappingReplacer(TransformationPhase<T> wrappingReplacer) {
    this.wrappingReplacer = wrappingReplacer;
    return this;
  }

  public WrapIdentifier<T> wrapHandlerTarget(HandlerTarget<T> wrapHandlerTarget) {
    this.wrapHandlerTarget = wrapHandlerTarget;
    return this;
  }

  public WrapIdentifier<T> wrapTarget(String wrapTarget) {
    this.wrapTarget = wrapTarget;
    return this;
  }

  public WrapIdentifier<T> wrappingInjector(TransformationPhase<T> wrappingInjector) {
    this.wrappingInjector = wrappingInjector;
    return this;
  }

  public WrapIdentifier<T> injectionLocation(InjectionPoint injectionLocation) {
    this.injectionLocation = injectionLocation;
    return this;
  }

  public WrapIdentifier<T> injectionExternalDeclaration(String injectionExternalDeclaration) {
    this.injectionExternalDeclaration = injectionExternalDeclaration;
    return this;
  }

  protected TransformationPhase<T> getWrapResultDetector() {
    return wrapResultDetector == null ? new WrapThrowTargetImpl<T>(getWrapResult())
        : wrapResultDetector;
  }

  protected String getWrapResult() {
    return withDefault(wrapResult, this::getWrapExpression);
  }

  protected String getWrapExpression() {
    return wrapExpression;
  }

  protected Function<GLSLParser, ExtendedContext> getParseMethod() {
    return withDefault(parseMethod, GLSLParser::expression);
  }

  protected TransformationPhase<T> getWrappingReplacer() {
    return withDefault(wrappingReplacer,
        () -> new SearchTerminalsImpl<T>(getWrapHandlerTarget()));
  }

  protected HandlerTarget<T> getWrapHandlerTarget() {
    return withDefault(wrapHandlerTarget, () -> {
      var expression = getWrapExpression();
      return expression == null ? new TerminalReplaceTargetImpl<T>(getWrapTarget(), getWrapResult())
          : new ParsedReplaceTargetImpl<T>(getWrapTarget(), expression, getParseMethod());
    });
  }

  protected String getWrapTarget() {
    return wrapTarget;
  }

  protected TransformationPhase<T> getWrappingInjector() {
    return withDefault(wrappingInjector,
        () -> RunPhase
            .withInjectExternalDeclarations(getInjectionLocation(), getInjectionExternalDeclaration()));
  }

  protected InjectionPoint getInjectionLocation() {
    return withDefault(injectionLocation, InjectionPoint.BEFORE_DECLARATIONS);
  }

  protected String getInjectionExternalDeclaration() {
    return injectionExternalDeclaration;
  }
}
