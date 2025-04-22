package io.weli.lang.processbuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Java9Pipeline {
    public static void main(String[] args) throws IOException {
        ProcessBuilder ls = new ProcessBuilder("ls");
        ProcessBuilder grep = new ProcessBuilder("grep", "t");
        ProcessBuilder wc = new ProcessBuilder("wc", "-l");

        List<ProcessBuilder> builders = Arrays.asList(ls, grep, wc);
        List<Process> processes = ProcessBuilder.startPipeline(builders);

        Process last = processes.get(processes.size() - 1);
        last.getInputStream().transferTo(System.out);
    }
}
