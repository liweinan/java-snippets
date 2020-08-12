package io.weli.lang;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by weinanli on 06/07/2017.
 */
public class PlayWithTryWithResource {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("/tmp"))) {
            // do something with `br`
        }
    }
}
