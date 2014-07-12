package net.bluedash.snippets.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MethodSpy {
    public MethodSpy() {
    }

    public MethodSpy(String initialValue) {
    }


    public String foo(String input) {
        return input;
    }

    public List<String> bar(List<String> input) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName(MethodSpy.class.getName());
        Method[] allMethods = clazz.getDeclaredMethods();
        for (Method mtd : allMethods) {
            System.out.println("mtd.toGenericString() " + mtd.toGenericString());
            System.out.println("mtd.getGenericReturnType() " + mtd.getGenericReturnType());
            Class<?>[] paramTypes = mtd.getParameterTypes();
            Type[] genericParamTypes = mtd.getGenericParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.println("paramTypes: " + paramTypes[i]);
                System.out.println("genericParamTypes: " + genericParamTypes[i]);
            }
            System.out.println("-----");
        }

        for (Constructor constructor : clazz.getConstructors()) {
            System.out.println(constructor.toGenericString());
            int i = constructor.getModifiers();
            String retval = Modifier.toString(i);
            System.out.println("Class Modifier = " + retval);
        }
    }
}
