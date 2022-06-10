package io.github.douira.glsl_transformer.traversal;

import java.util.*;
import java.util.function.Consumer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * This class was taken from <a href=
 * "https://github.com/antlr/antlr4/issues/841#issuecomment-84450146">here</a>
 * and was added to the public domain by the author DaveJarvis.
 * 
 * Instances of this class allow multiple listeners to receive events while
 * walking the parse tree. For example:
 *
 * <pre>
 * ProxyParseTreeListener proxy = new ProxyParseTreeListener();
 * ParseTreeListener listener1 = ... ;
 * ParseTreeListener listener2 = ... ;
 * proxy.add(listener1);
 * proxy.add(listener2);
 * ParseTreeWalker.DEFAULT.walk(proxy, ctx);
 * </pre>
 */
public class ProxyParseTreeListener implements PartialParseTreeListener {
  private Collection<PartialParseTreeListener> listeners = new ArrayList<>();
  private Collection<PartialParseTreeListener> stoppableListeners = new ArrayList<>();
  private Iterator<PartialParseTreeListener> listenerIterator;
  private PartialParseTreeListener currentListener;

  /**
   * Adds the given listener to the list of event notification recipients.
   * 
   * @param listener A listener to begin receiving events.
   */
  public void add(PartialParseTreeListener listener) {
    listeners.add(listener);
    if (listener.canStop()) {
      stoppableListeners.add(listener);
    }
  }

  /**
   * Returns if this listener even needs a tree walk. This is false if there are
   * no listeners
   * 
   * @return {@code true} if there are listeners
   */
  public boolean needsWalk() {
    return !listeners.isEmpty();
  }

  /**
   * Removes the listener last processed during iteration from the list of
   * listeners. This is used by the execution planner to remove nodes that are
   * finished with walking.
   */
  public void removeCurrentListener() {
    listenerIterator.remove();
    stoppableListeners.remove(currentListener);
  }

  private void iterateListeners(Consumer<PartialParseTreeListener> consumer) {
    listenerIterator = listeners.iterator();
    while (listenerIterator.hasNext()) {
      currentListener = listenerIterator.next();
      consumer.accept(currentListener);
    }
    listenerIterator = null;
  }

  private boolean hasNonStoppingListeners() {
    return stoppableListeners.size() < listeners.size();
  }

  @Override
  public boolean isDeepEnough(ExtendedContext node, int depth) {
    if (hasNonStoppingListeners()) {
      return false;
    }
    for (var listener : stoppableListeners) {
      if (!listener.isDeepEnough(node, depth)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isFinished(int depth) {
    if (hasNonStoppingListeners()) {
      return false;
    }
    var finished = true;
    var stoppableListenerIterator = stoppableListeners.iterator();
    while (stoppableListenerIterator.hasNext()) {
      var listener = stoppableListenerIterator.next();
      if (listener.isFinished(depth)) {
        listeners.remove(listener);
        stoppableListenerIterator.remove();
      } else {
        finished = false;
      }
    }
    return finished;
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    iterateListeners(listener -> {
      listener.enterEveryRule(ctx);
      ctx.enterRule(listener);
    });
  }

  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    iterateListeners(listener -> {
      ctx.exitRule(listener);
      listener.exitEveryRule(ctx);
    });
  }

  @Override
  public void visitErrorNode(ErrorNode node) {
    iterateListeners(listener -> {
      listener.visitErrorNode(node);
    });
  }

  @Override
  public void visitTerminal(TerminalNode node) {
    iterateListeners(listener -> {
      listener.visitTerminal(node);
    });
  }
}
