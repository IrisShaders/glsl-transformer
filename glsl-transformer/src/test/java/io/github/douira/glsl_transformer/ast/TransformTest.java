package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.print.token.ParserToken;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

public class TransformTest extends TestWithSingleASTTransformer {
  @ParameterizedTest
  @TestCaseSource(caseSet = "uniformRemoval", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testUniformRemoval(String type, String input, String output) {
    p.setTransformation((tree, root) -> {
      // identify the names of the uniforms to remove from the uniform block
      var blockId = root.identifierIndex.getOne("UniformBlock");
      Objects.requireNonNull(blockId, "UniformBlock identifier not found");
      var block = blockId.getAncestor(InterfaceBlockDeclaration.class);
      Objects.requireNonNull(block, "UniformBlock declaration not found");

      var uniformDeclarations = new HashSet<>();

      // identify the declaration external declarations the names are part of
      for (var declarator : (Iterable<StructDeclarator>) block.getStructBody().getChildren().stream()
          .flatMap(member -> member.getDeclarators().stream())::iterator) {
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
                || qualifier.getChildren().stream().noneMatch(
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
          if (typeAndInit.getMembers().size() == 1) {
            externalDeclaration.detachAndDelete();
          } else {
            declarationMember.detachAndDelete();
          }
        }
      }

    });
    assertTransformI(output, input);
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "functionRenameWrap", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testFunctionRenameWrap(String type, String input, String output) {
    p.setTransformation((tree, root) -> {
      renameWrap(p, root, "shadow2D", "texture");
      renameWrap(p, root, "shadow2DLod", "textureLod");
    });
    assertTransformI(output, input);
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
    p.setTransformation((tree, root) -> {
      root.process(root.nodeIndex.getStream(EmptyDeclaration.class), ASTNode::detachAndDelete);
    });
    assertTransformI(output, input);
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "outDeclarationModify", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testOutDeclarationModify(String type, String input, String output) {
    var outDeclarationMatcher = new AutoHintedMatcher<ExternalDeclaration>(
        "out float __name;", Matcher.externalDeclarationPattern, "__") {
      {
        markClassWildcard("type", pattern.getRoot().nodeIndex.getOne(BuiltinNumericTypeSpecifier.class));
      }
    };
    var inDeclarationMatcher = new AutoHintedMatcher<ExternalDeclaration>(
        "in float __name;", Matcher.externalDeclarationPattern, "__") {
      {
        markClassWildcard("type", pattern.getRoot().nodeIndex.getOne(BuiltinNumericTypeSpecifier.class));
      }
    };

    var declarationTemplate = Template.withExternalDeclaration("out __1 __2;");
    declarationTemplate.markLocalReplacement("__1", TypeSpecifier.class);
    declarationTemplate.markIdentifierReplacement("__2");
    var initTemplate = Template.withStatement("__1 = __2;");
    initTemplate.markIdentifierReplacement("__1");
    initTemplate.markLocalReplacement("__2", ReferenceExpression.class);

    p.setTransformation((tree, root) -> {
      // find out declarations
      var outDeclarations = new HashSet<String>();
      for (ExternalDeclaration declaration : root.nodeIndex.get(DeclarationExternalDeclaration.class)) {
        if (outDeclarationMatcher.matchesExtract(declaration)) {
          outDeclarations.add(outDeclarationMatcher.getStringDataMatch("name"));
        }
      }

      // sanity check that there is a main function
      assertNotNull(tree.getMainDefinitionBody());

      // add out declarations that are missing for in declarations
      root.process(root.nodeIndex
          .getStream(
              DeclarationExternalDeclaration.class)
          .sorted(Comparator.comparing(declaration -> {
            if (inDeclarationMatcher.matchesExtract(declaration)) {
              return inDeclarationMatcher.getStringDataMatch("name");
            }
            return "";
          })),
          declaration -> {
            if (inDeclarationMatcher.matchesExtract(declaration)) {
              var name = inDeclarationMatcher.getStringDataMatch("name");
              var specifier = inDeclarationMatcher.getNodeMatch("type", BuiltinNumericTypeSpecifier.class);

              if (specifier != null && !outDeclarations.contains(name)) {
                tree.injectNode(ASTInjectionPoint.BEFORE_DECLARATIONS,
                    declarationTemplate.getInstanceFor(root,
                        specifier.cloneInto(root),
                        new Identifier(name)));

                tree.prependMain(initTemplate.getInstanceFor(root,
                    new Identifier(name),
                    LiteralExpression.getDefaultValue(specifier.type)));
              }
            }
          });
    });
    assertTransformI(output, input);
  }

  @Test
  void testLayoutBindingSearch() {
    // a: 6 layout qualifier parts + one external declaration,
    // b: 2 binding layout qualifier parts
    // c: 5 named layout qualifier parts
    assertCall(7 * REPEAT, 2 * REPEAT, 5 * REPEAT, (a, b, c) -> {
      p.setTransformation((tree, root) -> {
        // find layout qualifiers
        for (LayoutQualifier layoutQualifier : root.nodeIndex.get(LayoutQualifier.class)) {
          // find layout binding
          Expression binding = null;
          for (LayoutQualifierPart layoutQualifierPart : layoutQualifier.getParts()) {
            a.run();

            // check if it's a named layout qualifier with the name "binding"
            if (layoutQualifierPart instanceof NamedLayoutQualifierPart named) {
              if (named.getName().getName().equals("binding")) {
                binding = named.getExpression();
                b.run();
              }
              c.run();
            }
          }

          // get the enclosing fully specified type
          var type = layoutQualifier.getAncestor(2, 0, FullySpecifiedType.class::isInstance);

          // get the enclosing external declaration
          ExternalDeclaration externalDeclaration = null;
          if (type != null) {
            externalDeclaration = (ExternalDeclaration) type.getAncestor(2, 0, ExternalDeclaration.class::isInstance);
            a.run();
          }
          assertNotNull(binding, "It should find a binding layout qualifier");
          assertNotNull(externalDeclaration, "It should find an external declaration");
        }

      });

      p.transform("layout(binding = 0, binding = 4 + 4, baz = zam, foo, shared, bar) uniform sampler2D u_texture;");
    });
  }

  @Test
  void testLayoutBindingSearch2() {
    record BindingResult(
        int binding, // the binding location
        TypeQualifier qualifier, // the type qualifier (uniform etc.)
        List<String> declarationNames, // the name of the declaration (identifier)
        String typeName, // the name of the type (custom or builtin like sampler)
        boolean isStruct) { // true if the type is from an interface block declaration
    }

    class ListJobParameter implements JobParameters {
      List<BindingResult> results = new ArrayList<>();

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (getClass() != obj.getClass())
          return false;
        ListJobParameter other = (ListJobParameter) obj;
        if (results == null) {
          if (other.results != null)
            return false;
        } else if (!results.equals(other.results))
          return false;
        return true;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((results == null) ? 0 : results.hashCode());
        return result;
      }
    }

    var t = new SingleASTTransformer<ListJobParameter>();
    t.setTransformation((tree, root, parameters) -> {
      // find layout qualifiers
      for (LayoutQualifier layoutQualifier : root.nodeIndex.get(LayoutQualifier.class)) {
        // find layout binding
        Expression binding = null;
        for (LayoutQualifierPart layoutQualifierPart : layoutQualifier.getParts()) {
          // check if it's a named layout qualifier with the name "binding"
          if (layoutQualifierPart instanceof NamedLayoutQualifierPart named
              && named.getName().getName().equals("binding")) {
            binding = named.getExpression();
          }
        }

        int bindingLocation;
        if (binding instanceof LiteralExpression literal && literal.isInteger()) {
          bindingLocation = (int) literal.getInteger();
        } else {
          continue;
        }

        // get the enclosing fully specified type
        FullySpecifiedType fullySpecifiedType = (FullySpecifiedType) layoutQualifier.getAncestor(2, 0,
            FullySpecifiedType.class::isInstance);

        // if it exists, we are in a regular type and init declaration
        if (fullySpecifiedType != null) {
          // get the enclosing type and init declaration
          TypeAndInitDeclaration declaration = (TypeAndInitDeclaration) fullySpecifiedType.getAncestor(
              1, 0,
              TypeAndInitDeclaration.class::isInstance);
          if (declaration == null) {
            continue;
          }

          // make sure it's in an external declaration
          if (!(declaration.hasAncestor(
              1, 0, DeclarationExternalDeclaration.class::isInstance))) {
            continue;
          }

          // determine the name of the type
          var typeSpecifier = fullySpecifiedType.getTypeSpecifier();
          boolean isStruct = false;
          String typeName = null;
          switch (typeSpecifier.getSpecifierType()) {
            case BUILTIN_NUMERIC -> typeName = ((BuiltinNumericTypeSpecifier) typeSpecifier).type.getMostCompactName();
            case BULTIN_FIXED ->
              typeName = new ParserToken(
                  ((BuiltinFixedTypeSpecifier) typeSpecifier).type.tokenType).getContent();
            case STRUCT -> {
              isStruct = true;
              typeName = ((StructSpecifier) typeSpecifier)
                  .getName().getName();
            }
            case REFERENCE -> typeName = ((TypeReference) typeSpecifier)
                .getReference().getName();
          }

          parameters.results.add(new BindingResult(
              bindingLocation,
              fullySpecifiedType.getTypeQualifier(),
              declaration
                  .getMembers()
                  .stream()
                  .map(DeclarationMember::getName)
                  .map(Identifier::getName)
                  .collect(Collectors.toList()),
              typeName,
              isStruct));
        } else {
          // this may be an interface block declaration
          // get the enclosing interface block declaration to check
          InterfaceBlockDeclaration interfaceBlockDeclaration = (InterfaceBlockDeclaration) layoutQualifier
              .getAncestor(2, 0, InterfaceBlockDeclaration.class::isInstance);
          if (interfaceBlockDeclaration == null) {
            continue;
          }

          // make sure it's in an external declaration
          if (!(interfaceBlockDeclaration.hasAncestor(
              1, 0, DeclarationExternalDeclaration.class::isInstance))) {
            continue;
          }

          parameters.results.add(new BindingResult(
              bindingLocation,
              interfaceBlockDeclaration.getTypeQualifier(),
              List.of(interfaceBlockDeclaration.getVariableName().getName()),
              interfaceBlockDeclaration.getBlockName().getName(),
              true));
        }
      }
    });

    t.setJobParameters(new ListJobParameter());
    t.transform(
        "void main() { layout(binding = 4) int foo = 4; } layout(binding = 0) uniform sampler2D u_texture; layout(binding = 2) readonly buffer BlasDataAddresses { uint64_t address[]; } quadBlobs; layout(binding = 1) uniform accelerationStructureEXT acc;");

    assertEquals(3, t.getJobParameters().results.size(), "It should find 3 bindings");
    assertEquals(1,
        t.getJobParameters().results.stream().filter(BindingResult::isStruct).count(),
        "It should find one struct binding");
    assertEquals(Set.of(0, 1, 2),
        t.getJobParameters().results.stream().map(BindingResult::binding).collect(Collectors.toSet()),
        "It should find bindings 0, 1 and 2");
    assertEquals(Set.of("sampler2D", "BlasDataAddresses", "accelerationStructureEXT"),
        t.getJobParameters().results.stream().map(BindingResult::typeName).collect(Collectors.toSet()),
        "It should find bindings for sampler2D, BlasDataAddresses and accelerationStructureEXT");
    assertEquals(Set.of("u_texture", "quadBlobs", "acc"),
        t.getJobParameters().results.stream().map(BindingResult::declarationNames).flatMap(List::stream)
            .collect(Collectors.toSet()),
        "It should find bindings for u_texture, quadBlobs and acc");
    assertEquals(List.of(1, 1, 1),
        t.getJobParameters().results.stream().map(BindingResult::declarationNames).map(List::size)
            .collect(Collectors.toList()),
        "It should find 1 declaration name for each binding");
    assertEquals(Map.of(
        "sampler2D", 2,
        "BlasDataAddresses", 3,
        "accelerationStructureEXT", 2),
        t.getJobParameters().results.stream()
            .collect(Collectors.toMap(
                BindingResult::typeName,
                result -> result.qualifier().getParts().size())),
        "It should find 3 or 2 type qualifiers for each binding");
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "sidecarInjection", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testSidecarInjection(String type, String input, String output) {
    class SidecarInjector {
      String targetFunction;
      Set<String> targets;
    }
  }
}
