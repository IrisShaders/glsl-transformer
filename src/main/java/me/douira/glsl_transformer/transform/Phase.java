package me.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.SyntaxTree;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.v4.runtime.tree.xpath.XPath;

import me.douira.glsl_transformer.GLSLParserBaseListener;
import me.douira.glsl_transformer.generic.EditContext;
import me.douira.glsl_transformer.generic.StringNode;

abstract class Phase extends GLSLParserBaseListener {
  private PhaseCollector parent;

  void setParent(PhaseCollector parent) {
    this.parent = parent;
  }

  protected EditContext getEditContext() {
    return parent.editContext;
  }

  protected Parser getParser() {
    return parent.parser;
  }

  protected void omitNodeTokens(SyntaxTree node) {
    getEditContext().omitNodeTokens(node);
  }

  protected static List<ParseTree> getSiblings(ParserRuleContext node) {
    return node.getParent().children;
  }

  protected void replaceNode(ParserRuleContext node, String newContents) {
    replaceNode(node, new StringNode(newContents));
  }

  protected void replaceNode(ParserRuleContext node, ParseTree newNode) {
    var children = getSiblings(node);
    children.set(children.indexOf(node), newNode);
    omitNodeTokens(node);
  }

  protected void removeNode(ParserRuleContext node) {
    // the node needs to be replaced with something to preserve the containing child
    // array's length or there's a NullPointerException in the walker
    replaceNode(node, new StringNode(null));
  }

  protected XPath compilePath(String xpath) {
    return new XPath(getParser(), xpath);
  }

  protected ParseTreePattern compilePattern(String pattern, int rootRule) {
    return getParser().compileParseTreePattern(pattern, rootRule);
  }

  /**
   * This method uses a statically constructed xpath so it doesn't need to be
   * repeatedly constructed. The subtrees yielded by the xpath need to start with
   * the rules that the pattern was constructed with or nothing will match.
   * 
   * Adapted from ANTLR's implementation of {@link ParseTreePattern.findAll}.
   * 
   * @param tree    The parse tree to find and match in
   * @param xpath   The xpath that leads to a subtree for matching
   * @param pattern The pattern that tests the subtrees for matches
   * @return A list of all matches resulting from the subtrees
   */
  public List<ParseTreeMatch> findAndMatch(ParseTree tree, XPath xpath, ParseTreePattern pattern) {
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

  protected void init() {
    // to be possibly overwritten by the implementing class
  }
}
