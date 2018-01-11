package lang.bridge;

/**
 * Created by weli on 15/05/2017.
 */
public class Run {

    public static void main(String[] args) {
        Foo foo = new FooImpl();
        System.out.println(foo.bar());
    }
}
