class Solution {
    public long maxScore(int[] nums1, int[] nums2, int k) {
     int n = nums1.length;

        // Store pairs: {nums2[i], nums1[i]}
        int[][] pairs = new int[n][2];

        for (int i = 0; i < n; i++) {
            pairs[i][0] = nums2[i];
            pairs[i][1] = nums1[i];
        }

        // Sort by nums2 in descending order
        Arrays.sort(pairs, (a, b) -> b[0] - a[0]);

        // Min Heap to maintain largest k elements of nums1
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        long sum = 0;
        long maxScore = 0;

        for (int[] pair : pairs) {

            int num2 = pair[0];
            int num1 = pair[1];

            pq.offer(num1);
            sum += num1;

            // Keep only k largest nums1 values
            if (pq.size() > k) {
                sum -= pq.poll();
            }

            // Calculate score when exactly k elements are selected
            if (pq.size() == k) {
                maxScore = Math.max(maxScore, sum * num2);
            }
        }

        return maxScore;       
    }
}