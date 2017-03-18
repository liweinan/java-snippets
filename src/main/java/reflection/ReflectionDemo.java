package reflection;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by weli on 4/14/16.
 */
public class ReflectionDemo {

    public static void foo() {

    }

    public static void main(String[] args) throws Exception {
//        public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable
        Class<?> clazz = Class.forName("java.lang.Enum");

        // print class name
        System.out.println(clazz.getName());

        // print type parameters
        for (TypeVariable<?> typeParam : clazz.getTypeParameters()) {
            System.out.println(typeParam.getName());
            // print bounds
            for (Type bound : typeParam.getBounds()) {
                System.out.println(bound);
                System.out.println(bound.getClass());
            }
        }
    }
}
