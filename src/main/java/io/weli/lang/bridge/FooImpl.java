package io.weli.lang.bridge;

/**
 * Created by weli on 15/05/2017.
 */
public class FooImpl implements Foo {

    @Override
    public String bar() {
        return "bar";
    }

    public Object bar$$bridge() {
        return bar() + " HACKED!!!";
    }
}
