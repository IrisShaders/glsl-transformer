package me.douira.antlr_experiments;

import org.antlr.v4.runtime.*;

public class MarkupApp {
    public static void main(String[] args) {
        CharStream inputStream = CharStreams
                .fromString("I would like to [b]emphasize[/b] this and [u]underline [b]that[/b][/u]. "
                        + "Let's not forget to quote: [quote author=\"John\"]You're wrong![/quote]");
        MarkupLexer markupLexer = new MarkupLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        MarkupParser markupParser = new MarkupParser(commonTokenStream);

        MarkupParser.FileContext fileContext = markupParser.file();
        MarkupVisitor visitor = new MarkupVisitor(System.out);
        visitor.visit(fileContext);
    }
}
