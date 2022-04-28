package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.*;
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
      manager.addConcurrent(
          assertResetWalkPhase(0, 2, "It should reset the phases within bounds."));
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
  void testAllowMultipleFinalization() {
    manager.planExecutionFor(NonFixedJobParameters.INSTANCE);
    assertDoesNotThrow(() -> manager.planExecutionFor(NonFixedJobParameters.INSTANCE),
        "It should throw if execution planning is initiated manually multiple times.");
  }

  @Test
  void testIncrementalFinalization() {
    var transformation = manager
        .getRootTransformation();
    transformation.addRootDependency(RunPhase.withRun(() -> nextIndex++));
    manager.transform("");
    assertEquals(1, nextIndex, "It should run the phase");
    transformation.addRootDependency(RunPhase.withRun(() -> nextIndex++));
    manager.transform("");
    assertEquals(2, nextIndex, "It should run the previous and the new phase");
  }

  @Test
  void testAllowManualFinalization() {
    manager.planExecutionFor(NonFixedJobParameters.INSTANCE);
    assertDoesNotThrow(() -> manager.transform(""),
        "It should not throw after regular manual planning.");
  }

  @Test
  void testIncrementalResetState() {
    transformation.chainDependent(
        new RunPhase<>() {
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
        new RunPhase<>() {
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
        assertOrderWalkPhase(0, "The nested phase should run.")));
    manager.transform("");
    assertEquals(1, nextIndex, "The phase should run.");
  }

  @Test
  void testNestedMultipleTransformation() {
    transformation.chainDependent(new Transformation<>(
        assertOrderWalkPhase(0, "The first nested phase should run first.")));
    transformation.chainDependent(new Transformation<>(
        assertOrderWalkPhase(1, "The second chained nested phase should run second.")));
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testNestedMixedTransformation() {
    transformation.addDependency(
        new Transformation<>(
            assertOrderWalkPhase(2, "The phase in the dependent transformation should run third.")),
        new Transformation<>(
            assertOrderWalkPhase(0, "The phase in the dependency transformation should run first.")));
    transformation.chainConcurrentSibling(assertOrderWalkPhase(1, "The chained sibling phase should run second."));
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run.");
  }

  @Test
  void testSharedExternalTransformationDependency() {
    var a = transformation.addRootDependency(
        new Transformation<>(assertResetWalkPhase(1, 2, "The concurrent nested phases should run in the second level")));
    var b = transformation.addRootDependency(
        new Transformation<>(assertResetWalkPhase(1, 2, "The concurrent nested phases should run in the second level")));
    var sibling = assertOrderWalkPhase(0, "The shared dependency phase should run first.");
    transformation.addDependency(a, sibling);
    transformation.addDependency(b, sibling);
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run.");
  }

  @Test
  void testCrossTransformationDependency() {
    var aPhase = assertOrderWalkPhase(1, "The transformation with the dependent phase should run second.");
    var bPhase = assertOrderWalkPhase(0, "The transformation with the dependency phase should run first.");
    transformation.addRootDependency(new Transformation<>(aPhase));
    transformation.addRootDependency(new Transformation<>(bPhase));
    transformation.addDependency(aPhase, bPhase);
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testDeeplyNestedTransformation() {
    transformation.addRootDependency(new Transformation<>(new Transformation<>(new Transformation<>(
        assertOrderWalkPhase(0, "The nested phase should run.")))));
    manager.transform("");
    assertEquals(1, nextIndex, "The nested phase should run.");
  }

  @Test
  void testNoInitOnSecondRun() {
    transformation.chainDependency(new RunPhase<>() {
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

  @Test
  void testNotCachedParameters() {
    var man = new TransformationManager<>();
    man.addConcurrent(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });

    man.planExecutionFor(new FullyFixedJobParameters());
    man.planExecutionFor(new FullyFixedJobParameters());
    man.planExecutionFor(new FullyFixedJobParameters());

    assertEquals(3, nextIndex, "It should run the graph setup method during each planning run.");
  }

  @Test
  void testFullyCachedParameters() {
    var man = new TransformationManager<>();
    man.addConcurrent(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });

    man.planExecutionFor(new NonFixedJobParameters());
    man.planExecutionFor(new NonFixedJobParameters());
    man.planExecutionFor(new NonFixedJobParameters());

    assertEquals(1, nextIndex, "It should run the graph setup method only once.");
  }

  @Test
  void testBranchedCycleThrow() {
    var aPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var bPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var cPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var dPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });

    transformation.addDependency(aPhase, bPhase);
    transformation.addDependency(bPhase, aPhase);
    transformation.addDependency(cPhase, aPhase);
    transformation.addDependency(bPhase, dPhase);

    assertThrows(Error.class, () -> manager.transform(""),
        "It should throw if there is a reachable cycle.");
  }

  @Test
  void testHalfBranchedCycleThrow() {
    var aPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var bPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var cPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });

    transformation.addDependency(aPhase, bPhase);
    transformation.addDependency(bPhase, aPhase);
    transformation.addDependency(cPhase, aPhase);

    assertThrows(Error.class, () -> manager.transform(""),
        "It should throw if there is a reachable cycle even without reaching the end node.");
  }

  @Test
  void testUnreachableCycleSilent() {
    var aPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
      throw new IllegalStateException("It should not run unreachable nodes.");
    });
    var bPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
      throw new IllegalStateException("It should not run unreachable nodes.");
    });
    var dPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
      throw new IllegalStateException("It should not run unreachable nodes.");
    });

    transformation.addDependency(aPhase, bPhase);
    transformation.addDependency(bPhase, aPhase);
    transformation.addDependency(bPhase, dPhase);

    assertDoesNotThrow(() -> manager.transform(""),
        "It should not throw if there is an unreachable cycle and do nothing instead.");
  }

  @Test
  void testBareCycleSilent() {
    var aPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
      throw new IllegalStateException("It should not run unreachable nodes.");
    });
    var bPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
      throw new IllegalStateException("It should not run unreachable nodes.");
    });

    transformation.addDependency(aPhase, bPhase);
    transformation.addDependency(bPhase, aPhase);

    assertDoesNotThrow(() -> manager.transform(""),
        "It should not throw if there is an unreachable cycle and do nothing instead.");
  }

  @Test
  void testThreeCycleThrow() {
    var aPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var bPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var cPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });
    var dPhase = RunPhase.<NonFixedJobParameters>withRun(() -> {
    });

    transformation.addDependency(aPhase, bPhase);
    transformation.addDependency(bPhase, cPhase);
    transformation.addDependency(cPhase, aPhase);
    transformation.addDependency(dPhase, aPhase);

    assertThrows(Error.class, () -> manager.transform(""),
        "It should throw if there is a reachable cycle also with more nodes (3).");
  }

  @Test
  void testCustomRootTransformation() {
    var man = new TransformationManager<>(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });
    man.planExecutionFor(null);
    assertEquals(1, nextIndex, "It should run the custom transformation's graph setup");

  }

  @Test
  void testSetRootTransformation() {
    manager.setRootTransformation(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex += 3;
      }
    });

    manager.planExecutionFor(null);
    assertEquals(3, nextIndex, "It should run the new root transformation's graph setup");
  }
}
