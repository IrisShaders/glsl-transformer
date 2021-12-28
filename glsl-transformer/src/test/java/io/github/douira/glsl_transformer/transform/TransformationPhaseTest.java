package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.SnapshotUtil;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.TestCaseReader;
import io.github.douira.glsl_transformer.transform.TransformationPhase.InjectionPoint;

@ExtendWith({ SnapshotExtension.class })
public class TransformationPhaseTest extends TestWithTransformationManager {
  private Expect expect;

  @BeforeAll
  static void setupInput() {
    loadResource(FileLocation.EXTERNAL_DECLARATIONS);
  }

  @Test
  void testCompilePath() {
    wrapRunTransform(new RunPhase() {
      XPath path;

      @Override
      protected void init() {
        path = compilePath("/translationUnit/externalDeclaration");

      }

      @Override
      protected void run(TranslationUnitContext ctx) {
        assertEquals(
            path.evaluate(ctx).size(), ctx.getChildCount() - 2,
            "It should compile a functioning xpath");
      }
    });
  }

  @Test
  void testCompilePattern() {
    wrapRunTransform(new RunPhase() {
      ParseTreePattern pattern;

      @Override
      protected void init() {
        pattern = compilePattern("varying <type:typeSpecifier> varyVec;",
            GLSLParser.RULE_externalDeclaration);

      }

      @Override
      protected void run(TranslationUnitContext ctx) {
        var match = pattern.match(ctx.getChild(5));
        assertTrue(match.succeeded(), "It should compile a functioning pattern");
        assertEquals(
            "vec2", match.get("type").getText(),
            "It should compile a functioning pattern");
      }
    });
  }

  @Test
  void testCreateLocalRoot() {

  }

  @Test
  void testFindAndMatch() {

  }

  @Test
  void testGetSiblings() {
    wrapRunTransform(new RunPhase() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertSame(
            ctx.children, TransformationPhase.getSiblings(ctx.versionStatement()),
            "It should find the siblings of a node");
      }
    });
  }

  /**
   * NOTE: #define is not a parsed directive and is disregarded,
   * NOTE: periods in the snapshots are inserted by the snapshot framework on
   * purpose since it uses three empty lines to separate scenarios
   * 
   * @implNote After transformation extra dots for newlines are inserted if there
   *           are three in a row. At the beginning and end where the snapshot
   *           library inserts extra newlines on its own dots are inserted even if
   *           there are just two newlines.
   */
  @ParameterizedTest
  @ArgumentsSource(TestCaseReader.class)
  @SnapshotName("testInjectNode")
  void testInjectNode(String scenario, String input) {
    for (var injectionPoint : InjectionPoint.values()) {
      setTestCode(input);
      var output = wrapRunTransform(new RunPhase() {
        @Override
        protected void run(TranslationUnitContext ctx) {
          injectExternalDeclaration("//prefix\ninjection; //suffix\n", injectionPoint);
        }
      });

      expect
          .scenario(scenario + "/" + injectionPoint.toString().toLowerCase())
          .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(input, output));
    }
  }

  @Test
  void testInjectNodes() {
    assertEquals(
        "e;//\nf;a;//present\nb;c;d;",
        wrapRunTransform("a;//present\nb;c;d;", new RunPhase() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectNodes(new LinkedList<ParseTree>(List.of(
                createLocalRoot("e;", getRootNode(), GLSLParser::externalDeclaration),
                createLocalRoot("//\nf;", getRootNode(), GLSLParser::externalDeclaration))),
                InjectionPoint.BEFORE_VERSION);
          }
        }));
  }

  @Test
  void testInjectExternalDeclaration() {
    assertEquals(
        "e;a;//present\nb;c;int foo;",
        wrapRunTransform("a;//present\nb;c;int foo;", new RunPhase() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectExternalDeclaration("e;", InjectionPoint.BEFORE_DECLARATIONS);
          }
        }));
  }

  @Test
  void testIsActive() {
    var phase = new TransformationPhase() {
    };
    assertTrue(phase.isActive(), "It should always be active");
  }

  @Test
  void testRemoveNode() {
    assertEquals(
        "a;d;",
        wrapRunTransform("a;//present\nb;c;d;", new RunPhase() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            removeNode(ctx.externalDeclaration(1));
            removeNode(ctx.externalDeclaration(1));
          }
        }));
  }

  @Test
  void testReplaceNode() {
    assertEquals(
        "a;new;c;d;",
        wrapRunTransform("a;//present\nb;c;d;", new RunPhase() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            replaceNode(ctx.externalDeclaration(1), "new;", GLSLParser::externalDeclaration);
          }
        }));
  }

  @Test
  void testSetParent() {
    wrapRunTransform(new RunPhase() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertSame(manager.getParser(), getParser(),
            "It should return the previously set parser inside the phase collector");
      }
    });
  }
}
