package io.github.douira.glsl_transformer.ast.print;

import java.util.function.Supplier;

public enum PrintType {
  SIMPLE(SimplePrinter::new),
  INDENTED(IndentingPrinter::new),
  COMPACT(CompactPrinter::new),

  /**
   * Not implemented yet
   */
  INDENTED_ANNOTATED(() -> new IndentingPrinter(new LineAnnotator())),

  /**
   * Not implemented yet
   */
  COMPAT_ANNOTATED(() -> new CompactPrinter(new LineAnnotator()));

  private final Supplier<TokenProcessor> printerSupplier;

  PrintType(Supplier<TokenProcessor> printerSupplier) {
    this.printerSupplier = printerSupplier;
  }

  public TokenProcessor getTokenProcessor() {
    return printerSupplier.get();
  }
}
