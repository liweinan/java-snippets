package io.alchemystudio.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weli on 27/02/2017.
 */
public class FindAfterMatch {

    public static void main(String[] args) {
        Pattern p = Pattern.compile("([a-z])([a-z])([a-z])");
        String text = "foo";
        Matcher matcher = p.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
