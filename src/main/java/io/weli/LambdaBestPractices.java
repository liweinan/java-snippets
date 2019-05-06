package io.weli;

import java.util.function.Function;

/*
 * https://www.baeldung.com/java-8-lambda-expressions-tips
 */
public class LambdaBestPractices {
   public static void main(String[] args) {
      Function<String, String> fn = v -> v.toUpperCase();
      System.out.println(fn.apply("hello"));

   }
}
