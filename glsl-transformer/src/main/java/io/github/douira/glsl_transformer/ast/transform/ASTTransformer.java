package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.*;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.job_parameter.*;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.util.TriConsumer;

/**
 * The AST transformer takes parses a string, turns it into an AST, transforms
 * it with the given transformation and then prints it back.
 */
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

  public ASTTransformer(Consumer<TranslationUnit> transformation, boolean throwParseErrors) {
    this(throwParseErrors);
    setTransformation(transformation);
  }

  public ASTTransformer(Consumer<TranslationUnit> transformation) {
    this();
    setTransformation(transformation);
  }

  public ASTTransformer(BiConsumer<TranslationUnit, Root> transformation) {
    this();
    setTransformation(transformation);
  }

  public ASTTransformer(TriConsumer<TranslationUnit, Root, T> transformation) {
    this();
    setTransformation(transformation);
  }

  public void setTransformation(Consumer<TranslationUnit> transformation) {
    this.transformation = transformation;
  }

  public void setTransformation(BiConsumer<TranslationUnit, Root> transformation) {
    this.transformation = wrapTransformation(this, transformation);
  }

  public void setTransformation(TriConsumer<TranslationUnit, Root, T> transformation) {
    this.transformation = wrapTransformation(this, transformation);
  }

  public static <T> Consumer<TranslationUnit> wrapTransformation(ParameterizedTransformer<T> transformer,
      TriConsumer<TranslationUnit, Root, T> transformation) {
    return translationUnit -> transformation.accept(
        translationUnit,
        translationUnit.getRoot(),
        transformer.getJobParameters());
  }

  public static Consumer<TranslationUnit> wrapTransformation(
      ParameterizedTransformer<?> transformer,
      BiConsumer<TranslationUnit, Root> transformation) {
    return translationUnit -> transformation.accept(
        translationUnit,
        translationUnit.getRoot());
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

  public <RuleType extends ExtendedContext, ReturnType extends ASTNode> ReturnType parseNode(
      String input,
      ASTNode parentTreeMember,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    return ASTBuilder.buildSubtreeWith(
        parentTreeMember,
        parser.parse(input, parseMethod),
        visitMethod);
  }

  public <RuleType extends ExtendedContext, ReturnType extends ASTNode> ReturnType parseNode(
      String input,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    return ASTBuilder.build(
        parser.parse(input, parseMethod),
        visitMethod);
  }

  public ASTNode parseNode(
      String input,
      Function<GLSLParser, ? extends ExtendedContext> parseMethod) throws RecognitionException {
    return ASTBuilder.build(parser.parse(input, parseMethod));
  }

  public String transformBare(PrintType printType, String str) throws RecognitionException {
    var translationUnit = parseTranslationUnit(str);
    transformation.accept(translationUnit);
    return ASTPrinter.print(printType, translationUnit);
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

  public TranslationUnit parseTranslationUnit(String input) throws RecognitionException {
    return parseNode(input, GLSLParser::translationUnit, ASTBuilder::visitTranslationUnit);
  }

  public ExternalDeclaration parseExternalDeclaration(ASTNode treeMember, String input)
      throws RecognitionException {
    return parseNode(input, treeMember, GLSLParser::externalDeclaration, ASTBuilder::visitExternalDeclaration);
  }

  public Expression parseExpression(ASTNode treeMember, String input) throws RecognitionException {
    return parseNode(input, treeMember, GLSLParser::expression, ASTBuilder::visitExpression);
  }

  public Statement parseStatement(ASTNode treeMember, String input) throws RecognitionException {
    return parseNode(input, treeMember, GLSLParser::statement, ASTBuilder::visitStatement);
  }
}
