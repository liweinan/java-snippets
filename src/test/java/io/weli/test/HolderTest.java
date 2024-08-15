package io.weli.test;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolderTest {
    public static class Holder<T> {
        private final T value;

        private Holder(final T value) {
            this.value = value;
        }

        T get() {
            return value;
        }

        public static Holder<String> valueOf(String value) {
            return new Holder<>(value);
        }
    }

    @Test
    public void test_test() {
        Holder hello = Holder.valueOf("Hello, world!");
        assertEquals("Hello, world!", hello.get());
    }
}
