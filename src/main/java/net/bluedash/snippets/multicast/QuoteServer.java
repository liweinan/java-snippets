package net.bluedash.snippets.multicast;
/*
 * mvn exec:java -Dexec.mainClass="net.bluedash.snippets.multicast.QuoteServer"
 */
public class QuoteServer {
	public static void main(String[] args) {
		new QuoteServerThread().start();
	}
}
