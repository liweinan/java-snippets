package io.alchemystudio.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasicProxyHandler implements InvocationHandler {

    private Object orginal;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println(method.getName());
        System.out.println("*ARGS*");

        for (Object arg : args) {
            System.out.println(arg.toString());
        }

        return method.invoke(args);
    }

    interface Foo {
        String echo(String val);
    }

    static class FooImpl implements Foo {
        public String echo(String val) {
            return val;
        }
    }

    public static void main(String[] args) {
        var p = (Foo) Proxy.newProxyInstance(
                BasicProxyHandler.class.getClassLoader(),
                new Class[]{Foo.class},
                new BasicProxyHandler());
        p.echo("Hello, world!");
    }
}
