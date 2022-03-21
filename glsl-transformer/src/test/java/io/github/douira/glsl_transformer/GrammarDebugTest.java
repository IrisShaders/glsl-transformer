package io.github.douira.glsl_transformer;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.transform.TransformationManager;

public class GrammarDebugTest {
  @Test
  @Disabled
  void testParseTree() {
    var man = new TransformationManager<StringBuilder>(false);
    man.addConcurrent(new PrintTreeDebug());

    Stream.of(
        TestResourceManager.getResource(FileLocation.GRAMMAR_DEBUG))
        .forEach(resource -> {
          var content = resource.content();
          var builder = new StringBuilder();
          man.transform(content, builder);
          System.out.println(ansi().fgBrightMagenta().bold().a(resource.getScenarioName()).reset());
          System.out.println(content);
          System.out.println(builder.toString());
        });
  }

  @AfterEach
  void flushOutput() {
    System.out.flush();
  }
}
