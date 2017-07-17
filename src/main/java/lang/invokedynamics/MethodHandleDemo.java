package lang.invokedynamics;

/**
 * Created by weinanli on 07/06/2017.
 * http://www.javaworld.com/article/2860079/learn-java/invokedynamic-101.html
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleDemo {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findStatic(MethodHandleDemo.class, "hello",
                MethodType.methodType(void.class));
        mh.invokeExact();
    }

    static void hello() {
        System.out.println("hello");
    }
}