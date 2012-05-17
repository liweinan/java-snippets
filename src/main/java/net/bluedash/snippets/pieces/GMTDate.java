package net.bluedash.snippets.pieces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GMTDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getGMTDate());

	}

	public static String getGMTDate() {
		SimpleDateFormat formater = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		formater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formater.format(new Date());
	}

}
