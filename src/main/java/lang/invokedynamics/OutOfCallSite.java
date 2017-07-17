package lang.invokedynamics;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by weinanli on 09/06/2017.
 */
public class OutOfCallSite {
    public static void main(String[] args) throws Throwable {
        Foo foo = new Foo();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.findVirtual(Foo.class, "hello1",
                MethodType.methodType(void.class));
        handle.invoke(foo);
        handle = lookup.findVirtual(Foo.class, "hello2",
                MethodType.methodType(void.class));
    }
}
