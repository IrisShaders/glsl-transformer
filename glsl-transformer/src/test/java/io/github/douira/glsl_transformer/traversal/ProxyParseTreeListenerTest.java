package io.github.douira.glsl_transformer.traversal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;
import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ProxyParseTreeListenerTest {
  class TreeListener implements PartialParseTreeListener {
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
    }

    @Override
    public void visitTerminal(TerminalNode node) {
    }
  }

  int nextIndex;
  ProxyParseTreeListener proxyListener;

  @BeforeEach
  void setup() {
    proxyListener = new ProxyParseTreeListener();
    nextIndex = 0;
  }

  @Test
  void testAdd() {
    var indexes = new HashSet<Integer>();
    for (var i = 0; i < 10; i++) {
      proxyListener.add(new TreeListener() {
        @Override
        public void visitTerminal(TerminalNode node) {
          indexes.add(nextIndex++);
        }
      });
    }
    proxyListener.visitTerminal(null);
    assertEquals(10, indexes.size(), "It should call each listener once");
  }

  class CanStopListener extends TreeListener {
    @Override
    public boolean canStop() {
      return true;
    }
  }

  class DeepEnoughListener extends CanStopListener {
    @Override
    public boolean isDeepEnough(ExtendedContext node) {
      return true;
    }
  }

  class NotDeepEnoughListener extends CanStopListener {
    @Override
    public boolean isDeepEnough(ExtendedContext node) {
      return false;
    }
  }

  @Test
  void testIsDeepEnough() {
    proxyListener.add(new DeepEnoughListener());
    proxyListener.add(new DeepEnoughListener());
    assertTrue(proxyListener.isDeepEnough(null), "It should signal deep enough if all listeners are deep enough");
    proxyListener.add(new NotDeepEnoughListener());
    assertFalse(proxyListener.isDeepEnough(null),
        "It should signal not deep enough if any listener is not deep enough");
    proxyListener.add(new TreeListener());
    assertFalse(proxyListener.isDeepEnough(null),
        "It should signal not deep enough also when an additional non stoppable listener is added");
  }

  @Test
  void testIsDeepEnoughNonStopping() {
    proxyListener.add(new TreeListener());
    proxyListener.add(new TreeListener());
    assertFalse(proxyListener.isDeepEnough(null),
        "It should signal not deep enough when listeners are non-stopping");
    proxyListener.add(new DeepEnoughListener());
    assertFalse(proxyListener.isDeepEnough(null),
        "It should not signal deep enough when a stoppable listener with the deep enough signal is added");
  }

  class FinishedListener extends CanStopListener {
    @Override
    public boolean isFinished() {
      return true;
    }
  }

  class NotFinishedListener extends CanStopListener {
    @Override
    public boolean isFinished() {
      return false;
    }
  }

  @Test
  void testIsFinished() {
    proxyListener.add(new FinishedListener());
    proxyListener.add(new FinishedListener());
    assertTrue(proxyListener.isFinished(), "It should signal finished if all listeners are finished");
    proxyListener.add(new NotFinishedListener());
    assertFalse(proxyListener.isFinished(),
        "It should signal not finished if any listener is not finished");
    proxyListener.add(new TreeListener());
    assertFalse(proxyListener.isFinished(),
        "It should signal not finished also when an additional non stoppable listener is added");
  }

  @Test
  void testIsFinishedNonStopping() {
    proxyListener.add(new TreeListener());
    proxyListener.add(new TreeListener());
    assertFalse(proxyListener.isFinished(), "It should not signal finished when listeners are non-stopping");
    proxyListener.add(new FinishedListener());
    assertFalse(proxyListener.isFinished(),
        "It should not signal finished when a stoppable listener with the finished signal is added");
  }

  @Test
  void testNeedsWalk() {
    assertFalse(proxyListener.needsWalk(), "The empty listener doesn't need to walk");
    proxyListener.add(new TreeListener());
    assertTrue(proxyListener.needsWalk(), "The listener with one listener does need to walk");
    proxyListener.add(new TreeListener());
    assertTrue(proxyListener.needsWalk(), "It also needs to walk with more listeners");
  }

  @Test
  void testRemoveCurrentListener() {
    proxyListener.add(new TreeListener() {
      @Override
      public void visitTerminal(TerminalNode node) {
        assertEquals(0, nextIndex++, "It should call the listener only once");
        proxyListener.removeCurrentListener();
      }
    });

    proxyListener.visitTerminal(null);
    proxyListener.visitTerminal(null);
    assertEquals(1, nextIndex, "It should call the listener once");
  }

  class ThrowOnAllVisits extends TreeListener {
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
      throw new RuntimeException("Should not be called");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
      throw new RuntimeException("Should not be called");
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
      throw new RuntimeException("Should not be called");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
      throw new RuntimeException("Should not be called");
    }
  }

  @Test
  void testEnterEveryRule() {
    proxyListener.add(new ThrowOnAllVisits() {
      @Override
      public void enterEveryRule(ParserRuleContext node) {
        assertEquals(0, nextIndex++, "It should call the right listener method");
      }
    });
    proxyListener.enterEveryRule(new ParserRuleContext());
    assertEquals(1, nextIndex, "It should call the listener's correct method");
  }

  @Test
  void testExitEveryRule() {
    proxyListener.add(new ThrowOnAllVisits() {
      @Override
      public void exitEveryRule(ParserRuleContext node) {
        assertEquals(0, nextIndex++, "It should call the right listener method");
      }
    });
    proxyListener.exitEveryRule(new ParserRuleContext());
    assertEquals(1, nextIndex, "It should call the listener's correct method");
  }

  @Test
  void testVisitErrorNode() {
    proxyListener.add(new ThrowOnAllVisits() {
      @Override
      public void visitErrorNode(ErrorNode node) {
        assertEquals(0, nextIndex++, "It should call the right listener method");
      }
    });
    proxyListener.visitErrorNode(null);
    assertEquals(1, nextIndex, "It should call the listener's correct method");
  }

  @Test
  void testVisitTerminal() {
    proxyListener.add(new ThrowOnAllVisits() {
      @Override
      public void visitTerminal(TerminalNode node) {
        assertEquals(0, nextIndex++, "It should call the right listener method");
      }
    });
    proxyListener.visitTerminal(null);
    assertEquals(1, nextIndex, "It should call the listener's correct method");
  }
}
