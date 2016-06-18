import java.util.Scanner;

/**
* https://projecteuler.net/problem=2
**/
public class EvenFibonacci {
  public static void main(String... args) {
    Scanner scan = new Scanner(System.in);

    int t = scan.nextInt();

    // 0,2,8,34,... E(n)= 4*E(n-1) + E(n-2)
    while(t-- > 0) {
      long n = scan.nextLong();
      long sum = 0;
      long a = 0;
      long b = 2;
      long temp = 0;

      while (b <= n) {
        temp = b;
        sum += b;
        b = 4 * b + a;
        a = temp;
      }

      System.out.println(sum);
    }
    scan.close();
  }
}
