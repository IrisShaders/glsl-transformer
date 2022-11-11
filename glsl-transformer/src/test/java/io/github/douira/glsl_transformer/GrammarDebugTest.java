package io.github.douira.glsl_transformer;

import static org.fusesource.jansi.Ansi.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.cst.transform.*;
import io.github.douira.glsl_transformer.job_parameter.WrappedParameters;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.FileLocation;

/**
 * This test is only enabled for debugging purposes.
 */
public class GrammarDebugTest {
  @AfterEach
  void flushOutput() {
    System.out.flush();
  }

  @Test
  void testParseTree() {
    var t = new CSTTransformer<WrappedParameters<StringBuilder>>();
    t.addConcurrent(new PrintTreeDebug());
    t.getLexer().enableCustomDirective = true;
    t.getLexer().enableIncludeDirective = true;

    Stream.of(
        TestResourceManager.getResource(FileLocation.GRAMMAR_DEBUG))
        .forEach(resource -> {
          var content = resource.content();
          var builder = new StringBuilder();
          t.transform(content, new WrappedParameters<>(builder));
          System.out.println(ansi().fgBrightMagenta().bold().a(resource.getScenarioName()).reset());
          System.out.println(content);
          System.out.println(builder.toString());
        });
  }
}
