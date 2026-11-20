class Solution {
    public int nearestExit(char[][] maze, int[] entrance) {
   int m = maze.length;
        int n = maze[0].length;

        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();

        int r = entrance[0];
        int c = entrance[1];

        queue.offer(new int[]{r, c, 0});
        visited[r][c] = true;

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];
            int steps = current[2];

            // Check if current cell is an exit
            if (steps > 0 &&
                (row == 0 || row == m - 1 ||
                 col == 0 || col == n - 1)) {
                return steps;
            }

            // Explore all four directions
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < m &&
                    newCol >= 0 && newCol < n &&
                    maze[newRow][newCol] == '.' &&
                    !visited[newRow][newCol]) {

                    visited[newRow][newCol] = true;

                    queue.offer(new int[]{
                        newRow, newCol, steps + 1
                    });
                }
            }
        }

        return -1;      
    }
}