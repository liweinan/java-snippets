package io.weli.lang;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RESTEASY_1029 {
   public static void main(String[] args) {
      {
         List<String> resolver1 = new ArrayList<>();
         for (Type type : resolver1.getClass().getGenericInterfaces()) {
            System.out.println(type);
         }
      }
   }
}
