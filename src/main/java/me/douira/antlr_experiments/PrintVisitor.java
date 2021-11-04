package me.douira.antlr_experiments;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The print visitor visits the parse tree and generates a list of tokens. These
 * tokens include those contributed by ReplacementNodes that were inserted for
 * program transformation.
 */
public class PrintVisitor extends GLSLParserBaseVisitor<List<Token>> {
  private BufferedTokenStream tokenStream;

  /**
   * Constructs a print visitor with a given token stream. The stream is used to
   * fetch tokens at particular index ranges.
   * 
   * @param tokenStream The token stream to fetch tokens from. Should be the one
   *                    that was also used for parsing or this all doesn't make
   *                    sense.
   */
  public PrintVisitor(BufferedTokenStream tokenStream) {
    this.tokenStream = tokenStream;
  }

  public String visitAndJoin(ParseTree tree) {
    var tokens = visit(tree);
    var builder = new StringBuilder(tokens.size());
    for (var token : tokens) {
      if (token.getType() != GLSLLexer.EOF) {
        builder.append(token.getText());
        builder.append(',');
      }
    }
    return builder.toString();
  }

  @Override
  public List<Token> visitChildren(RuleNode node) {
    final var context = (ParserRuleContext) node.getRuleContext();

    // get the token interval for this node (token indexes)
    final var superInterval = context.getSourceInterval();

    // list of tokens to return for this node
    // TODO: use a list that can concat two lists in O(1) for this later
    final var tokens = new LinkedList<Token>();

    // the index of the token that needs to be fetched next,
    // either by looking at a child or getting the token directly
    var fetchNext = superInterval.a;
    if (context.children != null) {
      for (var child : context.children) {
        // fetch the token between the last child (or the start) and this child
        var childInterval = child.getSourceInterval();
        if (fetchNext <= childInterval.a - 1) {
          tokens.addAll(tokenStream.get(fetchNext, childInterval.a - 1));
        }

        // add the tokens from the child's processing
        tokens.addAll(child.accept(this));

        // replacement nodes have -1,-1 intervals that will mess this up
        if (childInterval.b >= 0) {
          fetchNext = childInterval.b + 1;
        }
      }
    }

    // fetch all remaining tokens
    tokens.addAll(tokenStream.get(fetchNext, superInterval.b));

    return tokens;
  }

  @Override
  public List<Token> visitTerminal(TerminalNode node) {
    return new LinkedList<Token>(List.of(node.getSymbol()));
  }
}
