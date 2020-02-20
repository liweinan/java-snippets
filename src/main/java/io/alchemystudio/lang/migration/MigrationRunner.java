package io.alchemystudio.lang.migration;

public class MigrationRunner {
   public static void main(String[] args) throws Exception {
      String className = "io.weli.lang.migration.MigrationNoOperation";
      Migration migration = (Migration) Class.forName(className).getDeclaredConstructor().newInstance();
      migration.migrate();

   }
}
