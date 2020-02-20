package io.alchemystudio.lang;

import java.lang.annotation.Annotation;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class FooApp {
    FooAnnotation getInstance(final String foo) {
        FooAnnotation annotation = new FooAnnotation() {
            @Override
            public String foo() {
                return foo;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return BarAnnotation.class;
            }
        };

        return annotation;

    }

    public static void main(String[] args) {
        FooApp app = new FooApp();
        FooAnnotation fa = app.getInstance("Martian!");

        System.out.println(fa.annotationType());
    }
}
