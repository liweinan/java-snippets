package io.weli.misc;

public class Person {
    String name;
    Integer id;

    Person(String n, Integer i) {
        name = n;
        id = i;
    }

    Person(Integer i) {
        name = null;
        id = i;
    }

    @Override
    public String toString() {
        return name + " " + id;
    }

}
