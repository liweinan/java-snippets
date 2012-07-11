package net.bluedash.snippets.io;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 7/12/12
 * Time: 2:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsciiChart {

    public static void main(String[] args) {
        for (int i = 32; i < 127; i++) {
            System.out.write(i);

            if (i % 8 == 7) System.out.write('\n');
            else System.out.write('\t');
        }
        System.out.write('\n');
    }
}
