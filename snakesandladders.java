// TC: O(n)
// SC: O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Solution {
    public int snakesAndLadders(int[][] board) {
        // Edge case check.
        if (board == null || board.length == 0) return 0;
        
        int r = board.length;
        int c = board[0].length;
        int n = r * c; // Total number of squares
        
        // Convert the 2D board into a 1D moves array.
        int[] moves = new int[n];
        int i = r - 1, j = 0;
        int even = 0, counter = 0;
        while (i >= 0) {
            // If the board cell is not -1, record the destination (adjusting for 0-index)
            moves[counter] = (board[i][j] != -1) ? board[i][j] - 1 : -1;
            counter++;
            
            // Move in the current row (zigzag: left-to-right if even, right-to-left if odd).
            if (even % 2 == 0) {
                j++;
            } else {
                j--;
            }
            
            // Check if we reached the boundary of the current row.
            if (j >= c) {
                i--;
                even++;
                j = c - 1;
            } else if (j < 0) {
                i--;
                even++;
                j = 0;
            }
        }
        
        // BFS to find the minimum moves.
        int min = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        moves[0] = -2; // Mark the starting square as visited.
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int curr = q.poll();
                // If reached last square, return the number of moves.
                if (curr == n - 1) return min;
                
                // Try dice throws from 1 to 6.
                for (int l = 1; l < 7; l++) {
                    int child = curr + l;
                    if (child < n && moves[child] != -2) {
                        // If there's a snake or ladder, use its destination.
                        if (moves[child] > -1) {
                            q.add(moves[child]);
                        } else {
                            q.add(child);
                        }
                        // Mark the square as visited.
                        moves[child] = -2;
                    }
                }
            }
            min++;
        }
        return -1;
    }
}
