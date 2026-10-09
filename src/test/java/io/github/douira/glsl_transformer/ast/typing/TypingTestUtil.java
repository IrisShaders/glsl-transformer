package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;
import java.util.stream.Collectors;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.GroupingExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.FunctionDefinition;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.ExpressionStatement;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.ASTParser;
import io.github.douira.glsl_transformer.ast.traversal.*;

/**
 * Shared helpers for the typing tests.
 */
public class TypingTestUtil {
  private static final ASTParser PARSER = new ASTParser();

  static {
    PARSER.getLexer().enableStrings = true;
  }

  public static TranslationUnit parse(String source) {
    return PARSER.parseTranslationUnit(RootSupplier.DEFAULT, source);
  }

  /**
   * Derives the environment of a test source. The version and extensions are
   * taken from the source, the stage can be given with a comment of the form
   * {@code //! frag} at the start of a line.
   */
  public static TypeEnvironment environmentOf(TranslationUnit tree, String source) {
    var environment = TypeEnvironment.of(tree);
    for (var line : source.split("\n")) {
      if (line.startsWith("//! ")) {
        environment = environment.withStage(
            Objects.requireNonNull(ShaderStage.fromExtension(line.substring(4).trim()), line));
      }
    }
    return environment;
  }

  public static TypeAnalysis analyze(String source) {
    var tree = parse(source);
    return new TypeAnalyzer().setEnvironment(environmentOf(tree, source)).analyze(tree);
  }

  public static String codes(TypeAnalysis analysis) {
    return analysis.diagnostics().isEmpty() ? "ok"
        : analysis.diagnostics().stream().map(d -> d.code().name()).collect(Collectors.joining(" "));
  }

  /**
   * Returns the expression of the last statement of the last function in the
   * translation unit, without the parentheses around it.
   */
  public static Expression lastExpression(ASTNode tree) {
    var children = ((TranslationUnit) tree).getChildren();
    var statements = ((FunctionDefinition) children.get(children.size() - 1)).getBody().getStatements();
    var statement = (ExpressionStatement) statements.get(statements.size() - 1);
    return ((GroupingExpression) statement.getExpression()).getOperand();
  }

  /**
   * Types an expression in the context of global declarations and describes the
   * result as the type name, followed by the diagnostic codes if there are any.
   * The expression is wrapped in parentheses because the grammar parses a
   * statement that only consists of an identifier as a declaration.
   */
  public static String describeExpression(String declarations, String expression) {
    var analysis = analyze(declarations + "\nvoid typingTestMain() {\n(" + expression + ");\n}\n");
    var type = analysis.typeOf(lastExpression(analysis.getTop())).getTypeName();
    return analysis.diagnostics().isEmpty() ? type : type + " ! " + codes(analysis);
  }

  /**
   * Returns the expressions in the tree that the analysis has no type for. Since
   * every expression gets a type, there should be none.
   */
  public static List<Expression> untypedExpressions(ASTNode tree, TypeAnalysis analysis) {
    var untyped = new ArrayList<Expression>();
    ASTWalker.walk(new ASTListener() {
      @Override
      public void enterExpression(Expression node) {
        if (analysis.typeOf(node) == null) {
          untyped.add(node);
        }
      }
    }, tree);
    return untyped;
  }
}
