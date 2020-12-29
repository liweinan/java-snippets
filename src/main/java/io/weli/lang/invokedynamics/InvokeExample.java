package io.weli.lang.invokedynamics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 12/05/2017.
 */
public class InvokeExample {

    public static void main(String[] args) {
        InvokeExample sc = new InvokeExample();
        sc.run();
    }

    private void run() {
        List<String> ls = new ArrayList<>();
        ls.add("Good Day");

        ArrayList<String> als = new ArrayList<>();
        als.add("Dydh Da");
    }
}
