package io.weli.lambda;

public class Foo {

    private FooOp op;

    public void setup(FooOp op) {
        this.op = op;
    }

    public void foo(String val) {
        op.foo(val);
    }
}
