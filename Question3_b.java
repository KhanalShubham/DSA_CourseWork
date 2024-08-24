import java.util.Arrays;

public class Question3_b {

    /**
     * Rearranges passengers in groups of size 'k'.
     * If a group has exactly 'k' passengers, it reverses the order of that group.
     * Otherwise, it keeps the order as is for the remaining passengers.
     *
     * @param passengers Array of passenger IDs.
     * @param k Size of the group to reverse.
     * @return A new array with rearranged passengers.
     */
    public static int[] rearrangePassengers(int[] passengers, int k) {
        int n = passengers.length; // Total number of passengers
        int[] result = new int[n]; // Array to store the rearranged passengers
        int index = 0; // Index to track the position in the result array

        // Loop through the array in steps of 'k'
        for(int i = 0; i < n; i += k) {
            // Determine the end of the current group, ensuring it doesn't exceed the array bounds
            int e = Math.min(i + k, n);

            // If the group size is exactly 'k', reverse the order
            if(e - i == k) {
                for(int j = e - 1; j >= i; j--) {
                    result[index++] = passengers[j]; // Add reversed elements to the result array
                }
            }
            // If the group size is less than 'k' (last group), keep the original order
            else {
                for(int j = i; j < e; j++) {
                    result[index++] = passengers[j]; // Add elements as is to the result array
                }
            }
        }
        return result; // Return the rearranged array
    }

    public static void main(String[] args) {
        // Test case 1: k is 2
        int[] passengers1 = rearrangePassengers(new int[]{1, 2, 3, 4, 5}, 2);
        System.out.println("Test Case 1: " + Arrays.toString(passengers1));
        // Expected output: [2, 1, 4, 3, 5]

        // Test case 2: k is 3
        int[] passengers2 = rearrangePassengers(new int[]{1, 2, 3, 4, 5, 6}, 3);
        System.out.println("Test Case 2: " + Arrays.toString(passengers2));
        // Expected output: [3, 2, 1, 6, 5, 4]

        // Test case 3: k is 4
        int[] passengers3 = rearrangePassengers(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 4);
        System.out.println("Test Case 3: " + Arrays.toString(passengers3));
        // Expected output: [4, 3, 2, 1, 8, 7, 6, 5, 9]

        // Test case 4: k is 1 (no reversal)
        int[] passengers4 = rearrangePassengers(new int[]{1, 2, 3, 4, 5}, 1);
        System.out.println("Test Case 4: " + Arrays.toString(passengers4));
        // Expected output: [1, 2, 3, 4, 5]

        // Test case 5: k is greater than the array length (no reversal)
        int[] passengers5 = rearrangePassengers(new int[]{1, 2, 3}, 5);
        System.out.println("Test Case 5: " + Arrays.toString(passengers5));
        // Expected output: [1, 2, 3]

        // Test case 6: Empty array
        int[] passengers6 = rearrangePassengers(new int[]{}, 3);
        System.out.println("Test Case 6: " + Arrays.toString(passengers6));
        // Expected output: []

        // Test case 7: k is equal to the array length (full reversal)
        int[] passengers7 = rearrangePassengers(new int[]{1, 2, 3, 4}, 4);
        System.out.println("Test Case 7: " + Arrays.toString(passengers7));
        // Expected output: [4, 3, 2, 1]
    }
}
