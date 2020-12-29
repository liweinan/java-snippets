package io.weli;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

public class PlayWithProcessBuilder {
    public static void main(String[] args) throws Exception {
        String bash = "/bin/bash";
        String script = (new File("target/classes/test.sh")).getAbsolutePath();
        String[] command = { bash, script };

        System.out.println(">>> START <<<");
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        Map<String, String> env = processBuilder.environment();
        env.put("ENV_KEY", "ENV_VALUE");
        Process process = processBuilder.start();

        // output the process
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        // wait for the end of process
        process.waitFor();
        System.out.println(">>> END <<<");

    }
}
