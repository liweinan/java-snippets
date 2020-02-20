package io.alchemystudio.lang;

/**
 * Created by weli on 6/7/16.
 */
public class NotSynced {

    public static void main(String[] args) throws Exception {
        for (String thread : new String[]{"t1", "t2", "t3"}) {
            createThread(thread).start();
        }
    }

    private static Thread createThread(final String threadName) {
        return new Thread() {
            @Override
            public void run() {
                System.out.println(threadName + "await...");
                System.out.println(threadName + "go...");
            }
        };
    }
}
