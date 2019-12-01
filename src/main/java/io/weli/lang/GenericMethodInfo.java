package io.weli.lang;

//https://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
public class GenericMethodInfo {
    static class Foo<T> {
        private final Class<T> type;

        private T bar;

        public Foo(Class<T> type) {
            this.type = type;
        }

        public Class<T> getType() {
            return type;
        }
    }

    public static void main(String[] args) {
        var foo = new Foo<>(String.class);
        assert foo.getType() == String.class;

    }

}
