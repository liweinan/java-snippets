package io.weli.string;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Xor {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s1 = bufferedReader.readLine();
        String s2 = bufferedReader.readLine();

        List<Integer> s1s = Arrays.stream(s1.split("")).map(Integer::parseInt).toList();
        List<Integer> s2s = Arrays.stream(s2.split("")).map(Integer::parseInt).toList();

//        s1s.forEach(System.out::println);

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < s1s.size(); i++) {
            out.append(String.format("%d", s1s.get(i) ^ s2s.get(i)));
        }

        bufferedReader.close();

        System.out.println(out);
    }
}
