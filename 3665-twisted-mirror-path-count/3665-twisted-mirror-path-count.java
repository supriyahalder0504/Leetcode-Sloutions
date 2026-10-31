class Solution {
    public int uniquePaths(int[][] grid) {
     int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;

        // Precompute mirror destinations
        int[][][] next = new int[m][n][2];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                for (int d = 0; d < 2; d++) {

                    if (grid[i][j] == 0) {
                        next[i][j][d] = -1;
                        continue;
                    }

                    int ni = i + (d == 0 ? 1 : 0);
                    int nj = j + (d == 1 ? 1 : 0);

                    if (ni >= m || nj >= n) {
                        next[i][j][d] = -1;
                    } 
                    else if (grid[ni][nj] == 0) {
                        next[i][j][d] = ni * n + nj;
                    } 
                    else {
                        next[i][j][d] = next[ni][nj][1 - d];
                    }
                }
            }
        }

        // Dynamic programming
        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) {
                    continue;
                }

                // Move right
                if (j + 1 < n) {

                    int dest;

                    if (grid[i][j + 1] == 0) {
                        dest = i * n + j + 1;
                    } else {
                        dest = next[i][j + 1][0];
                    }

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;

                        dp[r][c] =
                            (dp[r][c] + dp[i][j]) % MOD;
                    }
                }

                // Move down
                if (i + 1 < m) {

                    int dest;

                    if (grid[i + 1][j] == 0) {
                        dest = (i + 1) * n + j;
                    } else {
                        dest = next[i + 1][j][1];
                    }

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;

                        dp[r][c] =
                            (dp[r][c] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}