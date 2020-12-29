package io.weli.lang.invokedynamics.demo;

import java.lang.invoke.*;

/**
 * Created by weinanli on 09/06/2017.
 */
public class IDDL {
    private static MethodHandle handle;

    private static void helloWorld() {
        System.out.println("Hello, World!");
    }
    

    public static CallSite bootstrapDynamic(MethodHandles.Lookup caller,
                                            String name,
                                            MethodType type)
            throws IllegalAccessException, NoSuchMethodException {

        System.out.println("bootstrap method called!");
        System.out.println("name: " + name);
        System.out.println("name force changed to helloWorld");
        
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class thisClass = lookup.lookupClass();
        handle = lookup.findStatic(thisClass, "helloWorld",
                MethodType.methodType(void.class));
        if (!type.equals(handle.type()))
            handle = handle.asType(type);

        return new ConstantCallSite(handle);
    }
}
