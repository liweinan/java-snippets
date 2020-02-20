package io.alchemystudio.lang.lambda;

import java.util.function.IntBinaryOperator;

public class IntBinaryOperatorImpl implements IntBinaryOperator {
    @Override
    public int applyAsInt(int left, int right) {
        return Math.max(left, right);
    }
}
