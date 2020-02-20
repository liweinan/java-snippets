package io.alchemystudio.lang.lambda;

import java.util.function.IntBinaryOperator;

public class DoubleColon {
    public static void accept(IntBinaryOperator op) {
        // void
    }

    public static void main(String[] args) throws Exception {
        accept(Math::max);
    }
}
