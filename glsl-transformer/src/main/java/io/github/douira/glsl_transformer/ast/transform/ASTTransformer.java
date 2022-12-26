package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

/**
 * The ast transformer transforms some representation of an input and returns
 * the same kind of output. The implementations of this class determine how it
 * works in detail.
 */
public abstract class ASTTransformer<T extends JobParameters, V> extends ASTParser
    implements ParameterizedTransformer<T, V> {
  private T jobParameters;
  private static final PrintType defaultPrintType = PrintType.COMPACT;
  private PrintType printType = defaultPrintType;

  @Override
  public T getJobParameters() {
    return jobParameters;
  }

  @Override
  public void setJobParameters(T parameters) {
    jobParameters = parameters;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    super.setTokenFilter(tokenFilter);
    ((TokenFilter<T>) tokenFilter).setJobParametersSupplier(this::getJobParameters);
  }

  public void setPrintType(PrintType printType) {
    this.printType = printType;
  }

  public PrintType getPrintType() {
    return printType;
  }
}
