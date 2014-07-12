package net.bluedash.snippets.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Main {

    public static void main(String[] args) {
        String regex = "\\d+\\w+";
        Pattern pattern = Pattern.compile(regex);

        String input = "1st 2nd";
        Matcher matcher = pattern.matcher(input);

        StringBuffer replacedStrBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(replacedStrBuffer, "This is " + matcher.group() + " match.");
            System.out.println(matcher.group(0));
        }
        matcher.appendTail(replacedStrBuffer);

        StringBuffer buf = new StringBuffer();

        String input2 = "abcdefg123 hijklm456";
        Pattern pattern2 = Pattern.compile("[a-z]");
        Matcher matcher2 = pattern2.matcher(input2);

        while (matcher2.find()) {
            matcher2.appendReplacement(buf, matcher2.group().toUpperCase());
        }
        matcher2.appendTail(buf);
        System.out.println(buf.toString());

        System.out.println(Pattern.quote("$5"));

        matcher2.region(11, input2.length());
        while (matcher2.find()) {
            System.out.println(matcher2.group());
        }
    }
}
