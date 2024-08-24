import java.util.Scanner;

public class Question5_b {

    // Function to find the length of the longest continuous hike within the elevation gain limit
    public static int longestContinuousHike(int[] trail, int k) {
        int n = trail.length; // Get the length of the trail array

        // Initialize the dp array where dp[i] represents the longest continuous hike ending at index i
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Each position in dp starts with 1, as each individual element is a valid hike
        }

        int max = 1; // Variable to track the maximum length of continuous hike found
        for (int i = 1; i < n; i++) {
            // Check if the current elevation is higher than the previous one and within the allowed gain
            if (trail[i] > trail[i - 1] && (trail[i] - trail[i - 1]) <= k) {
                // If so, extend the hike by increasing the dp value based on the previous element
                dp[i] = dp[i - 1] + 1;
            }
            // Update the maximum hike length found so far
            max = Math.max(max, dp[i]);
        }
        return max; // Return the maximum length of continuous hike
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input

        // Prompt the user to enter the length of the trail
        System.out.print("Enter the length of the trail: ");
        int n = scanner.nextInt();

        // Initialize the trail array based on user input
        int[] trail = new int[n];
        System.out.println("Enter the trail elements: ");
        for (int i = 0; i < n; i++) {
            trail[i] = scanner.nextInt(); // Fill the trail array with elevation points
        }

        // Prompt the user to enter the maximum allowed elevation gain (k)
        System.out.print("Enter the elevation gain limit (k): ");
        int k = scanner.nextInt();

        // Call the function and print the result for the longest continuous hike
        System.out.println("Longest continuous hike length: " + longestContinuousHike(trail, k));
    }
}
