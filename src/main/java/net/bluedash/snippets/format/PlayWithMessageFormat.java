package net.bluedash.snippets.format;

import java.text.MessageFormat;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithMessageFormat {
    public static void main(String[] args) {
        int someNumber = 42;
        String someString = "foobar";
        Object[] vals = {new Long(someNumber), someString};
        MessageFormat fmt =  new MessageFormat("String is \"{1}\", number is {0}.");
        System.out.println(fmt.format(vals));
    }
}
