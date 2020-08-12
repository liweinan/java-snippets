package io.weli.lang;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by weinanli on 06/07/2017.
 */
public class OldFinally {
    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/tmp"));
        } finally {
            if (br == null) {
                br.close();
            }
        }
    }
}
