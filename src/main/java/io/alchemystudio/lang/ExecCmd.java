package io.alchemystudio.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Pattern dockerHostPattern = Pattern.compile(".*DOCKER_HOST=\"(.*)\".*");

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            Matcher m = dockerHostPattern.matcher(line);
            System.out.println(m.matches());
            if (m.matches()) {
                System.out.println("docker host: " + m.group(1));
            }
        }


        System.out.println("</OUTPUT>");
        int exitVal = proc.waitFor();
        System.out.println("Process exitValue: " + exitVal);
    }
}
