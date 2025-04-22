package io.weli.clazz;

import org.testng.annotations.Test;

import java.util.List;

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

    public <T> Object[] safe(List<T> elements) {
        return elements.toArray();
    }

    public <T> Object[] solid(T seed, Class clazzToCheck) {
        Object[] plants = safe(List.of(seed, seed, seed));
        // type safety check
        for (Object p : plants) {
            assert (p.getClass() == clazzToCheck);
        }
        return plants;
    }

    public void bury() {
        String s = "seed";
        Object[] safeTypedSeeds = solid(s, s.getClass());
    }

    public void brokenBury() {
        String s = "seed";
        Object[] seeds = solid(s, Integer.class);
    }

    @Test(expectedExceptions = {ClassCastException.class})
    public void testPlant() {
        plant();
    }

    @Test
    public void testBury() {
        bury();
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testBrokenBury() {
        brokenBury();
    }

}
