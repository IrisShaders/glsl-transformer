package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.PreciseQualifier;
import io.github.douira.glsl_transformer.ast.query.index.NodeIndex;

public class NodeIndexTest {
  NodeIndex<?> index = NodeIndex.withUnordered();
  ASTNode a = new Identifier("a");
  ASTNode b = new Identifier("b");
  ASTNode c = new Identifier("c");
  ASTNode d = new Identifier("d");

  @BeforeEach
  void setUp() {
    index.index.clear();
  }

  @Test
  void testAdd() {
    index.add(a);
    index.add(b);
    index.add(c);
    index.add(d);
    assertEquals(1, index.index.size());
    assertEquals(4, index.get(a).size());
    assertEquals(4, index.get(Identifier.class).size());
    assertEquals(0, index.get(LiteralExpression.class).size());
    assertEquals(0, index.get(Expression.class).size());
  }

  @Test
  void testGetOne() {
    index.add(a);
    index.add(b);
    index.add(c);
    assertTrue(Set.of(a, b, c).contains(index.getOne(Identifier.class)));
    assertTrue(Set.of(a, b, c).contains(index.getOne(a)));
    assertNull(index.getOne(LiteralExpression.class));
  }

  @Test
  void testGetStream() {
    index.add(a);
    index.add(b);
    index.add(c);
    index.add(d);
    assertEquals(4, index.getStream(Identifier.class).count());
    assertEquals(Set.of(a, b, c, d),
        index.getStream(Identifier.class).collect(Collectors.toSet()));
    assertEquals(0, index.getStream(LiteralExpression.class).count());
    assertEquals(0, index.getStream(Expression.class).count());
  }

  @Test
  void testHas() {
    index.add(a);
    index.add(b);
    index.add(c);
    index.add(d);
    assertTrue(index.has(a));
    assertTrue(index.has(b));
    assertTrue(index.has(c));
    assertTrue(index.has(d));
    assertTrue(index.has(Identifier.class));
    assertFalse(index.has(LiteralExpression.class));
    assertFalse(index.has(Expression.class));
    assertTrue(index.has(new Identifier("e")));
  }

  @Test
  void testHasExact() {
    index.add(a);
    index.add(b);
    index.add(c);
    index.add(d);
    assertTrue(index.hasExact(a));
    assertTrue(index.hasExact(b));
    assertTrue(index.hasExact(c));
    assertTrue(index.hasExact(d));
    assertFalse(index.hasExact(new Identifier("e")));
  }

  @Test
  void testRemove() {
    index.add(a);
    index.add(b);
    index.add(c);
    index.add(d);
    assertEquals(4, index.get(Identifier.class).size());
    index.remove(a);
    index.remove(b);
    index.remove(c);
    assertEquals(1, index.get(Identifier.class).size());
    assertTrue(index.has(a)); //class match
    assertTrue(index.has(d));
    assertFalse(index.hasExact(a));
    assertTrue(index.hasExact(d));
  }

  @Test
  void testMixedTypes() {
    var u = new PreciseQualifier();
    var v = new PreciseQualifier();
    var w = new PreciseQualifier();
    index.add(u);
    index.add(v);
    index.add(w);
    index.add(a);
    index.add(b);
    index.add(c);
    assertEquals(2, index.index.size());
    assertTrue(index.has(PreciseQualifier.class));
    assertTrue(index.has(Identifier.class));
    assertFalse(index.has(LiteralExpression.class));
  }
}
