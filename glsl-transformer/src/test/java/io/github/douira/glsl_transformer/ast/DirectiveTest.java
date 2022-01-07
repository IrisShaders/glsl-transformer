package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DirectiveTest {
  @Test
  void testGetPrinted() {
    assertEquals("#define foo\n", new Directive(Directive.Type.DEFINE, "foo").getPrinted());
    assertEquals("#define\n", new Directive(Directive.Type.DEFINE, "").getPrinted());
    assertEquals("#define\n", new Directive(Directive.Type.DEFINE, " ").getPrinted());
    assertEquals("#define\n", new Directive(Directive.Type.DEFINE, " \n").getPrinted());
    assertEquals("#define foo\\\nbar\n", new Directive(Directive.Type.DEFINE, " foo\nbar").getPrinted());
    assertEquals("#define foo\n", new Directive(Directive.Type.DEFINE, " foo\n").getPrinted());
    assertEquals("#define foo\n", new Directive(Directive.Type.DEFINE, " \n foo").getPrinted());
  }

  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new Directive(Directive.Type.DEFINE, null));
    assertThrows(IllegalArgumentException.class, () -> new Directive(null, ""));
  }
}
