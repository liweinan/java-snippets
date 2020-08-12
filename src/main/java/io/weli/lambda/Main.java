package io.weli.lambda;

public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setup(val -> System.out.println(val));
        foo.foo("Hello, world!");

        foo.setup(val -> System.out.println(val.toUpperCase()));
        foo.foo("Hello, Martian!");
    }
}
