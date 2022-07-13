package io.github.douira.glsl_transformer.cst.print;

import java.util.LinkedList;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;

import com.github.bsideup.jabel.Desugar;

import io.github.douira.glsl_transformer.cst.node.UnparsableCSTNode;
import io.github.douira.glsl_transformer.cst.token_filter.TokenFilter;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The print visitor visits the parse tree and reprints it while preserving the
 * position and content of hidden tokens. This means it preserves all newlines
 * even if the parsed nodes that contains them was removed. This is useful
 * because it doesn't necessarily change the line numbers during patching.
 * 
 * Local roots in the parse tree tell print visitor what changes have been made
 * to the original parse tree and from which alternative token streams it has to
 * read when it prints newly inserted nodes (=local roots) and their subtrees.
 */
public class PrintVisitor extends AbstractParseTreeVisitor<Void> {
  /**
   * The attributed interval is used to attribute intervals in the print list to
   * local root nodes. Which root node an interval belongs to is used during
   * printing to read from the token stream for which the interval was
   * constructed.
   */
  @Desugar
  private static record AttributedInterval(ExtendedContext localRoot, Interval interval) {
  }

  private final LinkedList<Object> printItems = new LinkedList<>();
  private Interval cachedInterval;
  private ExtendedContext currentRoot;

  private PrintVisitor() {
  }

  private static boolean inInterval(Interval interval, int el) {
    return interval.a <= el && interval.b >= el;
  }

  /**
   * Prints the given parse tree that references the given token stream. Sets up
   * the given node as a root node if that hasn't happened already.
   * 
   * If the given token filter is not {@code null}, it will be used to check if
   * each otherwise qualified token should be printed.
   * 
   * @param rootTokenStream The token stream for the parse tree
   * @param tree            The parse tree to print
   * @param tokenFilter     An additional token filter to check before printing
   *                        each otherwise qualified token
   * @return The printed parse tree that includes the results of the
   *         transformations
   */
  public static String printTree(
      BufferedTokenStream rootTokenStream, ExtendedContext tree, TokenFilter<?> tokenFilter) {
    tree.makeLocalRoot(rootTokenStream);
    return new PrintVisitor()
        .visitAndJoin(rootTokenStream, tree, tree.getFullSourceInterval(), tokenFilter);
  }

  /**
   * Prints the given parse tree that references the given token stream. Sets up
   * the given node as a root node if that hasn't happened already.
   * 
   * @see #visitAndJoin(BufferedTokenStream, ExtendedContext, Interval,
   *      TokenFilter)
   * @see #printTree(BufferedTokenStream, ExtendedContext, TokenFilter)
   * 
   * @param rootTokenStream The token stream for the parse tree
   * @param tree            The parse tree to print
   * @return The printed parse tree that includes the results of the
   *         transformations
   */
  public static String printTree(
      BufferedTokenStream rootTokenStream, ExtendedContext tree) {
    return printTree(rootTokenStream, tree, null);
  }

