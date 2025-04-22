package io.weli.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/26/16.
 */
public class GetAndPut {

    static class Fruit {}
    static class Apple extends Fruit {}
    static class Orange extends Fruit {}
    static class RedApple extends Apple {}

    public static void main(String[] args) {
        // Producer - extends (GET)
        List<? extends Fruit> fruits = new ArrayList<Apple>();  // OK
        Fruit fruit = fruits.get(0);                           // OK
        // fruits.add(new Apple());                            // Error
        // fruits.add(new Fruit());                            // Error
        // fruits.add(new Object());                           // Error

        // Consumer - super (PUT)
        List<? super Fruit> basket = new ArrayList<Object>();  // OK
        basket.add(new Apple());                               // OK
        basket.add(new Fruit());                               // OK
        // basket.add(new Object());                           // Error
        
        // Demonstrating PECS - Producer Extends, Consumer Super
        transfer(fruits, basket);                              // OK
    }

    private static void transfer(List<? extends Fruit> source, List<? super Fruit> dest) {
        for (Fruit f : source) {
            dest.add(f);
        }
    }
}