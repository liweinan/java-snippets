package net.bluedash.snippets.pieces;

/*
 * mvn install
 * mvn -q exec:rmi -Dexec.mainClass="net.bluedash.snippets.pieces.ClassLoadingMethods"
 */
public class ClassLoadingMethods {

	public static void main(String[] args) throws ClassNotFoundException {
		/* 方法 1: 使用 .class */
		Class c = String[].class;

		/* 方法 2: 使用实例的getClass()方法 */
		c = new String[1].getClass();

		/* 方法 3: 使用 Class.forName */
		c = Class.forName("[Ljava.lang.String;");
	}

}
