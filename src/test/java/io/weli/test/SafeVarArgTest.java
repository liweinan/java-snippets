package io.weli.test;

import org.testng.annotations.Test;

//https://www.baeldung.com/java-safevarargs
public class SafeVarArgTest {

    public <T> T[] unsafe(T... elements) {
        return elements; // unsafe! don't ever return a parameterized varargs array
    }

    public <T> T[] broken(T seed) {
        T[] plant = unsafe(seed, seed, seed); // broken! This will be an Object[] no matter what T is
        return plant;
    }

    public void plant() {
        String[] plants = broken("seed"); // ClassCastException
    }

    @Test(expectedExceptions = {ClassCastException.class})
    public void testPlant() {
        plant();
    }

}
