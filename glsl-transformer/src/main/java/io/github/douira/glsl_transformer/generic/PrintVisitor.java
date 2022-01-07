package io.github.douira.glsl_transformer.generic;

import java.util.LinkedList;

import com.github.bsideup.jabel.Desugar;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.ast.UnparsableASTNode;

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
   * @param rootTokenStream The token stream for the parse tree
   * @param tree            The parse tree to print
   * @return The printed parse tree that includes the results of the
   *         transformations
   */
  public static String printTree(BufferedTokenStream rootTokenStream, ExtendedContext tree) {
    tree.makeLocalRoot(rootTokenStream);
    return new PrintVisitor()
        .visitAndJoin(rootTokenStream, tree, tree.getFullSourceInterval());
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
   * @return The printed parse tree that is within the bounds and contains the
   *         results of the transformations applied to the tree
   */
  public String visitAndJoin(BufferedTokenStream rootTokenStream,
      ExtendedContext rootNode, Interval bounds) {
    // visit the whole tree and accumulate tokens and intervals
    currentRoot = rootNode;
    visit(rootNode);

    // convert the list of tokens and intervals into just tokens,
    // and then into their strings
    var builder = new StringBuilder(512); // guessing
    for (var printItem : printItems) {
      if (printItem instanceof String literal) {
        builder.append(literal);
      } else if (printItem instanceof AttributedInterval attributedInterval) {
        var interval = attributedInterval.interval();
        var localRoot = attributedInterval.localRoot();
        var omissionSet = localRoot.getOmissionSet();

        for (var token : localRoot.getTokenStream().getTokens(interval.a, interval.b)) {
          // don't print EOF, only print the tokens in side the printing bounds,
          // but always allow inserted nodes, only print non-omitted tokens
          var tokenIndex = token.getTokenIndex();
          if (token.getType() != Lexer.EOF
              && (tokenIndex == -1
                  || (localRoot != rootNode || inInterval(bounds, tokenIndex))
                      && omissionSet.isTokenAllowed(token))) {
            builder.append(token.getText());
          }
        }
      } else {
        throw new Error(
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
   *           roots, through
   *           this process. this happens because fetchNext is not updated for
   *           local roots
   *           which causes the next non-local-root child (or at the after end of
   *           the child
   *           list) to add the whole interval covering the child after the child
   *           itself has
   *           been visited.
   */
  @Override
  public Void visitChildren(RuleNode node) {
    final var context = (ExtendedContext) node.getRuleContext();
    final var superInterval = context.getLargestSourceInterval();
    var lastWasUnparsableASTNode = false;

    // keeps track of the token index that needs to be processed next
    var fetchNext = superInterval.a;

    if (context.children != null) {
      for (var child : context.children) {
        // handle unparsable AST nodes
        if (child instanceof UnparsableASTNode) {
          // insert a newline before each group of unparsable ast nodes.
          // line preservation doesn't matter here since it's being broken anyways
          if (!lastWasUnparsableASTNode) {
            addLiteral("\n");
            lastWasUnparsableASTNode = true;
          }

          // unparsable ast nodes have no source interval
          child.accept(this);
          continue;
        }
        lastWasUnparsableASTNode = false;

        // handle local root nodes
        if (child instanceof ExtendedContext childNode && childNode.isLocalRoot()) {
          // set as new current root
          var previousRoot = currentRoot;
          currentRoot = childNode;

          child.accept(this);
          currentRoot = previousRoot;
          continue;
        }

        // handle everything else (regular non-terminal and terminal nodes)
        // interval before the current child
        var childInterval = child.getSourceInterval();
        addInterval(fetchNext, childInterval.a - 1);

        child.accept(this);

        // prevent an empty child interval from messing up fetchNext by being negative
        if (childInterval.length() != 0) {
          fetchNext = childInterval.b + 1;
        }
      }
    }

    // interval after the last child
    addInterval(fetchNext, superInterval.b);
    return null;
  }

  @Override
  public Void visitTerminal(TerminalNode node) {
    // empty terminal nodes have an empty source interval and have no effect
    addInterval(node.getSourceInterval());

    // if this is an unparsable AST node, add the literal it produces
    if (node instanceof UnparsableASTNode astNode) {
      addLiteral(astNode.getText());
    }
    return null;
  }
}
