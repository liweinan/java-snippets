package io.weli.hackerrank;

public class CountingValleys {
    public static int countingValleys(int steps, String path) {
        // Write your code here

        int prevAlt = 0;
        int currAlt = 0;
        int cnt = 0;

        for (int i = 0; i < steps; i++) {
            prevAlt = currAlt;
            char step = path.charAt(i);
            if (step == 'U') {
                currAlt++;
            } else {
                currAlt--;
            }

            if (prevAlt == 0 && currAlt < 0) {
                cnt++;
            }

        }

        return cnt;

    }

    public static void main(String[] args) {
        countingValleys(8, "UDDDUDUU");
    }
}
