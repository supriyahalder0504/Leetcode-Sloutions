class Solution {
    public int minReorder(int n, int[][] connections) {
    List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : connections) {
            int a = edge[0];
            int b = edge[1];

            graph.get(a).add(new int[]{b, 1});
            graph.get(b).add(new int[]{a, 0});
        }

        boolean[] visited = new boolean[n];

        return dfs(0, graph, visited);
    }

    private int dfs(int node, List<List<int[]>> graph,
                    boolean[] visited) {

        visited[node] = true;

        int count = 0;

        for (int[] neighbor : graph.get(node)) {
            int next = neighbor[0];
            int cost = neighbor[1];

            if (!visited[next]) {
                count += cost;
                count += dfs(next, graph, visited);
            }
        }

        return count;      
    }
}