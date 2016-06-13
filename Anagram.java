import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// Sorting is one method which will be nlogn.
// This method will make a pattern of count of alphabets and then match it with the list of strings.
public class Anagram {
	public static void main(String... args) {
		// Lowercase a value is 97
		final int modVal = 97;

		final int totalAlphabets = 26;
		String[] values = new String[] {"java", "vaaj", "aavj", "javarocks", "Java", "Jaaa"};

		String checkVal = "avaj";
		int[] checkPattern = new int[totalAlphabets];
		for (char c : checkVal.toLowerCase().toCharArray()) {
			checkPattern[c % modVal]++;
		}

		int[] pattern = new int[totalAlphabets];

		for (String val : values) {
			Arrays.fill(pattern, 0);
			if (val.length() != checkVal.length()){
				continue;
			}

			for (char c : val.toLowerCase().toCharArray()) {
				pattern[c % modVal]++;
			}

			boolean anagram = true;
			for (int i = 0; i < totalAlphabets; i++) {
				if (checkPattern[i] > 0 && checkPattern[i] != pattern[i]) {
					anagram = false;
					break;
				}
			}

			System.out.println(checkVal + (anagram ? " is an" : " not an") + " anagram of " + val);
		}
	}
}
