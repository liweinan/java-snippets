package io.weli;

import java.util.function.Function;

/*
 * https://www.baeldung.com/java-8-lambda-expressions-tips
 */
public class LambdaBestPractices {

   @FunctionalInterface
   public interface Foo {
      String method(String v);

      default String defaultMethod(String v) {
         return v;
      }
   }

   public static void main(String[] args) {
      Function<String, String> fn = v -> v.toUpperCase();
      System.out.println(fn.apply("hello"));

      Foo f = v -> v.toUpperCase();
      System.out.println(f.method("hello"));
      System.out.println(f.defaultMethod("hello"));

   }
}
