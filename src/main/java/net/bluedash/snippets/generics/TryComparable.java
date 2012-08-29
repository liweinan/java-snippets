package net.bluedash.snippets.generics;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public class TryComparable {

    public static <E> Comparator<List<E>> listComparator(final Comparator<? super E> comp) {
        return new Comparator<List<E>>() {
            public int compare(List<E> list1, List<E> list2) {
                int n1 = list1.size();
                int n2 = list2.size();
                for (int i = 0; i < Math.min(n1, n2); i++) {
                    int k = comp.compare(list1.get(i), list2.get(i));
                    if (k != 0) return k;
                }
                return (n1 < n2) ? -1 : (n1 == n2) ? 0 : 1;
            }
        };
    }

    public static <S extends Readable & Closeable, T extends Appendable & Closeable>
    void copy(S src, T trg, int size) throws IOException {
        try {
            CharBuffer buf = CharBuffer.allocate(size);
            int i = src.read(buf);
            while (i >= 0) {
                buf.flip(); // prepare buffer for writing
                trg.append(buf);
                buf.clear(); // prepare buffer for reading
                i = src.read(buf);
            }
        } finally {
            src.close();
            trg.close();
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println("a".compareTo("b"));

        // The longer the length of string, the bigger.
        // Copied from book - Java Generics and Collections.
        Comparator<String> sizeOrderComp = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return
                        s1.length() < s2.length() ? -1 : s1.length() > s2.length() ? 1 : s1.compareTo(s2);
            }
        };

        System.out.println("two".compareTo("three") > 0);
        System.out.println(sizeOrderComp.compare("two", "three") < 0);

        List<String> strings = Arrays.asList("from", "aaa", "to", "zzz");
        System.out.println(Collections.max(strings));
        System.out.println(Collections.max(strings, sizeOrderComp));

        // test listComparator
        Comparator<List<String>> stringListComp = listComparator(sizeOrderComp);
        List<String> strings2 = Arrays.asList("from", "aaa", "to", "yyy");
        System.out.println(stringListComp.compare(strings, strings2));

        //test copy
        int size = 32;
        FileReader r = new FileReader("file.in");
        FileWriter w = new FileWriter("file.out");
        copy(r, w, size);
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("file.out"));
        copy(br, bw, size);
    }

}
