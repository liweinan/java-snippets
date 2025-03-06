package io.weli.lang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class Num {
    public static void main(String[] args) {
        double x = (double) 1 / 3;


        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(6);

        System.out.println(numberFormat.format(x));

        DecimalFormat df = new DecimalFormat("0.000000");

        System.out.println(df.format(x));

        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(array.size());

        for (int i : array) {
            System.out.println(i);
        }
    }
}
