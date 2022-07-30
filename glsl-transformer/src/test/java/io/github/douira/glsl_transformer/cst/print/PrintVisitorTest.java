package io.github.douira.glsl_transformer.cst.print;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.cst.node.*;
import io.github.douira.glsl_transformer.cst.transform.*;
import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestWithResource;

public class PrintVisitorTest extends TestWithResource {
  @Test
  void testEmptyLiteral() {
    assertEquals(
        "b;\n__ f;a;",
        run("a;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectExternalDeclaration(CSTInjectionPoint.BEFORE_VERSION, "f;");
            injectNode(CSTInjectionPoint.BEFORE_VERSION, new StringNode(""));
            injectNode(CSTInjectionPoint.BEFORE_VERSION, new StringNode("__ "));
            injectNode(CSTInjectionPoint.BEFORE_VERSION, new UnparsableCSTNode() {
              @Override
              protected String getPrinted() {
                return null;
              }
            });
            injectExternalDeclaration(CSTInjectionPoint.BEFORE_VERSION, "b;");
          }
        }),
        "It should properly handle UnparsableASTNode tree members even if they are empty or contain null content.");
  }

  @Test
  void testPrintTree() {
    var modifiedManager = new CSTTransformer<NonFixedJobParameters>() {
      @Override
      public String transform(String str, NonFixedJobParameters parameters) throws RecognitionException {
        var tree = parseTranslationUnit(str);
        var tokenStream = getTokenStream();
        transformTree(tree, tokenStream);
        var printed = PrintVisitor.printTree(tokenStream, tree);
        return printed;
      }
    };
    modifiedManager.addConcurrent(new Transformation<>(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(CSTInjectionPoint.BEFORE_VERSION, "f;");
      }
    }));
    assertEquals("f;a;", modifiedManager.transform("a;"), "It should print even without a token filter");
  }
}
