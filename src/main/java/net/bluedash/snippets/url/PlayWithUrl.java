package net.bluedash.snippets.url;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: weinanli
 * Date: 4/19/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayWithUrl {

    public static void main(String[] args) throws Exception {
        String encodedAddr = "http://127.0.0.1";
        encodedAddr = encodedAddr.replace('/', '-').replace(':', '_').replace('%', '-');
        System.out.println(encodedAddr);
        System.out.println(URLEncoder.encode(encodedAddr, "UTF-8"));
    }
}
