package net.bluedash.snippets.classloader.demos;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 6/29/12
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloImpl1 implements Hello {

    static int counter = 0;

    public HelloImpl1() {

        System.out.println("counter: " + counter);
        counter++;
    }
    public void sayHello() {
        System.out.println("Hello from HelloImpl1");
    }
}
