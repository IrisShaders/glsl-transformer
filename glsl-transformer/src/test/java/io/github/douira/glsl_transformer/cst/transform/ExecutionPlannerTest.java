package io.github.douira.glsl_transformer.cst.transform;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.job_parameter.*;
import io.github.douira.glsl_transformer.test_util.*;

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
    manager.addConcurrent(incrementRunPhase());
    manager.transform("");
    assertEquals(1, nextIndex, "It should run the added phase");
  }

  @Test
  void testAddConcurrentMultiple() {
    manager.addConcurrent(incrementRunPhase());
    manager.addConcurrent(incrementRunPhase());
    manager.addConcurrent(incrementRunPhase());
    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testAddConcurrentMultipleSingleLevel() {
    for (int i = 0; i < 3; i++) {
      manager.addConcurrent(
          assertResetPhase(0, 2, "It should reset the phases within bounds."));
    }
    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testGetRootTransformation() {
    manager
        .getRootTransformation()
        .addRootDependency(incrementRunPhase());
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
    transformation.addRootDependency(incrementRunPhase());
    manager.transform("");
    assertEquals(1, nextIndex, "It should run the phase");
    transformation.addRootDependency(incrementRunPhase());
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
        assertOrderPhase(0, "The nested phase should run.")));
    manager.transform("");
    assertEquals(1, nextIndex, "The phase should run.");
  }

  @Test
  void testNestedMultipleTransformation() {
    transformation.chainDependent(new Transformation<>(
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
        new Transformation<>(assertResetPhase(1, 2, "The concurrent nested phases should run in the second level")));
    var b = transformation.addRootDependency(
        new Transformation<>(assertResetPhase(1, 2, "The concurrent nested phases should run in the second level")));
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
    var t = new CSTTransformer<>();
    t.addConcurrent(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });

    t.planExecutionFor(new FullyFixedJobParameters());
    t.planExecutionFor(new FullyFixedJobParameters());
    t.planExecutionFor(new FullyFixedJobParameters());

    assertEquals(3, nextIndex, "It should run the graph setup method during each planning run.");
  }

  @Test
  void testFullyCachedParameters() {
    var t = new CSTTransformer<>();
    t.addConcurrent(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });

    t.planExecutionFor(new NonFixedJobParameters());
    t.planExecutionFor(new NonFixedJobParameters());
    t.planExecutionFor(new NonFixedJobParameters());

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

    assertThrows(AssertionError.class, () -> manager.transform(""),
        "It should throw if there is an unreachable node (like one in a cycle).");
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

    assertThrows(AssertionError.class, () -> manager.transform(""),
        "It should throw if there is an unreachable node (like one in a cycle).");
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
    var t = new CSTTransformer<>(new Transformation<>() {
      @Override
      protected void setupGraph() {
        nextIndex++;
      }
    });
    t.planExecutionFor(null);
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

  @Test
  void testAdjacentSerialTransformations() {
    transformation.chainDependent(new Transformation<NonFixedJobParameters>(incrementRunPhase()));
    transformation.chainDependent(new Transformation<NonFixedJobParameters>(incrementRunPhase()));
    manager.transform("");
    assertEquals(2, nextIndex, "It should run two phases wrapped in transformations added as serial dependencies.");
  }

  /*
   * The following tests make sure that if a phase is added multiple times to the
   * same transformation manager even if it's wrapped inside another
   * transformation it's only run once and doesn't cause issues for execution
   * planning. If the phase has itself as a dependency, it's expected to cause an
   * exception during execution planning.
   */

  private void setupMultipleTimes(int count,
      BiConsumer<TransformationPhase<JobParameters>, Transformation<JobParameters>> setupOne) {
    var phase = incrementRunPhase();
    var localManager = new CSTTransformer<>();
    var localTransformation = localManager.getRootTransformation();
    for (var j = 0; j < count; j++) {
      setupOne.accept(phase, localTransformation);
    }
    nextIndex = 0;
    localManager.transform("");
  }

  private void assertMultipleRunNTimes(int expectedRuns,
      BiConsumer<TransformationPhase<JobParameters>, Transformation<JobParameters>> setupOne) {
    for (var i = 2; i <= 2; i++) {
      setupMultipleTimes(i, setupOne);
      assertEquals(expectedRuns, nextIndex,
          "It should run the phase " + expectedRuns + " time(s) when added " + i + " time(s).");
    }
  }

  private void assertMultipleRunOnce(
      BiConsumer<TransformationPhase<JobParameters>, Transformation<JobParameters>> setupOne) {
    assertMultipleRunNTimes(1, setupOne);
  }

  @Test
  void testRepeatedParallelWrapped() {
    // type: parallel wrapped
    assertMultipleRunOnce((phase, t) -> t.addEndDependent(new Transformation<>(phase)));
    assertRun(
        "It should run a single phase wrapped in two unique transformations added twice as serial dependencies just once.");
  }

  @Test
  void testRepeatedParallelBare() {
    // type: parallel bare
    assertMultipleRunOnce((phase, t) -> t.addEndDependent(phase));
    assertRun("It should run a single phase added twice as serial dependencies just once.");
  }

  @Test
  void testRepeatedSerialWrapped() {
    // type: serial wrapped
    assertThrows(AssertionError.class, () -> {
      setupMultipleTimes(2, (phase, t) -> t.chainDependent(new Transformation<>(phase)));
    }, "It should throw during execution planning if a phase is added as a dependency on itself.");
  }

  @Test
  void testRepeatedSerialBare() {
    // type: serial bare
    assertThrows(AssertionError.class,
        () -> assertMultipleRunOnce((phase, t) -> t.chainDependency(phase)),
        "It should throw during execution planning if a phase is unreachable because it depends on itself.");
  }
}
