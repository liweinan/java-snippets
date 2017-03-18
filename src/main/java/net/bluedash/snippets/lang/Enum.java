package net.bluedash.snippets.lang;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Enum {

    private static enum Time {
        HOUR, MINUTE, SECOND
    }

    public static void main(String[] args) {
        System.out.println(Time.HOUR);
        System.out.println(Time.HOUR == Time.HOUR);
        System.out.println(Time.HOUR.equals(Time.HOUR));

        Time timeA = Time.HOUR;
        Time timeB = Time.HOUR;

        System.out.println(timeA == timeB);
        System.out.println(timeA.equals(timeB));
    }
}
