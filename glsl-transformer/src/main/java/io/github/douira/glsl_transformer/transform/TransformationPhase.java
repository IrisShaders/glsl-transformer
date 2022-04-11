package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.DeclarationContext;
import io.github.douira.glsl_transformer.GLSLParser.ExtensionStatementContext;
import io.github.douira.glsl_transformer.GLSLParser.ExternalDeclarationContext;
import io.github.douira.glsl_transformer.GLSLParser.FunctionDefinitionContext;
import io.github.douira.glsl_transformer.GLSLParser.LayoutDefaultsContext;
import io.github.douira.glsl_transformer.GLSLParser.PragmaStatementContext;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.GLSLParser.VersionStatementContext;
import io.github.douira.glsl_transformer.GLSLParserBaseListener;
import io.github.douira.glsl_transformer.ast.Directive;
import io.github.douira.glsl_transformer.ast.Directive.Type;
import io.github.douira.glsl_transformer.print.EmptyTerminalNode;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * The transformations phase actually does a specific transformation. It can be
 * added to a transformation which holds multiple transformation phases and
 * their ordering. A phase can also be added to multiple transformations if they
 * should share the functionality. The transformation phase has methods for
 * adding and removing parse tree nodes. It can also inject nodes into the root
 * node's child array with injection points.
 */
