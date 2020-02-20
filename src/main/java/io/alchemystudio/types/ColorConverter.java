package io.alchemystudio.types;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ColorConverter implements ParamConverter<Color> {

    public Color fromString(String value) {
        if (value.equalsIgnoreCase(Color.BLACK.toString())) return Color.BLACK;
        else if (value.equalsIgnoreCase(Color.BLUE.toString())) return Color.BLUE;
        else if (value.equalsIgnoreCase(Color.RED.toString())) return Color.RED;
        else if (value.equalsIgnoreCase(Color.WHITE.toString())) return Color.WHITE;
        else if (value.equalsIgnoreCase(Color.SILVER.toString())) return Color.SILVER;
        throw new IllegalArgumentException("Invalid color: " + value);
    }

    public String toString(Color value) {
        return value.toString();
    }

}
