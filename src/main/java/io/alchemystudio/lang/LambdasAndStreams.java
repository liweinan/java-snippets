package io.alchemystudio.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//https://learning.oreilly.com/library/view/effective-java-3rd/9780134686097/ch7.xhtml#ch7
public class LambdasAndStreams {
   public static void main(String[] args) {
//      https://www.techiedelight.com/initialize-list-java-single-line/
      List<String> fruits = Arrays.asList("Apple", "Orange", "Tomato");
      Collections.sort(fruits, Comparator.comparingInt(String::length));
   }
}
