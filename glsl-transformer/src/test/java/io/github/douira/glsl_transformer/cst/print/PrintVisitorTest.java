package io.github.douira.glsl_transformer.cst.print;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.*;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.basic.InjectionPoint;
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
            injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "f;");
            injectNode(InjectionPoint.BEFORE_VERSION, new StringNode(""));
            injectNode(InjectionPoint.BEFORE_VERSION, new StringNode("__ "));
            injectNode(InjectionPoint.BEFORE_VERSION, new UnparsableCSTNode() {
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
    var modifiedManager = new CSTTransformer<NonFixedJobParameters>() {
      @Override
      public String transformStream(IntStream stream, NonFixedJobParameters parameters) throws RecognitionException {
        var tree = getInternalParser().parse(stream, null, GLSLParser::translationUnit);
        var tokenStream = getInternalParser().getTokenStream();
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