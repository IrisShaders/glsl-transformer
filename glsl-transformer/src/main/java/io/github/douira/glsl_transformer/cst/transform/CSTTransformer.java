package io.github.douira.glsl_transformer.cst.transform;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.cst.print.PrintVisitor;
import io.github.douira.glsl_transformer.cst.token_filter.TokenFilter;
import io.github.douira.glsl_transformer.job_parameter.*;

/**
 * Implements the execution planner by providing the boilerplate code for
 * setting
 * up an input, a lexer and a parser.
 * 
 * The CST transformer (formerly called the transformation manager) is meant to
 * be used to transform many strings and be re-used for many transformation jobs
 * of the same kind. For entirely different jobs a new manager should be
 * created. Common transformations can be shared between them.
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
public class CSTTransformer<T extends JobParameters> extends ExecutionPlanner<T>
    implements ParameterizedTransformer<T, String>, ParserInterface {
  /**
   * Optionally a token filter for printing a tree. Can be {@code null} if no
   * filter is to be used.
   */
  private TokenFilter<T> printTokenFilter;
  private TokenFilter<T> parseTokenFilter;

  private final EnhancedParser parser;

  /**
   * Creates a new transformation manager with a given root transformation. Throws
   * parse errors by default.
   * 
   * @param rootTransformation The root transformation to use
   */
  public CSTTransformer(Transformation<T> rootTransformation) {
    super(rootTransformation);
    parser = new EnhancedParser();
  }

  public CSTTransformer() {
    parser = new EnhancedParser();
  }

  @Override
  public GLSLLexer getLexer() {
    return parser.getLexer();
  }

  @Override
  public GLSLParser getParser() {
    return parser.getParser();
  }

  @Override
  public void setThrowParseErrors(boolean throwParseErrors) {
    parser.setThrowParseErrors(throwParseErrors);
  }

  @Override
  public void setParsingStrategy(ParsingStrategy parsingStrategy) {
    parser.setParsingStrategy(parsingStrategy);
  }

  @Override
  public void setSLLOnly() {
    parser.setSLLOnly();
  }

  @Override
  public void setLLOnly() {
    parser.setLLOnly();
  }

  /**
   * Sets the token filter to use before printing.
   * 
   * @param printTokenFilter The new print token filter
   */
  public void setPrintTokenFilter(TokenFilter<T> printTokenFilter) {
    this.printTokenFilter = printTokenFilter;
  }

  @Override
  @SuppressWarnings("unchecked") // the interface is not generic
  public void setParseTokenFilter(TokenFilter<?> parseTokenFilter) {
    parser.setParseTokenFilter(parseTokenFilter);
    this.parseTokenFilter = (TokenFilter<T>) parseTokenFilter;
  }

  @Override
  public TokenFilter<?> getParseTokenFilter() {
    return parser.getParseTokenFilter();
  }

  private void setTokenFilterPlanner(TokenFilter<T> tokenFilter) {
    if (tokenFilter != null) {
      tokenFilter.setPlanner(this);
    }
  }

  protected TranslationUnitContext parseTranslationUnit(String str) {
    return parser.parse(str);
  }

  protected BufferedTokenStream getTokenStream() {
    return parser.getTokenStream();
  }

  @Override
  public String transform(String str) throws RecognitionException {
    setTokenFilterPlanner(printTokenFilter);
    setTokenFilterPlanner(parseTokenFilter);
    var tree = parseTranslationUnit(str);
    var tokenStream = getTokenStream();
    transformTree(tree, tokenStream);
    return PrintVisitor.printTree(tokenStream, tree, printTokenFilter);
  }
}
