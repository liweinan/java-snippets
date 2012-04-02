package net.bluedash.snippets.multicast;
/*
 * mvn exec:java -Dexec.mainClass="net.bluedash.snippets.multicast.QuoteServer"
 */
public class MulticastServer {
	public static void main(String[] args) {
		new MulticastServerThread().start();
	}
}
