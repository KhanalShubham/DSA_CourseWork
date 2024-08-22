import java.util.Arrays;

public class SecretDecoderRing_Question_2 {

    static class Solution {
        /**
         * This method decodes a given string by applying a series of shifts to its characters.
         *
         * @param s The original string to be decoded.
         * @param shifts A 2D array where each entry specifies the start and end indices
         *               for the shift, and the direction of the shift (1 for right, 0 for left).
         * @return The decoded string after applying all the shifts.
         */
        public String decodeMessage(String s, int[][] shifts) {
            // Convert the string to a character array for easier manipulation
            char[] message = s.toCharArray();
            int n = message.length;

            // Array to store the net effect of shifts at each position
            int[] rotations = new int[n + 1];

            // Process all shifts
            for (int[] shift : shifts) {
                int start = shift[0];
                int end = shift[1];
                int direction = shift[2] == 1 ? 1 : -1; // 1 for right shift, -1 for left shift

                // Increment at the start of the shift and decrement right after the end
                rotations[start] += direction;
                rotations[end + 1] -= direction;
            }

            // Calculate cumulative rotations and apply them to each character in the message
            int currentRotation = 0;
            for (int i = 0; i < n; i++) {
                currentRotation += rotations[i];

                // Calculate the new character after applying the rotation
                int shift = ((message[i] - 'a' + currentRotation) % 26 + 26) % 26;
                message[i] = (char) ('a' + shift);
            }

            // Return the new string formed after all shifts
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
