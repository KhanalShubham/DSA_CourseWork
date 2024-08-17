import java.util.*;

public class Question3_a {
    private final int n;
    private Set<List<Integer>> restrictions;
    private List<Set<Integer>> friends;

    public Question3_a(int n, List<List<Integer>> restrictions) {
        this.n = n;
        this.restrictions = new HashSet<>(restrictions);
        this.friends = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            this.friends.add(new HashSet<>());
        }
    }

    private boolean canBeFriends(int house1, int house2) {
        // Check direct restriction
        if (restrictions.contains(Arrays.asList(house1, house2)) ||
                restrictions.contains(Arrays.asList(house2, house1))) {
            return false;
        }

        // Check indirect restrictions
        for (List<Integer> restriction : restrictions) {
            int r1 = restriction.get(0);
            int r2 = restriction.get(1);
            if (r1 == house1 || r2 == house1) {
                int other = (r1 == house1) ? r2 : r1;
                if (dfs(house2, other, new HashSet<>())) {
                    return false;
                }
            }
            if (r1 == house2 || r2 == house2) {
                int other = (r1 == house2) ? r2 : r1;
                if (dfs(house1, other, new HashSet<>())) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean dfs(int current, int target, Set<Integer> visited) {
        if (current == target) {
            return true;
        }
        visited.add(current);
        for (int friend : friends.get(current)) {
            if (!visited.contains(friend)) {
                if (dfs(friend, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> processRequests(List<List<Integer>> requests) {
        List<String> results = new ArrayList<>();
        for (List<Integer> request : requests) {
            int house1 = request.get(0);
            int house2 = request.get(1);
            if (canBeFriends(house1, house2)) {
                friends.get(house1).add(house2);
                friends.get(house2).add(house1);
                results.add("approved");
            } else {
                results.add("denied");
            }
        }
        return results;
    }

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