  /**
   * This is the lowest level printing method that should be used publicly. It
   * takes a parse tree and a token stream and prints it. Additionally an interval
   * is given that determines which tokens of the string should be printed.
   * 
   * @see #printTree(BufferedTokenStream, ExtendedContext)
   * @param rootTokenStream The token stream for the parse tree
   * @param rootNode        The parse tree to print
   * @param bounds          An token index interval of the tokens that should be
   *                        included in the printed string
   * @param tokenFilter     An additional token filter for checking if tokens
   *                        should be printed
   * @return The printed parse tree that is within the bounds and contains the
   *         results of the transformations applied to the tree
   */
  private String visitAndJoin(BufferedTokenStream rootTokenStream,
      ExtendedContext rootNode, Interval bounds, TokenFilter<?> tokenFilter) {
    // visit the whole tree and accumulate tokens and intervals
    currentRoot = rootNode;
    visit(rootNode);

    if (tokenFilter != null) {
      tokenFilter.resetState();
    }

    // convert the list of tokens and intervals into just tokens,
    // and then into their strings
    var builder = new StringBuilder(512); // guessing
    for (var printItem : printItems) {
      if (printItem instanceof String literal) {
        builder.append(literal);
      } else if (printItem instanceof AttributedInterval attributedInterval) {
        var interval = attributedInterval.interval();
        var localRoot = attributedInterval.localRoot();
        var omissionSet = localRoot.getLocalRootTokenOmissions();

        for (var token : localRoot.getTokenStream().getTokens(interval.a, interval.b)) {
          // don't print EOF, only print the tokens in side the printing bounds,
          // but always allow inserted nodes, only print non-omitted tokens
          var tokenIndex = token.getTokenIndex();
          if (token.getType() != Lexer.EOF
              && (tokenIndex == -1
                  || (localRoot != rootNode || inInterval(bounds, tokenIndex))
                      && omissionSet.tokenNotOmitted(token)
                      && (tokenFilter == null || tokenFilter.isTokenAllowed(token)))) {
            builder.append(token.getText());
          }
        }
      } else {
        throw new AssertionError(
            "A wrong type of object was inserted into the printItems! Only String and AttributedInterval are allowed.");
      }
    }
    return builder.toString();
  }

  /**
   * Adds an interval to the current list of intervals to print. The interval is
   * attributed to the current local root node.
   * 
   * @param a The left bound of the interval
   * @param b The right bound of the interval
   */
  private void addInterval(int a, int b) {
    // doing this check here saves an object construction if the interval is invalid
    // (and it often is invalid)
    if (a > b || a < 0 || b < 0) {
      return;
    }

    // cache a single interval since often the same interval is added repeatedly
    Interval interval;
    if (cachedInterval != null && a == cachedInterval.a && b == cachedInterval.b) {
      interval = cachedInterval;
    } else {
      interval = Interval.of(a, b);
      cachedInterval = interval;
    }

    addInterval(interval);
  }

  /**
   * Adds an interval to the current list of intervals to print. The interval is
   * attributed to the current local root node. If possible, this will join the
   * interval with the last added interval in order to reduce memory usage and
   * overhead during printing. Oftentimes the intervals are adjacent and share the
   * same local root, which makes them joinable. If an interval is empty, nothing
   * needs to be done with it since it can't contribute any tokens for printing.
   * 
   * @param newInterval The interval to add to the print list
   */
  private void addInterval(Interval newInterval) {
    if (newInterval.length() == 0) {
      return;
    }

    // join the given interval onto the last interval if possible without holes
    if (!printItems.isEmpty()) {
      var lastPrintItem = printItems.getLast();

      // only check for combining if last item is also an interval
      if (lastPrintItem instanceof AttributedInterval lastPrintInterval) {
        var lastInterval = lastPrintInterval.interval();
        if (lastInterval != null
            && currentRoot == lastPrintInterval.localRoot()
            && (!lastInterval.disjoint(newInterval)
                || lastInterval.adjacent(newInterval))) {
          if (lastInterval.properlyContains(newInterval)) {
            return;
          }

          printItems.removeLast();
          newInterval = lastInterval.union(newInterval);
        }
      }
    }

    printItems.add(new AttributedInterval(currentRoot, newInterval));
  }

  /**
   * Adds a string as a literal to be printed to the print list
   * 
   * @param literal
   */
  private void addLiteral(String literal) {
    if (literal == null || literal.isEmpty()) {
      return;
    }

    printItems.add(literal);
  }

