import java.util.Arrays;
import java.util.PriorityQueue;

class Question1_classroom {
    public int mostBooked(int n, int[][] meetings) {
        // Sort meetings by start time, then by duration if start times are equal
        Arrays.sort(meetings, (a, b) -> a[0] != b[0] ? a[0] - b[0] : (a[1] - a[0]) - (b[1] - b[0]));

        // PriorityQueue to keep track of available rooms (room number)
        PriorityQueue<Integer> availableRooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            availableRooms.offer(i);
        }

        // PriorityQueue to keep track of occupied rooms (end time, room number)
        PriorityQueue<long[]> occupiedRooms = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

        int[] roomUsageCount = new int[n];

        for (int[] meeting : meetings) {
            long startTime = meeting[0];
            long endTime = meeting[1];

            // Free up rooms that have become available
            while (!occupiedRooms.isEmpty() && occupiedRooms.peek()[0] <= startTime) {
                int roomToFree = (int) occupiedRooms.poll()[1];
                availableRooms.offer(roomToFree);
            }

            if (!availableRooms.isEmpty()) {
                // Room is available, use it
                int room = availableRooms.poll();
                occupiedRooms.offer(new long[]{endTime, room});
                roomUsageCount[room]++;
            } else {
                // No room available, delay the meeting
                long[] nextAvailable = occupiedRooms.poll();
                long newEndTime = nextAvailable[0] + (endTime - startTime);
                int room = (int) nextAvailable[1];
                occupiedRooms.offer(new long[]{newEndTime, room});
                roomUsageCount[room]++;
            }
        }

        // Find the room with the highest usage
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

