package io.github.douira.glsl_transformer.ast.print.token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.ASTNode;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public class ParserToken extends PrintToken {
  public int tokenType;

  public ParserToken(ASTNode source, TokenChannel channel, int tokenType) {
    super(source, channel);
    this.tokenType = tokenType;
  }

  public ParserToken(ASTNode source, int tokenType) {
    super(source);
    this.tokenType = tokenType;
  }

  @Override
  public String getContent() {
    var literalName = GLSLLexer.VOCABULARY.getLiteralName(tokenType);
    if (literalName == null) {
      throw new IllegalStateException(
          "Can't create a parser token for a token type that doesn't have a defined literal name!");
    }
    return literalName.substring(1, literalName.length() - 1);
  }
}
