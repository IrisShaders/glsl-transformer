package io.github.douira.glsl_transformer.generic;

import java.util.LinkedList;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The print visitor visits the parse tree and generates a list of tokens. These
 * tokens include those contributed by ReplacementNodes that were inserted for
 * program transformation. This is not a listener because we want to explicitly
 * trigger the visits to subtrees.
 */
public class PrintVisitor extends AbstractParseTreeVisitor<Void> {
  private record AttributedInterval(ParseTree localRoot, Interval interval) {
  }

  private final LinkedList<AttributedInterval> tokenIntervals = new LinkedList<>();
  private Interval cachedInterval;
  private ParseTree currentRoot;

  private PrintVisitor() {
  }

  private static boolean inInterval(Interval interval, int el) {
    return interval.a <= el && interval.b >= el;
  }

  public static String printTree(BufferedTokenStream rootTokenStream, ParseTree tree) {
    return printTree(rootTokenStream, tree, new EditContext(tree, rootTokenStream));
  }

  public static String printTree(BufferedTokenStream rootTokenStream, ParseTree tree, EditContext editContext) {
    return new PrintVisitor().visitAndJoin(rootTokenStream, tree, Interval.of(0, rootTokenStream.size() - 1),
        editContext);
  }

  public String visitAndJoin(BufferedTokenStream rootTokenStream, ParseTree rootNode, Interval bounds,
      EditContext editContext) {
    // add the tokens before the root node too
    var rootInterval = rootNode.getSourceInterval();
    addInterval(bounds.a, rootInterval.a - 1);

    // visit the whole tree and accumulate tokens and intervals
    currentRoot = rootNode;
    visit(rootNode);

    // and also the tokens after the root node
    addInterval(rootInterval.b + 1, bounds.b);

    // convert the list of tokens and intervals into just tokens,
    // and then into their strings
    var builder = new StringBuilder(512); // guessing
    for (var attributedInterval : tokenIntervals) {
      var interval = attributedInterval.interval();
      var localRootData = editContext.getLocalRootData(attributedInterval.localRoot());
      var omissionSet = localRootData.omissionSet();

      for (var token : localRootData.tokenStream().getTokens(interval.a, interval.b)) {
        // don't print EOF, only print the tokens in side the printing bounds,
        // but always allow inserted nodes,
        // if an edit context is given, only print if allowed by it
        var tokenIndex = token.getTokenIndex();
        if (token.getType() != Lexer.EOF && (tokenIndex == -1
            || inInterval(bounds, tokenIndex) && (omissionSet == null || omissionSet.isTokenAllowed(token)))) {
          builder.append(token.getText());
          // builder.append(',');
        }
      }
    }
    return builder.toString();
  }

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

  private void addInterval(Interval newInterval) {
    if (newInterval.length() == 0) {
      return;
    }

    // join the given interval onto the last interval if possible without holes
    if (!tokenIntervals.isEmpty()) {
      var last = tokenIntervals.getLast();
      var lastInterval = last.interval();
      if (currentRoot == last.localRoot()
          && (!lastInterval.disjoint(newInterval) || lastInterval.adjacent(newInterval))) {
        if (lastInterval.properlyContains(newInterval)) {
          return;
        }

        tokenIntervals.removeLast();
        newInterval = lastInterval.union(newInterval);
      }
    }

    tokenIntervals.add(new AttributedInterval(currentRoot, newInterval));
  }

  // NOTE: intervals that cover tokens that are not part of children (which are
  // only hidden tokens like whitespace because terminal nodes are children too)
  // are moved after inserted nodes, which are local roots, through this process.
  // this happens because fetchNext is not updated for local roots which causes
  // the next non-local-root child (or at the after end of the child list) to add
  // the whole interval covering the child after the child itself has been
  // visited.
  @Override
  public Void visitChildren(RuleNode node) {
    final var context = (ParserRuleContext) node.getRuleContext();
    final var superInterval = context.getSourceInterval();

    var fetchNext = superInterval.a;
    if (context.children != null) {
      for (var child : context.children) {
        var isLocalRoot = child.getParent() == null;

        ParseTree previousRoot = null;
        Interval childInterval = null;
        if (isLocalRoot) {
          // set as new current root
          previousRoot = currentRoot;
          currentRoot = child;
        } else {
          childInterval = child.getSourceInterval();
          addInterval(fetchNext, childInterval.a - 1);
        }

        child.accept(this);

        if (isLocalRoot) {
          // switch root back
          currentRoot = previousRoot;
        } else {
          fetchNext = childInterval.b + 1;
        }
      }
    }

    addInterval(fetchNext, superInterval.b);
    return null;
  }

  @Override
  public Void visitTerminal(TerminalNode node) {
    // empty terminal nodes have an empty source interval and have no effect
    addInterval(node.getSourceInterval());
    return null;
  }
}
