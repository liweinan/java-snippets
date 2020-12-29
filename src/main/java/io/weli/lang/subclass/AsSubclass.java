package io.weli.lang.subclass;

// https://www.logicbig.com/how-to/code-snippets/jcode-reflection-class-assubclass.html
public class AsSubclass {
    public static void main(String[] args) {
        C1 c1 = new C1();

        Class t = c1.getClass().asSubclass(I.class);

        System.out.println(t); // t = C1.class

        try {
            c1.getClass().asSubclass(C2.class);
        } catch (ClassCastException e) {
            // expected
        }

    }
}
