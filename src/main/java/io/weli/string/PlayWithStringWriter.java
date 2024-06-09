package io.weli.string;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PlayWithStringWriter {
    public static void main(String[] args) {
        var e = new IllegalArgumentException("foo");
        var str = getStackTraceAsString(e);
        System.out.println("-----------------------------------");
        System.out.println(str);
        System.out.println("-----------------------------------");
        System.out.println(e); // no stack trace
        System.out.println("-----------------------------------");
    }

    static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
