package io.alchemystudio.lang;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayWithDate {
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        System.out.println(String.format("/tmp/%s.sql", formatter.format(new Date())));
    }
}
