package io.weli.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

// https://stackoverflow.com/a/26409796/1212922
// https://learning.oreilly.com/library/view/learning-java/1565927184/apas02.html
public class Main {
    public static void main(String[] args) throws Exception {
        URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());

        URLConnection connection = new URL("myuri:blabla").openConnection();
        connection.connect();
    }
}
