package io.alchemystudio.clazz;

import java.util.Arrays;
import java.util.List;

public class sumList {
   static double sumList(List<? extends Number> lst) {
      return lst.stream().mapToDouble(Number::doubleValue).sum();
   }

   public static void main(String[] args) {
      List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
      System.out.printf("doubles sum is %s%n", sumList(doubles));
   }
}
