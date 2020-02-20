package io.alchemystudio.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

public class PassthroughInvocationHandler implements InvocationHandler {

   private final Object target;

   public PassthroughInvocationHandler(Object target) {
      this.target = target;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      return method.invoke(target, args);
   }

   @SuppressWarnings("unchecked")
   public static <T> T proxying(T target, Class<T> iface) {
      return (T) Proxy.newProxyInstance(
              iface.getClassLoader(),
              new Class<?>[]{iface},
              new PassthroughInvocationHandler(target));
   }

   public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>... otherIfaces) {
      Class<?>[] allInterfaces = Stream.concat(
              Stream.of(iface),
              Stream.of(otherIfaces))
              .distinct()
              .toArray(Class<?>[]::new);

      return (T) Proxy.newProxyInstance(
              iface.getClassLoader(),
              allInterfaces,
              handler);
   }

   public static <T> T passthroughProxy(Class<? extends T> iface, T target) {
      return simpleProxy(iface, new PassthroughInvocationHandler(target));
   }

   public static void main(String[] args) {

   }
}
