package lang.lambda;

/**
 * Created by weli on 22/05/2017.
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
