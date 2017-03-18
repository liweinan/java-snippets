package regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weli on 27/02/2017.
 */
public class Find {

    public static void main(String[] args) {
        Pattern p = Pattern.compile("([a-z])([a-z])([a-z])");
        String text = "foo 42 bar xyz";
        Matcher matcher = p.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
