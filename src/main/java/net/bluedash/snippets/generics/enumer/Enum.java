package net.bluedash.snippets.generics.enumer;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public abstract class Enum<E extends Enum<E>> implements Comparable<E> {
    private final String name;
    private final int ordinal;

    protected Enum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public final String name() {
        return name;
    }

    public final int ordinal() {
        return ordinal;
    }

    public String toString() {
        return name;
    }

    public final int compareTo(E o) {
        return ordinal - o.ordinal;
    }
}
