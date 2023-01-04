package io.github.douira.glsl_transformer.ast.print;

import java.util.function.Supplier;

public enum PrintType {
  SIMPLE(SimplePrinter::new),
  INDENTED(IndentingPrinter::new),
  COMPACT(CompactPrinter::new),
  SIMPLE_ANNOTATED(() -> new LineAnnotator(new SimplePrinter())),
  INDENTED_ANNOTATED(() -> new LineAnnotator(new IndentingPrinter())),
  COMPACT_ANNOTATED(() -> new LineAnnotator(new CompactPrinter()));

  private final Supplier<TokenProcessor> printerSupplier;

  PrintType(Supplier<TokenProcessor> printerSupplier) {
    this.printerSupplier = printerSupplier;
  }

  public TokenProcessor getTokenProcessor() {
    return printerSupplier.get();
  }
}
