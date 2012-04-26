package net.bluedash.threadlocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {
	private Map values = Collections.synchronizedMap(new HashMap());

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
