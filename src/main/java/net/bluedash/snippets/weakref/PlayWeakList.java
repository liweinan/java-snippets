package net.bluedash.snippets.weakref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 5/7/12
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayWeakList {
    private static String LONG_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static long LOOPS = 1000000000;

    static List list = new ArrayList();
    static WeakList weakList = new WeakList();

    private static class WeakListSizeReporter extends Thread {
        public void run() {
            try {
                sleep(1000);
                System.out.print(weakList.size() + '.');
            } catch (InterruptedException e) {

            }

        }
    }

    public static void main(String[] args) {

        try {
            for (int i = 0; i < LOOPS; i++) {
                list.add(LONG_STRING);
            }
        } catch (OutOfMemoryError err) {
        } finally {
            System.out.println("Maximum size of list:" + list.size());
        }

//        System.out.println("");
//        Thread reporter = new WeakListSizeReporter();
//        reporter.start();

        try {
            for (int i = 0; i < LOOPS; i++) {
                weakList.add(LONG_STRING);
            }
        } catch (OutOfMemoryError err) {
        } finally {
            System.out.println("Maximum size of weak list:" + list.size());
        }

    }
}
