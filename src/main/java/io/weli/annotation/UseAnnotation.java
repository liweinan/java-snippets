package io.weli.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class UseAnnotation {

    @MyAnnotation(foo ="Hello, world!")
    public static void foo() {

    }

    public static void main(String[] args) throws Exception{
        Method method = UseAnnotation.class.getMethod("foo");

        for (Annotation annotation : method.getAnnotations()) {

            if (annotation.annotationType() == MyAnnotation.class) {
                System.out.println(annotation);
                System.out.println(
                        ((MyAnnotation) annotation).foo()
                );
            }
        }
    }
}
