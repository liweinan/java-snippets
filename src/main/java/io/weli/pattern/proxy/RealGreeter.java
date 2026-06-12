package io.weli.pattern.proxy;

public class RealGreeter implements Greeter {

    @Override
    public String greet(String name) {
        return "Hello, " + name;
    }
}
