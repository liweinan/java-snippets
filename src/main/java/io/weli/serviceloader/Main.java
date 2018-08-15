package io.weli.serviceloader;

/**
 * Created by liweinan on 7/7/14.
 */
public class Main {

    /*
     * mvn exec:java -Dexec.mainClass="io.weli.serviceloader.Main"
     */
    public static void main(String[] args) {
        HelloProvider provider = HelloProvider.getDefault();
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println(provider.getMessage());
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }
}
