package net.bluedash.snippets.generics.enumer;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
final class Season extends Enum<Season> {
    private Season(String name, int ordinal) {
        super(name, ordinal);
    }

    public static final Season WINTER = new Season("WINTER", 0);
    public static final Season SPRING = new Season("SPRING", 1);
    public static final Season SUMMER = new Season("SUMMER", 2);
    public static final Season FALL = new Season("FALL", 3);
    private static final Season[] VALUES = {WINTER, SPRING, SUMMER, FALL};

    public static Season[] values() {
        return VALUES.clone();
    }

    public static Season valueOf(String name) {
        for (Season e : VALUES) if (e.name().equals(name)) return e;
        throw new IllegalArgumentException();
    }
}