package io.github.douira.glsl_transformer.ast.print;

import java.util.function.Supplier;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public enum PrintType {
  SIMPLE(SimpleASTPrinter::new),
  INDENTED(IndentingASTPrinter::new),
  COMPACT(CompactASTPrinter::new);

  private final Supplier<ASTPrinter> printerSupplier;

  PrintType(Supplier<ASTPrinter> printerSupplier) {
    this.printerSupplier = printerSupplier;
  }

  public ASTPrinter getPrinter(ASTNode node) {
    return printerSupplier.get();
  }
}
