package io.weli.lang;

public class ThreadCurrentContext
{
    public static void main(String[] args) {
        var cl = Thread.currentThread().getContextClassLoader();
        System.out.println(cl);
    }
}
