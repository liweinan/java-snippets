package net.bluedash.snippets.io;

import java.io.IOException;

/**
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.io.Hello"
 */
public class Hello {
	public static void main(String[] args) throws IOException {
		byte[] hello = { 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100,
				33, 10, 13 };
		System.out.write(hello);
		System.err.write(hello);
	}

}
