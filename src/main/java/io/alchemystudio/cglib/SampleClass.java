package io.alchemystudio.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

// https://dzone.com/articles/cglib-missing-manual
public class SampleClass {
    public String test() {
        return "Hello, world!";
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback((FixedValue) () -> "Hello, Martian!");
        SampleClass proxy = (SampleClass) enhancer.create();
        System.out.println(proxy.test());
    }
}
