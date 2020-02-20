package io.alchemystudio.lang.invokedynamics;

/**
 * Created by weinanli on 11/06/2017.
 */
public class PlayWithLambda {
    public static void main(String[] args) throws Exception {
        Calculator myApp = new Calculator();
        Calculator.IntegerMath addition = (a, b) -> a + b;
        Calculator.IntegerMath subtraction = (a, b) -> a - b;

        myApp.operateBinary(40, 2, addition);
        myApp.operateBinary(20, 10, subtraction);
    }
}
