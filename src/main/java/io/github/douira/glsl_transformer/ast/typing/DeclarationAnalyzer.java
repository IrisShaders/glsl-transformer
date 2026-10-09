package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.typing.FunctionSignature.*;

/**
 * Analyzes declarations and everything that is part of them: type specifiers,
 * array specifiers, structs, interface blocks, qualifiers, function prototypes
 * and initializers.
 */
final class DeclarationAnalyzer {
  private final AnalysisContext ctx;

  DeclarationAnalyzer(AnalysisContext ctx) {
    this.ctx = ctx;
  }

  void externalDeclaration(ExternalDeclaration node) {
    switch (node.getExternalDeclarationType()) {
      case FUNCTION_DEFINITION -> functionDefinition((FunctionDefinition) node);
      case DECLARATION -> declaration(((DeclarationExternalDeclaration) node).getDeclaration());
      case PRAGMA_DIRECTIVE, EXTENSION_DIRECTIVE, CUSTOM_DIRECTIVE, INCLUDE_DIRECTIVE, EMPTY_DECLARATION -> {
      }
    }
  }

  /**
   * Analyzes a declaration. The declaration node gets the type that it declares
   * things with: the return type of a function, the type before the names of
   * variables, the type a precision is declared for or the type of an interface
   * block. Declarations that only apply qualifiers to names have no type.
   */
  Type declaration(Declaration node) {
    return ctx.record(node, switch (node.getDeclarationType()) {
      case FUNCTION -> functionPrototype(((FunctionDeclaration) node).getFunctionPrototype()).returnType();
      case TYPE_AND_INIT -> typeAndInitDeclaration((TypeAndInitDeclaration) node);
      case PRECISION -> typeSpecifier(((PrecisionDeclaration) node).getTypeSpecifier());
      case INTERFACE_BLOCK -> interfaceBlockDeclaration((InterfaceBlockDeclaration) node);
      case VARIABLE -> variableDeclaration((VariableDeclaration) node);
    });
  }

  private static Set<StorageType> storage(TypeQualifier node) {
    var storage = EnumSet.noneOf(StorageType.class);
    if (node != null) {
      for (var part : node.getParts()) {
        if (part instanceof StorageQualifier storageQualifier) {
          storage.add(storageQualifier.storageType);
        }
      }
    }
    return storage;
  }

  /**
   * Types the expressions in the layout qualifiers.
   */
  private void typeQualifier(TypeQualifier node) {
    if (node != null) {
      for (var part : node.getParts()) {
        if (part instanceof LayoutQualifier layoutQualifier) {
          layoutQualifier(layoutQualifier);
        }
      }
    }
  }

  private void layoutQualifier(LayoutQualifier node) {
    for (var part : node.getParts()) {
      if (part instanceof NamedLayoutQualifierPart named && named.getExpression() != null) {
        ctx.expressions.type(named.getExpression());
      }
    }
  }

  private static boolean isAssignable(Set<StorageType> storage, boolean global) {
    return !storage.contains(StorageType.CONST) && !storage.contains(StorageType.UNIFORM)
        && !(global && (storage.contains(StorageType.IN) || storage.contains(StorageType.ATTRIBUTE)));
  }

  Type specifiedType(FullySpecifiedType node) {
    typeQualifier(node.getTypeQualifier());
    return ctx.record(node, typeSpecifier(node.getTypeSpecifier()));
  }

  Type typeSpecifier(TypeSpecifier node) {
    Type base = switch (node.getSpecifierType()) {
      case NUMERIC -> ((NumericTypeSpecifier) node).type;
      case FIXED -> ((FixedTypeSpecifier) node).type;
      case STRUCT -> structSpecifier((StructSpecifier) node);
      case REFERENCE -> typeReference((TypeReference) node);
    };
    return ctx.record(node, arrayOf(base, node.getArraySpecifier()));
  }

  private Type typeReference(TypeReference node) {
    var name = node.getReference().getName();
    var symbol = ctx.scope.lookup(name);
    ctx.analysis.symbols.put(node, symbol);
    if (symbol instanceof TypeSymbol typeSymbol) {
      return typeSymbol.type();
    }
    if (symbol instanceof FunctionSymbol function) {
      // functions declared with the subroutine qualifier are used as types
      return new SubroutineType(function);
    }
    return symbol == null
        ? ctx.error(DiagnosticCode.UNDECLARED_IDENTIFIER, node, "Undeclared type '" + name + "'")
        : ctx.error(DiagnosticCode.NOT_A_TYPE, node, "'" + name + "' is not a type");
  }

  private Type structSpecifier(StructSpecifier node) {
    var name = node.getName() == null ? null : node.getName().getName();
    var type = new StructType(name, structBody(node.getStructBody()), ctx.declaring(node));
    if (name != null) {
      ctx.declare(new TypeSymbol(name, type, ctx.declaring(node)), node);
    }
    return type;
  }

