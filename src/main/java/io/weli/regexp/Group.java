package io.weli.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weli on 27/02/2017.
 */
public class Group {

    public static void main(String[] args) {
        Pattern p = Pattern.compile("(^[A-Za-z]+)( [0-9]+)( [A-Za-z]+).*");
        String text = "foo 42 bar xyz";
        Matcher matcher = p.matcher(text);
        matcher.find();

        for (int i = 0; i <= matcher.groupCount(); i++) {
            System.out.println(i + ": " + matcher.group(i));
        }

    }
}
