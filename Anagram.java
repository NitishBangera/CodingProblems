import java.util.Arrays;

// Sorting is one method which will be nlogn.
// This method will make a pattern of count of alphabets and then match it with the list of strings.
public class Anagram {
	public static void main(String... args) {
		String[] values = new String[] {"java", "vaaj", "aavj", "javarocks", "Java"};
		
		String checkVal = "avaj";
		
		int[] pattern = new int[26];
		
		for (String val : values) {
			Arrays.fill(pattern, 0);
			
		}
	}
}