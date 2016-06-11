/* Input
The first line contains the number of tests, T. 
T testcases follow.
For each testcase, the first line contain N(Number of ladders) and after that N line follow. 
Each of the N line contain 2 integer representing the starting point and the ending point of a ladder respectively.
The next line contain integer M(Number of snakes) and after that M line follow. 
Each of the M line contain 2 integer representing the starting point and the ending point of a snake respectively.
 
2
3
32 62
42 68
12 98
7
95 13
97 25
93 37
79 27
75 19
49 47
67 17
4
8 52
6 80
26 42
2 72
9
51 19
39 11
37 29
81 3
59 5
79 23
53 7
43 33
77 21 

Output
For each of the T test cases, output one integer, each in a new line, 
which is the least number of moves (or rolls of the die) in which the player can 
reach the target square (Square Number 100) after starting at the base (Square Number 1). 
This corresponds to the ideal sequence of faces which show up when the die is rolled. 
If there is no solution, print -1.
3
5
*/

import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

class Cell {
    private int position;
    private int distance;

    public Cell(int position, int distance) {
        this.position = position;
        this.distance = distance;
    }
    
    public int getPosition() {
        return position;
    }
    
    public int getDistance() {
        return distance;
    }
}

public class SnakeAndLadder {
    public static int getMinimumMoves(final int[] board) {
        final int n = board.length;
        Queue<Cell> queue = new LinkedList<Cell>();
        Set<Integer> visited = new HashSet<Integer>();
        
        queue.add(new Cell(1 , 0));
        visited.add(1);
        
        Cell cell = null;
        while(!queue.isEmpty()) {
            cell = queue.poll();
            int pos = cell.getPosition();
            
            if (pos == n - 1) {
                break;
            }
            
            for (int i = (pos + 1); i <= (pos + 6) && i < n; i++) {
                if (!visited.contains(i)) {
                    int position = (board[i] != -1) ? board[i] : i;
                    int distance = cell.getDistance() + 1;
                    queue.add(new Cell(position, distance));
                    visited.add(i);
                }
            }
        }
        
        return (cell.getPosition() == n - 1) ? cell.getDistance() : -1;
    }
    
    public static void main(String[] args) {
        final int N = 100;
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        int[] board = new int[N + 1];
        
        for (int i = 0; i < t; i++) {
            // 1 to 100
            for (int k = 1; k <= N; k++) {
                board[k] = -1;
            }
            
            int n = scan.nextInt();
            // Ladder go from lower to upper cell.
            for (int j = 0; j < n; j++) {
                board[scan.nextInt()] = scan.nextInt();
            }
            
            int m = scan.nextInt();
            // Snakes go from upper to lower cell.
            for (int j = 0; j < m; j++) {
                board[scan.nextInt()] = scan.nextInt();
            }
            
            System.out.println(getMinimumMoves(board));
        }
    }
}