package io.weli.lang.lambda;

import java.util.function.Function;

// https://stackoverflow.com/questions/26771953/return-lambda-from-method-in-java-8
public class ReturnLambda {

    Function<Integer, String> getMyFunction() {
        return new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                for (var i = 0; i < integer; i++) {
                    System.out.print(".");
                }
                return "DONE";
            }
        };
    }

    public static void main(String[] args) {
        System.out.println(new ReturnLambda().getMyFunction().apply(3));
    }
}
