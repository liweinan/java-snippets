package io.weli.lang.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Play {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

//        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//                .map(Integer::parseInt)
//                .collect(toList());
        List<Integer> arr = Arrays.asList(769082435, 210437958, 673982045, 375809214, 380564127);
        io.weli.lang.sort.MinMax.miniMaxSum(arr);

        bufferedReader.close();

        var l = new ArrayList<Integer>();
        l.add(4);
        l.add(2);
        l.add(3);
        l.add(1);
        Collections.sort(l);
        Collections.sort(l, Collections.reverseOrder());
        System.out.println("------------------");
        l.stream().forEach(System.out::println);
    }
}
