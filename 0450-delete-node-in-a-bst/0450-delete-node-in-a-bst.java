/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
   if (root == null) {
            return null;
        }

        // Search for the node
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } 
        else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } 
        else {

            // Case 1: No left child
            if (root.left == null) {
                return root.right;
            }

            // Case 2: No right child
            if (root.right == null) {
                return root.left;
            }

            // Case 3: Both children exist
            // Find the smallest node in right subtree
            TreeNode minNode = root.right;

            while (minNode.left != null) {
                minNode = minNode.left;
            }

            // Replace current node's value
            root.val = minNode.val;

            // Delete the duplicate node
            root.right = deleteNode(root.right, minNode.val);
        }

        return root;      
    }
}