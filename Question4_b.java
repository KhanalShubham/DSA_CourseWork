class Question4_b {
    /**
     * Definition for a binary tree node.
     */
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            left = right = null;
        }
    }

    /**
     * Helper class to store the result of checking if a subtree is a BST.
     * Includes the minimum value, maximum value, sum of node values, maximum sum found,
     * and a flag indicating whether the subtree is a valid BST.
     */
    private class Result {
        int minValue, maxValue, sum, maxSum;
        boolean isBST;

        Result(int minValue, int maxValue, int sum, int maxSum, boolean isBST) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.sum = sum;
            this.maxSum = maxSum;
            this.isBST = isBST;
        }
    }

    /**
     * Finds the sum of the largest BST (Magical Grove) in the binary tree.
     *
     * @param root The root of the binary tree.
     * @return The sum of the largest BST in the tree.
     */
    public int largestBSTSubtree(TreeNode root) {
        return findLargestBST(root).maxSum;
    }

    /**
     * Recursively checks each subtree to determine if it is a BST.
     * Calculates the sum of the node values in the subtree if it is a BST.
     *
     * @param node The current node being checked.
     * @return The result containing information about the current subtree.
     */
    private Result findLargestBST(TreeNode node) {
        // Base case: if the node is null, it's considered a BST with sum 0.
        if (node == null) {
            return new Result(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, true);
        }

        // Recursively check the left and right subtrees.
        Result left = findLargestBST(node.left);
        Result right = findLargestBST(node.right);

        // Check if the current node forms a valid BST with its left and right subtrees.
        if (left.isBST && right.isBST && node.val > left.maxValue && node.val < right.minValue) {
            // If valid, calculate the sum of this BST.
            int currentSum = left.sum + right.sum + node.val;
            return new Result(
                    Math.min(left.minValue, node.val),
                    Math.max(right.maxValue, node.val),
                    currentSum,
                    Math.max(currentSum, Math.max(left.maxSum, right.maxSum)),
                    true
            );
        } else {
            // If not a valid BST, return the maximum sum found so far in either subtree.
            return new Result(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Math.max(left.maxSum, right.maxSum), false);
        }
    }

    /**
     * Main method to test the largestBSTSubtree functionality.
     */
    public static void main(String[] args) {
        // Test Case 1: Example tree provided
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(2);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(2);
        root1.right.right = new TreeNode(5);
        root1.right.right.left = new TreeNode(4);
        root1.right.right.right = new TreeNode(6);

        Question4_b solution = new Question4_b();
        int result1 = solution.largestBSTSubtree(root1);
        System.out.println("Test Case 1 - Largest Magical Grove Value: " + result1);  // Output: 20

        // Test Case 2: All nodes form a valid BST
        TreeNode root2 = new TreeNode(10);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(15);
        root2.left.left = new TreeNode(1);
        root2.left.right = new TreeNode(8);
        root2.right.right = new TreeNode(7);

        int result2 = solution.largestBSTSubtree(root2);
        System.out.println("Test Case 2 - Largest Magical Grove Value: " + result2);  // Output: 15

        // Test Case 3: Single node tree
        TreeNode root3 = new TreeNode(5);

        int result3 = solution.largestBSTSubtree(root3);
        System.out.println("Test Case 3 - Largest Magical Grove Value: " + result3);  // Output: 5

        // Test Case 4: Invalid BST tree where largest BST is a subtree
        TreeNode root4 = new TreeNode(10);
        root4.left = new TreeNode(15);
        root4.right = new TreeNode(5);
        root4.left.left = new TreeNode(20);
        root4.left.right = new TreeNode(11);
        root4.right.right = new TreeNode(25);

        int result4 = solution.largestBSTSubtree(root4);
        System.out.println("Test Case 4 - Largest Magical Grove Value: " + result4);  // Output: 25
    }
}
