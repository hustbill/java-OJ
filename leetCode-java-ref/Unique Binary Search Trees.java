/* Given n, how many structurally unique BST's (binary search trees) that store values 1...n?

For example,
Given n = 3, there are a total of 5 unique BST's.

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3 */
   
   
/* This is a one demension dynamic programming question. For more details, refer to the following link:
http://fisherlei.blogspot.com/2013/03/leetcode-unique-binary-search-trees.html    */

public class Solution {
    public int numTrees(int n) {
        int[] result=new int[n+1];
        result[0]=1;
        result[1]=1;
        for(int i=2;i<=n;++i){
            for(int j=0;j<i;++j){
                result[i]+=result[j]*result[i-1-j];
            }
        }
        return result[n];
    }
}