public abstract class TransformationPhase<T extends JobParameters> extends GLSLParserBaseListener
    implements LifecycleUser<T>, PartialParseTreeListener {
  private ExecutionPlanner<T> planner;
  private boolean walkFinishedNotified = false;
  private boolean initialized = false;

  /**
   * Called during planning in order to determine if this phase does any
   * walking at all or if it just runs some code, like a RunPhase. This doesn't
   * exclude or include this phase from walking but rather helps the execution
   * planner combine walk phases into execution levels. Enabling and disabling a
   * phase should be done with the methods
   * {@link #checkBeforeWalk(TranslationUnitContext)} and
   * {@link #runAfterWalk(TranslationUnitContext)}.
   * 
   * @implNote This method should run quickly and will only be called once (or
   *           never) during execution planning.
   * 
   * @return If this phase needs to be walked on the tree
   */
  protected abstract boolean canWalk();

  /**
   * Method called by the execution planner before the walk happens. The returned
   * boolean determines if the phase is added to the list of phases that are
   * walked on the tree. Returns false by default and implementing classes should
   * overwrite this.
   * 
   * @param ctx The root node
   * @return {@code true} if the phase should be walked on the tree
   */
  protected boolean checkBeforeWalk(TranslationUnitContext ctx) {
    return false;
  }

  /**
   * Method called by the execution planner after the walk happens. Does nothing
   * by default.
   * 
   * @param ctx The root node
   */
  protected void runAfterWalk(TranslationUnitContext ctx) {
  }

  @Override
  public ExecutionPlanner<T> getPlanner() {
    return planner;
  }

  /**
   * This must be called before executing this phase in the context of a specific
   * parse tree.
   * 
   * {@inheritDoc}
   */
  @Override
  public void setPlanner(ExecutionPlanner<T> parent) {
    this.planner = parent;
  }

  @Override
  public boolean isInitialized() {
    return initialized;
  }

  @Override
  public void setInitialized() {
    initialized = true;
  }

  /**
   * Marks this phase as being done walking the tree in the current execution.
   * This removes it from the proxy parse tree listener which in turn can make the
   * dynamic parse tree walker not further walk the parse tree if there are no
   * more listeners that are interested in continuing.
   * 
   * @apiNote Calling this method multiple times in the same execution has no
   *          effect but is efficient
   */
  protected void walkFinished() {
    if (walkFinishedNotified) {
      return;
    }

    walkFinishedNotified = true;
    getPlanner().removeCurrentPhaseFromWalk();
  }

  void resetWalkFinishState() {
    walkFinishedNotified = false;
  }

  /**
   * Gets the sibling nodes of a given node. It looks up the parent and then
   * returns the parent's children.
   * 
   * @param node The node to get the siblings for
   * @return The siblings of the given node. {@code null} if the node has no
   *         parent.
   */
  protected static List<ParseTree> getSiblings(TreeMember node) {
    var parent = node.getParent();
    return parent == null ? null : parent.children;
  }

  /**
   * Replaces the given node in its parent with a new node generated by parsing
   * the given string with the given method of the parser. See
   * {@link #createLocalRoot(String, ExtendedContext, Function)} for details of
   * creating parsed nodes.
   * 
   * @param removeNode  The node to be replaced
   * @param newContent  The string from which a new node is generated
   * @param parseMethod The method with which the string will be parsed
   */
  protected void replaceNode(TreeMember removeNode, String newContent,
      Function<GLSLParser, ExtendedContext> parseMethod) {
    replaceNode(removeNode,
        createLocalRoot(newContent, removeNode.getParent(), parseMethod));
  }

  /**
   * Replaces the given node in its parent with a new given node. The new node
   * should either be already set up as a local root or be a terminal node tree
   * member.
   * 
   * @param removeNode The node to be removed
   * @param newNode    The new node to take its place
   * @return The index of the removed and new node
   */
  protected int replaceNode(TreeMember removeNode, TreeMember newNode) {
    var parent = removeNode.getParent();
    if (parent == null) {
      throw new IllegalArgumentException("The root node may not be removed!");
    }

    /*
     * tell the node being replaced which node preceded it, this is to ensure that
     * the dynamic parse tree walker knows where to continue walking if some
     * operation during the walk modifies the array structurally (though it can only
     * be modified in such a way that the item the walker currently is looking at
     * moves further to the end of the array.)
     */
    newNode.setPreviousNode(removeNode);

    var children = parent.children;
    var index = children.indexOf(removeNode);
    newNode.setParent(parent);
    children.set(index, newNode);
    removeNode.omitTokens();
    return index;
  }

  /**
   * Removes the given node from its parent's child list.
   * 
   * @implNote The empty space is filled with an empty terminal node that keeps a
   *           reference to the removed node.
   * @param removeNode The node to remove
   * @return the index of the removed node
   */
  protected int removeNode(TreeMember removeNode) {
    // the node needs to be replaced with something to preserve the containing
    // array's length or there's a NullPointerException in the walker
    return replaceNode(removeNode, new EmptyTerminalNode());
  }

  /**
   * Compiles the given string as an xpath with the parser.
   * 
   * This method is meant to be used in {@link #init()} for initializing
   * (effectively) final but phase-specific fields.
   * 
   * @param xpath The string to compile as an xpath
   * @return The compiled xpath
   */
  protected XPath compilePath(String xpath) {
    return new XPath(getParser(), xpath);
  }

  /**
   * Compiles the given string as a parse tree matching pattern what starts
   * matching at the given parser rule. The pattern will not compile or function
   * correctly if the pattern can't be compiled in the context of the given parser
   * rule. See ANTLR's documentation on how tree matching patterns work. (there is
   * special syntax that should be used for extracting)
   * 
   * The resulting pattern will need to be applied to nodes that exactly match the
   * given root rule of the pattern. For finding nodes at any depth and then
   * matching,
   * {@link #findAndMatch(ParseTree, XPath, ParseTreePattern)} can be used.
   * 
   * This method is meant to be used in {@link #init()} for initializing
   * (effectively) final but phase-specific fields.
   * 
   * @param pattern  The string to compile as a tree matching pattern.
   * @param rootRule The parser rule to compile the pattern as
   * @return The compiled pattern
   */
  protected ParseTreePattern compilePattern(String pattern, int rootRule) {
    return getParser().compileParseTreePattern(pattern, rootRule, getLexer());
  }

  /**
   * This method uses a statically constructed xpath, so it doesn't need to be
   * repeatedly constructed. The subtrees yielded by the xpath need to start with
   * the rule that the pattern was constructed with or nothing will match.
   * 
   * Adapted from ANTLR's implementation of
   * {@link org.antlr.v4.runtime.tree.pattern.ParseTreePattern#findAll(ParseTree, String)}.
   * 
   * @param tree    The parse tree to find and match in
   * @param xpath   The xpath that leads to a subtree for matching
   * @param pattern The pattern that tests the subtrees for matches
   * @return A list of all matches resulting from the subtrees
   */
  protected List<ParseTreeMatch> findAndMatch(ParseTree tree, XPath xpath, ParseTreePattern pattern) {
    var subtrees = xpath.evaluate(tree);
    var matches = new ArrayList<ParseTreeMatch>();
    for (ParseTree sub : subtrees) {
      ParseTreeMatch match = pattern.match(sub);
      if (match.succeeded()) {
        matches.add(match);
      }
    }
    return matches;
  }

  /**
   * Overwrite this method to add a check of if this phase should be run at all.
   * Especially for WalkPhase this is important since it reduces the number of
   * listeners that need to be processed.
   * 
   * @return If the phase should run. {@code true} by default.
   */
  protected boolean isActive() {
    return true;
  }

  /**
   * Parses the given string using the given parser method. Since the parser
   * doesn't know which part of the parse tree any string would be part of, we
   * need to tell it. In many cases multiple methods would produce a correct
   * result. However, this can lead to a truncated parse tree when the resulting
   * node is inserted into a bigger parse tree. The parsing method should be
   * chosen such that when the resulting node is inserted into a parse tree, the
   * tree has the same structure as if it had been parsed as one piece.
   * 
   * For example, the code fragment {@code foo()} could be parsed as a
   * {@code functionCall}, a {@code primaryExpression}, an {@code expression} or
   * other enclosing parse rules. If it's inserted into an expression, it should
   * be parsed as an {@code expression} so that this rule isn't missing from the
   * parse tree. Using the wrong parse method often doesn't matter, but it can
   * cause tree matchers to not find the node if they are, for example, looking
   * for an {@code expression} specifically.
   * 
   * All nodes inserted into the parse tree must have properly configured parent
   * references or looking up a node's local root won't work. Other things in
   * ANTLR may also break if non-root nodes are missing their parent references.
   * 
   * @param <RuleType>  The type of the resulting parsed node
   * @param str         The string to be parsed
   * @param parent      The parent to be set on the node. All nodes will
   *                    eventually end up in the a main tree so some parent will
   *                    be available. Getting the siblings of the new node will
   *                    not work if no parent is set.
   * @param parseMethod The parser method with which the string is parsed
   * @return The resulting parsed node
   */
  protected <RuleType extends ExtendedContext> RuleType createLocalRoot(
      String str, ExtendedContext parent,
      Function<GLSLParser, RuleType> parseMethod) {
    var node = TransformationManager.INTERNAL.parse(str, parent, parseMethod);
    node.makeLocalRoot(TransformationManager.INTERNAL.tokenStream);
    return node;
  }

  /**
   * Shader code is expected to be roughly structured as follows:
   * version, extensions, other directives (#define, #pragma etc.), declarations
   * (layout etc.), functions (void main etc.).
   * 
   * These injection points can be used to insert nodes into the translation
   * unit's child list. An injection will happen before the syntax feature it
   * describes and any that follow it in the list.
   * 
   * @implNote AFTER versions of these points would be the same as the next BEFORE
   *           point in the list.
   */
  public enum InjectionPoint {
    /**
     * Before the #version statement (and all other syntax features by necessity)
     */
    BEFORE_VERSION,

    /**
     * Before the #extension statement, before other directives, declarations and
     * function definitions
     */
    BEFORE_EXTENSIONS() {
      @Override
      protected boolean checkChildRelevant(Class<?> childClass) {
        return BEFORE_DIRECTIVES.checkChildRelevant(childClass)
            || childClass == ExtensionStatementContext.class;
      }
    },

    /**
     * Before non-extension parsed #-directives such as #pragma, before
     * declarations and function definitions. (after extension statements if they
     * aren't mixed with other directives and directly follow the #version)
     * 
     * TODO: describe what happens to unparsed tokens that are in the stream
     */
    BEFORE_DIRECTIVES() {
      @Override
      protected boolean checkChildRelevant(Class<?> childClass) {
        return BEFORE_DECLARATIONS.checkChildRelevant(childClass)
            || childClass == PragmaStatementContext.class;
      }
    },

    /**
     * Before declarations like layout and struct, before function definitions
     */
    BEFORE_DECLARATIONS() {
      @Override
      protected boolean checkChildRelevant(Class<?> childClass) {
        return BEFORE_FUNCTIONS.checkChildRelevant(childClass)
            || childClass == LayoutDefaultsContext.class
            || DeclarationContext.class.isAssignableFrom(childClass);
      }
    },

    /**
     * Before function definitions
     */
    BEFORE_FUNCTIONS() {
      @Override
      protected boolean checkChildRelevant(Class<?> childClass) {
        return childClass == FunctionDefinitionContext.class;
      }
    },

    /**
     * Before the end of the file, basically the last possible location
     */
    BEFORE_EOF;

    /**
     * A set of the rule contexts that can make up an external declaration that each
     * injection point needs to inject before.
     */
    public Set<Class<? extends ParseTree>> EDBeforeTypes;

    /**
     * Checks if the given class of the child of an external declaration makes the
     * external declaration one that should be injected before. When this method
     * returns true, the injection happens right before the external declaration
     * that has the child that was found to be relevant.
     * 
     * @param childClass The class of the only child node of the external
     *                   declaration being tested for being a node before which the
     *                   injection has to happen
     * @return {@code true} if the class means the injection should happen before
     *         this external declaration according to the implementing injection
     *         location
     */
    protected boolean checkChildRelevant(Class<?> childClass) {
      throw new AssertionError("A non-special injection point doesn't have a child relevance implementation!");
    }
  }

  private int getInjectionIndex(InjectionPoint location) {
    var rootNode = getRootNode();
    var injectIndex = -1;

    if (location == InjectionPoint.BEFORE_VERSION) {
      injectIndex = rootNode.getChildIndexLike(VersionStatementContext.class);
      if (injectIndex == rootNode.getChildCount()) {
        injectIndex = 0;
      }
    } else if (location == InjectionPoint.BEFORE_EOF) {
      injectIndex = rootNode.getChildCount();
    } else {
      do {
        injectIndex++;
        if (rootNode.getChild(injectIndex) instanceof ExternalDeclarationContext externalDeclaration
            && externalDeclaration.getChild(0) instanceof ExtendedContext eChild
            && location.checkChildRelevant(eChild.getClass())) {
          break;
        }
      } while (injectIndex < rootNode.getChildCount());
    }
    return injectIndex;
  }

  /**
   * Injects the given node into the translation unit context root node at the
   * given injection point. Note that this may break things if used improperly (if
   * breaking the grammar's rules for example).
   * 
   * The {@code addChild} method sets the parent on the added node.
   * 
   * @implNote Since ANTLR's rule context stores children in an {@link ArrayList},
   *           this operation runs in linear time O(n) with respect to the the
   *           number n of external declarations in the root node.
   * 
   * @param location The injection point at which the new node is inserted
   * @param newNode  The new node to be inserted
   */
  protected void injectNode(InjectionPoint location, ParseTree newNode) {
    getRootNode().addChild(getInjectionIndex(location), newNode);
  }

  /**
   * Injects a list of nodes into the translation unit context node. Does the same
   * thing as {@link #injectNode(InjectionPoint, ParseTree)} but with a list of
   * nodes.
   * 
   * @param location The injection point at which the new nodes are inserted
   * @param newNodes The list of nodes to be inserted
   */
  protected void injectNodes(InjectionPoint location, Deque<ParseTree> newNodes) {
    var injectIndex = getInjectionIndex(location);
    var rootNode = getRootNode();
    newNodes.descendingIterator()
        .forEachRemaining(newNode -> rootNode.addChild(injectIndex, newNode));
  }

  /**
   * Injects an array of nodes at an injection location.
   * 
   * @see #injectNodes(InjectionPoint, Deque)
   * @param location The injection point at which the new nodes are inserted
   * @param newNodes The list of nodes to be inserted
   */
  protected void injectNodes(InjectionPoint location, ParseTree... newNodes) {
    var injectIndex = getInjectionIndex(location);
    var rootNode = getRootNode();
    for (var i = newNodes.length - 1; i >= 0; i--) {
      rootNode.addChild(injectIndex, newNodes[i]);
    }
  }

  /**
   * Injects the given string parsed as an external declaration. This is a
   * convenience method since most of the time injected nodes are external
   * declarations.
   * 
   * @see #injectNode(InjectionPoint, ParseTree)
   * @param location The injection point at which the new node is inserted
   * @param str      The code fragment to be parsed as an external declaration and
   *                 inserted at the given injection point
   */
  protected void injectExternalDeclaration(InjectionPoint location, String str) {
    injectNode(location, createLocalRoot(str, getRootNode(), GLSLParser::externalDeclaration));
  }

  /**
   * Injects multiple strings parsed as individual external declarations.
   * 
   * @see #injectNode(InjectionPoint, ParseTree)
   * @param location The injection point at which the new nodes are inserted
   * @param str      The strings to parse as external declarations and then insert
   */
  protected void injectExternalDeclarations(InjectionPoint location, String... str) {
    var nodes = new ParseTree[str.length];
    var rootNode = getRootNode();
    for (var i = 0; i < str.length; i++) {
      nodes[i] = createLocalRoot(str[i], rootNode, GLSLParser::externalDeclaration);
    }
    injectNodes(location, nodes);
  }

  /**
   * Injects a new {@code #define} statement at the specified location. This
   * method is for convenience since injecting defines is a common operation. For
   * other directives the {@link io.github.douira.glsl_transformer.ast.Directive }
   * class should be used.
   * 
   * @apiNote This method should be avoided if a direct replacement of identifiers
   *          using the appropriate core transformations is possible.
   * 
   * @param location The injection point at which the new node is inserted
   * @param content  The content after the #define prefix
   */
  protected void injectDefine(InjectionPoint location, String content) {
    injectNode(location, new Directive(Type.DEFINE, content));
  }
}
