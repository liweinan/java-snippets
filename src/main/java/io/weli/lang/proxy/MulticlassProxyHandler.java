package io.weli.lang.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

// 一个proxy代理多个classes
public class MulticlassProxyHandler extends BasicProxyHandler {

    // 存放要代理的classes
    Map<Class, Object> delegates = new HashMap<>();

    public MulticlassProxyHandler(Object... targets) {
        for (Object o : targets) {
            // interfaces作为keys
            delegates.put(o.getClass().getInterfaces()[0], o);
        }
    }

    interface Bar {
        void bar(String val);
    }

    static class BarImpl implements Bar {
        @Override
        public void bar(String val) {
            System.out.println(val);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 取出对应class的delegate，并进行invoke()。
        return method.invoke(delegates.get(method.getDeclaringClass()), args);
    }

    public static void main(String[] args) {
        Foo foo = new FooImpl();
        Bar bar = new BarImpl();

        // 一个proxy同时代理`foo`和`bar`。
        Object proxy = Proxy.newProxyInstance(
                MulticlassProxyHandler.class.getClassLoader(),
                new Class[]{Foo.class, Bar.class},
                new MulticlassProxyHandler(foo, bar));

        // 把proxy给cast成foo或者bar来使用。
        ((Foo) proxy).echo("Hello, foo!");
        ((Bar) proxy).bar("Hello, bar!");

    }
}
