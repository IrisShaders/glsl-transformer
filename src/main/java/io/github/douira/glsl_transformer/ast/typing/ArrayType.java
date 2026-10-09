package io.github.douira.glsl_transformer.ast.typing;

/**
 * An array type. Arrays of arrays are represented by nesting, where the
 * outermost array type has the size that is written first after a declared
 * name: {@code float a[2][3]} is an array of 2 arrays of 3 floats.
 *
 * @param element the type of the elements
 * @param size    the size of the array
 */
public record ArrayType(Type element, ArraySize size) implements Type {
  @Override
  public String getTypeName() {
    // the sizes are written in order from the outermost to the innermost array
    var sizes = new StringBuilder();
    Type current = this;
    while (current instanceof ArrayType array) {
      sizes.append('[').append(array.size).append(']');
      current = array.element;
    }
    return current.getTypeName() + sizes;
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
