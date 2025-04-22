package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class NullNull {
   static NullNull pass(Object obj) {
      return (NullNull) obj;
   }
   public static void main(String[] args) {
      System.out.println(pass(null));
      System.out.println(pass("aaaa"));

      List<String> strings = new ArrayList<>();
      strings.add("Hello");
      strings.add("World");
      System.out.println(strings);

      List lst2 = new ArrayList<>();
      lst2.add(1);
      lst2.add(new Object());

//      String str = (String) lst2.get(0);

   }
}
