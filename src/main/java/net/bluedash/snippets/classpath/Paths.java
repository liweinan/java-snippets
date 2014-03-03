package net.bluedash.snippets.classpath;

/**
 * Created with IntelliJ IDEA.
 * User: weinanli
 * Date: 6/7/13
 * Time: 7:05 PM
 * http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-rmi
 * mvn -q exec:rmi -Dexec.mainClass="net.bluedash.snippets.classpath.Paths"
 */
public class Paths {

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println("user.dir: " + currentDir);

        String classpath = System.getProperty("rmi.class.path");
        System.out.println("rmi.class.path: " + classpath);
    }
}
