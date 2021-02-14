package io.weli.lc;

public class Maze {

    public class Solution {
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            boolean[][] visited = new boolean[maze.length][maze[0].length]; // row * col
            return dfs(maze, start, destination, visited);
        }

        public boolean dfs(int[][] maze, int[] start, int[] destination, boolean[][] visited) {

            if (visited[start[0]][start[1]])
                return false;
            if (start[0] == destination[0] && start[1] == destination[1]) // only zero and one = row, col
                return true;
            visited[start[0]][start[1]] = true;

            // right
            // left
            // up
            // down
            int r = start[1] + 1, l = start[1] - 1, u = start[0] - 1, d = start[0] + 1;

            while (r < maze[0].length && maze[start[0]][r] == 0) // right
                r++;
            if (dfs(maze, new int[]{start[0], r - 1}, destination, visited))
                return true;

            while (l >= 0 && maze[start[0]][l] == 0) //left
                l--;
            if (dfs(maze, new int[]{start[0], l + 1}, destination, visited))
                return true;

            while (u >= 0 && maze[u][start[1]] == 0) //up
                u--;
            if (dfs(maze, new int[]{u + 1, start[1]}, destination, visited))
                return true;

            while (d < maze.length && maze[d][start[1]] == 0) //down
                d++;
            if (dfs(maze, new int[]{d - 1, start[1]}, destination, visited))
                return true;

            return false;
        }
    }

//    复杂度分析
//
//    作者：LeetCode
//    链接：https://leetcode-cn.com/problems/the-maze/solution/mi-gong-by-leetcode/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
