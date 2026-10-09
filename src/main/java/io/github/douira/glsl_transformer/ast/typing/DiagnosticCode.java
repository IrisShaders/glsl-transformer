package io.github.douira.glsl_transformer.ast.typing;

/**
 * The kinds of problems that the type analysis reports.
 */
public enum DiagnosticCode {
  /** A name is used that has no visible declaration. */
  UNDECLARED_IDENTIFIER,
  /** A name is used as a variable but refers to a function or a type. */
  NOT_A_VARIABLE,
  /** A name is used as a type but refers to a variable or a function. */
  NOT_A_TYPE,
  /** A name is called but refers to a variable. */
  NOT_A_FUNCTION,
  /** A name is declared again in the same scope in an incompatible way. */
  REDECLARATION,
  /** A declaration is invalid, for example a variable of type void. */
  INVALID_DECLARATION,
  /** No overload of the called function accepts the arguments. */
  NO_MATCHING_OVERLOAD,
  /** More than one overload of the called function fits equally well. */
  AMBIGUOUS_OVERLOAD,
  /** A constructor does not accept its arguments. */
  INVALID_CONSTRUCTOR,
  /** An operator is applied to operands of types it does not accept. */
  INVALID_OPERANDS,
  /** A value has a type that cannot be converted to the required type. */
  TYPE_MISMATCH,
  /** The target of an assignment or an out argument cannot be written to. */
  NOT_ASSIGNABLE,
  /** A condition is not a boolean. */
  INVALID_CONDITION,
  /** Something that cannot be indexed is indexed, or the index is no integer. */
  INVALID_INDEX,
  /** A constant index is outside of the bounds of the indexed value. */
  INDEX_OUT_OF_BOUNDS,
  /** A struct or interface block has no member with the accessed name. */
  NO_SUCH_MEMBER,
  /** A swizzle is malformed or accesses components the vector does not have. */
  INVALID_SWIZZLE,
  /** An array size is not a positive integer. */
  INVALID_ARRAY_SIZE,
  /** A constant expression is required but the expression is not constant. */
  NOT_CONSTANT,
  /** An initializer list is used with a type that cannot be initialized so. */
  INVALID_INITIALIZER,
  /** A returned value does not fit the return type of the function. */
  INVALID_RETURN,
  /** The value of a switch statement or a case label is not an integer. */
  INVALID_SWITCH
}
