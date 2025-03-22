package io.weli.misc;

import java.util.Arrays;
import java.util.List;

public class AddSub {
    static int index = 0;

    public static void main(String[] args) {
        List<MathOperation> myList = Arrays.asList(null, null);
        args = new String[]{"ADD", "SUBTRACT"};
        String[] finalArgs = args;
        myList.forEach(m ->
                System.out.print(m.calculate(finalArgs[index++], 1, 2) + " "));
    }

    class MathOperation {
        public static int calculate(String choice, int a, int b) {
            int c = 3;
            switch (choice) {
                case "ADD":
                    c += a + b;
                case "SUBTRACT":
                    c += a - b;
                case "MULTIPLY":
                    c += a * b;
                case "DIVIDE":
                    c += a / b;
                default:
                    c += a * b;
            }
            return c;
        }
    }


}
