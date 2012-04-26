package net.bluedash.threadlocal;

public class PlayMyThreadLocal {

	static final MyThreadLocal threadIdPool = new MyThreadLocal() {
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
