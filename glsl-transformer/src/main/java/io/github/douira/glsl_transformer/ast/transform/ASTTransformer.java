package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

/**
 * The ast transformer transforms some representation of an input and returns
 * the same kind of output. The implementations of this class determine how it
 * works in detail.
 */
public abstract class ASTTransformer<J extends JobParameters, V> extends ASTParser
    implements ParameterizedTransformer<J, V> {
  private J jobParameters;
  private PrintType printType = PrintType.COMPACT;

  @Override
  public J getJobParameters() {
    return jobParameters;
  }

  @Override
  public void setJobParameters(J parameters) {
    jobParameters = parameters;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    super.setTokenFilter(tokenFilter);
    ((TokenFilter<J>) tokenFilter).setJobParametersSupplier(this::getJobParameters);
  }

  public void setPrintType(PrintType printType) {
    this.printType = printType;
  }

  public PrintType getPrintType() {
    return printType;
  }
}
