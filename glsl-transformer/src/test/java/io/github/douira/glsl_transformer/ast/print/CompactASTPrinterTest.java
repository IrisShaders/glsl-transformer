package io.github.douira.glsl_transformer.ast.print;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class CompactASTPrinterTest extends TestWithSingleASTTransformer {
  @Test
  void testCompactPrinting() {
    assertReprint(PrintType.COMPACT, GLSLParser::translationUnit,
        "void main() { gl_FragColor = vec4(1.0); } ",
        "void main() {\n"
            + "  gl_FragColor = vec4(1.0);\n"
            + "}");
  }
}
