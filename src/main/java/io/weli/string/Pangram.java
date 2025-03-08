package io.weli.string;

import java.util.stream.Stream;

public class Pangram {

    public static String pangrams(String s) {
        // Write your code here
        boolean isPangram = true;

        for (char c = 'a'; c <= 'z'; c++) {
            if (!s.toLowerCase().contains(String.valueOf(c))) {
                isPangram = false;
                break;
            }
        }

        if (isPangram) {
            return "pangram";
        } else {
            return "not pangram";
        }
    }

    public static void main(String[] args) {
//        String str = "The quick brown fox jumps over the lazy dog";
        String str = "We promptly judged antique ivory buckles for the next prize\n";
        System.out.println(pangrams(str));
    }
}
