package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

/**
 * A scope maps names to symbols. Each scope has a single namespace shared by
 * variables, functions and types, as GLSL requires. Names that are not found
 * in a scope are looked up in the parent scope.
 */
public final class Scope {
  private final Scope parent;
  private final Map<String, Symbol> symbols = new LinkedHashMap<>();

  /**
   * Creates a new scope.
   *
   * @param parent the enclosing scope, or null if there is none
   */
  public Scope(Scope parent) {
    this.parent = parent;
  }

  public Scope getParent() {
    return parent;
  }

  /**
   * Returns the symbols declared directly in this scope in declaration order.
   *
   * @return an unmodifiable view of the symbols
   */
  public Map<String, Symbol> getSymbols() {
    return Collections.unmodifiableMap(symbols);
  }

  /**
   * Looks up a name in this scope only.
   *
   * @param name the name to look up
   * @return the symbol, or null if the name is not declared in this scope
   */
  public Symbol lookupLocal(String name) {
    return symbols.get(name);
  }

  /**
   * Looks up a name in this scope and the enclosing scopes.
   *
   * @param name the name to look up
   * @return the symbol of the innermost declaration, or null if the name is not
   *         declared
   */
  public Symbol lookup(String name) {
    for (var scope = this; scope != null; scope = scope.parent) {
      var symbol = scope.symbols.get(name);
      if (symbol != null) {
        return symbol;
      }
    }
    return null;
  }

  /**
   * Declares a symbol in this scope. A name can only be declared once per
   * scope, with two exceptions: functions with the same name are merged into
   * one symbol with multiple overloads, and an array variable that was declared
   * without a size can be redeclared with a size.
   *
   * @param symbol the symbol to declare
   * @return false if the declaration is an invalid redeclaration, in which case
   *         the existing symbol is kept
   */
  public boolean declare(Symbol symbol) {
    var existing = symbols.get(symbol.name());
    if (existing instanceof FunctionSymbol existingFunction && symbol instanceof FunctionSymbol function) {
      return existingFunction.merge(function);
    }
    var resizesArray = existing instanceof VariableSymbol existingVariable
        && existingVariable.type() instanceof ArrayType existingArray && !existingArray.size().isKnown()
        && symbol instanceof VariableSymbol variable
        && Conversions.isSameType(existingArray, variable.type());
    if (existing != null && !resizesArray) {
      return false;
    }
    symbols.put(symbol.name(), symbol);
    return true;
  }

  /**
   * Sets a symbol regardless of what is declared with its name.
   */
  void set(Symbol symbol) {
    symbols.put(symbol.name(), symbol);
  }

  void remove(String name) {
    symbols.remove(name);
  }
}
