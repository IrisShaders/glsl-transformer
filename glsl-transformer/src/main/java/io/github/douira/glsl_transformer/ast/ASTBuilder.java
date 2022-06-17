package io.github.douira.glsl_transformer.ast;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.ast.node.*;

public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    var type = node.getSymbol().getType();
    if (type == GLSLLexer.IDENTIFIER) {
      return Identifier.from(node);
    }
    throw new IllegalStateException("Unhandled terminal node: " + node.getText());
  }
}
