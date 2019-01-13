public class IsAssignableFrom {
   static interface Foo {}
   static class FooImpl implements Foo {}

   public static void main(String[] args) {
      System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
      System.out.println(Foo.class.isAssignableFrom(FooImpl.class));
      System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
      System.out.println(FooImpl.class.isAssignableFrom(Foo.class));
      System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
   }
}
