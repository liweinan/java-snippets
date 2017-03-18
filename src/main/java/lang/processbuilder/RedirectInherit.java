package lang.processbuilder;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by weli on 4/23/16.
 */
public class RedirectInherit {
    public static void main(String[] args) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        builder.inheritIO();
        builder.command("ls");
        Process process = builder.start();
        process.waitFor();

        Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
        System.out.println("=== S T A R T ===");
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        System.out.println("=== E N D ===");
    }
}
