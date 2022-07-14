package io.github.douira.glsl_transformer.cst.transform;

import org.antlr.v4.runtime.tree.ParseTree;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

/**
 * A run phase simply executes one method when it is executed in a level by the
 * execution planner. Even though it extends {@link TransformationPhase}, no
 * listener methods on
 * it are executed.
 */
public abstract class RunPhase<T extends JobParameters> extends TransformationPhase<T> {
  /**
   * This method is implemented by subclasses to be executed by the phase
   * planner at the right time.
   * 
   * @param ctx The root node of the parse tree being transformed
   */
  protected abstract void run(TranslationUnitContext ctx);

  @Override
  protected boolean canWalk() {
    return false;
  }

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
   * @see TransformationPhase#injectNodes(CSTInjectionPoint, ParseTree...)
   * 
   * @param <R>      The job parameter type
   * @param location The location to inject the nodes at
   * @param newNodes The nodes to inject
   * @return The run phase that does only these injections
   */
  public static <R extends JobParameters> RunPhase<R> withInjectNodes(
      CSTInjectionPoint location, ParseTree newNodes) {
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
   * @see TransformationPhase#injectExternalDeclarations(CSTInjectionPoint,
   *      String...)
   * 
   * @param <R>      The job parameter type
   * @param location The location to inject the external declarations at
   * @param str      The strings to parse as external declarations
   * @return The run phase that does only these injections
   */
  public static <R extends JobParameters> RunPhase<R> withInjectExternalDeclarations(
      CSTInjectionPoint location, String... str) {
    return new RunPhase<R>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclarations(location, str);
      }
    };
  }

  /**
   * Creates a new run phase that only executes the given runnable function. If
   * the function is {@code null}, the generated phase does nothing.
   * 
   * @param <R> The job parameter type
   * @param run The runnable to run in the run phase
   * @return The generated run phase
   */
  public static <R extends JobParameters> RunPhase<R> withRun(Runnable run) {
    return new RunPhase<R>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        if (run != null) {
          run.run();
        }
      }
    };
  }
}
