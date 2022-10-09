package io.github.douira.glsl_transformer.ast.node;

import java.util.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ListASTNode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.print.OutputOptions;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TranslationUnit extends ListASTNode<ExternalDeclaration> {
  protected VersionStatement versionStatement;
  public final OutputOptions outputOptions;

  public TranslationUnit(VersionStatement versionStatement, Stream<ExternalDeclaration> children, OutputOptions outputOptions) {
    super(children);
    this.versionStatement = setup(versionStatement, this::setVersionStatement);
    this.outputOptions = outputOptions;
  }

  public TranslationUnit(VersionStatement versionStatement, Stream<ExternalDeclaration> children) {
    super(children);
    this.versionStatement = setup(versionStatement, this::setVersionStatement);
    this.outputOptions = new OutputOptions();
  }

  public TranslationUnit(Stream<ExternalDeclaration> children) {
    super(children);
    this.outputOptions = new OutputOptions();
  }

  public VersionStatement getVersionStatement() {
    return versionStatement;
  }

  public void setVersionStatement(VersionStatement versionStatement) {
    updateParents(this.versionStatement, versionStatement, this::setVersionStatement);
    this.versionStatement = versionStatement;
  }

  public void injectNode(ASTInjectionPoint injectionPoint, ExternalDeclaration node) {
    getChildren().add(injectionPoint.getInjectionIndex(this), node);
  }

  public void injectNodes(
      ASTInjectionPoint injectionPoint,
      Collection<ExternalDeclaration> nodes) {
    getChildren().addAll(injectionPoint.getInjectionIndex(this), nodes);
  }

  public void injectNodes(
      ASTInjectionPoint injectionPoint,
      Stream<ExternalDeclaration> nodes) {
    nodes.reduce(injectionPoint.getInjectionIndex(this), (index, node) -> {
      getChildren().add(index, node);
      return index + 1;
    }, Integer::sum);
  }

  public void parseAndInjectNodes(
      ASTParser t,
      ASTInjectionPoint injectionPoint,
      Stream<String> externalDeclarations) {
    injectNodes(injectionPoint,
        externalDeclarations.map(str -> t.parseExternalDeclaration(this, str)));
  }

  public void parseAndInjectNode(
      ASTParser t,
      ASTInjectionPoint injectionPoint,
      String externalDeclaration) {
    getChildren().add(injectionPoint.getInjectionIndex(this),
        t.parseExternalDeclaration(
            this,
            externalDeclaration));
  }

  public void parseAndInjectNodes(
      ASTParser t,
      ASTInjectionPoint injectionPoint,
      String... externalDeclarations) {
    injectNodes(injectionPoint, t.parseExternalDeclarations(this, externalDeclarations));
  }

  public CompoundStatement getFunctionDefinitionBody(String functionName) {
    return getRoot().identifierIndex.getStream(functionName)
        .map(id -> id.getBranchAncestor(FunctionDefinition.class, FunctionDefinition::getFunctionPrototype))
        .filter(Objects::nonNull).findAny().map(FunctionDefinition::getBody).orElse(null);
  }

  public CompoundStatement getMainDefinitionBody() {
    return getFunctionDefinitionBody("main");
  }

  public void prependFunctionBody(String functionName, Statement statement) {
    getFunctionDefinitionBody(functionName).getStatements().add(0, statement);
  }

  public void prependFunctionBody(String functionName, Collection<Statement> statements) {
    getFunctionDefinitionBody(functionName).getStatements().addAll(0, statements);
  }

  public void appendFunctionBody(String functionName, Statement statement) {
    getFunctionDefinitionBody(functionName).getStatements().add(statement);
  }

  public void appendFunctionBody(String functionName, Collection<Statement> statements) {
    getFunctionDefinitionBody(functionName).getStatements().addAll(statements);
  }

  public void prependMain(Statement statement) {
    prependFunctionBody("main", statement);
  }

  public void prependMain(Collection<Statement> statements) {
    prependFunctionBody("main", statements);
  }

  public void appendMain(Statement statement) {
    appendFunctionBody("main", statement);
  }

  public void appendMain(Collection<Statement> statements) {
    appendFunctionBody("main", statements);
  }

  public void prependMain(ASTParser t, String... statements) {
    prependMain(t.parseStatements(this, statements));
  }

  public void prependMain(ASTParser t, String statement) {
    prependMain(t.parseStatement(this, statement));
  }

  public void appendMain(ASTParser t, String... statements) {
    appendMain(t.parseStatements(this, statements));
  }

  public void appendMain(ASTParser t, String statement) {
    appendMain(t.parseStatement(this, statement));
  }

  public void ensureVersionStatement() {
    if (versionStatement == null) {
      setVersionStatement(VersionStatement.getDefault());
    }
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitTranslationUnit(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTranslationUnit(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTranslationUnit(this);
  }

  @Override
  public TranslationUnit clone() {
    return new TranslationUnit(clone(versionStatement), getClonedChildren(), outputOptions.clone());
  }

  @Override
  public TranslationUnit cloneInto(Root root) {
    return (TranslationUnit) super.cloneInto(root);
  }

  @Override
  public TranslationUnit cloneSeparate() {
    return (TranslationUnit) super.cloneSeparate();
  }
}
