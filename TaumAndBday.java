/**
* https://www.hackerrank.com/challenges/taum-and-bday
**/

import java.util.Scanner;

public class TaumAndBday {
	public static void main(String... args) {
		Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while(t-- > 0){
            long b = in.nextLong();
            long w = in.nextLong();
            long x = in.nextLong();
            long y = in.nextLong();
            long z = in.nextLong();
            
            // Cost of black/white gifts is less than combined cost of white/black and conversion 
            // then no need to convert as it will increase the cost anyway.
            long minCostofBlack = (x < (y + z)) ? x : (y + z);
            long minCostofWhite = (y < (x + z)) ? y : (x + z);
            
            System.out.println(b * minCostofBlack + w * minCostofWhite);
        }
	}
}