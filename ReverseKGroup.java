/*25. Reverse Nodes in k-Group
Total Accepted: 52097 Total Submissions: 195033 Difficulty: Hard
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

You may not alter the values in the nodes, only nodes itself may be changed.

Only constant memory is allowed.

For example,
Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Hide Company Tags Microsoft Facebook
Hide Tags Linked List
Hide Similar Problems (M) Swap Nodes in Pairs
 */


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}
public class ReverseKGroup {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        
        head.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = null;
        
        int k = 2;
    
        ListNode dummy = reverseKGroup(head, k);
        while (dummy != null) {
             System.out.printf("%d ", dummy.val );
            dummy = dummy.next;
            if (dummy != null) {
                System.out.printf("->");
            }
        }
    }
    
    public static ListNode reverseKGroup(ListNode head, int k) {
        // https://leetcode.com/discuss/21301/short-but-recursive-java-code-with-comments
        ListNode curr = head;
        int count = 0;
        while (curr != null  && count != k) { //find the k+1 node
            curr = curr.next;
             count++;
           }
        if (count == k) {  // if k +1 node is found
            curr = reverseKGroup(curr, k) ; // reverse list with k+1 node as head
            // head - head-pointer to direct part,
            // curr - head-pointer to reversed part;
            while (count-- > 0) { // reverse current k-group;
                ListNode tmp = head.next;  // tmp - next head in direct part
                head.next = curr;  // preappending "direct" head to the reversed list
                curr = head; // move head of reversed part to a new node
                head = tmp; // move "direct" head to the next node in direct part
            }
            head = curr;
        }
        return head;
    }
    
    
}