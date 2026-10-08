package io.github.douira.glsl_transformer.util;

import java.lang.annotation.*;

/**
 * This annotation causes the annotated method to be excluded from the jacoco
 * coverage report.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcludeFromJacocoGeneratedReport {
}
