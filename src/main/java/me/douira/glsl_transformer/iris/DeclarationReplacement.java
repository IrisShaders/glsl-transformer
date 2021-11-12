package me.douira.glsl_transformer.iris;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.GLSLParser.DeclarationContext;
import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import me.douira.glsl_transformer.transform.Phase;
import me.douira.glsl_transformer.transform.Transformation;

public class DeclarationReplacement extends Transformation {
  private List<String> declarationNames;

  protected void initState() {
    declarationNames = new LinkedList<>();
  }

  protected void createPhases() {
    addPhase(new Phase() {
      ParseTreePattern declarationPattern;

      @Override
      public void init() {
        declarationPattern = getParser().compileParseTreePattern(
            "layout (location = 0) <type:storageQualifier> vec4 <names:declarationMemberList>;",
            GLSLParser.RULE_externalDeclaration);
      }

      @Override
      public void beforeWalk(TranslationUnitContext ctx) {
        var matches = declarationPattern.findAll(ctx, "//externalDeclaration");
        for (var match : matches) {
          System.out.println("match: " + match.getLabels());
          System.out.println(match.get("type").getText());
          System.out.println(match.get("names").getText());
          System.out.println(XPath.findAll(match.get("names"), "//declarationMember", getParser()));
        }
      }

      @Override
      public void enterDeclaration(DeclarationContext ctx) {
        replaceNode(ctx, "bar");

        // doesn't work because there are multiple type qualifiers
        // grammar needs further improvement
        // declarationName = ctx.typeQualifier();
      }
    });
  }
}
