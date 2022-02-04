package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.core.target.ParsedReplaceTargetImpl;
import io.github.douira.glsl_transformer.core.target.TerminalReplaceTargetImpl;
import io.github.douira.glsl_transformer.core.target.WrapThrowTargetImpl;
import io.github.douira.glsl_transformer.transform.RunPhase;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;
import io.github.douira.glsl_transformer.transform.TransformationPhase.InjectionPoint;

/**
 * The wrap identifier transformation wraps the usage of a certain identifier
 * with new code by replacing its usage with a new expression and inserting code
 * that takes care of handling the conversion from the new to the old value. It
 * also checks that the wrapped value isn't already present in the code.
 */
public class WrapIdentifier<T> extends Transformation<T> {
  /**
   * Creates a new wrap identifier transformation.
   * 
   * @param wrapResultFilter A phase that makes sure the wrap result doesn't
   *                         already exist in the tree
   * @param wrappingReplacer The replacer phase that should replace a target
   *                         identifier with a replacement expression or
   *                         identifier
   * @param wrappingInjector A transformation phase that does the additional
   *                         code injection, usually providing a definition
   *                         for the newly inserted identifier in the form of
   *                         an external declaration of some sort
   */
  public WrapIdentifier(
      TransformationPhase<T> wrapResultFilter,
      TransformationPhase<T> wrappingReplacer,
      TransformationPhase<T> wrappingInjector) {
    // throw if the wrap result already exists
    addPhase(wrapResultFilter);
    addPhase(wrappingReplacer);
    addConcurrentPhase(wrappingInjector);
  }

  /**
   * Creates a new wrap identifier transformation with a fixed result identifier.
   * 
   * @see WrapIdentifier#WrapIdentifier(TransformationPhase, TransformationPhase,
   *      TransformationPhase)
   * 
   * @param wrapResult       The identifier that's inserted for the wrapping
   * @param wrappingReplacer The replacer phase that replaces a target identifier
   *                         with a replacement expression or identifier
   * @param wrappingInjector A transformation phase that does the additional code
   *                         injection, usually providing a definition for the
   *                         newly inserted identifier in the form of an external
   *                         declaration of some sort
   */
  public WrapIdentifier(
      String wrapResult,
      TransformationPhase<T> wrappingReplacer,
      TransformationPhase<T> wrappingInjector) {
    this(new WrapThrowTargetImpl<T>(wrapResult), wrappingReplacer, wrappingInjector);
  }

  /**
   * Creates a new wrap identifier transformation with a fixed result identifier
   * and a fixed replacement phase for which only the target is given.
   * 
   * @param wrapResult       The identifier that's inserted for the wrapping
   * @param wrappingTarget   A replacement target to be used in a search terminals
   *                         phase
   * @param wrappingInjector A transformation phase that does the additional code
   *                         injection, usually providing a definition for the
   *                         newly inserted identifier in the form of an external
   *                         declaration of some sort
   */
  public WrapIdentifier(
      String wrapResult,
      HandlerTarget<T> wrappingTarget,
      TransformationPhase<T> wrappingInjector) {
    this(wrapResult, new SearchTerminals<T>(wrappingTarget), wrappingInjector);
  }

  /**
   * Creates a new wrap identifier transformation that uses an unparsed terminal
   * replace target. It uses the wrap result as the identifier to disallow as well
   * as the terminal to insert as a replacement. This is a commonly used operation
   * when the inserted replacement is just a different identifier.
   * 
   * @param <T>              The job parameter type
   * @param wrapTarget       The identifier to replace
   * @param wrapResult       The identifier that will be used to replace it
   * @param wrappingInjector A transformation phase that does the additional code
   *                         injection
   * @return The wrap identifier transformation with the given parameters
   */
  public static <T> WrapIdentifier<T> fromTerminal(
      String wrapTarget,
      String wrapResult,
      RunPhase<T> wrappingInjector) {
    return new WrapIdentifier<T>(
        wrapResult,
        new TerminalReplaceTargetImpl<T>(wrapTarget, wrapResult),
        wrappingInjector);
  }

  /**
   * Creates a new wrap identifier transformation that uses a parsed replace
   * target that replaces identifiers with an expression. (which may also just be
   * an identifier)
   * 
   * @param <T>              The job parameter type
   * @param wrapTarget       The identifier to replace
   * @param wrapResult       The identifier that will be used to replace it
   * @param wrapExpression   The expression to insert instead of the
   *                         {@code wrapTarget}
   * @param wrappingInjector A transformation phase that does the additional code
   *                         injection
   * @return The wrap identifier transformation with the given parameters
   */
  public static <T> WrapIdentifier<T> fromExpression(
      String wrapTarget,
      String wrapResult,
      String wrapExpression,
      RunPhase<T> wrappingInjector) {
    return new WrapIdentifier<T>(
        wrapResult,
        new ParsedReplaceTargetImpl<T>(wrapTarget, wrapExpression, GLSLParser::expression),
        wrappingInjector);
  }

  /**
   * Creates a new wrap identifier transformation that inserts a parsed expression
   * as a replacement and inserts a new external declaration.
   * 
   * @see #fromExpression(String, String, String, RunPhase)
   * 
   * @param <T>            The job parameter type
   * @param wrapTarget     The identifier to replace
   * @param wrapResult     The identifier that will be used to replace it
   * @param wrapExpression The expression to insert instead of the
   *                       {@code wrapTarget}
   * @param location       The injection location for the new code
   * @param injectedCode   The code to parse and inject as an external declaration
   *                       at the given location
   * @return The wrap identifier transformation with the given parameters
   */
  public static <T> WrapIdentifier<T> withExternalDeclaration(
      String wrapTarget,
      String wrapResult,
      String wrapExpression,
      InjectionPoint location,
      String injectedCode) {
    return fromExpression(wrapTarget, wrapResult, wrapExpression, new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(location, injectedCode);
      }
    });
  }
}
