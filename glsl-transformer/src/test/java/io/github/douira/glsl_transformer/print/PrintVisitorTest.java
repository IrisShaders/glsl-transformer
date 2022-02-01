package io.github.douira.glsl_transformer.print;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.ast.UnparsableASTNode;
import io.github.douira.glsl_transformer.transform.RunPhase;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationManager;

public class PrintVisitorTest extends TestWithTransformationManager<Void> {
  @Test
  void testEmptyLiteral() {
    assertEquals(
        "b;\n__ f;a;",
        runTransformation("a;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "f;");
            injectNode(InjectionPoint.BEFORE_VERSION, new StringNode(""));
            injectNode(InjectionPoint.BEFORE_VERSION, new StringNode("__ "));
            injectNode(InjectionPoint.BEFORE_VERSION, new UnparsableASTNode() {
              @Override
              protected String getPrinted() {
                return null;
              }
            });
            injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "b;");
          }
        }),
        "It should properly handle UnparsableASTNode tree members even if they are empty or contain null content.");
  }

  @Test
  void testPrintTree() {
    TransformationManager<Void> modifiedManager = new TransformationManager<>() {
      @Override
      public String transformStream(IntStream stream, Void parameters) throws RecognitionException {
        var tree = parse(stream, null, GLSLParser::translationUnit);
        transformTree(tree, tokenStream);
        var printed = PrintVisitor.printTree(tokenStream, tree);
        return printed;
      }
    };
    modifiedManager.registerTransformation(new Transformation<>(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "f;");
      }
    }));
    assertEquals("f;a;", modifiedManager.transform("a;"), "It should print even without a token filter");
  }
}
