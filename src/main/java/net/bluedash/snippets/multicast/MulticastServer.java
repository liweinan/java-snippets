package net.bluedash.snippets.multicast;
/*
 * mvn exec:rmi -Dexec.mainClass="net.bluedash.snippets.multicast.MulticastServer"
 */
public class MulticastServer {
	public static void main(String[] args) {
		new MulticastServerThread().start();
	}
}
