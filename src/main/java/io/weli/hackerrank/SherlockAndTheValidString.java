package io.weli.hackerrank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-sherlock-and-valid-string/problem
// https://github.com/liweinan/deepseek-answers/blob/main/files/hr-sherlock.md
public class SherlockAndTheValidString {
    public static String isValid(String s) {
        // Create frequency map for characters
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // Get set of frequencies
        Set<Integer> frequencies = new HashSet<>(freq.values());

        // If all characters have same frequency, string is valid
        if (frequencies.size() == 1) {
            return "YES";
        }

        // If more than two different frequencies, can't be valid by removing one
        if (frequencies.size() > 2) {
            return "NO";
        }

        // Get the two different frequencies
        Integer[] freqArray = frequencies.toArray(new Integer[0]);
        int f1 = freqArray[0];
        int f2 = freqArray[1];

        // Count how many characters have each frequency
        int countF1 = 0, countF2 = 0;
        for (int f : freq.values()) {
            if (f == f1) countF1++;
            else countF2++;
        }

        // Check if removing one character can make all frequencies equal
        // Case 1: One character appears once and others have same frequency
        if ((f1 == 1 && countF1 == 1) || (f2 == 1 && countF2 == 1)) {
            return "YES";
        }

        // Case 2: One character appears one more time than others
        if ((f1 == f2 + 1 && countF1 == 1) || (f2 == f1 + 1 && countF2 == 1)) {
            return "YES";
        }

        return "NO";
    }

    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//        String s = bufferedReader.readLine();
//        String result = SherlockAndTheValidString.isValid(s);
//        bufferedWriter.write(result);
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();

        System.out.println(SherlockAndTheValidString.isValid("abcd"));
        System.out.println(SherlockAndTheValidString.isValid("abbcc"));
        System.out.println(SherlockAndTheValidString.isValid("aabc"));
        System.out.println(SherlockAndTheValidString.isValid("aabbbc"));
    }

}
