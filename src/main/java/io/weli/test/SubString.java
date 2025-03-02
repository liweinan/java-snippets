package io.weli.test;

public class SubString {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder ("buffering");
        sb.replace (2, 7, "BUFFER");
        System.out.println(sb.toString());
        sb.delete (2, 4);
        System.out.println(sb.toString());
        String s = sb.substring (1, 5);
        System.out.println(s);
    }
}

