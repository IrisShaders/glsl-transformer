package io.github.douira.glsl_transformer.core;

import static io.github.douira.glsl_transformer.util.ConfigUtil.*;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.core.target.*;
import io.github.douira.glsl_transformer.transform.*;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The wrap identifier transformation wraps the usage of a certain identifier
 * with new code by replacing its usage with a new expression and inserting code
 * that takes care of handling the conversion from the new to the old value. It
 * also checks that the wrapped value isn't already present in the code.
 * 
 * This class uses a chaining configuration pattern. The class is constructed
 * with an empty constructor and then configured by calling some configuration
 * methods. Additionally, configuration values can be generated dynamically by
 * overriding the getter methods.
 */
public class WrapIdentifier<T extends JobParameters> extends Transformation<T> {
  private ActivatableLifecycleUser<T> wrapResultDetector;
  private String detectionResult;
  private String parsedReplacement;
  private Function<GLSLParser, ExtendedContext> parseMethod;

  private ActivatableLifecycleUser<T> wrappingReplacer;
  private HandlerTarget<T> wrapHandlerTarget;
  private String wrapTarget;

  private ActivatableLifecycleUser<T> injector;
  private InjectionPoint injectionLocation;
  private String injectionExternalDeclaration;

  /**
   * Create a new wrap identifier transformation. Configuration is done by calling
   * the various configuration methods.
   */
  public WrapIdentifier() {
  }

  /**
   * Setup is done here so that it can be overridden in subclasses.
   */
  @Override
  protected void setupGraph() {
    chainDependent(getWrapResultDetector());
    chainDependent(getWrappingReplacer());
    chainDependent(getInjector());
  }

  /**
   * Sets the detector that will be used to check if the detection result exists
   * already. Generated from the detection result by default.
   * 
   * @param wrapResultDetector The result detector
   * @return This object
   */
  public WrapIdentifier<T> wrapResultDetector(ActivatableLifecycleUser<T> wrapResultDetector) {
    this.wrapResultDetector = wrapResultDetector;
    return this;
  }

  /**
   * Sets the result that will be present in the code after the wrapping.
   * Generated from the parsed replacement by default. However, if a parsed
   * replacement is used, the detection result should usually be set manually if
   * the replacement isn't just a single identifier. If used without an explicit
   * expression to insert, this must be an identifier since it's not parsed.
   * 
   * @param detectionResult The detection result
   * @return This object
   */
  public WrapIdentifier<T> detectionResult(String detectionResult) {
    this.detectionResult = detectionResult;
    return this;
  }

  /**
   * Sets the parsed replacement that replaces the target. Has no default but
   * is only used if a parser method is set.
   * 
   * @param parsedReplacement The parsed replacement
   * @return This object
   */
  public WrapIdentifier<T> parsedReplacement(String parsedReplacement) {
    this.parsedReplacement = parsedReplacement;
    return this;
  }

  /**
   * Sets the parser method that will be used to parse the wrap expression. Is the
   * default expression parsing method by default.
   * 
   * @param parseMethod The replacement parsing method
   * @return This object
   */
  public WrapIdentifier<T> parseMethod(Function<GLSLParser, ExtendedContext> parseMethod) {
    this.parseMethod = parseMethod;
    return this;
  }

  /**
   * Sets the replacer that will be used to replace the target with the wrap
   * expression.
   * 
   * @param wrappingReplacer The replacer
   * @return This object
   */
  public WrapIdentifier<T> wrappingReplacer(ActivatableLifecycleUser<T> wrappingReplacer) {
    this.wrappingReplacer = wrappingReplacer;
    return this;
  }

  /**
   * Sets the handler target that does the replacement of the target. Generated
   * from the wrap target by default.
   * 
   * @param wrapHandlerTarget The replacement target handler
   * @return This object
   */
  public WrapIdentifier<T> wrapHandlerTarget(HandlerTarget<T> wrapHandlerTarget) {
    this.wrapHandlerTarget = wrapHandlerTarget;
    return this;
  }

