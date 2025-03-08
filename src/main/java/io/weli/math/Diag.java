package io.weli.math;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

/*
Input (stdin)
3
11 2 4
4 5 6
10 8 -12
Your Output (stdout)
0
Expected Output
15
 */
public class Diag {

    public static int diagonalDifference(List<List<Integer>> arr) {
        // Write your code here
        int left = 0;
        int right = 0;

        int n = arr.size();
        System.out.println("arr size: " + arr.size());

        for (int i = 0; i < n; i++) {
            System.out.println("left curr: " + arr.get(i).get(i));
            left += arr.get(i).get(i);
        }

        System.out.println("left: " + left);

        for (int i = 0; i < n; i++) {
            System.out.println("right curr: " + arr.get(i).get(n - i - 1));
            right += arr.get(i).get(n - i - 1);
        }
        System.out.println("right: " + right);

        return Math.abs(left - right);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> arr = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                arr.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Diag.diagonalDifference(arr);

        System.out.println("result: " + result);
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();

        bufferedReader.close();
//        bufferedWriter.close();
    }
}
