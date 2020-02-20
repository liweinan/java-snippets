package io.alchemystudio.math;
// http://www.herongyang.com/JVM/Thread-CPU-PrimeCalculator-CPU-Intensive-Process.html
/* PrimeCalculator.java
 * Copyright (c) 2014, HerongYang.com, All Rights Reserved.
 */
public class PrimeCalculator extends Thread {
    public long longMax; // The maximum integer range to seach
    public long primeMax; // The maximum prime found
    public long primeCount; // The number of primes found
    public long jobCount; // The number of jobs done so far

    public static void main(String[] a) {
        long max = 1000; // Default integer range to search
        long interval = 1; // Default monitoring interval in seconds
        if (a.length > 0) max = Long.parseLong(a[0]);
        if (a.length > 1) interval = Long.parseLong(a[1]);
        System.out.println("Integer range to search: " + max);

        PrimeCalculator t = new PrimeCalculator(max);
        t.start();
        int i = 0;
        long startTime = System.currentTimeMillis();
        System.out.println(
                "Time, Count, Productivity, PrimeCount, PrimeMax");
        while (true) {
            try {
                sleep(interval * 1000);
            } catch (InterruptedException e) {
                System.out.println("Monitor interrupted.");
            }
            long curTime = System.currentTimeMillis();
            long duration = (curTime - startTime) / 1000;
            long productivity = t.jobCount / duration;
            System.out.println(duration + ", " + t.jobCount
                    + ", " + productivity + ", " + t.primeCount + ", " + t.primeMax);
        }
    }

    public PrimeCalculator(long max) {
        longMax = max;
        primeCount = 0;
        primeMax = 0;
    }

    public void run() {
        jobCount = 0;
        while (true) {
            long count = 0;
            long max = 0;
            for (long i = 3; i <= longMax; i++) {
                boolean isPrime = true;
                for (long j = 2; j <= i / 2 && isPrime; j++) {
                    isPrime = i % j > 0;
                }
                if (isPrime) {
                    count++;
                    max = i;
                }
            }
            primeCount = count;
            primeMax = max;
            jobCount++;
        }
    }
}