class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
  List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
        }

        // dist[node][count]
        long[][] dist = new long[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        // Priority queue: {cost, node, consecutiveCount}
        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int startCount = 1;
        dist[0][startCount] = 0;

        pq.offer(new long[]{0, 0, startCount});

        while (!pq.isEmpty()) {

            long[] curr = pq.poll();

            long cost = curr[0];
            int u = (int) curr[1];
            int count = (int) curr[2];

            if (cost != dist[u][count]) {
                continue;
            }

            // Destination reached
            if (u == n - 1) {
                return (int) cost;
            }

            for (int[] edge : graph[u]) {

                int v = edge[0];
                int weight = edge[1];

                int newCount;

                if (labels.charAt(u) == labels.charAt(v)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                if (newCount > k) {
                    continue;
                }

                long newCost = cost + weight;

                if (newCost < dist[v][newCount]) {

                    dist[v][newCount] = newCost;

                    pq.offer(new long[]{
                        newCost,
                        v,
                        newCount
                    });
                }
            }
        }

        return -1;       
    }
}