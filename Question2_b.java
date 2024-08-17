import java.util.Arrays;

class Question2_b {
    public static boolean findSeatingArrangement(int[] nums, int indexDiff, int valueDiff) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < Math.min(nums.length, i + indexDiff + 1); j++) {
                if (Math.abs(nums[i] - nums[j]) <= valueDiff) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Question2_b obj=new Question2_b();

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