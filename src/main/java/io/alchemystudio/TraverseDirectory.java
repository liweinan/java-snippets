package io.alchemystudio;

import java.io.File;

public class TraverseDirectory {
    public static void showFiles(File[] files) {
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    showFiles(file.listFiles()); // Calls same method again.
                } else {
                    System.out.println("File: " + file.getName());
                }
            }
        }
    }

    public static String START_PATH = "/tmp";
    public static void main(String[] args) {
        showFiles(new File(START_PATH).listFiles());
    }
}
