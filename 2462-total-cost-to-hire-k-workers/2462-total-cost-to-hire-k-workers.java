class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
         PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        int i = 0;
        int j = costs.length - 1;

        // Fill the left and right heaps
        while (i <= j && left.size() < candidates) {
            left.offer(costs[i++]);
        }

        while (i <= j && right.size() < candidates) {
            right.offer(costs[j--]);
        }

        long total = 0;

        // Hire exactly k workers
        for (int hire = 0; hire < k; hire++) {

            if (right.isEmpty() ||
                (!left.isEmpty() && left.peek() <= right.peek())) {

                total += left.poll();

                if (i <= j) {
                    left.offer(costs[i++]);
                }

            } else {

                total += right.poll();

                if (i <= j) {
                    right.offer(costs[j--]);
                }
            }
        }

        return total;
       
    }
}