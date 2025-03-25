package io.weli.hackerrank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-bomber-man/problem
// https://github.com/RyanFehr/HackerRank/blob/master/Algorithms/Implementation/The%20Bomberman%20Game/Solution.java
public class BomberMan {

    public static final String BOMB = "O";

    /*
     * Complete the 'bomberMan' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY grid
     */

    // cycle 3 and cycle 5 are the same
    // i % 2 == 0 -> full map are bombs
    public static List<String> bomberMan(int n, List<String> grid) {
        // Write your code here
        List<String> out = new ArrayList<>();

        int rowSize = grid.size();
        int colSize = grid.get(0).length();

        if (n == 1) {
            return grid; // just 1 second passed so there is no change.
        }

        // full of bombs at even seconds
        if (n % 2 == 0) {
            for (int _i = 0; _i < rowSize; _i++) {
                StringBuilder sb = new StringBuilder();
                for (int _j = 0; _j < colSize; _j++) {
                    sb.append(BOMB);
                }
                out.add(sb.toString());
            }

            return out;
        }

        // calcuate the 3s status and the 5s statuses and record the statuses.
        // first, register the initial bombs.
        // then calc 2s, 3s, 4s, 5s
        // remember the seconds of a bomb
        // needs a method to explod bombs.
        //

        // the integer is the second to explode.
        // 0 = no bomb
        List<List<Integer>> bombMap = new ArrayList<>(rowSize);

        // initialize the bombMap
        for (int _i = 0; _i < rowSize; _i++) {
            bombMap.add(new ArrayList<>(colSize));
            for (int _j = 0; _j < colSize; _j++) {
                char c = grid.get(_i).charAt(_j);
                if (c == 'O') {
                    bombMap.get(_i).add(3);
                } else {
                    bombMap.get(_i).add(0);
                }
            }
        }

        System.out.println("bombMap");
        bombMap.stream().forEach((lst) -> {
            System.out.println(lst);
        });

        return out;
    }

    /*
6 7 3
.......
...O...
....O..
.......
OO.....
OO.....
     */
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r = Integer.parseInt(firstMultipleInput[0]);

        int c = Integer.parseInt(firstMultipleInput[1]);

        int n = Integer.parseInt(firstMultipleInput[2]);

        List<String> grid = IntStream.range(0, r).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = bomberMan(n, grid);

//        bufferedWriter.write(
//                result.stream()
//                        .collect(joining("\n"))
//                        + "\n"
//        );

        bufferedReader.close();
//        bufferedWriter.close();

    }
}
