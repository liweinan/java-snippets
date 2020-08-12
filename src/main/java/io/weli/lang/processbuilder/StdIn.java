package io.weli.lang.processbuilder;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by weli on 4/21/16.
 * http://stackoverflow.com/questions/3643939/java-process-with-input-output-stream
 */
public class StdIn {
    public static void main(String[] args) throws Exception {
        // start a bash process
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-i");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // execute ls command
        OutputStream stdin = process.getOutputStream();
        stdin.write("ls\n".getBytes());
        stdin.flush();

        // get result
        Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
