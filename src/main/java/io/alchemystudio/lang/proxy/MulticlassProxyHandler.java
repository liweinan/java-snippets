package io.alchemystudio.lang.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MulticlassProxyHandler extends BasicProxyHandler {

    Map<Class, Object> proxiedObjects = new HashMap<>();


    public MulticlassProxyHandler(Object... targets) {
        for (Object o : targets) {
            proxiedObjects.put(o.getClass(), o);
        }
    }

    public MulticlassProxyHandler(Object original) {
        super(original);
    }

    interface Bar {
        public void hello();
    }

    static class BarImpl implements Bar {
        @Override
        public void hello() {
            System.out.println("Hello, bar!");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(Proxy.isProxyClass(proxy.getClass()));
        System.out.println(method.getDeclaringClass().toString());
        return null;

    }

    public static void main(String[] args) {
        Foo foo = new FooImpl();
        Bar bar = new BarImpl();
        Proxy.newProxyInstance(
                MulticlassProxyHandler.class.getClassLoader(),
                new Class[]{Foo.class, Bar.class},
                new MulticlassProxyHandler(foo, bar));
    }
}
