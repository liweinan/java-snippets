package io.weli.testdome;

// Game Platform
/*
https://www.testdome.com/library?page=1&skillArea=30&questionId=117604
Example Case: Correct answer
Speed reduced to zero or below: Correct answer
Various inclines: Correct answer
 */
public class GamePlatform {
    public static double calculateFinalSpeed(double initialSpeed, int[] inclinations) {
        double currentSpeed = initialSpeed;

        for (int angle : inclinations) {
            currentSpeed -= angle;

            // If speed drops to 0 or below, return 0 immediately
            if (currentSpeed <= 0) {
                return 0;
            }
        }

        return currentSpeed;
    }

    public static void main(String[] args) {
        System.out.println(calculateFinalSpeed(60.0, new int[] { 0, 30, 0, -45, 0 })); // Should print 75.0
    }
}
