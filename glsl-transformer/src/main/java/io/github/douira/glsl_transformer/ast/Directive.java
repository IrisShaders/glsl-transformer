package io.github.douira.glsl_transformer.ast;

/**
 * This class models unparsed directives with the # sign. Parsed directives are
 * modelled as regular parse tree nodes (for now).
 */
public class Directive extends StringNode {
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
    LINE,

    /**
     * # (without a name and without content)
     */
    EMPTY
  }

  private final Type type;

  /**
   * Crates a new directive with the given directive type and content after the
   * directive name. Newlines in the content are escaped with GLSL's line
   * continuation marker "\".
   * 
   * @param type    The type of the directive.
   * @param content The content to put after the directive name
   */
  public Directive(Type type, String content) {
    super(cleanContent(content));

    if (type == null) {
      throw new IllegalArgumentException("Non-null type must be used to construct a directive!");
    }

    if (type == Type.EMPTY) {
      throw new IllegalArgumentException("The EMPTY type may only be used with the corresponding constructor!");
    }

    this.type = type;
  }

  /**
   * Creates a new directive of the empty type. It receives no content because
   * it's not allowed to have content as per the GLSL spec.
   */
  public Directive() {
    super("");
    this.type = Type.EMPTY;
  }

  private static String cleanContent(String content) {
    return content == null ? null : content.trim().replace("\n", "\\\n");
  }

  @Override
  protected String getPrinted() {
    if (type == Type.EMPTY) {
      return "#\n";
    }

    return ("#"
        + (type == null ? "" : type.name().toLowerCase())
        + " " + getContent()).trim() + "\n";
  }
}
