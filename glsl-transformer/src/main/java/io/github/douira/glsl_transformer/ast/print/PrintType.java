package io.github.douira.glsl_transformer.ast.print;

import java.util.function.Supplier;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public enum PrintType {
  SIMPLE(SimplePrinter::new),
  INDENTED(IndentingPrinter::new),
  COMPACT(CompactPrinter::new);

  private final Supplier<TokenProcessor> printerSupplier;

  PrintType(Supplier<TokenProcessor> printerSupplier) {
    this.printerSupplier = printerSupplier;
  }

  public TokenProcessor getTokenProcessor(ASTNode node) {
    return printerSupplier.get();
  }
}
