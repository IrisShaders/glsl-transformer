package io.github.douira.glsl_transformer.ast.print.token;

import java.util.*;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.print.TokenRole;
import io.github.douira.glsl_transformer.cst.token_filter.TokenChannel;

public class ParserToken extends PrintToken {
  private static final Map<Integer, String> missingTokenStrings = new HashMap<>() {
    {
      put(GLSLLexer.PRAGMA_INVARIANT, "invariant");
      put(GLSLLexer.INVARIANT, "invariant");
      put(GLSLLexer.LPAREN, "(");
      put(GLSLLexer.NR_LPAREN, "(");
      put(GLSLLexer.RPAREN, ")");
      put(GLSLLexer.NR_RPAREN, ")");
      put(GLSLLexer.PP_EMPTY, "#\n");
      put(GLSLLexer.COLON, ":");
      put(GLSLLexer.NR_COLON, ":");
    }
  };

  public final int tokenType;

  public ParserToken(TokenChannel channel, TokenRole role, int tokenType) {
    super(channel, role);
    this.tokenType = tokenType;
  }

  public ParserToken(TokenRole role, int tokenType) {
    super(role);
    this.tokenType = tokenType;
  }

  public ParserToken(TokenChannel channel, int tokenType) {
    super(channel);
    this.tokenType = tokenType;
  }

  public ParserToken(int tokenType) {
    super();
    this.tokenType = tokenType;
  }

  @Override
  public String getContent() {
    var literalName = GLSLLexer.VOCABULARY.getLiteralName(tokenType);
    if (literalName == null) {
      var replacement = missingTokenStrings.get(tokenType);
      if (replacement != null) {
        return replacement;
      }
      throw new IllegalStateException(
          "Can't create a parser token for a token type that doesn't have a defined literal name!");
    }
    return literalName.substring(1, literalName.length() - 1);
  }
}
