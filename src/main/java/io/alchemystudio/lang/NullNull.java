package io.alchemystudio.lang;

import java.util.ArrayList;
import java.util.List;

public class NullNull {
   static NullNull pass(Object obj) {
      return (NullNull) obj;
   }
   public static void main(String[] args) {
      System.out.println(pass(null));
      System.out.println(pass("aaaa"));

      List<String> lst = new ArrayList<>();
      lst.add("a");


      List lst2 = new ArrayList<>();
      lst2.add(1);
      lst2.add(new Object());

//      String str = (String) lst2.get(0);

   }
}
