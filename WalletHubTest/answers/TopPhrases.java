import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
* Finding top occuring phrases.
* n = Number of lines in the file
* m = Number of phrases in each line.
* Time Complexity = O(nm)
* Space Complexity = O(m + m) = O(2m) = O(m)
**/
public class TopPhrases {
    private List<String> getPhrases(final String fileName, final int limit) {
        final Map<String, Integer> topPhrases = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while((line=reader.readLine()) != null ) {
                // Splits the line and trims leading and trailing spaces.
                String[] phrases = Arrays.stream(line.split("\\|")).map(String::trim).toArray(String[]::new);
                for (String phrase : phrases) {
                    // Check for occurances of Phrases.
                    if (topPhrases.containsKey(phrase)) {
                        int count = topPhrases.get(phrase);
                        topPhrases.put(phrase, ++count);
                    } else {
                        topPhrases.put(phrase, 1);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
        // Sort the top phrases in decending order and trim it down to the limit and then create a List of phrases.
        return topPhrases.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit).map(Map.Entry::getKey).collect(Collectors.toList());
    }
    
    public void runTest() throws Exception {
        boolean testPassed = true;
        int count = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("../tests/TopPhrases.txt"))) {
            // Skip first line
            String line = reader.readLine();            
            while((line=reader.readLine()) != null ) {
                count++;
                String[] tokens = Arrays.stream(line.split("\\|")).map(String::trim).toArray(String[]::new);
                final String fileName = tokens[0];
                final int limit = Integer.parseInt(tokens[1]);
                String[] topExpectedPhrases = Arrays.stream(tokens[2].split(",")).map(String::trim).toArray(String[]::new);
                
                List<String> testPhrases = getPhrases(fileName, limit);
                
                if (testPhrases.size() == topExpectedPhrases.length) {
                    for (int i = 0; i < testPhrases.size(); i++) {
                        if (!testPhrases.get(i).equals(topExpectedPhrases[i])) {
                            System.out.printf("Testcase %s failed. Expected : %s but obtained : %s\n", count, topExpectedPhrases[i], testPhrases.get(i));
                            testPassed = false;
                        }
                    }
                } else {
                    System.out.printf("Testcase %s failed. Expected : %s but obtained : %s\n", count, topExpectedPhrases.length, testPhrases.size());
                    testPassed = false;
                }
            }
        }
        
        if (testPassed) {
            System.out.println("All " + count + " tests passed");
        }
    }
    
    public static void main(String... args) throws Exception {
        TopPhrases topPhrases = new TopPhrases();
        topPhrases.runTest();
    }
}
