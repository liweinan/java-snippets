package net.bluedash.snippets.vfs;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class EnumSpy {
    private static final String fmt = " %11s: %s %s%n";

    public static void main(String... args) throws Exception {
        for (TimeUnit unit : TimeUnit.values()) {
            play(unit.getClass());
        }
    }

    public static void play(Class<?> clazz) {
        if (!clazz.isEnum()) {
            out.format("%s is not an enum type%n", clazz);
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.isEnumConstant())
                    System.out.println("enum field: " + f);
                else
                    System.out.println("non-enum field: " + f);
            }
            return;
        }
    }
}
