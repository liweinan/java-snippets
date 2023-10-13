package io.weli.commons;

public class PlayWithStringFormat {
    public static void main(String[] args) {
        var str = String.format("a number: %s", 42);
        System.out.println(str);
    }
}
