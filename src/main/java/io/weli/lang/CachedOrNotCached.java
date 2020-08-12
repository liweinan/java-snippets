package io.weli.lang;

import java.util.HashMap;
import java.util.Map;

public class CachedOrNotCached {
   public static void main(String[] args) {
      System.out.println("a".hashCode());

      StringBuilder builder = new StringBuilder();
      builder.append("a");
      Object obj = builder.toString();
      System.out.println(obj.hashCode());
      System.out.println(Integer.valueOf(97).hashCode());

      Map<Object, Object> map = new HashMap<>();
      map.put(obj, 1);
      map.put("a", 2);

      System.out.println(map.toString());

      System.out.println(System.identityHashCode(Integer.valueOf(97)));
      System.out.println(System.identityHashCode("a"));
      System.out.println(System.identityHashCode(obj.hashCode()));

   }
}
