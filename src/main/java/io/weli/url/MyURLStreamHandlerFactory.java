package io.weli.url;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class MyURLStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if ("myuri".equals(protocol)) {
            return new MyURLStreamHandler();
        }

        return null;
    }

}
