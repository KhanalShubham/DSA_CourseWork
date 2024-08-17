import java.util.*;

public class Question4_a {
    private static final int MAX_WEIGHT = 2_000_000_000;
    private static final int INF = Integer.MAX_VALUE / 2;

    public static int[][] planRoads(int n, int[][] roads, int source, int destination, int target) {
        // Initialize distance matrix
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < n; i++) {
            dist[i][i] = 0;
        }

        // Fill known distances and mark construction roads
        for (int[] road : roads) {
            int u = road[0], v = road[1], w = road[2];
            if (w != -1) {
                dist[u][v] = dist[v][u] = w;
            }
        }

        // Floyd-Warshall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // Check if target is achievable
        if (dist[source][destination] > target) {
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

    private static boolean isValidSolution(int n, int[][] roads, int source, int destination, int target) {
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < n; i++) {
            dist[i][i] = 0;
        }

        for (int[] road : roads) {
            int u = road[0], v = road[1], w = road[2];
            dist[u][v] = dist[v][u] = w;
        }

        // Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        return dist[source][destination] == target;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source = 0;
        int destination = 1;
        int target = 5;

        System.out.println("Solving for:");
        System.out.println("Number of cities: " + n);
        System.out.println("Roads: " + Arrays.deepToString(roads));
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Target time: " + target);

        int[][] result = planRoads(n, roads, source, destination, target);

        if (result != null) {
            System.out.println("Solution found:");
            for (int[] road : result) {
                System.out.println(Arrays.toString(road));
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}