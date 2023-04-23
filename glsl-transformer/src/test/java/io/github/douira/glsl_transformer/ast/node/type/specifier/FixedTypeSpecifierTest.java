package io.github.douira.glsl_transformer.ast.node.type.specifier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.typing.FixedType;
import io.github.douira.glsl_transformer.ast.typing.FixedType.ValueFormat;

public class FixedTypeSpecifierTest {
  @Test
  void testFixedTypeSpecifierEnumNames() {
    for (FixedType type : FixedType.values()) {
      if (type.valueFormat == null || type.valueFormat == ValueFormat.FLOATING_POINT) {
        continue;
      }
      assertTrue(type.name().startsWith(type.valueFormat == ValueFormat.SIGNED_INTEGER ? "I" : "U"),
          "The enum entry " + type.name() + " should start with the correct prefix.");
    }
  }
}
