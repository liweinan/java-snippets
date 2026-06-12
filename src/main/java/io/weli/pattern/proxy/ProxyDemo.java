package io.weli.pattern.proxy;

public class ProxyDemo {

    public static void main(String[] args) {
        Greeter greeter = new GreeterProxy(new RealGreeter());
        System.out.println(greeter.greet("Alice"));
    }
}
