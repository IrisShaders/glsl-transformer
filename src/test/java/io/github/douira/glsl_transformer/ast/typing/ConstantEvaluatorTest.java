package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.TypingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ConstantEvaluatorTest {
  private static final String DECLARATIONS = """
      #version 460
      #extension GL_EXT_shader_explicit_arithmetic_types : enable
      const int two = 2;
      const uint three = 3u;
      const bool yes = true;
      const float half = 0.5;
      const ivec2 pair = ivec2(1, 2);
      const int values[3] = int[3](1, 2, 3);
      const int fromFloat = int(half);
      const float fromInt = 2;
      const int listed = { 1 };
      struct S { int a; };
      const S structure = S(1);
      int runtime = 1;
      uniform int uni;
      float sized[4];
      float unsized[];
      float unknownSize[fromFloat];
      vec3 vector;
      mat2x4 matrix;
      int user(int x) { return x; }
      """;

  private static String evaluate(String expression) {
    var tree = parse(DECLARATIONS + "\nvoid typingTestMain() {\n const int local = two + 1;\n int variable;\n("
        + expression + ");\n}\n");
    var environment = TypeEnvironment.of(tree);
    var context = new AnalysisContext(
        tree, environment, new Scope(BuiltinRegistry.getScope(environment)), false, false);
    TypeAnalyzer.analyze(context, tree);
    var value = context.constants.evaluate(lastExpression(tree));
    return value == null ? "not constant"
        : !value.isKnown() ? "unknown"
            : value.type().getTypeName() + " " + (value.isUnsigned()
                ? Long.toUnsignedString(value.value())
                : Long.toString(value.value()));
  }

  private static void assertEvaluations(String expectations) {
    var checks = new ArrayList<Executable>();
    for (var line : expectations.split("\n")) {
      var parts = line.split(" => ", 2);
      var actual = evaluate(parts[0]);
      checks.add(() -> assertEquals(parts[1].trim(), actual, parts[0]));
    }
    assertAll(checks);
  }

  @Test
  void testLiteralsAndReferences() {
    assertEvaluations("""
        1 => int 1
        0x10 => int 16
        010 => int 8
        7u => uint 7
        4000000000u => uint 4000000000
        true => bool 1
        false => bool 0
        5s => int16_t 5
        5ul => uint64_t 5
        1.0 => unknown
        1.0lf => unknown
        two => int 2
        three => uint 3
        yes => bool 1
        local => int 3
        half => unknown
        pair => unknown
        values => unknown
        fromFloat => unknown
        fromInt => unknown
        listed => unknown
        structure => unknown
        gl_MaxDrawBuffers => unknown
        runtime => not constant
        uni => not constant
        variable => not constant
        missing => unknown
        (two) => int 2
        ((7)) => int 7""");
  }

  @Test
  void testUnaryOperators() {
    assertEvaluations("""
        +two => int 2
        -two => int -2
        -three => uint 4294967293
        ~two => int -3
        ~three => uint 4294967292
        ~0u => uint 4294967295
        !yes => bool 0
        !false => bool 1
        -half => unknown
        -runtime => not constant
        !missing => unknown
        - -two => int 2
        two++ => not constant
        --variable => not constant
        variable-- => not constant
        ++variable => not constant""");
  }

  @Test
  void testArithmetic() {
    assertEvaluations("""
        1 + 2 => int 3
        two * 3 => int 6
        7 - 10 => int -3
        7 / 2 => int 3
        -7 / 2 => int -3
        7 % 3 => int 1
        -7 % 3 => int -1
        2147483647 + 1 => int -2147483648
        7u - 10u => uint 4294967293
        7u / 2u => uint 3
        4000000000u / 2u => uint 2000000000
        4000000000u % 7u => uint 3
        two + three => uint 5
        -1 + three => uint 2
        -1 / three => uint 1431655765
        1 / 0 => unknown
        1 % 0 => unknown
        1u / 0u => unknown
        1 + 1.0 => unknown
        half * 2 => unknown
        two + runtime => not constant
        runtime + two => not constant
        runtime + runtime => not constant
        two + pair.x => unknown
        1s + 2s => int16_t 3
        32767s + 1s => int16_t -32768
        5l * 4000000000l => int64_t 20000000000
        1l + two => int64_t 3
        18446744073709551615ul / 2ul => uint64_t 9223372036854775807
        18446744073709551615ul % 10ul => uint64_t 5""");
  }

  @Test
  void testBitwiseAndShifts() {
    assertEvaluations("""
        6 & 3 => int 2
        6 | 3 => int 7
        6 ^ 3 => int 5
        6u & 3u => uint 2
        -1 & 255 => int 255
        -1 | three => uint 4294967295
        1 << 4 => int 16
        1 << 31 => int -2147483648
        1u << 31 => uint 2147483648
        -16 >> 2 => int -4
        4294967280u >> 2 => uint 1073741820
        256 >> three => int 32
        1u << two => uint 4
        1 << runtime => not constant
        18446744073709551615ul >> 60 => uint64_t 15""");
  }

  @Test
  void testComparisonsAndLogic() {
    assertEvaluations("""
        1 < 2 => bool 1
        2 < 1 => bool 0
        2 <= 2 => bool 1
        3 <= 2 => bool 0
        2 > 1 => bool 1
        1 > 2 => bool 0
        2 >= 2 => bool 1
        1 >= 2 => bool 0
        2 == 2 => bool 1
        2 == 3 => bool 0
        2 != 3 => bool 1
        2 != 2 => bool 0
        -1 < 1 => bool 1
        -1 < three => bool 0
        4000000000u > 1u => bool 1
        yes == true => bool 1
        yes != true => bool 0
        true && false => bool 0
        true && yes => bool 1
        false || yes => bool 1
        false || false => bool 0
        true ^^ yes => bool 0
        true ^^ false => bool 1
        1 < half => unknown
        two < runtime => not constant
        runtime > 0 && yes => not constant""");
  }

  @Test
  void testConditional() {
    assertEvaluations("""
        yes ? 1 : 2 => int 1
        false ? 1 : 2 => int 2
        two > 1 ? 10u : 20u => uint 10
        yes ? 1 : 2u => uint 1
        yes ? two : fromFloat => int 2
        yes ? fromFloat : two => unknown
        false ? fromFloat : two => int 2
        yes ? 1 : 2.0 => unknown
        half > 0.0 ? 1 : 2 => unknown
        runtime > 0 ? 1 : 2 => not constant
        yes ? runtime : 2 => not constant
        yes ? 1 : runtime => not constant""");
  }

  @Test
  void testCallsAndAccess() {
    assertEvaluations("""
        int(7u) => int 7
        uint(-1) => uint 4294967295
        int(true) => int 1
        bool(2) => bool 1
        bool(0) => bool 0
        int8_t(300) => int8_t 44
        int(1.5) => unknown
        float(1) => unknown
        int(half) => unknown
        ivec2(1, 2) => unknown
        vec3(1) => unknown
        int[2](1, 2) => unknown
        S(1) => unknown
        int(runtime) => not constant
        ivec2(1, runtime) => not constant
        max(1, 2) => unknown
        abs(-two) => unknown
        max(runtime, 2) => not constant
        user(1) => not constant
        missingFunction(1) => unknown
        pair.x => unknown
        structure.a => unknown
        values[1] => unknown
        values[runtime] => not constant
        vector.x => not constant
        sized[0] => not constant
        sized.length() => int 4
        values.length() => int 3
        vector.length() => int 3
        matrix.length() => int 2
        unknownSize.length() => unknown
        unsized.length() => unknown
        structure.length() => not constant
        (1, 2) => not constant
        variable = 1 => not constant
        variable += 1 => not constant
        variable -= 1 => not constant
        variable *= 1 => not constant
        variable /= 1 => not constant
        variable %= 1 => not constant
        variable <<= 1 => not constant
        variable >>= 1 => not constant
        variable &= 1 => not constant
        variable |= 1 => not constant
        variable ^= 1 => not constant
        "text" => unknown""");
  }

  @Test
  void testFloatingPointConstantsAreNotEvaluated() {
    // imprecision: only integers and booleans are evaluated, so the size of this
    // array is not known and the access out of bounds is not reported
    assertEquals("ok", codes(analyze("""
        #version 460
        float array[int(2.0)];
        void main() { array[5] = 1.0; }
        """)));
  }
}
