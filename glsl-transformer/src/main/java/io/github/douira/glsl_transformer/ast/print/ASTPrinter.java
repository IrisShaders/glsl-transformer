package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
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
  public void enterBitwiseNotExpression(BitwiseNotExpression node) {
    emitType(node, GLSLLexer.BNEG_OP);
  }

  @Override
  public void enterBooleanNotExpression(BooleanNotExpression node) {
    emitType(node, GLSLLexer.NOT_OP);
  }

  @Override
  public void enterDecrementPrefixExpression(DecrementPrefixExpression node) {
    emitType(node, GLSLLexer.MINUS_OP, GLSLLexer.MINUS_OP);
  }

  @Override
  public void enterGroupingExpression(GroupingExpression node) {
    emitType(node, GLSLLexer.LPAREN);
  }

  @Override
  public void exitGroupingExpression(GroupingExpression node) {
    emitType(node, GLSLLexer.RPAREN);
  }

  @Override
  public void enterIncrementPrefixExpression(IncrementPrefixExpression node) {
    emitType(node, GLSLLexer.PLUS_OP, GLSLLexer.PLUS_OP);
  }

  @Override
  public void enterNegationExpression(NegationExpression node) {
    emitType(node, GLSLLexer.MINUS_OP);
  }

  @Override
  public void enterIdentityExpression(IdentityExpression node) {
    emitType(node, GLSLLexer.PLUS_OP);
  }

  @Override
  public void exitDecrementPostfixExpression(DecrementPostfixExpression node) {
    emitType(node, GLSLLexer.MINUS_OP, GLSLLexer.MINUS_OP);
  }

  @Override
  public void exitIncrementPostfixExpression(IncrementPostfixExpression node) {
    emitType(node, GLSLLexer.PLUS_OP, GLSLLexer.PLUS_OP);
  }

  // FunctionCall expression is just a function call (no extra visit needed)

  @Override
  public void exitMemberAccessExpression(MemberAccessExpression node) {
    emitType(node, GLSLLexer.DOT);
    emitLiteral(node, node.memberName);
  }

  @Override
  public Void visitLengthAccessExpression(LengthAccessExpression node) {
    visit(node.operand);
    emitType(node, GLSLLexer.DOT, GLSLLexer.LENGTH, GLSLLexer.LPAREN, GLSLLexer.RPAREN);
    return null;
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
  public Void visitSequenceExpression(SequenceExpression node) {
    for (int i = 0, size = node.expressions.size(); i < size; i++) {
      visit(node.expressions.get(i));
      if (i < size - 1) {
        emitType(node, GLSLLexer.COMMA);
        emitBreakableSpace(node);
      }
    }
    return null;
  }

  @Override
  public Void visitLiteralExpression(LiteralExpression node) {
    switch (node.literalType) {
      case BOOL:
        emitLiteral(node, node.booleanValue ? "true" : "false");
        break;
      case INT16:
        emitLiteral(node, Long.toString(node.integerValue) + "s");
        break;
      case UINT16:
        emitLiteral(node, Long.toString(node.integerValue) + "us");
        break;
      case INT32:
        emitLiteral(node, Long.toString(node.integerValue));
        break;
      case UINT32:
        emitLiteral(node, Long.toString(node.integerValue) + "u");
        break;
      case INT64:
        emitLiteral(node, Long.toString(node.integerValue) + "l");
        break;
      case UINT64:
        emitLiteral(node, Long.toString(node.integerValue) + "ul");
        break;
      case FLOAT16:
        emitLiteral(node, Double.toString(node.floatingValue) + "hf");
        break;
      case FLOAT32:
        emitLiteral(node, Double.toString(node.floatingValue));
        break;
      case FLOAT64:
        emitLiteral(node, Double.toString(node.floatingValue) + "lf");
        break;
      default:
        throw new IllegalStateException("Unexpected literal type: " + node.literalType);
    }
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
