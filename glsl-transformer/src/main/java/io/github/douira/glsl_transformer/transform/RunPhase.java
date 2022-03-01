package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.tree.ParseTree;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * A run phase simply executes one method when it is executed in a level by the
 * execution planner. Even though it extends {@link TransformationPhase}, no
 * listener methods on
 * it are executed.
 */
public abstract class RunPhase<T> extends TransformationPhase<T> {
  /**
   * This method is implemented by subclasses to be executed by the phase
   * planner at the right time.
   * 
   * @param ctx The root node of the parse tree being transformed
   */
  protected abstract void run(TranslationUnitContext ctx);

  @Override
  final protected boolean checkBeforeWalk(TranslationUnitContext ctx) {
    if (isActive()) {
      run(ctx);
    }
    return false;
  }

  @Override
  final protected void runAfterWalk(TranslationUnitContext ctx) {
  }

  /**
   * Returns a new run phase that injects the given nodes at the given location.
   * 
   * @see TransformationPhase#injectNodes(InjectionPoint, ParseTree...)
   * 
   * @param <R>      The job parameter type
   * @param location The location to inject the nodes at
   * @param newNodes The nodes to inject
   * @return The run phase that does only these injections
   */
  public static <R> RunPhase<R> withInjectNodes(InjectionPoint location, ParseTree newNodes) {
    return new RunPhase<R>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectNodes(location, newNodes);
      }
    };
  }

  /**
   * Returns a new run phase that injects the given strings as an external
   * declarations at the given location.
   * 
   * @see TransformationPhase#injectExternalDeclarations(InjectionPoint,
   *      String...)
   * 
   * @param <R>      The job parameter type
   * @param location The location to inject the external declarations at
   * @param str      The strings to parse as external declarations
   * @return The run phase that does only these injections
   */
  public static <R> RunPhase<R> withInjectExternalDeclarations(InjectionPoint location, String... str) {
    return new RunPhase<R>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclarations(location, str);
      }
    };
  }
}
