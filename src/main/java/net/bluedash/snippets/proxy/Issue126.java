package net.bluedash.snippets.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* http://www.javaspecialists.eu/archive/Issue126.html */
public class Issue126 {
    public static interface Forest {
        String getColour();
    }

    public static class ForestImpl implements Forest {
        private final String colour;

        public ForestImpl(String colour) {
            this.colour = colour;
        }

        public String getColour() {
            return colour;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Forest)) return false;
            Forest forest = (Forest) o;
            return colour.equals(forest.getColour());
        }

    }

    public static class DelegationHandler implements InvocationHandler {
        private final Object wrapped;

        public DelegationHandler(Object wrapped) {
            this.wrapped = wrapped;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            System.out.println("Called: " + method);
            return method.invoke(wrapped, args);
        }
    }

    public static void main(String[] args) {
        DelegationHandler dh = new DelegationHandler(
                new ForestImpl("Black"));
        Forest i0 = make(dh);
        Forest i1 = make(dh);
        System.out.println(i0 == i1); // should be false
        System.out.println(i0.equals(i1)); // should be true

    }

    private static Forest make(DelegationHandler dh) {
        return (Forest) Proxy.newProxyInstance(
                Forest.class.getClassLoader(),
                new Class[]{Forest.class}, dh);
    }
}
