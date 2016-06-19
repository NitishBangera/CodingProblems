
/**
* https://www.hackerrank.com/challenges/common-child
* https://en.wikipedia.org/wiki/Longest_common_substring_problem
* http://www.geeksforgeeks.org/longest-common-substring/
**/
public class CommonChild {
  public static void main(String... args) {
    String a = args[0].toUpperCase();
    String b = args[1].toUpperCase();

    int[][] board = new int[a.length() + 1][b.length() + 1];

    for (int i = 1; i <= a.length(); i++) {
      for (int j = 1; j <= b.length(); j++) {
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
          board[i][j] = board[i - 1][j - 1] + 1;
        } else {
          board[i][j] = Math.max(board[i - 1][j], board[i][j - 1]);
        }
      }
    }

    for (int i = 0; i <= a.length(); i++) {
      for (int j = 0; j <= b.length(); j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }

    System.out.println("Longest Common Substring Length : " + board[a.length()][b.length()]);
  }
}
