package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
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
    emitType(node, GLSLLexer.BIT_NEG_OP);
  }

  @Override
  public void enterBooleanNotExpression(BooleanNotExpression node) {
    emitType(node, GLSLLexer.BOOL_NOT_OP);
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
    emitType(node, GLSLLexer.DOT, GLSLLexer.DOT_LENGTH, GLSLLexer.LPAREN, GLSLLexer.RPAREN);
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
  public Void visitArrayAccessExpression(ArrayAccessExpression node) {
    visit(node.left);
    emitType(node, GLSLLexer.LBRACKET);
    visit(node.right);
    emitType(node, GLSLLexer.RBRACKET);
    return null;
  }

  @Override
  public Void visitMultiplicationExpression(MultiplicationExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.TIMES_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitDivisionExpression(DivisionExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.DIV_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitModuloExpression(ModuloExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.MOD_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitAdditionExpression(AdditionExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.PLUS_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitSubtractionExpression(SubtractionExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.MINUS_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitLeftShiftExpression(LeftShiftExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.LEFT_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitRightShiftExpression(RightShiftExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.RIGHT_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitLessThanExpression(LessThanExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.LT_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitGreaterThanExpression(GreaterThanExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.GT_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitLessThanEqualExpression(LessThanEqualExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.LE_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.GE_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitEqualityExpression(EqualityExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.EQ_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitInequalityExpression(InequalityExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.NE_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseAndExpression(BitwiseAndExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BIT_AND_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseXorExpression(BitwiseXorExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BIT_XOR_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseOrExpression(BitwiseOrExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BIT_OR_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBooleanAndExpression(BooleanAndExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BOOL_AND_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBooleanXorExpression(BooleanXorExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BOOL_XOR_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBooleanOrExpression(BooleanOrExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.BOOL_OR_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitAssignmentExpression(AssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.ASSIGN_OP);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.MUL_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.DIV_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.MOD_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.ADD_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.SUB_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.LEFT_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.RIGHT_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.AND_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.XOR_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
    return null;
  }

  @Override
  public Void visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    visit(node.left);
    emitBreakableSpace(node);
    emitType(node, GLSLLexer.OR_ASSIGN);
    emitBreakableSpace(node);
    visit(node.right);
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
