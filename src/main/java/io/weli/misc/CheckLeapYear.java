package io.weli.misc;

import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CheckLeapYear {
    public static void main(String[] args) {
//        var date = LocalDate.of(2024);
        var cal = Calendar.getInstance();
        cal.set(2020, 1, 1);
        int days = cal.getMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(days);
        days = cal.getMaximum(Calendar.DAY_OF_YEAR);
        System.out.println(days);
        System.out.println(Year.isLeap(2025));
        System.out.println(Year.isLeap(2020));

        var gc = new GregorianCalendar();
        gc.isLeapYear(2020);
    }
}
