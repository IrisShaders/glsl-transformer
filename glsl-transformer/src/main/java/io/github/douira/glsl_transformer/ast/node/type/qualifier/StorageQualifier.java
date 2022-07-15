package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import java.util.List;
import java.util.stream.Stream;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class StorageQualifier extends TypeQualifierPart {
  public enum StorageType implements TokenAssociatedEnum {
    CONST(GLSLLexer.CONST),
    IN(GLSLLexer.IN),
    OUT(GLSLLexer.OUT),
    INOUT(GLSLLexer.INOUT),
    CENTROID(GLSLLexer.CENTROID),
    PATCH(GLSLLexer.PATCH),
    SAMPLE(GLSLLexer.SAMPLE),
    UNIFORM(GLSLLexer.UNIFORM),
    VARYING(GLSLLexer.VARYING),
    ATTRIBUTE(GLSLLexer.ATTRIBUTE),
    BUFFER(GLSLLexer.BUFFER),
    SHARED(GLSLLexer.SHARED),
    RESTRICT(GLSLLexer.RESTRICT),
    VOLATILE(GLSLLexer.VOLATILE),
    COHERENT(GLSLLexer.COHERENT),
    READONLY(GLSLLexer.READONLY),
    WRITEONLY(GLSLLexer.WRITEONLY),
    SUBROUTINE(GLSLLexer.SUBROUTINE),
    DEVICECOHERENT(GLSLLexer.DEVICECOHERENT),
    QUEUEFAMILYCOHERENT(GLSLLexer.QUEUEFAMILYCOHERENT),
    WORKGROUPCOHERENT(GLSLLexer.WORKGROUPCOHERENT),
    SUBGROUPCOHERENT(GLSLLexer.SUBGROUPCOHERENT),
    NONPRIVATE(GLSLLexer.NONPRIVATE);

    public final int tokenType;

    private StorageType(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static StorageType fromToken(Token token) {
      return TypeUtil.enumFromToken(StorageType.values(), token);
    }
  }

  public final List<Identifier> typeNames; // TODO: nullable (optional)
  public StorageType storageType;

  public StorageQualifier(Stream<Identifier> typeNames) {
    this.typeNames = ChildNodeList.collect(typeNames, this);
    this.storageType = StorageType.SUBROUTINE;
  }

  public StorageQualifier(StorageType storageType) {
    this.typeNames = null;
    this.storageType = storageType;
  }

  @Override
  public QualifierType getQualifierType() {
    return QualifierType.STORAGE;
  }

  @Override
  public <R> R typeQualifierPartAccept(ASTVisitor<R> visitor) {
    return visitor.visitStorageQualifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterStorageQualifier(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitStorageQualifier(this);
  }
}
