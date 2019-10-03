package io.weli.lang.migration;

public class MigrationRunner {
   public static void main(String[] args) throws Exception {
      String className = "io.weli.lang.migration.Migration20191003";
      Migration migration = (Migration) Class.forName(className).getDeclaredConstructor().newInstance();
      migration.migrate();

   }
}