  /**
   * {@inheritDoc}
   * 
   * The visitor method that the print visitor overrides in order to collect each
   * node's intervals. The current local root is updated when a local root node is
   * found. Any created intervals for printing are attributed to the current local
   * root in order to know which token stream the interval's indexes refer to.
   * 
   * @implNote intervals that cover tokens that are not part of children (which
   *           are only hidden tokens like whitespace because terminal nodes are
   *           children too) are moved after inserted nodes, which are local
   *           roots, through this process. this happens because fetchNext is not
   *           updated for local roots which causes the next non-local-root child
   *           (or at the after end of the child list) to add the whole interval
   *           covering the child after the child itself has been visited.
   */
  @Override
  public Void visitChildren(RuleNode node) {
    final var context = (ExtendedContext) node.getRuleContext();
    final var superInterval = context.getLargestSourceInterval();
    var lastWasUnparsableASTNode = false;

    // keeps track of the token index that needs to be processed next
    var fetchNext = superInterval.a;

    // iterate all children of this node. This uses a for loop so that previous and
    // next children can be accessed
    if (context.children != null) {
      var childrenLength = context.children.size();
      for (int i = 0; i < childrenLength; i++) {
        var child = context.children.get(i);

        // prettify unparsable AST nodes
        if (child instanceof UnparsableCSTNode) {
          // insert a newline before each group of unparsable ast nodes.
          // line preservation doesn't matter here since it's being broken anyways
          if (!lastWasUnparsableASTNode && ((UnparsableCSTNode) child).doNewlineInsertion()) {
            addLiteral("\n");
            lastWasUnparsableASTNode = true;
          }

          // unparsable ast nodes have no source interval (length 0)
          // they will follow the path into opening detection
        } else {
          lastWasUnparsableASTNode = false;
        }

        // local root node handling requires updating the current root node from which
        // the openings are retrieved and that is annotated on the intervals
        if (child instanceof ExtendedContext childNode && childNode.isLocalRoot()) {
          // set as new current root
          var previousRoot = currentRoot;
          currentRoot = childNode;

          child.accept(this);
          currentRoot = previousRoot;
        } else {
          // handle everything else (regular non-terminal and terminal nodes)
          // interval before the current child
          var childInterval = child.getSourceInterval();
          if (childInterval.length() > 0) {
            addInterval(fetchNext, childInterval.a - 1);
            child.accept(this);

            // prevent an empty child interval from messing up fetchNext by being negative
            // (fetchNext is only updated if childInterval is not empty)
            fetchNext = childInterval.b + 1;
          } else {
            // detect openings left by removed nodes for new nodes that have no source
            // interval (e.g. unparsable ast nodes but also other such nodes).
            // The interval has to be invalid, not just empty since some normal nodes have
            // empty intervals when they represent empty lists (like empty function
            // parameter lists)
            if (childInterval == Interval.INVALID) {
              var previousChildEnd = i > 0
                  ? context.children.get(i - 1).getSourceInterval().b
                  : -1;

              // find the next opening that this node can be inserted into
              // but make sure it's between the end of the last child, the beginning of the
              // next child and not before the current fetchNext token position. (if fetchNext
              // is corrupted, the printer will wrongly output tokens multiple times)
              var nextOpening = currentRoot.getLocalRootOpenings().higher(previousChildEnd);
              if (nextOpening != null && nextOpening >= fetchNext) {
                var nextChildStart = i < childrenLength - 1
                    ? context.children.get(i + 1).getSourceInterval().a
                    : -1;
                if (nextChildStart == -1 || nextOpening < nextChildStart) {
                  // add the interval of tokens up to the next opening and advance the nextIndex
                  // so that the whitespace before the opening is placed before the inserted node
                  addInterval(fetchNext, nextOpening);
                  fetchNext = nextOpening + 1;
                }
              }
            }
            child.accept(this);
          }
        }
      }
    }

    // interval after the last child
    addInterval(fetchNext, superInterval.b);
    return null;
  }

  @Override
  public Void visitTerminal(TerminalNode node) {
    // empty terminal nodes have an empty source interval and will have no effect
    addInterval(node.getSourceInterval());

    // if this is an unparsable AST node, add the literal it produces
    if (node instanceof UnparsableCSTNode astNode) {
      addLiteral(astNode.getText());
    }
    return null;
  }
}
