package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.ast.query.index.PermutermTrie;
import io.github.douira.glsl_transformer.test_util.TestWithTrieObjects;

public class PermutermTrieTest extends TestWithTrieObjects {
  PermutermTrie<Object> trie;

  // setup before each test
  @BeforeEach
  void setup() {
    trie = new PermutermTrie<>();
  }

  @Test
  void testContainsKey() {
    trie.put("aaa", Set.of(a));
    trie.put("baa", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
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
    trie.put("aaa", Set.of(a));
    trie.put("baa", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertEquals(a, trie.get("aaa").iterator().next());
    assertEquals(b, trie.get("baa").iterator().next());
    assertEquals(c, trie.get("aac").iterator().next());
    assertEquals(d, trie.get("abc").iterator().next());
    assertEquals(e, trie.get("fdsfds").iterator().next());
    assertNull(trie.get("aa"));
    assertNull(trie.get("aab"));
    assertNull(trie.get("a"));
    assertNull(trie.get("cba"));
  }

  @Test
  void testRemove() {
    trie.put("aaa", Set.of(a));
    trie.put("baa", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
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
  void testInfixQueryFlat() {
    trie.put("aaa", Set.of(a));
    trie.put("tat", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertQuery(Set.of(a, b, c, d), trie.infixQueryFlat("a"));
    assertQuery(Set.of(a, c), trie.infixQueryFlat("aa"));
    assertQuery(Set.of(c), trie.infixQueryFlat("ac"));
    assertEquals(0, trie.infixQueryFlat("ca").count());
    assertEquals(0, trie.infixQueryFlat("ba").count());
    assertEquals(0, trie.infixQueryFlat("cba").count());
  }

  @Test
  void testPrefixQueryFlat() {
    trie.put("aaa", Set.of(a));
    trie.put("tat", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertQuery(Set.of(a, c, d), trie.prefixQueryFlat("a"));
    assertQuery(Set.of(a, c), trie.prefixQueryFlat("aa"));
    assertQuery(Set.of(d), trie.prefixQueryFlat("ab"));
    assertEquals(0, trie.prefixQueryFlat("ac").count());
    assertEquals(0, trie.prefixQueryFlat("ds").count());
    assertEquals(0, trie.prefixQueryFlat("aaaa").count());
  }

  @Test
  void testSuffixPrefixQueryFlat() {
    trie.put("aaa", Set.of(a));
    trie.put("tat", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("aabababbba", Set.of(e));
    assertQuery(Set.of(a, e), trie.invertedInfixQueryFlat("a", "a"));
    assertQuery(Set.of(a, e), trie.invertedInfixQueryFlat("aa", "a"));
    assertQuery(Set.of(b), trie.invertedInfixQueryFlat("t", "t"));
    assertQuery(Set.of(e), trie.invertedInfixQueryFlat("aa", "bbba"));
    assertQuery(Set.of(a, b, c, d, e), trie.invertedInfixQueryFlat("", ""));
    assertQuery(Set.of(a, c, d, e), trie.invertedInfixQueryFlat("a", ""));
    assertQuery(Set.of(a, e), trie.invertedInfixQueryFlat("", "a"));
    assertQuery(Set.of(b), trie.invertedInfixQueryFlat("", "tat"));
    assertEquals(0, trie.invertedInfixQueryFlat("aa", "aa").count());
  }

  @Test
  void testSuffixQueryFlat() {
    trie.put("aaa", Set.of(a));
    trie.put("tat", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertQuery(Set.of(c, d), trie.suffixQueryFlat("c"));
    assertQuery(Set.of(a), trie.suffixQueryFlat("aa"));
    assertQuery(Set.of(b), trie.suffixQueryFlat("tat"));
    assertEquals(0, trie.suffixQueryFlat("ta").count());
    assertEquals(0, trie.suffixQueryFlat("aaaa").count());
    assertEquals(0, trie.suffixQueryFlat("fsd").count());
  }
}
