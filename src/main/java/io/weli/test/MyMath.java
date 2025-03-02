package io.weli.test;

public class MyMath {
    public static void main(String args[]) {
        Math myMath = (Value1, Value2) -> Value1 + Value2;
        System.out.println(myMath.DoMath(5, 6));
        System.out.println(myMath.Truncate(5, 6));
    }
}
