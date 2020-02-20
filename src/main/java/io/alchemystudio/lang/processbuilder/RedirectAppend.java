package io.alchemystudio.lang.processbuilder;

import java.io.File;

/**
 * Created by weli on 4/23/16.
 */
public class RedirectAppend {

    public static void main(String[] args) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("ls");
        builder.redirectOutput(new File("/tmp/foo"));
        System.out.println(builder.redirectOutput());

        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("/tmp/foo")));
        System.out.println(builder.redirectOutput());

        builder.start().waitFor();
    }
}
