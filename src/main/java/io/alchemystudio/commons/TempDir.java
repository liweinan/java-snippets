package io.alchemystudio.commons;

public class TempDir {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++)
            System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
