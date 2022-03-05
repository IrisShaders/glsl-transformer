package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestForExecutionOrder;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class ExecutionPlannerTest extends TestForExecutionOrder {
  @Test
  void testGetRootNode() {
    manager.addConcurrent(new Transformation<>(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertSame(ctx, manager.getRootNode());
        nextIndex++;
      }
    }));

    assertNull(
        manager.getRootNode(), "It should only have a root node during transformation.");
    manager.transform("");
    assertEquals(1, nextIndex, "It should execute the added phase");
    assertNull(
        manager.getRootNode(), "It should only have a root node during transformation.");
  }

  @Test
  void testAddConcurrent() {
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.transform("");
    assertEquals(1, nextIndex, "It should run the added phase");
  }

  @Test
  void testAddConcurrentMultiple() {
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testAddConcurrentMultipleSingleLevel() {
    for (int i = 0; i < 3; i++) {
      manager.addConcurrent(new RunPhase<Void>() {
        @Override
        protected void run(TranslationUnitContext ctx) {
          nextIndex++;
        }

        @Override
        public void resetState() {
          assertEquals(0, nextIndex);
        }
      });
    }
    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testGetRootTransformation() {
    manager
        .getRootTransformation()
        .addRootDependency(RunPhase.withRun(() -> nextIndex++));
    manager.transform("");
    assertEquals(1, nextIndex, "It should run the root transformation");
  }

  @Test
  void testThrowMultipleFinalization() {
    manager.planExecution();
    assertThrows(IllegalStateException.class, () -> manager.planExecution(),
        "It should throw if execution planning is initiated manually multiple times.");
  }

  @Test
  void testAllowManualFinalization() {
    manager.planExecution();
    assertDoesNotThrow(() -> manager.transform(""),
        "It should not throw after regular manual planning.");
  }

  @Test
  void testIncrementalResetState() {
    transformation.chainDependent(
        new RunPhase<Void>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            nextIndex++;
          }

          @Override
          public void resetState() {
            assertEquals(0, nextIndex, "The first chained dependent should reset first.");
          }
        });
    transformation.chainDependent(
        new RunPhase<Void>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            nextIndex++;
          }

          @Override
          public void resetState() {
            assertEquals(1, nextIndex, "The first chained dependent should reset after the first one has run.");
          }
        });
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testNestedSingleTransformation() {
    transformation.addRootDependency(new Transformation<>(
        assertOrderPhase(0, "The nested phase should run.")));
    manager.transform("");
    assertEquals(1, nextIndex, "The phase should run.");
  }

  @Test
  void testNestedMultipleTransformation() {
    transformation.addEndDependent(new Transformation<>(
        assertOrderPhase(0, "The first nested phase should run first.")));
    transformation.chainDependent(new Transformation<>(
        assertOrderPhase(1, "The second chained nested phase should run second.")));
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testNestedMixedTransformation() {
    transformation.addDependency(
        new Transformation<>(
            assertOrderPhase(2, "The phase in the dependent transformation should run third.")),
        new Transformation<>(
            assertOrderPhase(0, "The phase in the dependency transformation should run first.")));
    transformation.chainConcurrentSibling(assertOrderPhase(1, "The chained sibling phase should run second."));
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run.");
  }

  @Test
  void testSharedExternalTransformationDependency() {
    var a = transformation.addRootDependency(
        new Transformation<>(assertResetPhase(1, "The concurrent nested phases should run in the second level")));
    var b = transformation.addRootDependency(
        new Transformation<>(assertResetPhase(1, "The concurrent nested phases should run in the second level")));
    var sibling = assertOrderPhase(0, "The shared dependency phase should run first.");
    transformation.addDependency(a, sibling);
    transformation.addDependency(b, sibling);
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run.");
  }

  @Test
  void testCrossTransformationDependency() {
    var aPhase = assertOrderPhase(1, "The transformation with the dependent phase should run second.");
    var bPhase = assertOrderPhase(0, "The transformation with the dependency phase should run first.");
    transformation.addRootDependency(new Transformation<>(aPhase));
    transformation.addRootDependency(new Transformation<>(bPhase));
    transformation.addDependency(aPhase, bPhase);
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testDeeplyNestedTransformation() {
    transformation.addRootDependency(new Transformation<>(new Transformation<>(new Transformation<>(
        assertOrderPhase(0, "The nested phase should run.")))));
    manager.transform("");
    assertEquals(1, nextIndex, "The nested phase should run.");
  }

  @Test
  void testNoInitOnSecondRun() {
    transformation.addRootDependency(new RunPhase<Void>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
      }

      @Override
      public void init() {
        assertEquals(0, nextIndex, "The phase should only be initialized once.");
        nextIndex += 100;
      }

      @Override
      public void resetState() {
        assertEquals(0, nextIndex % 100 % 10, "The phase should be reset every time.");
        nextIndex += 10;
      }
    });
    manager.transform("");
    manager.transform("");
    manager.transform("");
    assertEquals(130, nextIndex, "The phase should run each time.");
  }
}
