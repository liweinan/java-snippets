package net.bluedash.threadlocal;

public class Benchmark {

	private static final double NANO_TIME = 1000000000.0;

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

	private static final int ROUND = 10;
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

		System.out.println("Time of ThreadLocal:" + (sum / TIMES / NANO_TIME)
				+ "s");

		/*
		 * Clear ThreadPool
		 */
		for (int i = 0; i < ROUND; i++) {
			threadPool[i] = null;
		}

		/*
		 * Garbage Collection before MyThread testing. Because it may affect the
		 * result.
		 */
		System.gc();

		/*
		 * Test MyThreadLocal
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

		System.out.println("Time of MyThreadLocal:" + (sum / TIMES / NANO_TIME)
				+ "s");
	}
}
