package net.bluedash.snippets.io;

import java.io.Console;

public class In {

	/*
	 * mvn -q exec:rmi -Dexec.mainClass="net.bluedash.snippets.io.In"
	 */
	public static void main(String[] args) {
		Console console = System.console(); // get current console
		String in = console.readLine("Enter your name: ");
		console.printf("Your name: %s\n", in);

	}

}
