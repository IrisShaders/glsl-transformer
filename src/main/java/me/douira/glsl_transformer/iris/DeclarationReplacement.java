package me.douira.glsl_transformer.iris;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.generic.StringNode;
import me.douira.glsl_transformer.transform.Phase;
import me.douira.glsl_transformer.transform.Transformation;

public class DeclarationReplacement extends Transformation {
  private String declarationName;

  protected void init() {
    addPhase(new Phase() {
      @Override
      public void beforeWalk(GLSLParser.TranslationUnitContext TUContext) {
        TUContext.children.add(new StringNode("foo"));
      }
    });
  }
}
