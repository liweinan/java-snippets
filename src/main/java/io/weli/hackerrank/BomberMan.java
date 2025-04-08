package io.weli.hackerrank;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-bomber-man/problem
// https://github.com/RyanFehr/HackerRank/blob/master/Algorithms/Implementation/The%20Bomberman%20Game/Solution.java
public class BomberMan {

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


        int rowSize = grid.size();
        int colSize = grid.get(0).length();

        if (n == 1) {
            return grid; // just 1 second passed so there is no change.
        }

        // full of bombs at even seconds
        if (n % 2 == 0) {
            List<String> out = new ArrayList<>();
            for (int _i = 0; _i < rowSize; _i++) {
                StringBuilder sb = new StringBuilder();
                for (int _j = 0; _j < colSize; _j++) {
                    sb.append("O");
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
        int[][] bombMap = new int[rowSize][colSize];

        // initialize the bombMap at first second
        for (int _i = 0; _i < rowSize; _i++) {
            for (int _j = 0; _j < colSize; _j++) {
                char c = grid.get(_i).charAt(_j);
                if (c == 'O') {
                    bombMap[_i][_j] = 2; // at first second, the initial bombs are 2 seconds to explode.
                } else {
                    bombMap[_i][_j] = 0;
                }
            }
        }

        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();

        for (int s = 2; s <= 5; s++) {
            if (s % 2 == 0) {
                plantBombs(rowSize, colSize, bombMap);
            } else {
                explode(rowSize, colSize, bombMap);
            }
            printBombMap(bombMap, s);

            if (s == 3) {
                first = dumpBombMap(rowSize, colSize, bombMap);
            }

            if (s == 5) {
                second = dumpBombMap(rowSize, colSize, bombMap);
            }
        }

//        return dumpBombMap(rowSize, colSize, bombMap);

        if (n % 4 == 3) { // 3, 7, 11 ...
            return first;
        } else {
            return second; // 5, 9, 13 ...
        }
    }

    private static void explode(int rowSize, int colSize, int[][] bombMap) {
        boolean[][] explodeMap = initExplodeMap(rowSize, colSize);

        for (int _i = 0; _i < rowSize; _i++) {
            for (int _j = 0; _j < colSize; _j++) {
                bombMap[_i][_j]--;

                if (bombMap[_i][_j] == 0) { // the time bomb to explode
                    // mark the bomb to explode;
                    markToExplodeBombs(explodeMap, _i, _j, rowSize, colSize);
                }
            }
        }

        detonate(rowSize, colSize, bombMap, explodeMap);

    }

    private static void detonate(int rowSize, int colSize, int[][] bombMap, boolean[][] explodeMap) {
        for (int _i = 0; _i < rowSize; _i++) {
            for (int _j = 0; _j < colSize; _j++) {
                if (explodeMap[_i][_j] == true) {
                    bombMap[_i][_j] = 0;
                }
            }
        }
    }

    private static boolean[][] initExplodeMap(int rowSize, int colSize) {
        boolean[][] explodeMap = new boolean[rowSize][colSize];
        for (int _k = 0; _k < rowSize; _k++) {
            for (int _l = 0; _l < colSize; _l++) {
                explodeMap[_k][_l] = false;
            }
        }
        return explodeMap;
    }

    private static void plantBombs(int rowSize, int colSize, int[][] bombMap) {
        for (int _i = 0; _i < rowSize; _i++) {
            for (int _j = 0; _j < colSize; _j++) {
                int c = bombMap[_i][_j];
                if (c == 0) { // empty slot
                    // fill the empty slot with a bomb
                    bombMap[_i][_j] = 3;
                } else {
                    bombMap[_i][_j]--;
                }
            }
        }
    }

//    @NotNull
    private static List<String> dumpBombMap(int rowSize, int colSize, int[][] bombMap) {
        List<String> out = new ArrayList<>();

        for (int _i = 0; _i < rowSize; _i++) {
            StringBuilder sb = new StringBuilder();
            for (int _j = 0; _j < colSize; _j++) {
                if (bombMap[_i][_j] == 0) {
                    sb.append(".");
                } else {
                    sb.append("O");
                }
            }
            out.add(sb.toString());
        }

        return out;
    }

    private static void markToExplodeBombs(boolean[][] explodeMap, int _i, int _j, int rowSize, int colSize) {
        if (_i - 1 >= 0) {
            explodeMap[_i - 1][_j] = true;
        }
        if (_j - 1 >= 0) {
            explodeMap[_i][_j - 1] = true;
        }
        if (_i + 1 < rowSize) {
            explodeMap[_i + 1][_j] = true;
        }
        if (_j + 1 < colSize) {
            explodeMap[_i][_j + 1] = true;
        }
    }

    private static void printBombMap(int[][] bombMap, int n) {
        System.out.printf("bombMap at second %d-> \n", n);
        Arrays.stream(bombMap).forEach(row -> {
            Arrays.stream(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    /*
6 7 3
.......
...O...
....O..
.......
OO.....
OO.....

expect output:

OOO.OOO
OO...OO
OOO...O
..OO.OO
...OOOO
...OOOO
     */

    // try second 7
/*
    /*
6 7 7
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
