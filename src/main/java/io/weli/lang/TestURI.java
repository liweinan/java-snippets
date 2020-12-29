package io.weli.lang;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class TestURI {
    public static void main(String[] args) {
        URI uri = URI.create("https://127.0.0.1:8080/test");
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());

        List<String> noProxyHosts = Arrays.asList("localhost|127.*|[::1]".split("\\|"));
        for (String v : noProxyHosts) {
            System.out.println(v);
        }
    }
}
