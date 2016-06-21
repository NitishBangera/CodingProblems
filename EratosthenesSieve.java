/**
* https://en.wikipedia.org/wiki/Eratosthenes
* https://www.hackerrank.com/contests/projecteuler/challenges/euler010
*/
import java.util.Scanner;
import java.util.Arrays;

public class EratosthenesSieve {
  public static void main(String... args) {
    Scanner scan = new Scanner(System.in);
    int t = scan.nextInt();

    int maxSieve = 10000000;
    boolean[] prime = new boolean[maxSieve + 1];
    Arrays.fill(prime, true);

    for (int i=2; i*i<=maxSieve; i++) {
      if (prime[i]) {
        for (int j=i*2; j<=maxSieve; j += i) {
          prime[j] = false;
        }
      }
    }

    long[] sums = new long[maxSieve + 1];
    int[] primes = new int[maxSieve + 1];
    long sum = 0;
    int k = 0;
    for (int i = 2; i < maxSieve; i++) {
      if (prime[i]) {
        sum += i;
        primes[k++] = i;
      }
      sums[i] = sum;
    }

    for (int i = 0; i < t; i++) {
      int val = scan.nextInt();
      System.out.printf("Prime %d : %d\n", val, primes[val - 1]);
      System.out.printf("Sum of primes till %d : %d\n", val, sums[val]);
    }
  }
}
