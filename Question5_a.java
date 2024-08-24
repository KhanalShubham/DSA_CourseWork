import java.util.Arrays;
import java.util.Random;

public class Question5_a {

    // Creating a Random object with a fixed seed (42) to ensure reproducibility of results
    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) {
        // Define the number of cities
        int numCities = 10;

        // Generate random coordinates for the cities
        double[][] cities = generateCities(numCities);

        // Find the best route using the Hill Climbing algorithm
        int[] bestRoute = hillClimbing(cities);

        // Calculate the total distance of the best route found
        double bestDistance = calculateDistance(cities, bestRoute);

        // Print the best route and the corresponding distance
        System.out.println("Best Route: " + Arrays.toString(bestRoute));
        System.out.println("Best Distance: " + bestDistance);
    }

    // Function to generate random coordinates for a specified number of cities
    private static double[][] generateCities(int numCities) {
        double[][] cities = new double[numCities][2];
        // Loop through each city and assign random coordinates between 0 and 1
        for (int i = 0; i < numCities; i++) {
            cities[i][0] = RANDOM.nextDouble(); // X coordinate
            cities[i][1] = RANDOM.nextDouble(); // Y coordinate
        }
        return cities;
    }

    // Function to calculate the total distance of a given route
    private static double calculateDistance(double[][] cities, int[] route) {
        double totalDistance = 0.0;
        // Loop through each city in the route and calculate the distance to the next city
        for (int i = 0; i < route.length; i++) {
            int from = route[i];
            int to = route[(i + 1) % route.length]; // Ensures the route is circular
            // Calculate Euclidean distance between two cities and add it to the total distance
            totalDistance += Math.hypot(cities[from][0] - cities[to][0], cities[from][1] - cities[to][1]);
        }
        return totalDistance;
    }

    // Function to create an initial random route (permutation of city indices)
    private static int[] createInitialRoute(int numCities) {
        int[] route = new int[numCities];
        // Initialize the route with a sequential list of city indices
        for (int i = 0; i < numCities; i++) {
            route[i] = i;
        }
        // Randomly shuffle the route to create a random initial solution
        for (int i = 0; i < numCities; i++) {
            int j = RANDOM.nextInt(numCities);
            // Swap cities at positions i and j
            int temp = route[i];
            route[i] = route[j];
            route[j] = temp;
        }
        return route;
    }

    // Hill Climbing algorithm to find the shortest route among cities
    private static int[] hillClimbing(double[][] cities) {
        int numCities = cities.length;

        // Start with a random initial route
        int[] currentRoute = createInitialRoute(numCities);

        // Calculate the distance of the initial route
        double currentDistance = calculateDistance(cities, currentRoute);

        boolean improved;
        do {
            improved = false;
            // Explore all neighboring routes by swapping pairs of cities
            for (int i = 0; i < numCities - 1; i++) {
                for (int j = i + 1; j < numCities; j++) {
                    // Create a new route by swapping cities at positions i and j
                    int[] neighbor = currentRoute.clone();
                    int temp = neighbor[i];
                    neighbor[i] = neighbor[j];
                    neighbor[j] = temp;

                    // Calculate the distance of the new route
                    double neighborDistance = calculateDistance(cities, neighbor);

                    // If the new route is shorter, adopt it as the current route
                    if (neighborDistance < currentDistance) {
                        currentRoute = neighbor;
                        currentDistance = neighborDistance;
                        improved = true;
                    }
                }
            }
        } while (improved); // Repeat the process until no further improvement is found

        return currentRoute;
    }
}
