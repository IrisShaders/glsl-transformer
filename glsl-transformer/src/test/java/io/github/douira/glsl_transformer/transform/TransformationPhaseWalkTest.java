package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.TestForExecutionOrder;

public class TransformationPhaseWalkTest extends TestForExecutionOrder {
  @Test
  void testMaximumWalkDepth0() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        setMaximumWalkDepth(0);
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex++;
      }
    });
    manager.transform("int a = 1; int b = 1;");
    assertEquals(1, nextIndex, "It should only visit the root node");
  }

  @Test
  void testMaximumWalkDepth1() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        setMaximumWalkDepth(1);
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex += 1;
      }
    });
    manager.transform("int a = 1; int b = 1;");
    assertEquals(3, nextIndex, "It should visit the root node and its children");
  }

  @Test
  void testWalkIntoRulesEmpty() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        setWalkIntoRules(new HashSet<>());
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex++;
      }
    });
    manager.transform("int a = 1; int b = 1;");
    assertEquals(1, nextIndex, "It only visits the root node");
  }

  @Test
  void testWalkIntoRulesRoot() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        addWalkIntoRule(TranslationUnitContext.class);
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex++;
      }
    });
    manager.transform("int a = 1; int b = 1;");
    assertEquals(3, nextIndex, "It should visit the root node and its children");
  }

  @Test
  void testWalkIntoRulesSelective() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        addWalkIntoRule(TranslationUnitContext.class);
        addWalkIntoRule(ExternalDeclarationContext.class);
        addWalkIntoRule(EmptyDeclarationContext.class);
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex++;
      }
    });
    manager.transform("int a = 1; ;");
    assertEquals(5, nextIndex,
        "It should visit both external declarations but only walk deeper into one of the children");
  }

  @Test
  void testBothWalkLimiting() {
    manager.addConcurrent(new WalkPhase<NonFixedJobParameters>() {
      {
        addWalkIntoRule(TranslationUnitContext.class);
        addWalkIntoRule(ExternalDeclarationContext.class);
        addWalkIntoRule(EmptyDeclarationContext.class);
        setMaximumWalkDepth(1);
      }

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        nextIndex++;
      }
    });
    manager.transform("int a = 1; ;");
    assertEquals(3, nextIndex,
        "It should only visit the root node and its children");
  }
}
