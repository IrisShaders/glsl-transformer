package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.struct.StructDeclarator;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.transform.ASTTransformer;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

public class TransformTest extends TestWithASTTransformer {
  @ParameterizedTest
  @TestCaseSource(caseSet = "uniformRemoval", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testUniformRemoval(String type, String input, String output) {
    t.setTransformation((tree, root) -> {
      // identify the names of the uniforms to remove from the uniform block
      var blockId = root.identifierIndex.getOne("UniformBlock");
      Objects.requireNonNull(blockId, "UniformBlock identifier not found");
      var block = blockId.getAncestor(InterfaceBlockDeclaration.class);
      Objects.requireNonNull(block, "UniformBlock declaration not found");

      var uniformDeclarations = new HashSet<>();

      // identify the declaration external declarations the names are part of
      for (var declarator : (Iterable<StructDeclarator>) block.getStructBody().children.stream()
          .flatMap(member -> member.declarators.stream())::iterator) {
        var name = declarator.getName().getName();

        // get the enclosing declaration by looking at all usages of the name
        TypeAndInitDeclaration typeAndInit = null;
        DeclarationExternalDeclaration externalDeclaration = null;
        DeclarationMember declarationMember = null;
        var foundTarget = false;
        for (var id : declarator.getRoot().identifierIndex.get(name)) {
          // check that the identifier is part of the right structure and extract the
          // relevant nodes
          declarationMember = id.getAncestor(DeclarationMember.class);
          if (declarationMember == null) {
            continue;
          }
          typeAndInit = declarationMember.getAncestor(TypeAndInitDeclaration.class);
          if (typeAndInit == null) {
            continue;
          }
          externalDeclaration = typeAndInit.getAncestor(DeclarationExternalDeclaration.class);
          if (externalDeclaration == null) {
            continue;
          }

          // verify that it's actually a uniform declaration and cache this result
          if (!uniformDeclarations.contains(externalDeclaration)) {
            var qualifier = typeAndInit.getType().getTypeQualifier();
            if (qualifier == null
                || qualifier.children.stream().noneMatch(
                    qualifierPart -> qualifierPart instanceof StorageQualifier storageQualifier
                        && storageQualifier.storageType == StorageType.UNIFORM)) {
              continue;
            }
            uniformDeclarations.add(externalDeclaration);
          }

          foundTarget = true;
          break;
        }

        if (foundTarget) {
          // remove the declaration and if this is the only declaration member, remove the
          // whole external declaration
          if (typeAndInit.members.size() == 1) {
            externalDeclaration.detachAndDelete();
          } else {
            declarationMember.detachAndDelete();
          }
        }
      }

    });
    assertEquals(output, t.transform(input));
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "functionRenameWrap", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testFunctionRenameWrap(String type, String input, String output) {
    t.setTransformation((tree, root) -> {
      renameWrap(t, root, "shadow2D", "texture");
      renameWrap(t, root, "shadow2DLod", "textureLod");
    });
    assertEquals(output, t.transform(input));
  }

  private static void renameWrap(ASTTransformer<?> t, Root root, String oldName, String innerName) {
    root.process(root.identifierIndex.getStream(oldName)
        .filter(id -> id.getParent() instanceof FunctionCallExpression),
        id -> {
          FunctionCallExpression functionCall = (FunctionCallExpression) id.getParent();
          functionCall.getFunctionName().setName(innerName);
          FunctionCallExpression wrapper = (FunctionCallExpression) t.parseExpression(id, "vec4()");
          functionCall.replaceBy(wrapper);
          wrapper.getParameters().add(functionCall);
        });
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "emptyExternalDeclarationRemoval", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testEmptyExternalDeclarationRemoval(String type, String input, String output) {
    t.setTransformation((tree, root) -> {
      root.process(root.nodeIndex.getStream(EmptyDeclaration.class), ASTNode::detachAndDelete);
    });
    assertEquals(output, t.transform(input));
  }
}
