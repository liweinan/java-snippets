package net.bluedash.threadlocal;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {
	static final ThreadLocal<Long> threadIdPool1 = new ThreadLocal<Long>() {
		public Long initialValue() {
			return Long.valueOf(System.nanoTime());
		}
	};

	static class Thread1 extends Thread {
		public void run() {
			threadIdPool1.get();
		}
	}

	static final MyThreadLocal threadIdPool2 = new MyThreadLocal() {
		public Long initialValue() {
			return Long.valueOf(System.nanoTime());
		}
	};

	static class Thread2 extends Thread {
		public void run() {
			threadIdPool2.get();
		}
	}

	private static final int ROUND = 1000;
	private static final int TIMES = 1000;
	private static Thread[] threadPool = new Thread[ROUND]; 

	public static void main(String[] args) throws InterruptedException {		
		
		long sum = 0;
		for (int j = 0; j < TIMES; j++) {
			long start = System.nanoTime();
			for (int i = 0; i < ROUND; i++) {
				Thread t = new Thread1();
				threadPool[i] = t;
				t.start();			
			}
			
			for (Thread t : threadPool) {
				t.join();
			}
			long end = System.nanoTime();
			sum += (end - start);
		}

		System.out.println("Time of ThreadLocal:" + (sum / TIMES));

		
		/* 
		 * 清空ThreadPool
		 */
		for (int i = 0; i < ROUND; i++) {
			threadPool[i] = null;
		}
		
		/*
		 * 回收内存
		 */
		System.gc();
		
		/*
		 * 测试MyThread
		 */
		sum = 0;
		for (int j = 0; j < TIMES; j++) {
			long start = System.nanoTime();
			for (int i = 0; i < ROUND; i++) {
				Thread t = new Thread2();
				threadPool[i] = t;
				t.start();
			}
			for (Thread t : threadPool) {
				t.join();
			}
			long end = System.nanoTime();
			sum += (end - start);
		}

		System.out.println("Time of MyThreadLocal:" + (sum / TIMES));
	}
}
