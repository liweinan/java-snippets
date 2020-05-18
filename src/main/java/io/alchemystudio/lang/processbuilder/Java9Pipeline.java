package io.alchemystudio.lang.processbuilder;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Java9Pipeline {
    public static void main(String[] args) throws Exception {
        // $ ls /tmp | xargs echo
        List pipe = Arrays.asList(
                new ProcessBuilder("ls", "/tmp"),
                new ProcessBuilder("xargs", "echo"));

        List processes = ProcessBuilder.startPipeline(pipe);

        Process last = (Process) processes.get(processes.size() - 1);

        last.waitFor();

        InputStream is = last.getInputStream();

        // 直接输出到`System.out`
        is.transferTo(System.out);
    }
}
