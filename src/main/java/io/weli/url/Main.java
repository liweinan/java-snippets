package io.weli.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SuppressWarnings("deprecation")
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String url = "https://example.com";
        URL website = new URI(url).toURL();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
