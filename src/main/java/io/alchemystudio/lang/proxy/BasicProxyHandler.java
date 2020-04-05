package io.alchemystudio.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasicProxyHandler implements InvocationHandler {

    private Object original;

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }

    public BasicProxyHandler(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method: " + method.getName());
        Object returnVal = method.invoke(original, args);
        System.out.println("done");
        return returnVal;
    }

    interface Foo {
        void echo(String val);
    }

    static class FooImpl implements Foo {
        public void echo(String val) {
            System.out.println(val);
        }
    }

    public static void main(String[] args) {
        Foo foo = new FooImpl();

        Foo proxy = (Foo) Proxy.newProxyInstance(
                BasicProxyHandler.class.getClassLoader(),
                new Class[]{Foo.class},
                new BasicProxyHandler(foo));
        proxy.echo("Hello, world!");
        System.out.println(proxy.getClass());
    }
}
