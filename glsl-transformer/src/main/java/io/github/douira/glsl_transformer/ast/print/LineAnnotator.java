package io.github.douira.glsl_transformer.ast.print;

import java.util.*;

import org.antlr.v4.runtime.misc.Interval;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.print.token.PrintToken;

/**
 * Inserts #line directives that make the compiler report errors as if they were
 * happening in the original source file.
 * 
 * The "#line line source" directive specifies the line and the source file. The
 * line number sets the line number of the line of the directive itself.
 */
public class LineAnnotator extends DelegateTokenProcessor {
  private int sourceLine = 1;
  private int annotatedLine = 1;
  private int outputLine = 1;
  private static int SOURCE_TYPE = 1;
  private static int SYNTHETIC_TYPE = 0;
  private int currentSourceState = -1;

  /**
   * Keeps track of the source line on which the last part that was printed for a
   * specific node was printed. For example, if a function definition spans 
   */
  private Map<ASTNode, Integer> currentLines = new HashMap<>();

  public LineAnnotator(TokenProcessor delegate) {
    super(delegate);
  }

  public LineAnnotator() {
    super(new SimplePrinter());
  }

  @Override
  public void appendToken(PrintToken token) {
    // mark the first line according to the first token
    var nodeSourceLines = token.getSource().getSourceLines();
    var isSource = isSourceInterval(nodeSourceLines);
    if (outputLine == 1) {
      annotateLine((isSource ? nodeSourceLines.a : outputLine) - 1, isSource);
    }
    super.appendToken(token);
    if (token.endsWithNewline()) {
      outputLine++;
      sourceLine++;

    }
  }

  private void annotateLine(int line, boolean fromSource) {
    var sourceType = fromSource ? SOURCE_TYPE : SYNTHETIC_TYPE;
    if (line != outputLine || sourceType != currentSourceState) {
      appendDirectly("#line " + line +
          (sourceType != currentSourceState ? " " + sourceType : "")
          + "\n");
      outputLine++;
    }
  }

  private boolean isSourceInterval(Interval sourceLines) {
    return sourceLines != ASTNode.SYNTHETIC_SOURCE;
  }
}
