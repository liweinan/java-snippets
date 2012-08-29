package net.bluedash.snippets.generics;

import java.lang.reflect.Method;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public class TryBridge {

    public static void main(String[] args) {
        for (Method m : Inte.class.getMethods())
            if (m.getName().equals("compareTo"))
                System.out.println(m.toGenericString());
    }
}
