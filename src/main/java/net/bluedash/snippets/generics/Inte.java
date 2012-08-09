package net.bluedash.snippets.generics;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public class Inte implements Comp<Inte> {
    private final int value;

    public Inte(int value) {
        this.value = value;
    }

    public int compareTo(Inte i) {
        return (value < i.value) ? -1 : (value == i.value) ? 0 : 1;
    }

}
