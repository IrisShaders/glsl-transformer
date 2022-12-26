package io.github.douira.glsl_transformer;

import static org.fusesource.jansi.Ansi.*;

import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.basic.EnhancedParser;
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
  @Disabled
  void testDebugTree() {
    var parser = new EnhancedParser();
    parser.getLexer().enableCustomDirective = true;
    parser.getLexer().enableIncludeDirective = true;
    var walker = new ParseTreeWalker();

    Stream.of(
        TestResourceManager.getResource(FileLocation.GRAMMAR_DEBUG))
        .forEach(resource -> {
          var content = resource.content();
          var debugListener = new PrintCSTDebug();
          walker.walk(debugListener, parser.parse(content));
          System.out.println(ansi().fgBrightMagenta().bold().a(resource.getScenarioName()).reset());
          System.out.println(content);
          System.out.println(debugListener.builder.toString());
        });
  }
}
