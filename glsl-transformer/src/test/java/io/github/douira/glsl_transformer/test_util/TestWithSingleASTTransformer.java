package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.*;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.transform.SingleASTTransformer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.util.TriConsumer;

public abstract class TestWithSingleASTTransformer {
  public SingleASTTransformer<JobParameters> p;

  /**
   * How many times the transform method is called on the transformer here.
   */
  public static final int REPEAT = 3;

  @BeforeEach
  public void setUp() {
    p = new SingleASTTransformer<>() {
      private Consumer<TranslationUnit> localTransformation;

      @Override
      public void setTransformation(Consumer<TranslationUnit> transformation) {
        super.setTransformation(transformation);
        localTransformation = transformation;
      }

      @Override
      public void setTransformation(BiConsumer<TranslationUnit, Root> transformation) {
        super.setTransformation(transformation);
        localTransformation = wrapTransformation(this, transformation);
      }

      @Override
      public void setTransformation(TriConsumer<TranslationUnit, Root, JobParameters> transformation) {
        super.setTransformation(transformation);
        localTransformation = wrapTransformation(this, transformation);
      }

      @Override
      public String transform(String str) throws RecognitionException {
        var translationUnit = parseTranslationUnit(str);
        var tuClone = translationUnit.cloneSeparate();
        localTransformation.accept(translationUnit);
        var result = ASTPrinter.print(getPrintType(), translationUnit);

        // make sure it does the same as the original
        assertEquals(super.transform(str), result, "It should do the same as the original");

        // make sure results are the same if the result is cloned
        assertEquals(result, ASTPrinter.print(getPrintType(),
            translationUnit.cloneSeparate()));

        // make sure cloning and then transforming also works
        localTransformation.accept(tuClone);
        assertEquals(result, ASTPrinter.print(getPrintType(), tuClone));

        return result;
      }
    };
    p.setSLLOnly();
  }

  public void assertTransform(String expected, String input) {
    p.setPrintType(PrintType.COMPACT);
    assertEquals(expected, p.transform(input));
    p.setPrintType(PrintType.INDENTED);
  }

  public void assertTransformI(String expected, String input) {
    p.setPrintType(PrintType.INDENTED);
    assertEquals(expected, p.transform(input));
  }
}
