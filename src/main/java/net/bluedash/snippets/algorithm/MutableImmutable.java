package net.bluedash.snippets.algorithm;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MutableImmutable {

    public static void main(String[] args) {
        String a = "abc";
        String b = a; // copy by value
        a = "def";
        System.out.println(a);
        System.out.println(b);

        String[] arr = {"a", "b", "c"};
        String[] arr2 = arr; // copy by reference
        arr2[2] = "d";
        System.out.println(toString(arr));
        System.out.println(toString(arr2));
    }

    public static final String toString(String[] strs) {
        StringBuffer buffer = new StringBuffer();
        for (String s : strs) {
            buffer.append(s);
        }
        return buffer.toString();
    }
}
