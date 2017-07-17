package lang.invokedynamics;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by weinanli on 09/06/2017.
 */

public class InsertArguments {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.findStatic(Math.class, "pow",
                MethodType.methodType(double.class,
                        double.class,
                        double.class));
        System.out.printf("2^10 = %d%n", (int) (double) handle.invoke(2.0));
        handle = MethodHandles.insertArguments(handle, 1, 10);
    }
}
