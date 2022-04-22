package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.SnapshotUtil;
import io.github.douira.glsl_transformer.TestCaseSource;
import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationPhase.InjectionPoint;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * TODO: test node removal, replacement and injection in local roots
 */
@ExtendWith({ SnapshotExtension.class })
public class TransformationPhaseTest extends TestWithTransformationManager<NonFixedJobParameters> {
  private Expect expect;

  @BeforeAll
  static void setupInput() {
    loadResource(FileLocation.EXTERNAL_DECLARATIONS);
  }

  @Test
  void testCompilePath() {
    runTransformation(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertEquals(
            compilePath("/translationUnit/externalDeclaration")
                .evaluate(ctx).size(),
            ctx.getChildCount() - 2,
            "It should compile a functioning xpath");
      }
    });
  }

  @Test
  void testCompilePattern() {
    runTransformation(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        var match = compilePattern(
            "varying <type:typeSpecifier> varyVec;",
            GLSLParser.RULE_externalDeclaration)
                .match(ctx.getChild(5));
        assertTrue(match.succeeded(), "It should compile a functioning pattern");
        assertEquals(
            "vec2", match.get("type").getText(),
            "It should compile a functioning pattern");
      }
    });
  }

  @Test
  void testCreateLocalRoot() {
    runTransformation(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        var localRoot = createLocalRoot("f;", ctx, GLSLParser::externalDeclaration);
        assertTrue(localRoot.isLocalRoot(), "It should produce a local root");
        assertFalse(localRoot.isRoot(), "It should not mark the node as the global root");
        assertEquals(ctx, localRoot.parent,
            "It should produce a node bound to the given parent");
      }
    });
  }

  @Test
  void testFindAndMatch() {
    runTransformation(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        // note that usually the xpath and pattern should be compiled in the init method
        var matches = findAndMatch(ctx,
            compilePath("/translationUnit/externalDeclaration/declaration"),
            compilePattern(
                "<layout:layoutQualifier> <storage:storageQualifier> <specifier:typeSpecifier> <name:IDENTIFIER>;",
                GLSLParser.RULE_declaration));
        assertEquals(2, matches.size());
      }
    });
  }

  @Test
  void testGetSiblings() {
    runTransformation(new RunPhase<>() {
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
  @TestCaseSource
  @SnapshotName("testInjectNode")
  void testInjectNode(String scenario, String input) {
    for (var location : InjectionPoint.values()) {
      setTestCode(input);
      var output = runTransformation(new RunPhase<>() {
        @Override
        protected void run(TranslationUnitContext ctx) {
          injectExternalDeclaration(location, "//prefix\ninjection; //suffix\n");
        }
      });

      expect
          .scenario(scenario + "/" + location.toString().toLowerCase())
          .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(input, output));
    }
  }

  @ParameterizedTest
  @TestCaseSource("testInjectNode")
  @SnapshotName("testInjectDefine")
  void testInjectDefine(String scenario, String input) {
    for (var location : InjectionPoint.values()) {
      setTestCode(input);
      var output = runTransformation(new RunPhase<>() {
        @Override
        protected void run(TranslationUnitContext ctx) {
          injectDefine(location, "foo bar + baz");
        }
      });

      expect
          .scenario(scenario + "/" + location.toString().toLowerCase())
          .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(input, output));
    }
  }

  @Test
  void testInjectNodes() {
    assertEquals(
        "e;//\nf;a;//present\nb;c;d;",
        runTransformation("a;//present\nb;c;d;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectNodes(InjectionPoint.BEFORE_VERSION,
                new LinkedList<ParseTree>(List.of(
                    createLocalRoot("e;", getRootNode(), GLSLParser::externalDeclaration),
                    createLocalRoot("//\nf;", getRootNode(), GLSLParser::externalDeclaration))));
          }
        }));
  }

  @Test
  void testInjectExternalDeclaration() {
    assertEquals(
        "e;a;//present\nb;c;int foo;",
        runTransformation("a;//present\nb;c;int foo;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            injectExternalDeclaration(InjectionPoint.BEFORE_VERSION, "e;");
          }
        }),
        "It should inject an external declaration at the right position");
  }

  @Test
  void testIsActive() {
    assertTrue(
        (new WalkPhase<>() {
        }).isActive(),
        "It should always be active");
  }

  @Test
  void testRemoveNode() {
    assertEquals(
        "a;//present\nd;",
        runTransformation("a;//present\nb;c;d;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            removeNode(ctx.externalDeclaration(1));
            removeNode(ctx.externalDeclaration(1));
          }
        }),
        "It should correctly remove two nodes at the second position");
  }

  @Test
  void testReplaceNode() {
    assertEquals(
        "a;new;//present\nc;d;",
        runTransformation("a;//present\nb;c;d;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            replaceNode(ctx.externalDeclaration(1), "new;", GLSLParser::externalDeclaration);
          }
        }),
        "It should correctly replace the second node");

    assertThrows(IllegalArgumentException.class,
        () -> runTransformation("a;", new RunPhase<>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            replaceNode(ctx, null);
          }
        }), "It should throw if the root node is replaced");
  }

  @Test
  void testPreviousNodeHandling() {
    // this timeout is pretty long because overhead takes a while sometimes
    assertTimeoutPreemptively(Duration.ofMillis(500),
        () -> runTransformation("a;", new WalkPhase<>() {
          @Override
          public void visitTerminal(TerminalNode node) {
            replaceNode((TreeMember) node, new StringNode("b"));
          }
        }), "It should not get stuck in an infinite loop after replacement in a walk phase");
  }

  @Test
  void testSetParent() {
    runTransformation(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertSame(manager.getParser(), getParser(),
            "It should return the previously set parser inside the execution planner");
        assertSame(manager.getLexer(), getLexer(),
            "It should return the previously set lexer inside the execution planner");
      }
    });
  }
}
