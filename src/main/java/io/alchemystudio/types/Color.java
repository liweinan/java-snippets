package io.alchemystudio.types;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public enum Color {
    BLACK,
    BLUE,
    RED,
    WHITE,
    SILVER;

    public static void main(String[] args) {
        for (Color color : Color.values()) {
            System.out.println(color);
        }

        System.out.println(Color.BLACK.toString());

        System.out.println(Color.valueOf("BLACK"));

        System.out.println(Color.BLACK.valueOf("RED"));
    }
}
