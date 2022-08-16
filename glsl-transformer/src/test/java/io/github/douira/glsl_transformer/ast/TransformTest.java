package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.StructDeclarator;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

public class TransformTest extends TestWithSingleASTTransformer {
  @ParameterizedTest
  @TestCaseSource(caseSet = "uniformRemoval", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testUniformRemoval(String type, String input, String output) {
    p.setPrintType(PrintType.INDENTED);
    p.setTransformation((tree, root) -> {
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
    assertEquals(output, p.transform(input));
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "functionRenameWrap", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testFunctionRenameWrap(String type, String input, String output) {
    p.setPrintType(PrintType.INDENTED);
    p.setTransformation((tree, root) -> {
      renameWrap(p, root, "shadow2D", "texture");
      renameWrap(p, root, "shadow2DLod", "textureLod");
    });
    assertEquals(output, p.transform(input));
  }

  private static void renameWrap(ASTParser p, Root root, String oldName, String innerName) {
    root.process(root.identifierIndex.getStream(oldName)
        .filter(id -> id.getParent() instanceof FunctionCallExpression),
        id -> {
          FunctionCallExpression functionCall = (FunctionCallExpression) id.getParent();
          functionCall.getFunctionName().setName(innerName);
          FunctionCallExpression wrapper = (FunctionCallExpression) p.parseExpression(id, "vec4()");
          functionCall.replaceBy(wrapper);
          wrapper.getParameters().add(functionCall);
        });
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "emptyExternalDeclarationRemoval", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testEmptyExternalDeclarationRemoval(String type, String input, String output) {
    p.setPrintType(PrintType.INDENTED);
    p.setTransformation((tree, root) -> {
      root.process(root.nodeIndex.getStream(EmptyDeclaration.class), ASTNode::detachAndDelete);
    });
    assertEquals(output, p.transform(input));
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "outDeclarationModify", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testOutDeclarationModify(String type, String input, String output) {
    AutoHintedMatcher<ExternalDeclaration> outDeclarationMatcher = new AutoHintedMatcher<ExternalDeclaration>(
        "out float __name;", Matcher.externalDeclarationPattern, "__") {
      {
        markClassWildcard("type", pattern.getRoot().nodeIndex.getOne(BuiltinNumericTypeSpecifier.class));
      }
    };
    AutoHintedMatcher<ExternalDeclaration> inDeclarationMatcher = new AutoHintedMatcher<ExternalDeclaration>(
        "in float __name;", Matcher.externalDeclarationPattern, "__") {
      {
        markClassWildcard("type", pattern.getRoot().nodeIndex.getOne(BuiltinNumericTypeSpecifier.class));
      }
    };
    var tag = "_____";
    var typeTag = tag + "1";
    var nameTag = tag + "2";
    var outDeclarationTemplate = "out " + typeTag + " " + nameTag + ";";

    p.setPrintType(PrintType.INDENTED);
    p.setTransformation((tree, root) -> {
      // find out declarations
      var outDeclarations = new HashMap<>();
      for (ExternalDeclaration declaration : root.nodeIndex.get(DeclarationExternalDeclaration.class)) {
        if (outDeclarationMatcher.matchesExtract(declaration)) {
          outDeclarations.put(
              outDeclarationMatcher.getStringDataMatch("name"),
              outDeclarationMatcher.getNodeMatch("type", TypeSpecifier.class));
        }
      }

      // add out declarations that are missing for in declarations
      for (ExternalDeclaration declaration : root.nodeIndex.get(DeclarationExternalDeclaration.class)) {
        if (inDeclarationMatcher.matchesExtract(declaration)) {
          var name = inDeclarationMatcher.getStringDataMatch("name");
          var specifier = inDeclarationMatcher.getNodeMatch("type", BuiltinNumericTypeSpecifier.class);

          if (specifier != null && !outDeclarations.containsKey(name)) {
            var inDeclaration = p.parseExternalDeclaration(tree, outDeclarationTemplate);
            tree.injectNode(ASTInjectionPoint.BEFORE_DECLARATIONS, inDeclaration);
            root.identifierIndex.rename(nameTag, name);

            // TODO: more efficient copying of the fully specified type
            // use node cloning once available
            root.identifierIndex.getOne(typeTag).getAncestor(TypeSpecifier.class)
                .replaceBy(new BuiltinNumericTypeSpecifier(specifier.type));
          }
        }
      }

      // TODO: initialization in the main function
      // LiteralExpression.getDefaultValue(numberType)
    });
    assertEquals(output, p.transform(input));
  }
}
