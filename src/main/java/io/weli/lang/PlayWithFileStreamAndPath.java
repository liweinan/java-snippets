package io.weli.lang;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Base64;

public class PlayWithFileStreamAndPath {
    public static void main(String[] args) throws Exception {
        String clazzToPath = PlayWithFileStreamAndPath.class.getName().replaceAll("\\.", "/") + ".class";
        System.out.println(clazzToPath);

        String path = Paths.get("target/classes/").toAbsolutePath() + "/" + clazzToPath;
        System.out.println(path);

        try (FileInputStream is =
                     new FileInputStream(path)) {
            System.out.println(is);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines().forEach(l -> {
                System.out.println(Base64.getEncoder().encodeToString(l.getBytes()));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
