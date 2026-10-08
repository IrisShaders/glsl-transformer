package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.query.index.*;

public class SuperclassNodeIndexTest {
  NodeIndex<?> index = SuperclassNodeIndex.withUnordered();
  ASTNode a = new PreciseQualifier();
  ASTNode b = new PreciseQualifier();
  ASTNode c = new PreciseQualifier();
  ASTNode d = new PreciseQualifier();
  ASTNode e = new TypeQualifier(Stream.empty());

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
    index.add(e);
    assertEquals(3, index.index.size());
    assertEquals(4, index.get(a).size());
    assertEquals(4, index.get(PreciseQualifier.class).size());
    assertEquals(4, index.get(TypeQualifierPart.class).size());
    assertEquals(1, index.get(TypeQualifier.class).size());
    assertEquals(0, index.get(ASTNode.class).size());
    assertEquals(0, index.get(InnerASTNode.class).size());
    assertEquals(0, index.get(ListASTNode.class).size());
  }
}
