package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.test_util.TestWithTrieObjects;

public class PrefixSuffixTrieTest extends TestWithTrieObjects {
  PrefixSuffixTrie<Object> trie;

  // setup before each test
  @BeforeEach
  void setup() {
    trie = new PrefixSuffixTrie<>();
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
  void testPrefixQueryFlat() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(a, c, d), trie.prefixQueryFlat("a"));
    assertQuery(Set.of(a, c), trie.prefixQueryFlat("aa"));
    assertQuery(Set.of(d), trie.prefixQueryFlat("ab"));
    assertTrue(trie.prefixQueryFlat("ac").count() == 0);
    assertTrue(trie.prefixQueryFlat("ds").count() == 0);
    assertTrue(trie.prefixQueryFlat("aaaa").count() == 0);
  }

  @Test
  void testSuffixQueryFlat() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(c, d), trie.suffixQueryFlat("c"));
    assertQuery(Set.of(a), trie.suffixQueryFlat("aa"));
    assertQuery(Set.of(b), trie.suffixQueryFlat("tat"));
    assertQuery(Set.of(b), trie.suffixQueryFlat("at"));
    assertTrue(trie.suffixQueryFlat("ta").count() == 0);
    assertTrue(trie.suffixQueryFlat("aaaa").count() == 0);
    assertTrue(trie.suffixQueryFlat("fsd").count() == 0);
  }
}
