import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int val = scan.nextInt();
            boolean prime = !(val <= 1);
            for (int j = 2; j <= Math.sqrt(val); j++) {
                if (val % j == 0) {
                    prime = false;
                    break;
                }
            }
            System.out.println(prime ? "Prime" : "Not prime");
        }
    }
}