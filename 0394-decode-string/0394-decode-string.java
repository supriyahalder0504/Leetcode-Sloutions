class Solution {
    public String decodeString(String s) {
   Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();

        StringBuilder current = new StringBuilder();
        int num = 0;

        for (char ch : s.toCharArray()) {

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }

            else if (ch == '[') {
                countStack.push(num);
                stringStack.push(current);

                current = new StringBuilder();
                num = 0;
            }

            else if (ch == ']') {
                int count = countStack.pop();
                StringBuilder previous = stringStack.pop();

                for (int i = 0; i < count; i++) {
                    previous.append(current);
                }

                current = previous;
            }

            else {
                current.append(ch);
            }
        }

        return current.toString();        
    }
}