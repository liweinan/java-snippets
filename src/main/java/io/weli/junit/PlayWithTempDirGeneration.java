package io.weli.junit;

import org.junit.rules.TemporaryFolder;

public class PlayWithTempDirGeneration {
    public static void main(String[] args) throws Exception {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();
        System.out.println(folder.getRoot().getAbsolutePath());
    }
}
