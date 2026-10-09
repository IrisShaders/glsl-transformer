package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.Declaration;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.TypeSpecifier;

/**
 * Performs type analysis on an AST: it determines the type of every
 * expression, resolves every reference to its declaration and reports
 * problems.
 * <p>
 * By default the analysis is lenient: it never throws on invalid shaders but
 * gives everything that cannot be typed the {@link ErrorType} and records a
 * {@link Diagnostic}. In strict mode a {@link TypeAnalysisException} is thrown
 * for the first problem.
 * <p>
 * Translation units, external declarations, declarations, statements,
 * expressions and type specifiers can be analyzed. Trees with a different top
 * node yield an empty analysis.
 */
public final class TypeAnalyzer {
  private TypeEnvironment environment;
  private boolean strict;

  /**
   * Sets the environment to analyze in.
   *
   * @param environment the environment, or null to derive the environment from
   *                    the analyzed tree with
   *                    {@link TypeEnvironment#of(ASTNode)}
   * @return this analyzer
   */
  public TypeAnalyzer setEnvironment(TypeEnvironment environment) {
    this.environment = environment;
    return this;
  }

  /**
   * Sets whether the analysis throws on the first problem.
   *
   * @param strict true to throw a {@link TypeAnalysisException} for the first
   *               problem
   * @return this analyzer
   */
  public TypeAnalyzer setStrict(boolean strict) {
    this.strict = strict;
    return this;
  }

  /**
   * Analyzes the tree with the given top node. The tree is not modified.
   *
   * @param top the top node of the tree to analyze
   * @return the result of the analysis
   */
  public TypeAnalysis analyze(ASTNode top) {
    var env = environment != null ? environment : TypeEnvironment.of(top);
    var context = new AnalysisContext(top, env, new Scope(BuiltinRegistry.getScope(env)), strict, false);
    analyze(context, top);
    return context.analysis;
  }

  static void analyze(AnalysisContext ctx, ASTNode top) {
    if (top instanceof TranslationUnit translationUnit) {
      for (var externalDeclaration : translationUnit.getChildren()) {
        ctx.declarations.externalDeclaration(externalDeclaration);
      }
    } else if (top instanceof ExternalDeclaration externalDeclaration) {
      ctx.declarations.externalDeclaration(externalDeclaration);
    } else if (top instanceof Declaration declaration) {
      ctx.declarations.declaration(declaration);
    } else if (top instanceof Statement statement) {
      ctx.statements.statement(statement);
    } else if (top instanceof Expression expression) {
      ctx.expressions.type(expression);
    } else if (top instanceof FullySpecifiedType specifiedType) {
      ctx.declarations.specifiedType(specifiedType);
    } else if (top instanceof TypeSpecifier typeSpecifier) {
      ctx.declarations.typeSpecifier(typeSpecifier);
    }
  }
}
