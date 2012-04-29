package net.bluedash.snippets.threadlocal;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class MyThreadLocal {
	private Map values = Collections.synchronizedMap(new WeakHashMap());

	public Object get() {
		Thread curThread = Thread.currentThread();
		Object o = values.get(curThread);
		if (o == null && !values.containsKey(curThread)) {
			o = initialValue();
			values.put(curThread, o);
		}
		return o;
	}

	public void set(Object newValue) {
		values.put(Thread.currentThread(), newValue);
	}

	public Object initialValue() {
		return null;
	}

}
