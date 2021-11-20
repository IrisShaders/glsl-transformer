package me.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.GLSLParser.ExternalDeclarationContext;

public class DebugTransformation extends Transformation {
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
          removeNode((ParserRuleContext) match.getTree());
        }
      }
    });
  }
}
