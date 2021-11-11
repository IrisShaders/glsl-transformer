package me.douira.glsl_transformer.transform;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

public class MatchPhase extends Phase {
  abstract static class ContextMatch {
    Class<? extends ParserRuleContext> matchClass;

    ContextMatch() {
    }

    ContextMatch(Class<? extends ParserRuleContext> matchClass) {
      this.matchClass = matchClass;
    }

    ContextMatch matches(ParserRuleContext ctx) {
      return ctx.getClass() == matchClass ? this : null;
    }
  }

  public static class ImmediateMatch extends ContextMatch {
    public ImmediateMatch(Class<? extends ParserRuleContext> matchClass, ContextMatch child) {
      super(matchClass);
    }
  }

  public static class DepthMatch extends ContextMatch {
    public DepthMatch(Class<? extends ParserRuleContext> matchClass, ContextMatch child) {
      super(matchClass);
    }
  }

  public static class AnyDepthMatch extends ContextMatch {
    public AnyDepthMatch(Class<? extends ParserRuleContext> matchClass, ContextMatch child) {
      super(matchClass);
    }
  }

  public static class MultiMatch extends ContextMatch {
    public MultiMatch(Class<? extends ParserRuleContext> matchClass, ContextMatch child) {
      super(matchClass);
    }
  }

  private ContextMatch matchRoot;

  private Map<Class<? extends ParserRuleContext>, Collection<ContextMatch>> nextMatches;

  private int depth = 0;

  public MatchPhase(ContextMatch matchRoot) {
    this.matchRoot = matchRoot;
    expectMatch(matchRoot);
  }

  private void expectMatch(ContextMatch match) {
    var collection = nextMatches.get(match.matchClass);
    if (collection == null) {
      collection = new LinkedList<ContextMatch>();
      nextMatches.put(match.matchClass, collection);
    }
    collection.add(match);
  }

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    // get all the waiting matchers
    var matched = nextMatches.get(ctx.getClass());
  }
}
