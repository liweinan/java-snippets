package lang.instrument;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileInputStream;
import java.io.PrintWriter;

/**
 * Created by weli on 14/05/2017.
 */
public class TestInstrumented {
    public static void main(String[] args) {
        System.err.println("CALL printOne");
        printOne();
        System.err.println("RETURN printOne");

        System.err.println("CALL printOne");
        printOne();
        System.err.println("RETURN printOne");

        System.err.println("CALL printTwo");
        printTwo();


    }

    public static void printOne() {
        System.err.println("CALL println");
        System.out.println("Hello World");
        System.err.println("RETURN println");
    }

    public static void printTwo() {
        System.err.println("CALL printOne");
        printOne();
        System.err.println("RETURN printOne");

        System.err.println("CALL printOne");
        printOne();
        System.err.println("RETURN printOne");
    }
}
