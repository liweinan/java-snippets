package io.alchemystudio.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TestTypeAndRawType {

    private List<Integer> foo;

    public static void main(String[] args) {
        Field field = TestTypeAndRawType.class.getDeclaredFields()[0];
        System.out.println(field.getType());

        Type type = field.getGenericType();
        System.out.println(type);
    }
}
