package me.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

public record AttributedInterval(ParseTree localRoot, Interval interval) {
}
