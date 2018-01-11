package lang.lambda;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleDemo {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle =
                lookup.findStatic(MethodHandleDemo.class, "hello", MethodType.methodType(void.class));
        handle.invokeExact();
    }

    static void hello() {
        System.out.println("Hello");
    }
}
