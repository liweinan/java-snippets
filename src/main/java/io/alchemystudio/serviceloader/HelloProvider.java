package io.alchemystudio.serviceloader;

import java.util.ServiceLoader;

public abstract class HelloProvider {
    public static HelloProvider getDefault() {
        ServiceLoader<HelloProvider> ldr = ServiceLoader.load(HelloProvider.class);
        for (HelloProvider provider : ldr) {
            //We are only expecting one
            return provider;
        }
        throw new Error("No HelloProvider registered");
    }

    public abstract String getMessage();
}
