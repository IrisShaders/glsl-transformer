package io.github.douira.glsl_transformer.ast.print;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class SimpleASTPrinterTest extends TestWithSingleASTTransformer {
  @Test
  void testCompactPrinting() {
    assertReprint(PrintType.SIMPLE, GLSLParser::translationUnit,
        "void main() {\ngl_FragColor = vec4(1.0f);\n}\n",
        "void main() {\n"
            + "  gl_FragColor = vec4(1.0f);\n"
            + "}");
  }
}
