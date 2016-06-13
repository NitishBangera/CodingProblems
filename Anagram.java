import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// Sorting is one method which will be nlogn.
// This method will make a pattern of count of alphabets and then match it with the list of strings.
public class Anagram {
	// Lowercase a value is 97
	private static final int MOD_VAL = 97;

	private static final int TOTAL_ALPHA = 26;

	private static void method1(String checkVal, String[] values) {
		System.out.println("Method 1");

		int[] checkPattern = new int[TOTAL_ALPHA];
		for (char c : checkVal.toLowerCase().toCharArray()) {
			checkPattern[c % MOD_VAL]++;
		}

		int[] pattern = new int[TOTAL_ALPHA];

		for (String val : values) {
			Arrays.fill(pattern, 0);
			if (val.length() != checkVal.length()){
				continue;
			}

			for (char c : val.toLowerCase().toCharArray()) {
				pattern[c % MOD_VAL]++;
			}

			boolean anagram = true;
			for (int i = 0; i < TOTAL_ALPHA; i++) {
				if (checkPattern[i] > 0 && checkPattern[i] != pattern[i]) {
					anagram = false;
					break;
				}
			}

			System.out.println(checkVal + (anagram ? " is an" : " not an") + " anagram of " + val);
		}
	}

	private static void method2(String checkVal, String[] values) {
		System.out.println("Method 2");

		for (String val : values) {
			System.out.println(checkVal + (checkAnagram(checkVal, val)
			? " is an" : " not an") + " anagram of " + val);
		}
	}

	private static boolean checkAnagram(String string1, String string2) {
		char[] checkVal = string1.toLowerCase().toCharArray();
		char[] val = string2.toLowerCase().toCharArray();
		if (checkVal.length != val.length) {
    	return false;
		}
    int[] pattern = new int[TOTAL_ALPHA];
    for(int i = 0 ; i < checkVal.length; i++) {
			pattern[checkVal[i] % MOD_VAL]++;
			pattern[val[i] % MOD_VAL]--;
    }
    for (int i = 0; i < TOTAL_ALPHA; i++) {
			if(pattern[i] != 0) {
			  return false;
			}
    }
    return true;
	}

	public static void main(String... args) {
		String[] values = new String[] {"java", "vaaj", "aavj", "javarocks", "Java", "Jaaa"};
		String checkVal = "avaj";
		method1(checkVal, values);
		method2(checkVal, values);
	}
}
