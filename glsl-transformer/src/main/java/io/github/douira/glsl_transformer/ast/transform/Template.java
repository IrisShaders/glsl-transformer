package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.Supplier;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.query.Root;

public class Template<T extends ASTNode> {
  private final Map<ASTNode, Supplier<ASTNode>> replacements = new HashMap<>();
  private int localReplacementsMarked = 0;
  private List<ASTNode> localReplacements = Collections.emptyList();
  protected final T source;

  public Template(T source) {
    this.source = source;
  }

  public T getSource() {
    return source;
  }

  public Root getSourceRoot() {
    return source.getRoot();
  }

  @SuppressWarnings("unchecked") // the replacements map is always consistent
  public <R> R getReplacement(R original) {
    // correct use of the API should result in the right type here
    var replacementSupplier = replacements.get(original);
    return replacementSupplier == null ? null : (R) replacementSupplier.get();
  }

  @SuppressWarnings("unchecked") // all ASTNodes clone themselves with the right type
  public T getSeparateInstance() {
    return (T) source.cloneSeparate();
  }

  @SuppressWarnings("unchecked") // all ASTNodes clone themselves with the right type
  public T getInstanceFor(Root root) {
    return (T) source.cloneInto(root);
  }

  @SuppressWarnings("unchecked") // all ASTNodes clone themselves with the right type
  public T getInstanceFor(ASTNode treeMember) {
    return (T) source.cloneInto(treeMember);
  }

  public void supplyLocalReplacements(List<ASTNode> replacements) {
    Objects.requireNonNull(replacements);
    if (replacements.size() < localReplacementsMarked) {
      throw new IllegalStateException(
          "The local replacements must have enough items for all marked nodes in the template.");
    }
    this.localReplacements = replacements;
  }

  public void supplyLocalReplacements(ASTNode replacement) {
    Objects.requireNonNull(replacement);
    supplyLocalReplacements(Collections.singletonList(replacement));
  }

  public void supplyLocalReplacements(ASTNode... replacements) {
    Objects.requireNonNull(replacements);
    supplyLocalReplacements(Arrays.asList(replacements));
  }

  public T getSeparateInstance(List<ASTNode> localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getSeparateInstance();
  }

  public T getInstanceFor(Root root, List<ASTNode> localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getInstanceFor(root);
  }

  public T getInstanceFor(ASTNode treeMember, List<ASTNode> localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getInstanceFor(treeMember);
  }

  public T getSeparateInstance(ASTNode localReplacement) {
    supplyLocalReplacements(localReplacement);
    return getSeparateInstance();
  }

  public T getInstanceFor(Root root, ASTNode localReplacement) {
    supplyLocalReplacements(localReplacement);
    return getInstanceFor(root);
  }

  public T getInstanceFor(ASTNode treeMember, ASTNode localReplacement) {
    supplyLocalReplacements(localReplacement);
    return getInstanceFor(treeMember);
  }

  public T getSeparateInstance(ASTNode... localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getSeparateInstance();
  }

  public T getInstanceFor(Root root, ASTNode... localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getInstanceFor(root);
  }

  public T getInstanceFor(ASTNode treeMember, ASTNode... localReplacements) {
    supplyLocalReplacements(localReplacements);
    return getInstanceFor(treeMember);
  }

  public void markLocalReplacement(ASTNode original) {
    final var index = localReplacementsMarked++;
    markReplacement(original, () -> localReplacements.get(index));
  }

  public void markLocalReplacement(String tag, Class<? extends ASTNode> clazz) {
    markLocalReplacement(source.getRoot().identifierIndex.getOne(tag).getAncestor(clazz));
  }

  public void markIdentifierReplacement(String tag) {
    markLocalReplacement(source.getRoot().identifierIndex.getOne(tag));
  }

  public void markIdentifierReplacement(String tag, Supplier<ASTNode> replacement) {
    markReplacement(source.getRoot().identifierIndex.getOne(tag), replacement);
  }

  public void markReplacement(ASTNode original, Supplier<ASTNode> replacement) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(replacement);
    replacements.put(original, replacement);
    if (!original.hasParent()) {
      throw new IllegalArgumentException(
          "The original node must have a parent, otherwise setting it as a template doesn't work and it also doesn't make any sense to template the whole tree.");
    }
    original.getParent().markTemplate(this);
  }

  @SuppressWarnings("unchecked")
  public <R extends ASTNode> void markReplacement(String tag, Class<R> clazz, Supplier<R> replacement) {
    markReplacement(source.getRoot().identifierIndex.getOne(tag).getAncestor(clazz), (Supplier<ASTNode>) replacement);
  }

  @SuppressWarnings("unchecked") // all ASTNodes clone themselves with the right type
  public static <T extends ASTNode> Template<T> ofCloned(T source) {
    return new Template<T>((T) source.cloneSeparate());
  }

  public static Template<ExternalDeclaration> withExternalDeclaration(String input) {
    return new Template<>(ASTParser.getInternalInstance().parseSeparateExternalDeclaration(input));
  }

  public static Template<Statement> withStatement(String input) {
    return new Template<>(ASTParser.getInternalInstance().parseSeparateStatement(input));
  }

  public static Template<Expression> withExpression(String input) {
    return new Template<>(ASTParser.getInternalInstance().parseSeparateExpression(input));
  }
}
