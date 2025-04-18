package io.github.douira.glsl_transformer.parser;

import io.github.douira.glsl_transformer.GLSLParser;
import org.antlr.v4.runtime.ParserRuleContext;

public class TranslationUnitFilterCachingParser extends CachingParser {
  public TranslationUnitFilterCachingParser(boolean throwParseErrors, int cacheSize) {
    super(throwParseErrors, cacheSize);
  }

  public TranslationUnitFilterCachingParser() {
    super();
  }

  @Override
  public <C extends ParserRuleContext> C parse(String str, ParserRuleContext parent, ParseShape<C, ?> parseShape) {
    if (parseShape.ruleType == GLSLParser.TranslationUnitContext.class) {
      return parse(str, parent, parseShape);
    }
    return super.parse(str, parent, parseShape);
  }
}
