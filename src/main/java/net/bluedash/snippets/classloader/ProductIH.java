package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ProductIH implements InvocationHandler {
	private Object target = null;
	static private Class[] productAInterfaces = { Product.class };

	public static Product newInstance(Product obj) {
		return (Product) Proxy.newProxyInstance(
				obj.getClass().getClassLoader(), productAInterfaces,
				new ProductIH(obj));
	}

	private ProductIH(Object obj) {
		target = obj;
	}

	public void setTarget(Object x) {
		target = x;
	}

	public Object getTarget() {
		return target;
	}

	public Object invoke(Object t, Method m, Object[] args) throws Throwable {
		Object result = null;
		try {
			Method _m = target.getClass().getMethod(m.getName());
			result = _m.invoke(target);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return result;
	}
}