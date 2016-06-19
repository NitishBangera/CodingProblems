/*
* https://projecteuler.net/problem=15
*
* https://www.hackerrank.com/contests/projecteuler/challenges/euler015
*/
public class LatticePath {
  public static void main(String... args) {
    // Moding to 10 ^ 9 + 7 as the values will be large or use BigInteger
    final long modVal = 1000000007;

    // Adding 1 to m and n for border traversals.
    int m = Integer.parseInt(args[0]) + 1;
    int n = Integer.parseInt(args[1]) + 1;

    long[][] board = new long[m][n];

    for (int i = 0; i < m; i++) {
      board[i][0] = 1;
    }

    for (int i = 0; i < n; i++) {
      board[0][i] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        board[i][j] = (board[i][j - 1] + board[i - 1][j]) % modVal;
      }
    }

    System.out.println("Number of paths : " + board[m - 1][n - 1]);
  }
}
