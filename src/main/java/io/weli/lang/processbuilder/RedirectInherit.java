package io.weli.lang.processbuilder;

/**
 * Created by weli on 4/23/16.
 */
public class RedirectInherit {
    public static void main(String[] args) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();

        // 继承了I/O以后，命令的输出直接输出给本Java Process.
        builder.inheritIO();

        builder.command("ls");
        Process process = builder.start();
        process.waitFor();
    }
}
