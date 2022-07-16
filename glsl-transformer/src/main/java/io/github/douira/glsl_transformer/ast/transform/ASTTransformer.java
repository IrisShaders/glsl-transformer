package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.*;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.job_parameter.*;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTTransformer<T extends JobParameters> implements ParameterizedTransformer<T>, ParserInterface {
  private final EnhancedParser parser;
  private Consumer<TranslationUnit> transformation;
  private T jobParameters;

  public ASTTransformer() {
    parser = new EnhancedParser();
  }

  public ASTTransformer(boolean throwParseErrors) {
    parser = new EnhancedParser(throwParseErrors);
  }

  public ASTTransformer(Consumer<TranslationUnit> transformation) {
    this();
    this.transformation = transformation;
  }

  public ASTTransformer(Consumer<TranslationUnit> transformation, boolean throwParseErrors) {
    this(throwParseErrors);
    this.transformation = transformation;
  }

  public void setTransformation(Consumer<TranslationUnit> transformation) {
    this.transformation = transformation;
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
  public EnhancedParser getInternalParser() {
    return parser;
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

  public <RuleType extends ExtendedContext> ASTNode parseNode(
      String input,
      Function<GLSLParser, RuleType> parseMethod) throws RecognitionException {
    var parseTree = parser.parse(input, null, parseMethod);
    return ASTBuilder.build(parseTree);
  }

  public <RuleType extends ExtendedContext> ASTNode parseNode(
      String input,
      ASTNode parentTreeMember,
      Function<GLSLParser, RuleType> parseMethod) throws RecognitionException {
    var parseTree = parser.parse(input, null, parseMethod);
    return ASTBuilder.buildSubtreeFor(parentTreeMember, parseTree);
  }

  public <RuleType extends ExtendedContext, ReturnType extends ASTNode> ReturnType parseNode(
      String input,
      ASTNode parentTreeMember,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    var parseTree = parser.parse(input, null, parseMethod);
    return ASTBuilder.buildSubtreeWith(parentTreeMember, parseTree, visitMethod);
  }

  public String transformBare(PrintType printType, String str) throws RecognitionException {
    var parseTree = parser.parse(CharStreams.fromString(str), null, GLSLParser::translationUnit);
    var translationUnit = (TranslationUnit) ASTBuilder.build(parseTree);
    transformation.accept(translationUnit);
    return ASTPrinter.printIndented(translationUnit);
  }

  public String transform(
      PrintType printType, String str, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transformBare(printType, str));
  }

  public String transform(PrintType printType, String str) throws RecognitionException {
    return transform(printType, str, null);
  }

  @Override
  public T getJobParameters() {
    return jobParameters;
  }

  @Override
  public void setJobParameters(T parameters) {
    jobParameters = parameters;
  }

  @Override
  public String transformBare(String str) throws RecognitionException {
    return transformBare(PrintType.INDENTED, str);
  }
}
