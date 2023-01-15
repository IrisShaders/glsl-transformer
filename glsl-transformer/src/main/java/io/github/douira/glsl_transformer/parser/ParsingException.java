package io.github.douira.glsl_transformer.parser;

import java.util.function.Supplier;

import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ParsingException extends RuntimeException {
  public ParsingException(String message) {
    super(message);
  }

  public ParsingException(String message, ParseCancellationException cause) {
    super(message, cause);
  }

  public ParsingException(ParseCancellationException cause) {
    super(cause);
  }

  public ParsingException() {
    super();
  }

  @Override
  public synchronized ParseCancellationException getCause() {
    return (ParseCancellationException) super.getCause();
  }

  public static ParseCancellationException extractParseCancellationException(RuntimeException exception) {
    if (exception instanceof ParsingException) {
      return ((ParsingException) exception).getCause();
    } else {
      return (ParseCancellationException) exception;
    }
  }

  public static Runnable thrower(String message) {
    return () -> {
      throw new ParsingException(message);
    };
  }

  public static Supplier<ParsingException> supplier(String message) {
    return () -> new ParsingException(message);
  }
}
