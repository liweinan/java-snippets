package io.weli.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weli on 27/02/2017.
 */
public class Region {

    public static void main(String[] args) throws Exception {
        Pattern p = Pattern.compile("(.* )(.* )");
        String text = "foo 42 bar xyz";
        Matcher matcher = p.matcher(text);
        matcher.region(1, text.length());
        matcher.find();

        for (int i = 0; i <= matcher.groupCount(); i++) {
            System.out.println(i + ": " + matcher.group(i));
        }
    }
}
