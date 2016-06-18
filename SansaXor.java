import java.util.Scanner;

/**
* https://www.hackerrank.com/challenges/sansa-and-xor
*/
public class SansaXor {
  public static void main(String... args) {
    Scanner scan = new Scanner(System.in);

    int t = scan.nextInt();

    while(t-- > 0) {
      int n = scan.nextInt();
      int xorVal = 0;
      // Even number variables in the array cancel out in xor.
      if ((n & 1) != 0) {
        while (n-- > 0) {
          int val = scan.nextInt();
          // Only take even indexes as odd index will cancel out.
          if ((n & 1) == 0) {
            xorVal ^= val;
          }
        }
      } else {
        while (n-- > 0) {
          scan.nextInt();
        }
      }
      System.out.println(xorVal);
    }
  }
}
