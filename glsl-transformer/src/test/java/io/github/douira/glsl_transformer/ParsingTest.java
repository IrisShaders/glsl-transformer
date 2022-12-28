package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.DirectoryLocation;

@ExtendWith({ SnapshotExtension.class })
public class ParsingTest {
  private Expect expect;
  private Exception storeException;
  private SingleASTTransformer<JobParameters> manager;

  @BeforeEach
  void setupManager() {
    manager = new SingleASTTransformer<>((tu) -> {
      tu.parseAndInjectNode(manager, ASTInjectionPoint.BEFORE_ALL, "f;");
    });
  }

  @Test
  void testGetLexer() {
    assertNotNull(manager.getParser(), "It should have a parser");
  }

  @Test
  void testGetParser() {
    assertNotNull(manager.getParser(), "It should have a lexer");
  }

  void assertParseErrorType(Class<? extends RecognitionException> type, Executable executable, String message) {
    assertThrows(ParseCancellationException.class, () -> {
      try {
        executable.execute();
      } catch (ParseCancellationException exception) {
        storeException = exception;
        throw exception;
      }
    });
    assertSame(type, storeException.getCause().getClass(),
        "It should throw a ParseCancellationException with the cause " + type.getSimpleName());
  }

  @Test
  void testTransform() {
    assertEquals(
        "f; a; b; c; d; ",
        manager.transform("a;//present\nb;c;d;"));

    assertParseErrorType(
        InputMismatchException.class, () -> manager.transform(
            "//present"),
        "It should throw on an incomplete input");
    assertParseErrorType(
        NoViableAltException.class, () -> manager.transform(
            "foo"),
        "It should throw when there is no viable alternative while parsing the input");
    assertParseErrorType(
        LexerNoViableAltException.class, () -> manager.transform(
            "§"),
        "It should throw when there is no viable alternative while tokenizing the input");

    // FailedPredicateException is difficult to test and may never actually occur
  }

  @Test
  @SnapshotName("testGlslangErrors")
  void testGlslangErrors() {
    class CollectingErrorListener extends BaseErrorListener {
      private List<String> errors = new ArrayList<>();

      @Override
      public void syntaxError(
          Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
          String msg, RecognitionException e) throws ParseCancellationException {
        errors.add(
            line + ":" + charPositionInLine + "; " +
                (offendingSymbol == null
                    ? "<no symbol>"
                    : offendingSymbol instanceof CommonToken token
                        ? token.toString(recognizer)
                        : offendingSymbol.toString())
                + "; " + msg + "; " +
                (e == null
                    ? "<no exception>"
                    : e.getClass().getSimpleName() + ":" + e.getMessage()));
      }
    }

    TestResourceManager
        .getDirectoryResources(DirectoryLocation.GLSLANG_TESTS)
        .forEach(resource -> {
          var t = new SingleASTTransformer<JobParameters>(SingleASTTransformer.IDENTITY_TRANSFORMATION);
          t.setPrintType(PrintType.INDENTED);
          t.setThrowParseErrors(false);
          t.setSLLOnly();
          var collectingListener = new CollectingErrorListener();
          t.getLexer().addErrorListener(collectingListener);
          t.getParser().addErrorListener(collectingListener);
          t.getLexer().enableIncludeDirective = true;

          var content = resource.content();
          var expectScenario = expect.scenario(resource.getScenarioName());

          Exception astException = null;
          try {
            t.transform(content);
          } catch (Exception e) {
            astException = e;
          }

          expectScenario.toMatchSnapshot(
              SnapshotUtil.inputOutputSnapshot(
                  content,
                  String.join("\n", collectingListener.errors) +
                      (astException == null ? "" : "\n" + astException)));

        });
  }

  @Test
  void testWithJobParameters() {
    var man = new SingleASTTransformer<JobParameters>(SingleASTTransformer.IDENTITY_TRANSFORMATION);
    assertNull(man.getJobParameters(), "It should start with no job parameters");
    var parameters = JobParameters.EMPTY;
    man.withJobParameters(parameters,
        () -> {
          assertSame(parameters, man.getJobParameters(), "It should contain the job parameters");
          return null;
        });
    assertNull(man.getJobParameters(), "It should delete the job parameters after use");
    var result = new Object();
    assertSame(result, man.withJobParameters(parameters,
        () -> {
          assertSame(parameters, man.getJobParameters(), "It should contain the job parameters again");
          return result;
        }), "It should return the value of the supplier function");
  }
}
