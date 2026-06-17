package io.weli.hackerrank;

import java.util.Scanner;

//  https://www.hackerrank.com/challenges/java-output-formatting/problem?isFullScreen=true
public class JavaOutputFormat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("================================");
        while (sc.hasNextLine()) {
//        for (int i = 0; i < 3; i++) {
            String s1 = sc.next();
            int x = sc.nextInt();
            //Complete this line

//            System.out.println(s1.length());
            StringBuilder builder = new StringBuilder(s1);
            for (int j = 0; j < 15 - s1.length(); j++) {
                builder.append(" ");
            }

            if (x < 10) {
                builder.append("0");
            }
            if (x < 100) {
                builder.append("0");
            }

            builder.append(x);
            System.out.println(builder);

        }
        System.out.println("================================");
    }
}

