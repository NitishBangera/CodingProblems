import java.util.Scanner;
import java.math.BigInteger;

/**
* https://www.hackerrank.com/challenges/counter-game
*/
public class CounterGame {
  public static void main(String... args) {
    Scanner scan = new Scanner(System.in);
    int t = scan.nextInt();

    while(t-- > 0) {
      BigInteger n = scan.nextBigInteger();
      // Main point is to count bits 1s/0s before the last 1 for power and non power of two
      // Hence flip the bits i.e. n - 1. n = 8 => 110 => 101. n = 4 => 100 => 011
      BigInteger bitCount = new BigInteger(n.subtract(BigInteger.ONE).bitCount() + "");
      // As Louse gets the first chance, if bitcount is even then Richard wins
      System.out.println(bitCount.and(BigInteger.ONE).equals(BigInteger.ZERO)
                         ? "Richard" : "Louise");
    }

    scan.close();
  }
}
