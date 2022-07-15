package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.Predicate;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.util.ExcludeFromJacocoGeneratedReport;

public enum ASTInjectionPoint {
  /**
   * The first index.
   */
  BEFORE_ALL() {
    @Override
    public int getInjectionIndex(TranslationUnit translationUnit) {
      return 0;
    }
  },

  /**
   * The last most index before any declaration, function definition or layout
   * defaults.
   */
  BEFORE_DECLARATIONS() {
    @Override
    public int getInjectionIndex(TranslationUnit translationUnit) {
      return findLastIndexWith(translationUnit,
          node -> node instanceof FunctionDefinition
              || node instanceof DeclarationExternalDeclaration
              || node instanceof LayoutDefaults);
    }
  },

  /**
   * The last most index before any function definition.
   */
  BEFORE_FUNCTIONS() {
    @Override
    public int getInjectionIndex(TranslationUnit translationUnit) {
      return findLastIndexWith(translationUnit,
          node -> node instanceof FunctionDefinition);
    }
  },

  /**
   * The last index.
   */
  END() {
    @Override
    public int getInjectionIndex(TranslationUnit translationUnit) {
      return translationUnit.getChildren().size();
    }
  };

  public abstract int getInjectionIndex(TranslationUnit translationUnit);

  private static int findLastIndexWith(
      TranslationUnit translationUnit,
      Predicate<ExternalDeclaration> stopPredicate) {
    int i = 0;
    int size = translationUnit.children.size();
    while (i < size) {
      if (stopPredicate.test(translationUnit.children.get(i))) {
        return i;
      }
      i++;
    }
    return i;
  }

  @ExcludeFromJacocoGeneratedReport
  protected boolean checkChildRelevant(Class<?> childClass) {
    throw new AssertionError("A non-special injection point doesn't have a child relevance implementation!");
  }
}
