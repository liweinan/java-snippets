package io.weli.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayWithDate {

    public static void main(String[] args) {
        var dateStr = "2024-07-06T12:30:00";

        System.out.println(LocalDateTime.parse(dateStr));

        var date = LocalDateTime.parse(dateStr);
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println(date.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
