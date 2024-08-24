import java.util.Arrays;

public class Question4_a {
    private static final int MAX_WEIGHT = 2_000_000_000;
    private static final int INF = Integer.MAX_VALUE / 2;

    /**
     * Plans the roads by assigning appropriate weights to construction roads (-1 weight roads)
     * such that the shortest path from the source to the destination equals the target time.
     *
     * @param n           The number of cities (nodes).
     * @param roads       The array of roads, where each road is represented as {u, v, w}.
     * @param source      The source city.
     * @param destination The destination city.
     * @param target      The desired shortest path length from source to destination.
     * @return The modified roads array with assigned weights, or null if no valid solution is found.
     */
    public static int[][] planRoads(int n, int[][] roads, int source, int destination, int target) {
        // Initialize the distance array with infinity
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        // Apply Bellman-Ford to calculate the shortest paths from the source
        for (int i = 0; i < n - 1; i++) {
            for (int[] road : roads) {
                int u = road[0], v = road[1], w = road[2];
                if (w != -1) {
                    if (dist[u] < INF) {
                        dist[v] = Math.min(dist[v], dist[u] + w);
                    }
                    if (dist[v] < INF) {
                        dist[u] = Math.min(dist[u], dist[v] + w);
                    }
                }
            }
        }

        // Check if the target is achievable with the current road network
        if (dist[destination] > target) {
            System.out.println("Target not achievable with current road network.");
            return null;
        }

        // Assign weights to construction roads
        for (int[] road : roads) {
            int u = road[0], v = road[1], w = road[2];
            if (w == -1) {
                for (int weight = 1; weight <= MAX_WEIGHT; weight++) {
                    road[2] = weight;
                    if (isValidSolution(n, roads, source, destination, target)) {
                        return roads;
                    }
                }
                road[2] = -1; // Reset if no valid weight found
            }
        }

        System.out.println("No valid solution found within constraints.");
        return null;
    }

    /**
     * Validates whether the current assignment of weights to the roads achieves the target distance.
     *
     * @param n           The number of cities (nodes).
     * @param roads       The array of roads, where each road is represented as {u, v, w}.
     * @param source      The source city.
     * @param destination The destination city.
     * @param target      The desired shortest path length from source to destination.
     * @return True if the current road weights achieve the target, otherwise false.
     */
    private static boolean isValidSolution(int n, int[][] roads, int source, int destination, int target) {
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        // Apply Bellman-Ford to check if the solution is valid
        for (int i = 0; i < n - 1; i++) {
            for (int[] road : roads) {
                int u = road[0], v = road[1], w = road[2];
                if (dist[u] < INF) {
                    dist[v] = Math.min(dist[v], dist[u] + w);
                }
                if (dist[v] < INF) {
                    dist[u] = Math.min(dist[u], dist[v] + w);
                }
            }
        }

        // Check if the distance from source to destination matches the target
        return dist[destination] == target;
    }

    /**
     * Main method for testing the functionality of the program.
     */
    public static void main(String[] args) {
        // Test Case 1
        int n1 = 5;
        int[][] roads1 = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source1 = 0;
        int destination1 = 1;
        int target1 = 5;

        System.out.println("Test Case 1:");
        int[][] result1 = planRoads(n1, roads1, source1, destination1, target1);
        if (result1 != null) {
            System.out.println("Solution found:");
            for (int[] road : result1) {
                System.out.println(Arrays.toString(road));
            }
        } else {
            System.out.println("No solution found.");
        }

        // Test Case 2: Target not achievable scenario
        int n2 = 4;
        int[][] roads2 = {{0, 1, 10}, {1, 2, 10}, {2, 3, 10}, {0, 3, -1}};
        int source2 = 0;
        int destination2 = 3;
        int target2 = 15;

        System.out.println("Test Case 2:");
        int[][] result2 = planRoads(n2, roads2, source2, destination2, target2);
        if (result2 != null) {
            System.out.println("Solution found:");
            for (int[] road : result2) {
                System.out.println(Arrays.toString(road));
            }
        } else {
            System.out.println("No solution found.");
        }

        // Additional test cases can be added here following the same pattern.
    }
}
