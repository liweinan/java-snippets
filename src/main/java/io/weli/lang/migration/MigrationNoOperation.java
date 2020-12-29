package io.weli.lang.migration;

public class MigrationNoOperation implements Migration {
   @Override
   public void migrate() {
      /// do nothing
      System.out.println("NO-OP");

   }
}
