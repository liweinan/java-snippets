package io.alchemystudio.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target(METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String foo();
}
