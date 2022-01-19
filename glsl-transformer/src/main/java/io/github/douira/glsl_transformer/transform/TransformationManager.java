package io.github.douira.glsl_transformer.transform;

import java.util.function.Function;

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
import io.github.douira.glsl_transformer.print.PrintVisitor;
import io.github.douira.glsl_transformer.print.filter.TokenFilter;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

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
 * 
 * The transformation manager also manages job parameters. It has a private
 * field that it stores the job parameters, that are passed to it along with the
 * string to be transformed, during transformation. This means that the entire
 * chain of objects that are involved in the transformation have to be generic
 * in order to be able to make use of job parameters. If no job parameter is
 * used the {@link java.lang.Void} type can be used and object constructors can
 * use the default parameter {@code <>} which assigns an
 * {@link java.lang.Object}. This is fine as long as the anonymous class being
 * constructed doesn't need to use the job parameters. If it does need to use
 * them, the whole chain of participating objects needs to be properly
 * parameterized. (transformation manager -> transformation -> transformation
 * phase)
 * 
 * @param <T> The job parameters type
 */
public class TransformationManager<T> extends PhaseCollector<T> {
  /**
   * An internal instance of this class that is used by other library-internal
   * classes for parsing needs.
   */
  public static final TransformationManager<Void> INTERNAL = new TransformationManager<>();

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
  private T jobParameters;

  /**
   * The last parsed input stream. This property can be used together with the
   * parse methods since they don't give direct access to the internally created
   * input stream and token stream.
   */
  protected IntStream input;

  /**
   * The last parsed tokens stream.
   * 
   * @see #input
   */
  protected BufferedTokenStream tokenStream;

  /**
   * Optionally a token filter for printing a tree. Can be {@code null} if no
   * filter is to be used.
   */
  private TokenFilter printTokenFilter;

  /**
   * Optionally a token filter source that applies a token filter before parsing.
   * It filters the tokens coming from the lexer before the parser consumes them.
   * The contained token filter can be {@code null} if no filter is to be used.
   */
  private FilterTokenSource tokenSource = new FilterTokenSource(lexer);

  /**
   * Creates a new transformation manager and specifies if parse errors should be
   * thrown during parsing. If they should not be thrown they will not be reported
   * or printed to the console. ANTLR will attempt to recover from errors during
   * parsing any construct a parse tree containing error nodes. These nodes can
   * mess up transformation and printing. Do not expect anything to function
   * properly if an error was encountered during parsing.
   * 
   * Custom error handlers can be registered on the parser and lexer manually. For
   * example, an error handler similar to ConsoleErrorListener that allows
   * recovery and only collects the errors instead of printing them could be
   * created.
   * 
   * @param throwParseErrors If {@code true}, the transformation manager throw any
   *                         parse errors encountered during parsing
   */
  public TransformationManager(boolean throwParseErrors) {
    lexer.removeErrorListeners();
    parser.removeErrorListeners();

    if (throwParseErrors) {
      lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
      parser.addErrorListener(ThrowingErrorListener.INSTANCE);
      // parser.setErrorHandler(new BailErrorStrategy());
    }
  }

  /**
   * Creates a new transformation manager that throws parse errors by default.
   */
  public TransformationManager() {
    this(true);
  }

  /**
   * The returned parser (and lexer) may contain no token stream or a wrong token
   * stream. However, the parser should not be used for parsing manually anyways.
   * The state and contents of the parser are setup correctly when the
   * transformation is performed.
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

  @Override
  T getJobParameters() {
    return jobParameters;
  }

  /**
   * Sets the token filter to use before printing.
   * 
   * @param printTokenFilter The new print token filter
   */
  public void setPrintTokenFilter(TokenFilter printTokenFilter) {
    this.printTokenFilter = printTokenFilter;
  }

  /**
   * Sets the token filter to use before parsing. It's placed between the lexer
   * and the token stream.
   * 
   * @param parseTokenFilter The new parse token filter
   */
  public void setParseTokenFilter(TokenFilter parseTokenFilter) {
    this.tokenSource.setTokenFilter(parseTokenFilter);
  }

