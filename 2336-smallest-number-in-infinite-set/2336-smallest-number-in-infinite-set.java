class SmallestInfiniteSet {
PriorityQueue<Integer> pq;
    HashSet<Integer> set;
    int curr;

    public SmallestInfiniteSet() {
  pq = new PriorityQueue<>();
        set = new HashSet<>();
        curr = 1;       
    }
    
    public int popSmallest() {
       if (!pq.isEmpty()) {
            int smallest = pq.poll();
            set.remove(smallest);
            return smallest;    
    }
     return curr++;
    } 
    public void addBack(int num) {
     if (num < curr && !set.contains(num)) {
            pq.offer(num);
            set.add(num);
        }    
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */