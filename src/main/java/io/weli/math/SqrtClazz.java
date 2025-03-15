package io.weli.math;

public class SqrtClazz {
    public static void main(String[] args) {
        long x = 16;
        var a = Math.sqrt(x);
        System.out.println(String.valueOf(a));
        System.out.println(String.valueOf(a).matches(".*\\.\\d+"));
        System.out.println(Math.log(4));
        System.out.println(Math.log(2));
        System.out.println(Math.log(3));
        System.out.println((Math.log(4) / Math.log(2)) % 1);
        System.out.println((Math.log(3) / Math.log(2)) % 1);
    }
}
