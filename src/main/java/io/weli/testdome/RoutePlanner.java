package io.weli.testdome;

import java.util.*;

// https://www.testdome.com/library?questionId=110104

public class RoutePlanner {

    /**
     * Determines if there exists a path from the start (fromRow, fromColumn) to the destination (toRow, toColumn)
     * in a boolean matrix where true represents a road and false represents no road.
     * Roads are connected only if they are immediately left, right, above, or below each other.
     * Uses Breadth-First Search (BFS) to explore all possible paths efficiently.
     *
     * @param fromRow    Starting row index
     * @param fromColumn Starting column index
     * @param toRow      Destination row index
     * @param toColumn   Destination column index
     * @param mapMatrix  Boolean matrix representing the map (true for road, false for no road)
     * @return true if a path exists from start to destination, false otherwise
     */
    public static boolean routeExists(int fromRow, int fromColumn, int toRow, int toColumn, boolean[][] mapMatrix) {
        // Validate start and destination: ensure they are within bounds and are roads (true)
        if (!isValid(fromRow, fromColumn, mapMatrix) || !isValid(toRow, toColumn, mapMatrix)) {
            return false; // No path possible if start or destination is invalid
        }

        // Get matrix dimensions
        int rows = mapMatrix.length;
        int cols = mapMatrix[0].length;

        // Initialize visited array to track explored cells and avoid cycles
        boolean[][] visited = new boolean[rows][cols];

        // Initialize queue for BFS to store cells to explore
        Queue<int[]> queue = new LinkedList<>();

        // Add starting position to queue and mark it as visited
        queue.offer(new int[]{fromRow, fromColumn});
        visited[fromRow][fromColumn] = true;

        // Define four possible directions: up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        // BFS loop: continue until all reachable cells are explored or destination is found
        while (!queue.isEmpty()) {
            // Dequeue the current cell
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            // Check if the current cell is the destination
            if (row == toRow && col == toColumn) {
                return true; // Path found
            }

            // Explore all four adjacent cells
            for (int[] dir : directions) {
                // Calculate new coordinates
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Check if the new cell is valid (within bounds, is a road, and not visited)
                if (isValid(newRow, newCol, mapMatrix) && !visited[newRow][newCol]) {
                    // Add the new cell to the queue for further exploration
                    queue.offer(new int[]{newRow, newCol});
                    // Mark the new cell as visited to avoid revisiting
                    visited[newRow][newCol] = true;
                }
            }
        }

        // If queue is empty and destination not found, no path exists
        return false;
    }

    /**
     * Helper method to validate if a cell is within bounds and is a road (true).
     *
     * @param row       Row index to check
     * @param col       Column index to check
     * @param mapMatrix Boolean matrix representing the map
     * @return true if the cell is valid (within bounds and a road), false otherwise
     */
    private static boolean isValid(int row, int col, boolean[][] mapMatrix) {
        return row >= 0 && row < mapMatrix.length &&
                col >= 0 && col < mapMatrix[0].length &&
                mapMatrix[row][col];
    }

    /**
     * Main method to test the routeExists functionality with an example map.
     * The example map has a path from (0,0) to (2,2), so it should print true.
     */
    public static void main(String[] args) {
        // Example map: true indicates a road, false indicates no road
        boolean[][] mapMatrix = {
                {true, false, false},
                {true, true, false},
                {false, true, true}
        };

        // Test the routeExists method and print result
        System.out.println(routeExists(0, 0, 2, 2, mapMatrix)); // Expected output: true
    }
}