package me.douira.glsl_transformer.transform;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.SyntaxTree;

import me.douira.glsl_transformer.GLSLParserBaseListener;
import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import me.douira.glsl_transformer.generic.EditContext;
import me.douira.glsl_transformer.generic.StringNode;

public abstract class Phase extends GLSLParserBaseListener {
  private EditContext editContext;

  public static List<ParseTree> getSiblings(ParserRuleContext node) {
    return node.getParent().children;
  }

  void setEditContext(EditContext editContext) {
    this.editContext = editContext;
  }

  public void omitNodeTokens(SyntaxTree node) {
    editContext.omitNodeTokens(node);
  }

  public void replaceNode(ParserRuleContext node, ParseTree newNode) {
    var children = getSiblings(node);
    children.set(children.indexOf(node), newNode);
    omitNodeTokens(node);
  }

  public void removeNode(ParserRuleContext node) {
    // the node needs to be replaced with something to preserve the length or
    // there's NPEs in the walker
    replaceNode(node, new StringNode(null));
  }

  public void replaceNode(ParserRuleContext node, String newContents) {
    replaceNode(node, new StringNode(newContents));
  }

  public void beforeWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }

  public void afterWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }
}
