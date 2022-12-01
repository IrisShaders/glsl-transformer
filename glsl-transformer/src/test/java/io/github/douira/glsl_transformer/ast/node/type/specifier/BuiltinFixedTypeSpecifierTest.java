package io.github.douira.glsl_transformer.ast.node.type.specifier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.type.specifier.BuiltinFixedTypeSpecifier.BuiltinType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.BuiltinFixedTypeSpecifier.BuiltinType.ValueFormat;

public class BuiltinFixedTypeSpecifierTest {
  @Test
  void testFixedTypeSpecifierEnumNames() {
    for (BuiltinType type : BuiltinType.values()) {
      if (type.valueFormat == null || type.valueFormat == ValueFormat.FLOATING_POINT) {
        continue;
      }
      assertTrue(type.name().startsWith(type.valueFormat == ValueFormat.SIGNED_INTEGER ? "I" : "U"), "The enum entry " + type.name() + " should start with the correct prefix.");
    }
  }
}
