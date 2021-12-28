package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.generic.PrintVisitor;

/**
 * Implements the phase collector by providing the boilerplate code for setting
 * up an input, a lexer and a parser.
 * 
 * The transformation manager is meant to be used to transform many strings and
 * be re-used for many transformation jobs of the same kind. For entirely
 * different jobs a new manager should be created. Common transformations can be
 * shared between them.
 * 
 * For printing a tree without transforming it, a manager without and
 * transformations can be used.
 * 
 * Creating manager instances isn't costly as ANTLR's parser and lexer
 * instantiation is efficient.
 */
public class TransformationManager extends PhaseCollector {
  private static class ThrowingErrorListener extends BaseErrorListener {
    public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

    @Override
    public void syntaxError(
        Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
        String msg, RecognitionException e) throws ParseCancellationException {
      throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg, e);
    }
  }

  // inited with null since they need an argument
  private final GLSLLexer lexer = new GLSLLexer(null);
  private final GLSLParser parser = new GLSLParser(null);

  private IntStream input;
  private BufferedTokenStream tokenStream;

  public TransformationManager() {
    super();

    lexer.removeErrorListeners();
    lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

    parser.removeErrorListeners();
    parser.addErrorListener(ThrowingErrorListener.INSTANCE);

    // parser.setErrorHandler(new BailErrorStrategy());
  }

  /**
   * The returned parser may contain no token stream or a wrong token stream.
   * However, the parser should not be used for parsing manually anyways. The
   * state and contents of the parser are setup correctly when the transformation
   * is performed.
   * 
   * {@inheritDoc}
   */
  @Override
  public GLSLParser getParser() {
    return parser;
  }

  @Override
  public GLSLLexer getLexer() {
    return lexer;
  }

  /**
   * Transforms the given string by parsing, transforming it with the already
   * registered transformations and then re-printing it.
   * 
   * @param str The string to be transformed
   * @return The transformed string
   */
  public String transform(String str) throws RecognitionException {
    return transformStream(CharStreams.fromString(str));
  }

  /**
   * Transforms a given input stream and re-prints it as a string. This is useful
   * if the input isn't a string yet. Then ANTLR's
   * {@link org.antlr.v4.runtime.CharStreams} can be used to generate a stream,
   * not necessarily from a string.
   * 
   * @param stream The input stream to be transformed
   * @return The transformed string
   */
  public String transformStream(IntStream stream) throws RecognitionException {
    input = stream;
    lexer.setInputStream(input);
    lexer.reset();
    tokenStream = new CommonTokenStream(lexer);
    parser.setTokenStream(tokenStream);
    parser.reset();

    var tree = parser.translationUnit();
    transformTree(tree, tokenStream);
    var result = PrintVisitor.printTree(tokenStream, tree);

    input = null;
    tokenStream = null;
    return result;
  }
}
