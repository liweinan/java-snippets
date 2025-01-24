package io.weli.concurrent;

import java.util.Timer;
import java.util.TimerTask;

public class PlayWithTimer {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task run");
            }
        };
        timer.schedule(task, 1000L, 1000L);

        Thread.sleep(3000L);
        System.out.println("task end");
    }
}
