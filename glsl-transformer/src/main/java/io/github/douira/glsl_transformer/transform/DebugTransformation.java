package io.github.douira.glsl_transformer.transform;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.ExternalDeclarationContext;
import io.github.douira.glsl_transformer.generic.ExtendedParserRuleContext;

/**
 * The debug transformation is used for testing out things.
 */
public class DebugTransformation extends Transformation {
  @Override
  protected void createPhases() {
    addPhase(new WalkPhase() {
      ParseTreePattern pattern;

      @Override
      protected void init() {
        pattern = compilePattern("void <IDENTIFIER>() <compoundStatement>", GLSLParser.RULE_externalDeclaration);
      }

      @Override
      public void enterExternalDeclaration(ExternalDeclarationContext ctx) {
        var match = pattern.match(ctx);
        if (match.succeeded()) {
          removeNode((ExtendedParserRuleContext) match.getTree());
        }
      }
    });
  }
}
