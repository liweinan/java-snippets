package io.alchemystudio.lang.lambda;

import java.util.function.Function;

// https://stackoverflow.com/questions/26771953/return-lambda-from-method-in-java-8
public class ReturnLambda {

    Function<Integer, String> getMyFunction() {
        return counter -> {
            for (var i = 0; i < counter; i++) {
                System.out.print(".");
            }
            return "DONE";
        };
    }

    // https://www.baeldung.com/java-8-lambda-expressions-tips
    private String buildString(String parameter) {
        String result = "Something " + parameter;
        return result;
    }


    @FunctionalInterface
    public interface Foo {
        String method(String string);
    }

    public String add(String string, Foo foo) {
        return foo.method(string);
    }

    public static void main(String[] args) {
        new ReturnLambda().run();
    }

    private void run() {
        getMyFunction().apply(3);

        {
            Foo foo = param -> buildString(param);
            String out = foo.method("Hello, world!");
            System.out.println(out);
        }
    }
}
