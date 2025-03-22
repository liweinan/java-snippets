package io.weli.misc;

public class MyClass {
    String Howdy = "Hello There!";
    static MyOtherClass otherClass;

    MyClass() {
        otherClass = new MyOtherClass();
    }

    public static void main(String[] args) {
        System.out.println(otherClass.Goodbye);
    }


    class MyOtherClass {
        static public String Goodbye = "So Long!";

        MyOtherClass() {
        }

    }
}