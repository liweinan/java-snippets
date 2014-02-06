package net.bluedash.snippets.instrumentation;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class AgentTest {
    public static void main(String[] args) {
        printObjectSize("");
        printObjectSize("1");
        printObjectSize("12");
        printObjectSize("123");
        printObjectSize("1234");
        printObjectSize("12345");
        printObjectSize("123456");
        printObjectSize("1234567");
        printObjectSize("12345678");
        printObjectSize("123456789");
    }

    static void printObjectSize(Object obj) {
        System.out.println("" + obj + " : " + getObjectSize(obj) + "B");
    }

    static long getObjectSize(Object obj) {
        return Agent.getInstrumentation().getObjectSize(obj);
    }
}
