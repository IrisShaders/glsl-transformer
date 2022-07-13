package io.github.douira.glsl_transformer;

import static org.fusesource.jansi.Ansi.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.transform.*;

/**
 * This test is only enabled for debugging purposes.
 */
public class GrammarDebugTest {
  @AfterEach
  void flushOutput() {
    System.out.flush();
  }

  @Test
  @Disabled
  void testParseTree() {
    var transformer = new CSTTransformer<WrappedParameters<StringBuilder>>(false);
    transformer.addConcurrent(new PrintTreeDebug());

    Stream.of(
        TestResourceManager.getResource(FileLocation.GRAMMAR_DEBUG))
        .forEach(resource -> {
          var content = resource.content();
          var builder = new StringBuilder();
          transformer.transform(content, new WrappedParameters<>(builder));
          System.out.println(ansi().fgBrightMagenta().bold().a(resource.getScenarioName()).reset());
          System.out.println(content);
          System.out.println(builder.toString());
        });
  }
}
