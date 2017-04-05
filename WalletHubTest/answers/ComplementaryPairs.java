import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
* Finding K complementary pairs in an array A
* K = A[i] + A[j] then Pair(A[i], A[j]).
* n = Numbers of values in the array.
* Time Complexity = O(n)
* Space Complexity = O(n) => Additional Set used to keep track of the difference.
**/
public class ComplementaryPairs {
    public int getPairs(final Integer[] values, final int k) {
        // Set to keep track of the difference.
        Set<Integer> check = new HashSet<Integer>();
        int pairs = 0;
        
        for (int val : values) {
            // Find the difference with K.
            Integer diff = k - val;
            
            // Check for the difference and if the set has the difference 
            // then the value was encountered earlier hence a pair is found.
            if (check.contains(diff)) {
                pairs++;
            }
            
            // Add the current value for the future difference check.
            check.add(val);
        }
        
        return pairs;
    }
    
    public void runTest() throws Exception {
        boolean testPassed = true;
        int count = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("../tests/ComplementaryPairs.txt"))) {
            // Skip first line
            String line = reader.readLine();            
            while((line=reader.readLine()) != null ) {
                count++;
                String[] tokens = Arrays.stream(line.split("\\|")).map(String::trim).toArray(String[]::new);
                Integer[] arr = Arrays.stream(tokens[0].split(",")).map(String::trim).map(Integer::parseInt).toArray(Integer[]::new);
                int k = Integer.parseInt(tokens[1]);
                int pairs = Integer.parseInt(tokens[2]);
                int testPairs = getPairs(arr, k);
                if (pairs != testPairs) {
                    System.out.printf("Testcase %s failed. Expected : %s but obtained : %s\n", count, pairs, testPairs);
                    if (testPassed) {
                        testPassed = false;
                    }
                }                
            }
        }
        
        if (testPassed) {
            System.out.println("All " + count + " tests passed");
        }
    }
    
    public static void main(String... args) throws Exception {
        ComplementaryPairs pair = new ComplementaryPairs();
        pair.runTest();
    }
}
