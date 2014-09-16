package net.bluedash.snippets.reflect;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithGetMethod {

    public void varArgs(String... args) {
        System.out.println(args.getClass());
    }

    public static void main(String[] args) throws Exception {
        Class[] argTypes = new Class[]{String[].class};
        System.out.println(String[].class);

        Method method = PlayWithGetMethod.class.getMethod("varArgs", argTypes);

        String[] mainArgs = {"foo", "bar"};
        method.invoke(new PlayWithGetMethod(), (Object) mainArgs); // without Object typecasting it will fail.
    }
}
