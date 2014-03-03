package net.bluedash.snippets.pieces;

/*
 * mvn install
 * mvn -q exec:rmi -Dexec.mainClass="net.bluedash.snippets.pieces.StackOverflow"
 */
public class StackOverflow {

    public static void overflow() {
        for (StackTraceElement element : (new Throwable()).getStackTrace()) {
            if (element.getMethodName().equals("overflow")) {
                System.out.println("break");
                return;
            }
        }
        overflow();
    }

    public static void main(String[] args) {
        overflow();
    }
}
