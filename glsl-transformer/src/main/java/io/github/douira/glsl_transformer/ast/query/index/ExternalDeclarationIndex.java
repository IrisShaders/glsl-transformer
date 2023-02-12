package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.*;

import com.github.bsideup.jabel.Desugar;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.FunctionPrototype;
import io.github.douira.glsl_transformer.ast.query.index.ExternalDeclarationIndex.DeclarationEntry;

public class ExternalDeclarationIndex<S extends Set<DeclarationEntry>, I extends Map<String, S>>
    extends StringKeyedIndex<DeclarationEntry, ExternalDeclaration, S, I> {
  public ExternalDeclarationIndex(I index, Supplier<S> setFactory) {
    super(index, setFactory);
  }

  @Desugar
  public static record DeclarationEntry(ExternalDeclaration declaration, ASTNode keyMember) {
  }

  @Override
  protected ExternalDeclaration getNode(DeclarationEntry entry) {
    return entry.declaration;
  }

  private void iterateKeyMembers(ExternalDeclaration node, BiConsumer<ASTNode, String> memberConsumer) {
    if (node instanceof DeclarationExternalDeclaration declarationExternalDeclaration) {
      Declaration declaration = declarationExternalDeclaration.getDeclaration();
      if (declaration instanceof TypeAndInitDeclaration typeAndInitDeclaration) {
        for (var member : typeAndInitDeclaration.getMembers()) {
          memberConsumer.accept(member, member.getName().getName());
        }
      } else if (declaration instanceof FunctionDeclaration functionDeclaration) {
        memberConsumer.accept(node, functionDeclaration.getFunctionPrototype().getName().getName());
      } else if (declaration instanceof InterfaceBlockDeclaration interfaceBlockDeclaration) {
        // TODO: use the block name or the variable name (which can be null)?
        memberConsumer.accept(node, interfaceBlockDeclaration.getBlockName().getName());
      } else if (declaration instanceof VariableDeclaration variableDeclaration) {
        for (var name : variableDeclaration.getNames()) {
          memberConsumer.accept(name, name.getName());
        }
      }

      // skips PrecisionDeclaration
    } else if (node instanceof FunctionDefinition functionDefinition) {
      memberConsumer.accept(node, functionDefinition.getFunctionPrototype().getName().getName());
    } else if (node instanceof ExtensionDirective extensionDirective) {
      memberConsumer.accept(node, extensionDirective.name);
    } else if (node instanceof CustomDirective customDirective) {
      memberConsumer.accept(node, customDirective.content);
    } else if (node instanceof IncludeDirective includeDirective) {
      memberConsumer.accept(node, includeDirective.content);
    }

    // skips PragmaDirective, LayoutDefaults, EmptyDeclaration
  }

  private void addEntry(ExternalDeclaration node, ASTNode keyMember, String key) {
    var set = index.get(key);
    if (set == null) {
      set = setFactory.get();
      index.put(key, set);
    }
    set.add(new DeclarationEntry(node, keyMember));
  }

  private void removeEntry(ExternalDeclaration node, ASTNode keyMember, String key) {
    var set = index.get(key);
    if (set == null) {
      return;
    }
    set.remove(new DeclarationEntry(node, keyMember));
    if (set.isEmpty()) {
      index.remove(key);
    }
  }

  @Override
  public void add(ExternalDeclaration node) {
    iterateKeyMembers(node, (keyMember, key) -> {
      addEntry(node, keyMember, key);
    });
  }

  @Override
  public void remove(ExternalDeclaration node) {
    iterateKeyMembers(node, (keyMember, key) -> {
      removeEntry(node, keyMember, key);
    });
  }

  private void iterateSubtreeEntries(
      ASTNode node,
      ASTNode nodeParent,
      BiConsumer<ASTNode, String> memberConsumer) {
    // only some nodes are interesting since most other types of nodes don't
    // influence the key of an external declaration entry
    if (node == null) {
      return;
    }

    // if the modified node is a parent that can have children, they need to be
    // iterated
    if (node instanceof TypeAndInitDeclaration typeAndInitDeclaration) {
      for (var member : typeAndInitDeclaration.getMembers()) {
        memberConsumer.accept(member, member.getName().getName());
      }
      return;
    }
    if (node instanceof VariableDeclaration variableDeclaration) {
      for (var name : variableDeclaration.getNames()) {
        memberConsumer.accept(name, name.getName());
      }
      return;
    }

    // from this point on, only a singe key member will be generated

    String singleKey = null;
    if (node instanceof Identifier identifier) {
      singleKey = identifier.getName();
      node = node.getParent();
    }

    // step up through the various admissable node types
    if (node instanceof DeclarationMember) {
      node = node.getParent();
    }
    if (node instanceof FunctionPrototype) {
      node = node.getParent();
    }
    if (node instanceof FunctionDefinition) {
      memberConsumer.accept(node, singleKey);
      return;
    }
    if (node instanceof VariableDeclaration
        || node instanceof TypeAndInitDeclaration
        || node instanceof InterfaceBlockDeclaration
        || node instanceof FunctionDeclaration) {
      node = node.getParent();
    }
    if (node instanceof DeclarationExternalDeclaration) {
      memberConsumer.accept(node, singleKey);
      return;
    }
  }

  /**
   * Run when some other node is added. This is necessary to keep track
   * of the key members since their names determine the keys of the external
   * declarations stored in this index.
   * 
   * This method should only be called on the root node of the subtree being
   * registered to avoid complication of repeatedly adding parts of the same
   * subtree.
   * 
   * @param subtreeRoot the node that was added, not an external declaration
   */
  public void notifySubtreeAdd(ASTNode subtreeRoot) {
    iterateSubtreeEntries(subtreeRoot, subtreeRoot.getParent(), (keyMember, key) -> {
      addEntry(keyMember.getAncestor(ExternalDeclaration.class), keyMember, key);
    });
  }

  /**
   * Method to notify of a node being removed. For notification of a subtree
   * change so that the external declaration index can be updated, see
   * {@link #notifySubtreeAdd(ASTNode)}.
   * 
   * @param subtreeRoot the node that was removed, not an external declaration
   */
  public void notifySubtreeRemove(ASTNode subtreeRoot) {
    // when the node is unregistered, the parent has already been removed. The last
    // parent (the one that was just removed) must be used instead.
    iterateSubtreeEntries(subtreeRoot, subtreeRoot.getLastParent(), (keyMember, key) -> {
      removeEntry(keyMember.getAncestor(ExternalDeclaration.class), keyMember, key);
    });
  }
}
