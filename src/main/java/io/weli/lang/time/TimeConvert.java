package io.weli.lang.time;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

//Input (stdin)
//12:40:22AM
//Expected Output
//00:40:22


//Input (stdin)
//12:45:54PM
//Expected Output
//12:45:54
public class TimeConvert {
    public static void main(String[] args) throws IOException {
//        var s1 = "07:05:45PM";
//        var s1 = "12:40:22AM";
        var s1 = "12:45:54PM";
        var t1s = s1.split(":");

//        Arrays.stream(t1s).forEach(System.out::println);
//
//        System.out.println(t1s[2].contains("PM"));
//
//        System.out.println(Integer.parseInt(t1s[0]));
//
//        System.out.println(Integer.parseInt(t1s[1]));
//
//        System.out.printf("%02d\n", Integer.parseInt(t1s[1]));
//
//        System.out.println(t1s[2].substring(0, 2));

        int hour = Integer.parseInt(t1s[0]);

        int min = Integer.parseInt(t1s[1]);

        int sec = Integer.parseInt(t1s[2].substring(0, 2));

        if ((t1s[2].contains("PM") && Integer.parseInt(t1s[0]) != 12) || (t1s[2].contains("AM") && Integer.parseInt(t1s[0]) == 12)) {
            hour += 12;
            System.out.println("hour: " + hour);
            if (hour == 24) {
                hour = 0;
            }
        }

        System.out.printf("%02d:%02d:%02d\n", hour, min, sec);
    }
}
