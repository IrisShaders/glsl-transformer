package io.github.douira.glsl_transformer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Provides parameterized test arguments from a .cases test case file. The test
 * cases with the given name are read. If no name is provided, the name of the
 * snapshot is used instead. If that is also no set, then the name of the method
 * is used.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(TestCaseProvider.class)
public @interface TestCaseSource {
  /**
   * The name of the test case set to use.
   * @return The name of the test case set
   */
  String value() default "";
}
