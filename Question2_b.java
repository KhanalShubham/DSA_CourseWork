import java.util.Arrays;

class Question2_b {

    /**
     * Method to find if there is a pair of elements in the array such that:
     * 1. The difference in their indices is less than or equal to indexDiff.
     * 2. The difference in their values is less than or equal to valueDiff.
     *
     * @param nums The array of integers.
     * @param indexDiff The maximum allowed difference between the indices of the pair.
     * @param valueDiff The maximum allowed difference between the values of the pair.
     * @return true if such a pair exists, otherwise false.
     */
    public static boolean findSeatingArrangement(int[] nums, int indexDiff, int valueDiff) {
        // Iterate through the array with two nested loops to compare each pair of elements
        for (int i = 0; i < nums.length; i++) {
            // Inner loop to check elements within the indexDiff range from the current element
            for (int j = i + 1; j < Math.min(nums.length, i + indexDiff + 1); j++) {
                // Check if the absolute difference in values is less than or equal to valueDiff
                if (Math.abs(nums[i] - nums[j]) <= valueDiff) {
                    return true; // A valid pair is found, return true
                }
            }
        }
        return false; // No valid pair found, return false
    }

    public static void main(String[] args) {
        // Create an instance of the Question2_b class
        Question2_b obj = new Question2_b();

        // Test case 1
        int[] nums1 = {2, 3, 5, 4, 9};
        int indexDiff1 = 2;
        int valueDiff1 = 1;
        boolean result1 = Question2_b.findSeatingArrangement(nums1, indexDiff1, valueDiff1);
        System.out.println("Test case 1:");
        System.out.println("Input: nums = " + Arrays.toString(nums1) + ", indexDiff = " + indexDiff1 + ", valueDiff = " + valueDiff1);
        System.out.println("Output: " + result1);
        System.out.println("Expected: true");
        System.out.println();

        // Test case 2
        int[] nums2 = {1, 5, 9, 1, 5, 9};
        int indexDiff2 = 2;
        int valueDiff2 = 3;
        boolean result2 = Question2_b.findSeatingArrangement(nums2, indexDiff2, valueDiff2);
        System.out.println("Test case 2:");
        System.out.println("Input: nums = " + Arrays.toString(nums2) + ", indexDiff = " + indexDiff2 + ", valueDiff = " + valueDiff2);
        System.out.println("Output: " + result2);
        System.out.println("Expected: false");
        System.out.println();

        // Test case 3
        int[] nums3 = {1, 2, 3, 1};
        int indexDiff3 = 3;
        int valueDiff3 = 0;
        boolean result3 = Question2_b.findSeatingArrangement(nums3, indexDiff3, valueDiff3);
        System.out.println("Test case 3:");
        System.out.println("Input: nums = " + Arrays.toString(nums3) + ", indexDiff = " + indexDiff3 + ", valueDiff = " + valueDiff3);
        System.out.println("Output: " + result3);
        System.out.println("Expected: true");
    }
}
