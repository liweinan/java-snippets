package io.alchemystudio.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithRegExp {

    public static final Pattern hierarchicalUri = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
    public static final String url = "https://www.youtube.com/watch?v=6exFuFJhfcA#t=6h07m01s";

    public static void main(String[] args) {
        Matcher matcher = hierarchicalUri.matcher(url);
        outputResult(matcher);
    }

    private static void outputResult(Matcher matcher) {
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                System.out.println("+ " + matcher.group(i));
            }
        }
    }
}
