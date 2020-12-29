package io.weli.lang.autoclosable;

public class PlayWithAutoclosable  {
    public static void main(String[] args) {
        try (FooClass foo = new FooClass()) {
            // use the FooClass instance here.
            System.out.println(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
