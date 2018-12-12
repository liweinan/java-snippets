package io.weli.clazz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UnknownType {
   public static void main(String[] args) {
      List<Object> objects = new ArrayList<>();
      objects.add("Hello");
      objects.add(LocalDate.now());
      objects.add(3);
      System.out.println(objects);
   }
}
