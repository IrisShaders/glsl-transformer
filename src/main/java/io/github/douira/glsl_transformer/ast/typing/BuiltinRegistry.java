package io.github.douira.glsl_transformer.ast.typing;

import java.nio.charset.StandardCharsets;
import java.util.*;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.ASTParser;
import io.github.douira.glsl_transformer.util.LRUCache;

/**
 * Provides the scope of the builtin functions, variables, constants and types
 * for an environment.
 * <p>
 * The builtins are defined in resource files as GLSL declarations that are
 * parsed with this library's own parser. The files are divided into sections
 * by lines that start with {@code @} followed by the conditions under which the
 * declarations of the section are available:
 * <ul>
 * <li>{@code glsl>=130} and {@code es>=300}: the minimum desktop and ES
 * versions. If a section has a version condition or an extension, it is not
 * available in the kind of GLSL it has no version condition for.</li>
 * <li>{@code glsl<420} and {@code es<300}: the desktop and ES versions from
 * which on the declarations are not available anymore. The desktop limit only
 * applies to the core profile, the compatibility profile keeps everything.</li>
 * <li>{@code ext:GL_ARB_example}: an extension that makes the declarations
 * available regardless of the version.</li>
 * <li>{@code variadic}: the functions accept any number of additional
 * arguments.</li>
 * <li>{@code vert}, {@code frag} and the other names of the
 * {@link ShaderStage}s: the stages the declarations are available in.</li>
 * </ul>
 * Function prototypes can use placeholder types like {@code genType} or
 * {@code gsampler2D}, which stand for a family of types. A prototype is
 * instantiated once for each member of the families it uses, where all used
 * families need to have the same size.
 */
public final class BuiltinRegistry {
  /**
   * The names of the resource files that define the builtins.
   */
  static final List<String> FILES = List.of(
      "functions", "texture", "image", "variables", "compatibility", "extensions");

  private record Section(
      int desktopMinimum, int desktopEnd, int esMinimum, int esEnd,
      Set<String> extensions, Set<ShaderStage> stages, boolean variadic,
      TranslationUnit declarations) {
    boolean isAvailable(TypeEnvironment env) {
      if (env.stage() != null && !stages.isEmpty() && !stages.contains(env.stage())) {
        return false;
      }
      var number = env.version().number;
      var matchesVersion = env.isES() ? number >= esMinimum && number < esEnd
          : number >= desktopMinimum && (number < desktopEnd || !env.profile().isCore());
      return matchesVersion || extensions.stream().anyMatch(env::hasExtension);
    }
  }

  private static final Map<String, Type[]> FAMILIES = new HashMap<>();
  private static final List<Section> SECTIONS = new ArrayList<>();
  private static final LRUCache<TypeEnvironment, Scope> CACHE = new LRUCache<>(64);

  private BuiltinRegistry() {
  }

  private static void addFamilies(String prefix, NumericType scalar) {
    var all = new ArrayList<Type>(NumericType.ofNumberType(scalar.getNumberType()));
    all.removeIf(type -> ((NumericType) type).getBitDepth() != scalar.getBitDepth());
    FAMILIES.put(prefix + "vec", all.subList(1, 4).toArray(Type[]::new));
    if (all.size() > 4) {
      FAMILIES.put(prefix + "mat", all.subList(4, all.size()).toArray(Type[]::new));
      all.subList(4, all.size()).clear();
    }

    // the generic type of floats is called genFType but also just genType
    var name = prefix.toUpperCase(Locale.ROOT);
    FAMILIES.put("gen" + (name.isEmpty() ? "F" : name) + "Type", all.toArray(Type[]::new));
    FAMILIES.put("gen" + name + "Type", all.toArray(Type[]::new));
  }

