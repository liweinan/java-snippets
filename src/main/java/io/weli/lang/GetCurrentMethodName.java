package io.weli.lang;


/**
 * getStackTrace
 * bar
 * foo
 * main
 * -----------
 * main
 */
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
        System.out.println("-----------");
        inst.getCurrentMethodName();
    }

    public void getCurrentMethodName() {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
