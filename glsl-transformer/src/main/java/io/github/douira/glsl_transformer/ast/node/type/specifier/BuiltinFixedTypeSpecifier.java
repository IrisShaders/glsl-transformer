package io.github.douira.glsl_transformer.ast.node.type.specifier;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BuiltinFixedTypeSpecifier extends TypeSpecifier {
  public enum BuiltinType implements TokenTyped {
    VOID(GLSLLexer.VOID, TypeKind.VOID),
    ATOMIC_UINT(GLSLLexer.ATOMIC_UINT, TypeKind.ATOMIC_UINT),
    SAMPLER2D(GLSLLexer.SAMPLER2D, TypeKind.SAMPLER),
    SAMPLER3D(GLSLLexer.SAMPLER3D, TypeKind.SAMPLER),
    SAMPLERCUBE(GLSLLexer.SAMPLERCUBE, TypeKind.SAMPLER),
    SAMPLER2DSHADOW(GLSLLexer.SAMPLER2DSHADOW, TypeKind.SAMPLER),
    SAMPLERCUBESHADOW(GLSLLexer.SAMPLERCUBESHADOW, TypeKind.SAMPLER),
    SAMPLER2DARRAY(GLSLLexer.SAMPLER2DARRAY, TypeKind.SAMPLER),
    SAMPLER2DARRAYSHADOW(GLSLLexer.SAMPLER2DARRAYSHADOW, TypeKind.SAMPLER),
    SAMPLERCUBEARRAY(GLSLLexer.SAMPLERCUBEARRAY, TypeKind.SAMPLER),
    SAMPLERCUBEARRAYSHADOW(GLSLLexer.SAMPLERCUBEARRAYSHADOW, TypeKind.SAMPLER),
    ISAMPLER2D(GLSLLexer.ISAMPLER2D, TypeKind.SAMPLER),
    ISAMPLER3D(GLSLLexer.ISAMPLER3D, TypeKind.SAMPLER),
    ISAMPLERCUBE(GLSLLexer.ISAMPLERCUBE, TypeKind.SAMPLER),
    ISAMPLER2DARRAY(GLSLLexer.ISAMPLER2DARRAY, TypeKind.SAMPLER),
    ISAMPLERCUBEARRAY(GLSLLexer.ISAMPLERCUBEARRAY, TypeKind.SAMPLER),
    USAMPLER2D(GLSLLexer.USAMPLER2D, TypeKind.SAMPLER),
    USAMPLER3D(GLSLLexer.USAMPLER3D, TypeKind.SAMPLER),
    USAMPLERCUBE(GLSLLexer.USAMPLERCUBE, TypeKind.SAMPLER),
    USAMPLER2DARRAY(GLSLLexer.USAMPLER2DARRAY, TypeKind.SAMPLER),
    USAMPLERCUBEARRAY(GLSLLexer.USAMPLERCUBEARRAY, TypeKind.SAMPLER),
    SAMPLER1D(GLSLLexer.SAMPLER1D, TypeKind.SAMPLER),
    SAMPLER1DSHADOW(GLSLLexer.SAMPLER1DSHADOW, TypeKind.SAMPLER),
    SAMPLER1DARRAY(GLSLLexer.SAMPLER1DARRAY, TypeKind.SAMPLER),
    SAMPLER1DARRAYSHADOW(GLSLLexer.SAMPLER1DARRAYSHADOW, TypeKind.SAMPLER),
    ISAMPLER1D(GLSLLexer.ISAMPLER1D, TypeKind.SAMPLER),
    ISAMPLER1DARRAY(GLSLLexer.ISAMPLER1DARRAY, TypeKind.SAMPLER),
    USAMPLER1D(GLSLLexer.USAMPLER1D, TypeKind.SAMPLER),
    USAMPLER1DARRAY(GLSLLexer.USAMPLER1DARRAY, TypeKind.SAMPLER),
    SAMPLER2DRECT(GLSLLexer.SAMPLER2DRECT, TypeKind.SAMPLER),
    SAMPLER2DRECTSHADOW(GLSLLexer.SAMPLER2DRECTSHADOW, TypeKind.SAMPLER),
    ISAMPLER2DRECT(GLSLLexer.ISAMPLER2DRECT, TypeKind.SAMPLER),
    USAMPLER2DRECT(GLSLLexer.USAMPLER2DRECT, TypeKind.SAMPLER),
    SAMPLERBUFFER(GLSLLexer.SAMPLERBUFFER, TypeKind.SAMPLER),
    ISAMPLERBUFFER(GLSLLexer.ISAMPLERBUFFER, TypeKind.SAMPLER),
    USAMPLERBUFFER(GLSLLexer.USAMPLERBUFFER, TypeKind.SAMPLER),
    SAMPLER2DMS(GLSLLexer.SAMPLER2DMS, TypeKind.SAMPLER),
    ISAMPLER2DMS(GLSLLexer.ISAMPLER2DMS, TypeKind.SAMPLER),
    USAMPLER2DMS(GLSLLexer.USAMPLER2DMS, TypeKind.SAMPLER),
    SAMPLER2DMSARRAY(GLSLLexer.SAMPLER2DMSARRAY, TypeKind.SAMPLER),
    ISAMPLER2DMSARRAY(GLSLLexer.ISAMPLER2DMSARRAY, TypeKind.SAMPLER),
    USAMPLER2DMSARRAY(GLSLLexer.USAMPLER2DMSARRAY, TypeKind.SAMPLER),
    IMAGE2D(GLSLLexer.IMAGE2D, TypeKind.IMAGE),
    IIMAGE2D(GLSLLexer.IIMAGE2D, TypeKind.IMAGE),
    UIMAGE2D(GLSLLexer.UIMAGE2D, TypeKind.IMAGE),
    IMAGE3D(GLSLLexer.IMAGE3D, TypeKind.IMAGE),
    IIMAGE3D(GLSLLexer.IIMAGE3D, TypeKind.IMAGE),
    UIMAGE3D(GLSLLexer.UIMAGE3D, TypeKind.IMAGE),
    IMAGECUBE(GLSLLexer.IMAGECUBE, TypeKind.IMAGE),
    IIMAGECUBE(GLSLLexer.IIMAGECUBE, TypeKind.IMAGE),
    UIMAGECUBE(GLSLLexer.UIMAGECUBE, TypeKind.IMAGE),
    IMAGEBUFFER(GLSLLexer.IMAGEBUFFER, TypeKind.IMAGE),
    IIMAGEBUFFER(GLSLLexer.IIMAGEBUFFER, TypeKind.IMAGE),
    UIMAGEBUFFER(GLSLLexer.UIMAGEBUFFER, TypeKind.IMAGE),
    IMAGE1D(GLSLLexer.IMAGE1D, TypeKind.IMAGE),
    IIMAGE1D(GLSLLexer.IIMAGE1D, TypeKind.IMAGE),
    UIMAGE1D(GLSLLexer.UIMAGE1D, TypeKind.IMAGE),
    IMAGE1DARRAY(GLSLLexer.IMAGE1DARRAY, TypeKind.IMAGE),
    IIMAGE1DARRAY(GLSLLexer.IIMAGE1DARRAY, TypeKind.IMAGE),
    UIMAGE1DARRAY(GLSLLexer.UIMAGE1DARRAY, TypeKind.IMAGE),
    IMAGE2DRECT(GLSLLexer.IMAGE2DRECT, TypeKind.IMAGE),
    IIMAGE2DRECT(GLSLLexer.IIMAGE2DRECT, TypeKind.IMAGE),
    UIMAGE2DRECT(GLSLLexer.UIMAGE2DRECT, TypeKind.IMAGE),
    IMAGE2DARRAY(GLSLLexer.IMAGE2DARRAY, TypeKind.IMAGE),
    IIMAGE2DARRAY(GLSLLexer.IIMAGE2DARRAY, TypeKind.IMAGE),
    UIMAGE2DARRAY(GLSLLexer.UIMAGE2DARRAY, TypeKind.IMAGE),
    IMAGECUBEARRAY(GLSLLexer.IMAGECUBEARRAY, TypeKind.IMAGE),
    IIMAGECUBEARRAY(GLSLLexer.IIMAGECUBEARRAY, TypeKind.IMAGE),
    UIMAGECUBEARRAY(GLSLLexer.UIMAGECUBEARRAY, TypeKind.IMAGE),
    IMAGE2DMS(GLSLLexer.IMAGE2DMS, TypeKind.IMAGE),
    IIMAGE2DMS(GLSLLexer.IIMAGE2DMS, TypeKind.IMAGE),
    UIMAGE2DMS(GLSLLexer.UIMAGE2DMS, TypeKind.IMAGE),
    IMAGE2DMSARRAY(GLSLLexer.IMAGE2DMSARRAY, TypeKind.IMAGE),
    IIMAGE2DMSARRAY(GLSLLexer.IIMAGE2DMSARRAY, TypeKind.IMAGE),
    UIMAGE2DMSARRAY(GLSLLexer.UIMAGE2DMSARRAY, TypeKind.IMAGE);

    public enum TypeKind {
      VOID,
      ATOMIC_UINT,
      SAMPLER,
      IMAGE;
    }

    public final int tokenType;
    public final TypeKind kind;

    private BuiltinType(int tokenType, TypeKind kind) {
      this.tokenType = tokenType;
      this.kind = kind;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static BuiltinType fromToken(Token token) {
      return TypeUtil.enumFromToken(BuiltinType.values(), token);
    }
  }

  public BuiltinType type;

  public BuiltinFixedTypeSpecifier(BuiltinType type) {
    this.type = type;
  }

  public BuiltinFixedTypeSpecifier(BuiltinType type, ArraySpecifier arraySpecifier) {
    super(arraySpecifier);
    this.type = type;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.BULTIN_FIXED;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitBuiltinFixedTypeSpecifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal nodes have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    // terminal nodes have no children
  }

  @Override
  public BuiltinFixedTypeSpecifier clone() {
    return (BuiltinFixedTypeSpecifier) super.clone();
  }

  @Override
  public BuiltinFixedTypeSpecifier cloneInto(Root root) {
    return (BuiltinFixedTypeSpecifier) super.cloneInto(root);
  }

  @Override
  public BuiltinFixedTypeSpecifier cloneSeparate() {
    return (BuiltinFixedTypeSpecifier) super.cloneSeparate();
  }
}
