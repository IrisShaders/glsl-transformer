package me.douira.antlr_experiments;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.SyntaxTree;

public class TransformationVisitor extends GLSLParserBaseListener {
  private EditContext editContext;

  public TransformationVisitor(EditContext editContext) {
    this.editContext = editContext;
  }

  private void registerRemoval(SyntaxTree node) {
    editContext.markDeleted(node);
  }

  private void removeNode(ParserRuleContext node) {
    var children = node.getParent().children;

    //the node needs to be replaced with something to preserve the length or there's NPEs in the walker
    children.set(children.indexOf(node), new StringNode(null));

    registerRemoval(node);
  }

  @Override
  public void enterLayoutQualifier(GLSLParser.LayoutQualifierContext context) {
    removeNode(context);
  }

  @Override
  public void enterFunctionDefinition(GLSLParser.FunctionDefinitionContext context) {
    removeNode(context);
  }
}
