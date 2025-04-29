package io.weli.hackerrank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-climbing-the-leaderboard
public class ClimbingTheLeaderboard {

    public static List<Integer> climbingLeaderboard(List<Integer> ranked, List<Integer> player) {
        // Write your code here
        // Remove duplicates and create array of unique scores in descending order
        Integer[] uniqueRanks = ranked.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toArray(Integer[]::new);

        List<Integer> result = new ArrayList<>();
        int rankIndex = uniqueRanks.length - 1; // Start from lowest score
        System.out.println("uniqueRanks: " + Arrays.stream(uniqueRanks).toList());

        // Process each player score
        for (int score : player) {
            // Move rankIndex up until we find where score fits
            System.out.println("score: " + score);
            System.out.println("rankIndex: " + rankIndex);

            while (rankIndex >= 0 && score >= uniqueRanks[rankIndex]) {
                System.out.printf("uniqueRanks[%d]: %d\n", rankIndex, uniqueRanks[rankIndex]);
                rankIndex--;
            }
            // Rank is index + 2 (because index -1 means top rank, and we add 1 for 1-based ranking)
            System.out.printf("score: %d, index: %d\n", score, rankIndex + 2);
            result.add(rankIndex + 2);
        }

        return result;
    }

    public static void main(String[] args) throws IOException {

        var ranked = List.of(100, 90, 90, 80);
        var player = List.of(70, 60, 80, 105);

        List<Integer> result = climbingLeaderboard(ranked, player);
        System.out.println(result);

    }
}
