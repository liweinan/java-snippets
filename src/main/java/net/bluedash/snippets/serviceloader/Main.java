package net.bluedash.snippets.serviceloader;

/**
 * Created by liweinan on 7/7/14.
 */
public class Main {

    /*
     * mvn exec:rmi -Dexec.mainClass="net.bluedash.snippets.serviceloader.HelloProvider"
     */
    public static void main(String[] ignored) {
        HelloProvider provider = HelloProvider.getDefault();
        System.out.println(provider.getMessage());
    }
}
