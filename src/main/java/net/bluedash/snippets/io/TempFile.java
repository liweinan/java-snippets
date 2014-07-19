package net.bluedash.snippets.io;

import java.io.File;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TempFile {
    public static void main(String[] args) throws Exception {
        String tmpDir = System.getProperty("java.io.tmpdir");
        System.out.println(tmpDir);

        File tempFile = File.createTempFile("name", ".tmp");
        String tmpFilePath = tempFile.getPath();
        System.out.println(tmpFilePath);

    }
}
