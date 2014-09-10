package net.bluedash.snippets.vfs;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */

enum Eon {
    HADEAN, ARCHAEAN, PROTEROZOIC, PHANEROZOIC
}

public class EnumConstants {
    public static void main(String... args) throws Exception {
        try {
            Class<?> c = (args.length == 0 ? TimeUnit.class : Class.forName(args[0]));
            System.out.println(Eon.class.getSuperclass());
            Eon[] arr = Eon.values();
            System.out.println(arr);
            for (TimeUnit unit : TimeUnit.values()) {
                System.out.println("u: " + unit);
            }

            out.format("Enum name: %s%nEnum constants: %s%n", c.getName(), Arrays.asList(c.getEnumConstants()));
            out.format("Eon.values(): %s%n", Arrays.asList(Eon.values()));
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
    }
}
