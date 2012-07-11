package net.bluedash.snippets.io;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 7/12/12
 * Time: 2:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ByteOut {
    public static void main(String[] args) throws IOException {
        String str = "What are the strings?";
        System.out.write(str.getBytes());
    }
}
