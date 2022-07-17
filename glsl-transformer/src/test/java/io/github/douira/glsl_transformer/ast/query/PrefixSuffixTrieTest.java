package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

public class PrefixSuffixTrieTest {
  static Object a = new Object(),
      b = new Object(),
      c = new Object(),
      d = new Object(),
      e = new Object(),
      f = new Object();

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
  void testSuffixQuery() {
    trie.put("aaa", a);
    trie.put("tat", b);
    trie.put("aac", c);
    trie.put("abc", d);
    trie.put("fdsfds", e);
    assertQuery(Set.of(c, d), trie.suffixQuery("c"));
    assertQuery(Set.of(a), trie.suffixQuery("aa"));
    assertQuery(Set.of(b), trie.suffixQuery("tat"));
    assertQuery(Set.of(b), trie.suffixQuery("at"));
    assertTrue(trie.suffixQuery("ta").count() == 0);
    assertTrue(trie.suffixQuery("aaaa").count() == 0);
    assertTrue(trie.suffixQuery("fsd").count() == 0);
  }
}
