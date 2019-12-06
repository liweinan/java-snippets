package io.weli;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CopyVolatileTest {

   class Foo {
      private volatile List list;

      public Foo() {
         list = new ArrayList();
      }

      public Foo(List list) {
         this.list = list;
      }

      public List getList() {
         return list;
      }
   }


   @Test
   public void testCopy() {

      Foo foo = new Foo();
      Foo foo2 = new Foo(foo.getList());

      System.out.println(foo.getList().hashCode());
      System.out.println(foo2.getList().hashCode());

      foo.getList().add("abc");

      System.out.println(foo2.getList());

   }
}
