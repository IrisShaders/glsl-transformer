package io.github.douira.glsl_transformer.ast.node;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class VersionStatementTest extends TestWithSingleASTTransformer {
  @Test
  void testMissingWithEnsure() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var tu = p.parseTranslationUnit(root, ";");
      tu.ensureVersionStatement();
      assertNotNull(tu.versionStatement);
      assertNull(tu.versionStatement.profile);
      assertEquals(Version.GLSL11, tu.versionStatement.version);
    });
  }

  @Test
  void testMissingVersionStatement() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var tu = p.parseTranslationUnit(root, ";");
      assertNull(tu.versionStatement);
    });
  }

  @Test
  void testMissingProfile() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var tu = p.parseTranslationUnit(root, "#version 330\n;");
      assertNotNull(tu.versionStatement);
      assertNull(tu.versionStatement.profile);
      assertEquals(Version.GLSL33, tu.versionStatement.version);
    });
  }

  @Test
  void testVersionWithProfile() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var tu = p.parseTranslationUnit(root, "#version 330 core\n;");
      assertNotNull(tu.versionStatement);
      assertEquals(Profile.CORE, tu.versionStatement.profile);
      assertEquals(Version.GLSL33, tu.versionStatement.version);
    });
  }

  @Test
  void testVersionWithProfile2() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var tu = p.parseTranslationUnit(root, "#version 140 compatibility\n;");
      assertNotNull(tu.versionStatement);
      assertEquals(Profile.COMPATIBILITY, tu.versionStatement.profile);
      assertEquals(Version.GLSL14, tu.versionStatement.version);
    });
  }

  @Test
  void testGetNormalizedProfile() {
    Root.indexBuildSession(p.supplyRoot(), (root) -> {
      var a = p.parseTranslationUnit(root, "#version 330\n;");
      assertEquals(Profile.CORE, a.versionStatement.getNormalizedProfile());
      var b = p.parseTranslationUnit(root, "#version 150\n;");
      assertEquals(Profile.CORE, b.versionStatement.getNormalizedProfile());
      var c = p.parseTranslationUnit(root, "#version 140\n;");
      assertEquals(Profile.COMPATIBILITY, c.versionStatement.getNormalizedProfile());
    });
  }
}
