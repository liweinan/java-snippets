package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/14/16.
 */
public class InstanceTests {

    public static void main(String[] args) {
        List<?> a = new ArrayList();
        if (a instanceof List<?>) {
            System.out.println("a instanceof List<?>");
        }
        if (a instanceof List) {
            System.out.println("a instanceof List");
        }
        if (a instanceof ArrayList) {
            System.out.println("a instanceof ArrayList");
        }
        if (a instanceof ArrayList<?>) {
            System.out.println("a instanceof ArrayList<?>");
        }
        // compile error
//        if (a instanceof List<E>) {
//
//        }
//        if (a instanceof List<String>) {
//
//        }
    }
}
