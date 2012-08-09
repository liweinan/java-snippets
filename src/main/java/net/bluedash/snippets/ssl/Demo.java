package net.bluedash.snippets.ssl;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 7/12/12
 * Time: 2:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Demo extends JPanel {

    public static void main(String[] args) {
        System.out.println(System.getProperty("javax.net.ssl.trustStore"));
        System.out.println(System.getProperty("javax.net.ssl.keyStore"));
    }
}
