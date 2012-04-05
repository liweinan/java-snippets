package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ProductIH implements InvocationHandler {
	private Product target = null;
	static private Class[] productAInterfaces = { Product.class };

	public static Product newInstance(Product obj) {
		return (Product) Proxy.newProxyInstance(
				obj.getClass().getClassLoader(), productAInterfaces,
				new ProductIH(obj));
	}

	private ProductIH(Product obj) {
		target = obj;
	}

	public void setTarget(Product x) {
		target = x;
	}

	public Product getTarget() {
		return target;
	}

	public Object invoke(Object t, Method m, Object[] args) throws Throwable {
		Object result = null;
		try {
			result = m.invoke(target, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return result;
	}
}