  /**
   * Sets the target that will be replaced.
   * 
   * @param wrapTarget The replacement target identifier
   * @return This object
   */
  public WrapIdentifier<T> wrapTarget(String wrapTarget) {
    this.wrapTarget = wrapTarget;
    return this;
  }

  /**
   * Sets the injector that will be used to inject additional code after
   * replacement is done. Generated from the injection location and the injection
   * external declaration by default.
   * 
   * @param injector The injector
   * @return This object
   */
  public WrapIdentifier<T> injector(ActivatableLifecycleUser<T> injector) {
    this.injector = injector;
    return this;
  }

  /**
   * Sets the injection location for the generated injector.
   * 
   * @param injectionLocation The injection location
   * @return This object
   */
  public WrapIdentifier<T> injectionLocation(InjectionPoint injectionLocation) {
    this.injectionLocation = injectionLocation;
    return this;
  }

  /**
   * Sets the external declaration for the generated injector.
   * 
   * @param injectionExternalDeclaration The external declaration
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclaration(String injectionExternalDeclaration) {
    this.injectionExternalDeclaration = injectionExternalDeclaration;
    return this;
  }

  /**
   * Gets the result detector.
   * 
   * @return The result detector
   */
  protected ActivatableLifecycleUser<T> getWrapResultDetector() {
    return withDefault(wrapResultDetector,
        () -> new SearchTerminalsImpl<>(new WrapThrowTargetImpl<T>(detectionResult())))
        .activation(this::isActive);
  }

  /**
   * Gets the detection result.
   * 
   * @return The detection result
   */
  protected String detectionResult() {
    return withDefault(detectionResult, this::getParsedReplacement);
  }

  /**
   * Gets the parsed replacement.
   * 
   * @return The parsed replacement
   */
  protected String getParsedReplacement() {
    return parsedReplacement;
  }

  /**
   * Gets the replacement expression parsing method.
   * 
   * @return The replacement expression parsing method
   */
  protected Function<GLSLParser, ExtendedContext> getParseMethod() {
    return withDefault(parseMethod, GLSLParser::expression);
  }

  /**
   * Gets the wrapping replacer.
   * 
   * @return The wrapping replacer
   */
  protected ActivatableLifecycleUser<T> getWrappingReplacer() {
    return withDefault(wrappingReplacer,
        () -> new SearchTerminalsImpl<T>(getWrapHandlerTarget()))
        .activation(this::isActive);
  }

  /**
   * Gets the replacement target handler.
   * 
   * @return The replacement target handler
   */
  protected HandlerTarget<T> getWrapHandlerTarget() {
    return withDefault(wrapHandlerTarget, () -> {
      var replacement = getParsedReplacement();
      return replacement == null
          ? new TerminalReplaceTargetImpl<T>(getWrapTarget(), detectionResult())
          : new ParsedReplaceTargetImpl<T>(getWrapTarget(), replacement, getParseMethod());
    });
  }

  /**
   * Gets the wrap target.
   * 
   * @return The wrap target
   */
  protected String getWrapTarget() {
    return wrapTarget;
  }

  /**
   * Gets the wrapping injector.
   * 
   * @return The injector
   */
  protected ActivatableLifecycleUser<T> getInjector() {
    return withDefault(injector,
        () -> RunPhase.<T>withInjectExternalDeclarations(
            getInjectionLocation(), getInjectionExternalDeclaration()))
        .activation(this::isActive);
  }

  /**
   * Gets the injection location.
   * 
   * @return The injection location
   */
  protected InjectionPoint getInjectionLocation() {
    return withDefault(injectionLocation, InjectionPoint.BEFORE_DECLARATIONS);
  }

  /**
   * Gets the injection external declaration.
   * 
   * @return The injection external declaration
   */
  protected String getInjectionExternalDeclaration() {
    return injectionExternalDeclaration;
  }
}
