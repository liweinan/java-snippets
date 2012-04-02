package net.bluedash.snippets.multicast;
/*
 * mvn exec:java -Dexec.mainClass="net.bluedash.snippets.multicast.MulticastServer"
 */
public class MulticastServer {
	public static void main(String[] args) {
		new MulticastServerThread().start();
	}
}