  /**
   * Parses a string using a parser method reference into a parse tree.
   * 
   * @param <RuleType>  The type of the resulting parsed node
   * @param str         The string to parse
   * @param parseMethod The parser method reference to use for parsing
   * @return The parsed string as a parse tree that has the given type
   */
  public <RuleType extends ExtendedContext> RuleType parse(
      String str, Function<GLSLParser, RuleType> parseMethod) {
    return parse(str, null, parseMethod);
  }

  /**
   * Parses a string using a parser method reference into a parse tree.
   * 
   * @param <RuleType>  The type of the resulting parsed node
   * @param str         The string to parse
   * @param parent      The parent to attach to the parsed node
   * @param parseMethod The parser method reference to use for parsing
   * @return The parsed string as a parse tree that has the given type
   */
  public <RuleType extends ExtendedContext> RuleType parse(
      String str, ExtendedContext parent,
      Function<GLSLParser, RuleType> parseMethod) {
    return parse(CharStreams.fromString(str), parent, parseMethod);
  }

  /**
   * Parses an int stream (which is similar to a string) using a parser method
   * reference into a parse tree. This method exists so non-string streams can
   * also be parsed.
   * 
   * @param <RuleType>  The type of the resulting parsed node
   * @param stream      The int stream to parse
   * @param parent      The parent to attach to the parsed node
   * @param parseMethod The parser method reference to use for parsing
   * @return The parsed string as a parse tree that has the given type
   */
  public <RuleType extends ExtendedContext> RuleType parse(
      IntStream stream, ExtendedContext parent,
      Function<GLSLParser, RuleType> parseMethod) {
    input = stream;
    lexer.setInputStream(input);
    lexer.reset();
    tokenStream = new CommonTokenStream(tokenSource);
    parser.setTokenStream(tokenStream);
    parser.reset();

    var node = parseMethod.apply(parser);
    node.setParent(parent);
    return node;
  }

  /**
   * Transforms the given string by parsing, transforming it with the already
   * registered transformations and then re-printing it. The job parameters are
   * set to {@code null} since none are passed.
   * 
   * @param str The string to be transformed
   * @return The transformed string
   */
  public String transform(String str) throws RecognitionException {
    return transform(str, null);
  }

  /**
   * Transforms the given string by parsing, transforming it with the already
   * registered transformations and then re-printing it. The job parameters are
   * set to {@code null} since none are passed.
   * 
   * @param str        The string to be transformed
   * @param parameters The job parameters
   * @return The transformed string
   */
  public String transform(String str, T parameters) throws RecognitionException {
    return transformStream(CharStreams.fromString(str), parameters);
  }

  /**
   * Transforms a given input stream and re-prints it as a string.
   * 
   * @see #transformStream(IntStream, Object)
   * 
   * @param stream The input stream to be transformed
   * @return The transformed string
   */
  public String transformStream(IntStream stream) throws RecognitionException {
    return transformStream(stream, null);
  }

  /**
   * Transforms a given input stream and re-prints it as a string. This is useful
   * if the input isn't a string yet. Then ANTLR's
   * {@link org.antlr.v4.runtime.CharStreams} can be used to generate a stream,
   * not necessarily from a string.
   * 
   * This is the entry point for job parameters that can be accessed by
   * transformation phases during transformation. It's called "job" parameters
   * because it may change for each transformation job. A transformation job is a
   * single instance of parsing and transformation. There is only one job
   * parameter reference. If multiple values are required, they have to be packed
   * into an object or an array. (This is a good place to use a Record)
   * 
   * @param stream     The input stream to be transformed
   * @param parameters The job parameters
   * @return The transformed string
   */
  public String transformStream(IntStream stream, T parameters) throws RecognitionException {
    var tree = parse(stream, null, GLSLParser::translationUnit);
    jobParameters = parameters;
    transformTree(tree, tokenStream);
    jobParameters = null;
    return PrintVisitor.printTree(tokenStream, tree, printTokenFilter);
  }
}
