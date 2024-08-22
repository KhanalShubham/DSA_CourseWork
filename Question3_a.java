import java.util.*;

public class Question3_a {
    private final int n;
    private Set<List<Integer>> restrictions;
    private List<Set<Integer>> friends;

    /**
     * Constructor to initialize the network with the number of houses and restrictions.
     *
     * @param n            the number of houses in the network.
     * @param restrictions a list of pairs indicating restricted friendships.
     */
    public Question3_a(int n, List<List<Integer>> restrictions) {
        this.n = n;
        // Convert restrictions list to a set for faster lookup.
        this.restrictions = new HashSet<>(restrictions);
        // Initialize the friends list where each house has its own set of friends.
        this.friends = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            this.friends.add(new HashSet<>());
        }
    }

    /**
     * Method to check if two houses can be friends.
     *
     * @param house1 the first house.
     * @param house2 the second house.
     * @return true if they can be friends, false otherwise.
     */
    private boolean canBeFriends(int house1, int house2) {
        // Check direct restriction between house1 and house2.
        if (restrictions.contains(Arrays.asList(house1, house2)) ||
                restrictions.contains(Arrays.asList(house2, house1))) {
            return false;
        }

        // Check for indirect restrictions by examining all restrictions.
        for (List<Integer> restriction : restrictions) {
            int r1 = restriction.get(0);
            int r2 = restriction.get(1);
            // If house1 is part of the restriction, check if house2 indirectly connects.
            if (r1 == house1 || r2 == house1) {
                int other = (r1 == house1) ? r2 : r1;
                if (dfs(house2, other, new HashSet<>())) {
                    return false;
                }
            }
            // If house2 is part of the restriction, check if house1 indirectly connects.
            if (r1 == house2 || r2 == house2) {
                int other = (r1 == house2) ? r2 : r1;
                if (dfs(house1, other, new HashSet<>())) {
                    return false;
                }
            }
        }

        // If no restrictions are violated, they can be friends.
        return true;
    }

    /**
     * Depth-First Search (DFS) to check if there's an indirect connection between two houses.
     *
     * @param current the current house being visited.
     * @param target  the target house to check for a connection.
     * @param visited a set to keep track of visited houses during DFS.
     * @return true if an indirect connection exists, false otherwise.
     */
    private boolean dfs(int current, int target, Set<Integer> visited) {
        if (current == target) {
            return true;
        }
        visited.add(current);
        // Explore all friends of the current house recursively.
        for (int friend : friends.get(current)) {
            if (!visited.contains(friend)) {
                if (dfs(friend, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Process all friendship requests and determine if they can be approved or denied.
     *
     * @param requests a list of friendship requests between pairs of houses.
     * @return a list of strings, "approved" or "denied", based on whether the requests can be fulfilled.
     */
    public List<String> processRequests(List<List<Integer>> requests) {
        List<String> results = new ArrayList<>();
        for (List<Integer> request : requests) {
            int house1 = request.get(0);
            int house2 = request.get(1);
            // Check if the friendship can be established.
            if (canBeFriends(house1, house2)) {
                // If approved, update the friends list for both houses.
                friends.get(house1).add(house2);
                friends.get(house2).add(house1);
                results.add("approved");
            } else {
                results.add("denied");
            }
        }
        return results;
    }

    /**
     * Static method to solve the problem with given inputs and return the results.
     *
     * @param n            the number of houses.
     * @param restrictions the restrictions between pairs of houses.
     * @param requests     the friendship requests between pairs of houses.
     * @return a list of results for each request.
     */
    public static List<String> solveFriendshipRequests(int n, List<List<Integer>> restrictions, List<List<Integer>> requests) {
        Question3_a network = new Question3_a(n, restrictions);
        return network.processRequests(requests);
    }

    public static void main(String[] args) {
        // Test case 1
        List<List<Integer>> restrictions1 = Arrays.asList(Arrays.asList(0, 1));
        List<List<Integer>> requests1 = Arrays.asList(Arrays.asList(0, 2), Arrays.asList(2, 1));
        System.out.println(solveFriendshipRequests(3, restrictions1, requests1));

        // Test case 2
        List<List<Integer>> restrictions2 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 3)
        );
        List<List<Integer>> requests2 = Arrays.asList(
                Arrays.asList(0, 4),
                Arrays.asList(1, 2),
                Arrays.asList(3, 1),
                Arrays.asList(3, 4)
        );
        System.out.println(solveFriendshipRequests(5, restrictions2, requests2));
    }
}
