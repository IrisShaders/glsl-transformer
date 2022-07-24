package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.ast.query.index.PrefixSuffixTrie;
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
    trie.put("aaa", Set.of(a));
    trie.put("baa", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertTrue(trie.containsKey("aaa"));
    assertTrue(trie.containsKey("baa"));
    assertTrue(trie.containsKey("aac"));
    assertTrue(trie.containsKey("abc"));
    assertTrue(trie.containsKey("fdsfds"));
    assertFalse(trie.containsKey("aa"));
    assertFalse(trie.containsKey("aab"));
    assertFalse(trie.containsKey("a"));
    assertFalse(trie.containsKey("cba"));
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
  void testSuffixQueryFlat() {
    trie.put("aaa", Set.of(a));
    trie.put("tat", Set.of(b));
    trie.put("aac", Set.of(c));
    trie.put("abc", Set.of(d));
    trie.put("fdsfds", Set.of(e));
    assertQuery(Set.of(c, d), trie.suffixQueryFlat("c"));
    assertQuery(Set.of(a), trie.suffixQueryFlat("aa"));
    assertQuery(Set.of(b), trie.suffixQueryFlat("tat"));
    assertQuery(Set.of(b), trie.suffixQueryFlat("at"));
    assertEquals(0, trie.suffixQueryFlat("ta").count());
    assertEquals(0, trie.suffixQueryFlat("aaaa").count());
    assertEquals(0, trie.suffixQueryFlat("fsd").count());
  }
}
