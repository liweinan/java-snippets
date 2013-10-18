package net.bluedash.snippets.datastructure;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Ex0102 {
    public static void main(String[] args) {
        double x = 1E200;
        System.out.println("x = " + x);
        System.out.println("x*x = " + x*x);
        System.out.println("(x*x)/x = " + (x*x)/x);
        System.out.println("(x*x)/(x*x) = " + (x*x)/(x*x));
    }
}
