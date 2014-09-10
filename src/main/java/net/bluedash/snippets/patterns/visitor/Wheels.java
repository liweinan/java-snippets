package net.bluedash.snippets.patterns.visitor;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Wheels extends DefaultCarPartImpl {
    private static final String name = "WHEEL";

    public void rotate() {
        System.out.println("wheels rotating");
    }
}
