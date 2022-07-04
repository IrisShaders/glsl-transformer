package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.*;

import org.junit.jupiter.api.*;

public class PermutermTrieTest {
  static Object a = new Object(),
      b = new Object(),
      c = new Object(),
      d = new Object(),
      e = new Object(),
      f = new Object();

  PermutermTrie<Object> trie;

  // setup before each test
  @BeforeEach
  void setup() {
    trie = new PermutermTrie<>();
  }

  void assertQuery(Set<Object> expected, Stream<Object> result) {
    assertEquals(expected, result.collect(Collectors.toSet()));
  }

  @Test
  void testContainsKey() {
    trie.put("aaa", a);
    trie.put("baa", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    Assertions.assertTrue(trie.containsKey("aaa"));
    Assertions.assertTrue(trie.containsKey("baa"));
    Assertions.assertTrue(trie.containsKey("aac"));
    Assertions.assertTrue(trie.containsKey("abc"));
    Assertions.assertTrue(trie.containsKey("fdsfds"));
    Assertions.assertFalse(trie.containsKey("aa"));
    Assertions.assertFalse(trie.containsKey("aab"));
    Assertions.assertFalse(trie.containsKey("a"));
    Assertions.assertFalse(trie.containsKey("cba"));
  }

  @Test
  void testGet() {
    trie.put("aaa", a);
    trie.put("baa", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertEquals(a, trie.get("aaa"));
    assertEquals(b, trie.get("baa"));
    assertEquals(c, trie.get("aac"));
    assertEquals(d, trie.get("abc"));
    assertEquals(e, trie.get("fdsfds"));
    assertNull(trie.get("aa"));
    assertNull(trie.get("aab"));
    assertNull(trie.get("a"));
    assertNull(trie.get("cba"));
  }

  @Test
  void testRemove() {
    trie.put("aaa", a);
    trie.put("baa", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    trie.remove("baa");
    trie.remove("a");
    trie.remove("aac");
    trie.remove("abc");
    trie.remove("fdsfds");
    assertTrue(trie.containsKey("aaa"));
    assertFalse(trie.containsKey("baa"));
    assertFalse(trie.containsKey("aac"));
    assertFalse(trie.containsKey("abc"));
    assertFalse(trie.containsKey("fdsfds"));
  }

  @Test
  void testInfixQuery() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(a, b, c, d), trie.infixQuery("a"));
    assertQuery(Set.of(a, c), trie.infixQuery("aa"));
    assertQuery(Set.of(c), trie.infixQuery("ac"));
    assertTrue(trie.infixQuery("ca").count() == 0);
    assertTrue(trie.infixQuery("ba").count() == 0);
    assertTrue(trie.infixQuery("cba").count() == 0);
  }

  @Test
  void testPrefixQuery() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(a, c, d), trie.prefixQuery("a"));
    assertQuery(Set.of(a, c), trie.prefixQuery("aa"));
    assertQuery(Set.of(d), trie.prefixQuery("ab"));
    assertTrue(trie.prefixQuery("ac").count() == 0);
    assertTrue(trie.prefixQuery("ds").count() == 0);
    assertTrue(trie.prefixQuery("aaaa").count() == 0);
  }

  @Test
  void testSuffixPrefixQuery() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("aabababbba", e);
    assertQuery(Set.of(a, e), trie.suffixPrefixQuery("a", "a"));
    assertQuery(Set.of(a, e), trie.suffixPrefixQuery("aa", "a"));
    assertQuery(Set.of(b), trie.suffixPrefixQuery("t", "t"));
    assertQuery(Set.of(e), trie.suffixPrefixQuery("aa", "bbba"));
    assertQuery(Set.of(a, b, c, d, e), trie.suffixPrefixQuery("", ""));
    assertQuery(Set.of(a, c, d, e), trie.suffixPrefixQuery("a", ""));
    assertQuery(Set.of(a, e), trie.suffixPrefixQuery("", "a"));
    assertQuery(Set.of(b), trie.suffixPrefixQuery("", "tat"));
    assertTrue(trie.suffixPrefixQuery("aa", "aa").count() == 0);
  }

  @Test
  void testSuffixQuery() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(c,d), trie.suffixQuery("c"));
    assertQuery(Set.of(a), trie.suffixQuery("aa"));
    assertQuery(Set.of(b), trie.suffixQuery("tat"));
    assertTrue(trie.suffixQuery("ta").count() == 0);
    assertTrue(trie.suffixQuery("aaaa").count() == 0);
    assertTrue(trie.suffixQuery("fsd").count() == 0);
  }
}
