package concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TestRef {

    static class Foo {
        private Bar bar;
        private int i;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }
    }
    static class Bar {}

    private static ThreadLocal<Object> ref = new ThreadLocal<Object>();
    private static ThreadLocal<Foo> fooref = new ThreadLocal<Foo>();
    public static void main(String[] args) throws Exception {
        Foo foo = new Foo();
        Bar bar = new Bar();

        ref.set(foo);
        System.out.println(ref.get());

        Object myref = ref.get();
        myref = bar;
        System.out.println(ref.get());
        System.out.println(myref == ref.get());

        foo.setBar(bar);
        Bar mybar = foo.getBar();
        Bar bar2 = new Bar();
        mybar = bar2;
        System.out.println(foo.getBar() == mybar);


        foo.setI(1);
        fooref.set(foo);
        foo.setI(2);
        System.out.println(fooref.get().getI());


    }
}
