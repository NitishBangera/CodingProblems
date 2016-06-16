/**
* https://www.codechef.com/problems/COOKMACH
*/

import java.util.Scanner;

public class Chef {
	public static int resetSetting(int v) {
		return ((v & 1) == 0) ? v >> 1 : (v - 1) >> 1;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			int count = 0;
			
			// x shouldn't be equal y.
			while (x != y) {				
				if (x < y) {
					// x -> y
					x = (y % x == 0) ? x << 1 : resetSetting(x);
				} else if (x > y) {
					//y -> x
					x = resetSetting(x);
				}
				count++;
			}
			
			System.out.println(count);
		}
	}
}