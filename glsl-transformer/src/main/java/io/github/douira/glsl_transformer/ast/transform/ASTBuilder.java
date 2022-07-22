package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.VersionStatement.Profile;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression.IntegerFormat;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionStatement.ExtensionBehavior;
import io.github.douira.glsl_transformer.ast.node.external_declaration.LayoutDefaults.LayoutMode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.PragmaStatement.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.InterpolationQualifier.InterpolationType;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.PrecisionQualifier.PrecisionLevel;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.BuiltinFixedTypeSpecifier.BuiltinType;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.util.Type;

/**
 * The AST builder is a visitor of the parse tree (not an AST visitor) that
 * generates AST nodes from parse tree nodes. Sometimes more or fewer nodes are
 * generated than are in the parse tree depending on how the parse tree is
 * nested and how fine-grained the AST nodes have to be. Information about the
 * relationship between a parse tree and an AST is encoded in this visitor.
 */
public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  public static ASTNode build(ParseTree ctx) {
    return Root.indexNodes(() -> doBuild(ctx));
  }

  public static ASTNode build(Root rootInstance, ParseTree ctx) {
    return Root.indexNodes(rootInstance, () -> doBuild(ctx));
  }

  public static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType build(
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return Root.indexNodes(() -> buildWith(ctx, visitMethod));
  }

  public static ASTNode buildSubtreeFor(ASTNode parentTreeMember, ParseTree ctx) {
    return Root.indexNodes(parentTreeMember, () -> doBuild(ctx));
  }

  public static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType buildSubtreeWith(
      ASTNode parentTreeMember,
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return Root.indexNodes(parentTreeMember, () -> buildWith(ctx, visitMethod));
  }

  private static ASTNode doBuild(ParseTree ctx) {
    return new ASTBuilder().visit(ctx);
  }

  private static <TreeType extends ParseTree, ReturnType extends ASTNode> ReturnType buildWith(
      TreeType ctx,
      BiFunction<ASTBuilder, TreeType, ReturnType> visitMethod) {
    return visitMethod.apply(new ASTBuilder(), ctx);
  }

  private static <N, R> R applySafe(N ctx, Function<N, R> visitMethod) {
    return ctx == null ? null : visitMethod.apply(ctx);
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration()
        .stream().map(this::visitExternalDeclaration);
    return versionStatement == null
        ? new TranslationUnit(externalDeclarations)
        : new TranslationUnit(versionStatement, externalDeclarations);
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    if (ctx == null) {
      return null;
    }
    var version = Integer.parseInt(ctx.version.getText());
    return new VersionStatement(version, applySafe(ctx.profile, Profile::fromToken));
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public PragmaStatement visitPragmaStatement(PragmaStatementContext ctx) {
    var stdGL = ctx.stdGL != null;
    var type = PragmaType.fromToken(ctx.type);
    return type == PragmaType.CUSTOM
        ? new PragmaStatement(stdGL, ctx.type.getText())
        : new PragmaStatement(stdGL, type, PragmaState.fromToken(ctx.state));
  }

  @Override
  public ExtensionStatement visitExtensionStatement(ExtensionStatementContext ctx) {
    var extensionName = ctx.extensionName.getText();
    return new ExtensionStatement(
        extensionName, applySafe(ctx.extensionBehavior, ExtensionBehavior::fromToken));
  }

  @Override
  public LayoutDefaults visitLayoutDefaults(LayoutDefaultsContext ctx) {
    return new LayoutDefaults(
        visitLayoutQualifier(ctx.layoutQualifier()),
        LayoutMode.fromToken(ctx.layoutMode));
  }

  @Override
  public ConditionExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
    return new ConditionExpression(
        visitExpression(ctx.condition),
        visitExpression(ctx.trueAlternative),
        visitExpression(ctx.falseAlternative));
  }

  @Override
  public FunctionCallExpression visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
    var functionIdentifier = ctx.IDENTIFIER();
    Identifier functionName = null;
    TypeSpecifier functionType = null;
    if (functionIdentifier != null) {
      functionName = visitIdentifier(functionIdentifier);
    } else {
      functionType = visitTypeSpecifier(ctx.typeSpecifier());
    }

    var parameters = ctx.parameters.stream().map(this::visitExpression);
    return functionName != null
        ? new FunctionCallExpression(functionName, parameters)
        : new FunctionCallExpression(functionType, parameters);
  }

  @Override
  public GroupingExpression visitGroupingExpression(GroupingExpressionContext ctx) {
    return new GroupingExpression(visitExpression(ctx.value));
  }

  @Override
  public MemberAccessExpression visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    return new MemberAccessExpression(
        visitExpression(ctx.operand),
        new Identifier(ctx.member));
  }

  @Override
  public LengthAccessExpression visitLengthAccessExpression(LengthAccessExpressionContext ctx) {
    return new LengthAccessExpression(visitExpression(ctx.operand));
  }

  @Override
  public UnaryExpression visitPostfixExpression(PostfixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLParser.INC_OP:
        return new IncrementPostfixExpression(operand);
      case GLSLParser.DEC_OP:
        return new DecrementPostfixExpression(operand);
      default:
        throw new IllegalArgumentException("Unknown postfix operator: " + ctx.op.getText());
    }
  }

  @Override
  public UnaryExpression visitPrefixExpression(PrefixExpressionContext ctx) {
    var operand = visitExpression(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLLexer.INC_OP:
        return new IncrementPrefixExpression(operand);
      case GLSLLexer.DEC_OP:
        return new DecrementPrefixExpression(operand);
      case GLSLLexer.PLUS_OP:
        return new IdentityExpression(operand);
      case GLSLLexer.MINUS_OP:
        return new NegationExpression(operand);
      case GLSLLexer.BOOL_NOT_OP:
        return new BooleanNotExpression(operand);
      case GLSLLexer.BIT_NEG_OP:
        return new BitwiseNotExpression(operand);
      default:
        throw new IllegalStateException("Unexpected prefix operator type" + ctx.op.getText());
    }
  }

  @Override
  public SequenceExpression visitSequenceExpression(SequenceExpressionContext ctx) {
    // SequenceExpressions in the parse tree are nested in the left operand
    ExpressionContext left = ctx;
    var expressions = new ArrayList<Expression>();

    // collect the nested sequence expressions
    do {
      var sequence = (SequenceExpressionContext) left;
      if (sequence.right instanceof SequenceExpressionContext) {
        throw new IllegalStateException("Sequence expressions should not be nested on the right operand!");
      }
      var right = visitExpression(sequence.right);
      expressions.add(right);

      left = sequence.left;
    } while (left instanceof SequenceExpressionContext);

    expressions.add(visitExpression(left));
    Collections.reverse(expressions);

    // converting to stream and back is fine
    // since the child list has to copy anyways
    return new SequenceExpression(expressions.stream());
  }

  private static final Pattern intExtractor = Pattern.compile(
      "(.*?)(?:us|ul|u|s)?$", Pattern.CASE_INSENSITIVE);
  private static final Pattern floatExtractor = Pattern.compile(
      "(.*?)(?:f|hf|lf)?$", Pattern.CASE_INSENSITIVE);

  @Override
  public LiteralExpression visitLiteralExpression(LiteralExpressionContext ctx) {
    // start and end token are the same as there is one token in this rule
    var content = ctx.getStart();
    var literalType = Type.ofLiteralTokenType(content.getType());
    var tokenContent = content.getText();
    switch (literalType.getNumberType()) {
      case BOOLEAN:
        return new LiteralExpression(
            literalType, tokenContent.equals("true"));
      case SIGNED_INTEGER:
      case UNSIGNED_INTEGER:
        var intMatcher = intExtractor.matcher(tokenContent);
        intMatcher.matches();
        tokenContent = intMatcher.group(1);
        if (tokenContent.equals("0")) {
          return new LiteralExpression(
              literalType, 0);
        }
        if (tokenContent.startsWith("0x")) {
          return new LiteralExpression(
              literalType,
              Long.parseLong(tokenContent.substring(2), 16),
              IntegerFormat.HEXADECIMAL);
        } else if (tokenContent.startsWith("0")) {
          return new LiteralExpression(
              literalType,
              Long.parseLong(tokenContent.substring(1), 8),
              IntegerFormat.OCTAL);
        } else {
          return new LiteralExpression(
              literalType,
              Long.parseLong(tokenContent, 10), IntegerFormat.DECIMAL);
        }
      case FLOATING_POINT:
        var floatMatcher = floatExtractor.matcher(tokenContent);
        floatMatcher.matches();
        tokenContent = floatMatcher.group(1);
        return new LiteralExpression(
            literalType, Double.parseDouble(tokenContent));
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + literalType);
    }
  }

  @Override
  public BinaryExpression visitAdditiveExpression(AdditiveExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.PLUS_OP:
        return new AdditionExpression(left, right);
      case GLSLLexer.MINUS_OP:
        return new SubtractionExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown additive operator: " + ctx.op.getText());
    }
  }

  @Override
  public ArrayAccessExpression visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {
    return new ArrayAccessExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitAssignmentExpression(AssignmentExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.ASSIGN_OP:
        return new AssignmentExpression(left, right);
      case GLSLLexer.MUL_ASSIGN:
        return new MultiplicationAssignmentExpression(left, right);
      case GLSLLexer.DIV_ASSIGN:
        return new DivisionAssignmentExpression(left, right);
      case GLSLLexer.MOD_ASSIGN:
        return new ModuloAssignmentExpression(left, right);
      case GLSLLexer.ADD_ASSIGN:
        return new AdditionAssignmentExpression(left, right);
      case GLSLLexer.SUB_ASSIGN:
        return new SubtractionAssignmentExpression(left, right);
      case GLSLLexer.AND_ASSIGN:
        return new BitwiseAndAssignmentExpression(left, right);
      case GLSLLexer.XOR_ASSIGN:
        return new BitwiseXorAssignmentExpression(left, right);
      case GLSLLexer.OR_ASSIGN:
        return new BitwiseOrAssignmentExpression(left, right);
      case GLSLLexer.LEFT_ASSIGN:
        return new LeftShiftAssignmentExpression(left, right);
      case GLSLLexer.RIGHT_ASSIGN:
        return new RightShiftAssignmentExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown assignment operator: " + ctx.op.getText());
    }
  }

  @Override
  public BitwiseAndExpression visitBitwiseAndExpression(BitwiseAndExpressionContext ctx) {
    return new BitwiseAndExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BitwiseXorExpression visitBitwiseExclusiveOrExpression(BitwiseExclusiveOrExpressionContext ctx) {
    return new BitwiseXorExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BitwiseOrExpression visitBitwiseInclusiveOrExpression(BitwiseInclusiveOrExpressionContext ctx) {
    return new BitwiseOrExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitEqualityExpression(EqualityExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.EQ_OP:
        return new EqualityExpression(left, right);
      case GLSLLexer.NE_OP:
        return new InequalityExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown equality operator: " + ctx.op.getText());
    }
  }

  @Override
  public BooleanAndExpression visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
    return new BooleanAndExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BooleanXorExpression visitLogicalExclusiveOrExpression(LogicalExclusiveOrExpressionContext ctx) {
    return new BooleanXorExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BooleanOrExpression visitLogicalInclusiveOrExpression(LogicalInclusiveOrExpressionContext ctx) {
    return new BooleanOrExpression(
        visitExpression(ctx.left),
        visitExpression(ctx.right));
  }

  @Override
  public BinaryExpression visitRelationalExpression(RelationalExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.LT_OP:
        return new LessThanExpression(left, right);
      case GLSLLexer.GT_OP:
        return new GreaterThanExpression(left, right);
      case GLSLLexer.LE_OP:
        return new LessThanEqualExpression(left, right);
      case GLSLLexer.GE_OP:
        return new GreaterThanEqualExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown relational operator: " + ctx.op.getText());
    }
  }

  @Override
  public BinaryExpression visitShiftExpression(ShiftExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.LEFT_OP:
        return new LeftShiftExpression(left, right);
      case GLSLLexer.RIGHT_OP:
        return new RightShiftExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown shift operator: " + ctx.op.getText());
    }
  }

  @Override
  public BinaryExpression visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
    var left = visitExpression(ctx.left);
    var right = visitExpression(ctx.right);
    switch (ctx.op.getType()) {
      case GLSLLexer.TIMES_OP:
        return new MultiplicationExpression(left, right);
      case GLSLLexer.DIV_OP:
        return new DivisionExpression(left, right);
      case GLSLLexer.MOD_OP:
        return new ModuloExpression(left, right);
      default:
        throw new IllegalArgumentException("Unknown multiplicative operator: " + ctx.op.getText());
    }
  }

  @Override
  public ReferenceExpression visitReferenceExpression(ReferenceExpressionContext ctx) {
    return new ReferenceExpression(visitIdentifier(ctx.IDENTIFIER()));
  }

  @Override
  public CompoundStatement visitCompoundStatement(CompoundStatementContext ctx) {
    return new CompoundStatement(ctx.statement().stream().map(this::visitStatement));
  }

  @Override
  public ContinueStatement visitContinueStatement(ContinueStatementContext ctx) {
    return new ContinueStatement();
  }

  @Override
  public BreakStatement visitBreakStatement(BreakStatementContext ctx) {
    return new BreakStatement();
  }

  @Override
  public ReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
    return new ReturnStatement(applySafe(ctx.expression(), this::visitExpression));
  }

  @Override
  public DiscardStatement visitDiscardStatement(DiscardStatementContext ctx) {
    return new DiscardStatement();
  }

  @Override
  public DemoteStatement visitDemoteStatement(DemoteStatementContext ctx) {
    return new DemoteStatement();
  }

  @Override
  public DeclarationStatement visitDeclarationStatement(DeclarationStatementContext ctx) {
    return new DeclarationStatement(visitDeclaration(ctx.declaration()));
  }

  @Override
  public ExpressionStatement visitExpressionStatement(ExpressionStatementContext ctx) {
    return new ExpressionStatement(visitExpression(ctx.expression()));
  }

  @Override
  public EmptyStatement visitEmptyStatement(EmptyStatementContext ctx) {
    return new EmptyStatement();
  }

  @Override
  public SelectionStatement visitSelectionStatement(SelectionStatementContext ctx) {
    // unwrap the nested selection statements that are created through "else if"
    // chains
    var conditions = Stream.<Expression>builder();
    var statements = Stream.<Statement>builder();
    SelectionStatementContext nextSelection = ctx;
    do {
      conditions.add(visitExpression(nextSelection.condition));
      statements.add(visitStatement(nextSelection.ifTrue));
      var ifFalse = nextSelection.ifFalse;
      nextSelection = null;
      if (ifFalse != null) {
        var nestedSelectionStatement = ifFalse.selectionStatement();
        if (nestedSelectionStatement != null) {
          nextSelection = nestedSelectionStatement;
        } else {
          // add a regular else branch, has no control flow attribute
          // since they are only present on the whole selection statement
          conditions.add(null);
          statements.add(visitStatement(ifFalse));
        }
      }
    } while (nextSelection != null);
    return new SelectionStatement(conditions.build(), statements.build());
  }

  @Override
  public SwitchStatement visitSwitchStatement(SwitchStatementContext ctx) {
    return new SwitchStatement(
        visitExpression(ctx.condition),
        visitCompoundStatement(ctx.compoundStatement()));
  }

  @Override
  public DefaultStatement visitDefaultCaseLabel(DefaultCaseLabelContext ctx) {
    return new DefaultStatement();
  }

  @Override
  public CaseStatement visitValuedCaseLabel(ValuedCaseLabelContext ctx) {
    return new CaseStatement(visitExpression(ctx.expression()));
  }

  @Override
  public ForLoopStatement visitForStatement(ForStatementContext ctx) {
    Expression initExpression = null;
    Declaration initDeclaration = null;

    var initExpressionStatement = ctx.expressionStatement();
    if (initExpressionStatement != null) {
      initExpression = visitExpression(initExpressionStatement.expression());
    } else {
      var initDeclarationStatement = ctx.declarationStatement();
      if (initDeclarationStatement != null) {
        initDeclaration = visitDeclaration(initDeclarationStatement.declaration());
      }
    }

    return new ForLoopStatement(
        initExpression,
        initDeclaration,
        applySafe(ctx.condition, this::visitExpression),
        applySafe(ctx.initCondition, this::visitIterationCondition),
        applySafe(ctx.incrementer, this::visitExpression),
        visitStatement(ctx.statement()));
  }

  @Override
  public WhileLoopStatement visitWhileStatement(WhileStatementContext ctx) {
    return ctx.condition != null
        ? new WhileLoopStatement(
            visitExpression(ctx.condition),
            visitStatement(ctx.loopBody))
        : new WhileLoopStatement(
            visitIterationCondition(ctx.initCondition),
            visitStatement(ctx.loopBody));
  }

  @Override
  public DoWhileLoopStatement visitDoWhileStatement(DoWhileStatementContext ctx) {
    return new DoWhileLoopStatement(
        visitStatement(ctx.loopBody),
        visitExpression(ctx.condition));
  }

  @Override
  public IterationConditionInitializer visitIterationCondition(IterationConditionContext ctx) {
    return new IterationConditionInitializer(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        new Identifier(ctx.name),
        visitInitializer(ctx.initializer()));
  }

  @Override
  public ArraySpecifier visitArraySpecifier(ArraySpecifierContext ctx) {
    return new ArraySpecifier(ctx.arraySpecifierSegment().stream()
        .<Expression>map(child -> applySafe(child.expression(), this::visitExpression)));
  }

  @Override
  public FunctionDefinition visitFunctionDefinition(FunctionDefinitionContext ctx) {
    return new FunctionDefinition(
        visitFunctionPrototype(ctx.functionPrototype()),
        visitCompoundStatement(ctx.compoundStatement()));
  }

  @Override
  public FunctionPrototype visitFunctionPrototype(FunctionPrototypeContext ctx) {
    var returnType = visitFullySpecifiedType(ctx.fullySpecifiedType());
    var name = visitIdentifier(ctx.IDENTIFIER());
    return new FunctionPrototype(returnType, name,
        applySafe(ctx.functionParameterList().parameters,
            parameters -> parameters.stream().map(this::visitParameterDeclaration)));
  }

  @Override
  public DeclarationMember visitDeclarationMember(DeclarationMemberContext ctx) {
    var arraySpecifier = ctx.arraySpecifier();
    var name = visitIdentifier(ctx.IDENTIFIER());
    var initializer = ctx.initializer();
    return new DeclarationMember(
        name,
        applySafe(arraySpecifier, this::visitArraySpecifier),
        applySafe(initializer, this::visitInitializer));
  }

  @Override
  public FullySpecifiedType visitFullySpecifiedType(FullySpecifiedTypeContext ctx) {
    return new FullySpecifiedType(
        applySafe(ctx.typeQualifier(), this::visitTypeQualifier),
        visitTypeSpecifier(ctx.typeSpecifier()));
  }

  @Override
  public FunctionParameter visitParameterDeclaration(ParameterDeclarationContext ctx) {
    return new FunctionParameter(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        applySafe(ctx.parameterName, Identifier::new),
        applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
  }

  @Override
  public FunctionDeclaration visitFunctionDeclaration(FunctionDeclarationContext ctx) {
    return new FunctionDeclaration(visitFunctionPrototype(ctx.functionPrototype()));
  }

  @Override
  public TypeAndInitDeclaration visitTypeAndInitDeclaration(TypeAndInitDeclarationContext ctx) {
    return new TypeAndInitDeclaration(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        ctx.declarationMembers.stream().map(this::visitDeclarationMember));
  }

  @Override
  public PrecisionDeclaration visitPrecisionDeclaration(PrecisionDeclarationContext ctx) {
    return new PrecisionDeclaration(
        visitPrecisionQualifier(ctx.precisionQualifier()),
        visitTypeSpecifier(ctx.typeSpecifier()));
  }

  @Override
  public InterfaceBlockDeclaration visitInterfaceBlockDeclaration(InterfaceBlockDeclarationContext ctx) {
    var typeQualifier = visitTypeQualifier(ctx.typeQualifier());
    var name = new Identifier(ctx.blockName);
    var structBody = visitStructBody(ctx.structBody());
    if (ctx.variableName != null) {
      var variableName = new Identifier(ctx.variableName);
      var arraySpecifierContext = ctx.arraySpecifier();
      if (arraySpecifierContext != null) {
        var arraySpecifier = visitArraySpecifier(arraySpecifierContext);
        return new InterfaceBlockDeclaration(
            typeQualifier, name, structBody, variableName, arraySpecifier);
      } else {
        return new InterfaceBlockDeclaration(typeQualifier, name, structBody, variableName);
      }
    }
    return new InterfaceBlockDeclaration(typeQualifier, name, structBody);
  }

  @Override
  public VariableDeclaration visitVariableDeclaration(VariableDeclarationContext ctx) {
    return new VariableDeclaration(
        visitTypeQualifier(ctx.typeQualifier()),
        ctx.variableNames.stream().map(Identifier::new));
  }

  @Override
  public Initializer visitInitializer(InitializerContext ctx) {
    var expressionContext = ctx.expression();
    if (expressionContext != null) {
      return new ExpressionInitializer(visitExpression(expressionContext));
    }
    var initializers = ctx.initializers;
    return initializers == null
        ? new NestedInitializer()
        : new NestedInitializer(initializers.stream().map(this::visitInitializer));

  }

  @Override
  public NamedLayoutQualifierPart visitNamedLayoutQualifier(NamedLayoutQualifierContext ctx) {
    return new NamedLayoutQualifierPart(
        new Identifier(ctx.getStart()),
        applySafe(ctx.expression(), this::visitExpression));
  }

  @Override
  public SharedLayoutQualifierPart visitSharedLayoutQualifier(SharedLayoutQualifierContext ctx) {
    return new SharedLayoutQualifierPart();
  }

  public LayoutQualifierPart visitLayoutQualifierPart(LayoutQualifierIdContext ctx) {
    return (LayoutQualifierPart) visit(ctx);
  }

  @Override
  public LayoutQualifier visitLayoutQualifier(LayoutQualifierContext ctx) {
    return new LayoutQualifier(
        ctx.layoutQualifiers.stream().map(this::visitLayoutQualifierPart));
  }

  @Override
  public PreciseQualifier visitPreciseQualifier(PreciseQualifierContext ctx) {
    return new PreciseQualifier();
  }

  @Override
  public InvariantQualifier visitInvariantQualifier(InvariantQualifierContext ctx) {
    return new InvariantQualifier();
  }

  @Override
  public InterpolationQualifier visitInterpolationQualifier(InterpolationQualifierContext ctx) {
    return new InterpolationQualifier(InterpolationType.fromToken(ctx.getStart()));
  }

  @Override
  public PrecisionQualifier visitPrecisionQualifier(PrecisionQualifierContext ctx) {
    return new PrecisionQualifier(PrecisionLevel.fromToken(ctx.getStart()));
  }

  @Override
  public ASTNode visitStorageQualifier(StorageQualifierContext ctx) {
    return ctx.typeNames.isEmpty()
        ? new StorageQualifier(StorageType.fromToken(ctx.getStart()))
        : new StorageQualifier(
            ctx.typeNames.stream().map(Identifier::new));
  }

  @Override
  public StructBody visitStructBody(StructBodyContext ctx) {
    return new StructBody(ctx.structMember().stream().map(this::visitStructMember));
  }

  @Override
  public StructMember visitStructMember(StructMemberContext ctx) {
    return new StructMember(
        visitFullySpecifiedType(ctx.fullySpecifiedType()),
        ctx.structDeclarators.stream().map(this::visitStructDeclarator));
  }

  @Override
  public StructDeclarator visitStructDeclarator(StructDeclaratorContext ctx) {
    return new StructDeclarator(
        new Identifier(ctx.getStart()),
        applySafe(ctx.arraySpecifier(), this::visitArraySpecifier));
  }

  @Override
  public TypeSpecifier visitTypeSpecifier(TypeSpecifierContext ctx) {
    var arraySpecifier = applySafe(ctx.arraySpecifier(), this::visitArraySpecifier);

    var builtinTypeFixed = ctx.builtinTypeSpecifierFixed();
    if (builtinTypeFixed != null) {
      var type = BuiltinType.fromToken(builtinTypeFixed.getStart());
      return new BuiltinFixedTypeSpecifier(type, arraySpecifier);
    }

    var builtinNumericType = ctx.builtinTypeSpecifierParseable();
    if (builtinNumericType != null) {
      var type = Type.fromToken(builtinNumericType.getStart());
      return new BuiltinNumericTypeSpecifier(type, arraySpecifier);
    }

    var structSpecifierContext = ctx.structSpecifier();
    if (structSpecifierContext != null) {
      return new StructSpecifier(
          applySafe(structSpecifierContext.IDENTIFIER(), this::visitIdentifier),
          visitStructBody(structSpecifierContext.structBody()),
          arraySpecifier);
    }

    var identifier = visitIdentifier(ctx.IDENTIFIER());
    return new TypeReference(identifier, arraySpecifier);
  }

  @Override
  public TypeQualifier visitTypeQualifier(TypeQualifierContext ctx) {
    return new TypeQualifier(
        ctx.children.stream().map(child -> (TypeQualifierPart) visit(child)));
  }

  public Expression visitExpression(ExpressionContext ctx) {
    return (Expression) visit(ctx);
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
  }

  @Override
  public ExternalDeclaration visitExternalDeclaration(ExternalDeclarationContext ctx) {
    // wrap in an extra layer since we can't inherit from both external declaration
    // and declaration
    var result = super.visitExternalDeclaration(ctx);
    if (result instanceof Declaration declaration) {
      return new DeclarationExternalDeclaration(declaration);
    }
    return (ExternalDeclaration) result;
  }

  public Declaration visitDeclaration(DeclarationContext ctx) {
    return (Declaration) visit(ctx);
  }

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    var type = node.getSymbol().getType();
    if (type == GLSLLexer.IDENTIFIER) {
      visitIdentifier(node);
    }
    throw new IllegalStateException("Unhandled terminal node: " + node.getText());
  }

  public Identifier visitIdentifier(TerminalNode identifier) {
    if (identifier.getSymbol().getType() != GLSLLexer.IDENTIFIER) {
      throw new IllegalStateException("Expected identifier, got: " + identifier.getText());
    }
    return new Identifier(identifier.getSymbol());
  }
}
