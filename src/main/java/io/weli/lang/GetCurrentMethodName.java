package io.weli.lang;

public class GetCurrentMethodName {
    public void foo() {
        bar();
    }

    public void bar() {
        for (var s : Thread.currentThread().getStackTrace()) {
            System.out.println(s.getMethodName());
        }

    }

    public static void main(String[] args) {
        var inst = new GetCurrentMethodName();
        inst.foo();
    }
}
