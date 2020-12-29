package io.weli.lang;

public class SystemPropertiesDemo {
   public static void main(String[] args) throws Exception {
//      new Thread(() -> SubtypeAndWildcard.BlaBlaBlaProperty.main(null)).start();

      Thread.sleep(1000);
      System.out.println(System.getProperty("FOOBAR"));
   }
}
