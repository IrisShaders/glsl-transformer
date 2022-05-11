package io.github.douira.glsl_transformer.core;

import java.util.Collection;
import java.util.function.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.core.target.*;
import io.github.douira.glsl_transformer.transform.*;
import io.github.douira.glsl_transformer.tree.*;
import io.github.douira.glsl_transformer.util.CompatUtil;

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
public class WrapIdentifier<T extends JobParameters> extends ConfigurableTransformation<T> {
  private Supplier<ActivatableLifecycleUser<T>> wrapResultDetector = once(this::getWrapResultDetector);
  private Supplier<String> detectionResult = once(this::getParsedReplacement);

  private Supplier<String> parsedReplacement = once(this::getParsedReplacement);
  private Supplier<Function<GLSLParser, ExtendedContext>> parseMethod = once(this::getParseMethod);
  private Supplier<ActivatableLifecycleUser<T>> wrappingReplacer = once(this::getWrappingReplacer);
  private Supplier<Collection<HandlerTarget<T>>> wrapHandlerTargets = once(this::getWrapHandlerTargets);
  private Supplier<String> wrapTarget = once(this::getWrapTarget);

  private Supplier<ActivatableLifecycleUser<T>> injector = once(this::getInjector);
  private Supplier<InjectionPoint> injectionLocation = once(this::getInjectionLocation);
  private Supplier<String> injectionExternalDeclaration = once(this::getInjectionExternalDeclaration);

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
    this.wrapResultDetector = swapSupplier(this.wrapResultDetector, wrapResultDetector);
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
    this.detectionResult = swapSupplier(this.detectionResult, detectionResult);
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
    this.parsedReplacement = swapSupplier(this.parsedReplacement, parsedReplacement);
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
    this.parseMethod = swapSupplier(this.parseMethod, parseMethod);
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
    this.wrappingReplacer = swapSupplier(this.wrappingReplacer, wrappingReplacer);
    return this;
  }

  /**
   * Sets the handler target that does the replacement of the target. Generated
   * from the wrap target by default.
   * 
   * @param wrapHandlerTargets The replacement target handler
   * @return This object
   */
  public WrapIdentifier<T> wrapHandlerTargets(Collection<HandlerTarget<T>> wrapHandlerTargets) {
    this.wrapHandlerTargets = swapSupplier(this.wrapHandlerTargets, wrapHandlerTargets);
    return this;
  }

  /**
   * Sets the target that will be replaced.
   * 
   * @param wrapTarget The replacement target identifier
   * @return This object
   */
  public WrapIdentifier<T> wrapTarget(String wrapTarget) {
    this.wrapTarget = swapSupplier(this.wrapTarget, wrapTarget);
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
    this.injector = swapSupplier(this.injector, injector);
    return this;
  }

  /**
   * Sets the injection location for the generated injector.
   * 
   * @param injectionLocation The injection location
   * @return This object
   */
  public WrapIdentifier<T> injectionLocation(InjectionPoint injectionLocation) {
    this.injectionLocation = swapSupplier(this.injectionLocation, injectionLocation);
    return this;
  }

  /**
   * Sets the external declaration for the generated injector.
   * 
   * @param injectionExternalDeclaration The external declaration
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclaration(String injectionExternalDeclaration) {
    this.injectionExternalDeclaration = swapSupplier(this.injectionExternalDeclaration, injectionExternalDeclaration);
    return this;
  }

  public WrapIdentifier<T> wrapResultDetector(Supplier<ActivatableLifecycleUser<T>> wrapResultDetector) {
    this.wrapResultDetector = swapSupplier(this.wrapResultDetector, wrapResultDetector);
    return this;
  }

  public WrapIdentifier<T> detectionResult(Supplier<String> detectionResult) {
    this.detectionResult = swapSupplier(this.detectionResult, detectionResult);
    return this;
  }

  public WrapIdentifier<T> parsedReplacement(Supplier<String> parsedReplacement) {
    this.parsedReplacement = swapSupplier(this.parsedReplacement, parsedReplacement);
    return this;
  }

  public WrapIdentifier<T> parseMethod(Supplier<Function<GLSLParser, ExtendedContext>> parseMethod) {
    this.parseMethod = swapSupplier(this.parseMethod, parseMethod);
    return this;
  }

  public WrapIdentifier<T> wrappingReplacer(Supplier<ActivatableLifecycleUser<T>> wrappingReplacer) {
    this.wrappingReplacer = swapSupplier(this.wrappingReplacer, wrappingReplacer);
    return this;
  }

  public WrapIdentifier<T> wrapHandlerTargets(Supplier<Collection<HandlerTarget<T>>> wrapHandlerTargets) {
    this.wrapHandlerTargets = swapSupplier(this.wrapHandlerTargets, wrapHandlerTargets);
    return this;
  }

  public WrapIdentifier<T> wrapTarget(Supplier<String> wrapTarget) {
    this.wrapTarget = swapSupplier(this.wrapTarget, wrapTarget);
    return this;
  }

  public WrapIdentifier<T> injector(Supplier<ActivatableLifecycleUser<T>> injector) {
    this.injector = swapSupplier(this.injector, injector);
    return this;
  }

  public WrapIdentifier<T> injectionLocation(Supplier<InjectionPoint> injectionLocation) {
    this.injectionLocation = swapSupplier(this.injectionLocation, injectionLocation);
    return this;
  }

  public WrapIdentifier<T> injectionExternalDeclaration(Supplier<String> injectionExternalDeclaration) {
    this.injectionExternalDeclaration = swapSupplier(this.injectionExternalDeclaration, injectionExternalDeclaration);
    return this;
  }

  public WrapIdentifier<T> wrapResultDetector(CachePolicy newPolicy) {
    this.wrapResultDetector = swapPolicy(this.wrapResultDetector, newPolicy);
    return this;
  }

  public WrapIdentifier<T> detectionResult(CachePolicy newPolicy) {
    this.detectionResult = swapPolicy(this.detectionResult, newPolicy);
    return this;
  }

  public WrapIdentifier<T> parsedReplacement(CachePolicy newPolicy) {
    this.parsedReplacement = swapPolicy(this.parsedReplacement, newPolicy);
    return this;
  }

  public WrapIdentifier<T> parseMethod(CachePolicy newPolicy) {
    this.parseMethod = swapPolicy(this.parseMethod, newPolicy);
    return this;
  }

  public WrapIdentifier<T> wrappingReplacer(CachePolicy newPolicy) {
    this.wrappingReplacer = swapPolicy(this.wrappingReplacer, newPolicy);
    return this;
  }

  public WrapIdentifier<T> wrapHandlerTargets(CachePolicy newPolicy) {
    this.wrapHandlerTargets = swapPolicy(this.wrapHandlerTargets, newPolicy);
    return this;
  }

  public WrapIdentifier<T> wrapTarget(CachePolicy newPolicy) {
    this.wrapTarget = swapPolicy(this.wrapTarget, newPolicy);
    return this;
  }

  public WrapIdentifier<T> injector(CachePolicy newPolicy) {
    this.injector = swapPolicy(this.injector, newPolicy);
    return this;
  }

  public WrapIdentifier<T> injectionLocation(CachePolicy newPolicy) {
    this.injectionLocation = swapPolicy(this.injectionLocation, newPolicy);
    return this;
  }

  public WrapIdentifier<T> injectionExternalDeclaration(CachePolicy newPolicy) {
    this.injectionExternalDeclaration = swapPolicy(this.injectionExternalDeclaration, newPolicy);
    return this;
  }

  protected final ActivatableLifecycleUser<T> wrapResultDetector() {
    return wrapResultDetector.get().activation(this::isActive);
  }

  protected final String detectionResult() {
    return detectionResult.get();
  }

  protected final String parsedReplacement() {
    return parsedReplacement.get();
  }

  protected final Function<GLSLParser, ExtendedContext> parseMethod() {
    return parseMethod.get();
  }

  protected final ActivatableLifecycleUser<T> wrappingReplacer() {
    return wrappingReplacer.get().activation(this::isActive);
  }

  protected final Collection<HandlerTarget<T>> wrapHandlerTargets() {
    return wrapHandlerTargets.get();
  }

  protected final String wrapTarget() {
    return wrapTarget.get();
  }

  protected final ActivatableLifecycleUser<T> injector() {
    return injector.get().activation(this::isActive);
  }

  protected final InjectionPoint injectionLocation() {
    return injectionLocation.get();
  }

  protected final String injectionExternalDeclaration() {
    return injectionExternalDeclaration.get();
  }

  /**
   * Gets the result detector.
   * 
   * @return The result detector
   */
  protected ActivatableLifecycleUser<T> getWrapResultDetector() {
    return new SearchTerminals<T>().target(new WrapThrowTarget<T>() {
      @Override
      protected String getWrapResult() {
        return detectionResult();
      }
    });
  }

  /**
   * Gets the detection result.
   * 
   * @return The detection result
   */
  protected String getDetectionResult() {
    return parsedReplacement();
  }

  /**
   * Gets the parsed replacement.
   * 
   * @return The parsed replacement
   */
  protected String getParsedReplacement() {
    throw new IllegalStateException("No parsed replacement is set");
  }

  /**
   * Gets the replacement expression parsing method.
   * 
   * @return The replacement expression parsing method
   */
  protected Function<GLSLParser, ExtendedContext> getParseMethod() {
    return GLSLParser::expression;
  }

  /**
   * Gets the wrapping replacer.
   * 
   * @return The wrapping replacer
   */
  protected ActivatableLifecycleUser<T> getWrappingReplacer() {
    return new SearchTerminals<T>() {
      @Override
      protected Collection<HandlerTarget<T>> getTargets() {
        return wrapHandlerTargets();
      }
    };
  }

  /**
   * Gets the replacement target handler.
   * 
   * @return The replacement target handler
   */
  protected Collection<HandlerTarget<T>> getWrapHandlerTargets() {
    // TODO: this is not great strcuturally since it's checking for a throw
    try {
      parsedReplacement();
      return CompatUtil.listOf(new ParsedReplaceTarget<T>() {
        @Override
        public String getNeedle() {
          return wrapTarget();
        }

        @Override
        protected String getNewContent(TreeMember node, String match) {
          return parsedReplacement();
        }

        @Override
        protected Function<GLSLParser, ExtendedContext> getParseMethod(TreeMember node, String match) {
          return parseMethod();
        }
      });
    } catch (Exception e) {
      return CompatUtil.listOf(new TerminalReplaceTarget<T>() {
        @Override
        public String getNeedle() {
          return wrapTarget();
        }

        @Override
        protected String getTerminalContent() {
          return detectionResult();
        }
      });
    }
  }

  /**
   * Gets the wrap target.
   * 
   * @return The wrap target
   */
  protected String getWrapTarget() {
    throw new IllegalStateException("No wrap target is set");
  }

  /**
   * Gets the wrapping injector.
   * 
   * @return The injector
   */
  protected ActivatableLifecycleUser<T> getInjector() {
    return new RunPhase<T>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(injectionLocation(), injectionExternalDeclaration());
      }
    };
  }

  /**
   * Gets the injection location.
   * 
   * @return The injection location
   */
  protected InjectionPoint getInjectionLocation() {
    return InjectionPoint.BEFORE_DECLARATIONS;
  }

  /**
   * Gets the injection external declaration.
   * 
   * @return The injection external declaration
   */
  protected String getInjectionExternalDeclaration() {
    throw new IllegalStateException("No injection external declaration is set");
  }
}
