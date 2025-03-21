package io.weli.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://www.baeldung.com/mapstruct
public class IPV4 {
    /*
        https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Regular_expressions/Word_boundary_assertion
        A word boundary is where the next character is a word character and the previous character is not a word character, or vice versa.Jul 25, 2024
     */
    /*
    25[0-5] – This matches a number between 250 and 255.
    (2[0-4]|1\\d|[1-9]) – This matches a number between 20 – 24, 10 – 19, and 1 – 9 or nothing (last '|')
    \\d – This matches any digit (0-9). Finally -> 200 - 249, 100 - 199, 10 - 19, 0 - 9
    \\.? – This matches an optional dot(.) character.
    \\b – This is a word boundary.
     */
    public static void main(String[] args) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        String ip = "255.255.255.255";
        Matcher matcher = pattern.matcher(ip);
        System.out.println(matcher.matches());
    }
}
