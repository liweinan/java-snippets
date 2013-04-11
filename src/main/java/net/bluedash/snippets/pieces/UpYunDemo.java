package net.bluedash.snippets.pieces;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpYunDemo {
	public static void main(String[] args) throws Exception{
		/// 初始化空间
		UpYun upyun = new UpYun("bluedash", "bluedashbaby", "sun5kong");
		File file = new File("/Users/liweinan/projs/upyun-ruby/google.png");
		FileInputStream is = new FileInputStream(file);
		
		URL url = new URL("http://" + "v0.api.upyun.com" + "/" + "bluedash" + "/google.png");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(100);
		conn.setRequestMethod("PUT");
		conn.setUseCaches(false);
		conn.setRequestProperty("Date", "Thu, 17 May 2012 18:24:02 GMT");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Length", is.available() + "");

		System.out.println(upyun.sign(conn, "/bluedash/google.png", is.available()));
	}
}
