package io.github.douira.glsl_transformer.print;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.*;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.transform.*;

public class PrintVisitorTest extends TestWithTransformationManager<NonFixedJobParameters> {
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
    var modifiedManager = new TransformationManager<NonFixedJobParameters>() {
      @Override
      public String transformStream(IntStream stream, NonFixedJobParameters parameters) throws RecognitionException {
        var tree = parse(stream, null, GLSLParser::translationUnit);
        transformTree(tree, tokenStream);
        var printed = PrintVisitor.printTree(tokenStream, tree);
        return printed;
      }
    };
    modifiedManager.addConcurrent(new Transformation<>(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "f;");
      }
    }));
    assertEquals("f;a;", modifiedManager.transform("a;"), "It should print even without a token filter");
  }
}
