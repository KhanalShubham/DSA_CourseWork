import java.util.*;

public class Question1_a {

    static class Solution {

        /**
         * Method to find the most booked meeting room.
         * @param n The number of available meeting rooms.
         * @param meetings A 2D array where each element is a meeting represented by its start and end time.
         * @return The index of the room that was booked the most times.
         */
        public int mostBooked(int n, int[][] meetings) {
            // Sort meetings by start time; if start times are equal, sort by duration
            Arrays.sort(meetings, (a, b) -> a[0] != b[0] ? a[0] - b[0] : (a[1] - a[0]) - (b[1] - b[0]));

            // PriorityQueue to track available rooms, ordered by room number
            PriorityQueue<Integer> availableRooms = new PriorityQueue<>();
            for (int i = 0; i < n; i++) {
                availableRooms.offer(i);
            }

            // PriorityQueue to track occupied rooms, ordered by end time and room number
            PriorityQueue<long[]> occupiedRooms = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

            // Array to count how many times each room has been used
            int[] roomUsageCount = new int[n];

            // Iterate over each meeting
            for (int[] meeting : meetings) {
                long startTime = meeting[0];
                long endTime = meeting[1];

                // Free up rooms that have become available by the current meeting start time
                while (!occupiedRooms.isEmpty() && occupiedRooms.peek()[0] <= startTime) {
                    int roomToFree = (int) occupiedRooms.poll()[1];
                    availableRooms.offer(roomToFree);
                }

                if (!availableRooms.isEmpty()) {
                    // If there is an available room, use it for the current meeting
                    int room = availableRooms.poll();
                    occupiedRooms.offer(new long[]{endTime, room});
                    roomUsageCount[room]++;
                } else {
                    // If no room is available, delay the meeting to the next available room time
                    long[] nextAvailable = occupiedRooms.poll();
                    long newEndTime = nextAvailable[0] + (endTime - startTime);
                    int room = (int) nextAvailable[1];
                    occupiedRooms.offer(new long[]{newEndTime, room});
                    roomUsageCount[room]++;
                }
            }

            // Find the room with the highest usage count
            int maxUsage = 0;
            int result = 0;
            for (int i = 0; i < n; i++) {
                if (roomUsageCount[i] > maxUsage) {
                    maxUsage = roomUsageCount[i];
                    result = i;
                }
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1
        int n1 = 2;
        int[][] meetings1 = {{0, 10}, {1, 5}, {2, 7}, {3, 4}};
        int result1 = solution.mostBooked(n1, meetings1);
        System.out.println("Test case 1:");
        System.out.println("Input: n = " + n1 + ", meetings = " + Arrays.deepToString(meetings1));
        System.out.println("Output: " + result1);
        System.out.println("Expected: 0");
        System.out.println();

        // Test case 2
        int n2 = 3;
        int[][] meetings2 = {{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
        int result2 = solution.mostBooked(n2, meetings2);
        System.out.println("Test case 2:");
        System.out.println("Input: n = " + n2 + ", meetings = " + Arrays.deepToString(meetings2));
        System.out.println("Output: " + result2);
        System.out.println("Expected: 1");
        System.out.println();

        // Test case 3
        int n3 = 4;
        int[][] meetings3 = {{1, 10}, {2, 7}, {3, 19}, {4, 12}, {5, 14}, {6, 15}, {7, 16}};
        int result3 = solution.mostBooked(n3, meetings3);
        System.out.println("Test case 3:");
        System.out.println("Input: n = " + n3 + ", meetings = " + Arrays.deepToString(meetings3));
        System.out.println("Output: " + result3);
        System.out.println("Expected: 0");
    }
}
