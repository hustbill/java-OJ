/*
 * @lc app=leetcode id=314 lang=java
 *
 * [314] Binary Tree Vertical Order Traversal
 *
 * https://leetcode.com/problems/binary-tree-vertical-order-traversal/description/
 *
 * algorithms
 * Medium (41.63%)
 * Total Accepted:    84.9K
 * Total Submissions: 201.8K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * Given a binary tree, return the vertical order traversal of its nodes'
 * values. (ie, from top to bottom, column by column).
 * 
 * If two nodes are in the same row and column, the order should be from left
 * to right.
 * 
 * Examples 1:
 * 
 * 
 * Input: [3,9,20,null,null,15,7]
 * 
 * ⁠  3
 * ⁠ /\
 * ⁠/  \
 * ⁠9  20
 * ⁠   /\
 * ⁠  /  \
 * ⁠ 15   7 
 * 
 * Output:
 * 
 * [
 * ⁠ [9],
 * ⁠ [3,15],
 * ⁠ [20],
 * ⁠ [7]
 * ]
 * 
 * 
 * Examples 2:
 * 
 * 
 * Input: [3,9,8,4,0,1,7]
 * 
 * ⁠    3
 * ⁠   /\
 * ⁠  /  \
 * ⁠  9   8
 * ⁠ /\  /\
 * ⁠/  \/  \
 * ⁠4  01   7 
 * 
 * Output:
 * 
 * [
 * ⁠ [4],
 * ⁠ [9],
 * ⁠ [3,0,1],
 * ⁠ [8],
 * ⁠ [7]
 * ]
 * 
 * 
 * Examples 3:
 * 
 * 
 * Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left
 * child is 5)
 * 
 * ⁠    3
 * ⁠   /\
 * ⁠  /  \
 * ⁠  9   8
 * ⁠ /\  /\
 * ⁠/  \/  \
 * ⁠4  01   7
 * ⁠   /\
 * ⁠  /  \
 * ⁠  5   2
 * 
 * Output:
 * 
 * [
 * ⁠ [4],
 * ⁠ [9,5],
 * ⁠ [3,0,1],
 * ⁠ [8,2],
 * ⁠ [7]
 * ]
 * 
 * 
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    /*
     * BFS,
     * put node, col into queue at the same time
     * Every left child access col - 1 while right child col + 1
     * This maps node into different col buckets
     * Get col boundary min and max on the fly
     * Retrive result from cols
     * https://leetcode.com/problems/binary-tree-vertical-order-traversal/discuss/76401/5ms-Java-Clean-Solution
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
       List<List<Integer>> ret = new ArrayList<List<Integer>>();
       if (root == null) return ret;
        
       Map<Integer, ArrayList<Integer>> map = new HashMap<>();

       Queue<TreeNode> q = new LinkedList<>();
       Queue<Integer> cols = new LinkedList<>();
       
       q.add(root);
       cols.add(0);
       int min = 0;
       int max = 0;
    
       while (!q.isEmpty()) {
           TreeNode node = q.poll();
           int col = cols.poll();
           
           if (!map.containsKey(col)) {
               map.put(col, new ArrayList<Integer>());
           }
           map.get(col).add(node.val);
         
           if (node.left != null) {
               q.add(node.left);
               cols.add(col - 1);
               min = Math.min(min, col - 1);
           }
           if (node.right != null) {
               q.add(node.right);
               cols.add(col + 1);
               max = Math.max(max, col + 1);
           }
       }
      
       for (int i = min; i <= max; i++) {
           ret.add(map.get(i));
       }
       return ret; 
    }
    
    // my doesn't work solution,  
    // only travesal left.left, right.right, not cover left.right, or right.left
    public List<List<Integer>> verticalOrderi_nok(TreeNode root) {
        int col = 0; 
        Map<Integer, List<Integer>> map = new HashMap<>();

        List<Integer> vals = new ArrayList<>();
        vals.add(root.val);
        map.put(col, vals);

       if (root == null) return null;

       TreeNode left = root.left;
       TreeNode right = root.right;
        while (left != null) {
           col--;
           if (map.containsKey(col)) {
               vals = map.get(col);
           } else {
               vals = new ArrayList<Integer>();
          }
           vals.add(left.val);
           map.put(col, vals);
           left = left.left;
         }
         col = 0; 
         while (right != null) {
           col++;
           if (map.containsKey(col)) {
               vals = map.get(col);
           } else {
               vals = new ArrayList<Integer>();
          }
           vals.add(right.val);
           map.put(col, vals);
           right = right.right;
          }
       System.out.println(map.values());
       List<List<Integer>> ret = new ArrayList<List<Integer>>();
      
       for (List<Integer> list : map.values()) 
           ret.add(list);
       return ret; 
    }
}
