class Solution {
    public int[] asteroidCollision(int[] asteroids) {
    int[] stack = new int[asteroids.length];
        int top = -1;

        for (int asteroid : asteroids) {
            boolean alive = true;

            while (alive && asteroid < 0 && top >= 0 && stack[top] > 0) {
                if (stack[top] < -asteroid) {
                    // The top asteroid explodes
                    top--;
                } else if (stack[top] == -asteroid) {
                    // Both asteroids explode
                    top--;
                    alive = false;
                } else {
                    // The current asteroid explodes
                    alive = false;
                }
            }

            if (alive) {
                stack[++top] = asteroid;
            }
        }

        int[] result = new int[top + 1];
        System.arraycopy(stack, 0, result, 0, top + 1);

        return result;      
    }
}