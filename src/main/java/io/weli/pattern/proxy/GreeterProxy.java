package io.weli.pattern.proxy;

public class GreeterProxy implements Greeter {

    private final Greeter target;

    public GreeterProxy(Greeter target) {
        this.target = target;
    }

    @Override
    public String greet(String name) {
        System.out.println("proxy: calling greet(" + name + ")");
        String result = target.greet(name);
        System.out.println("proxy: result = " + result);
        return result;
    }
}
