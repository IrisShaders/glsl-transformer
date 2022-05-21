package io.github.douira.glsl_transformer.core;

import java.util.*;
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
  protected Collection<String> detectionResultsDirect = new ArrayList<String>(0);
  protected Collection<String> externalDeclarationsDirect = new ArrayList<String>(0);

  private Supplier<ActivatableLifecycleUser<T>> wrapResultDetector = once(this::getWrapResultDetector);
  private Supplier<Collection<String>> detectionResults = once(this::getDetectionResults);

  private Supplier<String> parsedReplacement = once(this::getParsedReplacement);
  private Supplier<Function<GLSLParser, ExtendedContext>> parseMethod = once(this::getParseMethod);
  private Supplier<ActivatableLifecycleUser<T>> wrappingReplacer = once(this::getWrappingReplacer);
  private Supplier<Collection<HandlerTarget<T>>> wrapHandlerTargets = once(this::getWrapHandlerTargets);
  private Supplier<String> wrapTarget = once(this::getWrapTarget);

  private Supplier<ActivatableLifecycleUser<T>> injector = once(this::getInjector);
  private Supplier<InjectionPoint> injectionLocation = once(this::getInjectionLocation);
  private Supplier<Collection<String>> injectionExternalDeclarations = once(this::getInjectionExternalDeclarations);

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
   * Adds a detection result to the list of static detection result to use. This
   * constitutes the default behavior of {@link #getDetectionResults()}.
   * 
   * @param detectionResult The detection result to add
   */
  public void addDetectionResult(String detectionResult) {
    detectionResultsDirect.add(detectionResult);
  }

  /**
   * Adds an external declaration to the list of external declarations to use.
   * This constitutes the default behavior of
   * {@link #getInjectionExternalDeclarations()}.
   * 
   * @param externalDeclaration The external declaration to add
   */
  public void addExternalDeclaration(String externalDeclaration) {
    externalDeclarationsDirect.add(externalDeclaration);
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * This method can deal with a list of detection results that is longer than one
   * item. It can even handle this list's contents or identity changing, but it
   * doesn't handle the list getting longer and added items will be ignored. If
   * the list gets shorter, the generated wrap throw targets will become no-ops
   * but still exist.
   * 
   * @return The configuration property value
   */
  protected ActivatableLifecycleUser<T> getWrapResultDetector() {
    var staticDetects = detectionResults();
    if (staticDetects.size() == 1) {
      return new SearchTerminals<T>().singleTarget(new WrapThrowTarget<T>() {
        @Override
        protected String getWrapResult() {
          return detectionResult();
        }
      });
    } else {
      return new SearchTerminals<T>() {
        List<String> detectsList;

        @Override
        public void resetState() {
          var detects = detectionResults();
          if (detects != detectsList) {
            detectsList = new ArrayList<>(detects);
          }
        }

        @Override
        protected Collection<HandlerTarget<T>> getTargets() {
          var targets = new ArrayList<HandlerTarget<T>>(detectsList.size());
          for (var i = 0; i < detectsList.size(); i++) {
            var index = i;
            targets.add(new WrapThrowTarget<T>() {
              @Override
              protected String getWrapResult() {
                if (detectsList.size() <= index) {
                  return null;
                }
                return detectsList.get(index);
              }
            });
          }
          return targets;
        }
      };
    }
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected Collection<String> getDetectionResults() {
    return detectionResultsDirect.isEmpty() ? CompatUtil.listOf(parsedReplacement()) : detectionResultsDirect;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected String getParsedReplacement() {
    throw new IllegalStateException(
        "No parsed replacement is set. This error can also occur if no detection results are set.");
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected Function<GLSLParser, ExtendedContext> getParseMethod() {
    return GLSLParser::expression;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
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
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
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
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected String getWrapTarget() {
    throw new IllegalStateException("No wrap target is set");
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected ActivatableLifecycleUser<T> getInjector() {
    return new RunPhase<T>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        var location = injectionLocation();
        for (String externalDeclaration : injectionExternalDeclarations()) {
          injectExternalDeclaration(location, externalDeclaration);
        }
      }
    };
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected InjectionPoint getInjectionLocation() {
    return InjectionPoint.BEFORE_DECLARATIONS;
  }

  /**
   * Gets the value of a configuration property. This method should not be called
   * by subclasses, only implemented in case a custom value generation is needed.
   * 
   * @return The configuration property value
   */
  protected Collection<String> getInjectionExternalDeclarations() {
    if (externalDeclarationsDirect.isEmpty()) {
      throw new IllegalStateException("No injection external declarations are set");
    }
    return externalDeclarationsDirect;
  }

  // the rest of this class is just configuration methods
  // #region Configuration methods
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
   * @param detectionResults The detection results
   * @return This object
   */
  public WrapIdentifier<T> detectionResults(Collection<String> detectionResults) {
    this.detectionResults = swapSupplier(this.detectionResults, detectionResults);
    return this;
  }

  /**
   * Sets a single detection result. Other detection results will be ignored.
   * 
   * @see #detectionResults(Collection)
   * 
   * @param detectionResult The detection result
   * @return This object
   */
  public WrapIdentifier<T> detectionResult(String detectionResult) {
    return detectionResults(CompatUtil.listOf(detectionResult));
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
   * Sets the external declarations for the generated injector.
   * 
   * @param injectionExternalDeclarations The external declarations
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclarations(Collection<String> injectionExternalDeclarations) {
    this.injectionExternalDeclarations = swapSupplier(this.injectionExternalDeclarations,
        injectionExternalDeclarations);
    return this;
  }

  /**
   * Sets a single external declaration for the generated injector. No other
   * external declarations will be considered.
   * 
   * @param injectionExternalDeclaration The external declaration
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclaration(String injectionExternalDeclaration) {
    return injectionExternalDeclarations(CompatUtil.listOf(injectionExternalDeclaration));
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param wrapResultDetector The value supplier
   * @return This object
   */
  public WrapIdentifier<T> wrapResultDetector(Supplier<ActivatableLifecycleUser<T>> wrapResultDetector) {
    this.wrapResultDetector = swapSupplier(this.wrapResultDetector, wrapResultDetector);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param detectionResults The value supplier
   * @return This object
   */
  public WrapIdentifier<T> detectionResults(Supplier<Collection<String>> detectionResults) {
    this.detectionResults = swapSupplier(this.detectionResults, detectionResults);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param parsedReplacement The value supplier
   * @return This object
   */
  public WrapIdentifier<T> parsedReplacement(Supplier<String> parsedReplacement) {
    this.parsedReplacement = swapSupplier(this.parsedReplacement, parsedReplacement);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param parseMethod The value supplier
   * @return This object
   */
  public WrapIdentifier<T> parseMethod(Supplier<Function<GLSLParser, ExtendedContext>> parseMethod) {
    this.parseMethod = swapSupplier(this.parseMethod, parseMethod);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param wrappingReplacer The value supplier
   * @return This object
   */
  public WrapIdentifier<T> wrappingReplacer(Supplier<ActivatableLifecycleUser<T>> wrappingReplacer) {
    this.wrappingReplacer = swapSupplier(this.wrappingReplacer, wrappingReplacer);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param wrapHandlerTargets The value supplier
   * @return This object
   */
  public WrapIdentifier<T> wrapHandlerTargets(Supplier<Collection<HandlerTarget<T>>> wrapHandlerTargets) {
    this.wrapHandlerTargets = swapSupplier(this.wrapHandlerTargets, wrapHandlerTargets);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param wrapTarget The value supplier
   * @return This object
   */
  public WrapIdentifier<T> wrapTarget(Supplier<String> wrapTarget) {
    this.wrapTarget = swapSupplier(this.wrapTarget, wrapTarget);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param injector The value supplier
   * @return This object
   */
  public WrapIdentifier<T> injector(Supplier<ActivatableLifecycleUser<T>> injector) {
    this.injector = swapSupplier(this.injector, injector);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param injectionLocation The value supplier
   * @return This object
   */
  public WrapIdentifier<T> injectionLocation(Supplier<InjectionPoint> injectionLocation) {
    this.injectionLocation = swapSupplier(this.injectionLocation, injectionLocation);
    return this;
  }

  /**
   * Sets the supplier for a configuration property.
   * 
   * @param injectionExternalDeclarations The value supplier
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclarations(Supplier<Collection<String>> injectionExternalDeclarations) {
    this.injectionExternalDeclarations = swapSupplier(this.injectionExternalDeclarations,
        injectionExternalDeclarations);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> wrapResultDetector(CachePolicy newPolicy) {
    this.wrapResultDetector = swapPolicy(this.wrapResultDetector, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> detectionResults(CachePolicy newPolicy) {
    this.detectionResults = swapPolicy(this.detectionResults, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> parsedReplacement(CachePolicy newPolicy) {
    this.parsedReplacement = swapPolicy(this.parsedReplacement, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> parseMethod(CachePolicy newPolicy) {
    this.parseMethod = swapPolicy(this.parseMethod, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> wrappingReplacer(CachePolicy newPolicy) {
    this.wrappingReplacer = swapPolicy(this.wrappingReplacer, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> wrapHandlerTargets(CachePolicy newPolicy) {
    this.wrapHandlerTargets = swapPolicy(this.wrapHandlerTargets, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> wrapTarget(CachePolicy newPolicy) {
    this.wrapTarget = swapPolicy(this.wrapTarget, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> injector(CachePolicy newPolicy) {
    this.injector = swapPolicy(this.injector, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> injectionLocation(CachePolicy newPolicy) {
    this.injectionLocation = swapPolicy(this.injectionLocation, newPolicy);
    return this;
  }

  /**
   * Changes the cache policy of a configuration property.
   * 
   * @param newPolicy The new cache policy
   * @return This object
   */
  public WrapIdentifier<T> injectionExternalDeclarations(CachePolicy newPolicy) {
    this.injectionExternalDeclarations = swapPolicy(this.injectionExternalDeclarations, newPolicy);
    return this;
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final ActivatableLifecycleUser<T> wrapResultDetector() {
    return wrapResultDetector.get().activation(this::isActive);
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final Collection<String> detectionResults() {
    return detectionResults.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final String detectionResult() {
    return detectionResults.get().stream().findFirst().orElse(null);
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final String parsedReplacement() {
    return parsedReplacement.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final Function<GLSLParser, ExtendedContext> parseMethod() {
    return parseMethod.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final ActivatableLifecycleUser<T> wrappingReplacer() {
    return wrappingReplacer.get().activation(this::isActive);
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final Collection<HandlerTarget<T>> wrapHandlerTargets() {
    return wrapHandlerTargets.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final String wrapTarget() {
    return wrapTarget.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final ActivatableLifecycleUser<T> injector() {
    return injector.get().activation(this::isActive);
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final InjectionPoint injectionLocation() {
    return injectionLocation.get();
  }

  /**
   * Returns the proper value of a configuration property.
   * 
   * @return The configuration property value
   */
  protected final Collection<String> injectionExternalDeclarations() {
    return injectionExternalDeclarations.get();
  }
  // #endregion
}
