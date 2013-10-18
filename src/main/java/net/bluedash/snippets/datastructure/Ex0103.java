package net.bluedash.snippets.datastructure;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Ex0103 {
    public static void main(String[] args) {
        String s = "2.7182818284590";
        System.out.println("s = " + s);
        Double x = new Double(s);
        System.out.println("x = " + x);
        double y = x.doubleValue();
        System.out.println("y = " + y);
        s += 45;
        System.out.println("s = " + s);
        y += 45;
        System.out.println("y = " + y);
        int n = x.intValue();
        System.out.println("n = " + n);
        n = Integer.parseInt("3A9", 16);
        System.out.println("n = " + n);
    }
}