  private SequencedMap<String, Type> structBody(StructBody node) {
    var fields = new LinkedHashMap<String, Type>();
    for (var member : node.getMembers()) {
      var base = specifiedType(member.getSpecifiedType());
      for (var declarator : member.getDeclarators()) {
        var name = declarator.getName().getName();
        var type = ctx.record(declarator, arrayOf(base, declarator.getArraySpecifier()));
        if (fields.put(name, type) != null) {
          ctx.error(DiagnosticCode.REDECLARATION, declarator, "Duplicate member '" + name + "'");
        }
      }
    }
    return fields;
  }

  /**
   * Wraps the type in the arrays that the array specifier describes. The first
   * dimension of the specifier is the outermost array.
   */
  private Type arrayOf(Type base, ArraySpecifier node) {
    if (node == null) {
      return base;
    }
    var dimensions = node.getChildren();
    var type = base;
    for (var i = dimensions.size() - 1; i >= 0; i--) {
      type = new ArrayType(type, arraySize(dimensions.get(i)));
    }
    return type;
  }

  private ArraySize arraySize(Expression node) {
    if (node == null) {
      return ArraySize.UNSIZED;
    }
    var type = ctx.expressions.type(node);
    var value = ctx.constants.evaluate(node);
    if (value == null) {
      ctx.error(DiagnosticCode.NOT_CONSTANT, node, "The array size is not a constant expression");
      return ArraySize.UNKNOWN;
    }
    if (type instanceof NumericType numeric && numeric.isScalar() && numeric.getNumberType().isInteger()
        && (!value.isKnown() || value.value() > 0 && value.value() <= Integer.MAX_VALUE)) {
      return value.isKnown() ? ArraySize.of((int) value.value()) : ArraySize.UNKNOWN;
    }
    if (!type.isError()) {
      ctx.error(DiagnosticCode.INVALID_ARRAY_SIZE, node, "The array size has to be a positive integer");
    }
    return ArraySize.UNKNOWN;
  }

  private Type variableDeclaration(VariableDeclaration node) {
    typeQualifier(node.getTypeQualifier());
    for (var name : node.getNames()) {
      var symbol = ctx.scope.lookup(name.getName());
      ctx.analysis.symbols.put(name, symbol);
      if (!(symbol instanceof VariableSymbol)) {
        ctx.error(DiagnosticCode.UNDECLARED_IDENTIFIER, name, "Undeclared variable '" + name.getName() + "'");
      }
    }
    return null;
  }

  private Type typeAndInitDeclaration(TypeAndInitDeclaration node) {
    var specifiedType = node.getSpecifiedType();

    // The grammar parses a statement that only consists of a variable name or
    // an indexed variable, like "a;" or "a[i];", as a declaration without
    // declared names. Such a statement is not treated as a declaration: only its
    // index expressions are typed.
    if (node.getMembers().isEmpty() && specifiedType.getTypeSpecifier() instanceof TypeReference reference
        && ctx.scope.lookup(reference.getReference().getName()) instanceof VariableSymbol variable) {
      ctx.analysis.symbols.put(reference, variable);
      if (reference.getArraySpecifier() != null) {
        for (var index : reference.getArraySpecifier().getChildren()) {
          if (index != null) {
            ctx.expressions.type(index);
          }
        }
      }
      return variable.type();
    }

    var base = specifiedType(specifiedType);
    var storage = storage(specifiedType.getTypeQualifier());
    for (var member : node.getMembers()) {
      declareVariable(member, member.getName().getName(),
          arrayOf(base, member.getArraySpecifier()), member.getInitializer(), storage);
    }
    return base;
  }

  /**
   * Declares a variable after analyzing its initializer, so that the
   * initializer does not see the variable itself.
   */
  private Type declareVariable(ASTNode node, String name, Type declaredType, Initializer initializer,
      Set<StorageType> storage) {
    var type = declaredType;
    if (type == FixedType.VOID) {
      type = ctx.error(DiagnosticCode.INVALID_DECLARATION, node, "The variable '" + name + "' cannot be void");
    }
    // constants without a known initializer, like the builtin constants, have an unknown value
    var constantValue = storage.contains(StorageType.CONST) ? ConstantValue.UNKNOWN : null;
    if (initializer != null) {
      type = initializer(initializer, type);
      if (constantValue != null && initializer instanceof ExpressionInitializer expression) {
        // a constant that is not initialized with a constant expression is not one itself
        constantValue = ctx.constants.evaluate(expression.getExpression());
        if (constantValue != null && constantValue.isKnown()) {
          constantValue = ConstantValue.of(type, constantValue.value());
        }
      }
    }
    ctx.declare(new VariableSymbol(name, type, isAssignable(storage, ctx.isTopScope()), constantValue,
        ctx.declaring(node)), node);
    return ctx.record(node, type);
  }

  Type iterationConditionInitializer(IterationConditionInitializer node) {
    var specifiedType = node.getSpecifiedType();
    return declareVariable(node, node.getName().getName(), specifiedType(specifiedType),
        node.getInitializer(), storage(specifiedType.getTypeQualifier()));
  }

