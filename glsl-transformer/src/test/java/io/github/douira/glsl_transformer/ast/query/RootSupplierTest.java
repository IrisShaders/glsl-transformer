package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RootSupplierTest {
  // test that the pre-made root suppliers do the right things
  @Test
  public void testRootSuppliers() {
    var root1 = RootSupplier.EXACT_UNORDERED.get();
    assertNull(root1.externalDeclarationIndex);
    assertThrows(IllegalStateException.class, () -> root1.getPrefixIdentifierIndex());
    assertNotNull(root1.identifierIndex);
    assertNotNull(root1.nodeIndex);
    assertThrows(IllegalStateException.class, () -> root1.getPrefixExternalDeclarationIndex());

    var root2 = RootSupplier.PREFIX_UNORDERED.get();
    assertNull(root2.externalDeclarationIndex);
    assertNotNull(root2.getPrefixIdentifierIndex());
    assertNotNull(root2.identifierIndex);
    assertNotNull(root2.nodeIndex);
    assertThrows(IllegalStateException.class, () -> root2.getPrefixExternalDeclarationIndex());

    var root3 = RootSupplier.EXACT_UNORDERED_ED_EXACT.get();
    assertNotNull(root3.externalDeclarationIndex);
    assertThrows(IllegalStateException.class, () -> root3.getPrefixIdentifierIndex());
    assertNotNull(root3.identifierIndex);
    assertNotNull(root3.nodeIndex);
    assertThrows(IllegalStateException.class, () -> root3.getPrefixExternalDeclarationIndex());

    var root4 = RootSupplier.PREFIX_UNORDERED_ED_EXACT.get();
    assertNotNull(root4.externalDeclarationIndex);
    assertNotNull(root4.getPrefixIdentifierIndex());
    assertNotNull(root4.identifierIndex);
    assertNotNull(root4.nodeIndex);
    assertThrows(IllegalStateException.class, () -> root4.getPrefixExternalDeclarationIndex());

    var root5 = RootSupplier.PREFIX_UNORDERED_ED_PREFIX.get();
    assertNotNull(root5.externalDeclarationIndex);
    assertNotNull(root5.getPrefixIdentifierIndex());
    assertNotNull(root5.identifierIndex);
    assertNotNull(root5.nodeIndex);
    assertNotNull(root5.getPrefixExternalDeclarationIndex());
  }
}
