package net.bluedash.snippets.classloader;


/*
 * mvn install 
 * mvn exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.ProductReloadTest"
 */
public class ProductReloadTest {

	public static void main(String[] args) throws Throwable {
		Product product = ProductFactory.newInstance();
		product.show();
		
		ProductFactory.reload("net/bluedash/snippets/classloader/impl2");
		/* the product is replaced by a new instance */
		product.show();
	}

}
