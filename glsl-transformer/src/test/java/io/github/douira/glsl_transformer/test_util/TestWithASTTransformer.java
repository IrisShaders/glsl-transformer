package io.github.douira.glsl_transformer.test_util;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.transform.ASTTransformer;

public abstract class TestWithASTTransformer {
  public ASTTransformer transformer;

  @BeforeEach
  public void setUp() {
    transformer = new ASTTransformer();
  }
}