  static {
    addFamilies("", NumericType.FLOAT32);
    addFamilies("d", NumericType.FLOAT64);
    addFamilies("i", NumericType.INT32);
    addFamilies("u", NumericType.UINT32);
    addFamilies("b", NumericType.BOOL);
    addFamilies("f16", NumericType.FLOAT16);
    addFamilies("i8", NumericType.INT8);
    addFamilies("u8", NumericType.UINT8);
    addFamilies("i16", NumericType.INT16);
    addFamilies("u16", NumericType.UINT16);
    addFamilies("i64", NumericType.INT64);
    addFamilies("u64", NumericType.UINT64);
    FAMILIES.put("gvec4", new Type[] { NumericType.F32VEC4, NumericType.I32VEC4, NumericType.U32VEC4 });
    FAMILIES.put("iuvec4", new Type[] { NumericType.I32VEC4, NumericType.U32VEC4 });
    FAMILIES.put("iuint", new Type[] { NumericType.INT32, NumericType.UINT32 });
    FAMILIES.put("iuint64", new Type[] { NumericType.INT64, NumericType.UINT64 });
    FAMILIES.put("string", new Type[] { StringType.INSTANCE });

    // the sampler and image types that exist for floats, signed and unsigned integers
    for (var type : FixedType.values()) {
      if (type.valueFormat == NumberType.SIGNED_INTEGER) {
        var floating = FixedType.valueOf(type.name().substring(1));
        var unsigned = FixedType.valueOf("U" + floating.name());
        FAMILIES.put("g" + floating.getTypeName(), new Type[] { floating, type, unsigned });
        FAMILIES.put("iu" + floating.getTypeName(), new Type[] { type, unsigned });
      }
    }

    var parser = new ASTParser();
    for (var file : FILES) {
      String content;
      try (var scanner = new Scanner(
          BuiltinRegistry.class.getResourceAsStream("builtins/" + file + ".glsl"), StandardCharsets.UTF_8)) {
        content = scanner.useDelimiter("\\A").next();
      }

      // the content before the first section header is ignored
      var rawSections = content.split("(?m)^@");
      for (var i = 1; i < rawSections.length; i++) {
        var parts = rawSections[i].split("\\R", 2);
        SECTIONS.add(parseSection(parts[0], parser.parseTranslationUnit(RootSupplier.EMPTY, parts[1])));
      }
    }
  }

  private static Section parseSection(String header, TranslationUnit declarations) {
    // the minimums of the kinds of GLSL the section is not restricted to
    var unrestricted = !header.matches(".*((glsl|es)[<>]|ext:).*");
    var desktopMinimum = unrestricted ? 0 : Integer.MAX_VALUE;
    var esMinimum = desktopMinimum;
    var desktopEnd = Integer.MAX_VALUE;
    var esEnd = Integer.MAX_VALUE;
    var extensions = new HashSet<String>();
    var stages = EnumSet.noneOf(ShaderStage.class);
    var variadic = false;
    for (var condition : header.trim().split("\\s+")) {
      if (condition.startsWith("glsl>=")) {
        desktopMinimum = Integer.parseInt(condition.substring(6));
      } else if (condition.startsWith("glsl<")) {
        desktopEnd = Integer.parseInt(condition.substring(5));
      } else if (condition.startsWith("es>=")) {
        esMinimum = Integer.parseInt(condition.substring(4));
      } else if (condition.startsWith("es<")) {
        esEnd = Integer.parseInt(condition.substring(3));
      } else if (condition.startsWith("ext:")) {
        extensions.add(condition.substring(4));
      } else if (condition.equals("variadic")) {
        variadic = true;
      } else if (!condition.isEmpty()) {
        stages.add(Objects.requireNonNull(ShaderStage.fromExtension(condition), condition));
      }
    }
    return new Section(desktopMinimum, desktopEnd, esMinimum, esEnd, extensions, stages, variadic,
        declarations);
  }

  /**
   * Returns the scope that contains the builtins of an environment. The scope
   * is shared and may not be modified.
   *
   * @param env the environment to get the builtins of
   * @return the scope of the builtins, which has no parent
   */
  public static synchronized Scope getScope(TypeEnvironment env) {
    return CACHE.cachedGet(env, () -> buildScope(env));
  }

  private static Scope buildScope(TypeEnvironment env) {
    var scope = new Scope(null);
    for (var section : SECTIONS) {
      if (!section.isAvailable(env)) {
        continue;
      }
      for (var declaration : section.declarations.getChildren()) {
        // a declaration is instantiated for each member of the families it uses,
        // which is known after the first instantiation
        var instances = 1;
        for (var instance = 0; instance < instances; instance++) {
          for (var family : FAMILIES.entrySet()) {
            var types = family.getValue();
            scope.set(new TypeSymbol(family.getKey(), types[instance % types.length], null));
          }
          var context = new AnalysisContext(declaration, env, scope, true, true);
          context.variadic = section.variadic;
          TypeAnalyzer.analyze(context, declaration);
          for (var symbol : context.analysis.symbols.values()) {
            if (symbol instanceof TypeSymbol type && FAMILIES.containsKey(type.name())) {
              instances = FAMILIES.get(type.name()).length;
            }
          }
        }
      }
    }
    FAMILIES.keySet().forEach(scope::remove);
    return scope;
  }
}
