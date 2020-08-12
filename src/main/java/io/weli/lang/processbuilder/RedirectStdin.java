package io.weli.lang.processbuilder;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by weli on 4/23/16.
 */
public class RedirectStdin {

    public static void main(String[] args) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-i");
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectInput(ProcessBuilder.Redirect.from(new File("/tmp/cmd")));
        Process process = processBuilder.start();

        Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
