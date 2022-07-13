package io.github.douira.glsl_transformer.transform;

import java.util.function.Consumer;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.ast.ASTBuilder;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.transform.EnhancedParser.ParsingStrategy;

public class ASTTransformer implements Transformer, ParserInterface {
  private final EnhancedParser parser;
  private Consumer<TranslationUnit> transformation;

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

  @Override
  public String transformStream(IntStream charStream) throws RecognitionException {
    var parseTree = parser.parse(charStream, null, GLSLParser::translationUnit);
    var translationUnit = (TranslationUnit)ASTBuilder.build(parseTree);
    transformation.accept(translationUnit);
    return ASTPrinter.printedIndented(translationUnit);
  }
}
