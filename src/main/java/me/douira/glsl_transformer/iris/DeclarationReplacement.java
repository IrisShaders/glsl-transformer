package me.douira.glsl_transformer.iris;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.GLSLParser.ExternalDeclarationContext;
import me.douira.glsl_transformer.GLSLParser.FunctionHeaderContext;
import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import me.douira.glsl_transformer.GLSLParser.VariableIdentifierContext;
import me.douira.glsl_transformer.transform.Transformation;
import me.douira.glsl_transformer.transform.WalkPhase;

//TODO: do multiple declarations need to be found or can there only ever be one in a semantically valid shader?
public class DeclarationReplacement extends Transformation {
  private record Declaration(String type, String name) {
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
          // check for valid format and add to the list if it is valid
          var type = match.get("type").getText();
          var name = match.get("name").getText();

          if (name == "iris_Position") {
            throw new SemanticException(String.format("Disallowed GLSL declaration with the name \"{0}\"!", name), ctx);
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
          throw new SemanticException(
              String.format("Disallowed GLSL declaration with the name \"{0}\"!", "iris_getModelSpaceVertexPosition"),
              ctx);
        }
      }

      @Override
      protected void afterWalk(TranslationUnitContext ctx) {
        if (!declarations.isEmpty()) {
          // is only run if phase is found to be active
          // TODO: the function content and the new attribute declaration
          ctx.children
              .add(createLocalRoot("void iris_getModelSpaceVertexPosition() { }", GLSLParser::externalDeclaration));
          ctx.children.add(
              createLocalRoot("layout (location = 0) attribute vec4 iris_Position;", GLSLParser::externalDeclaration));
        }
      }
    });

    addPhase(new WalkPhase() {
      @Override
      protected boolean isActive() {
        return !declarations.isEmpty();
      }

      @Override
      public void enterVariableIdentifier(VariableIdentifierContext ctx) {
        // check for one of the identifiers we're looking for
        var identifier = ctx.IDENTIFIER();
        var matchingDeclaration = declarations.get(identifier.getText());
        if (matchingDeclaration != null) {
          // perform replacement of this reference
          replaceNode(ctx, "iris_getModelSpaceVertexPosition()", GLSLParser::expression);
        }
      }
    });
  }
}
