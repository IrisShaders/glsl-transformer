package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.StringNode;
import io.github.douira.glsl_transformer.print.EmptyTerminalNode;
import io.github.douira.glsl_transformer.traversal.DynamicParseTreeWalker;
import io.github.douira.glsl_transformer.tree.*;

public class DynamicParseTreeWalkerTest {
  @Test
  void testWalk() {
    var tree = new ExtendedContext(null, 0) {
    };
    tree.addChild(new StringNode(""));
    tree.addChild(new StringNode(""));
    tree.addChild(new EmptyTerminalNode(new StringNode("")));
    tree.addChild(new EmptyTerminalNode(new StringNode("")));
    tree.addChild(new StringNode(""));
    tree.addChild(new ErrorNodeImpl(null));

    var listener = new ParseTreeListener() {
      int terminalVisits = 0;
      int ruleState = 0;
      int errorVisits = 0;

      @Override
      public void enterEveryRule(ParserRuleContext ctx) {
        assertEquals(0, ruleState++, "It should enter the whole rule once");
      }

      @Override
      public void exitEveryRule(ParserRuleContext ctx) {
        assertEquals(1, ruleState++, "It should exit the whole rule once");
      }

      @Override
      public void visitTerminal(TerminalNode node) {
        // add a node and replace one, this tests the fast-forwarding mechanism
        if (terminalVisits == 1) {
          // this node is never visited because it's before the node that replaces the
          // currently visited one
          tree.children.add(1, new StringNode(""));

          // the current node is wrapped in a placeholder so that the walker can find it
          tree.children.set(1, new EmptyTerminalNode((TreeMember) tree.children.get(1)));
        }

        terminalVisits++;
      }

      @Override
      public void visitErrorNode(ErrorNode node) {
        errorVisits++;
      }
    };

    DynamicParseTreeWalker.walkTree(listener, tree);

    assertEquals(4, tree.children.size(), "It should remove empty terminal nodes during walking");
    assertEquals(3, listener.terminalVisits, "It should visit each terminal but not the removed one");
    assertEquals(1, listener.errorVisits, "It should visit the error node");
    assertEquals(2, listener.ruleState, "It should enter and exit one time");
  }

  @Test
  void testNullChildList() {
    assertDoesNotThrow(() -> {
      var tree = new ExtendedContext(null, 0) {
      };

      DynamicParseTreeWalker.walkTree(new WalkPhase<>() {
      }, tree);
      assertNull(tree.children, "The child array should be null");
    }, "It should not throw when a parent has a null child array");
  }

  @Test
  void testEmptyChildList() {
    assertDoesNotThrow(() -> {
      var tree = new ExtendedContext(null, 0) {
      };
      tree.children = new ArrayList<>();

      DynamicParseTreeWalker.walkTree(new WalkPhase<>() {
      }, tree);
      assertEquals(0, tree.children.size(), "The child array should be not null but empty");
    }, "It should not throw when a parent has an empty child array");
  }
}
