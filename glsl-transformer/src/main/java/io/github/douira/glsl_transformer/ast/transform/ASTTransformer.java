package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.*;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

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
    var translationUnit = (TranslationUnit) ASTBuilder.build(parseTree);
    transformation.accept(translationUnit);
    return ASTPrinter.printedIndented(translationUnit);
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
}