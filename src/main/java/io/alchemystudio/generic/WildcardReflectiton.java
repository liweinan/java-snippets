package io.alchemystudio.generic;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * Created by weli on 4/28/16.
 */
public class WildcardReflectiton {

    public static class Foo {
        public static List<? extends Number> p() {
            return null;
        }

        public static List<? super Number> q() {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Method p = Foo.class.getMethod("p");
        System.out.println(p);

        Type pType = p.getGenericReturnType();
        System.out.println(pType);
        System.out.println(pType.getClass());

        if (pType instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) pType).getActualTypeArguments();

            Type typeArg = typeArgs[0];
            System.out.println(typeArg);
            System.out.println(typeArg.getClass());

            if (typeArg instanceof WildcardType) {
                Type[] uppers = ((WildcardType) typeArg).getUpperBounds();
                System.out.println(uppers[0]);
                System.out.println(uppers[0].getClass());
            }
        }

    }
}
