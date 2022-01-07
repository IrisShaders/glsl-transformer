package io.github.douira.glsl_transformer.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
   * @param <T>   The type of the items
   * @param items The items to add to the set
   * @return The set with the given items
   */
  @SafeVarargs
  public static <T> Set<T> setOf(T... items) {
    return new HashSet<>(Arrays.asList(items));
  }

  /**
   * Creates a {@link java.util.HashSet} that contains the given item.
   * 
   * @param <T>  The type of the item
   * @param item The item to add to the set
   * @return The set with the given item
   */
  public static <T> Set<T> setOf(T item) {
    var set = new HashSet<T>();
    set.add(item);
    return set;
  }

  /**
   * Creates a {@link java.util.HashSet} that contains the given items.
   * 
   * @param <T>   The type of the items
   * @param itemA The first item to add to the set
   * @param itemB The second item to add to the set
   * @return The set with the given items
   */
  public static <T> Set<T> setOf(T itemA, T itemB) {
    var set = new HashSet<T>();
    set.add(itemA);
    set.add(itemB);
    return set;
  }

  /**
   * Creates an {@link java.util.ArrayList} that contains the given items in the
   * same order. This is likely cheaper for making a collection than using
   * {@link #setOf(Object...)}.
   * 
   * @param <T>   The type of the items
   * @param items The items to create the list out of
   * @return The list with the given items
   */
  @SafeVarargs
  public static <T> List<T> listOf(T... items) {
    return Arrays.asList(items);
  }
}
