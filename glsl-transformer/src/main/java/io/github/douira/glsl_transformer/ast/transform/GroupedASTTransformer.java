package io.github.douira.glsl_transformer.ast.transform;

import java.util.Map;
import java.util.function.*;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

/**
 * The grouped AST transformer parses multiple strings stored in an arbitrarily
 */
public class GroupedASTTransformer<T extends JobParameters, K, M extends Map<K, String>, N extends Map<K, TranslationUnit>>
    extends ASTTransformer<T, Map<K, String>> {
  private Consumer<N> transformation;
  private Supplier<N> tuMapSupplier;
  private Supplier<M> resultMapSupplier;

  public GroupedASTTransformer(
      Consumer<N> transformation,
      Supplier<N> tuMapSupplier,
      Supplier<M> resultMapSupplier) {
    this.transformation = transformation;
    this.tuMapSupplier = tuMapSupplier;
    this.resultMapSupplier = resultMapSupplier;
  }

  public GroupedASTTransformer(
      Supplier<N> tuMapSupplier,
      Supplier<M> resultMapSupplier) {
    this.tuMapSupplier = tuMapSupplier;
    this.resultMapSupplier = resultMapSupplier;
  }

  public GroupedASTTransformer(Consumer<N> transformation) {
    this.transformation = transformation;
  }

  public GroupedASTTransformer() {
  }

  public void setTransformation(Consumer<N> transformation) {
    this.transformation = transformation;
  }

  public void setTuMapSupplier(Supplier<N> tuMapSupplier) {
    this.tuMapSupplier = tuMapSupplier;
  }

  public void setResultMapSupplier(Supplier<M> resultMapSupplier) {
    this.resultMapSupplier = resultMapSupplier;
  }

  @Override
  public M transform(PrintType printType, Map<K, String> items)
      throws RecognitionException {
    // parse all items
    var translationUnits = tuMapSupplier.get();
    for (var entry : items.entrySet()) {
      translationUnits.put(entry.getKey(), parseTranslationUnit(entry.getValue()));
    }

    // transform them all at once
    transformation.accept(translationUnits);

    // print all items
    var printedItems = resultMapSupplier.get();
    for (var entry : translationUnits.entrySet()) {
      printedItems.put(entry.getKey(), ASTPrinter.print(printType, entry.getValue()));
    }
    return printedItems;
  }
}
