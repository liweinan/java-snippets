package io.alchemystudio.lang;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DoEncode {
    public static void main(String[] args) throws Exception {
        System.out.println(
                URLEncoder
                        .encode("http://0.0.0.0:8080/users?paging=0%2c-1sp137<script>Ealert(1)%3C%2fscript%3Emzx4u",
                                StandardCharsets.UTF_8.toString()));

    }
}