  /**
   * Checks an initializer against the type of the initialized variable.
   *
   * @return the type of the variable, which has the size of the initializer if
   *         the variable is declared as an array without a size
   */
  private Type initializer(Initializer node, Type target) {
    return ctx.record(node, switch (node.getInitializerType()) {
      case EXPRESSION -> {
        var type = ctx.expressions.type(((ExpressionInitializer) node).getExpression());
        var converts = ctx.converts(type, target);
        if (!converts) {
          ctx.error(DiagnosticCode.TYPE_MISMATCH, node,
              "Cannot initialize a value of type " + target.getTypeName() + " with " + type.getTypeName());
        }
        yield converts && type instanceof ArrayType ? type : target;
      }
      case NESTED -> nestedInitializer((NestedInitializer) node, target);
    });
  }

  /**
   * Returns the types of the parts an aggregate value of the given type is made
   * of, or null if the type is not an array or a struct.
   */
  static List<Type> aggregateElements(Type type, int count) {
    if (type instanceof ArrayType array) {
      return Collections.nCopies(array.size().isKnown() ? array.size().length() : count, array.element());
    }
    return type instanceof StructType struct ? List.copyOf(struct.getFields().values()) : null;
  }

  private Type nestedInitializer(NestedInitializer node, Type target) {
    var initializers = node.getInitializers();
    var count = initializers.size();
    var expected = target instanceof NumericType numeric && !numeric.isScalar()
        ? Collections.<Type>nCopies(numeric.isMatrix() ? numeric.getColumns() : numeric.getRows(),
            numeric.getElementType())
        : aggregateElements(target, count);

    // an empty initializer list initializes anything to zero
    if (count == 0) {
      return target;
    }
    if (expected == null) {
      for (var initializer : initializers) {
        initializer(initializer, ErrorType.INSTANCE);
      }
      return target.isError() ? target
          : ctx.error(DiagnosticCode.INVALID_INITIALIZER, node,
              "A value of type " + target.getTypeName() + " cannot be initialized with an initializer list");
    }
    if (expected.size() != count) {
      ctx.error(DiagnosticCode.TYPE_MISMATCH, node, "The initializer list for a value of type "
          + target.getTypeName() + " needs " + expected.size() + " items but has " + count);
    }
    for (var i = 0; i < count; i++) {
      initializer(initializers.get(i), i < expected.size() ? expected.get(i) : ErrorType.INSTANCE);
    }
    return target instanceof ArrayType array && !array.size().isKnown()
        ? new ArrayType(array.element(), ArraySize.of(count))
        : target;
  }

  private FunctionSignature functionPrototype(FunctionPrototype node) {
    var name = node.getName().getName();
    var returnType = specifiedType(node.getReturnType());
    var parameters = new ArrayList<Parameter>();
    for (var parameter : node.getParameters()) {
      var specifiedType = parameter.getSpecifiedType();
      var type = ctx.record(parameter, arrayOf(specifiedType(specifiedType), parameter.getArraySpecifier()));
      var storage = storage(specifiedType.getTypeQualifier());

      // a parameter list can be written as (void)
      if (type != FixedType.VOID) {
        parameters.add(new Parameter(type, storage.contains(StorageType.OUT) ? Direction.OUT
            : storage.contains(StorageType.INOUT) ? Direction.INOUT : Direction.IN));
      }
    }
    var signature = new FunctionSignature(name, returnType, parameters, ctx.variadic, ctx.declaring(node));
    ctx.record(node, returnType);
    ctx.analysis.signatures.put(node, signature);
    ctx.declare(new FunctionSymbol(name, signature), node);
    return signature;
  }

  private void functionDefinition(FunctionDefinition node) {
    var prototype = node.getFunctionPrototype();
    var signature = functionPrototype(prototype);

    // the parameters and the body share one scope
    ctx.pushScope(node);
    for (var parameter : prototype.getParameters()) {
      if (parameter.getName() != null) {
        var storage = storage(parameter.getSpecifiedType().getTypeQualifier());
        ctx.declare(new VariableSymbol(parameter.getName().getName(), ctx.analysis.typeOf(parameter),
            isAssignable(storage, false), null, parameter), parameter);
      }
    }
    ctx.returnType = signature.returnType();
    ctx.statements.body(node.getBody());
    ctx.returnType = null;
    ctx.popScope();
  }

  private Type interfaceBlockDeclaration(InterfaceBlockDeclaration node) {
    typeQualifier(node.getTypeQualifier());
    var assignable = isAssignable(storage(node.getTypeQualifier()), true);
    var members = structBody(node.getStructBody());
    var type = ctx.record(node, new InterfaceBlockType(node.getBlockName().getName(), members,
        ctx.declaring(node)));
    if (node.getVariableName() != null) {
      ctx.declare(new VariableSymbol(node.getVariableName().getName(),
          arrayOf(type, node.getArraySpecifier()), assignable, null, ctx.declaring(node)), node);
    } else {
      // the members of a block without instance name are declared directly
      members.forEach((name, memberType) -> ctx.declare(
          new VariableSymbol(name, memberType, assignable, null, ctx.declaring(node)), node));
    }
    return type;
  }
}
