class Question4_b {
    // Definition for a binary tree node.
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            left = right = null;
        }
    }

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

    public int largestBSTSubtree(TreeNode root) {
        return findLargestBST(root).maxSum;
    }

    private Result findLargestBST(TreeNode node) {
        if (node == null) {
            return new Result(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, true);
        }

        Result left = findLargestBST(node.left);
        Result right = findLargestBST(node.right);

        if (left.isBST && right.isBST && node.val > left.maxValue && node.val < right.minValue) {
            int currentSum = left.sum + right.sum + node.val;
            return new Result(Math.min(left.minValue, node.val), Math.max(right.maxValue, node.val), currentSum, Math.max(currentSum, Math.max(left.maxSum, right.maxSum)), true);
        } else {
            return new Result(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Math.max(left.maxSum, right.maxSum), false);
        }
    }

    public static void main(String[] args) {
        // Example usage:
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(4);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(2);
        root.right.right = new TreeNode(5);
        root.right.right.left = new TreeNode(4);
        root.right.right.right = new TreeNode(6);

        Question4_b solution = new Question4_b();
        int result = solution.largestBSTSubtree(root);
        System.out.println("Largest Magical Grove Value: " + result);  // Output: 20
    }
}
