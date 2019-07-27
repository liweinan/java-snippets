package io.weli.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/26/16.
 */
public class GetAndPut {

    public static interface Fruit {
    }

    public static class Apple implements Fruit {
    }

    public static class Pear implements Fruit {
    }

    public static void main(String[] args) {
        List<? extends Fruit> badFruits = new ArrayList();
//        badFruits.add(new Apple()); // type not safe

        List<? super Fruit> fruits = new ArrayList();
        fruits.add(new Apple());
        fruits.add(new Pear());

        List<? extends Fruit> buckets = (List<? extends Fruit>) fruits;
        System.out.println(buckets.get(0));
        System.out.println(buckets.get(1));
    }
}