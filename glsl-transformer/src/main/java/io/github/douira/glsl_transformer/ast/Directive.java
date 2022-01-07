package io.github.douira.glsl_transformer.ast;

/**
 * This class models unparsed directives with the # sign. Parsed directives are
 * modelled as regular parse tree nodes (for now).
 */
public class Directive extends UnparsableASTNode {
  /**
   * The types of directives that can be generated.
   */
  public static enum Type {
    /**
     * #define
     */
    DEFINE,

    /**
     * #include
     */
    INCLUDE,

    /**
     * #undef
     */
    UNDEF,

    /**
     * #if
     */
    IF,

    /**
     * #ifdef
     */
    IFDEF,

    /**
     * #ifndef
     */
    IFNDEF,

    /**
     * #else
     */
    ELSE,

    /**
     * #elif
     */
    ELIF,

    /**
     * #endif
     */
    ENDIF,

    /**
     * #error
     */
    ERROR,

    /**
     * #line
     */
    LINE
  }

  private Type type;
  private String content;

  /**
   * Crates a new directive with the given directive type and content after the
   * directive name. Newlines in the content are escaped with GLSL's line
   * continuation marker "\".
   * 
   * @param type    The type of the directive.
   * @param content The content to put after the directive name
   */
  public Directive(Type type, String content) {
    if (content == null) {
      throw new IllegalArgumentException("Non-null content must used to construct a directive!");
    }
    if (type == null) {
      throw new IllegalArgumentException("Non-null type must be used to construct a directive!");
    }

    content = content.trim().replace("\n", "\\\n");

    this.type = type;
    this.content = content;
  }

  @Override
  protected String getPrinted() {
    return ("#"
        + (type == null ? "" : type.name().toLowerCase())
        + " " + content).trim() + "\n";
  }
}
