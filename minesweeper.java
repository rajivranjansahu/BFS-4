// TC: O(n)
// SC: O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Solution {
    // Eight possible directions (up, down, left, right, and diagonals)
    private int[][] dirs = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0},
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    
    // Instance fields for board dimensions
    private int m;
    private int n;
    
    public char[][] updateBoard(char[][] board, int[] click) {
        if (board == null || board.length == 0) return board;
        
        // Set the instance dimensions from the board to use in getMines
        this.m = board.length;
        this.n = board[0].length;
        
        int i = click[0];
        int j = click[1];
        
        // Rule 1: If a mine ('M') is revealed, mark it as 'X'
        if (board[i][j] == 'M') {
            board[i][j] = 'X';
            return board;
        }
        
        // Initialize the queue for BFS with the starting click position.
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int mines = getMines(board, curr[0], curr[1]);
            
            if (mines == 0) {
                board[curr[0]][curr[1]] = 'B';
                // Expand into all eight possible directions.
                for (int[] dir : dirs) {
                    int r = curr[0] + dir[0];
                    int c = curr[1] + dir[1];
                    // Check bounds and whether the cell is unrevealed ('E')
                    if (r >= 0 && r < m && c >= 0 && c < n && board[r][c] == 'E') {
                        // Mark as 'B' to avoid adding duplicates and add to queue
                        board[r][c] = 'B';
                        q.add(new int[]{r, c});
                    }
                }
            } else {
                board[curr[0]][curr[1]] = (char) (mines + '0');
            }
        }
        return board;
    }
    
    private int getMines(char[][] board, int i, int j) {
        int count = 0;
        for (int[] dir : dirs) {
            int r = i + dir[0];
            int c = j + dir[1];
            if (r < 0 || r >= m || c < 0 || c >= n) continue;
            if (board[r][c] == 'M') count++;
        }
        return count;
    }
}
