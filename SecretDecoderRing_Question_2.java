import java.util.Arrays;

public class SecretDecoderRing_Question_2 {
    static class Solution {
        public String decodeMessage(String s, int[][] shifts) {
            char[] message = s.toCharArray();
            int n = message.length;
            int[] rotations = new int[n + 1];

            // Process all shifts
            for (int[] shift : shifts) {
                int start = shift[0];
                int end = shift[1];
                int direction = shift[2] == 1 ? 1 : -1;

                rotations[start] += direction;
                rotations[end + 1] -= direction;
            }

            // Calculate cumulative rotations
            int currentRotation = 0;
            for (int i = 0; i < n; i++) {
                currentRotation += rotations[i];
                int shift = ((message[i] - 'a' + currentRotation) % 26 + 26) % 26;
                message[i] = (char) ('a' + shift);
            }

            return new String(message);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1
        String s1 = "hello";
        int[][] shifts1 = {{0, 1, 1}, {2, 3, 0}, {0, 2, 1}};
        String result1 = solution.decodeMessage(s1, shifts1);
        System.out.println("Test case 1:");
        System.out.println("Input: s = \"" + s1 + "\", shifts = " + Arrays.deepToString(shifts1));
        System.out.println("Output: " + result1);
        System.out.println("Expected: jglko");
        System.out.println();

        // Test case 2
        String s2 = "abcde";
        int[][] shifts2 = {{0, 4, 1}, {1, 3, 0}, {2, 2, 1}};
        String result2 = solution.decodeMessage(s2, shifts2);
        System.out.println("Test case 2:");
        System.out.println("Input: s = \"" + s2 + "\", shifts = " + Arrays.deepToString(shifts2));
        System.out.println("Output: " + result2);
        System.out.println("Expected: bcdfa");
        System.out.println();

        // Test case 3
        String s3 = "zyxwvutsrqponmlkjihgfedcba";
        int[][] shifts3 = {{0, 25, 1}};
        String result3 = solution.decodeMessage(s3, shifts3);
        System.out.println("Test case 3:");
        System.out.println("Input: s = \"" + s3 + "\", shifts = " + Arrays.deepToString(shifts3));
        System.out.println("Output: " + result3);
        System.out.println("Expected: abcdefghijklmnopqrstuvwxyz");
    }
}