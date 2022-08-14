package io.github.douira.glsl_transformer.ast.transform;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.job_parameter.*;

/**
 * The ast transformer transforms some representation of an input and returns
 * the same kind of output. The implementations of this class determine how it
 * works in detail.
 */
public abstract class ASTTransformer<T extends JobParameters, V> extends ASTParser
    implements ParameterizedTransformer<T, V> {
  private T jobParameters;

  @Override
  public T getJobParameters() {
    return jobParameters;
  }

  @Override
  public void setJobParameters(T parameters) {
    jobParameters = parameters;
  }

  public abstract V transform(PrintType printType, V str) throws RecognitionException;

  @Override
  public V transform(V str) throws RecognitionException {
    return transform(PrintType.COMPACT, str);
  }
}
