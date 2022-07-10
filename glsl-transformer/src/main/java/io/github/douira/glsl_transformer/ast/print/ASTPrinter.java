package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.PragmaStatement.PragmaType;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.print.token.EOFToken;

/**
 * The AST printer emits tokens to convert an AST node into a string with the
 * help of a few other utility classes. Information encoded about the string
 * content and printed structure of each node is encoded in this printer.
 */
public abstract class ASTPrinter extends ASTPrinterBase {
  @Override
  public void exitTranslationUnit(TranslationUnit node) {
    emitToken(new EOFToken());
  }

  @Override
  public Void visitVersionStatement(VersionStatement node) {
    emitType(GLSLLexer.NR, GLSLLexer.VERSION);
    emitExtendableSpace();
    emitLiteral(Integer.toString(node.version));
    if (node.profile != null) {
      emitExtendableSpace();
      emitType(node.profile.tokenType);
    }
    emitExactNewline();
    return null;
  }

  @Override
  public Void visitEmptyDeclaration(EmptyDeclaration node) {
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitPragmaStatement(PragmaStatement node) {
    emitType(GLSLLexer.NR, GLSLLexer.PRAGMA);
    emitExtendableSpace();
    if (node.stdGL) {
      emitType(GLSLLexer.NR_STDGL);
      emitExtendableSpace();
    }
    if (node.type == PragmaType.CUSTOM) {
      emitLiteral(node.customName);
    } else {
      emitType(
          node.type.tokenType,
          GLSLLexer.NR_LPAREN,
          node.state.tokenType,
          GLSLLexer.NR_RPAREN);
    }
    emitExactNewline();
    return null;
  }

  @Override
  public Void visitExtensionStatement(ExtensionStatement node) {
    emitType(GLSLLexer.NR, GLSLLexer.EXTENSION);
    emitExtendableSpace();
    emitLiteral(node.name);
    if (node.behavior != null) {
      emitType(GLSLLexer.NR_COLON);
      emitExtendableSpace();
      emitType(node.behavior.tokenType);
    }
    emitExactNewline();
    return null;
  }

  @Override
  public void exitLayoutDefaults(LayoutDefaults node) {
    emitType(node.mode.tokenType);
    emitBreakableSpace();
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
  }

  @Override
  public void enterBitwiseNotExpression(BitwiseNotExpression node) {
    emitType(GLSLLexer.BIT_NEG_OP);
  }

  @Override
  public void enterBooleanNotExpression(BooleanNotExpression node) {
    emitType(GLSLLexer.BOOL_NOT_OP);
  }

  @Override
  public void enterDecrementPrefixExpression(DecrementPrefixExpression node) {
    emitType(GLSLLexer.MINUS_OP, GLSLLexer.MINUS_OP);
  }

  @Override
  public void enterGroupingExpression(GroupingExpression node) {
    emitType(GLSLLexer.LPAREN);
  }

  @Override
  public void exitGroupingExpression(GroupingExpression node) {
    emitType(GLSLLexer.RPAREN);
  }

  @Override
  public void enterIncrementPrefixExpression(IncrementPrefixExpression node) {
    emitType(GLSLLexer.PLUS_OP, GLSLLexer.PLUS_OP);
  }

  @Override
  public void enterNegationExpression(NegationExpression node) {
    emitType(GLSLLexer.MINUS_OP);
  }

  @Override
  public void enterIdentityExpression(IdentityExpression node) {
    emitType(GLSLLexer.PLUS_OP);
  }

  @Override
  public void exitDecrementPostfixExpression(DecrementPostfixExpression node) {
    emitType(GLSLLexer.MINUS_OP, GLSLLexer.MINUS_OP);
  }

  @Override
  public void exitIncrementPostfixExpression(IncrementPostfixExpression node) {
    emitType(GLSLLexer.PLUS_OP, GLSLLexer.PLUS_OP);
  }

  // FunctionCall expression is just a function call (no extra visit needed)

  @Override
  public Void visitMemberAccessExpression(MemberAccessExpression node) {
    visit(node.getOperand());
    emitType(GLSLLexer.DOT);
    visit(node.getMember());
    return null;
  }

  @Override
  public void exitLengthAccessExpression(LengthAccessExpression node) {
    emitType(GLSLLexer.DOT_LENGTH, GLSLLexer.LPAREN, GLSLLexer.RPAREN);
  }

  @Override
  public Void visitConditionExpression(ConditionExpression node) {
    visit(node.getCondition());
    emitBreakableSpace();
    emitType(GLSLLexer.QUERY_OP);
    emitExtendableSpace();
    visit(node.getTrueExpression());
    emitBreakableSpace();
    emitType(GLSLLexer.COLON);
    emitExtendableSpace();
    visit(node.getFalseExpression());
    return null;
  }

  @Override
  public Void visitSequenceExpression(SequenceExpression node) {
    visitWithSeparator(node.expressions, () -> {
      emitType(GLSLLexer.COMMA);
      emitBreakableSpace();
    });
    return null;
  }

  @Override
  public Void visitLiteralExpression(LiteralExpression node) {
    // literal expressions are always positive, negation is handled with a negation
    // expression
    switch (node.literalType.getNumberType()) {
      case BOOLEAN:
        emitLiteral(node.booleanValue ? "true" : "false");
        break;
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        int radix = node.getIntegerRadix();
        var intString = Long.toString(node.integerValue, radix);
        if (radix == 16) {
          intString = "0x" + intString;
        } else if (radix == 8) {
          intString = "0" + intString;
        }
        switch (node.literalType) {
          case INT16:
            emitLiteral(intString + "s");
            break;
          case UINT16:
            emitLiteral(intString + "us");
            break;
          case INT32:
            emitLiteral(intString);
            break;
          case UINT32:
            emitLiteral(intString + "u");
            break;
          case INT64:
            emitLiteral(intString + "l");
            break;
          case UINT64:
            emitLiteral(intString + "ul");
            break;
          default:
            throw new IllegalStateException("Unexpected int type: " + node.literalType);
        }
        break;
      case FLOATING_POINT:
        switch (node.literalType) {
          case FLOAT16:
            emitLiteral(Double.toString(node.floatingValue) + "hf");
            break;
          case FLOAT32:
            emitLiteral(Double.toString(node.floatingValue));
            break;
          case FLOAT64:
            emitLiteral(Double.toString(node.floatingValue) + "lf");
            break;
          default:
            throw new IllegalStateException("Unexpected float type: " + node.literalType);
        }
        break;
    }
    return null;
  }

  @Override
  public Void visitArrayAccessExpression(ArrayAccessExpression node) {
    visit(node.getLeft());
    emitType(GLSLLexer.LBRACKET);
    visit(node.getRight());
    emitType(GLSLLexer.RBRACKET);
    return null;
  }

  @Override
  public Void visitMultiplicationExpression(MultiplicationExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.TIMES_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitDivisionExpression(DivisionExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.DIV_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitModuloExpression(ModuloExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.MOD_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitAdditionExpression(AdditionExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.PLUS_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitSubtractionExpression(SubtractionExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.MINUS_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitLeftShiftExpression(LeftShiftExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.LEFT_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitRightShiftExpression(RightShiftExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.RIGHT_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitLessThanExpression(LessThanExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.LT_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitGreaterThanExpression(GreaterThanExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.GT_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitLessThanEqualExpression(LessThanEqualExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.LE_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitGreaterThanEqualExpression(GreaterThanEqualExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.GE_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitEqualityExpression(EqualityExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.EQ_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitInequalityExpression(InequalityExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.NE_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseAndExpression(BitwiseAndExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BIT_AND_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseXorExpression(BitwiseXorExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BIT_XOR_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseOrExpression(BitwiseOrExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BIT_OR_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBooleanAndExpression(BooleanAndExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BOOL_AND_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBooleanXorExpression(BooleanXorExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BOOL_XOR_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBooleanOrExpression(BooleanOrExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.BOOL_OR_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitAssignmentExpression(AssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.ASSIGN_OP);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitMultiplicationAssignmentExpression(MultiplicationAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.MUL_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitDivisionAssignmentExpression(DivisionAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.DIV_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitModuloAssignmentExpression(ModuloAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.MOD_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitAdditionAssignmentExpression(AdditionAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.ADD_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitSubtractionAssignmentExpression(SubtractionAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.SUB_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitLeftShiftAssignmentExpression(LeftShiftAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.LEFT_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitRightShiftAssignmentExpression(RightShiftAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.RIGHT_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseAndAssignmentExpression(BitwiseAndAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.AND_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseXorAssignmentExpression(BitwiseXorAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.XOR_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitBitwiseOrAssignmentExpression(BitwiseOrAssignmentExpression node) {
    visit(node.getLeft());
    emitBreakableSpace();
    emitType(GLSLLexer.OR_ASSIGN);
    emitBreakableSpace();
    visit(node.getRight());
    return null;
  }

  @Override
  public Void visitEmptyStatement(EmptyStatement node) {
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  /**
   * ANTLR grammar rule:
   * compoundStatement: LBRACE statement* RBRACE;
   */
  @Override
  public void enterCompoundStatement(CompoundStatement node) {
    emitType(GLSLLexer.LBRACE);
    emitCommonNewline();
    indent();
    if (node.getParent() instanceof SwitchStatement) {
      indent();
    }
  }

  @Override
  public void exitCompoundStatement(CompoundStatement node) {
    unindent();
    if (node.getParent() instanceof SwitchStatement) {
      unindent();
    }
    emitType(GLSLLexer.RBRACE);
    emitCommonNewline();
  }

  @Override
  public void exitDeclarationStatement(DeclarationStatement node) {
    emitCommonNewline();
  }

  @Override
  public void exitExpressionStatement(ExpressionStatement node) {
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
  }

  /**
   * ANTLR grammar rule:
   * selectionStatement:
   * attribute? IF LPAREN condition = expression RPAREN ifTrue = statement (
   * ELSE ifFalse = statement
   * )?;
   */
  @Override
  public Void visitSelectionStatement(SelectionStatement node) {
    for (int i = 0, size = node.statements.size(); i < size; i++) {
      compactCommonNewline(CompoundStatement.class);
      var condition = node.conditions.get(i);
      if (i > 0) {
        emitType(GLSLLexer.ELSE);
        if (condition != null) {
          emitExactSpace();
        }
      }
      if (condition != null) {
        emitType(GLSLLexer.IF);
        emitExtendableSpace();
        emitType(GLSLLexer.LPAREN);
        visit(node.conditions.get(i));
        emitType(GLSLLexer.RPAREN);
      }
      emitBreakableSpace();
      visit(node.statements.get(i));
    }
    return null;
  }

  /**
   * ANTLR grammar rule:
   * switchStatement:
   * attribute? SWITCH LPAREN condition = expression RPAREN compoundStatement;
   */
  @Override
  public Void visitSwitchStatement(SwitchStatement node) {
    emitType(GLSLLexer.SWITCH);
    emitExtendableSpace();
    emitType(GLSLLexer.LPAREN);
    visit(node.getExpression());
    emitType(GLSLLexer.RPAREN);
    emitBreakableSpace();
    visit(node.getStatement());
    return null;
  }

  @Override
  public void enterCaseLabelStatement(CaseLabelStatement node) {
    unindent();
  }

  @Override
  public void exitCaseLabelStatement(CaseLabelStatement node) {
    indent();
  }

  @Override
  public Void visitCaseStatement(CaseStatement node) {
    emitType(GLSLLexer.CASE);
    emitBreakableSpace();
    visit(node.getExpression());
    emitType(GLSLLexer.COLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitDefaultStatement(DefaultStatement node) {
    emitType(GLSLLexer.DEFAULT, GLSLLexer.COLON);
    emitCommonNewline();
    return null;
  }

  /**
   * ANTLR grammar rule:
   * * iterationCondition:
   * expression
   * | fullySpecifiedType IDENTIFIER ASSIGN_OP initializer;
   * 
   * forStatement:
   * attribute? FOR LPAREN (
   * emptyStatement
   * | expressionStatement
   * | declarationStatement
   * ) condition = iterationCondition? SEMICOLON incrementer = expression? RPAREN
   * loopBody = statement;
   * 
   */
  @Override
  public Void visitForLoopStatement(ForLoopStatement node) {
    emitType(GLSLLexer.FOR);
    emitBreakableSpace();
    emitType(GLSLLexer.LPAREN);
    if (!visitSafe(node.getInitExpression())) {
      if (!visitSafe(node.getInitDeclaration())) {
        emitExactSpace();
      }
    }
    emitType(GLSLLexer.SEMICOLON);
    emitBreakableSpace();
    if (!visitSafe(node.getCondition())) {
      visitSafe(node.getIterationConditionInitializer());
    }
    emitType(GLSLLexer.SEMICOLON);
    emitBreakableSpace();
    visitSafe(node.getIncrementer());
    emitType(GLSLLexer.RPAREN);
    emitBreakableSpace();
    visit(node.getStatement());
    return null;
  }

  /**
   * ANTLR grammar rules:
   * whileStatement:
   * attribute? WHILE LPAREN condition = iterationCondition RPAREN loopBody =
   * statement;
   */
  @Override
  public Void visitWhileLoopStatement(WhileLoopStatement node) {
    emitType(GLSLLexer.WHILE);
    emitBreakableSpace();
    emitType(GLSLLexer.LPAREN);
    if (!visitSafe(node.getCondition())) {
      visitSafe(node.getIterationConditionInitializer());
    }
    emitType(GLSLLexer.RPAREN);
    emitBreakableSpace();
    visit(node.getStatement());
    return null;
  }

  /**
   * ANTLR grammar rule:
   * doWhileStatement:
   * attribute? DO loopBody = statement WHILE LPAREN condition = expression RPAREN
   * SEMICOLON;
   */
  @Override
  public Void visitDoWhileLoopStatement(DoWhileLoopStatement node) {
    emitType(GLSLLexer.DO);
    emitBreakableSpace();
    visit(node.getStatement());
    compactCommonNewline(CompoundStatement.class);
    emitType(GLSLLexer.WHILE);
    emitBreakableSpace();
    emitType(GLSLLexer.LPAREN);
    visit(node.getCondition());
    emitType(GLSLLexer.RPAREN);
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitContinueStatement(ContinueStatement node) {
    emitType(GLSLLexer.CONTINUE, GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitBreakStatement(BreakStatement node) {
    emitType(GLSLLexer.BREAK, GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitReturnStatement(ReturnStatement node) {
    emitType(GLSLLexer.RETURN);
    if (node.getExpression() != null) {
      emitBreakableSpace();
      visit(node.getExpression());
    }
    emitType(GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitDiscardStatement(DiscardStatement node) {
    emitType(GLSLLexer.DISCARD, GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitDemoteStatement(DemoteStatement node) {
    emitType(GLSLLexer.DEMOTE, GLSLLexer.SEMICOLON);
    emitCommonNewline();
    return null;
  }

  @Override
  public Void visitDeclaration(Declaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitDeclarationMember(DeclarationMember node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitFullTypeParameter(FullTypeParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitFunctionDeclaration(FunctionDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitFunctionParameter(FunctionParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitInterfaceBlockDeclaration(InterfaceBlockDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitNamedParameter(NamedParameter node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitPrecisionDeclaration(PrecisionDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitTypeAndInitDeclaration(TypeAndInitDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitVariableDeclaration(VariableDeclaration node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitExpressionInitializer(ExpressionInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitInitializer(Initializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitNestedInitializer(NestedInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitInterpolationQualifier(InterpolationQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitInvariantQualifier(InvariantQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitLayoutQualifier(LayoutQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitLayoutQualifierPart(LayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitNamedLayoutQualifierPart(NamedLayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitPreciseQualifier(PreciseQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitPrecisionQualifier(PrecisionQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitSharedLayoutQualifierPart(SharedLayoutQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitStorageQualifier(StorageQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitTypeQualifier(TypeQualifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitTypeQualifierPart(TypeQualifierPart node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitArraySpecifier(ArraySpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitBuiltinFixedTypeSpecifier(BuiltinFixedTypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitBuiltinNumericTypeSpecifier(BuiltinNumericTypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitTypeReference(TypeReference node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitTypeSpecifier(TypeSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitStructBody(StructBody node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitStructDeclarator(StructDeclarator node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitStructMember(StructMember node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitStructSpecifier(StructSpecifier node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitFullySpecifiedType(FullySpecifiedType node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitIterationConditionInitializer(IterationConditionInitializer node) {
    throw new UnsupportedOperationException(); // TODO
  }

  @Override
  public Void visitIdentifier(Identifier node) {
    emitLiteral(node.name);
    return null;
  }
}
