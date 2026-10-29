class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
      List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] parent = new int[n];
        int[] order = new int[n];
        int[] stack = new int[n];
        int[] bit = new int[n + 2];

        char[] chars = s.toCharArray();

        // Iterative DFS
        Arrays.fill(parent, -1);

        int top = 0;
        int count = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = count;
            order[count++] = u;

            for (int v : graph[u]) {
                if (parent[v] != -1) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        // Calculate subtree sizes
        int[] size = new int[n];
        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
            up[0][u] = parent[u];
        }

        // Binary lifting table
        for (int j = 1; j < LOG; j++) {
            for (int u = 0; u < n; u++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }
        }

        // Root-to-node prefix XOR masks
        int[] pref = new int[n];

        for (int u : order) {
            pref[u] = 1 << (chars[u] - 'a');

            if (u != 0) {
                pref[u] ^= pref[parent[u]];
            }
        }

        // Initialize Fenwick tree as an XOR difference array
        for (int u = 0; u < n; u++) {
            rangeXor(bit, tin[u] + 1, tin[u] + 1, pref[u]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int delta =
                        (1 << (chars[u] - 'a')) ^
                        (1 << (c - 'a'));

                if (delta != 0) {
                    // Every prefix mask in this subtree changes
                    rangeXor(
                            bit,
                            tin[u] + 1,
                            tout[u] + 1,
                            delta
                    );

                    chars[u] = c;
                }

            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int w = lca(u, v, depth, up, LOG);

                int pu = query(bit, tin[u] + 1);
                int pv = query(bit, tin[v] + 1);

                int pathMask =
                        pu ^ pv ^ (1 << (chars[w] - 'a'));

                // At most one character may have odd frequency
                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    private int lca(
            int u, int v, int[] depth, int[][] up, int LOG) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    private void rangeXor(
            int[] bit, int left, int right, int value) {

        update(bit, left, value);
        update(bit, right + 1, value);
    }

    private void update(int[] bit, int i, int value) {
        while (i < bit.length) {
            bit[i] ^= value;
            i += i & -i;
        }
    }

    private int query(int[] bit, int i) {
        int result = 0;

        while (i > 0) {
            result ^= bit[i];
            i -= i & -i;
        }

        return result;
    }
}