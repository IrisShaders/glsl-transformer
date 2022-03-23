package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

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
  private Collection<PartialParseTreeListener> listeners;
  private Iterator<PartialParseTreeListener> listenerIterator;

  /**
   * Creates a new proxy without an empty list of listeners. Add listeners before
   * walking the tree.
   */
  public ProxyParseTreeListener() {
    // Setting the listener to null automatically instantiates a new list.
    this(null);
  }

  /**
   * Creates a new proxy with the given list of listeners.
   * 
   * @param listeners A list of listeners to receive events.
   */
  public ProxyParseTreeListener(Collection<PartialParseTreeListener> listeners) {
    setListeners(listeners);
  }

  /**
   * Adds the given listener to the list of event notification recipients.
   * 
   * @param listener A listener to begin receiving events.
   */
  public void add(PartialParseTreeListener listener) {
    getListeners().add(listener);
  }

  /**
   * Removes the given listener to the list of event notification recipients.
   * 
   * @param listener A listener to stop receiving events.
   * @return false The listener was not registered to receive events.
   */
  public boolean remove(PartialParseTreeListener listener) {
    return getListeners().remove(listener);
  }

  /**
   * Returns the list of listeners.
   * 
   * @return The list of listeners to receive tree walking events.
   */
  private Collection<PartialParseTreeListener> getListeners() {
    return this.listeners;
  }

  /**
   * Changes the list of listeners to receive events. If the given list of
   * listeners is null, an empty list will be created.
   * 
   * @param listeners A list of listeners to receive tree walking events.
   */
  public void setListeners(Collection<PartialParseTreeListener> listeners) {
    this.listeners = Optional.ofNullable(listeners).orElseGet(ArrayList<PartialParseTreeListener>::new);
  }

  /**
   * Checks if the list of listeners is empty.
   * 
   * @return {@code true} if the list of listeners is empty
   */
  public boolean isEmpty() {
    return this.listeners.isEmpty();
  }

  /**
   * Removes the listener last processed during iteration from the list of
   * listeners. This is used by the execution planner to remove nodes that are
   * finished with walking.
   */
  public void removeCurrentListener() {
    listenerIterator.remove();
  }

  private void iterateListeners(Consumer<PartialParseTreeListener> consumer) {
    listenerIterator = getListeners().iterator();
    while (listenerIterator.hasNext()) {
      consumer.accept(listenerIterator.next());
    }
    listenerIterator = null;
  }

  @Override
  public boolean isDeepEnough(ExtendedContext node) {
    for (var listener : getListeners()) {
      if (!listener.isDeepEnough(node)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isFinished() {
    if (isEmpty()) {
      return true;
    }
    for (var listener : getListeners()) {
      if (!listener.isFinished()) {
        return false;
      }
    }
    return true;
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
