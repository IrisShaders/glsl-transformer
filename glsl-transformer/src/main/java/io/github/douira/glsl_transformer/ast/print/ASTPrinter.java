package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.ConditionExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.PragmaStatement.PragmaType;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.print.token.EOFToken;

public abstract class ASTPrinter extends ASTPrinterUtil {
  @Override
  public void exitTranslationUnit(TranslationUnit node) {
    emitToken(new EOFToken(node));
  }

  @Override
  public Void visitVersionStatement(VersionStatement node) {
    emitType(node, GLSLLexer.NR, GLSLLexer.VERSION);
    emitExtendableSpace(node);
    emitLiteral(node, Integer.toString(node.version));
    if (node.profile != null) {
      emitExtendableSpace(node);
      emitType(node, node.profile.tokenType);
    }
    emitExactNewline(node);
    return null;
  }

  @Override
  public Void visitEmptyDeclaration(EmptyDeclaration node) {
    emitType(node, GLSLLexer.SEMICOLON);
    emitCommonNewline(node);
    return null;
  }

  @Override
  public Void visitPragmaStatement(PragmaStatement node) {
    emitType(node, GLSLLexer.NR, GLSLLexer.PRAGMA);
    emitExtendableSpace(node);
    if (node.stdGL) {
      emitType(node, GLSLLexer.NR_STDGL);
      emitExtendableSpace(node);
    }
    if (node.type == PragmaType.CUSTOM) {
      emitLiteral(node, node.customName);
    } else {
      emitType(node,
          node.type.tokenType,
          GLSLLexer.NR_LPAREN,
          node.state.tokenType,
          GLSLLexer.NR_RPAREN);
    }
    emitExactNewline(node);
    return null;
  }

  @Override
  public Void visitExtensionStatement(ExtensionStatement node) {
    emitType(node, GLSLLexer.NR, GLSLLexer.EXTENSION);
    emitExtendableSpace(node);
    emitLiteral(node, node.name);
    if (node.behavior != null) {
      emitType(node, GLSLLexer.NR_COLON);
      emitExtendableSpace(node);
      emitType(node, node.behavior.tokenType);
    }
    emitExactNewline(node);
    return null;
  }

  @Override
  public void exitLayoutDefaults(LayoutDefaults node) {
    emitType(node, node.mode.tokenType);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.SEMICOLON);
    emitCommonNewline(node);
  }

  @Override
  public Void visitConditionExpression(ConditionExpression node) {
    visit(node.getCondition());
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.QUERY_OP);
    emitExtendableSpace(node);
    visit(node.getTrueExpression());
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.COLON);
    emitExtendableSpace(node);
    visit(node.getFalseExpression());
    return null;
  }

  @Override
  public Void visitEmptyStatement(EmptyStatement node) {
    emitType(node, GLSLLexer.SEMICOLON);
    emitCommonNewline(node);
    return null;
  }

  @Override
  public void enterCompoundStatement(CompoundStatement node) {
    emitType(node, GLSLLexer.LBRACE);
    emitCommonNewline(node);
  }

  @Override
  public void exitCompoundStatement(CompoundStatement node) {
    emitType(node, GLSLLexer.RBRACE);
    emitCommonNewline(node);
  }

  @Override
  public Void visitIdentifier(Identifier node) {
    emitLiteral(node, node.name);
    return null;
  }
}
