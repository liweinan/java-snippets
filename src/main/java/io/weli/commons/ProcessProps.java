package io.weli.commons;

import java.util.regex.Pattern;

public class ProcessProps {

    static final String PATTERN = "\\$\\{([0-9a-zA-Z_\\-]+)(:([0-9a-zA-Z_\\-]*))?\\}";
    public static void main(String[] args) {

        var prop_val1 = "${x:y}";
        var prop_val2 = "${x::y}";
        var prop_val3 = "${x:y}}";
        var prop_val4 = "${{x:y}";
        var prop_val5 = "${x:y}";
        var prop_val6 = "${DB_USER:admin}";
        var prop_val7 = "${DB_PASS:}"; // no default value
        var prop_val8 = "${FOO:}"; // no default value
        verifyStr(prop_val1);
        verifyStr(prop_val2);
        verifyStr(prop_val3);
        verifyStr(prop_val4);
        verifyStr(prop_val5);
        verifyStr(prop_val6);
        verifyStr(prop_val7);
        verifyStr(prop_val8);
    }

    private static void verifyStr(String propVal) {
        System.out.println("------------------------------------");
        System.out.println(Pattern.matches(PATTERN, propVal));
        var matcher = Pattern.compile(PATTERN).matcher(propVal);
        while (matcher.find()) {
            System.out.println("group(0): " + matcher.group(0) + " pos: " + matcher.start());
            System.out.println("group(1): " + matcher.group(1) + " pos: " + matcher.start());
            System.out.println("group(2): " + matcher.group(2) + " pos: " + matcher.start());
            System.out.println("group(3): " + matcher.group(3) + " pos: " + matcher.start());

            System.out.println("k -> " + matcher.group(1));
            System.out.println("v -> " + matcher.group(3));
        }
    }


}
