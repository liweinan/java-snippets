package net.bluedash.snippets.serviceloader;

import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 8/8/13
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
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

    /*
     * mvn exec:rmi -Dexec.mainClass="net.bluedash.snippets.serviceloader.HelloProvider"
     */
    public static void main(String[] ignored) {
        HelloProvider provider = HelloProvider.getDefault();
        System.out.println(provider.getMessage());
    }

}