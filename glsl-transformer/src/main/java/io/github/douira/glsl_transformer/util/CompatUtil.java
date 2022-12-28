package io.github.douira.glsl_transformer.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * This class contains utility methods that allow maintaining Java 8 API
 * compatability. (Even though the code is at Java 16 source compatability)
 */
public class CompatUtil {
  /**
   * Repeats the given string a number of times.
   * 
   * From https://stackoverflow.com/a/1235213
   * 
   * @param str The string to repeat
   * @param n   The number of times to repeat the string
   * @return The given string repeated n times
   */
  public static String repeat(String str, int n) {
    if (str == null) {
      return null;
    }
    if (str.length() == 0 || n <= 0) {
      return "";
    }
    if (n == 1) {
      return str;
    }

    var builder = new StringBuilder(str.length() * n);
    for (int i = 0; i < n; i++) {
      builder.append(str);
    }
    return builder.toString();
  }

  /**
   * Creates a {@link java.util.HashSet} that contains the given items. The heap
   * pollution warning is suppressed as it is in {@link java.util.Arrays}.
   * 
   * @param <V>   The type of the items
   * @param items The items to add to the set
   * @return The set with the given items
   */
  @SafeVarargs
  public static <V> Set<V> setOf(V... items) {
    return new HashSet<>(Arrays.asList(items));
  }

  /**
   * Creates a {@link java.util.HashSet} that contains the given item.
   * 
   * @param <V>  The type of the item
   * @param item The item to add to the set
   * @return The set with the given item
   */
  public static <V> Set<V> setOf(V item) {
    var set = new HashSet<V>();
    set.add(item);
    return set;
  }

  /**
   * Creates a {@link java.util.HashSet} that contains the given items.
   * 
   * @param <V>   The type of the items
   * @param itemA The first item to add to the set
   * @param itemB The second item to add to the set
   * @return The set with the given items
   */
  public static <V> Set<V> setOf(V itemA, V itemB) {
    var set = new HashSet<V>();
    set.add(itemA);
    set.add(itemB);
    return set;
  }

  /**
   * Creates an {@link java.util.ArrayList} that contains the given items in the
   * same order. This is likely cheaper for making a collection than using
   * {@link #setOf(Object...)}.
   * 
   * @param <V>   The type of the items
   * @param items The items to create the list out of
   * @return The list with the given items
   */
  @SafeVarargs
  public static <V> List<V> listOf(V... items) {
    return Arrays.asList(items);
  }

  /**
   * Returns a sequential ordered {@code Stream} produced by iterative
   * application of the given {@code next} function to an initial element,
   * conditioned on satisfying the given {@code hasNext} predicate. The
   * stream terminates as soon as the {@code hasNext} predicate returns false.
   *
   * <p>
   * {@code Stream.iterate} should produce the same sequence of elements as
   * produced by the corresponding for-loop:
   * 
   * <pre>{@code
   *     for (T index=seed; hasNext.test(index); index = next.apply(index)) {
   *         ...
   *     }
   * }</pre>
   *
   * <p>
   * The resulting sequence may be empty if the {@code hasNext} predicate
   * does not hold on the seed value. Otherwise the first element will be the
   * supplied {@code seed} value, the next element (if present) will be the
   * result of applying the {@code next} function to the {@code seed} value,
   * and so on iteratively until the {@code hasNext} predicate indicates that
   * the stream should terminate.
   *
   * <p>
   * The action of applying the {@code hasNext} predicate to an element
   * <a href=
   * "../concurrent/package-summary.html#MemoryVisibility"><i>happens-before</i></a>
   * the action of applying the {@code next} function to that element. The
   * action of applying the {@code next} function for one element
   * <i>happens-before</i> the action of applying the {@code hasNext}
   * predicate for subsequent elements. For any given element an action may
   * be performed in whatever thread the library chooses.
   *
   * @param <V>     the type of stream elements
   * @param seed    the initial element
   * @param hasNext a predicate to apply to elements to determine when the
   *                stream must terminate.
   * @param next    a function to be applied to the previous element to produce
   *                a new element
   * @return a new sequential {@code Stream}
   * @implNote The implementation was taken from the JDK 9 source code.
   */
  public static <V> Stream<V> iterateStream(V seed, Predicate<? super V> hasNext, UnaryOperator<V> next) {
    Objects.requireNonNull(next);
    Objects.requireNonNull(hasNext);
    Spliterator<V> spliterator = new Spliterators.AbstractSpliterator<>(Long.MAX_VALUE,
        Spliterator.ORDERED | Spliterator.IMMUTABLE) {
      V prev;
      boolean started, finished;

      @Override
      public boolean tryAdvance(Consumer<? super V> action) {
        Objects.requireNonNull(action);
        if (finished)
          return false;
        V t;
        if (started)
          t = next.apply(prev);
        else {
          t = seed;
          started = true;
        }
        if (!hasNext.test(t)) {
          prev = null;
          finished = true;
          return false;
        }
        action.accept(prev = t);
        return true;
      }

      @Override
      public void forEachRemaining(Consumer<? super V> action) {
        Objects.requireNonNull(action);
        if (finished)
          return;
        finished = true;
        V t = started ? next.apply(prev) : seed;
        prev = null;
        while (hasNext.test(t)) {
          action.accept(t);
          t = next.apply(t);
        }
      }
    };
    return StreamSupport.stream(spliterator, false);
  }
}
