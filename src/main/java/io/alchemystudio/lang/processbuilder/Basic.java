package io.alchemystudio.lang.processbuilder;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by weli on 4/20/16.
 */
public class Basic {

    public static void main(String[] args) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("ls");
        Process process = processBuilder.start();

        int retVal = process.waitFor();
        System.out.println("retVal: " + retVal);
        System.out.println(System.getProperty("user.dir"));

        // stdout = input stream
        // stdin = output stream
        Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
