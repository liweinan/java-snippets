package net.bluedash.snippets.threadlocal;

public class PlayThreadLocal {

	static final ThreadLocal<Long> threadIdPool = new ThreadLocal<Long>() {
		public Long initialValue() {
			return Long.valueOf(System.nanoTime());
		}
	};

	static class MyThread extends Thread {
		public void run() {
			System.out.println(threadIdPool.get());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread t = new MyThread();
			t.start();
		}
	}

}
