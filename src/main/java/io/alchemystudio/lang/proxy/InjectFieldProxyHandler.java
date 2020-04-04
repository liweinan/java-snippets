package io.alchemystudio.lang.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 展示如何从proxy里面提取handler。
// 这样就可以在handler里面封装metadata。
public class InjectFieldProxyHandler extends BasicProxyHandler {

    public InjectFieldProxyHandler(Object original) {
        super(original);
    }

    // 这个方法取不到。
    // 这个用来封装一个`metadata`的field。
    private Object metadata;

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(getOriginal(), args); // 直接pass给original的对应method。
    }

    public static void main(String[] args) {
        Foo foo = new FooImpl();

        Foo proxy = (Foo) Proxy.newProxyInstance(
                InjectFieldProxyHandler.class.getClassLoader(),
                new Class[]{Foo.class},
                new InjectFieldProxyHandler(foo));

        proxy.echo("Hello, world!");

        InjectFieldProxyHandler handler = (InjectFieldProxyHandler) Proxy.getInvocationHandler(proxy);
        handler.setMetadata(System.currentTimeMillis());

        System.out.println(handler.getMetadata());
    }
}
