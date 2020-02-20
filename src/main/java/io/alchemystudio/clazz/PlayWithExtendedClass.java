package io.alchemystudio.clazz;

public class PlayWithExtendedClass {
   static class A {

   }

   static class B extends A {

   }

   public static void main(String[] args) {
      B b = new B();
      foo(b);
   }

   private static void foo(A a) {
      System.out.println(a.getClass());
      System.out.println(a.getClass().equals(B.class));
   }
}
