package io.weli.reflection;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ClassCast {
    static class A {
        public static void show() {
            System.out.println("Class A show() function");
        }
    }

    static class B extends A {
        public static void show() {
            System.out.println("Class B show() function");
        }
    }

    public static void main(String[] args) {

        ClassCast cast = new ClassCast();
        Class clazz = cast.getClass();
        System.out.println(clazz);

        Object a = new A();
        B b = new B();
        b.show();

        // casts object
        A castA = A.class.cast(b);

        System.out.println(a.getClass());
        System.out.println(b.getClass());
        System.out.println(castA.getClass());
        System.out.println(A.class.cast(b).getClass());
    }
}
