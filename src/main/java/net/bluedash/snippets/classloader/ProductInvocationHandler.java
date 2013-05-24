package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ProductInvocationHandler implements InvocationHandler {
	private Object productInstance = null;
	static private Class[] productInterface = { Product.class };

	public static Product newInstance(Product productInstance) {
		return (Product) Proxy.newProxyInstance(
                productInstance.getClass().getClassLoader(), productInterface,
                new ProductInvocationHandler(productInstance));
	}

	private ProductInvocationHandler(Object productInstance) {
		this.productInstance = productInstance;
	}

	public void setProductInstance(Object productInstance) {
		this.productInstance = productInstance;
	}

	public Object getProductInstance() {
		return productInstance;
	}

	public Object invoke(Object t, Method m, Object[] args) throws Throwable {
		Object result;
		try {
			Method _m = productInstance.getClass().getMethod(m.getName());
			result = _m.invoke(productInstance);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return result;
	}
}