package io.weli.lang.invokedynamics;

/**
 * Created by weinanli on 11/06/2017.
 */
public class Calculator {
    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }
}
