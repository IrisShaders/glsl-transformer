package io.github.douira.glsl_transformer.ast.typing;

import java.util.Map;

public class StructType extends ValueType {
  public Map<String, Type> fields;
}
