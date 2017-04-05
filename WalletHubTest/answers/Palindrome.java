import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
* Finding if the string is a Palindrome.
* n = Number of characters in String.
* Time Complexity = O(n/2) = O(n)
**/
public class Palindrome {
    public boolean isPalindrome(final String input) {
        final int len = input.length();
        
        // Traversing the String from the start and back.
        for(int left = 0, right = len - 1; left <= right; left++, right--) {
            // Check the characters anf if they don't match then the string is not a palindrome.
            if (input.charAt(left) != input.charAt(right)) {
                return false;
            }
        }
        
        return true;
    }
    
    public void runTest() throws Exception {
        boolean testPassed = true;
        int count = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("../tests/Palindrome.txt"))) {
            // Skip first line
            String line = reader.readLine();            
            while((line=reader.readLine()) != null ) {
                String[] tokens = Arrays.stream(line.split("\\|")).map(String::trim).toArray(String[]::new);
                final String testString = tokens[0];
                final boolean expected = Boolean.parseBoolean(tokens[1]);
                final boolean caseSensitive = Boolean.parseBoolean(tokens[2]);
                final boolean testValue = isPalindrome(caseSensitive ? testString : testString.toLowerCase());
                if (expected != testValue) {
                    System.out.printf("Test for %s failed. Expected : %s but obtained : %s\n", testString, expected, testValue);
                    if (testPassed) {
                        testPassed = false;
                    }
                }
                count++;
            }
        }
        
        if (testPassed) {
            System.out.println("All " + count + " tests passed");
        }
    }
    
    public static void main(String... args) throws Exception {
        Palindrome pal = new Palindrome();
        pal.runTest();
    }
}
