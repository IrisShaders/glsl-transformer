package me.douira.glsl_transformer.iris;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.GLSLParser.ExternalDeclarationContext;
import me.douira.glsl_transformer.GLSLParser.FunctionHeaderContext;
import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import me.douira.glsl_transformer.GLSLParser.VariableIdentifierContext;
import me.douira.glsl_transformer.generic.StringNode;
import me.douira.glsl_transformer.transform.WalkPhase;
import me.douira.glsl_transformer.transform.Transformation;

//TODO: do multiple declarations need to be found or can there only ever be one in a semantically valid shader?
public class DeclarationReplacement extends Transformation {
  private class Declaration {
    public String type;
    public String name;

    Declaration(String type, String name) {
      this.type = type;
      this.name = name;
    }
  }

  private Map<String, Declaration> declarations;

  @Override
  protected void resetState() {
    declarations = new HashMap<>();
  }

  @Override
  protected void createPhases() {
    addPhase(new WalkPhase() {
      ParseTreePattern declarationPattern;

      @Override
      protected void init() {
        declarationPattern = compilePattern("layout (location = 0) <type:storageQualifier> vec4 <name:IDENTIFIER>;",
            GLSLParser.RULE_externalDeclaration);
      }

      @Override
      public void enterExternalDeclaration(ExternalDeclarationContext ctx) {
        var match = declarationPattern.match(ctx);
        if (match.succeeded()) {
          // System.out.println("match: " + match.getLabels());
          // System.out.println(match.get("type").getText());
          // System.out.println(match.get("name").getText());

          // check for valid format and add to the list if it is valid
          var type = match.get("type").getText();
          var name = match.get("name").getText();

          if (name == "iris_Position") {
            // TODO: throw exception on bad name
          }

          if (type.equals("in") || type.equals("attribute")) {
            declarations.put(name, new Declaration(type, name));
            removeNode((ParserRuleContext) match.getTree());
          }
        }
      }

      @Override
      public void enterFunctionHeader(FunctionHeaderContext ctx) {
        if (ctx.IDENTIFIER().getText().equals("iris_getModelSpaceVertexPosition")) {
          // TODO: throw error on bad name
        }
      }

      @Override
      protected void afterWalk(TranslationUnitContext ctx) {
        // add the new things if necessary
        if (!declarations.isEmpty()) {
          // TODO: the function content and the new attribute declaration
          ctx.children.add(new StringNode(
              "iris_getModelSpaceVertexPosition() { TODO }\nlayout (location = 0) attribute vec4 iris_Position;"));

        }
      }
    });

    addPhase(new WalkPhase() {
      @Override
      public void enterVariableIdentifier(VariableIdentifierContext ctx) {
        // check for one of the identifiers we're looking for
        var identifier = ctx.IDENTIFIER();
        var matchingDeclaration = declarations.get(identifier.getText());
        if (matchingDeclaration != null) {
          // perform replacement of this reference
          replaceNode(ctx, "iris_getModelSpaceVertexPosition()");
        }
      }
    });
  }
}
