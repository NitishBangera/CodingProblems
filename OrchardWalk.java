/**
* You have a rectangular apple orchard full of ripe apples. The orchard is divided into
* squares and is represented by a 2 dimensional array. Each element in the array is an
* int, showing the number of apples at the corresponding square in the orchard.
* Our goal is to collect as many apples as we can by walking from the bottom left corner
* of the orchard to the top right corner, taking all apples in each square along the way.
* From a given square you can only go either up, or to the right.
* For example consider that this is the orchard:
* [[4, 0, 1],
* [1, 0, 0],
* [0, 4, 0]]
* At most you can collect 6 apples, by going up (+1), up (+4), right (+0), right (+1).
* Write a method that takes a 2 dimensional int array as the argument, and
* returns an int. The returned int is the maximum number of apples that you can
* collect. Try to make sure that the method runs fast.
* public int collectApples(int[][] orchard);
* 
* Let’s assume you also have 2 tokens. While you are walking in the orchard, you can
* use the tokens to double the number of apples on the current square. You can only
* use one token on any given square.
* Modify the method you have to take the tokens into account. The method
* signature should remain the same.
* 
* Runing the program : javac OrchardWalk.java ; java OrchardWalk <tokens>
*/

import java.util.List;
import java.util.ArrayList;

/**
* Walk the orchard and collect fruits.
*/
public class OrchardWalk {
    private volatile int tokens;
    
    /**
    * Setting the number of tokens to be used on the orchard.
    */
    public void setTokens(final int tokens) {
        if (tokens > 0) {
            this.tokens = tokens; 
        }
    }
    
    /**
    * Collect the maximum number of apples going through the orchard.
    */
    public int collectApples(int[][] orchard) {
        final int[][] walkCollect = getMaxApplePath(orchard);
        int tokens = this.tokens;
        if (tokens > 0 ) {
            List<Integer> vals = getActualPath(orchard, walkCollect);
            vals.sort((a, b) -> Integer.compare(b, a));
            int totalApples = 0;
            for (int val : vals) {
                if (tokens > 0) {
                    totalApples += val * 2;
                    tokens--;
                } else {
                    totalApples += val;
                }                
            }
            return totalApples;
        } else {
            // Get the Top right cell info.
            return walkCollect[0][orchard[0].length - 1];
        }
    }
    
    /**
    * Get the maximum collection path traversing from bottom left to top right.
    */
    private int[][] getMaxApplePath(int[][] orchard) {
        int m = orchard.length;
        int n = orchard[0].length;
        // Bottom left start point.
        int startRow = m - 1;
        int startColumn = 0;
        
        // Temp matrix to hold path collections.
        int[][] walkCollect = new int[m][n];
        walkCollect[startRow][startColumn] = orchard[startRow][startColumn];
        
        // Populate leftmost column.
        for (int i = startRow - 1; i >= 0; i--) {
            walkCollect[i][0] = walkCollect[i + 1][0] + orchard[i][0];
        }
        
        // Populate bottom row.
        for (int i = startColumn + 1; i < n; i++) {
            walkCollect[startRow][i] = walkCollect[startRow][i - 1] + orchard[startRow][i];
        }
        
        for (int i = startRow - 1; i >= 0; i--) {
            for (int j = startColumn + 1; j < n; j++) {
                // Max of previous cells(left and bottom) to get the max apples.
                walkCollect[i][j] = Math.max(walkCollect[i][j - 1], walkCollect[i + 1][j]) + orchard[i][j];
            }
        }
        return walkCollect;
    }
    
    /**
    * BackTrack the maximum apple's path from the destination
    */
    private List<Integer> getActualPath(int[][] orchard, int[][] walkCollect) {
        int m = orchard.length;
        int n = orchard[0].length;
        List<Integer> vals = new ArrayList<>();
        // Add the top right element.
        vals.add(orchard[0][n - 1]);            
        int i = 0;
        int j = n - 1;            
        
        while(true) {                            
            if (j > 0 && i < m) {                    
                if (walkCollect[i][j - 1] > walkCollect[i + 1][j]) {
                    j--;
                } else if (walkCollect[i][j - 1] < walkCollect[i + 1][j]) {
                    i++;
                }
            } else if (j == 0) {
                i++;
            } else if (i == m - 1) {
                j--;
            }
            
            // Stop when the bottom left cell is reached.
            if (i == (m - 1) && j == 0) {
                break;
            }
            
            vals.add(orchard[i][j]); 
        }
        // Add the bottom left element.
        vals.add(orchard[m - 1][0]);
        return vals;
    }
    
    public static void main(String... args) {
        int orchard[][]= {{4, 0, 1}, 
                          {1, 0, 0}, 
                          {0, 4, 0}};
        OrchardWalk walk = new OrchardWalk();
        int tokens = args.length > 0 ? Integer.parseInt(args[0]) : 0;
        walk.setTokens(tokens);
        System.out.println("Tokens : " + tokens);
        System.out.println("Max Apples : " + walk.collectApples(orchard));
    }
}
