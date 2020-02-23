package io.alchemystudio.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecCmd {
    // https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();

        // 后面两个参数要隔开成两个独立的string
        String[] commands = {"docker-machine", "env", "default"};

        Process proc = rt.exec(commands);

        InputStream stdIn = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdIn);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        System.out.println("<OUTPUT>");

        while ((line = br.readLine()) != null)
            System.out.println(line);

        System.out.println("</OUTPUT>");
        int exitVal = proc.waitFor();
        System.out.println("Process exitValue: " + exitVal);
    }
}
